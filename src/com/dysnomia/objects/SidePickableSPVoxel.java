package com.dysnomia.objects;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import com.dysnomia.utils.ColorGUID;
import com.dysnomia.utils.ColorIdGenerator;

/**
 * Side Pickable (each side is selectable / definable with color) Self Positioning Voxel
 * @author steve
 *
 */
public class SidePickableSPVoxel extends SelfPositioningVoxel implements Drawable,SavePickable {

	protected Map<String, Color> colors = new HashMap<String, Color>();
	
	public SidePickableSPVoxel(int id, float x, float y, float z, Color color) {
		super(id,x,y,z,color);
		// Do colors here
		this.initPickingColors();
	}
	
	protected void initPickingColors() {
		// Top
		this.setColor("top");
		// Front
		this.setColor("front");
		// Left
		this.setColor("left");
		// Right
		this.setColor("right");
		// Back
		this.setColor("back");
		// Bottom
		this.setColor("bottom");
	}
		
	protected void useColor(String side) {
		Color color_to_use = colors.get(side);
		glColor4f(color_to_use.r, color_to_use.g, color_to_use.b, 1.0f);
	}
	
	public void setBlockColor(Color clr) {
		this.color = clr;
	}
	
	protected void setColor(String side) {
		int colorId = ColorIdGenerator.getId();
		int[] colors = ColorGUID.decode(colorId);
		Color newColor = new Color(colors[0], colors[1], colors[2]);
		this.colors.put(side, newColor);
	}

	@Override
	public void drawPickable() {
		glPushMatrix();
			glTranslatef(x, y, z);
			glBegin(GL_QUADS);
				this.useColor("front");
				this.drawFront();
				this.useColor("back");
				this.drawBack();
				this.useColor("top");
				this.drawTop();
				this.useColor("bottom");
				this.drawBottom();
				this.useColor("right");
				this.drawRight();
				this.useColor("left");
				this.drawLeft();
			glEnd();
		glPopMatrix();
	}
	
	protected int getColorId(String side) {
		Color clr = colors.get(side);
		int clr_id = ColorGUID.encode(clr.r, clr.g, clr.b);
		return clr_id;
	}

	@Override
	public Vector3f get_selection_coords(int selection) {
		Vector3f vct = new Vector3f(x,y,z);
		int clr_id;
		
		clr_id = this.getColorId("top");
		if (clr_id == selection) {
			vct.y += 1.0f;
			return vct;
		}
		clr_id = this.getColorId("bottom");
		if (clr_id == selection) {
			vct.y -= 1.0f;
			return vct;
		}
		
		clr_id = this.getColorId("left");
		if (clr_id == selection) {
			vct.x -= 1.0f;
			return vct;
		}
		clr_id = this.getColorId("right");
		if (clr_id == selection) {
			vct.x += 1.0f;
			return vct;
		}
		
		clr_id = this.getColorId("front");
		if (clr_id == selection) {
			vct.z += 1.0f;
			return vct;
		}
		clr_id = this.getColorId("back");
		if (clr_id == selection) {
			vct.z -= 1.0f;
			return vct;
		}
		return vct;
	}

	@Override
	public void save(ObjectOutputStream os) throws IOException {
		os.writeObject(this.x);
		os.writeObject(this.y);
		os.writeObject(this.z);
		os.writeObject(this.color);
	}
}