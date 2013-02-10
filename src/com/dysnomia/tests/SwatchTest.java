package com.dysnomia.tests;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector2f;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import com.dysnomia.forms.ColorSwatches;
import com.dysnomia.utils.GLLoader;
import com.dysnomia.utils.fontRenderer;

public class SwatchTest {
	protected boolean running = true;
	protected DisplayMode displayMode;
	protected ColorSwatches swatch;
	protected Vector2f mpos;
	protected boolean changed = false;
	
	public SwatchTest() {
		init();
		mpos = new Vector2f(0,0);
	}
	
	private void init() {
		initDisplay();
		fontRenderer.init();
		fontRenderer.keep("SwatchTest", 0, 0);
		GLLoader.initGL(displayMode);
		swatch = new ColorSwatches();
		System.out.println(swatch.count());
	}
	
	private void initDisplay() {
		try {
			Display.setFullscreen(false);
			displayMode = getDisplayMode();
			Display.setDisplayMode(displayMode);
			Display.setTitle("Swatch Test");
			Display.create();
		} catch(LWJGLException e) {
			e.printStackTrace();
			System.exit(2);
		}
	}
	
	public void run() {
		while (running && !Display.isCloseRequested()) {
			input();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glLoadIdentity();
			glTranslatef(0.0f, 0.0f, -10.0f);
			if (changed) {
				pick();
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				changed = false;
			}
			glColor4f(Color.white.r, Color.white.g, Color.white.b, 1.0f);
			
			
			draw();
			//pick();
			fontRenderer.tick();
			
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
		System.exit(0);
	}
	
	private void pick() {
		GLLoader.enterOrtho();
		swatch.pick();
		GLLoader.exitOrtho();
	}
	
	private void draw() {
		GLLoader.enterOrtho();
		swatch.draw();
		//swatch.pick();
		GLLoader.exitOrtho();
	}
	
	private void input() {
		while(Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_Q) && Keyboard.getEventKeyState()) {
				this.stop();
			}
		}
		if (Mouse.getX() != mpos.x || Mouse.getY() != mpos.y) {
			mpos.x = Mouse.getX();
			mpos.y = Mouse.getY();
			changed = true;
		}
		if (Mouse.isButtonDown(0)) {
			changed = true;
		}
	}
	
	public void stop() {
		running = false;
	}
	
	private DisplayMode getDisplayMode() throws LWJGLException {
		DisplayMode[] d = Display.getAvailableDisplayModes();
		for (int i = 0; i < d.length; i++) {
			if (d[i].getWidth() == 1024
					&& d[i].getHeight() == 768
					&& d[i].getBitsPerPixel() == 32) {
				return d[i];
			}
		}
		return new DisplayMode(800,600); //default display mode
	}
	
	public static void main(String[] args) {
		SwatchTest st = new SwatchTest();
		st.run();
	}
}
