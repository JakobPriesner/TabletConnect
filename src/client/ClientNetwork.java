package client;

import logic.Message;
import logic.Network;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientNetwork extends Network{

    Socket connection;
    private static final Logger log = Logger.getLogger(ClientNetwork.class.getName());

    public ClientNetwork(){
        log.setLevel(Level.ALL);
    }

    public void run(String ip){
        try(Socket conn = new Socket(ip, PORT) ) {
            connection = conn;
            log.info("Connected to Server");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMouseUpdataToServer(int x, int y, boolean isPressed){
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
