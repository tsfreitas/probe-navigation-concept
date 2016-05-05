package com.tsfreitas.probe.model;

import static com.tsfreitas.probe.constants.COMMAND.L;
import static com.tsfreitas.probe.constants.COMMAND.M;
import static com.tsfreitas.probe.constants.COMMAND.R;

import java.util.ArrayList;
import java.util.List;

import com.tsfreitas.probe.constants.COMMAND;
import com.tsfreitas.probe.exception.CrashException;

/**
 * Controle da missão, controla as sondas e tem conhecimento do terreno a ser
 * explorado
 * 
 * Created by tsfreitas on 02/05/16.
 */
public class MissionControl {

	private final Coordinate maxCoordinate;

	private List<Probe> deployedProbes = new ArrayList<>();

	public MissionControl(Coordinate maxCoordinate) {
		this.maxCoordinate = maxCoordinate;
	}

	/**
	 * Pousa uma nova sonda no planalto
	 * 
	 * @param probeName
	 *            Nome da minha sonda
	 * @param probe
	 *            sonda
	 * @throws CrashException
	 *             Caso a sonda pouse onde outra sonda esteja
	 */
	public void landProbe(Probe probe) throws CrashException {
		deployedProbes.add(probe);
		validateCrash(probe);
	}

	/**
	 * Recbe e executa os comandos dados a uma sonda
	 * 
	 * @param probeName
	 * @param commands
	 * @throws CrashException
	 */
	public void receiveCommands(String probeName, List<COMMAND> commands) throws CrashException {
		// Recupera sonda
		Probe probe = getProbe(probeName);

		for (COMMAND command : commands) {
			makeMovement(command, probe);
		}

	}

	/**
	 * Recupera a sonda com o nome dado ou NullPointer caso não ache
	 * @param probeName
	 * @return sonda encontrada
	 */
	public Probe getProbe(String probeName) {
		return deployedProbes.stream().
				filter(probe -> {
					return probe.getProbeName().equals(probeName);
		})
				.findFirst()
				.orElseThrow(() -> new NullPointerException(String.format("Sonda `%s` inexistente", probeName)));
	}

	public List<Probe> getAllProbes() {
		return deployedProbes;
	}

	private void validateCrash(Probe probe) throws CrashException {
		Coordinate coordinate = probe.getCoordinate();
		long probesOnSameCoordinate = deployedProbes.stream()
				// Valida crash
				.filter(p -> p.getCoordinate().overrideCoordina(coordinate)).count();

		boolean outsidePlateau = coordinate.lessThan(new Coordinate(0, 0)) || coordinate.greaterThan(maxCoordinate);

		if (probesOnSameCoordinate > 1 || outsidePlateau) {
			throw new CrashException();
		}
	}

	private void makeMovement(COMMAND command, Probe probe) throws CrashException {

		if (L.equals(command)) {
			probe.left();
		}

		if (R.equals(command)) {
			probe.right();
		}

		if (M.equals(command)) {
			probe.move();
			validateCrash(probe);
		}

	}

}
