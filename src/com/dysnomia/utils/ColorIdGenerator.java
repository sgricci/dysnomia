package com.dysnomia.utils;
/**
 * Generator for color ids, static and linear
 * @author steve
 *
 */
public class ColorIdGenerator {

	public static int lastId = 0;
	
	public static int getId() {
		lastId++;
		return lastId;
	}
	
	public static int getLastId() {
		return lastId;
	}
	
	public static void reset() {
		lastId = 0;
	}
}