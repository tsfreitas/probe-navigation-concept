package com.tsfreitas.probe.exception;

public class ProbeNotExistsException extends Exception {
	
	private String probeName;

	private static final long serialVersionUID = 2969084380195229429L;

	public ProbeNotExistsException(String probeName) {
		super();
		this.probeName = probeName;
	}
	
	public String getProbeName() {
		return probeName;
	}

}
