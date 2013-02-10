/**
 * 
 */
package com.dysnomia.utils;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;
import com.dysnomia.forms.Label;

/**
 * @author steve
 * Yes, Volatile is volatil (because Volatile is a keyword)
 */
@SuppressWarnings("deprecation")
public class fontRenderer {
	public static Map<String, TrueTypeFont> fonts = new HashMap<String, TrueTypeFont>();
	private static Map<String, Label> nonVolatil = new HashMap<String, Label>();
	private static Map<String, Label> volatil = new HashMap<String, Label>();
	
	private static Color defaultColor = Color.white;
	private static float defaultSize = 20.0f;
	
	public static void init() {
		initFont(14f);
		initFont(20f);
		initFont(40f);
	}
	
	protected static void initFont(float size) {
		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream("fonts/PT_Sans.ttf");
			
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(size);
			fonts.put(String.valueOf(size), new TrueTypeFont(awtFont, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void keep(String text, int x, int y) {
		keep(text,x,y,defaultSize, defaultColor);
	}
	
	public static void keep(String text, int x, int y, float size, Color color) {
		nonVolatil.put(text+String.valueOf(x)+String.valueOf(y), new Label(text, x, y, size, color));
	}
	
	public static void keep(Label lbl) {
		keep(lbl.text(), (int)lbl.pos().x, (int)lbl.pos().y, lbl.size(), lbl.color());
	}
	
	public static void write(Label lbl) {
		write(lbl.text(), (int) lbl.pos().x, (int) lbl.pos().y, lbl.size(), lbl.color());
	}
	
	public static void write(String text, int x, int y) {
		write(text,x,y,defaultSize,defaultColor);
	}
	
	public static void write(String text, int x, int y, float size, Color color) {
		volatil.put(text+String.valueOf(x)+String.valueOf(y), new Label(text, x, y, size, color));
	}
	
	public static void tick() {
		GLLoader.enterOrtho();
		for(String key : nonVolatil.keySet()) {
			draw(nonVolatil.get(key));
		}
		
		for (String key : volatil.keySet()) {
			draw(volatil.get(key));
		}
		GLLoader.exitOrtho();
		// Clear volatil
		volatil = new HashMap<String, Label>();
	}
	
	private static void draw(Label lbl) {
		draw(lbl.text(), lbl.pos(), lbl.size(), lbl.color());
	}
	
	private static void draw(String text, Vector2f pos, float size, Color clr) {
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glLoadIdentity();
		
		fonts.get(String.valueOf(size)).drawString(pos.x, pos.y, text, clr);
		
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}
	
	public static int width(String text, float size) {
		return fonts.get(String.valueOf(size)).getWidth(text);
	}
}
