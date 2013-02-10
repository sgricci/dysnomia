package com.dysnomia.utils;

import org.lwjgl.Sys;

public class FPSCounter {
	private static int fps;
	private static int currentFPS;
	private static long lastFPS;
	private static long lastFrame;
	
	private long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public void updateFPS()
	{
		if ((getTime() - lastFPS) > 1000)
		{
			currentFPS = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	public int getFPS()
	{
		return currentFPS;
	}
	
	public int getDelta()
	{
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		
		return delta;
	}
	
	public FPSCounter() {
		lastFPS = getTime();
	}
	
}
