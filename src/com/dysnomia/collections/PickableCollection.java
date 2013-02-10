package com.dysnomia.collections;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;

import com.dysnomia.objects.Loadable;
import com.dysnomia.objects.Savable;
import com.dysnomia.objects.SavePickable;
import com.dysnomia.objects.SidePickableSPVoxel;
import com.dysnomia.utils.ColorIdGenerator;
import com.dysnomia.utils.IdGenerator;

public class PickableCollection implements Savable,Loadable {
	protected SavePickable items[];
	
	public void pick() {
		int length = this.items.length;
		for (int i = 0; i < length; i++) {
			this.items[i].drawPickable();
		}
	}
	
	public void draw() {
		int length = this.items.length;
		for (int i = 0; i < length; i++) {
			this.items[i].draw();
		}
	}
	
	public Vector3f getResolution() {
		float max_x = 0, max_z = 0, max_y = 0;
		float min_x = 0, min_z = 0, min_y = 0;
		int length = this.items.length;
		for (int i = 0; i < length; i++) {
			if (this.items[i].getX() < min_x) {
				min_x = this.items[i].getX();
			}
			if (this.items[i].getX() > max_x) {
				max_x = this.items[i].getX();
			}
			if (this.items[i].getY() < min_y) {
				min_y = this.items[i].getY();
			}
			if (this.items[i].getY() > max_y) {
				max_y = this.items[i].getY();
			}
			if (this.items[i].getZ() < min_z) {
				min_z = this.items[i].getZ();
			}
			if (this.items[i].getZ() > max_z) {
				max_z = this.items[i].getZ();
			}
		}
		float len_x = 0, len_y = 0, len_z = 0;
		len_x = max_x - min_x;
		len_y = max_y - min_y;
		len_z = max_z - min_z;
		return new Vector3f(len_x+1,len_y+1,len_z+1);
	}
	
	public void doubleResolution() {
		IdGenerator.reset();
		IdGenerator.getId();
		IdGenerator.getId();
		IdGenerator.getId();
		IdGenerator.getId();
		IdGenerator.getId();
		IdGenerator.getId();
		int length  = this.items.length;
		int new_length = length*8;
		
		SavePickable[] new_items = new SavePickable[new_length];
		int x = 0;
		for (int i = 0; i < length; i++) {
			Color clr = this.items[i].getColor();
			int iterator = i*8;
			float start_x = this.items[i].getX()*2;
			float start_y = this.items[i].getY()*2;
			float start_z = this.items[i].getZ()*2;
			new_items[iterator] = new SidePickableSPVoxel(IdGenerator.getId(), start_x  , start_y  , start_z  , clr);
			iterator++;
			new_items[iterator] = new SidePickableSPVoxel(IdGenerator.getId(), start_x+1, start_y  , start_z  , clr);
			iterator++;
			new_items[iterator] = new SidePickableSPVoxel(IdGenerator.getId(), start_x  , start_y+1, start_z  , clr);
			iterator++;
			new_items[iterator] = new SidePickableSPVoxel(IdGenerator.getId(), start_x  , start_y  , start_z+1, clr);
			iterator++;
			new_items[iterator] = new SidePickableSPVoxel(IdGenerator.getId(), start_x+1, start_y+1, start_z  , clr);
			iterator++;
			new_items[iterator] = new SidePickableSPVoxel(IdGenerator.getId(), start_x+1, start_y  , start_z+1, clr);
			iterator++;
			new_items[iterator] = new SidePickableSPVoxel(IdGenerator.getId(), start_x  , start_y+1, start_z+1, clr);
			iterator++;
			new_items[iterator] = new SidePickableSPVoxel(IdGenerator.getId(), start_x+1, start_y+1, start_z+1, clr);
		}
		items = new_items;
	}
	
