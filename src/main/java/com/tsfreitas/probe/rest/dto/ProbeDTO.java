package com.tsfreitas.probe.rest.dto;

import javax.validation.constraints.NotNull;

import com.tsfreitas.probe.constants.DIRECTION;
import com.tsfreitas.probe.model.Probe;

public class ProbeDTO extends CoordinateDTO {

	@NotNull
	private DIRECTION direction;

	public DIRECTION getDirection() {
		return direction;
	}

	public Probe createProbe(String probeName) {
		return new Probe(probeName, createCoordinate(), direction);
	}

}
