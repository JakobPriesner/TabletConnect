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
        try(Scanner sc = new Scanner(System.in)) {
            String ip = sc.next();
            client.network = new ClientNetwork(ip);
        }
        log.info("ClientNetwork initialized.");
        client.logic = new ClientLogic();
        log.info("ClientLogic initialized.");
        client.startSystems();
        Thread.sleep(1000);
        client.logic.modes.add(new StreamScreen(client.logic, client.network));
    }

    public void receivedMessage(Message m){
        // Simply forward message
        logic.proceedMessage(m);
    }

    private void startSystems(){
        network.run();
        log.info("Network started.");
    }



}
