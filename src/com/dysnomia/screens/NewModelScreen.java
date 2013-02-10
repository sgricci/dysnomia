package com.dysnomia.screens;

import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.dysnomia.forms.Label;
import com.dysnomia.utils.GLLoader;
import com.dysnomia.utils.HUD;
import com.dysnomia.utils.ScreenManager;
import com.dysnomia.utils.fontRenderer;

public class NewModelScreen extends ParentScreen implements Screen {
	
	protected String currentInput = "";
	
	public NewModelScreen(ScreenManager sm) {
		super(sm);
	}
	
	@Override
	public void draw() {

	}

	@Override
	public void input() {
//		System.out.println("Input");
		String input = "";
		while(Keyboard.next()) {
			if (!Keyboard.getEventKeyState()) {
				break;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
				// Delete last char
				if (currentInput.length() == 0) {
					break;
				}
				currentInput = currentInput.substring(0, currentInput.length()-1);
			} else if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
				sm.setState(new EditorScreen(sm, currentInput));
			} else {
				char key = Keyboard.getEventCharacter();
				if (!Character.isLetterOrDigit(key)) {
					break;
				}
				input = Character.toString(key);
				if (input != null) {
					currentInput += input;
				}
			}
		}
		

	}

	@Override
	public void hud() {
		fontRenderer.write(new Label("Model Name: "+currentInput, 0, (int)Display.getHeight()/2-40, 40.0f, Color.green));
		fontRenderer.tick();
	}

}
