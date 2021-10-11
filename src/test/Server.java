package test;

import logic.Core;
import logic.Message;
import logic.Network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server extends Network {
    public Server(Core core) {
        super(core);
    }
/*
    public Server(Socket socket) {
        super();

    }

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
                queuePendingMessage(new Message(input, ""));
                Message msg = receiveMessage(conn);
                System.out.println(msg.getMessageType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
}
