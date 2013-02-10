package com.dysnomia.EditorTools;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import com.dysnomia.collections.EditorTool;
import com.dysnomia.objects.SelectionHighlightBox;
import com.dysnomia.screens.EditorScreen;

public class PaintTool extends BaseTool implements EditorTool {

	protected SelectionHighlightBox shb = null;
	protected int selection = 0;
	
	public PaintTool(EditorScreen es) {
		super(es);
	}

	@Override
	public void onClick(int selection) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onHover(int selection) {
		Vector3f coords = es.getItems().get_selection_coords(selection);
		this.selection = selection;
		if (coords != null)
			shb = new SelectionHighlightBox(0, coords.x, coords.y, coords.z);

	}

	@Override
	public void noHover() {
		shb = null;
		selection = 0;
	}

	@Override
	public void draw() {
		if (shb != null)
			shb.draw();
	}

	@Override
	public boolean input() {
		boolean changed = false;
		if (shb != null && selection != 0) {
			if (Mouse.isButtonDown(0) && Mouse.getEventButtonState()) {
				es.getItems().setColorById(selection, es.getColorSelection());
			}
		}
		return changed;
	}

	public String getName(){
		return "Paint";
	}
}
