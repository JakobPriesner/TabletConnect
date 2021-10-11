package server;

import logic.Core;
import logic.GUI;
import logic.Message;
import logic.Network;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Core {

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
        log.info("Server logic initialized.");
        log.info("All Systems successfully initialized.");
        server.startSystems();
        log.info("Systems successfully started.");
    }

    private void startSystems(){
        Thread th = new Thread(network);
        th.start();
        log.info("ServerNetwork started.");
    }

    @Override
    public void receivedMessage(Message m){
        // Simply forward message
        logic.processMessage(m);
    }

}
