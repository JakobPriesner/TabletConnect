package client.modes;

import client.Client;
import client.ClientLogic;
import client.ClientNetwork;
import logic.Network;

import java.awt.image.BufferedImage;

public class StreamScreen extends Mode implements Runnable{

    public boolean running = true;

    @Override
    public void run(){
        while (running){
            sendEventMessage();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public StreamScreen(ClientLogic logic, ClientNetwork network){
        super.logic = logic;
    }

    @Override
    public void sendEventMessage(){
        BufferedImage img = logic.getScreenImage();
        logic.sendPictureToServer(img);
    }

}
