package com.dysnomia.objects;

import java.io.ObjectOutputStream;

import org.lwjgl.util.vector.Vector3f;

public interface Pickable extends Drawable {
	public void drawPickable();

	public Vector3f get_selection_coords(int selection);
}
