package com.dysnomia.screens;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;

import com.dysnomia.EditorTools.CreateTool;
import com.dysnomia.collections.EditorTool;
import com.dysnomia.collections.PickableCollection;
import com.dysnomia.forms.ColorSwatches;
import com.dysnomia.forms.Label;
import com.dysnomia.forms.Slider;
import com.dysnomia.objects.ColorSelection;
import com.dysnomia.objects.SidePickableSPVoxel;
import com.dysnomia.utils.ColorIdGenerator;
import com.dysnomia.utils.GLLoader;
import com.dysnomia.utils.IdGenerator;
import com.dysnomia.utils.Picker;
import com.dysnomia.utils.ScreenManager;
import com.dysnomia.utils.Toolbox;
import com.dysnomia.utils.fontRenderer;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glLoadIdentity;

public class EditorScreen extends ParentScreen implements Screen {
	protected PickableCollection items = new PickableCollection();
	protected float zoom = -20.0f;
	protected float xrot = 14.0f;
	protected float yrot = 0.0f;
	protected float xfloat = 0.0f;
	protected boolean changed = false;
	protected Label[] labels;
	protected Slider[] sliders;
	protected int selection;
	protected int lastX;
	protected int lastY;
	protected ColorSelection cs;
	protected ColorSwatches swatches;
	protected int swatch_id = 0;
	protected int tbox_id = 0;
	protected Vector3f res;
	protected Toolbox tbox;
	protected EditorTool currentTool = null;
	protected String model_name = null;
	
	public EditorScreen(ScreenManager sm) {
		super(sm);
		
		currentTool = new CreateTool(this);
		// Add editor controls
		labels = new Label[7];
		labels[0] = new Label("R:", 10, 100, 14f);
		labels[1] = new Label("0", 295, 100, 14f);
		labels[2] = new Label("G:", 10, 120, 14f);
		labels[3] = new Label("0", 295, 120, 14f);
		labels[4] = new Label("B:", 10, 140, 14f);
		labels[5] = new Label("0", 295, 140, 14f);
		labels[6] = new Label("Current Color", 10, 160, 14f, Color.darkGray);
		
		sliders = new Slider[3];
		sliders[0] = new Slider(ColorIdGenerator.getId(), 28f, 107f, 0, 255);
		sliders[0].onChange(labels[1]);
		sliders[1] = new Slider(ColorIdGenerator.getId(), 28f, 127f, 0, 255);
		sliders[1].onChange(labels[3]);
		sliders[2] = new Slider(ColorIdGenerator.getId(), 28f, 147f, 0, 255);
		sliders[2].onChange(labels[5]);
		
		swatch_id = ColorIdGenerator.getId();
		swatches = new ColorSwatches(swatch_id, 260f, 240f);
		tbox_id = ColorIdGenerator.getId();
		tbox = new Toolbox(tbox_id);
		// burn off some numbers
		ColorIdGenerator.getId();
		
		cs = new ColorSelection(20, 190);
		this.updateColor(Color.white);
		
		items.addItem(new SidePickableSPVoxel(IdGenerator.getId(), 0.0f, 0.0f, 0.0f, Color.white));
		res = items.getResolution();
	}

	public EditorScreen(ScreenManager sm, String model_name) {
		this(sm);
		this.model_name = model_name;
	}

	@Override
	public void draw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		if (selection != 0) {
			if (selection < 7) {
				hud.addInfo("Selected (HUD): "+selection);
			} else {
				hud.addInfo("Selected: "+selection);
			}
			hud.addInfo("Selected Block ID: "+(int) Math.ceil((double)selection/6));
		}
		
