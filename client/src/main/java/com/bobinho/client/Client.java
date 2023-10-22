package com.bobinho.client;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

@Slf4j
public class Client {

    public static void main(String[] args) {
        log.info(System.getProperty("java.security.policy"));

        try {
            final Model model = new Model();
            final Controller controller = new Controller(model);
            SwingUtilities.invokeLater(() -> {
                final View view = new View("Cameleon");
                model.linkView(view);
                view.linkController(controller);
            });
        }

		catch (Exception e) {
            log.error("Unexpected client exception", e);
        }
    }

}