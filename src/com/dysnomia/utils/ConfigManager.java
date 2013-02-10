package com.dysnomia.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	public static Properties cfg = new Properties();
	
	public static void load() {
		File f = new File("src/config/editor.properties");
		if (!f.exists()) {
			System.out.println("File Does Not Exist");
			return;
		}
		InputStream is = null;
		try {
			is = new FileInputStream(f);
			cfg.load(is);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			System.exit(3);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(2);
		}
	}
	
	public static String get(String name) {
		return cfg.getProperty(name);
	}
	
	public static int getInt(String name) {
		return Integer.valueOf(cfg.getProperty(name));
	}
}
