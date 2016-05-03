package com.tsfreitas.probe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Controle da missão, controla as sondas e tem conhecimento do terreno a ser
 * explorado
 * 
 * Created by tsfreitas on 02/05/16.
 */
public class CruiseControl {

	private final Coordinate maxCoordinate;

	private List<Probe> deployedProbes = new ArrayList<>();

	public CruiseControl(Coordinate maxCoordinate) {
		this.maxCoordinate = maxCoordinate;
	}

	public void landProbe(Probe probe) {
		deployedProbes.add(probe);
	}

}
