package com.tsfreitas.probe.constants;

import java.util.stream.Stream;

public enum COMMAND {
	L, R, M;

	public static COMMAND commandOf(String letter) {
		return Stream.of(COMMAND.values()).//
				filter(cmd -> cmd.name().equalsIgnoreCase(letter)).findFirst().orElse(null);

	}
}
