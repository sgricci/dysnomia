package com.dysnomia.collections;

import com.dysnomia.objects.Box;
import static org.lwjgl.opengl.GL11.*;


public class BoxGrid {
	public Box[][][] grid;
	public int height = 12;
	public int width  = 12;
	public int depth  = 12;
	
	public BoxGrid() {
		grid = new Box[height][width][depth];
	}
	
	
	public void fill() {
		int i = 0;
		for (int h=0; h < height; h++) {
			for (int w=0; w < width; w++) {
				for (int z=0; z < depth; z++) {
					grid[h][w][z] = new Box(i);
					i++;	
				}
				
			}
		}
	}
	
	public void fillCenter() {
		int half_height = height / 2;
		int half_depth  = depth / 2;
		grid[half_height][0][half_depth].setFilled(true);
	}
	
	public void draw(boolean outline) {
		for (int h=0; h < height; h++) {
			for (int w=0; w < width; w++) {
				for (int z=0; z < depth; z++) {
					glPushMatrix();
					glTranslatef(h*1.0f, w*1.0f, z*1.0f);
					if (grid[h][w][z].getFilled()) {
						grid[h][w][z].draw();
						this.outlineSurroundingGrid(h,w,z);
					}
					glPopMatrix();
				}
			}
		} 
	}

	public void outlineSurroundingGrid(int height, int width, int depth) {
		glTranslatef(1.0f, 0.0f, 0.0f);
		height++;
		dwln(height, width, depth);
		
		glTranslatef(-2.0f, 0.0f, 0.0f);
		height--; height--;
		dwln(height, width, depth);
		
		glTranslatef(1.0f, 0.0f, 0.0f); // Reset
		
		glTranslatef(0.0f, 0.0f, 1.0f);
		depth++;
		dwln(height, width, depth);
		
		glTranslatef(0.0f, 0.0f, -2.0f);
		depth--; depth--;
		dwln(height, width, depth);
		
	}

	private void dwln(int height, int width, int depth) {
		System.out.println("Drawing Surrounding Outline: H:"+height+",W:"+width+",D:"+depth);
		grid[height][width][depth].drawOutline();
	}
	
	public void drawPicking() {
		for (int h=0; h < height; h++) {
			for (int w=0; w < width; w++) {
				for (int z=0; z < depth; z++) {
					glPushMatrix();
					glTranslatef(h*1.0f, w*1.0f, z*1.0f);
					grid[h][w][z].drawPicking();
					glPopMatrix();
				}
			}
		}
	}
	
	private void clearHover() {
		for (int h=0; h < height; h++) {
			for (int w=0; w < width; w++) {
				for (int z=0; z < depth; z++) {
					if (grid[h][w][z].hovered()) {
						grid[h][w][z].setHover(false);
					}
				}
			}
		}
	}
	
	public void setFilled(int selectedId) {
		for (int h=0; h < height; h++) {
			for (int w=0; w < width; w++) {
				for (int z=0; z < depth; z++) {
					if (grid[h][w][z].getId() == selectedId) {
						grid[h][w][z].setFilled(true);
					}
				}
			}
		}
	}
	
	public void setHover(int selectedId) {
		this.clearHover();
		for (int h=0; h < height; h++) {
			for (int w=0; w < width; w++) {
				for (int z=0; z < depth; z++) {
					if (grid[h][w][z].getId() == selectedId) {
						grid[h][w][z].hover();
					}
				}
			}
		}
	}
}