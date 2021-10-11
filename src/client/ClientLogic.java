package client;

import client.modes.Mode;
import logic.Logic;
import logic.Message;
import logic.Network;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientLogic implements Logic {

    Robot robot;
    Rectangle screen;
    ArrayList<Mode> modes;
    ClientNetwork network;
    private static final Logger log = Logger.getLogger(Network.class.getName());

    public ClientLogic(ClientNetwork network){
        log.setLevel(Level.ALL);
        try {
            this.robot = new Robot();
            this.screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            this.modes = new ArrayList<>();
            this.network = network;
        } catch (AWTException e) {
            e.printStackTrace(); //toDo: Exception-Handling
        }
    }

    public BufferedImage getScreenImage(){
        return robot.createScreenCapture(screen);
    }

    public void sendPictureToServer(BufferedImage img){
        //toDo: ConvId
        Message msg = new Message.MessageBuilder()
                .withMessageType("updateFrame")
                .withInformation(img)
                .build();
        network.queuePendingMessage(msg);

    }

    public void proceedMessage(Message m){
        if (m.getMessageType().equals("statusUpdate")){
            return;
        }
    }

    public void addMode(Mode m){
        System.out.println("addMode....");
        modes.add(m);
        Thread th = new Thread(m);
        th.start();
        log.info("Mode " + m.getClass().getName() + " added");
    }

    private static class MyMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            System.out.println(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println(e);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            System.out.println(e);
        }
    }

}


