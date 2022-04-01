package controller;

import java.awt.Color;

import model.Config;
import model.Coordinates;
import model.Figure;
import model.FigureType;
import model.Grid;

public class FigureController {
	
	private Grid grid;
	private Coordinates[] controlled;
	private boolean controlStatus;
	
	private Coordinates figureCenter;
	private Color figureColor;
	private int figureSize;
	
	public FigureController(Grid _grid) {
		grid = _grid;
		controlled = null;
		controlStatus = false;
		figureCenter = null;
		figureColor = null;
		figureSize = 0;
	}
	
	public Coordinates[] getCoords(){
		if (controlled == null) {
			return null;
		}
		return controlled;
	}
	
	public int getFigureSize() {
		return figureSize;
	}
	
	public boolean controlsFigure(){
		return controlStatus;
	}
	
	public void disableControl() {
		controlStatus = false;
	}
	
	public void createFigure() {
		FigureType figureType = FigureType.randomFigureType();
		Figure figure = new Figure(figureType);
		figureSize = figure.getFigureSize();
		controlled = new Coordinates[figureSize];
		for (int i = 0; i < figureSize; ++i) {
			controlled[i] = new Coordinates();
		}
		figure.loadFigureCoordinatesToArray(controlled);
		figureCenter = figure.getFigureCenter();
		figureColor = figure.getFigureColor();
		controlStatus = true;
	}
	
	private boolean isBlockControlled(int x, int y) {
		for (int blockNum = 0; blockNum < figureSize; ++blockNum) {
			if (controlled[blockNum].getX() == x && controlled[blockNum].getY() == y) {
				return true;
			}
		}
		return false;
	}
	
	public boolean moveFigureDown() {
		if (!canMoveFigureDown()) {
			return false;
		}
		for (int blockNum = 0; blockNum < figureSize; ++blockNum) {
			int oldBlockX = controlled[blockNum].getX();
			int oldBlockY = controlled[blockNum].getY();
			
			controlled[blockNum].setY(oldBlockY + 1);
			
			if (oldBlockY >= 0) {
				if (!isBlockControlled(oldBlockX, oldBlockY)) {
					grid.setBlockColor(oldBlockX, oldBlockY, Config.DEFAULT_COLOR);
				}
			}
			if (oldBlockY + 1 >= 0) {	
				grid.setBlockColor(oldBlockX, oldBlockY + 1, figureColor);
			}
		}
		if (figureCenter != null) {
			figureCenter.setY(figureCenter.getY() + 1);
		}
		return true;
	}
	
	private boolean canMoveFigureDown() {
		int count = 0;
		for (int blockNum = 0; blockNum < figureSize; ++blockNum) {
			int blockX = controlled[blockNum].getX();
			int blockY = controlled[blockNum].getY();
			
			if (blockY + 1 >= Config.HEIGHT) {
				return false;
			}
			
			if (!isBlockControlled(blockX, blockY + 1)) {
				if (blockY + 1 >= 0) {
					if (grid.getBlockColor(blockX, blockY + 1) == Config.DEFAULT_COLOR) {
						count++;
					}
				}
				else {
					count++;
				}
			}
			else {
				count++;
			}
		}
		if (count == figureSize) {
			return true;
		}
		return false;
	}
	
	public void moveFigureLeft() {
		if (!canMoveFigureLeft()) {
			return;
		}
		for (int blockNum = 0; blockNum < figureSize; ++blockNum) {
			int oldBlockX = controlled[blockNum].getX();
			int oldBlockY = controlled[blockNum].getY();
			
			controlled[blockNum].setX(oldBlockX - 1);
			
			if (oldBlockY >= 0) {
				if (!isBlockControlled(oldBlockX, oldBlockY)) {
					grid.setBlockColor(oldBlockX, oldBlockY, Config.DEFAULT_COLOR);
				}
				if (0 <= oldBlockX - 1 && oldBlockX - 1 < Config.WIDTH && 0 <= oldBlockY && oldBlockY < Config.HEIGHT) {
					grid.setBlockColor(oldBlockX - 1, oldBlockY, figureColor);
				}
			}
		}
		if (figureCenter != null) {
			figureCenter.setX(figureCenter.getX() - 1);
		}
	}
	
