package com.bobinho.server;

import com.bobinho.common.interfaces.BoardService;
import com.bobinho.common.interfaces.EColor;
import com.bobinho.common.interfaces.SquareService;
import com.bobinho.common.utils.ConfigUtils;

import java.awt.Point;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Board extends UnicastRemoteObject implements BoardService {

    private final List<SquareService> board;

    public Board(List<SquareService> board) throws RemoteException {
        super();

        this.board = board;
    }

    public Board() throws RemoteException {
        super();

        this.board = new ArrayList<>();

        for (int i = 0; i < ConfigUtils.BOARD_LENGTH * ConfigUtils.BOARD_LENGTH; i++) {
            this.board.add(new Square(EColor.WHITE, new Point(i % ConfigUtils.BOARD_LENGTH, i / ConfigUtils.BOARD_LENGTH)));
        }
    }

    @Override
    public List<SquareService> getBoard() throws RemoteException {
        return this.board;
    }

}