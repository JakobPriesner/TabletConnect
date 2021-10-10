package client;

import logic.Message;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class ClientLogic {

    Robot robot;
    Rectangle screen;

    public ClientLogic(){
        try {
            this.robot = new Robot();
            this.screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        } catch (AWTException e) {
            e.printStackTrace(); //toDo: Exception-Handling
        }
    }

    public BufferedImage getScreenImage(){
        return robot.createScreenCapture(screen);
    }

    public void proceedMessage(Message m){
        if (m.getMessageType().equals("initType")){
            //toDo: Handle different Types
        }
    }

    public static class MyMouseListener implements MouseListener{

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


