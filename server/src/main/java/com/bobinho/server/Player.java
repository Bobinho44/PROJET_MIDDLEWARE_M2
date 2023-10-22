package com.bobinho.server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import com.bobinho.common.interfaces.PlayerService;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class Player extends UnicastRemoteObject implements PlayerService, Serializable {

	private final UUID id;
	private final String name;

	public Player(String name) throws RemoteException {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
	}

	@Override
	public UUID getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

}