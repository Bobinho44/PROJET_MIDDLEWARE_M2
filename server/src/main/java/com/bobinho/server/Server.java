package com.bobinho.server;

import lombok.extern.slf4j.Slf4j;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
@Slf4j
public class Server {

	public static void main(String[] args) {
		log.info("Java RMI server started on port 1099");

		try {
			Registry registry = LocateRegistry.createRegistry(10999);
			registry.rebind("GameManagerService", new GameManager());
		}

		catch(Exception e) {
			log.error("Server exception: " + e);
		}
	}

}