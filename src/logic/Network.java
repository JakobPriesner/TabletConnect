package logic;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.logging.Level;

public abstract class Network {

    private static final Logger log = Logger.getLogger(Network.class.getName());
    public static final int PORT = 50000;

    public Network(){
        log.setLevel(Level.ALL);
    }

    public static void sendMessage(Socket conn, Message m){
        try(OutputStream os = conn.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os)){
            oos.writeObject(m);
            oos.flush();
        } catch (IOException e) {
            log.warning("Couldn't sent MessageObject: " + e.getMessage());
        }
    }

    public static Message receiveMessage(Socket conn) throws IOException, ClassNotFoundException {
        Message m;
        InputStream is = conn.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        m = (Message) ois.readObject();
        log.info("Received MessageObject: "); //toDo: print MessageInfo
        return m;
    }

}