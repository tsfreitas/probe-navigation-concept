package com.tsfreitas.probe.constants;

import com.tsfreitas.probe.model.Coordinate;

/**
 * Created by tsfreitas on 02/05/16.
 */
public enum DIRECTION {

	NORTH(0, 1), EAST(1, 0), SOUTH(0, -1), WEST(-1, 0);

	private Coordinate coordinate;

	DIRECTION(int x, int y) {
		this.coordinate =new Coordinate(x,y);
	}
	
	public Coordinate getCoordinate() {
		return coordinate;
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
