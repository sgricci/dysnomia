package com.dysnomia.objects;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.newdawn.slick.Color;

public class Box {
	protected float size     = 1.0f;
	protected Color color    = Color.white;
	protected int id         = 0;
	protected boolean filled = false;
	protected boolean hover  = true;

	public Box(int id)
	{
		int identifier = id*256*256 + id*256 + id;
		this.id = identifier;
		System.out.println("Creating Box ID: "+identifier);
		color = new Color(id, id, id);
	}

	public void draw() {
		glColor4f(0.7f, 0.7f, 0.7f, 1.0f);
		
		glBegin(GL_QUADS);
			this.drawVertex();
		glEnd();
	}
	
	public void drawPicking() {
		glColor4f(color.r, color.g, color.b, 1.0f);
		glBegin(GL_QUADS);
			this.drawVertex();
		glEnd();
	}
	
	private void drawVertex() {
		glVertex3f(-0.5f*size, -0.5f*size,  0.5f*size);
		glVertex3f( 0.5f*size, -0.5f*size,  0.5f*size);
		glVertex3f( 0.5f*size,  0.5f*size,  0.5f*size);
		glVertex3f(-0.5f*size,  0.5f*size,  0.5f*size);	
		
		glVertex3f(-0.5f*size, -0.5f*size, -0.5f*size);
		glVertex3f(-0.5f*size,  0.5f*size, -0.5f*size);
		glVertex3f( 0.5f*size,  0.5f*size, -0.5f*size);
		glVertex3f( 0.5f*size, -0.5f*size, -0.5f*size);

        glVertex3f(-0.5f*size,  0.5f*size, -0.5f*size);    // Point 1 (Top) // TL
        glVertex3f(-0.5f*size,  0.5f*size,  0.5f*size);    // Point 2 (Top) // BL
        glVertex3f( 0.5f*size,  0.5f*size,  0.5f*size);    // Point 3 (Top) // BR
        glVertex3f( 0.5f*size,  0.5f*size, -0.5f*size);    // Point 4 (Top) // TR

	    glVertex3f(-0.5f*size, -0.5f*size, -0.5f*size);    // Point 1 (Bottom) // BL
        glVertex3f( 0.5f*size, -0.5f*size, -0.5f*size);    // Point 2 (Bottom) // BR
        glVertex3f( 0.5f*size, -0.5f*size,  0.5f*size);    // Point 3 (Bottom) // TR
        glVertex3f(-0.5f*size, -0.5f*size,  0.5f*size);    // Point 4 (Bottom) // TL

        glVertex3f( 0.5f*size, -0.5f*size, -0.5f*size);    // Point 1 (Right) // BR
        glVertex3f( 0.5f*size,  0.5f*size, -0.5f*size);    // Point 2 (Right) // TR
        glVertex3f( 0.5f*size,  0.5f*size,  0.5f*size);    // Point 3 (Right) // TL
        glVertex3f( 0.5f*size, -0.5f*size,  0.5f*size);    // Point 4 (Right) // BL

        glVertex3f(-0.5f*size, -0.5f*size, -0.5f*size);   // Point 1 (Left) // BL
        glVertex3f(-0.5f*size, -0.5f*size,  0.5f*size);   // Point 2 (Left) // BR
        glVertex3f(-0.5f*size,  0.5f*size,  0.5f*size);   // Point 3 (Left) // TR
        glVertex3f(-0.5f*size,  0.5f*size, -0.5f*size);   // Point 4 (Left) // TL
	}
	
	public void drawOutline() {
		if (hover) {
			glColor4f(Color.cyan.r, Color.cyan.g, Color.cyan.b, 1.0f);
		} else {
			glColor4f(Color.gray.r, Color.gray.g, Color.gray.b, 1.0f);
		}
		glBegin(GL_LINES);
			this.drawVertex();
		glEnd();
	}

	public boolean getFilled() {
		return this.filled;
	}
	
	public void setFilled(boolean b) {
		this.filled = b;
	}
	
	public int getId() {
		return this.id;
	}

	public void hover() {
		hover = true;
	}
	
	public void setHover(boolean b) {
		hover = b;
	}
	
	public boolean hovered() {
		return this.hover;
	}
}