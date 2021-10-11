package logic;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

public abstract class Network {

    private static final Logger log = Logger.getLogger(Network.class.getName());
    private final ArrayList<Message> pendingMessages;
    private final Core core;
    private ReceiveMessagesHandler receiveMessagesHandler;
    private PendingMessagesHandler pendingMessagesHandler;
    private Socket connection;
    public static final int PORT = 55555;

    public Network(Core core){
        this.core = core;
        this.pendingMessages = new ArrayList<>();
        log.setLevel(Level.ALL);
    }

    public void startNetworkHandler(Socket connection){
        this.connection = connection;
        receiveMessagesHandler = new ReceiveMessagesHandler(this.core, this.connection);
        pendingMessagesHandler = new PendingMessagesHandler(this.connection, this);
        Thread receiveMessagesHandlerThread = new Thread(receiveMessagesHandler);
        Thread pendingMessagesHandlerThread = new Thread(pendingMessagesHandler);
        receiveMessagesHandlerThread.start();
        pendingMessagesHandlerThread.start();
    }

    public void stopNetworkHandler(){
        receiveMessagesHandler.running = false;
        pendingMessagesHandler.running = false;
    }

    public void queuePendingMessage(Message msg){
        pendingMessages.add(msg);
    }

    public void stopNetwork(){
        stopNetworkHandler();
    }

    private void sendMessage(Message msg){
        OutputStream os = null;
        try {
            os = this.connection.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(msg);
            oos.flush();
            log.info("Message sent.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Message receiveMessage(Socket conn){
        try {
            InputStream is = conn.getInputStream();
            Message msg;
            ObjectInputStream ois = new ObjectInputStream(is);
            msg = (Message) ois.readObject();
            log.info("Message received.");
            return msg;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Message("invalidMessage", "");
    }

    private static class ReceiveMessagesHandler implements Runnable{

        Core core;
        Socket conn;
        boolean running;

        public ReceiveMessagesHandler(Core core, Socket conn){
            this.core = core;
            this.conn = conn;
        }

        @Override
        public void run() {
            this.running = true;
            while (running){
                Message msg = receiveMessage(conn);
                core.receivedMessage(msg);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class PendingMessagesHandler implements Runnable{

        Socket conn;
        Network network;
        boolean running;

        public PendingMessagesHandler(Socket conn, Network network){
            this.conn = conn;
            this.network = network;
        }

        @Override
        public void run() {
            running = true;
            while (running){
                if (!(network.pendingMessages.isEmpty())){
                    Message msg = network.pendingMessages.remove(0);
                    network.sendMessage(msg);
                } else {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}