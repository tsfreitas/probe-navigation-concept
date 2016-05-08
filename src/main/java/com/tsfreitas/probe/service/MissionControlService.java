package com.tsfreitas.probe.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.tsfreitas.probe.constants.COMMAND;
import com.tsfreitas.probe.exception.AlreadyExistProbeException;
import com.tsfreitas.probe.exception.CrashException;
import com.tsfreitas.probe.exception.MissionNotStartedException;
import com.tsfreitas.probe.exception.ProbeNotExistsException;
import com.tsfreitas.probe.model.Coordinate;
import com.tsfreitas.probe.model.MissionControl;
import com.tsfreitas.probe.model.Probe;

@Service
public class MissionControlService {

	private MissionControl missionControl = null;

	/**
	 * Registra uma nova missão dentro de um planalto. Apaga registros da missão
	 * anterior
	 */
	public void registerMission(Coordinate maxPlateauCoordinate) {
		missionControl = new MissionControl(maxPlateauCoordinate);
	}

	/**
	 * Adiciona uma nova sonda no planalto
	 * 
	 * @return
	 * 
	 * @throws CrashException
	 * @throws AlreadyExistProbeException
	 */
	public List<Probe> addProbe(Probe probe)
			throws CrashException, MissionNotStartedException, AlreadyExistProbeException {
		validate();
		missionControl.landProbe(probe);

		return missionControl.getDeployedProbes();
	}

	/**
	 * Recebe os comandos para uma sonda
	 * 
	 * @return
	 * @throws ProbeNotExistsException
	 */
	public List<Probe> executeCommands(String probeName, String commandString)
			throws CrashException, MissionNotStartedException, ProbeNotExistsException {
		validate();

		List<COMMAND> commands = transformStringIntoCommands(commandString);

		missionControl.receiveCommands(probeName, commands);

		return missionControl.getDeployedProbes();
	}

	public MissionControl getMissionControl() throws MissionNotStartedException {
		validate();
		return missionControl;
	}

	public Probe getProbe(String probeName) throws ProbeNotExistsException {
		return missionControl.getProbe(probeName);
	}

	private List<COMMAND> transformStringIntoCommands(String commandString) {
		return Stream.of(commandString.split("|"))//
				.map(letter -> COMMAND.commandOf(letter))//
				.filter(cmd -> cmd != null).collect(Collectors.toList());
	}

	private void validate() throws MissionNotStartedException {
		if (Objects.isNull(missionControl)) {
			throw new MissionNotStartedException();
		}
	}

}
