package com.tsfreitas.probe.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.gson.Gson;
import com.tsfreitas.probe.constants.DIRECTION;
import com.tsfreitas.probe.exception.CrashException;
import com.tsfreitas.probe.exception.MissionException;
import com.tsfreitas.probe.model.Coordinate;
import com.tsfreitas.probe.model.MissionControl;
import com.tsfreitas.probe.model.Probe;

public class MissionControlServiceTest {

	@Test
	public void deveRegistrarMissao() {
		// GIVEN
		MissionControlService service = new MissionControlService();
		Coordinate maxPlateauCoordinate = new Coordinate(5, 5);

		// WHEN
		service.registerMission(maxPlateauCoordinate);

		// THEN
		MissionControl mc = (MissionControl) ReflectionTestUtils.getField(service, "missionControl");

		String json = new Gson().toJson(mc);

		Assert.assertEquals("{\"maxCoordinate\":{\"x\":5,\"y\":5},\"deployedProbes\":[]}", json);

	}

	@Test
	public void deveAdicionarSonda() throws CrashException, MissionException {
		// GIVEN
		MissionControlService service = new MissionControlService();
		service.registerMission(new Coordinate(5, 5));
		Probe probe = new Probe("probe1", new Coordinate(1, 1), DIRECTION.NORTH);

		// WHEN
		service.addProbe(probe);

		// THEN
		MissionControl mc = (MissionControl) ReflectionTestUtils.getField(service, "missionControl");

		String json = new Gson().toJson(mc);

		Assert.assertEquals(
				"{\"maxCoordinate\":{\"x\":5,\"y\":5},\"deployedProbes\":[{\"probeName\":\"probe1\",\"coordinate\":{\"x\":1,\"y\":1},\"direction\":\"NORTH\"}]}",
				json);

	}

	@Test
	public void deveExecutarComandos() throws CrashException, MissionException {
		// GIVEN
		MissionControlService service = new MissionControlService();
		service.registerMission(new Coordinate(5, 5));
		Probe probe = new Probe("probe1", new Coordinate(1, 1), DIRECTION.NORTH);
		service.addProbe(probe);

		// WHEN
		service.executeCommands("probe1", "RMM");

		// THEN
		MissionControl mc = (MissionControl) ReflectionTestUtils.getField(service, "missionControl");

		String json = new Gson().toJson(mc);

		Assert.assertEquals(
				"{\"maxCoordinate\":{\"x\":5,\"y\":5},\"deployedProbes\":[{\"probeName\":\"probe1\",\"coordinate\":{\"x\":3,\"y\":1},\"direction\":\"EAST\"}]}",
				json);

	}

	@Test(expected = MissionException.class)
	public void deveDarErroAoAdicionarSondaSemRegistrarMissao() throws CrashException, MissionException {
		// GIVEN
		MissionControlService service = new MissionControlService();
		Probe probe = new Probe("probe1", new Coordinate(1, 1), DIRECTION.NORTH);

		// WHEN
		service.addProbe(probe);

		// THEN
		Assert.fail();

	}

	@Test(expected = MissionException.class)
	public void deveDarErroAoExecutarComandoDeSondaSemRegistrarMissao() throws CrashException, MissionException {
		// GIVEN
		MissionControlService service = new MissionControlService();

		// WHEN
		service.executeCommands("probe1)", "LLM");
		// THEN
		Assert.fail();

	}

	@Test(expected = MissionException.class)
	public void deveDarErroAoExecutarComandoDeSondaInexistente() throws CrashException, MissionException {
		// GIVEN
		MissionControlService service = new MissionControlService();

		// WHEN
		service.executeCommands("probe2)", "LLM");
		// THEN
		Assert.fail();

	}

	@Test
	public void deveIgnorarComandoInexistente() throws CrashException, MissionException {
		// GIVEN
		MissionControlService service = new MissionControlService();
		service.registerMission(new Coordinate(5, 5));
		Probe probe = new Probe("probe1", new Coordinate(1, 1), DIRECTION.NORTH);
		service.addProbe(probe);

		// WHEN
		service.executeCommands("probe1", "IJHKKRYT");

		// THEN
		MissionControl mc = (MissionControl) ReflectionTestUtils.getField(service, "missionControl");

		String json = new Gson().toJson(mc);

		Assert.assertEquals(
				"{\"maxCoordinate\":{\"x\":5,\"y\":5},\"deployedProbes\":[{\"probeName\":\"probe1\",\"coordinate\":{\"x\":1,\"y\":1},\"direction\":\"EAST\"}]}",
				json);
	}

}
