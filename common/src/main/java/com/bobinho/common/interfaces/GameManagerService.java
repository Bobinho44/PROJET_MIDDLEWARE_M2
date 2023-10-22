package com.bobinho.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface GameManagerService extends Remote {

	Collection<RoomService> getAllRoom() throws RemoteException;

	RoomService createRoom(String name, PlayerService player) throws RemoteException, InterruptedException;

	boolean joinRoom(RoomService room, PlayerService player) throws RemoteException;

	void leaveRoom(RoomService room, PlayerService player) throws RemoteException;

	PlayerService createPlayer(String name) throws RemoteException;

}