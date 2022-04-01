package model;

import java.awt.Color;

/*
 
 0 | 1 2 3  X  \
 --+-|-|-|-->   |
   |            |
 1 -          	 > Indexing by X and Y
   |			|
 2 -            |
 Y V           /
*/

public class Grid {
	private Box[][] grid;
	
	public Grid(){
		grid = new Box[Config.WIDTH][Config.HEIGHT];
		for (int x = 0; x < Config.WIDTH; ++x) {
			for (int y = 0; y < Config.HEIGHT; ++y) {
				grid[x][y] = new Box(x, y);
			}
		}
	}
	
	public Box[][] getGrid(){
		return grid;
	}
	
	public void setBlockColor(int x, int y, Color color) {
		grid[x][y].setColor(color);
	}
	
	public Color getBlockColor(int x, int y) {
		return grid[x][y].getColor();
	}
}
