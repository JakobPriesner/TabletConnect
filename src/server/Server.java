package server;

import logic.GUI;
import logic.Message;
import logic.Network;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    ServerNetwork network;
    ServerLogic logic;
    GUI gui;
    private static final Logger log = Logger.getLogger(Network.class.getName());

    public static void main(String[] args) {
        log.setLevel(Level.ALL);
        Server server = new Server();
        log.info("Server initialized.");
        server.network = new ServerNetwork(server);
        log.info("ServerNetwork initialized.");
        server.logic = new ServerLogic();
        server.startSystems();
    }

    private void startSystems(){
        network.run();
        log.info("ServerNetwork started.");
    }

    public void receivedMessage(Message m){
        // Simply forward message
        logic.processMessage(m);
    }

}
