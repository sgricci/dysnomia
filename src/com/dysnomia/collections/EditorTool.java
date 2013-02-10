package com.dysnomia.collections;


public interface EditorTool {
	void onClick(int selection);
	void onHover(int selection);
	void noHover();
	void draw();
	boolean input();
	String getName();
}
