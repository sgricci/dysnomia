package com.dysnomia.EditorTools;

import com.dysnomia.screens.EditorScreen;

public class BaseTool {
	protected EditorScreen es;
	protected String name = "(Undefined)";
	public BaseTool(EditorScreen es) {
		this.es = es;
	}
	
	public String getName()	{
		return name;
	}
}
