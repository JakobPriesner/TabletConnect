package logic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class GUI {

    private final VideoStreamHandler vsHandler;

    public GUI(){
        vsHandler = new VideoStreamHandler();
    }

    public static void main(String[] args) {
        VideoStreamHandler vsh = new VideoStreamHandler();
        vsh.run();
    }

    public void criticalErrorMessage(String title, String message, String errorDescription){

    }

    public void runVideoStreamHandler(){
        vsHandler.run();
    }

    public void addImgToVideoStreamHandler(BufferedImage img){
        vsHandler.pendingImages.add(img);
    }

    private static class StartWindow implements Runnable{
        JFrame mainWindow;

        @Override
        public void run(){
            createWindow();
            mainWindow.setVisible(true);
        }

        private void createWindow(){
            mainWindow = new JFrame("TabletConnect - by Jakob Priesner");
            mainWindow.setDefaultCloseOperation(closeProgram());
            mainWindow.setLocationRelativeTo(null);
        }

        private int closeProgram(){

            return WindowConstants.EXIT_ON_CLOSE;
        }
    }

    private static class VideoStreamHandler implements Runnable{

        JFrame streamWindow;
        JLabel jLabel;
        ArrayList<BufferedImage> pendingImages = new ArrayList<>();

        public VideoStreamHandler(){
        }

        @Override
        public void run() {
            createWindow();
            streamWindow.setVisible(true);
            if (!pendingImages.isEmpty()){
                pendingImages.forEach(this::updateImage);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // don't care
                }
            }
        }

        private void createWindow(){
            streamWindow = new JFrame("TabletConnect - View");
            streamWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            jLabel = new JLabel();
            String filename = "src/img/waiting_for_picture.jpeg";
            try{
                BufferedImage image = ImageIO.read(new File(filename));
                ImageIcon icon = new ImageIcon(image);
                jLabel.setIcon(icon);
            } catch (Exception e){
                e.printStackTrace();
            }
            streamWindow.getContentPane().add(jLabel, BorderLayout.CENTER);
            streamWindow.pack();
            streamWindow.setLocationRelativeTo(null);
        }

        private void updateImage(BufferedImage img){
            jLabel.setIcon(new ImageIcon(img));
        }
    }

}
