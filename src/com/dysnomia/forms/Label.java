package com.dysnomia.forms;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import com.dysnomia.objects.Drawable;
import com.dysnomia.utils.fontRenderer;

public class Label implements IChangeCallback, Drawable {
	protected String text;
	protected Vector2f pos;
	protected float size = 20f;
	protected Color color = Color.white;
	
	public Label(String text, int x, int y) {
		this.pos = new Vector2f(x,y);
		this.text = text;
	}
	
	public Label(String text, int x, int y, float size) {
		this(text, x, y);
		this.size = size;
	}
	
	public Label(String text, int x, int y, float size, Color color) {
		this(text,x,y,size);
		this.color = color;
	}

	public String text() {
		return this.text;		
	}
	
	public Color color() {
		return this.color;
	}
	
	public float size() {
		return this.size;
	}
	
	public void setSize(float size) {
		this.size = size;
	}
	
	public Vector2f pos() {
		return this.pos;
	}
	
	public void setX(int x) {
		this.pos.x = x;
	}
	
	public void setY(int y) {
		this.pos.y = y;
	}

	@Override
	public void change(String value) {
		this.text = value;
	}

	@Override
	public void change(int value) {
		change(String.valueOf(value));
	}

	@Override
	public void draw() {
		fontRenderer.write(this);
	}

	@Override
	public int getId() {
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
		return color;
	}
}