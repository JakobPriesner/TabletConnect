package test;

import logic.Core;
import logic.Message;
import logic.Network;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client extends Network {
    public Client(Core core) {
        super(core);
    }
/*
    public Client(){
        super();
    }

    public static void main(String[] args) {
        Client c = new Client();
        c.run();
    }

    public void run(){
        try(Socket conn = new Socket("127.0.0.1", 50000);
            Scanner sc = new Scanner(System.in)){
            while (true) {
                Message msg = receiveMessage(conn);
                System.out.println(msg.getMessageType());
                String input = sc.next();
                queuePendingMessage(new Message(input, ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
}
