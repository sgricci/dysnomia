package com.dysnomia.utils;

/**
 * Static class to generate ids linearly
 * @author steve
 *
 */

public class IdGenerator {
	public static int lastId = 1;
	
	public static int getId() {
		lastId++;
		return lastId;
	}
	
	public static int getLastId() {
		return lastId;
	}
	
	public static void reset() {
		lastId = 1;
	}
}
