package com.dysnomia.objects;

import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;

import com.dysnomia.utils.GLLoader;

public class SelectionHighlightBox extends SelfPositioningVoxel implements Drawable {

	public SelectionHighlightBox(int id, float x, float y, float z) {
		super(id, x, y, z, new Color(0.0f, 0.0f, 0.0f, 0.0f));
	}
	
	public Vector3f coords() {
		return new Vector3f(x,y,z);
	}
	
	@Override
	public void draw() {
		GLLoader.disableLighting();
		glColor4f(Color.cyan.r,Color.cyan.g,Color.cyan.b,Color.cyan.a);
		glPushMatrix();
			glTranslatef(x,y,z);
			glBegin(GL_LINES);
				this.drawVertex();
			glEnd();
		glPopMatrix();
		GLLoader.enableLighting();
	}
	
	protected void drawVertex() {
		this.drawFront();
		this.drawBack();
		this.drawSides();
	}
	
	protected void drawFront() {
		// Top Line
		glVertex3f(-0.5f*size, -0.5f*size, 0.5f*size);
		glVertex3f( 0.5f*size, -0.5f*size, 0.5f*size);
		// Left Line
		glVertex3f(-0.5f*size, -0.5f*size, 0.5f*size);
		glVertex3f(-0.5f*size,  0.5f*size, 0.5f*size);
		// Bottom Line
		glVertex3f( 0.5f*size,  0.5f*size, 0.5f*size);
		glVertex3f(-0.5f*size,  0.5f*size, 0.5f*size);
		// Right Line
		glVertex3f( 0.5f*size,  0.5f*size, 0.5f*size);
		glVertex3f( 0.5f*size, -0.5f*size, 0.5f*size);
	}
	
	protected void drawBack() {
		// Top Line
		glVertex3f(-0.5f*size, -0.5f*size, -0.5f*size);
		glVertex3f( 0.5f*size, -0.5f*size, -0.5f*size);
		// Left Line                       
		glVertex3f(-0.5f*size, -0.5f*size, -0.5f*size);
		glVertex3f(-0.5f*size,  0.5f*size, -0.5f*size);
		// Bottom Line                     
		glVertex3f( 0.5f*size,  0.5f*size, -0.5f*size);
		glVertex3f(-0.5f*size,  0.5f*size, -0.5f*size);
		// Right Line                      
		glVertex3f( 0.5f*size,  0.5f*size, -0.5f*size);
		glVertex3f( 0.5f*size, -0.5f*size, -0.5f*size);
	}
	
	
	protected void drawSides() {
		// Top Left Line
		glVertex3f(-0.5f,  0.5f, -0.5f);
		glVertex3f(-0.5f,  0.5f,  0.5f);
		// Bottom Left Line
		glVertex3f(-0.5f, -0.5f, -0.5f);
		glVertex3f(-0.5f, -0.5f,  0.5f);
		// Top Right Line
		glVertex3f( 0.5f, 0.5f, -0.5f);
		glVertex3f( 0.5f, 0.5f,  0.5f);
		// Bottom Right Line
		glVertex3f( 0.5f, -0.5f, -0.5f);
		glVertex3f( 0.5f, -0.5f, 0.5f);
	}
	
}
