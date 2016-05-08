package com.tsfreitas.probe.rest.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.tsfreitas.probe.model.Coordinate;

public class CoordinateDTO {

	@NotNull
	@Min(0)
	private Integer x;

	@NotNull
	@Min(0)
	private Integer y;

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public Coordinate createCoordinate() {
		return new Coordinate(x, y);
	}

}
