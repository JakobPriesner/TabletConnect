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
            System.out.println("sending image");
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
        super.network = network;
    }

    @Override
    public void sendEventMessage(){
        BufferedImage img = logic.getScreenImage();
        network.sendPictureToServer(img);
    }

}
