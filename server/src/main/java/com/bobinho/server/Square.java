package com.bobinho.server;

import com.bobinho.common.interfaces.EColor;
import com.bobinho.common.interfaces.SquareService;

import java.awt.Point;
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

	public Point getCoordinates() throws RemoteException {
		return this.coordinates;
	}

	public int getX() throws RemoteException {
		return this.coordinates.x;
	}

	public int getY() throws RemoteException {
		return this.coordinates.y;
	}
	
}