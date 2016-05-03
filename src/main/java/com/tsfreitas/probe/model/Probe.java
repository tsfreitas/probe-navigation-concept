package com.tsfreitas.probe.model;

import com.tsfreitas.probe.constants.DIRECTION;

/**
 * A sonda sabe se movimentar, não possui conhecimento de sondas vizinhas ou da
 * extensão do terreno Created by tsfreitas on 02/05/16.
 */
public class Probe {

	private Coordinate coordinate;

	private DIRECTION direction;

	public Probe(Coordinate coordinate, DIRECTION direction) {
		this.coordinate = coordinate;
		this.direction = direction;
	}
	
	public Coordinate getCoordinate() {
		return coordinate;
	}

	public DIRECTION getDirection() {
		return direction;
	}

	public void left() {
		direction = direction.previous();
	}

	public void right() {
		direction = direction.next();
	}

	public void move() {
		this.coordinate = coordinate.sum(direction.getCoordinate());
	}
}
