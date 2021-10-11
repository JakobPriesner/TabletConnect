package client;

import client.modes.StreamScreen;
import logic.Message;
import logic.Network;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientNetwork extends Network implements Runnable{

    Socket connection;
    String ip;
    ArrayList<Message> messagesToSend;
    private static final Logger log = Logger.getLogger(ClientNetwork.class.getName());

    public ClientNetwork(String ip){
        log.setLevel(Level.ALL);
        this.ip = ip;
        this.messagesToSend = new ArrayList<>();
    }

    @Override
    public void run(){
        try(Socket conn = new Socket(ip, PORT) ) {
            connection = conn;
            log.info("Connected to Server");
            start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMessage(Message msg){
        messagesToSend.add(msg);
    }

    public void start(){
        log.info("NetworkThread started.");
        while (true){
            if (!(messagesToSend.isEmpty())){
                messagesToSend.forEach(message -> sendMessage(connection, message));
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void recieveActionFromServer(){

    }

}
