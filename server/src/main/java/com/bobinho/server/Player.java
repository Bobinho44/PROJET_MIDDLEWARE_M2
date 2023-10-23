package com.bobinho.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import com.bobinho.common.interfaces.PlayerService;

public class Player extends UnicastRemoteObject implements PlayerService {

	private final UUID id;
	private final String name;

	public Player(String name) throws RemoteException {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
	}

	@Override
	public UUID getId() throws RemoteException{
		return this.id;
	}

	@Override
	public String getName() throws RemoteException {
		return this.name;
	}

}