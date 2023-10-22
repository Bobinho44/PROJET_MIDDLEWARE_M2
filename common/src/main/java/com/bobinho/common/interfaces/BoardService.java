package com.bobinho.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BoardService extends Remote {

    List<SquareService> getBoard() throws RemoteException;

}