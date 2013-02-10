package com.dysnomia.menu;

import com.dysnomia.screens.EditorScreen;
import com.dysnomia.utils.ColorIdGenerator;
import com.dysnomia.utils.IdGenerator;
import com.dysnomia.utils.ScreenManager;

public class NewEditorCallback implements IClickableCallback {

	protected ScreenManager sm;
	
	public NewEditorCallback(ScreenManager sm) {
		this.sm = sm;
	}
	@Override
	public void click() {
		ColorIdGenerator.reset();
		IdGenerator.reset();
		sm.setState(new EditorScreen(sm));
	}

}
