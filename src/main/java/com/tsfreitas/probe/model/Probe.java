package com.tsfreitas.probe.model;

import com.tsfreitas.probe.constants.DIRECTION;

/**
 * A sonda sabe se movimentar, não possui conhecimento de sondas vizinhas ou da extensão do terreno
 * Created by tsfreitas on 02/05/16.
 */
public class Probe {

	private int x;

	private int y;

	private DIRECTION direction;

	public Probe(int x, int y, DIRECTION direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public DIRECTION getDirection() {
		return direction;
	}
	
	 public void left() {
		 direction = direction.previous();
	 }
	 
	 public void right() {
		 direction = direction.next();
	 }
	 
	 public void move() {
		 x += direction.getX();
		 y += direction.getY();
	 }
}
