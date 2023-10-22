package com.bobinho.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameService extends Remote {

	void awaitPlayerTurn(PlayerService player) throws RemoteException, InterruptedException;

	void play(int i, int j) throws RemoteException;

	boolean isFinished() throws RemoteException;

	BoardService getBoard() throws RemoteException;

}