package controller;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import model.Box;
import model.Config;
import model.Grid;

public class Controller extends KeyAdapter{
	private FigureController fc;
	private StaticController sc;
	private Grid grid;
	private int score;
	private int scoreLinesDeleted;
	public Controller(FigureController _fc, StaticController _sc, Grid _grid) {
		fc = _fc;
		sc = _sc;
		grid = _grid;
		score = 0;
		scoreLinesDeleted = 0;
	}
	public Box[][] getGrid() {
		return grid.getGrid();
	}
	
	public int getScore() {
		return score;
	}
	
	public int getScoreLinesDeleted() {
		return scoreLinesDeleted;
	}
	
	public void deleteLines() {
		int linesDeleted = sc.deleteLines();
		score += linesDeleted * linesDeleted * Config.WIDTH;
		scoreLinesDeleted += linesDeleted;
	}
	
	public boolean controlsFigure() {
		return fc.controlsFigure();
	}
	
	public void createFigure() {
		fc.createFigure();
	}
	
	public void moveFigureDown() {
		if (fc.controlsFigure()) {
			boolean movedSuccessfully = fc.moveFigureDown();
			if (!movedSuccessfully) {
				sc.getFigure(fc);
			}
		}
	}
	
	public boolean blocksTouchTopEdge() {
		return sc.blocksTouchTopEdge();
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (fc.controlsFigure()) {
				fc.turnFigure();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (fc.controlsFigure()) {
				fc.moveFigureLeft();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (fc.controlsFigure()) {
				fc.moveFigureRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (fc.controlsFigure()) {
				boolean movedSuccessfully = fc.moveFigureDown();
				if (!movedSuccessfully) {
					sc.getFigure(fc);
				}
				else {
					score += 1;
				}
			}
		}
	}
}
