package com.bobinho.server;

import com.bobinho.common.interfaces.GameManagerService;
import com.bobinho.common.interfaces.PlayerService;
import com.bobinho.common.interfaces.RoomService;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@Slf4j
public class GameManager extends UnicastRemoteObject implements GameManagerService {

    private static final HashMap<UUID, Room> rooms = new HashMap<>();

    public GameManager() throws RemoteException {
        super();

        log.info("GameManager constructor called");
    }

    @Override
    public Collection<RoomService> getAllAvailableRoom() throws RemoteException {
        return rooms.values().stream()
                .map(room -> (RoomService) room)
                .filter(room -> Try.of(room::isNotFull).get())
                .toList();
    }

    @Override
    public RoomService createRoom(String name, PlayerService player) throws RemoteException, InterruptedException {
        Room room = new Room(name);
        rooms.put(room.getId(), room);

        synchronized (rooms.get(room.getId())) {

            rooms.get(room.getId()).addPlayer(player);

            while (rooms.get(room.getId()).isNotFull()) {
                rooms.get(room.getId()).wait();
            }
        }

        return rooms.get(room.getId());
    }

    @Override
    public boolean joinRoom(RoomService room, PlayerService player) throws RemoteException {

        synchronized (rooms.get(room.getId())) {

            if (rooms.get(room.getId()).isNotFull()) {
                rooms.get(room.getId()).addPlayer(player);
                rooms.get(room.getId()).generateGame();
                rooms.get(room.getId()).notifyAll();

				return true;
            }
        }

		return false;
    }

    @Override
    public void leaveRoom(RoomService room, PlayerService player) throws RemoteException {
        synchronized (rooms.get(room.getId())) {

            rooms.get(room.getId()).removePlayer(player);

            rooms.get(room.getId()).notifyAll();

            if (rooms.get(room.getId()).isEmpty()) {
                rooms.remove(rooms.get(room.getId()).getId());
            }
        }
    }

    @Override
    public PlayerService createPlayer(String name) throws RemoteException {
        return new Player(name);
    }

}