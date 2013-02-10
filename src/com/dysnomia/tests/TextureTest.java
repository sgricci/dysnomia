package com.dysnomia.tests;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import com.dysnomia.utils.GLLoader;
import com.dysnomia.utils.TextureStorage;

/**
 * @author steve
 */

public class TextureTest {
	protected boolean running = true;
	protected DisplayMode displayMode;
	protected boolean fullscreen;

	private Texture currentTexture;
	
	public TextureTest() {
		init();
	}
	
	private void init() {
		initDisplay();
		GLLoader.initGL(displayMode);
	}
	
	private void initDisplay() {
		try {
			Display.setFullscreen(fullscreen);
			displayMode = getDisplayMode();
			Display.setDisplayMode(displayMode);
			Display.setTitle("Texture Test");
			Display.create();
		} catch(LWJGLException e) {
			e.printStackTrace();
			System.exit(2);
		}
	}
	
	
	public void run() {
		int x = 0;
		while(running && !Display.isCloseRequested()) {
			input();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glLoadIdentity();
			glColor4f(Color.white.r, Color.white.g, Color.white.b, 1.0f);
			
			//glPushMatrix();
				glTranslatef(0.0f, 0.0f, -10.0f);
				if (x == 0) currentTexture = TextureStorage.get("checkbox_unchecked");
				if (x == 5) currentTexture = TextureStorage.get("checkbox_checked");
				if (x == 10) x = -1;
				currentTexture.bind();
				draw();
			//glPopMatrix();
			Display.update();
			Display.sync(60);
			x++;
		}
		Display.destroy();
		System.exit(0);
	}
	
	private void draw() {
		//glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
		float width = 0.15f;
		float height = 0.15f;
		glBegin(GL_QUADS);
			glTexCoord2f(0.0f, 1.0f);
			glVertex3f(  0.0f,   0.0f,  0.0f);
			glTexCoord2f(1.0f, 1.0f);
			glVertex3f( width,   0.0f,  0.0f);
			glTexCoord2f(1.0f, 0.0f);
			glVertex3f( width, height,  0.0f);
			glTexCoord2f(0.0f, 0.0f);
			glVertex3f(  0.0f, height,  0.0f);
		glEnd();
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
		TextureTest tt = new TextureTest();
		tt.run();
	}
}
