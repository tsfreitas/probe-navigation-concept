package com.tsfreitas.probe.rest.dto;

import javax.validation.constraints.NotNull;

public class CommandsDTO {

	@NotNull
	private String commands;

	public String getCommands() {
		return commands;
	}

}
