package com.dysnomia.forms;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.dysnomia.objects.Pickable;
import com.dysnomia.utils.TextureStorage;
import com.dysnomia.utils.fontRenderer;

public class Checkbox implements Pickable {
	protected Vector2f position;
	protected int id;
	protected boolean checked = false;
	protected Label label;
	protected float width = 0.15f;
	protected float height = 0.15f;
	
	public Checkbox(int id, float x, float y, String label) {
		this(id, x, y);
		x += 1.0f;
		this.label = new Label(label, (int) 160, (int) 43, 14f); 
	}
	
	public Checkbox(int id, float x, float y) {
		this.id = id;
		this.position = new Vector2f(x,y);
	}
	
	public boolean checked() {
		return this.checked;
	}
	
	@Override
	public void draw() {
		Texture tex = TextureStorage.get("checkbox_unchecked");
		if (checked) {
			tex = TextureStorage.get("checkbox_checked");
		}
		tex.bind();
		glEnable(GL_TEXTURE_2D);
		this.setX(-4.0f);
		this.setY( 3.5f);
		this.drawVertex(true);
		glDisable(GL_TEXTURE_2D);
	}
	
	public void setX(float x) {
		position.x = x;
	}
	
	public float getX() {
		return position.x;
	}
	
	public void setY(float y) {
		position.y = y;
	}
	
	public float getY() {
		return position.y;
	}
	
	public Label getLabel() {
		return this.label;
	}
	
	protected void drawVertex(boolean withTexture) {
		glPushMatrix();
			glTranslatef(position.x, position.y, 0.0f);
			glBegin(GL_QUADS);
				if (withTexture)
					glTexCoord2f(0.0f, 1.0f);
				glVertex3f(  0.0f,   0.0f,  0.0f);
				if (withTexture)
					glTexCoord2f(1.0f, 1.0f);
				glVertex3f( width,   0.0f,  0.0f);
				if (withTexture)
					glTexCoord2f(1.0f, 0.0f);
				glVertex3f( width, height,  0.0f);
				if (withTexture)
					glTexCoord2f(0.0f, 0.0f);
				glVertex3f(  0.0f, height,  0.0f);
			glEnd();
		glPopMatrix();
		this.label.setY(43);
		this.label.setX(160);
		//System.out.println(this.label.size());
		fontRenderer.write(this.label);
	}
	
	@Override
	public int getId() {
		return this.id;
	}
	
	@Override
	public void drawPickable() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Vector3f get_selection_coords(int selection) {
		// TODO Auto-generated method stub
		return null;
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
