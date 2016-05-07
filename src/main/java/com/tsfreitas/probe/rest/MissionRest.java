package com.tsfreitas.probe.rest;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tsfreitas.probe.constants.DIRECTION;
import com.tsfreitas.probe.exception.AlreadyExistProbeException;
import com.tsfreitas.probe.exception.CrashException;
import com.tsfreitas.probe.exception.MissionNotStartedException;
import com.tsfreitas.probe.model.Coordinate;
import com.tsfreitas.probe.model.MissionControl;
import com.tsfreitas.probe.model.Probe;
import com.tsfreitas.probe.service.MissionControlService;

@RestController
@RequestMapping(value = "/mission", produces = MediaType.APPLICATION_JSON_VALUE)
public class MissionRest {

	private MissionControlService service;

	@Autowired
	public MissionRest(MissionControlService service) {
		this.service = service;
	}

	/**
	 * Mostra a posição de todas as sondas no planalto
	 * 
	 * @return
	 * @throws MissionNotStartedException
	 */
	@RequestMapping("report")
	public MissionControl report() throws MissionNotStartedException {
		return service.getMissionControl();
	}

	/**
	 * Inicia uma missão, mostrando o tamanho do planalto
	 * 
	 * @return
	 * @throws MissionNotStartedException
	 */
	@RequestMapping(value = "startMission", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public MissionControl startMission(@Valid @RequestBody CoordinateDTO body) throws MissionNotStartedException {
		// Transforma
		Coordinate maxPlateauCoordinate = new Coordinate(body.getX(), body.getY());

		// Executa ação
		service.registerMission(maxPlateauCoordinate);
		return report();
	}

	/**
	 * Envia uma sonda para o planalto
	 * 
	 * @return
	 * @throws AlreadyExistProbeException
	 * @throws MissionNotStartedException
	 * @throws CrashException
	 */
	@RequestMapping(value = "sendProbe/{probeName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public MissionControl sendProbe(@PathVariable String probeName, @RequestBody ProbeDTO body)
			throws CrashException, MissionNotStartedException, AlreadyExistProbeException {
		Coordinate coordinate = new Coordinate(body.getX(), body.getY());

		Probe probe = new Probe(probeName, coordinate, body.getDirection());

		service.addProbe(probe);
		return report();
	}

	/**
	 * Envia comandos para uma sonda
	 */
	@RequestMapping("sendCommand")
	public void sendCommand() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Manda todos os dados da missão de uma única vez
	 */
	@RequestMapping("batchMission")
	public void batchMission() {
		throw new UnsupportedOperationException();
	}

}

class CoordinateDTO {

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

}

class ProbeDTO extends CoordinateDTO {

	@NotNull
	private DIRECTION direction;

	public DIRECTION getDirection() {
		return direction;
	}

}
