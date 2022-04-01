package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Box extends JPanel{
	private Color backColor;
	private int x;
	private int y;
	public Box(int _x, int _y){
		x = _x;
		y = _y;
		setBounds(x * Config.SIZE, y * Config.SIZE,
				      Config.SIZE,     Config.SIZE);
		setColor(Config.DEFAULT_COLOR);
	}
	public void setColor(Color color) {
		backColor = color;
		setBackground(backColor);
		if (backColor == Config.DEFAULT_COLOR) {
			int top = 1;
			int left = 1;
			int bottom = 1;
			int right = 1;
			if (y == 0) top = 0;
			if (x == 0) left = 0;
			if (y == Config.HEIGHT - 1) bottom = 0;
			if (x == Config.WIDTH - 1) right = 0;
			setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Config.GRID_COLOR));
		}
		else {
			setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Config.DEFAULT_COLOR));
		}
	}
	public Color getColor() {
		return backColor;
	}
}
