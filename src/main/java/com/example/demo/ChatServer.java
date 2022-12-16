package com.example.demo;

import java.net.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the chat client program.
 * Type 'bye' to terminte the program.
 *
 * @author www.codejava.net
 */
public class ChatServer{
    private int port;
    private Set<String> userNames = new HashSet<>();
    private ServerSocket serverSocket;
    private ServerSocket serverSocket2;

    //private Set<UserThread> userThreads = new HashSet<>();

    public ChatServer(ServerSocket serverSocket, ServerSocket serverSocket2) throws IOException {
        this.serverSocket = serverSocket;
        this.serverSocket2=serverSocket2;
    }
    public void closeHand(){
        try {
            if(serverSocket!=null){
                serverSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start()  {
        try {
            System.out.println("Chat Server is listening on port " + serverSocket.getLocalPort());

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                Socket socket1 = serverSocket2.accept();
                System.out.println("New user connected");
                ClientHand clientHand = new ClientHand(socket,socket1);
                Thread thread = new Thread(clientHand);
                thread.start();

        }

    }
        catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }

}
public static void main(String[] args) throws IOException {
    //int port = Integer.parseInt(args[0]);

    ServerSocket serverSocket1= new ServerSocket(9080);
    ServerSocket serverSocket2= new ServerSocket(8080);
    ChatServer  server = new ChatServer(serverSocket1,serverSocket2);

    server.start();
}

}
