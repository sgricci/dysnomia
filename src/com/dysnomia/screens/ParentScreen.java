package com.dysnomia.screens;

import java.util.HashMap;
import java.util.Map;

import com.dysnomia.utils.HUD;
import com.dysnomia.utils.ScreenManager;

abstract class ParentScreen implements Screen {
	protected ScreenManager sm;
	protected HUD hud;
	public String build = "0";

	public ParentScreen(ScreenManager sm) {
		this.sm = sm;
	}
	
	public Map<String, Object> getState() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hud", hud);
		return map;
	}
	
	@Override
	public void setState(Map<String, Object> map) {
		this.build = (String) map.get("build");
		this.hud = (HUD) map.get("hud");
	}
	
	@Override
	public void run() {
		this.draw();
		this.input();
		this.hud();
	}

	@Override
	public void setHUD(HUD hud) {
		this.hud = hud;
		
	}

}
