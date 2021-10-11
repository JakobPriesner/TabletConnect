package client;

import client.modes.StreamScreen;
import logic.Core;
import logic.Message;
import logic.Network;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Core {

    private static final Logger log = Logger.getLogger(Network.class.getName());
    ClientNetwork network;
    ClientLogic logic;


    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        log.setLevel(Level.ALL);
        client.network = new ClientNetwork("192.168.178.23", client);
        log.info("ClientNetwork initialized.");
        client.logic = new ClientLogic(client.network);
        log.info("ClientLogic initialized.");
        log.info("All Systems successfully initialized.");
        client.startSystems();
        log.info("Systems successfully started.");
        client.logic.addMode(new StreamScreen(client.logic, client.network));
        log.info("Mode added");
    }

    private void startSystems(){
        Thread th = new Thread(network);
        th.start();
        log.info("ClientNetwork started.");
    }

    @Override
    public void receivedMessage(Message m){
        // Simply forward message
        logic.proceedMessage(m);
    }

}
