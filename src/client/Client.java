package client;

import client.modes.StreamScreen;
import logic.Message;
import logic.Network;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private static final Logger log = Logger.getLogger(Network.class.getName());
    ClientNetwork network;
    ClientLogic logic;


    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        log.setLevel(Level.ALL);
        client.network = new ClientNetwork("127.0.0.1");
        log.info("ClientNetwork initialized.");
        client.logic = new ClientLogic(client.network);
        log.info("ClientLogic initialized.");

        client.startSystems();
        client.test();
        //client.logic.addMode(new StreamScreen(client.logic, client.network));
    }

    public void test(){
        network.addMessage(new Message(1, "hello", "World"));
    }

    public void receivedMessage(Message m){
        // Simply forward message
        logic.proceedMessage(m);
    }

    private void startSystems(){
        Thread th = new Thread(network, "network");
        th.start();
        log.info("Network started.");
    }

}
