package model;

import java.util.concurrent.ThreadLocalRandom;

public enum FigureType {
	J,
	I,
	O,
	L,
	Z,
	T,
	S;
	
	private static final FigureType[] VALUES = values();
	
	public static FigureType randomFigureType()  {
		return VALUES[ThreadLocalRandom.current().nextInt(0, 7)];
	}
}
