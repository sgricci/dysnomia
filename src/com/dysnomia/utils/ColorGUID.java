package com.dysnomia.utils;

import org.newdawn.slick.Color;


public class ColorGUID {
	private static final int factor_r = 65535;
	private static final int factor_g = 255;
	private static final int factor_b = 1;
	
	public static int encode(int r, int g, int b) {
		r = r * factor_r;
		g = g * factor_g;
		b = b * factor_b;
		return r+g+b;
	}
	
	public static int encode(float r, float g, float b) {
		int red = (int)Math.round(r*256);
		int blue = (int)Math.round(b*256);
		int green = (int)Math.round(g*256);
		return encode(red,green,blue);
	}
	
	public static int[] decode(int i)
	{
		int ret[] = new int[3];
		ret[0] = (int) Math.floor(i/factor_r);
		if (ret[0] > 255) {ret[0] = 255;}
		i = i - ret[0]*factor_r;
		ret[1] = (int) Math.floor(i/factor_g);
		if (ret[1] > 255) {ret[1] = 255;}
		i = i - ret[1]*factor_g;
		ret[2] = (int) Math.floor(i/factor_b);
		if (ret[2] > 255) {ret[2] = 255;}
		return ret;
	}
	
	public static Color decodeToColor(int i) {
		int[] arrayColors = decode(i);
		
		Color color = new Color(arrayColors[0], arrayColors[1], arrayColors[2]);
		return color;
	}
	
	public static void main(String[] args) {
		int id = ColorGUID.encode(100, 150, 255);
		System.out.println(id);
		int[] ret = ColorGUID.decode(id);
		System.out.println(ret[0]);
		System.out.println(ret[1]);
		System.out.println(ret[2]);
	}
}
