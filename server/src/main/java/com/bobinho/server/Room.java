package com.bobinho.server;

import com.bobinho.common.interfaces.GameService;
import com.bobinho.common.interfaces.PlayerService;
import com.bobinho.common.interfaces.RoomService;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Slf4j
public class Room extends UnicastRemoteObject implements RoomService {

	private static final int ROOM_SIZE = 2;

	private final UUID id;
	private final String name;
	private final HashMap<UUID, PlayerService> players = new HashMap<>();
	private GameService game;

	public Room(String name) throws RemoteException {
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

	@Override
	public boolean isNotFull() throws RemoteException {
		return this.players.size() < ROOM_SIZE;
	}

	@Override
	public boolean isEmpty() throws RemoteException {
		return this.players.isEmpty();
	}

	public void addPlayer(PlayerService player) throws RemoteException {
		this.players.put(player.getId(), player);
	}

	@Override
	public void removePlayer(PlayerService player) throws RemoteException {
		this.players.remove(player.getId());
	}

	@Override
	public GameService getGame() throws RemoteException, InterruptedException {
		synchronized (this) {

			while (this.game == null) {
				this.wait();
			}
		}

		return this.game;
	}

	protected void generateGame() throws RemoteException {
		synchronized (this) {

			this.game = new Game(this, this.players.values());
			this.notifyAll();
		}
	}

	public List<String> getPlayerNames() throws RemoteException {
		return this.players.values().stream().map(player -> Try.of(player::getName).get()).toList();
	}

}