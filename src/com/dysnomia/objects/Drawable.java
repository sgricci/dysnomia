package com.dysnomia.objects;

import org.newdawn.slick.Color;

public interface Drawable {
	public void draw();
	public float getX();
	public float getY();
	public float getZ();
	public int getId();
	public Color getColor();
}