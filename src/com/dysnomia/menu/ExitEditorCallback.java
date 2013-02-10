package com.dysnomia.menu;

import com.dysnomia.utils.ScreenManager;

public class ExitEditorCallback implements IClickableCallback {
	protected ScreenManager sm;
	public ExitEditorCallback(ScreenManager sm) {
		this.sm = sm;
	}
	@Override
	public void click() {
		sm.stop();
	}

}
