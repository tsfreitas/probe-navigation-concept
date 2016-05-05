package com.tsfreitas.probe.model;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.tsfreitas.probe.constants.DIRECTION;
import com.tsfreitas.probe.exception.CrashException;

public class CruiseControlTest {

	@Test
	public void devePousarDuasSondas() throws CrashException {
		// GIVEN
		Coordinate maxCoordinate = new Coordinate(5, 5);
		CruiseControl cr = new CruiseControl(maxCoordinate);

		Probe probe1 = new Probe(new Coordinate(1, 1), DIRECTION.NORTH);
		Probe probe2 = new Probe(new Coordinate(2, 2), DIRECTION.SOUTH);

		// WHEN
		cr.landProbe("probe1", probe1);
		cr.landProbe("probe2", probe2);

		// THEN
		Assert.assertEquals(DIRECTION.NORTH, cr.getProbe("probe1").getDirection());
		Assert.assertEquals(1, cr.getProbe("probe1").getCoordinate().getX());
		Assert.assertEquals(1, cr.getProbe("probe1").getCoordinate().getY());

		Assert.assertEquals(DIRECTION.SOUTH, cr.getProbe("probe2").getDirection());
		Assert.assertEquals(2, cr.getProbe("probe2").getCoordinate().getX());
		Assert.assertEquals(2, cr.getProbe("probe2").getCoordinate().getY());
	}

	@Test(expected = CrashException.class)
	public void devePousarDuasSondasNaMesmaPosicao() throws CrashException {
		// GIVEN
		Coordinate maxCoordinate = new Coordinate(5, 5);
		CruiseControl cr = new CruiseControl(maxCoordinate);

		Probe probe1 = new Probe(new Coordinate(1, 1), DIRECTION.NORTH);
		Probe probe2 = new Probe(new Coordinate(1, 1), DIRECTION.SOUTH);

		// WHEN
		cr.landProbe("probe1", probe1);
		cr.landProbe("probe2", probe2);

		// THEN
		Assert.fail();
	}

	@Test
	public void deveMandarComandoParaPrimeiraSonda() throws CrashException {
		// GIVEN
		Coordinate maxCoordinate = new Coordinate(5, 5);
		CruiseControl cr = new CruiseControl(maxCoordinate);

		Probe probe1 = new Probe(new Coordinate(1, 1), DIRECTION.NORTH);
		Probe probe2 = new Probe(new Coordinate(2, 2), DIRECTION.SOUTH);

		cr.landProbe("probe1", probe1);
		cr.landProbe("probe2", probe2);

		// WHEN
		cr.receiveCommands("probe1", Arrays.asList("L", "M"));

		// THEN
		Assert.assertEquals(DIRECTION.WEST, cr.getProbe("probe1").getDirection());
		Assert.assertEquals(0, cr.getProbe("probe1").getCoordinate().getX());
		Assert.assertEquals(1, cr.getProbe("probe1").getCoordinate().getY());

		Assert.assertEquals(DIRECTION.SOUTH, cr.getProbe("probe2").getDirection());
		Assert.assertEquals(2, cr.getProbe("probe2").getCoordinate().getX());
		Assert.assertEquals(2, cr.getProbe("probe2").getCoordinate().getY());

	}

	@Test
	public void deveMandarComandoParaSegundaSonda() throws CrashException {
		// GIVEN
		Coordinate maxCoordinate = new Coordinate(5, 5);
		CruiseControl cr = new CruiseControl(maxCoordinate);

		Probe probe1 = new Probe(new Coordinate(1, 1), DIRECTION.NORTH);
		Probe probe2 = new Probe(new Coordinate(2, 2), DIRECTION.SOUTH);

		cr.landProbe("probe1", probe1);
		cr.landProbe("probe2", probe2);

		// WHEN
		cr.receiveCommands("probe2", Arrays.asList("L", "M"));

		// THEN
		Assert.assertEquals(DIRECTION.NORTH, cr.getProbe("probe1").getDirection());
		Assert.assertEquals(1, cr.getProbe("probe1").getCoordinate().getX());
		Assert.assertEquals(1, cr.getProbe("probe1").getCoordinate().getY());

		Assert.assertEquals(DIRECTION.EAST, cr.getProbe("probe2").getDirection());
		Assert.assertEquals(3, cr.getProbe("probe2").getCoordinate().getX());
		Assert.assertEquals(2, cr.getProbe("probe2").getCoordinate().getY());
	}

