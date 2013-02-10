package com.dysnomia.utils;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import com.dysnomia.utils.GLLoader;
import com.dysnomia.screens.MainMenuScreen;
import com.dysnomia.screens.MenuScreen;
import com.dysnomia.screens.Screen;

public class ScreenManager {
	private boolean fullscreen = false;
	private final String windowTitle = "Dysnomia";
	private final String version = "v0.2";
	private Screen currentState;
	private boolean running = true;
	public String build;
	private HUD hud;
	private DisplayMode displayMode;
	private Screen previousState;

	
	public ScreenManager() {
		currentState = new MainMenuScreen(this);
	}
	
	public void init() {
		ConfigManager.load();
		this.build = initBuildManager();
		initDisplay();
		
		GLLoader.initGL(displayMode);
		fontRenderer.init();
		this.hud = new HUD();
		currentState.setHUD(hud);
	}
	
	private String initBuildManager() {
		BuildManager bm = new BuildManager("src/com/dysnomia/build.props");
		return String.valueOf(bm.getBuild());
	}
	
	public void fullscreen_toggle() {
		fullscreen = !fullscreen;
		try {
			Display.setFullscreen(fullscreen);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
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
			if (d[i].getWidth() == ConfigManager.getInt("width")
					&& d[i].getHeight() == ConfigManager.getInt("height")
					&& d[i].getBitsPerPixel() == 32) {
				return d[i];
			}
		}
		return new DisplayMode(800,600); // Default display mode
	}
	
	public void stop() {
		running = false;
	}
	
	public void setState(Screen nextState) {
		nextState.setState(currentState.getState());
		this.currentState = nextState;
	}
	
	public void run() { 
		init();
		
		while(running && !Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glLoadIdentity();
			currentState.run();
			Display.update();
		}
		Display.destroy();
		System.exit(0);
	}
	
	public static void main(String[] args) {
		ScreenManager sm = new ScreenManager();
		sm.run();
	}

	public void menu() {
		this.previousState = this.currentState;
		Screen newState = new MenuScreen(this);
		newState.setState(previousState.getState());
		this.currentState = newState;
	}
	
	public void back() {
		this.currentState = this.previousState;
		
	}
}