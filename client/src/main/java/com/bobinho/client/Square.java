package com.bobinho.client;

import com.bobinho.common.interfaces.EColor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Square {

	private EColor color;
	private final Point coordinates;

	public Square(EColor color, Point coordinates) {
		super();

		this.color = color;
		this.coordinates = coordinates;
	}

	public void setColor(EColor color) {
		this.color = color;
	}

	public EColor getColor() {
		return this.color;
	}

	public Point getCoordinates() {
		return this.coordinates;
	}

	public int getX() {
		return this.coordinates.x;
	}

	public int getY() {
		return this.coordinates.y;
	}

	public void draw(Graphics g, Point boardTopLeftCorner, int squareSize) {
		g.setColor(getColor().getPaintColor());
		int x = boardTopLeftCorner.x + this.getX() * (squareSize + 1) + 1;
		int y = boardTopLeftCorner.y + this.getY() * (squareSize + 1) + 1;
		g.fillRect(x, y, squareSize, squareSize);
		g.setColor(Color.BLACK);

		if (getY() == 0) g.drawString("" + (getX() + 1), x + squareSize / 2, y - 15);
		if (getX() == 0) g.drawString("" + (getY() + 1), x - 25, y + squareSize / 2);
	}
	
}