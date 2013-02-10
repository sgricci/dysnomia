package com.dysnomia.screens;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import com.dysnomia.forms.Label;
import com.dysnomia.menu.ExitEditorCallback;
import com.dysnomia.menu.LoadEditorCallback;
import com.dysnomia.menu.MenuItem;
import com.dysnomia.menu.NewEditorCallback;
import com.dysnomia.menu.NewModelCallback;
import com.dysnomia.menu.OptionsEditorCallback;
import com.dysnomia.menu.PickableMenu;
import com.dysnomia.utils.ColorIdGenerator;
import com.dysnomia.utils.GLLoader;
import com.dysnomia.utils.IdGenerator;
import com.dysnomia.utils.Picker;
import com.dysnomia.utils.ScreenManager;
import com.dysnomia.utils.fontRenderer;

public class MainMenuScreen extends ParentScreen implements Screen {
	
	protected Vector3f mouse_pos = new Vector3f(0.0f, 0.0f, 0.0f);
	protected boolean mouse_moved = false;
	protected int selected = 0;
	protected PickableMenu menu;
	
	public MainMenuScreen(ScreenManager sm) {
		super(sm);
		menu = new PickableMenu(50f, 50f);
		menu.addItem(new MenuItem("New", new NewModelCallback(sm)));
		menu.addItem(new MenuItem("Load", new LoadEditorCallback()));
		menu.addItem(new MenuItem("Options", new OptionsEditorCallback()));
		menu.addItem(new MenuItem("Exit", new ExitEditorCallback(sm)));
	}
	
	@Override
	public void draw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		if (mouse_moved) {
			mouse_moved = false;
			glLoadIdentity();
			GLLoader.enterOrtho();
			menu.drawPickable();
			GLLoader.exitOrtho();
			selected = Picker.run();
			if (selected != 0 && selected != 1644825) {
				menu.hover(selected);
			} else {
				menu.unhover();
			}

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		}
	}

	@Override
	public void input() {
		while(Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && Keyboard.getEventKeyState()) {
				sm.stop();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && Keyboard.getEventKeyState()) {
				// Go to next state
				ColorIdGenerator.reset();
				IdGenerator.reset();
				sm.setState(new EditorScreen(sm));
			}
		}
		
		if (Mouse.getX() != mouse_pos.x) {
			mouse_pos.x = Mouse.getX();
			mouse_pos.y = Mouse.getY();
			mouse_moved = true;
		}
		if (Mouse.getY() != mouse_pos.y) {
			mouse_pos.x = Mouse.getX();
			mouse_pos.y = Mouse.getY();
			mouse_moved = true;
		}
		
		while(Mouse.next()) {
			if (Mouse.isButtonDown(0) && Mouse.getEventButtonState() && selected != 0 && selected != 1644825) {
				menu.selected(selected);
			}
		}
	}
	
	@Override
	public void hud() {
		fontRenderer.write(new Label("Dysnomia", (int)Display.getWidth()-412, 250, 40.0f, Color.lightGray));
		
		menu.draw();
		fontRenderer.tick();
	}
}
