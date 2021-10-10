package server;

import logic.Message;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ServerLogic{

    Robot robot;
    Rectangle screen;

    //toDo: translate screen sizes

    public ServerLogic(){
        try {
            this.robot = new Robot();
            this.screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        } catch (AWTException e) {
            e.printStackTrace(); //toDo: Exception-Handling
        }
    }

    public void processMessage(Message m){
        return;
    }

    public BufferedImage getScreenImage(){
        return robot.createScreenCapture(screen);
    }
}
