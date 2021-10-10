package client;

import client.modes.StreamScreen;
import logic.Message;
import logic.Network;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientNetwork extends Network implements Runnable{

    Socket connection;
    String ip;
    private static final Logger log = Logger.getLogger(ClientNetwork.class.getName());

    public ClientNetwork(String ip){
        log.setLevel(Level.ALL);
        this.ip = ip;
    }

    @Override
    public void run(){
        try(Socket conn = new Socket(ip, PORT) ) {
            connection = conn;
            log.info("Connected to Server");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPictureToServer(BufferedImage img){
        //toDo: ConvId
        Message msg = new Message.MessageBuilder()
                .withConvID(1)
                .withMessageType("updateFrame")
                .withInformation(img)
                .build();
        sendMessage(connection, msg);
    }

    public void recieveActionFromServer(){

    }



}