		hud.addInfo("Resolution: "+res.x+" x "+res.y+" x "+res.z);
		hud.addInfo("Voxels: "+this.items.count());
		if (changed) {
			res = items.getResolution();
			changed = false;
			glPushMatrix();
				GLLoader.enterOrtho();
				int length = sliders.length;
				for (int i = 0; i < length; i++) {
					sliders[i].drawPick();
				}
				swatches.pick();
				tbox.pick();
				GLLoader.exitOrtho();
				GLLoader.disableLighting();
				glTranslatef(xfloat, 0.0f, zoom);
				glRotatef(xrot, 1.0f, 0.0f, 0.0f);
				glRotatef(yrot, 0.0f, 1.0f, 0.0f);
				items.pick();
				selection = Picker.run();
				GLLoader.enableLighting();
			glPopMatrix();
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			// Create selection box
			if (selection > 6) {
				currentTool.onHover(selection);
			} else {
				currentTool.noHover();
			}
		}
		glLoadIdentity();
		glPushMatrix();
			glTranslatef(xfloat, 0.0f, zoom);
			glRotatef(xrot, 1.0f, 0.0f, 0.0f);
			glRotatef(yrot, 0.0f, 1.0f, 0.0f);
			items.draw();
			currentTool.draw();
		glPopMatrix();
	}
	
	public PickableCollection getItems() {
		return this.items;
	}

	@Override
	public void input() {
		while(Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && Keyboard.getEventKeyState()) {
				sm.menu();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_Q) && Keyboard.getEventKeyState()) {
				sm.stop();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_TAB) && Keyboard.getEventKeyState()) {
				this.zoom = -20.0f;
				this.xrot = 14.0f;
				this.yrot = 0.0f;
				this.xfloat = 0.0f;
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_I)&& Keyboard.getEventKeyState()) {
				this.items.doubleResolution();
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_F1) && Keyboard.getEventKeyState()) {
				sm.fullscreen_toggle();
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_F5) && Keyboard.getEventKeyState()) {
				System.out.println("Saving");
				this.items.save(model_name+".dobj");
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_F6) && Keyboard.getEventKeyState()) {
				this.model_name = this.items.load(model_name+".dobj");
				//this.items.load(model_name+".sav");
			}
			tbox.input(this);
		}
		
		while(Mouse.next()) {
			int length = sliders.length;
			if (selection != 0 && selection < 7) {
				if (selection == swatch_id && Mouse.isButtonDown(0)) {
					Color clr = swatches.selection();
					this.updateColor(clr);
				}
				if (selection == tbox_id && Mouse.isButtonDown(0)) {
					GLLoader.enterOrtho();
					tbox.select(this);
					GLLoader.exitOrtho();
				}
				for (int i = 0; i < length; i++) {
					if (sliders[i].checkSelection(selection)) {
						this.updateColor();
					}
				}
			} else {
				for (int i = 0; i < length; i++) {
					if (sliders[i].checkSelection(selection)) {
						this.updateColor();
					}
				}
			}
			changed = currentTool.input();
		}
		
		float rotation_amount = 0.5f;
		float zoom_amount = 0.05f;
		float float_amount = 0.05f;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			rotation_amount = 0.75f;
			float_amount = 0.75f;
			zoom_amount = 0.1f;
		}
		/*
		 * Control X Rotation
		 */
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			xrot += rotation_amount;
			changed = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			xrot -= rotation_amount;
			changed = true;
		}
		/*
		 * Control Y Rotation
		 */
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			yrot += rotation_amount;
			changed = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			yrot -= rotation_amount;
			changed = true;
		}
		
		/*
		 * Control zoom (W/S)
		 */
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			zoom += zoom_amount;
			changed = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			zoom -= zoom_amount;
			changed = true;
		}
		/*
		 * Move model left and right (A/D) 
		 */
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			xfloat += float_amount;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			xfloat -= float_amount;
		}
		
		// Check if mouse moved, and if it did, update picking selection
		if (Mouse.getX() != lastX || Mouse.getY() != lastY) {
			changed = true;
			lastX = Mouse.getX();
			lastY = Mouse.getY();
		}
	}
	
	public void addItem(Vector3f coords)
	{
		this.items.addItem(new SidePickableSPVoxel(IdGenerator.getId(), coords.x, coords.y, coords.z, cs.get()));
	}
	
	public Color getColorSelection() {
		return cs.get();
	}

	public void updateColor(Color clr) {
		sliders[0].setValue(clr.r*255);
		sliders[1].setValue(clr.g*255);
		sliders[2].setValue(clr.b*255);
		cs.setR(clr.r);
		cs.setG(clr.g);
		cs.setB(clr.b);
	}

	protected void updateColor() {
		// Need to update the color selection
		float r = sliders[0].value();
		float g = sliders[1].value();
		float b = sliders[2].value();
		cs.setR(r/255);
		cs.setG(g/255);
		cs.setB(b/255);
	}

	@Override
	public void hud() {
		if (model_name != null) {
			hud.addInfo("Model Name: "+model_name);
		}
		hud.draw();
		// Draw current editing mode
		fontRenderer.write(new Label("Current Tool: "+currentTool.getName(), (int)Display.getWidth()/2-45, Display.getHeight()-22, 14.0f, Color.green));
		int length = labels.length;
		for (int i = 0; i < length; i++) {
			labels[i].draw();
		}
		length = sliders.length;
		GLLoader.enterOrtho();
		for (int i = 0; i < length; i++) {
			sliders[i].draw();
		}
		cs.draw();
		swatches.draw();
		tbox.draw();
		GLLoader.exitOrtho();
		fontRenderer.tick();
	}

	public void removeSelection() {
		if (selection > 6) {
			this.items.removeItemById((int)Math.ceil((double)selection/6));
		}
	}

	public void setCurrentTool(Object newInstance) {
		currentTool = (EditorTool) newInstance;
		
	}
}
