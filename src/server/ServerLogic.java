package server;

import logic.GUI;
import logic.Logic;
import logic.Message;
import logic.Network;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ServerLogic implements Logic {

    private Robot robot;
    private GUI gui;
    private Rectangle screen;
    private MouseEventHandler mvHandler;
    private static final Logger log = Logger.getLogger(Network.class.getName());

    public ServerLogic(){
        try {
            this.robot = new Robot();
            this.gui = new GUI();
            log.info("Gui initialized.");
            this.screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            this.mvHandler = new MouseEventHandler();
            log.info("MouseEventHandler initialized.");
            gui.runVideoStreamHandler();
        } catch (AWTException e) {
            e.printStackTrace(); //toDo: Exception-Handling
        }
    }

    public void processMessage(Message m){
        if (m.getMessageType().equals("invalidMessage")){
            log.warning("Received an invalid Message!");
        } else if (m.getMessageType().equals("initType")){
            if (! (m.getInformation() instanceof String)){
                log.warning("Incorrect message received!");
                gui.criticalErrorMessage(
                        "fataler Fehler!",
                        "Es ist ein fataler Fehler in dem Program aufgetreten. Bitte starte es auf dem Server und Client neu.",
                        "Message type and message information do not match!"
                );
            } else if (m.getInformation().equals("streamWindow")){
                gui.runVideoStreamHandler();
            } else if (m.getInformation().equals("streamWindowAndMouseActions")){
                gui.runVideoStreamHandler();

            }
            if (m.getMessageType().equals("updateFrame")){
                if (! (m.getInformation() instanceof BufferedImage)){
                    log.warning("Incorrect message received!");
                    gui.criticalErrorMessage(
                            "fataler Fehler!",
                            "Es ist ein fataler Fehler in dem Program aufgetreten. Bitte starte es auf dem Server und Client neu.",
                            "Message type and message information do not match!"
                    );
                }
                gui.addImgToVideoStreamHandler((BufferedImage) m.getInformation());
            }

        } else if (m.getMessageType().equals("updateMouseEvent")){
            if (! (m.getInformation() instanceof MouseEvent)){
                log.warning("Incorrect message received!");
                gui.criticalErrorMessage(
                        "fataler Fehler!",
                        "Es ist ein fataler Fehler in dem Program aufgetreten. Bitte starte es auf dem Server und Client neu.",
                        "Message type and message information do not match!"
                );
            }
            addMouseEventToHandler((MouseEvent) m.getInformation());
        }
    }

    public void addMouseEventToHandler(MouseEvent event){
        mvHandler.mouseEvents.add(event);
    }

    public BufferedImage getScreenImage(){
        return robot.createScreenCapture(screen);
    }

    private class MouseEventHandler implements Runnable{

        ArrayList<MouseEvent> mouseEvents;

        public MouseEventHandler(){
            mouseEvents = new ArrayList<>();
        }

        @Override
        public void run() {

        }
    }
}
