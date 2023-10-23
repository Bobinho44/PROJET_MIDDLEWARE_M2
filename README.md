# Java Middleware Project

## How to run

### Package the project with Maven
- `mvn clean package`

### Start the server
- `java -cp server/target/server-1.0-jar-with-dependencies.jar com.bobinho.server.Server`

### Start the client
- `java -cp client/target/client-1.0-jar-with-dependencies.jar com.bobinho.client.Client`

### Rules

The goal of the game is to color as many squares as possible with your own color by clicking. Players take turns, and they can choose only one white drawer per turn. When a player colors a new drawer, all the adjacent squares that are already colored will change to the player's color.

### Limitation

This project does not allow leaving a room or an ongoing session. It also does not consider the disconnection of the client if they are in a room (which will result in the blocking of other clients in the room).