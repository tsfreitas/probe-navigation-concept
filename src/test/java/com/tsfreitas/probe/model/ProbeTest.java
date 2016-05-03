package com.tsfreitas.probe.model;

import org.junit.Assert;
import org.junit.Test;

import com.tsfreitas.probe.constants.DIRECTION;

/**
 * Created by tsfreitas on 30/04/16.
 */
public class ProbeTest {

	private Probe probe;

	@Test
	public void deveVirarParaADireita() {
		// GIVEN
		probe = new Probe(new Coordinate(1, 1), DIRECTION.NORTH);

		// WHEN
		probe.right();

		// THEN
		validateProbe(1, 1, DIRECTION.EAST);
	}

	@Test
	public void deveVirarParaAEsquerda() {
		// GIVEN
		probe = new Probe(new Coordinate(1, 1), DIRECTION.NORTH);

		// WHEN
		probe.left();

		// THEN
		validateProbe(1, 1, DIRECTION.WEST);
	}

	@Test
	public void deveAndarParaONorte() {
		// GIVEN
		probe = new Probe(new Coordinate(1, 1), DIRECTION.NORTH);

		// WHEN
		probe.move();

		// THEN
		validateProbe(1, 2, DIRECTION.NORTH);

	}

	@Test
	public void deveAndarParaOSul() {
		// GIVEN
		probe = new Probe(new Coordinate(1, 1), DIRECTION.SOUTH);

		// WHEN
		probe.move();

		// THEN
		validateProbe(1, 0, DIRECTION.SOUTH);
	}

	@Test
	public void deveAndarParaOLeste() {
		// GIVEN
		probe = new Probe(new Coordinate(1, 1), DIRECTION.EAST);

		// WHEN
		probe.move();

		// THEN
		validateProbe(2, 1, DIRECTION.EAST);
	}

	@Test
	public void deveAndarParaOOeste() {
		// GIVEN
		probe = new Probe(new Coordinate(1, 1), DIRECTION.WEST);

		// WHEN
		probe.move();

		// THEN
		validateProbe(0, 1, DIRECTION.WEST);
	}

	private void validateProbe(int x, int y, DIRECTION direction) {
		Assert.assertEquals(x, probe.getCoordinate().getX());
		Assert.assertEquals(y, probe.getCoordinate().getY());
		Assert.assertEquals(direction, probe.getDirection());
	}

}
