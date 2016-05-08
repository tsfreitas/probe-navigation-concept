package com.tsfreitas.probe.exception;

public class OfflineCommandsException extends Exception {

	private static final long serialVersionUID = 7243215510133633399L;

	public OfflineCommandsException() {
		super();
	}

	public String getPattern() {
		return "5 5\n1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRM\n";
	}

}