	@Test
	public void deveMandarComandoParaAsDuasSondas() throws CrashException {
		// GIVEN
		Coordinate maxCoordinate = new Coordinate(5, 5);
		CruiseControl cr = new CruiseControl(maxCoordinate);

		Probe probe1 = new Probe(new Coordinate(1, 2), DIRECTION.NORTH);
		Probe probe2 = new Probe(new Coordinate(3, 3), DIRECTION.EAST);

		cr.landProbe("probe1", probe1);
		cr.landProbe("probe2", probe2);

		// WHEN
		cr.receiveCommands("probe1", Arrays.asList("LMLMLMLMM".split("|")));
		cr.receiveCommands("probe2", Arrays.asList("MMRMMRMRRM".split("|")));

		// THEN
		Assert.assertEquals(DIRECTION.NORTH, cr.getProbe("probe1").getDirection());
		Assert.assertEquals(1, cr.getProbe("probe1").getCoordinate().getX());
		Assert.assertEquals(3, cr.getProbe("probe1").getCoordinate().getY());

		Assert.assertEquals(DIRECTION.EAST, cr.getProbe("probe2").getDirection());
		Assert.assertEquals(5, cr.getProbe("probe2").getCoordinate().getX());
		Assert.assertEquals(1, cr.getProbe("probe2").getCoordinate().getY());
	}

	@Test(expected = NullPointerException.class)
	public void deveMandarComandoParaSondaInexistente() throws CrashException {
		// GIVEN
		Coordinate maxCoordinate = new Coordinate(5, 5);
		CruiseControl cr = new CruiseControl(maxCoordinate);

		Probe probe1 = new Probe(new Coordinate(1, 1), DIRECTION.NORTH);
		Probe probe2 = new Probe(new Coordinate(2, 2), DIRECTION.SOUTH);

		cr.landProbe("probe1", probe1);
		cr.landProbe("probe2", probe2);

		// WHEN
		cr.receiveCommands("probe3", Arrays.asList("L", "M"));

		// THEN
	}

	@Test(expected = CrashException.class)
	public void deveMandarSondaParaForaDoPlanalto() throws CrashException {
		// GIVEN
		Coordinate maxCoordinate = new Coordinate(1, 1);
		CruiseControl cr = new CruiseControl(maxCoordinate);

		Probe probe1 = new Probe(new Coordinate(1, 1), DIRECTION.NORTH);

		cr.landProbe("probe1", probe1);

		// WHEN
		cr.receiveCommands("probe1", Arrays.asList("LMLMLMLMM".split("|")));

		// THEN
		Assert.fail();
	}

	@Test(expected = CrashException.class)
	public void deveMandarDuasSondasParaAMesmaPosicao() throws CrashException {
		// GIVEN
		Coordinate maxCoordinate = new Coordinate(5, 5);
		CruiseControl cr = new CruiseControl(maxCoordinate);

		Probe probe1 = new Probe(new Coordinate(1, 1), DIRECTION.NORTH);
		Probe probe2 = new Probe(new Coordinate(1, 3), DIRECTION.SOUTH);

		cr.landProbe("probe1", probe1);
		cr.landProbe("probe2", probe2);

		// WHEN
		cr.receiveCommands("probe1", Arrays.asList("M"));
		cr.receiveCommands("probe2", Arrays.asList("M"));

		// THEN
		Assert.fail();
	}

}
