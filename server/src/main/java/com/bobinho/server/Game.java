package com.bobinho.server;

import com.bobinho.common.interfaces.BoardService;
import com.bobinho.common.interfaces.EColor;
import com.bobinho.common.interfaces.GameService;
import com.bobinho.common.interfaces.PlayerService;
import com.bobinho.common.interfaces.RoomService;
import com.bobinho.common.utils.ConfigUtils;
import com.bobinho.common.utils.PointUtils;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Slf4j
public class Game extends UnicastRemoteObject implements GameService {

    private final RoomService room;
    private final List<PlayerService> players;
    private final BoardService board;
    private PlayerService currentPlayer;

    public Game(RoomService room, Collection<PlayerService> players) throws RemoteException {
        super();

        log.info("Game as {} players", players.size());

        this.room = room;
        this.players = new ArrayList<>(players);
        this.currentPlayer = this.chooseNextPlayer();
        this.board = new Board();
    }

    @Override
    public void awaitPlayerTurn(PlayerService player) throws RemoteException, InterruptedException {
        synchronized(this.room) {

            while (!this.currentPlayer.equals(player)) {
                this.room.wait();
            }

        }
    }

    @Override
    public boolean isFinished() throws RemoteException {
        return this.players.size() < 2 || this.board.getBoard().stream().allMatch(square -> Try.of(square::getColor).getOrElse(EColor.WHITE) != EColor.WHITE);
    }

    @Override
    public void play(int i, int j) throws RemoteException {
        synchronized (this.room) {

            EColor color = EColor.getColorFromInt(this.players.indexOf(this.currentPlayer));

            IntStream.range(0, 9)
                    .mapToObj(x -> new int[]{i + (x / 3 - 1), j + (x % 3 - 1)})
                    .filter(coords -> PointUtils.isValidIndex(coords[0], coords[1], ConfigUtils.BOARD_LENGTH, ConfigUtils.BOARD_LENGTH))
                    .map(coords -> Try.of(() -> this.board.getBoard().get(coords[1] * ConfigUtils.BOARD_LENGTH + coords[0])).get())
                    .filter(square -> Try.of(() -> square.getColor() != EColor.WHITE || (square.getX() == i && square.getY() == j)).getOrElse(false))
                    .forEach(square -> Try.run(() -> square.setColor(color)));

            this.currentPlayer = chooseNextPlayer();

            this.room.notifyAll();
        }
    }

    @Override
    public BoardService getBoard() throws RemoteException {
        return this.board;
    }

    private PlayerService chooseNextPlayer() {

        if (this.currentPlayer == null) {
            return this.players.get(new Random().nextInt(this.players.size()));
        }

        else {
            return this.players.stream()
                    .filter(player -> !player.equals(this.currentPlayer))
                    .findFirst()
                    .orElseThrow();
        }
    }

}