package client;

import logic.Network;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private static final Logger log = Logger.getLogger(Network.class.getName());
    ClientNetwork network;
    ClientLogic logic;


    public static void main(String[] args) {
        Client client = new Client();
        log.setLevel(Level.ALL);
        client.network = new ClientNetwork();
        log.info("ClientNetwork started.");
        client.logic = new ClientLogic();
        log.info("ClientLogic started.");

    }

    private static class VideoStreamHandler implements Runnable{

        boolean streaming = true;
        ClientLogic logic;
        ClientNetwork network;
        String mode;

        private VideoStreamHandler(ClientLogic cl){
            this.logic = cl;
        }

        @Override
        public void run() {
            while (streaming){
                BufferedImage img = logic.getScreenImage();
                network.sendPictureToServer(img);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
