package com.dysnomia.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;

import com.dysnomia.menu.MenuItem;
import com.dysnomia.objects.Pickable;
import com.dysnomia.utils.IdGenerator;
import com.dysnomia.utils.fontRenderer;

public class PickableMenu implements Pickable {

	protected Vector2f pos;
	protected int id;
	protected List<MenuItem> items = new ArrayList<MenuItem>();
	protected Map<String, Integer> widths = new HashMap<String, Integer>();
	protected MenuItem currentHover;
	
	public PickableMenu(float x, float y) {
		this.pos = new Vector2f(x, y);
		this.id = IdGenerator.getId(); 
	}
	
	public void setX(float x)	{
		this.pos.x = x;
	}
	
	public float x() {
		return this.pos.x;
	}
	
	public void setY(float y) {
		this.pos.y = y;
	}
	
	public float y() {
		return this.pos.y;
	}
	
	public void addItem(MenuItem item) {
		items.add(item);
	}
		
	@Override
	public void draw() {
		
		int offset = 0;
		for (MenuItem item : items) {
			fontRenderer.write(item.text(), (int)this.pos.x, (int)this.pos.y+offset, 40.0f, item.color());
			offset += 50;
		}
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public void drawPickable() {
		int offset = 50;
		glPushMatrix();
		glTranslatef(this.pos.x, this.pos.y, -1.0f);
		for (MenuItem item : items) {
			if (!widths.containsKey(item.text())) {
				widths.put(item.text(), fontRenderer.width(item.text(), 40.0f));		
			}
			item.draw(widths.get(item.text()));
			glTranslatef(0.0f, (float)offset, 0.0f);
		}
		glPopMatrix();
	}

	@Override
	public Vector3f get_selection_coords(int selection) {
		// TODO Auto-generated method stub
		return null;
	}

	public void hover(int selected) {
		for (MenuItem item : items) {
			if (item.getId() == selected) {
				item.hover();
				currentHover = item;
			} else {
				item.unhover();
			}
		}
	}
	
	public void selected(int selected) {
		for (MenuItem item : items) {
			if (item.getId() == selected) {
				item.doCallback();
			}
		}
	}

	public void unhover() {
		// TODO Auto-generated method stub
		if (currentHover != null) {
			currentHover.unhover();
		}
	}

	@Override
	public float getX() {
		return this.pos.x;
	}

	@Override
	public float getY() {
		return this.pos.y;
	}

	@Override
	public float getZ() {
		return 0;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
