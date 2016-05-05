package com.tsfreitas.probe.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tsfreitas.probe.exception.CrashException;
import com.tsfreitas.probe.model.Coordinate;
import com.tsfreitas.probe.model.Probe;

@Service
public class MissionControlService {

	/**
	 * Registra uma nova missão dentro de um planalto. Apaga registros da missão
	 * anterior
	 */
	public void registerMission(Coordinate maxPlateauCoordinate) {
	}

	/**
	 * Adiciona uma nova sonda no planalto
	 * 
	 * @throws CrashException
	 */
	public void addProbe(Probe probe) throws CrashException {
	}

	/**
	 * Recebe os comandos para uma sonda
	 */
	public void executeCommands(String probeName, List<String> commands) {
		
	}

}
