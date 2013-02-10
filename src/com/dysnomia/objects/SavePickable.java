package com.dysnomia.objects;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.newdawn.slick.Color;

public interface SavePickable extends Pickable {
	void save(ObjectOutputStream os) throws IOException;

	void setBlockColor(Color colorSelection);
}
