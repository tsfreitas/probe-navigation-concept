package com.tsfreitas.probe.constants;

import org.junit.Assert;
import org.junit.Test;

public class DIRECTIONTest {
	
	
	@Test
	public void devePegarProximaDirecao() {
		Assert.assertEquals(DIRECTION.EAST, DIRECTION.NORTH.next());
		Assert.assertEquals(DIRECTION.SOUTH, DIRECTION.EAST.next());
		Assert.assertEquals(DIRECTION.WEST, DIRECTION.SOUTH.next());
		Assert.assertEquals(DIRECTION.NORTH, DIRECTION.WEST.next());
	}
	
	
	@Test
	public void devePegarDirecaoAnterior() {
		Assert.assertEquals(DIRECTION.WEST, DIRECTION.NORTH.previous());
		Assert.assertEquals(DIRECTION.SOUTH, DIRECTION.WEST.previous());
		Assert.assertEquals(DIRECTION.EAST, DIRECTION.SOUTH.previous());
		Assert.assertEquals(DIRECTION.NORTH, DIRECTION.EAST.previous());
	}

}
