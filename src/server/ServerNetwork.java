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
    Server server;
    ArrayList<Message> pendingMessages = new ArrayList<>();

    public ServerNetwork(Server server){
        log.setLevel(Level.ALL);
        this.server = server;
    }

    @Override
    public void run(){
        try( ServerSocket ss = new ServerSocket(Network.PORT);
             Socket conn = ss.accept() ) {
            log.info("Connected to Client on " + conn.getInetAddress());
            PendingMessagesHandler pendingMessagesHandler = new PendingMessagesHandler(conn, this);
            ReceiveMessagesHandler receiveMessagesHandler = new ReceiveMessagesHandler(conn, this);
            pendingMessagesHandler.run();
            receiveMessagesHandler.run();
        } catch (IOException e) {
            //toDo: handle Exceptions
            e.printStackTrace();
        }
    }

    private static class PendingMessagesHandler implements Runnable{
        Socket conn;
        ServerNetwork serverNetwork;
        boolean isRunning = true;

        private PendingMessagesHandler(Socket conn, ServerNetwork serverNetwork){
            this.conn = conn;
            this.serverNetwork = serverNetwork;
        }

        @Override
        public void run() {
            while (isRunning){
                // Send messages to client if any are present in pending-ArrayList
                if (!serverNetwork.pendingMessages.isEmpty()){
                    serverNetwork.pendingMessages.forEach(new Consumer<Message>() {
                        @Override
                        public void accept(Message message) {
                            log.info("Send Message to Client..."); //toDo: specify Message Info
                            sendMessage(conn, message);
                        }
                    });;
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
        }

        @Override
        public void run() {
            // transfers received messages to the core
            while (isRunning){
                try{
                    Message message = receiveMessage(conn);
                    log.info("Received Message from Client: "); //toDo: specify MessageInfs
                    serverNetwork.server.receivedMessage(message);
                } catch (IOException e) {
                    log.warning("Couldn't receive MessageObject: " + e.getMessage());
                } catch (ClassNotFoundException e){
                    log.warning("Couldn't receive MessageObject, because MessageObject is invalid!\n"+e.getMessage());
                }

            }
        }
    }

}
