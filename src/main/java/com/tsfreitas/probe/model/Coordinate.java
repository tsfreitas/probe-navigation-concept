package com.tsfreitas.probe.model;

public final class Coordinate {

	private int x;

	private int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Coordinate sum(Coordinate coordinate) {
		return new Coordinate(coordinate.getX() + x, coordinate.getY() + y);
	}

	public boolean overrideCoordina(Coordinate coordinate) {
		return coordinate.x == x && coordinate.y == y;
	}

	public boolean lessThan(Coordinate coordinate) {
		return x < coordinate.x || y < coordinate.y;
	}

	public boolean greaterThan(Coordinate coordinate) {
		return x > coordinate.x || y > coordinate.y;
	}

}
