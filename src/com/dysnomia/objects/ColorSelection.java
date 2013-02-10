package com.dysnomia.objects;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import static org.lwjgl.opengl.GL11.*;

public class ColorSelection implements Drawable {
	protected Color selection = Color.white;
	protected float width = 50f;
	protected float height = 50f;
	protected Vector2f pos;
	
	public ColorSelection(float x, float y) {
		this.pos = new Vector2f(x,y);
	}

	@Override
	public void draw() {
		glPushMatrix();
		//System.out.println(selection);
			glColor4f(selection.r, selection.g, selection.b, 1.0f);
			glTranslatef(pos.x, pos.y, -1.0f);
			glBegin(GL_QUADS);
				glVertex3f(0.0f, 0.0f, 0.0f);
				glVertex3f(width, 0.0f, 0.0f);
				glVertex3f(width, height, 0.0f);
				glVertex3f(0.0f, height, 0.0f);
			glEnd();
		glPopMatrix();
	}
	
	public Color get() {
		return this.selection;
	}
	
	public float getR() {
		return selection.r;
	}
	
	public float getG() {
		return selection.g;
	}
	
	public float getB() {
		return selection.b;
	}
	
	public float getA() {
		return 1.0f;
	}
	
	public Vector2f pos() {
		return this.pos;
	}
	
	public void setX(float x) {
		this.pos.x = x;
	}
	
	public void setY(float y) { 
		this.pos.y = y;
	}
	
	public float width() {
		return width;
	}
	
	public float height() {
		return height;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public void setRGB(float r, float g, float b) {
		this.selection = new Color(r, g, b, 1.0f);
	}
	
	public void setR(float r) {
		this.selection = new Color(r, selection.g, selection.b, 1.0f);
	}

	public void setG(float g) {
		this.selection = new Color(selection.r, g, selection.b, 1.0f);
	}
	
	public void setB(float b) {
		this.selection = new Color(selection.r, selection.g, b, 1.0f);
	}
	

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return this.pos.x;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return this.pos.y;
	}

	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
