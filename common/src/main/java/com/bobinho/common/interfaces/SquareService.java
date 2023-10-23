package com.bobinho.common.interfaces;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SquareService extends Remote {

    void setColor(EColor color) throws RemoteException;

    EColor getColor() throws RemoteException;

    Point getCoordinates() throws RemoteException;

    int getX() throws RemoteException;

    int getY() throws RemoteException;

}