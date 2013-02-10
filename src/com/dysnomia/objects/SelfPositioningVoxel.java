package com.dysnomia.objects;

import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glPopMatrix;

import org.newdawn.slick.Color;

public class SelfPositioningVoxel extends Voxel implements Drawable {

	protected float x = 0;
	protected float y = 0;
	protected float z = 0;
	
	public SelfPositioningVoxel(int id, float x, float y, float z, Color color) {
		super(id, 1.0f, color);
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void draw() {
		//if (id == 2) {
			//glColor4f(Color.green.r, Color.green.g, Color.green.b, 1.0f);
		//} else {
			glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		//}
		glPushMatrix();
			glTranslatef(x, y, z);
			super.draw();
		glPopMatrix();
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public float getZ() {
		return this.z;
	}
}
