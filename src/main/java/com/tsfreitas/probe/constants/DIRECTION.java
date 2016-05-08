package com.tsfreitas.probe.constants;

import com.tsfreitas.probe.model.Coordinate;

/**
 * Created by tsfreitas on 02/05/16.
 */
public enum DIRECTION {

	NORTH("N", 0, 1), EAST("E", 1, 0), SOUTH("S", 0, -1), WEST("W", -1, 0);

	private Coordinate coordinate;

	private String abbreviation;

	DIRECTION(String abbreviation, int x, int y) {
		this.abbreviation = abbreviation;
		this.coordinate = new Coordinate(x, y);
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

		int previousPos = position < 0 ? DIRECTION.values().length - 1 : position;

		return DIRECTION.values()[previousPos];
	}

	public static DIRECTION getDirectionFromAbbreviation(String abbreviation) {
		for (DIRECTION direction : DIRECTION.values()) {
			if (direction.abbreviation.equalsIgnoreCase(abbreviation)) {
				return direction;
			}
		}

		return null;
	}

}
