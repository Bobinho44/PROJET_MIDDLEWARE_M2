package com.bobinho.client;

import com.bobinho.common.interfaces.RoomService;
import com.bobinho.common.utils.PointUtils;
import lombok.extern.slf4j.Slf4j;

import java.awt.Color;
import java.awt.Point;
import java.rmi.RemoteException;

@Slf4j
public class Controller {

    private final Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public void getAvailableRoom() {


        try {
            this.model.refreshAvailableRoom();
        }

        catch (Exception e) {
            log.error("Unexpected available room exception!", e);
        }
    }

    public boolean isValidRoomName(String name) {
        return name != null && name.length() >= 3;
    }

    public boolean register(String name) {

		if (name == null || name.length() < 3) {
			return false;
		}

        try {
            this.model.register(name);
        }

        catch (Exception e) {
            log.error("Unexpected registration exception!", e);
			return false;
        }

		return true;
    }

    public void createRoom(String name) {

        try {
            this.model.createRoom(name);
        }

        catch (RemoteException | InterruptedException e) {
            log.error("Unexpected room creation exception!", e);
        }
    }

    public boolean joinRoom(RoomService room) {

        try {
            return this.model.joinRoom(room);
        }

        catch (RemoteException | InterruptedException e) {
            log.error("Unexpected room join exception!", e);
        }

        return false;
    }

    public void click(Point clickedPoint, Color color) throws RemoteException {

        if (PointUtils.isBetween(View.GAMEBOARD_TOP_LEFT_CORNER, PointUtils.translate(View.GAMEBOARD_TOP_LEFT_CORNER, View.GAMEBOARD_SIZE), clickedPoint) && color.equals(Color.WHITE)) {

            int i = (int) ((clickedPoint.getX() - 1 - View.GAMEBOARD_TOP_LEFT_CORNER.getX()) / (View.SQUARE_SIZE + 1));
            int j = (int) ((clickedPoint.getY() - 1 - View.GAMEBOARD_TOP_LEFT_CORNER.getY()) / (View.SQUARE_SIZE + 1));

            this.model.play(i, j);
        }
    }

}