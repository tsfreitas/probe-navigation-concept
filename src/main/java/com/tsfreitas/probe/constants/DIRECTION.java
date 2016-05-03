package com.tsfreitas.probe.constants;

/**
 * Created by tsfreitas on 02/05/16.
 */
public enum DIRECTION {

	NORTH(0, 1), EAST(1, 0), SOUTH(0, -1), WEST(-1, 0);

	private int x;

	private int y;

	DIRECTION(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public DIRECTION next() {
		int position = this.ordinal() + 1;

		int nextPos = DIRECTION.values().length == position ? 0 : position;

		return DIRECTION.values()[nextPos];
	}

	public DIRECTION previous() {
		int position = this.ordinal() - 1;
		
		int previousPos = position < 0 ? DIRECTION.values().length -1 : position;
		
		return DIRECTION.values()[previousPos];
	}

}
