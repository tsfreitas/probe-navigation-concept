package com.tsfreitas.probe.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.tsfreitas.probe.exception.CrashException;

/**
 * Controle da missão, controla as sondas e tem conhecimento do terreno a ser
 * explorado
 * 
 * Created by tsfreitas on 02/05/16.
 */
public class CruiseControl {

	private final Coordinate maxCoordinate;

	private Map<String, Probe> deployedProbes = new HashMap<>();

	public CruiseControl(Coordinate maxCoordinate) {
		this.maxCoordinate = maxCoordinate;
	}

	public void landProbe(String probeName, Probe probe) throws CrashException {
		deployedProbes.put(probeName, probe);
		validateCrash(probe);
	}

	public Probe getProbe(String probeName) {
		return deployedProbes.get(probeName);
	}

	public void receiveCommands(String probeName, List<String> commands) throws CrashException {
		// Recupera sonda
		Probe probe = Optional.ofNullable(deployedProbes.get(probeName))
				.orElseThrow(() -> new NullPointerException(String.format("Sonda `%s` inexistente", probeName)));

		for (String command : commands) {
			makeMovement(command, probe);
		}

	}

	private void validateCrash(Probe probe) throws CrashException {
		Coordinate coordinate = probe.getCoordinate();
		long probesOnSameCoordinate = deployedProbes.values().stream()
				// Valida crash
				.filter(p -> p.getCoordinate().overrideCoordina(coordinate)).count();

		boolean outsidePlateau = coordinate.lessThan(new Coordinate(0, 0)) || coordinate.greaterThan(maxCoordinate);

		if (probesOnSameCoordinate > 1 || outsidePlateau) {
			throw new CrashException();
		}
	}

	private void makeMovement(String command, Probe probe) throws CrashException {

		if (command.equals("L")) {
			probe.left();
		}

		if (command.equals("R")) {
			probe.right();
		}

		if (command.equals("M")) {
			probe.move();
			validateCrash(probe);
		}

	}

}
