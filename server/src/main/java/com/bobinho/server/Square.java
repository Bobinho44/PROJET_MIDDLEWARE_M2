package com.bobinho.server;

import java.awt.*;

import java.awt.Point;

import com.bobinho.common.interfaces.EColor;
import com.bobinho.common.interfaces.SquareService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Square extends UnicastRemoteObject implements SquareService {

	private EColor color;
	private final Point coordinates;

	public Square(EColor color, Point coordinates) throws RemoteException {
		super();

		this.color = color;
		this.coordinates = coordinates;
	}

	public void setColor(EColor color) throws RemoteException {
		this.color = color;
	}

	public EColor getColor() throws RemoteException {
		return this.color;
	}

	public Point getCoordinates() {
		return this.coordinates;
	}

	public int getX() throws RemoteException {
		return this.coordinates.x;
	}

	public int getY() throws RemoteException {
		return this.coordinates.y;
	}

	public void draw(Graphics g, Point boardTopLeftCorner, int squareSize) throws RemoteException {
		g.setColor(getColor().getPaintColor());

		int x = boardTopLeftCorner.x + this.getX() * (squareSize + 1) + 1;
		int y = boardTopLeftCorner.y + this.getY() * (squareSize + 1) + 1;
		g.fillRect(x, y, squareSize, squareSize);

		g.setColor(java.awt.Color.BLACK);

		if (getY() == 0) g.drawString("" + (getX() + 1), x + squareSize / 2, y - 15);
		if (getX() == 0) g.drawString("" + (getY() + 1), x - 25, y + squareSize / 2);
	}
	
}