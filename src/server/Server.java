package server;

import logic.GUI;
import logic.Message;

import java.awt.image.BufferedImage;

public class Server {

    ServerNetwork network;
    ServerLogic logic;
    GUI gui;

    public static void main(String[] args) {
        Server server = new Server();
        server.network = new ServerNetwork(server);
        server.logic = new ServerLogic();
        server.gui = new GUI();
    }

    public void receivedMessage(Message m){
        if (m.getMessageType().equals("updateFrame")){
            gui.videoStreamWrapper((BufferedImage) m.getInformation());
        }
    }

    public void changeMode(){

    }

}
