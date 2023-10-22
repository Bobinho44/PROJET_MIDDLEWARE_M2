package com.bobinho.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface PlayerService extends Remote {

	UUID getId() throws RemoteException;

	String getName() throws RemoteException;

}
