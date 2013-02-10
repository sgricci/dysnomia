package com.dysnomia.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;

public class HUD {
	private FPSCounter cnt;
	private String[] info = new String[0];
	private Vector3f cursor_position;

	public HUD() {
		cnt = new FPSCounter();
	}

	public void draw() {
		GLLoader.enterOrtho();
		cnt.updateFPS();
		// Do rendering
		drawTime();
		drawInfo();
		
		if (cnt.getFPS() != 0) {
			drawFPS(cnt.getFPS());
		}
		GLLoader.exitOrtho();
		this.clearInfo();
	}
	
	public Vector3f get_cursor_position() {
		return this.cursor_position;
	}
	
	private void drawTime()	{
		DateFormat dateFormat = new SimpleDateFormat("hh:mma");
		Date date = new Date();
		fontRenderer.write(dateFormat.format(date), (int) Display.getWidth()-74, 10, 14f, Color.white);
	}

	private void drawFPS(long fps) {
		fontRenderer.write("FPS: "+fps, 10, Display.getHeight()-50, 14f, Color.white);
	}
	
	private void drawInfo() {
		int size = info.length;
		int height = 10;
		for (int i=0; i<size; i++) {
			fontRenderer.write(info[i], 10, height, 14f, Color.white);
			height += 20;
		}
	}

	public void addInfo(String str) {
		String[] old_info = new String[info.length];
		old_info = info;
		info = new String[old_info.length+1];
		
		int size = old_info.length;
		for (int i=0; i<size; i++) {
			info[i] = old_info[i];
		}
		info[size] = str;
	}
	
	public void clearInfo() {
		info = new String[0];
	}
}
