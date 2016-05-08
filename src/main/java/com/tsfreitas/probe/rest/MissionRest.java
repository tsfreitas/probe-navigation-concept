package com.tsfreitas.probe.rest;

import javax.validation.Valid;

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
import com.tsfreitas.probe.exception.OfflineCommandsException;
import com.tsfreitas.probe.exception.ProbeNotExistsException;
import com.tsfreitas.probe.model.Coordinate;
import com.tsfreitas.probe.model.MissionControl;
import com.tsfreitas.probe.model.Probe;
import com.tsfreitas.probe.rest.dto.CommandsDTO;
import com.tsfreitas.probe.rest.dto.CoordinateDTO;
import com.tsfreitas.probe.rest.dto.ProbeDTO;
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
		service.registerMission(body.createCoordinate());

		return report();
	}

	/**
	 * Mostra posição da sonda
	 * 
	 * @throws ProbeNotExistsException
	 */
	@RequestMapping("probe/{probeName}")
	public Probe showProbe(@PathVariable String probeName) throws ProbeNotExistsException {
		return service.getProbe(probeName);
	}

	/**
	 * Envia uma sonda para o planalto
	 * 
	 * @return
	 * @throws AlreadyExistProbeException
	 * @throws MissionNotStartedException
	 * @throws CrashException
	 */
	@RequestMapping(value = "probe/{probeName}/send", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public MissionControl sendProbe(@PathVariable String probeName, @Valid @RequestBody ProbeDTO body)
			throws CrashException, MissionNotStartedException, AlreadyExistProbeException {

		service.addProbe(body.createProbe(probeName));

		return report();
	}

	/**
	 * Envia comandos para uma sonda
	 * 
	 * @return
	 * @throws ProbeNotExistsException
	 * @throws MissionNotStartedException
	 * @throws CrashException
	 */
	@RequestMapping(value = "probe/{probeName}/commands", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public MissionControl sendCommand(@PathVariable String probeName, @Valid @RequestBody CommandsDTO body)
			throws CrashException, MissionNotStartedException, ProbeNotExistsException {

		service.executeCommands(probeName, body.getCommands());

		return report();

	}

	/**
	 * Manda todos os dados da missão de uma única vez
	 * 
	 * @throws MissionNotStartedException
	 * @throws AlreadyExistProbeException
	 * @throws CrashException
	 * @throws ProbeNotExistsException
	 * @throws OfflineCommandsException
	 */
	@RequestMapping(value = "offline", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
	public MissionControl batchMission(@RequestBody String body) throws MissionNotStartedException, CrashException,
			AlreadyExistProbeException, ProbeNotExistsException, OfflineCommandsException {

		try {
			String[] split = body.split("\n");

			// Cria planalto
			Coordinate plateauCoordinates = createCoordinateFromString(split[0]);
			service.registerMission(plateauCoordinates);

			// Recupe posição inicial e comandos de cada sonda
			int line = 1;
			int probeNumber = 1;
			while (line < split.length) {
				// Adiciona sonda
				String probeName = "probe" + probeNumber++;
				Probe probe = createProbeFromStrings(probeName, split[line++]);
				service.addProbe(probe);

				// Executa comandos
				service.executeCommands(probeName, split[line++]);

			}
		} catch (NumberFormatException | NullPointerException | ArrayIndexOutOfBoundsException ex) {
			throw new OfflineCommandsException();
		}

		return report();
	}

	private Probe createProbeFromStrings(String probeName, String commandString) {
		String[] split = commandString.split(" ");
		Coordinate coordinate = createCoordinateFromString(commandString);

		DIRECTION direction = DIRECTION.getDirectionFromAbbreviation(split[2].trim());

		return new Probe(probeName, coordinate, direction);
	}

	private Coordinate createCoordinateFromString(String commandString) {
		String[] split = commandString.split(" ");
		Integer x = Integer.valueOf(split[0].trim());
		Integer y = Integer.valueOf(split[1].trim());

		return new Coordinate(x, y);
	}

}
