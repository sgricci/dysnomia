package com.dysnomia.forms;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import com.dysnomia.objects.Drawable;
import com.dysnomia.utils.ColorGUID;
import com.dysnomia.utils.GLLoader;
import com.dysnomia.utils.Picker;

import static org.lwjgl.opengl.GL11.*;

public class ColorSwatches implements Drawable {
	protected float[][] colors = new float[112][3];
	protected int x = 0;
	protected float size = 15.0f;
	protected int per_row = 16;
	protected int id = 0;
	protected Vector2f pos;
	private int ptr;
	
	
	public ColorSwatches(int id, float x, float y) {
		this();
		this.id = id;
		this.pos = new Vector2f(x,y);
		this.build();
	}
	
	public ColorSwatches() {
		add(239, 240, 243);
		add(253, 255, 66);
		add(4, 255, 59);
		add(44, 255, 254);
		add(43, 0, 249);
		add(254, 0, 250);
		add(255);
		add(235);
		add(225);
		add(215);
		add(204);
		add(194);
		add(183);
		add(172);
		add(160);
		add(149);
		add(235, 14, 45);
		add(253, 243, 54);
		add(0, 167, 85);
		add(23,173,235);
		add(51,45,143);
		add(235, 0, 139);
		add(137);
		add(125);
		add(112);
		add(98);
		add(85);
		add(70);
		add(54);
		add(38);
		add(17);
		add(0);
		add(245,150,125);
		add(248,173,134);
		add(251,198,143);
		add(254,248,161);
		add(195,224,159);
		add(163,212,159);
		add(132,203,159);
		add(126,205,199);
		add(116,208,244);
		add(129,167,214);
		add(134,146,199);
		add(137,128,187);
		add(161,133,188);
		add(188,139,189);
		add(243,153,192);
		add(244,151,158);
		add(240,107,84);
		add(244,141,93);
		add(248,175,102);
		add(253,246,118);
		add(170,213,121);
		add(124,198,123);
		add(62,186,123);
		add(41,187,179);
		add(21,190,239);
		add(75,139,199);
		add(90,115,181);
		add(98,90,165);
		add(134,93,165);
		add(167,97,166);
		add(239,106,168);
		add(239,106,126);
		add(235,14,45);
		add(240,100,48);
		add(244,147,52);
		add(253,243,54);
		add(140,200,77);
		add(59,182,82);
		add(0,167,85);
		add(0,169,156);
		add(23,173,235);
		add(19,112,184);
		add(18,83,163);
		add(51,45,143);
		add(103,40,142);
		add(146,31,141);
		add(235, 0, 139);
		add(235,0,92);
		add(156,2,22);
		add(158,65,25);
		add(162,97,28);
		add(170,161,36);
		add(88,134,49);
		add(27,124,54);
		add(0,115,58);
		add(0,116,106);
		add(12,118,160);
		add(10,74,125);
		add(9,51,111);
		add(31,16,97);
		add(69,8,96);
		add(99,0,94);
		add(157,0,92);
		add(156,0,57);
		add(120,0,1);
		add(122,46,5);
		add(124,73,10);
		add(129,124,20);
		add(63,103,33);
		add(1,96,38);
		add(0,89,40);
		add(0,90,81);
		add(7,90,125);
		add(6,53,97);
		add(5,32,84);
		add(17,0,74);
		add(50,0,72);
		add(75,0,71);
		add(121,0,69);
		add(120,0,39);
	}
	
	
	public void setPos(Vector2f pos) {
		this.pos = pos;
	}
	
	private void add(int r, int g, int b) {
		float[] c = new float[3];
		c[0] = (float)r;
		c[0] = c[0]/255;
		c[1] = (float)g;
		c[1] = c[1]/255;
		c[2] = (float)b;
		c[2] = c[2]/255;
		colors[x] = c;
		x++;
	}
	
	private void add(int a) {
		float[] c = new float[3];
		c[0] = (float)a;
		c[0] = c[0]/255;
		c[1] = c[0];
		c[2] = c[0];
		colors[x] = c;
		x++;
	}
	
	public int count() {
		return this.colors.length;
	}
	
	protected void setX(float x)
	{
		this.pos.x = x;
	}
	
	protected void setY(float y) {
		this.pos.y = y;
	}

	public void build() {
		this.ptr = glGenLists(1);
		
		glNewList(this.ptr, GL_COMPILE);
		{
			int length = colors.length;
			for (int i = 0; i < length; i++) {
				if ((i % per_row) == 0) 
					glTranslatef(-size*per_row, size, 0.0f);
				glColor4f(colors[i][0], colors[i][1], colors[i][2], 1.0f);
				glBegin(GL_QUADS);
					glVertex3f( 0.0f,  0.0f, 0.0f);	
					glVertex3f( size,  0.0f, 0.0f);
					glVertex3f( size,  size, 0.0f);
					glVertex3f( 0.0f,  size, 0.0f);
				glEnd();
				glTranslatef(size, 0.0f, 0.0f);
			}
		}
		glEndList();
	}
	
	@Override
	public void draw() {
		glPushMatrix();
			glTranslatef(pos.x, pos.y, -1.0f);
			glCallList(this.ptr);
		glPopMatrix();
	}

	@Override
	public int getId() {
		return this.id;
	}

	public void pick() {
		glPushMatrix();
			glTranslatef(pos.x-240, pos.y+15, -1.0f);
			int length = colors.length / per_row;
			float si = length*size;
			float width = per_row*size;
			Color clr = ColorGUID.decodeToColor(id);
			glColor4f(clr.r,clr.g,clr.b,1.0f);
			glBegin(GL_QUADS);
				glVertex3f(  0.0f,   0.0f, 0.0f);
				glVertex3f( width,   0.0f, 0.0f);
				glVertex3f( width,     si, 0.0f);
				glVertex3f(  0.0f,     si, 0.0f);
			glEnd();
		glPopMatrix();
	}

	public Color selection() {
		GLLoader.enterOrtho();
		draw();
		int selection = Picker.run();
		GLLoader.exitOrtho();
		return ColorGUID.decodeToColor(selection);
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
