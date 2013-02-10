package com.dysnomia;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.dysnomia.collections.BoxGrid;
import com.dysnomia.utils.BuildManager;
import com.dysnomia.utils.GLLoader;
import com.dysnomia.utils.HUD;
import com.dysnomia.utils.Picking;

public class Editor {
	private boolean fullscreen = false;
	private boolean outline = true;
	private boolean running = true;
	private DisplayMode displayMode;
	private final String windowTitle = "Dysnomia Editor";
	private final String version = "super-pre-alpha";
	private String build;
	@SuppressWarnings("unused")
	private Picking picking;
	private boolean changed = false;
	private int selection;
	private BoxGrid grid;
	protected HUD hud;
	private float zoom = -20.0f;
	private float xrot = 14.0f;
	private float yrot = 0.0f;
	private float xoffset = -6.0f;
	private float yoffset = 1.5f;
	
	public void run() {
		try {
			initGame();
			kickStart();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void kickStart() {
		while(running) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glLoadIdentity();
			glTranslatef(0.0f, 0.0f, zoom);
			glRotatef(xrot, 1.0f, 0.0f, 0.0f);
			glRotatef(yrot, 0.0f, 1.0f, 0.0f);
			
			if (changed) {
//				selection = picking.run(xrot, yrot, zoom, hud.get_cursor_position());
				grid.setHover(selection);
			}
			glTranslatef(xoffset, yoffset, 0.0f);
			grid.draw(outline);
			input();
			
			hud.draw();
			hud.clearInfo();
			Display.update();
		}
		Display.destroy();
		System.exit(0);
	}

	private void input() {
		if (Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			running = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && Mouse.isGrabbed() == true) {
			System.out.println("Released!");
			Mouse.setGrabbed(false);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
			zoom += 0.01f;
			changed = true;
		} 
		if (Keyboard.isKeyDown(Keyboard.KEY_V)) {
			zoom -= 0.01f;
			changed = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			xrot -= 0.1f;
			changed = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			xrot += 0.1f;
			changed = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			yrot += 0.1f;
			changed = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			yrot -= 0.1f;
			changed = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			xoffset += 0.05f;
			changed = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			xoffset -= 0.05f;
			changed = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			yoffset += 0.05f;
			changed = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			yoffset -= 0.05f;
			changed = true;
		}
		
		if (Mouse.isButtonDown(0)) {
			grid.setFilled(selection);
		}
		
		while(Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_O) && Keyboard.getEventKeyState()) {
				outline = !outline;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_TAB) && Keyboard.getEventKeyState()) {
				xrot =    14.0f;
				yrot =     0.0f;
				xoffset = -6.0f;
				yoffset =  1.5f;
				zoom =   -20.0f;
				changed = true;
			}
		}

		hud.addInfo("Zoom: "+String.valueOf(zoom)+", Xoffset: "+String.valueOf(xoffset)+", Yoffset: "+String.valueOf(yoffset));
		hud.addInfo("X-Rot: "+String.valueOf(xrot)+", Y-Rot: "+String.valueOf(yrot));
		hud.addInfo("Selection: "+String.valueOf(selection));
		hud.addInfo("Outline: "+String.valueOf(outline));
	}

	private void initGame() {
		build = initBuildManager();
		initDisplay();
		initHUD();
		picking = new Picking();
		GLLoader.initGL(displayMode);
		grid = new BoxGrid();
		grid.fill();
		grid.fillCenter();
	}
	
	private String initBuildManager() {
		BuildManager bm = new BuildManager("src/com/dysnomia/build.props");
		return String.valueOf(bm.getBuild());
	}

	private void initDisplay() {
		try {
			Display.setFullscreen(fullscreen);
			displayMode = getDisplayMode();
			Display.setDisplayMode(displayMode);
			
			Display.setTitle(windowTitle+" - "+version+" b"+build);
			Display.create();
		} catch(LWJGLException e) {
			e.printStackTrace();
			System.exit(2);
		}
	}
	
	private DisplayMode getDisplayMode() throws LWJGLException {
		DisplayMode[] d = Display.getAvailableDisplayModes();
		for (int i = 0; i < d.length; i++) {
			if (d[i].getWidth() == 800
					&& d[i].getHeight() == 600
					&& d[i].getBitsPerPixel() == 32) {
				return d[i];
			}
		}
		return new DisplayMode(800,600);
	}
	
	private void initHUD() {
		
	}
	
	public static void main(String[] args) {
		Editor editor = new Editor();
		editor.run();
	}

}