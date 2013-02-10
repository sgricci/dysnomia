package com.dysnomia.screens;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import com.dysnomia.menu.ExitEditorCallback;
import com.dysnomia.menu.LoadEditorCallback;
import com.dysnomia.menu.MenuItem;
import com.dysnomia.menu.NewEditorCallback;
import com.dysnomia.menu.OptionsEditorCallback;
import com.dysnomia.menu.PickableMenu;
import com.dysnomia.utils.GLLoader;
import com.dysnomia.utils.Picker;
import com.dysnomia.utils.ScreenManager;

public class MenuScreen extends ParentScreen implements Screen {
	protected Vector3f mouse_pos = new Vector3f(0.0f, 0.0f, 0.0f);
	protected boolean mouse_moved = false;
	protected int selected = 0;
	protected PickableMenu menu;

	public MenuScreen(ScreenManager sm) {
		super(sm);
		
		menu = new PickableMenu(50f, 50f);
		menu.addItem(new MenuItem("New", new NewEditorCallback(sm)));
		menu.addItem(new MenuItem("Load", new LoadEditorCallback()));
		menu.addItem(new MenuItem("Options", new OptionsEditorCallback()));
		menu.addItem(new MenuItem("Exit", new ExitEditorCallback(sm)));
		
	}

	@Override
	public void draw() {
		
	}

	@Override
	public void input() {
		while(Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && Keyboard.getEventKeyState()) {
				sm.back();
			}
		}
		
		if (Mouse.getX() != mouse_pos.x) {
			mouse_pos.x = Mouse.getX();
			mouse_pos.y = Mouse.getY();
			mouse_moved = true;
			System.out.println(mouse_pos);
		}
		
		if (Mouse.getY() != mouse_pos.y) {
			mouse_pos.x = Mouse.getX();
			mouse_pos.y = Mouse.getY();
			mouse_moved = true;
		}
		
	}

	@Override
	public void hud() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		if (mouse_moved) {
			mouse_moved = false;
			GLLoader.enterOrtho();
			glLoadIdentity();
			menu.drawPickable();
			selected = Picker.run();
			GLLoader.exitOrtho();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		}

		GLLoader.enterOrtho();
		menu.draw();
		GLLoader.exitOrtho();
/*
		if (selected == menuMap.get("save")) {
			hud.drawTextBig("Save",    Color.yellow, (int) Display.getWidth() / 2 - 30, (int) Display.getHeight() / 2 - 120);
		} else {
			hud.drawTextBig("Save",    Color.white, (int) Display.getWidth() / 2 - 30, (int) Display.getHeight() / 2 - 120);
		}
		
		if (selected == menuMap.get("load")) {
			hud.drawTextBig("Load",    Color.yellow, (int) Display.getWidth() / 2 - 30, (int) Display.getHeight() / 2 - 70);
		} else {
			hud.drawTextBig("Load",    Color.white, (int) Display.getWidth() / 2 - 30, (int) Display.getHeight() / 2 - 70);
		}
		
		if (selected == menuMap.get("options")) {
			hud.drawTextBig("Options", Color.yellow, (int) Display.getWidth() / 2 - 45, (int) Display.getHeight() / 2 - 20);	
		} else {
			hud.drawTextBig("Options", Color.white, (int) Display.getWidth() / 2 - 45, (int) Display.getHeight() / 2 - 20);
		} 
		
		if (selected == menuMap.get("exit")) {
			hud.drawTextBig("Exit",    Color.yellow, (int) Display.getWidth() / 2 - 20, (int) Display.getHeight() / 2 + 30);	
		} else {
			hud.drawTextBig("Exit",    Color.white, (int) Display.getWidth() / 2 - 20, (int) Display.getHeight() / 2 + 30);
		}
		*/
	}	
}
