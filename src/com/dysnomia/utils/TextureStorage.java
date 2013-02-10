package com.dysnomia.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class TextureStorage {

	private static Map<String, Texture> textures = new HashMap<String, Texture>();
	
	public static Texture get(String texture_name) {
		load(texture_name);
		return textures.get(texture_name);
	}
	
	private static void load(String texture_name) {
		if (is_loaded(texture_name))
			return;
		try {
			Texture loadableTexture = TextureLoader.getTexture("PNG", new FileInputStream("src/textures/"+texture_name+".png"));
			textures.put(texture_name, loadableTexture);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean is_loaded(String texture_name) {
		return textures.containsKey(texture_name);
	}
}
