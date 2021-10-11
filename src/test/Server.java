package test;

import logic.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        Server s = new Server();
        s.run();
    }

    public void run(){
        try(ServerSocket ss = new ServerSocket(50000);
            Socket conn = ss.accept();
            Scanner sc = new Scanner(System.in)) {
            while (true){
                String input = sc.next();
                sendMessage(conn, new Message(1, input, "null"));
                Message msg = receiveMessage(conn);
                System.out.println(msg.getMessageType());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Socket conn, Message msg) throws IOException {
        OutputStream os = conn.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(msg);
        oos.flush();
    }

    private Message receiveMessage(Socket conn) throws IOException, ClassNotFoundException {
        InputStream is = conn.getInputStream();
        Message msg;
        ObjectInputStream ois = new ObjectInputStream(is);
        msg = (Message) ois.readObject();
        System.out.println("recieved Message");
        return msg;
    }

}