	public void removeItemById(int id) {
		int length = this.items.length;
		
		if (length < 2) {
			// There is only one (or none) pickables, so just return to begin state
			items = null;
			return;
		}
		SavePickable[] new_items = new SavePickable[length-1];
		int x = 0;
		for (int i = 0; i < length; i++) {
			if (id != items[i].getId()) {
				new_items[x] = items[i];
				x++;
			}
		}
		items = new_items;
	}

	public Vector3f get_selection_coords(int selection) {
		// Figure out what block has been selected
		int selectedBlock = (int) Math.ceil((double)selection/6);
		int length = this.items.length;
		Vector3f selection_coordinates = null;
		for (int i = 0; i < length; i++) {
			if (this.items[i].getId() == selectedBlock) {
				selection_coordinates = this.items[i].get_selection_coords(selection);
				break;
			}
		}
		return selection_coordinates;
	}
	
	
	public void addItem(SavePickable item) {
		int length;
		if (this.items == null) {
			length = 1;
		} else {
			length = this.items.length+1;
		}
		SavePickable[] new_items = new SavePickable[length];
		if (this.items != null) {
			for (int i = 0; i < length-1; i++) {
				new_items[i] = this.items[i];
			}
		}
		new_items[length-1] = item;
		this.items = new_items;
	}
	
	public void removeItem(int index) {
		int length = items.length;
		if (length < 2) {
			// There is only one (or no) Pickables, so just return to begin state)
			items = null;
		}
		SavePickable[] new_items = new SavePickable[length-2];
		for (int i = 0; i < length; i++) {
			if (i != index) {
				new_items[new_items.length] = items[i]; 
			}
		}
		items = new_items;
	}
	
	public void clear() {
		items = null;
		ColorIdGenerator.reset();
		ColorIdGenerator.getId();
		ColorIdGenerator.getId();
		ColorIdGenerator.getId();
		ColorIdGenerator.getId();
		ColorIdGenerator.getId();
		ColorIdGenerator.getId();
		IdGenerator.reset();
	}
	
	public int count() {
		return this.items.length;
	}

	@Override
	public boolean save(String filename) {
		try {
			FileOutputStream saveFile = new FileOutputStream(filename);
			ObjectOutputStream os = new ObjectOutputStream(saveFile);
			int length = items.length;
			os.writeObject(length);
			String model_name = filename.substring(0, filename.length()-5);
			os.writeObject(model_name);
			for (int i = 0; i < length; i++) {
				items[i].save(os);
			}
			os.close();
		} catch(Exception exc) {
			exc.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public String load(String filename) {
		String model_name = null;
		try {
			this.clear();
			FileInputStream saveFile = new FileInputStream(filename);
			
			ObjectInputStream save = new ObjectInputStream(saveFile);
			int length = (Integer) save.readObject();
			model_name = (String) save.readObject();
			float x = 0, y = 0, z = 0;
			Color clr;
			for (int i = 0; i < length; i++) {
				x = (Float) save.readObject();
				y = (Float) save.readObject();
				z = (Float) save.readObject();
				clr = (Color) save.readObject();
				this.addItem(new SidePickableSPVoxel(IdGenerator.getId(), x, y, z, clr));
			} 
			save.close();
		} catch(Exception exc) {
			exc.printStackTrace();
			return null;
		}
		return model_name;
	}

	public Color getColorById(int selection) {
		int selectedBlock = (int) Math.ceil((double)selection/6);
		int length = this.items.length;
		Color clr = null;
		for (int i = 0; i < length; i++) {
			if (this.items[i].getId() == selectedBlock) {
				clr = this.items[i].getColor();
				break;
			}
		}
		return clr;
	}

	public void setColorById(int selection, Color colorSelection) {
		int selectedBlock = (int) Math.ceil((double)selection/6);
		int length = this.items.length;
		for (int i = 0; i < length; i++) {
			if (this.items[i].getId() == selectedBlock) {
				this.items[i].setBlockColor(colorSelection);
				break;
			}
		}
	}
	
	
}
