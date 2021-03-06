package com.tsfreitas.probe.model;

import static com.tsfreitas.probe.constants.COMMAND.L;
import static com.tsfreitas.probe.constants.COMMAND.M;
import static com.tsfreitas.probe.constants.COMMAND.R;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.tsfreitas.probe.constants.COMMAND;
import com.tsfreitas.probe.exception.AlreadyExistProbeException;
import com.tsfreitas.probe.exception.CrashException;
import com.tsfreitas.probe.exception.ProbeNotExistsException;

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

	public List<Probe> getDeployedProbes() {
		return deployedProbes;
	}

	public Coordinate getMaxCoordinate() {
		return maxCoordinate;
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
	 * @throws AlreadyExistProbeException
	 */
	public void landProbe(Probe probe) throws CrashException, AlreadyExistProbeException {
		if (deployedProbes.stream().filter(probeWithName(probe.getProbeName())).findFirst().isPresent()) {
			throw new AlreadyExistProbeException();
		}

		deployedProbes.add(probe);
		validateCrash(probe);
	}

	/**
	 * Recbe e executa os comandos dados a uma sonda
	 * 
	 * @param probeName
	 * @param commands
	 * @throws CrashException
	 * @throws ProbeNotExistsException
	 */
	public void receiveCommands(String probeName, List<COMMAND> commands)
			throws CrashException, ProbeNotExistsException {
		// Recupera sonda
		Probe probe = getProbe(probeName);

		for (COMMAND command : commands) {
			makeMovement(command, probe);
		}

	}

	/**
	 * Recupera a sonda com o nome dado ou NullPointer caso não ache
	 * 
	 * @param probeName
	 * @return sonda encontrada
	 */
	public Probe getProbe(String probeName) throws ProbeNotExistsException {
		return deployedProbes.stream().filter(probeWithName(probeName)).findFirst()
				.orElseThrow(() -> new ProbeNotExistsException(probeName));
	}

	private Predicate<Probe> probeWithName(String name) {
		return probe -> probe.getProbeName().equals(name);
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
