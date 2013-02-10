package com.dysnomia.menu;

import org.newdawn.slick.Color;
import com.dysnomia.utils.ColorGUID;
import com.dysnomia.utils.ColorIdGenerator;
import static org.lwjgl.opengl.GL11.*;

public class MenuItem {
	protected String label;
	protected int id;
	protected IClickableCallback callback;
	protected boolean hover = false;
	
	public MenuItem(String label, IClickableCallback callback) {
		this.label = label;
		this.id = ColorIdGenerator.getId();
		this.callback = callback;
	}
	
	public String text() {
		return this.label;
	}
	
	public Color color() {
		if (hover) {
			return Color.yellow;
		}
		return Color.white;
	}
	
	public void draw(float width) {
		Color clr = ColorGUID.decodeToColor(id);
		glColor4f(clr.r, clr.g, clr.b, clr.a);
		glBegin(GL_QUADS);
			glVertex3f( 0.0f, 0.0f, 0.0f);
			glVertex3f(width, 0.0f, 0.0f);
			glVertex3f(width, 50f , 0.0f);
			glVertex3f( 0.0f, 50f , 0.0f);
		glEnd();
	}
	
	public void hover() {
		this.hover = true;
	}
	
	public void unhover() {
		this.hover = false;
	}

	public int getId() {
		return this.id;
	}

	public void doCallback() {
		//System.out.println("Should be doing callback for: "+this.text());
		callback.click();
		
	}
}
