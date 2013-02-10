package com.dysnomia.objects;

import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.GL_QUADS;

import org.newdawn.slick.Color;

public class Voxel implements Drawable {

	protected int id = 0;
	protected float size = 1.0f;
	protected Color color = Color.white;
	
	public Voxel(int id) {
		this.id = id;
	}
	
	public Voxel(int id, float size) {
		this(id);
		this.size = size;
	}
	
	public Voxel(int id, float size, Color color) {
		this(id, size);
		this.color = color;
	}
	
	@Override
	public void draw() {
		glBegin(GL_QUADS);
			glColor4f(color.r, color.g, color.b, color.a);
			this.drawVertex();
		glEnd();
	}
	
	protected void drawVertex() {
		this.drawFront();
		this.drawBack();
		this.drawTop();
		this.drawBottom();
		this.drawRight();
		this.drawLeft();
	}
	
	protected void drawFront() {
		glNormal3f(0.0f, 0.0f, 1.0f);
		glVertex3f(-0.5f*size, -0.5f*size,  0.5f*size);
		glVertex3f( 0.5f*size, -0.5f*size,  0.5f*size);
		glVertex3f( 0.5f*size,  0.5f*size,  0.5f*size);
		glVertex3f(-0.5f*size,  0.5f*size,  0.5f*size);
	}
	
	protected void drawBack() {
		glNormal3f(0.0f, 0.0f, -1.0f);
		glVertex3f(-0.5f*size, -0.5f*size, -0.5f*size);
		glVertex3f(-0.5f*size,  0.5f*size, -0.5f*size);
		glVertex3f( 0.5f*size,  0.5f*size, -0.5f*size);
		glVertex3f( 0.5f*size, -0.5f*size, -0.5f*size);
	}
	
	protected void drawTop() {
		glNormal3f(0.0f, 1.0f, 0.0f);
		glVertex3f(-0.5f*size,  0.5f*size, -0.5f*size);    // Point 1 (Top) // TL
        glVertex3f(-0.5f*size,  0.5f*size,  0.5f*size);    // Point 2 (Top) // BL
        glVertex3f( 0.5f*size,  0.5f*size,  0.5f*size);    // Point 3 (Top) // BR
        glVertex3f( 0.5f*size,  0.5f*size, -0.5f*size);    // Point 4 (Top) // TR
	}
	
	protected void drawBottom() {
		glNormal3f(0.0f, -1.0f, 0.0f);
	    glVertex3f(-0.5f*size, -0.5f*size, -0.5f*size);    // Point 1 (Bottom) // BL
        glVertex3f( 0.5f*size, -0.5f*size, -0.5f*size);    // Point 2 (Bottom) // BR
        glVertex3f( 0.5f*size, -0.5f*size,  0.5f*size);    // Point 3 (Bottom) // TR
        glVertex3f(-0.5f*size, -0.5f*size,  0.5f*size);    // Point 4 (Bottom) // TL
	}
	
	protected void drawRight() {
		glNormal3f(1.0f, 0.0f, 0.0f);
		glVertex3f( 0.5f*size, -0.5f*size, -0.5f*size);    // Point 1 (Right) // BR
        glVertex3f( 0.5f*size,  0.5f*size, -0.5f*size);    // Point 2 (Right) // TR
        glVertex3f( 0.5f*size,  0.5f*size,  0.5f*size);    // Point 3 (Right) // TL
        glVertex3f( 0.5f*size, -0.5f*size,  0.5f*size);    // Point 4 (Right) // BL
	}
	
	protected void drawLeft() {
		glNormal3f(-1.0f, 0.0f, 0.0f);
		glVertex3f(-0.5f*size, -0.5f*size, -0.5f*size);   // Point 1 (Left) // BL
        glVertex3f(-0.5f*size, -0.5f*size,  0.5f*size);   // Point 2 (Left) // BR
        glVertex3f(-0.5f*size,  0.5f*size,  0.5f*size);   // Point 3 (Left) // TR
        glVertex3f(-0.5f*size,  0.5f*size, -0.5f*size);   // Point 4 (Left) // TL
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Color getColor() {
		return this.color;
	}

}
