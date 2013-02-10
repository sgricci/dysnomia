package com.dysnomia.tests;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector2f;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import com.dysnomia.forms.Label;
import com.dysnomia.forms.Slider;
import com.dysnomia.utils.GLLoader;
import com.dysnomia.utils.IdGenerator;
import com.dysnomia.utils.fontRenderer;

public class SliderTest {
	protected boolean running = true;
	protected DisplayMode displayMode;
	protected Slider slide;
	protected Vector2f mpos;
	protected boolean changed = false;
	protected Label lbl;
	protected Label valueLbl;
	
	public SliderTest() {
		init();
		mpos = new Vector2f(0,0);
	}
	
	private void init() {
		initDisplay();
		fontRenderer.init();
		fontRenderer.keep("SliderTest", 0, 0);
		GLLoader.initGL(displayMode);
		slide = new Slider(IdGenerator.getId(), -452f, -337f);
		lbl = new Label("R:", 40, 40, 14f);
		valueLbl = new Label("0", 325, 40, 14f);
		slide.onChange(valueLbl);
		//slide = new Slider(170, -452f, -335f);
	}
	
	private void initDisplay() {
		try {
			Display.setFullscreen(false);
			displayMode = getDisplayMode();
			Display.setDisplayMode(displayMode);
			Display.setTitle("Slider Test");
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
		slide.pick();
		GLLoader.exitOrtho();
	}
	
	private void draw() {
		
		fontRenderer.write(lbl);
		fontRenderer.write(valueLbl);
		GLLoader.enterOrtho();
		slide.draw();
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
		SliderTest st = new SliderTest();
		st.run();
	}
}
