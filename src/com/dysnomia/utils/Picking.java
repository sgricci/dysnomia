package com.dysnomia.utils;

import static org.lwjgl.opengl.GL11.*;
import java.nio.ByteBuffer;

import org.lwjgl.util.vector.Vector3f;
public class Picking {
	public int run(int x, int y) {
		ByteBuffer pixels = ByteBuffer.allocateDirect(3);
		glReadPixels(x, y, 1, 1, GL_RGB, GL_UNSIGNED_BYTE, pixels);
		pixels.rewind();
		byte px;
		px = pixels.get();
		int red   = (int) (px & 0xFF);
		if (red < 0) red++;
		px = pixels.get();
		int green = (int) (px & 0xFF);
		if (px < 0) green++;
		px = pixels.get();
		int blue  = (int) (px & 0xFF);
		if (px < 0) blue++;
		//System.out.println("r: "+red+", g:"+green+", b:"+blue);
		//System.out.println("ID: "+ColorGUID.encode(red, green, blue));

		return ColorGUID.encode(red, green, blue);
	}
	
	public int run(Vector3f pos)
	{
		return this.run((int)pos.x, (int)pos.y);
	}
	
}
