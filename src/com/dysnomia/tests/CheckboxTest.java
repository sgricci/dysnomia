package com.dysnomia.tests;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import com.dysnomia.forms.Checkbox;
import com.dysnomia.utils.GLLoader;
import com.dysnomia.utils.IdGenerator;
import com.dysnomia.utils.fontRenderer;

/**
 * @author steve
 */

public class CheckboxTest {
	protected boolean running = true;
	protected DisplayMode displayMode;
	protected boolean fullscreen;
	protected Texture currentTexture;
	protected Checkbox chk;
	
	public CheckboxTest() {
		init();
	}
	
	private void init() {
		initDisplay();
		fontRenderer.init();
		fontRenderer.keep("CheckboxTest", 0, 0);
		chk = new Checkbox(IdGenerator.getId(), 1.0f, 1.0f, "Label");
		GLLoader.initGL(displayMode);
	}
	
	private void initDisplay() {
		try {
			Display.setFullscreen(fullscreen);
			displayMode = getDisplayMode();
			Display.setDisplayMode(displayMode);
			Display.setTitle("Checkbox Test");
			Display.create();
		} catch(LWJGLException e) {
			e.printStackTrace();
			System.exit(2);
		}
	}
	
	
	public void run() {
		while(running && !Display.isCloseRequested()) {
			input();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glLoadIdentity();
			glColor4f(Color.white.r, Color.white.g, Color.white.b, 1.0f);

			glTranslatef(0.0f, 0.0f, -10.0f);
			chk.draw();
			fontRenderer.tick();
			
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
		System.exit(0);
	}
	
	public void input() {
		while(Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_Q) && Keyboard.getEventKeyState()) {
				this.stop();
			}
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
		return new DisplayMode(800,600); // Default display mode
	}
	
	public static void main(String[] args) {
		CheckboxTest ct = new CheckboxTest();
		ct.run();
	}
}