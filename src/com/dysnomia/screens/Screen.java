package com.dysnomia.screens;

import java.util.Map;

import com.dysnomia.utils.HUD;

public interface Screen {
	public void draw();
	public void input();
	public void hud();
	public void run();
	public Map<String, Object> getState();
	public void setState(Map<String,Object> map);
	public void setHUD(HUD hud);
}
