package client;

import logic.Message;
import logic.Network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientNetwork extends Network implements Runnable{

    final Client client;

    Socket connection;
    String ip;
    ArrayList<Message> messagesToSend;
    private static final Logger log = Logger.getLogger(ClientNetwork.class.getName());
    private boolean running;

    public ClientNetwork(String ip, Client client) {
        super(client);
        log.setLevel(Level.ALL);
        this.client = client;
        this.ip = ip;
    }

    @Override
    public void run(){
        try {
            log.info("Try to connect on " + ip + ":" + PORT);
            connection = new Socket(ip, PORT);
            log.info("Connected to Server on " + ip);
            startNetworkHandler(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Socket closed!");
    }

    public void addMessage(Message msg){
        messagesToSend.add(msg);
    }

    public void recieveActionFromServer(){

    }

}
