package com.dysnomia.menu;

import com.dysnomia.screens.NewModelScreen;
import com.dysnomia.utils.ColorIdGenerator;
import com.dysnomia.utils.IdGenerator;
import com.dysnomia.utils.ScreenManager;

public class NewModelCallback implements IClickableCallback {
	
	protected ScreenManager sm;
	
	public NewModelCallback(ScreenManager sm) {
		this.sm = sm;
	}

	@Override
	public void click() {
		ColorIdGenerator.reset();
		IdGenerator.reset();
		sm.setState(new NewModelScreen(sm));
	}

}
