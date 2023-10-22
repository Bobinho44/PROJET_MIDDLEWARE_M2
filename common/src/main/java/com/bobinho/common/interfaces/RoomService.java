package com.bobinho.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface RoomService extends Remote {

	UUID getId()throws RemoteException;

	String getName()throws RemoteException;

	boolean isNotFull()throws RemoteException;

	boolean isEmpty()throws RemoteException;

	void addPlayer(PlayerService player) throws RemoteException;

	void removePlayer(PlayerService player) throws RemoteException;

	GameService getGame() throws RemoteException, InterruptedException;

	List<String> getPlayerNames() throws RemoteException;

}