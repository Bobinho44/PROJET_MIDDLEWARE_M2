# Java Middleware Project

## How to run

### Package the project with Maven
- `mvn clean package`

### Start the server
- `java -cp server/target/server-1.0-jar-with-dependencies.jar com.bobinho.server.Server`

### Start the client
- `java -cp client/target/client-1.0-jar-with-dependencies.jar com.bobinho.client.Client`

## Rules

The goal of the game is to color as many squares as possible with your own color by clicking. Players take turns, and they can choose only one white drawer per turn. When a player colors a new drawer, all the adjacent squares that are already colored will change to the player's color.

## Limitation

This project does not allow leaving a room or an ongoing session. It also does not consider the disconnection of the client if they are in a room (which will result in the blocking of other clients in the room).

## Synchronisation

This project implements several synchronization points.
Firstly, for game creation, we need to be able to synchronize all connected players so that they can access the games that other players have created.
We also need to prevent too many players joining the same game. In other words, if several players try to join a game at the same time, only one player should be able to join, and the others should be refused.
and the others must be refused.

Then, once the game has started, both participants must be synchronized to take turns. What's more, a player must not be able to play on a square on a square that has already been played by his opponent.
Finally, it must be possible to synchronize the scores of both players, as well as the end of a game and the possibility of returning to the main menu.

## Synchronization setup

To enable this synchronization, we used the RMI client-server model and Hoare monitors.
So any action requiring synchronization is handled on the server side using the monitors, and the information is then sent back to the clients to update the front end.