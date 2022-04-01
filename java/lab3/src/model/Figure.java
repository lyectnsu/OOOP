package model;

import java.awt.Color;

public class Figure {
	
	private Coordinates figureCenter;
	private Color figureColor;
	private FigureType type;
	private int figureSize;
	
	public Figure(FigureType figureType) {
		type = figureType;
		if (type == FigureType.J) {
			figureCenter = new Coordinates(7, -2);
			figureColor = Config.J_COLOR;
			figureSize = 4;
		}
		if (type == FigureType.I) {
			figureCenter = new Coordinates(7, -2);
			figureColor = Config.I_COLOR;
			figureSize = 4;
		}
		if (type == FigureType.O) {
			figureCenter = null;
			figureColor = Config.O_COLOR;
			figureSize = 4;
		}
		if (type == FigureType.L) {
			figureCenter = new Coordinates(7, -2);
			figureColor = Config.L_COLOR;
			figureSize = 4;
		}
		if (type == FigureType.Z) {
			figureCenter = new Coordinates(7, -2);
			figureColor = Config.Z_COLOR;
			figureSize = 4;
		}
		if (type == FigureType.T) {
			figureCenter = new Coordinates(7, -2);
			figureColor = Config.T_COLOR;
			figureSize = 4;
		}
		if (type == FigureType.S) {
			figureCenter = new Coordinates(7, -2);
			figureColor = Config.S_COLOR;
			figureSize = 4;
		}
	}
	
	public void loadFigureCoordinatesToArray(Coordinates[] coords) {
		if (type == FigureType.J) {
			coords[0].setX(7); coords[0].setY(-3);
			coords[1].setX(7); coords[1].setY(-2);
			coords[2].setX(7); coords[2].setY(-1);
			coords[3].setX(6); coords[3].setY(-1);
		}
		if (type == FigureType.I) {
			coords[0].setX(7); coords[0].setY(-4);
			coords[1].setX(7); coords[1].setY(-3);
			coords[2].setX(7); coords[2].setY(-2);
			coords[3].setX(7); coords[3].setY(-1);
		}
		if (type == FigureType.O) {
			coords[0].setX(7); coords[0].setY(-2);
			coords[1].setX(7); coords[1].setY(-1);
			coords[2].setX(6); coords[2].setY(-2);
			coords[3].setX(6); coords[3].setY(-1);
		}
		if (type == FigureType.L) {
			coords[0].setX(7); coords[0].setY(-3);
			coords[1].setX(7); coords[1].setY(-2);
			coords[2].setX(7); coords[2].setY(-1);
			coords[3].setX(8); coords[3].setY(-1);
		}
		if (type == FigureType.Z) {
			coords[0].setX(6); coords[0].setY(-2);
			coords[1].setX(7); coords[1].setY(-2);
			coords[2].setX(7); coords[2].setY(-1);
			coords[3].setX(8); coords[3].setY(-1);
		}
		if (type == FigureType.T) {
			coords[0].setX(6); coords[0].setY(-1);
			coords[1].setX(7); coords[1].setY(-2);
			coords[2].setX(7); coords[2].setY(-1);
			coords[3].setX(8); coords[3].setY(-1);
		}
		if (type == FigureType.S) {
			coords[0].setX(6); coords[0].setY(-1);
			coords[1].setX(7); coords[1].setY(-1);
			coords[2].setX(7); coords[2].setY(-2);
			coords[3].setX(8); coords[3].setY(-2);
		}
	}
	
	public Color getFigureColor() {
		return figureColor;
	}
	
	public int getFigureSize() {
		return figureSize;
	}
	
	public Coordinates getFigureCenter() {
		return figureCenter;
	}
}
