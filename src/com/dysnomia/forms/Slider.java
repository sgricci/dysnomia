package com.dysnomia.forms;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import com.dysnomia.utils.ColorGUID;
import com.dysnomia.utils.Picker;

import static org.lwjgl.opengl.GL11.*;

public class Slider {
	protected Vector2f position;
	protected int id;
	protected boolean dragging = false;
	protected int value = 0;
	protected int min = 0;
	protected int max = 1;
	protected float width = 258f;
	protected float height = 6.0f;
	protected float nipple_width = 6.0f;
	protected float nipple_height = 8.0f;
	protected float nipple_x = 0.0f;
	protected boolean hover = false;
	protected boolean grabbed = false;
	protected IChangeCallback changeCallback = null; 
	
	public Slider(int id, float x, float y) {
		this.position = new Vector2f(x, y);
		this.nipple_x = x;
		this.id = id;
	}
	
	public Slider(int id, float x, float y, int min, int max) {
		this(id, x, y);
		this.min = min;
		this.max = max;
	}
	
	public Slider(int id, float x, float y, int min, int max, int val) {
		this(id, x, y, min, max);
		this.value = val;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setX(float x) {
		this.position.x = x;
	}
	
	public float x() {
		return this.position.x;
	}
	
	public void setY(float y) {
		this.position.y = y;
	}
	
	public float y() {
		return this.position.y;
	}
	
	public int min() {
		return this.min;
	}
	
	public void setMin(int min) {
		this.min = min;
	}
	
	public int max() {
		return this.max;
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
	public int value() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = value;
		this.nipple_x = value+this.position.x;
		this.changeCallback.change(this.value());
	}
	
	public void setValue(float value) {
		setValue((int)value);
	}
	
	public void draw() {
		this.drawBackground();
		this.drawNipple();
	}
	
	public void onChange(IChangeCallback changeCallback) {
		this.changeCallback = changeCallback;
	}
	
	public void pick() {
		drawPick();
		int selected = Picker.run();
		if (selected == id) {
			this.hover = true;
			while (Mouse.next()) {
				if (Mouse.isButtonDown(0) && Mouse.getEventButtonState()) {
					this.grabbed = true;
				}
			}
		} else {
			this.hover = false;
		}
		if (!Mouse.isButtonDown(0) && this.grabbed) {
			this.grabbed = false;
			this.change();
		} else if (this.grabbed) {
			this.nipple_x = Mouse.getX();
			
			if (this.nipple_x < this.position.x){
				this.nipple_x = this.position.x;
			} else if (this.nipple_x > (this.position.x + width) - 6) {
				this.nipple_x = this.position.x + width-3;
			}
			this.change();
		}
		
	}

	public boolean checkSelection(int selection) {
		if (selection == id) {
			this.hover = true;
			if (Mouse.isButtonDown(0) && Mouse.getEventButtonState()) {
				this.grabbed = true;
			}
		} else if (this.hover) {
			this.hover = false;
		}
		if (this.grabbed && !Mouse.isButtonDown(0)) {
			this.grabbed = false;
			this.change();
			return true;
		} else if (this.grabbed) {
			this.nipple_x = Mouse.getX();
			if (this.nipple_x < this.position.x){
				this.nipple_x = this.position.x;
			} else if (this.nipple_x > (this.position.x + width) - 6) {
				this.nipple_x = this.position.x + width-3;
			}
			this.change();
			return true;
		}
		return false;
	}

	
	public void change() {
		this.setValue((int) (this.nipple_x-this.position.x));
	}
	
	public void drawPick() {
		this.drawBackgroundPick();
		this.drawNipplePick();
	}
	
	private void drawNipplePick() {
		drawNipple(true);
	}
	
	private void drawNipple() {
		drawNipple(false);
	}
	
	private void drawNipple(boolean pick) {
		if (pick) {
			Color color = ColorGUID.decodeToColor(id);
			glColor4f(color.r, color.g, color.b, 1.0f);
		} else if (grabbed) {
			glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
		} else if (hover) {
			glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		} else {
			glColor4f(0.9f, 0.9f, 0.9f, 1.0f);
		}
		
		glPushMatrix();
			glTranslatef(nipple_x, position.y-1, 0.0f);
			glBegin(GL_QUADS);
				glVertex3f(  0.0f,   0.0f,  0.0f);
				glVertex3f( nipple_width,   0.0f,  0.0f);
				glVertex3f( nipple_width, nipple_height,  0.0f);
				glVertex3f(  0.0f, nipple_height,  0.0f);
			glEnd();
		glPopMatrix();
	}
	
	private void drawBackgroundPick() {
		drawBackground(true);
	}
	
	private void drawBackground() {
		drawBackground(false);
	}
	
	private void drawBackground(boolean pick) {
		if (pick) {
			Color color = ColorGUID.decodeToColor(id);
			glColor4f(color.r, color.g, color.b, 1.0f);
		} else {
			glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
		}
		
		glPushMatrix();
			glTranslatef(position.x, position.y, 0.0f);
			glBegin(GL_QUADS);
			glVertex3f(  0.0f,   0.0f,  0.0f);
			glVertex3f( width,   0.0f,  0.0f);
			glVertex3f( width, height,  0.0f);
			glVertex3f(  0.0f, height,  0.0f);
			glEnd();
		glPopMatrix();
	}
	
	
}
