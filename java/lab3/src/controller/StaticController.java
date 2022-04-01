package controller;

import java.awt.Color;

import model.Config;
import model.Coordinates;
import model.Grid;

public class StaticController {
	Grid grid;
	boolean controlled[][];
	public StaticController(Grid _grid) {
		grid = _grid;
		controlled = new boolean[Config.WIDTH][Config.HEIGHT];
		for (int x = 0; x < Config.WIDTH; ++x) {
			for (int y = 0; y < Config.HEIGHT; ++y) {
				controlled[x][y] = false;
			}
		}
	}
	
	public boolean blocksTouchTopEdge() {
		for (int x = 0; x < Config.WIDTH; ++x) {
			if (controlled[x][0]) {
				return true;
			}
		}
		return false;
	}
	
	public int deleteLines() {
		int linesDeleted = 0;
		for (int y = 0; y < Config.HEIGHT; ++y) {
			int blocksInLine = 0;
			for (int x = 0; x < Config.WIDTH; ++x) {
				if (controlled[x][y]) {
					++blocksInLine;
				}
			}
			if (blocksInLine == Config.WIDTH) {
				++linesDeleted;
				for (int x = 0; x < Config.WIDTH; ++x) {
					controlled[x][y] = false;
					grid.setBlockColor(x, y, Config.DEFAULT_COLOR);
				}
				moveUpperBlocksDown(y);
				--y; // to stay on the same line
			}
		}
		return linesDeleted;
	}
	
	private void moveUpperBlocksDown(int deletedLineNumber) {
		for (int y = deletedLineNumber; y >= 0; --y) {
			for (int x = 0; x < Config.WIDTH; ++x) {
				Color upBlockColor = Config.DEFAULT_COLOR;
				boolean upBlockControl = false;
				if (y != 0) {
					upBlockColor = grid.getBlockColor(x, y - 1);
					upBlockControl = controlled[x][y - 1];
				}
				grid.setBlockColor(x, y, upBlockColor);
				controlled[x][y] = upBlockControl;
			}
		}
	}
	
	public void getFigure(FigureController fc) {
		Coordinates[] figureCoords = fc.getCoords();
		int figureSize = fc.getFigureSize();
		
		for (int blockNum = 0; blockNum < figureSize; ++blockNum) {
			int blockX = figureCoords[blockNum].getX();
			int blockY = figureCoords[blockNum].getY();
			if (0 <= blockX && blockX < Config.WIDTH && 0 <= blockY && blockY < Config.HEIGHT) {
				controlled[blockX][blockY] = true;
			}
		}
		
		fc.disableControl();
	}
}
