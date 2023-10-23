package com.bobinho.client;

import com.bobinho.common.interfaces.EColor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Drawer {

	private final EColor color;
	private final Point coordinates;

	public Drawer(EColor color, Point coordinates) {
		super();

		this.color = color;
		this.coordinates = coordinates;
	}

	public EColor getColor() {
		return this.color;
	}

	public void draw(Graphics g, Point boardTopLeftCorner, int squareSize) {
		g.setColor(this.color.getPaintColor());

		int x = boardTopLeftCorner.x + this.coordinates.x * (squareSize + 1) + 1;
		int y = boardTopLeftCorner.y + this.coordinates.y * (squareSize + 1) + 1;

		g.fillRect(x, y, squareSize, squareSize);
		g.setColor(Color.BLACK);

		if (this.coordinates.y == 0) g.drawString("" + (this.coordinates.x + 1), x + squareSize / 2, y - 15);
		if (this.coordinates.x == 0) g.drawString("" + (this.coordinates.y + 1), x - 25, y + squareSize / 2);
	}
	
}