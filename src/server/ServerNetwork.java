package server;
import logic.*;

import javax.xml.stream.FactoryConfigurationError;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerNetwork extends Network implements Runnable{

    private static final Logger log = Logger.getLogger(Network.class.getName());
    private boolean running;
    Server server;

    public ServerNetwork(Server server){
        super(server);
        log.setLevel(Level.ALL);
        this.server = server;
    }

    @Override
    public void stopNetwork(){
        super.stopNetwork();
        this.running = false;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                ServerSocket ss = new ServerSocket(Network.PORT);
                Socket conn = ss.accept();
                log.info("Connected to Client on " + conn.getInetAddress());
                startNetworkHandler(conn);
            } catch (IOException e) {
                //toDo: handle Exceptions
                e.printStackTrace();
            }
            log.info("Socket closed!");
        }
    }

}
