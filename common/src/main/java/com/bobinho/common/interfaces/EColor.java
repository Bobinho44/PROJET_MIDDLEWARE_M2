package com.bobinho.common.interfaces;

import java.util.Arrays;

public enum EColor {
	WHITE(2, java.awt.Color.WHITE),
	BLUE(1, java.awt.Color.BLUE),
	RED(0, java.awt.Color.RED);

	private final int playerNumber;
	private final java.awt.Color paintColor;

	EColor(int playerNumber, java.awt.Color paintColor) {
		this.playerNumber = playerNumber;
		this.paintColor = paintColor;
	}

	public static EColor getColorFromInt(int associatedInt) {

		return Arrays.stream(values())
				.filter(color -> color.getPlayerNumber() == associatedInt)
				.findFirst()
				.orElse(WHITE);
	}

	public int getPlayerNumber() {
		return this.playerNumber;
	}

	public java.awt.Color getPaintColor() {
		return this.paintColor;
	}

}