	private boolean canMoveFigureLeft() {
		int count = 0;
		for (int blockNum = 0; blockNum < figureSize; ++blockNum) {
			int blockX = controlled[blockNum].getX();
			int blockY = controlled[blockNum].getY();
			
			if (blockX - 1 < 0) {
				return false;
			}
			
			if (!isBlockControlled(blockX - 1, blockY)) {
				if (blockY >= 0) {
					if (grid.getBlockColor(blockX - 1, blockY) == Config.DEFAULT_COLOR) {
						count++;
					}
				}
				else {
					count++;
				}
			}
			else {
				count++;
			}
		}
		if (count == figureSize) {
			return true;
		}
		return false;
	}
	
	public void moveFigureRight() {
		if (!canMoveFigureRight()) {
			return;
		}
		for (int blockNum = 0; blockNum < figureSize; ++blockNum) {
			int oldBlockX = controlled[blockNum].getX();
			int oldBlockY = controlled[blockNum].getY();
			
			controlled[blockNum].setX(oldBlockX + 1);
			
			if (oldBlockY >= 0) {
				if (!isBlockControlled(oldBlockX, oldBlockY)) {
					grid.setBlockColor(oldBlockX, oldBlockY, Config.DEFAULT_COLOR);
				}
				grid.setBlockColor(oldBlockX + 1, oldBlockY, figureColor);
			}
		}
		if (figureCenter != null) {
			figureCenter.setX(figureCenter.getX() + 1);
		}
	}
	
	private boolean canMoveFigureRight() {
		int count = 0;
		for (int blockNum = 0; blockNum < figureSize; ++blockNum) {
			int blockX = controlled[blockNum].getX();
			int blockY = controlled[blockNum].getY();
			
			if (blockX + 1 >= Config.WIDTH) {
				return false;
			}
			
			if (!isBlockControlled(blockX + 1, blockY)) {
				if (blockY >= 0) {
					if (grid.getBlockColor(blockX + 1, blockY) == Config.DEFAULT_COLOR) {
						count++;
					}
				}
				else {
					count++;
				}
			}
			else {
				count++;
			}
		}
		if (count == figureSize) {
			return true;
		}
		return false;
	}
	
	public void turnFigure() {
		/* figure turning is a "matrix to vector" multiplication
		 * 
		 * | 0 -1 | \/ | x |
		 * | 1  0 | /\ | y |
		 * 
		 * where matrix is a rotation matrix which rotates vector
		 * clockwise through an angle pi/2
		*/
		
		if (figureCenter == null) { // for O figure
			return;
		}
		if (!canTurnFigure()) {
			return;
		}
		for (int blockNum = 0; blockNum < figureSize; ++blockNum) {
			int oldX = controlled[blockNum].getX();
			int oldY = controlled[blockNum].getY();
			int vecX = oldX - figureCenter.getX();
			int vecY = oldY - figureCenter.getY();
			int newX = -vecY + figureCenter.getX();
			int newY =  vecX + figureCenter.getY();
			
			controlled[blockNum].setX(newX);
			controlled[blockNum].setY(newY);
			
			if (0 <= oldX && oldX <= Config.WIDTH && 0 <= oldY && oldY <= Config.HEIGHT) {
				grid.setBlockColor(oldX, oldY, Config.DEFAULT_COLOR);
			}
			if (0 <= newX && newX <= Config.WIDTH && 0 <= newY && newY <= Config.HEIGHT) {
				grid.setBlockColor(newX, newY, figureColor);
			}
		}
	}
	
	private boolean canTurnFigure() {
		if (figureCenter == null) {
			return true;
		}
		for (int blockNum = 0; blockNum < figureSize; ++blockNum) {
			int vecX = controlled[blockNum].getX() - figureCenter.getX();
			int vecY = controlled[blockNum].getY() - figureCenter.getY();
			int newX = -vecY + figureCenter.getX();
			int newY =  vecX + figureCenter.getY();
			if (newX < 0 || newX >= Config.WIDTH || newY < 0 || newY >= Config.HEIGHT) {
				return false;
			}
			if (!isBlockControlled(newX, newY)) {
				if (grid.getBlockColor(newX, newY) != Config.DEFAULT_COLOR) {
					return false;
				}
			}
		}
		return true;
	}
}
