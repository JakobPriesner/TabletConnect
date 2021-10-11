package test;

import logic.Message;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Client c = new Client();
        c.run();
    }

    public void run(){
        while (true){
            try(Socket conn = new Socket("127.0.0.1", 50000);
                Scanner sc = new Scanner(System.in)){
                Message msg = receiveMessage(conn);
                System.out.println(msg.getMessageType());
                String input = sc.next();
                sendMessage(conn, new Message(1, input, "null"));

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }
        }

    }

    private void sendMessage(Socket conn, Message msg) throws IOException {
        try(OutputStream os = conn.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os)){
            oos.writeObject(msg);
            oos.flush();
        }

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
