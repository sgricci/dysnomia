package com.dysnomia.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.dysnomia.collections.EditorTool;
import com.dysnomia.EditorTools.*;
import com.dysnomia.forms.Label;
import com.dysnomia.objects.Drawable;
import com.dysnomia.screens.EditorScreen;

import static org.lwjgl.opengl.GL11.*;

@SuppressWarnings("unused")
public class Toolbox implements Drawable {
	protected int id;
	protected float height = 400f;
	protected float width = 40f;
	
	protected Map<String, String> items = new HashMap<String,String>();
	
	
	public Toolbox(int id) {
		this.id = id;
		addItem(this._prefix("DeleteTool"), "D");
		addItem(this._prefix("EyedropTool"), "Cl");
		addItem(this._prefix("PaintTool"), "P");
		addItem(this._prefix("CreateTool"), "C");
	}

	public void addItem(String tool, String lbl) {
		this.items.put(lbl, tool);
	}
	
	@Override
	public void draw() {
		glTranslatef(Display.getWidth() - width -1, (Display.getHeight() / 2) - (height / 2), 0.0f);
		glColor4f(0.25f, 0.25f, 0.25f, 1.0f);
		glBegin(GL_QUADS);
			glVertex3f(0.0f, 0.0f, 0.0f);
			glVertex3f(width, 0.0f, 0.0f);
			glVertex3f(width, height, 0.0f);
			glVertex3f(0.0f, height, 0.0f);
		glEnd();
		// Draw Items in the Toolbox
		glPushMatrix();
			this.drawItems();
		glPopMatrix();
		//Draw outline
		glColor4f(1.0f,1.0f,1.0f,1.0f);
		glBegin(GL_LINES);
			glVertex3f(0.0f, 0.0f, 0.0f);
			glVertex3f(width, 0.0f, 0.0f);
			
			glVertex3f(0.0f, 0.0f, 0.0f);
			glVertex3f(0.0f, height, 0.0f);

			glVertex3f(0.0f, height, 0.0f);
			glVertex3f(width, height, 0.0f);
			
			glVertex3f(width, height, 0.0f);
			glVertex3f(width, 0.0f, 0.0f);
		glEnd();
		
		
	}
	
	public void drawItems() {
		int x = 0;
		float box_height = 50f;
		for (String lbl : items.keySet()) {
			// Draw box
			glColor4f(0.30f, 0.30f, 0.30f, 1.0f);
//			glColor4f(Color.red.r, Color.red.b, Color.red.g, 1.0f);
			glBegin(GL_QUADS);
				glVertex3f(0.0f, 0.0f, 0.0f);
				glVertex3f(width, 0.0f, 0.0f);
				glVertex3f(width, box_height, 0.0f);
				glVertex3f(0.0f, box_height, 0.0f);
			glEnd();
			// Draw text
			fontRenderer.write(new Label(lbl, (int) (Display.getWidth() - width + 5), (int) ((Display.getHeight() / 2) - (height / 2) + x*box_height), 40.0f, Color.white));
			glTranslatef(0.0f, box_height, 0.0f);
			// Draw a bottom line
			glColor4f(1.0f,1.0f,1.0f,1.0f);
			glBegin(GL_LINES);
				glVertex3f(0.0f, 0.0f, 0.1f);
				glVertex3f(width, 0.0f, 0.1f);
			glEnd();
			x++;
		}
	}
	
	public void select(EditorScreen es) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glTranslatef(Display.getWidth() - width -1, (Display.getHeight() / 2) - (height / 2), 0.0f);
		float box_height = 50f;
		for (String lbl : items.keySet()) {
			Color clr = ColorGUID.decodeToColor(hashTitle(items.get(lbl)));
			// Draw box
			glColor4f(clr.r, clr.g, clr.b, clr.a);
			glBegin(GL_QUADS);
				glVertex3f(0.0f, 0.0f, 0.0f);
				glVertex3f(width, 0.0f, 0.0f);
				glVertex3f(width, box_height, 0.0f);
				glVertex3f(0.0f, box_height, 0.0f);
			glEnd();
			glTranslatef(0.0f, box_height, 0.0f);
		}
		int selection = Picker.run();
		if (selection > 0) {
			for (String lbl : items.keySet()) {
				if (selection == hashTitle(items.get(lbl))) {
					
					this.setTool(lbl, es);
					
					return;
				}
			}
		}
		return;
	}
	
	private void setTool(String string, EditorScreen es) {
		Class<?> c1 = null;
		try {
			c1 = Class.forName(items.get(string));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Constructor<?> con = null;
		try {
			con = c1.getConstructor(EditorScreen.class);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			es.setCurrentTool((EditorTool)con.newInstance(es));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void input(EditorScreen es) {
		if (Keyboard.getEventKeyState()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
				// Paint tool
				this.setTool("P", es);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
				// Eyedropper tool
				this.setTool("Cl", es);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
				// Create tool
				this.setTool("C", es);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
				// Delete tool
				this.setTool("D", es);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_P) && Keyboard.getEventKeyState()) {
			
		}
		
	}
	
	private String _prefix(String string) {
		return "com.dysnomia.EditorTools."+string;
	}

	private int hashTitle(String string) {
		char[] ca = string.toCharArray();
		String w = null;
		int code = 0;
		for (int i = 25; i < 29; i++) {
			w = Character.toString(ca[i]);
			code += w.hashCode();
		}
		
		return code-300;
	}

	public void pick() {
		glTranslatef(Display.getWidth() - width - 1, (Display.getHeight()/2) - (height/2), 0.0f);
		Color clr = ColorGUID.decodeToColor(this.id);
		glColor4f(clr.r, clr.g, clr.b, clr.a);
		glBegin(GL_QUADS);
			glVertex3f(0.0f, 0.0f, 0.0f);
			glVertex3f(width, 0.0f, 0.0f);
			glVertex3f(width, height, 0.0f);
			glVertex3f(0.0f, height, 0.0f);
		glEnd();
	}

	@Override
	public float getX() {
		return 0;
	}

	@Override
	public float getY() {
		return 0;
	}

	@Override
	public float getZ() {
		return 0;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}
}
