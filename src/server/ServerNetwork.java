package server;
import logic.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerNetwork extends Network implements Runnable{

    private static final Logger log = Logger.getLogger(Network.class.getName());
    public boolean running = true;
    Server server;
    private PendingMessagesHandler pendingMessagesHandler;
    private ReceiveMessagesHandler receiveMessagesHandler;
    ArrayList<Message> pendingMessages = new ArrayList<>();

    public ServerNetwork(Server server){
        log.setLevel(Level.ALL);
        this.server = server;
    }

    @Override
    public void run(){
        while (running){
            try( ServerSocket ss = new ServerSocket(Network.PORT);
                 Socket conn = ss.accept() ) {
                log.info("Connected to Client on " + conn.getInetAddress());
                pendingMessagesHandler = new PendingMessagesHandler(conn, this);
                receiveMessagesHandler = new ReceiveMessagesHandler(conn, this);
                Thread pendingMessagesHandlerThread = new Thread(pendingMessagesHandler, "pendingMessagesHandler");
                Thread receiveMessagesHandlerThread = new Thread(receiveMessagesHandler, "receiveMessagesHandler");
                //pendingMessagesHandlerThread.start();
                receiveMessagesHandlerThread.start();
                //pendingMessagesHandlerThread.join();
                receiveMessagesHandlerThread.join();


            } catch (IOException | InterruptedException e) {
                //toDo: handle Exceptions
                e.printStackTrace();
                log.warning(e.getMessage());
            }
            log.info("Socket closed!");
        }
    }

    private static class PendingMessagesHandler implements Runnable{
        Socket conn;
        ServerNetwork serverNetwork;
        boolean isRunning = true;

        private PendingMessagesHandler(Socket conn, ServerNetwork serverNetwork){
            this.conn = conn;
            this.serverNetwork = serverNetwork;
            log.info("PendingMessagesHandler initialized.");
        }

        @Override
        public void run() {
            log.info("PendingMessagesHandler running.");
            while (isRunning){
                // Send messages to client if any are present in pending-ArrayList
                if (!serverNetwork.pendingMessages.isEmpty()){
                    serverNetwork.pendingMessages.forEach(message -> {
                        log.info("Send Message to Client..."); //toDo: specify Message Info
                        sendMessage(conn, message);
                    });
                    serverNetwork.pendingMessages.clear();
                }
            }
        }
    }

    private static class ReceiveMessagesHandler implements Runnable{
        Socket conn;
        ServerNetwork serverNetwork;
        boolean isRunning = true;

        private ReceiveMessagesHandler(Socket conn, ServerNetwork serverNetwork){
            this.conn = conn;
            this.serverNetwork = serverNetwork;
            log.info("ReceiveMessagesHandler initialized.");
        }

        @Override
        public void run() {
            log.info("ReceiveMessagesHandler running.");
            // transfers received messages to the core
            while (isRunning){
                try {
                    Message message = receiveMessage(conn);
                    log.info("Received Message from Client: "); //toDo: specify MessageInfs
                    serverNetwork.server.receivedMessage(message);
                } catch (Exception e){
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

}
