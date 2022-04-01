package model;

public class Coordinates{
	private int x;
	private int y;
	public Coordinates() {
		x = 0;
		y = 0;
	}
	public Coordinates(int _x, int _y){
		x = _x;
		y = _y;
	}
	public void setX(int newX) {x = newX;}
	public void setY(int newY) {y = newY;}
	public int getX() {return x;}
	public int getY() {return y;}
}