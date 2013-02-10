package com.dysnomia.utils;

import java.nio.ByteBuffer;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Picker {

	public static int run() {
		return run(Mouse.getX(), Mouse.getY());
	}
	
	public static int run(int x, int y) {
		ByteBuffer pixels = ByteBuffer.allocateDirect(3);
		glReadPixels(x,y,1,1,GL_RGB,GL_UNSIGNED_BYTE, pixels);
		pixels.rewind();
		byte px;
		px = pixels.get();
		int red   = (int) (px & 0xFF);
		if (px < 0) red++;
		px = pixels.get();
		int green = (int) (px & 0xFF);
		if (px < 0) green++;
		px = pixels.get();
		int blue  = (int) (px & 0xFF);
		if (px < 0) blue++;
		
		return ColorGUID.encode(red, green, blue);
	}
	
	public static int run(Vector3f pos) {
		return run((int) pos.x, (int) pos.y);
	}
	
	public static int run(Vector2f pos) {
		return run((int) pos.x, (int) pos.y);
	}
}
