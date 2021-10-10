package client.modes;

import client.ClientLogic;
import client.ClientNetwork;

public abstract class Mode implements Runnable{

    ClientNetwork network;
    ClientLogic logic;
    boolean intervallRunning;

    public void startIntervall(int intervall) {
        intervallRunning = true;
        while (intervallRunning){
            sendEventMessage();
            try {
                Thread.sleep(intervall);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void sendEventMessage();


}
