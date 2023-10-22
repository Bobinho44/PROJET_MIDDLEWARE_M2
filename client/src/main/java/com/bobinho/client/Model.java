package com.bobinho.client;

import com.bobinho.common.interfaces.GameManagerService;
import com.bobinho.common.interfaces.GameService;
import com.bobinho.common.interfaces.PlayerService;
import com.bobinho.common.interfaces.RoomService;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import javax.swing.SwingUtilities;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.stream.Collectors;

@Slf4j
public class Model {

    public final GameManagerService gameManager;
    private PlayerService player;
    private RoomService room;
    private GameService game;
    private View view;
    private volatile boolean waitPlay = false;

    public Model() throws RemoteException, NotBoundException {
        this.gameManager = (GameManagerService) LocateRegistry.getRegistry(10999).lookup("GameManagerService");
    }

    public void linkView(View view) {
        this.view = view;
    }

    public void register(String name) throws RemoteException {
        this.player = this.gameManager.createPlayer(name);
    }

    public void createRoom(String name) throws RemoteException, InterruptedException {
        this.room = this.gameManager.createRoom(name, this.player);

        log.info("You have created the " + name + " room");

        this.launchGame();
    }

    public boolean joinRoom(RoomService room) throws RemoteException, InterruptedException {

        if (!this.gameManager.joinRoom(room, this.player)) {
            return false;
        }

        log.info("You have joined the " + room.getName() + " room");

        this.room = room;

        this.launchGame();

        return true;
    }

    private void launchGame() throws RemoteException, InterruptedException {

        this.game = this.room.getGame();
        this.view.start(this.room.getPlayerNames());

        log.info("A game is launched...");

        new Thread(() -> {

            try {

                while (true) {

                    if (!waitPlay && this.game.isFinished()) {
                        log.error("END");
                        break;
                    }

                    if (!waitPlay) {

                        this.updateView();
                        log.info("Waiting for your turn...");
                        this.game.awaitPlayerTurn(this.player);

                        this.updateView();

                        if (this.game.isFinished()) {
                            log.error("END");
                            break;
                        }

                        log.info("You can play...");
                        waitPlay = true;
                    }
                }
            }

            catch (RemoteException | InterruptedException e) {
                log.error("Unexpected game exception!", e);
            }

        }).start();
    }

    public void play(int i, int j) {
        SwingUtilities.invokeLater(() -> {

            if (waitPlay) {

                try {

                    log.info("You played in (" + i + " " + j + ")...");
                    this.game.play(i, j);

                    this.updateView();
                    waitPlay = false;
                }

                catch (RemoteException e) {
                    log.error("Unexpected play exception!", e);
                }
            }

            else {
                log.error("You can't play, it's not your turn!");
            }
        });
    }

    public void refreshAvailableRoom() throws RemoteException {
        this.view.refreshAvailableRoom(this.gameManager.getAllRoom().stream()
                .collect(Collectors.toMap(map -> Try.of(map::getName).get(), map -> map)));
    }

    private void updateView() {
        SwingUtilities.invokeLater(() -> {
            try {
                this.view.update(
                        false,
                        this.game.getBoard().getBoard().stream()
                                .map(square -> Try.of(() -> new Square(square.getColor(), square.getCoordinates())).get())
								.toList()
                );
            }

            catch (RemoteException e) {
                log.error("Unexpected view update exception!", e);
            }
        });
    }
}