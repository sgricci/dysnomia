package com.dysnomia.objects;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import com.dysnomia.utils.ColorGUID;

public class MenuPick implements Pickable {

	protected Vector3f position;
	protected int id;
	protected float width;
	protected float height;
	
	public MenuPick(int id, float x, float y, float z, float width, float height) {
		this.id = id;
		this.position = new Vector3f(x,y,z);
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw() {
		// This is going to be invisible to the user
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public void drawPickable() {
		Color color = ColorGUID.decodeToColor(this.id);
		glColor4f(color.r,color.g,color.b,1.0f);
		System.out.println(color);
		glPushMatrix();
			glLoadIdentity();
			
			glTranslatef(position.x,position.y,position.z);
			glBegin(GL_QUADS);
				glVertex3f(  0.0f,   0.0f,  0.0f);
				glVertex3f( width,   0.0f,  0.0f);
				glVertex3f( width, height,  0.0f);
				glVertex3f(  0.0f, height,  0.0f);
			glEnd();
		glPopMatrix();
	}

	@Override
	public Vector3f get_selection_coords(int selection) {
		return null;
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return this.position.x;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return this.position.y;
	}

	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return this.position.z;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
