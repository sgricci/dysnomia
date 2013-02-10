package com.dysnomia.EditorTools;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import com.dysnomia.collections.EditorTool;
import com.dysnomia.objects.SelectionHighlightBox;
import com.dysnomia.screens.EditorScreen;

public class CreateTool extends BaseTool implements EditorTool {
	
	protected SelectionHighlightBox shb = null;
	
	public CreateTool(EditorScreen es) {
		super(es);
	}

	@Override
	public void onHover(int selection) {
		Vector3f coords = es.getItems().get_selection_coords(selection);
		if (coords != null)
			shb = new SelectionHighlightBox(0, coords.x, coords.y, coords.z);
	}
	
	public void noHover() {
		shb = null;
	}

	@Override
	public void onClick(int selection) {
	
	}

	@Override
	public void draw() {
		if (shb != null)
			shb.draw();
			
	}

	@Override
	public boolean input() {
		boolean changed = false;
		if (shb != null) {
			if (Mouse.isButtonDown(0) && Mouse.getEventButtonState()) {
				Vector3f coords = shb.coords();
				es.addItem(coords);
				changed = true;
			}
			if(Mouse.isButtonDown(1) && Mouse.getEventButtonState()) {
				es.removeSelection();
				changed = true;
			}
		}
		
		return changed;
	}
	
	public String getName(){
		return "Create";
	}

}
