package com.dysnomia.utils;

import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;
 
@SuppressWarnings("deprecation")
public class TextRenderer {
 
	/** The fonts to draw to the screen */
	protected Map<String, TrueTypeFont> fonts = new HashMap<String, TrueTypeFont>();
	
	/** Boolean flag on whether AntiAliasing is enabled or not */
	private boolean antiAlias = true;
	
	/**
	 * Start the test 
	 */
	public TextRenderer() {
		init(14f);
		init(40f);
	}
	 
	/**
	 * Initialize resources
	 */
	public void init(float size) {
		// load a default java font
		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream("fonts/PT_Sans.ttf");
			
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(size);
			fonts.put(String.valueOf(size), new TrueTypeFont(awtFont, antiAlias));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	private void _draw(float size, String text, Color color, int x, int y) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glLoadIdentity();
		
		Color.white.bind();
		fonts.get(String.valueOf(size)).drawString(x, y, text, color);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	/**
	 * Game loop render
	 */
	public void draw(String text, Color color, int x, int y) {
		this._draw(14f, text, color, x, y);
	}

	public void drawBig(String text, Color color, int x, int y) {
		this._draw(40f, text, color, x, y);
	}
}