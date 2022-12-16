package com.example.demo;

import javafx.scene.control.ListView;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHand implements Runnable {
    public static ArrayList<ClientHand> clientHands =new ArrayList<>();
    private Socket socket;
    private Socket socket1;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientusername;
    private ObjectOutputStream objectOutputStream;
    public static ListView<Message> serverView;

    public ClientHand(Socket socket, Socket socket1) throws IOException {
        try {
            this.socket =socket;
            this.socket1=socket1;
            this. bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.objectOutputStream = new ObjectOutputStream((socket1.getOutputStream()));
            this.clientusername = bufferedReader.readLine();
            clientHands.add(this);
//            String ser =  String.format("%s  has entered the chat",clientusername);
//
//            serverView.getItems().add(new Message(0,"server","has entered the chat"));
//            System.out.println("recALL");
            broadcastmessage("server: "+clientusername+ "  has entered the chat");
            Message bo = new Message(1,"server","has entered the chat");
            serverView.getItems().add(bo);
            //System.out.println(serverView.getItems().get(0).name);

        } catch (Exception e) {
            closer(socket,bufferedReader,bufferedWriter);
        }
    }

    @Override
    public void run() {
    String messagefromc;
    while(socket.isConnected()){
        try {
            messagefromc = bufferedReader.readLine();
            broadcastmessage(messagefromc);
        } catch (IOException e) {
            try {
                closer(socket,bufferedReader,bufferedWriter);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            break;
        }
    }
    }
    public void broadcastmessage(String messageout) throws IOException {
        for (ClientHand clientHand:clientHands) {
            try {
                if(!clientHand.clientusername.equals(clientusername)){
                    clientHand.bufferedWriter.write(messageout);
                    clientHand.bufferedWriter.newLine();
                    clientHand.bufferedWriter.flush();


                }
            } catch (Exception e) {
                closer(socket,bufferedReader,bufferedWriter);
            }}
            System.out.println("serv loop");

        for (ClientHand clientHand2:clientHands) {
            System.out.println("sendding");
            clientHand2.objectOutputStream.writeObject(new Message(0,"server"," connected"));
            clientHand2.objectOutputStream.flush();

                try { String nam = clientusername;
                    Message bo = new Message(1,"","");
                    bo.name=clientusername;
                    bo.message=messageout;
                    serverView.getItems().add(bo);
                    clientHand2.objectOutputStream.writeObject(bo);
                    clientHand2.objectOutputStream.flush();}
                catch (IOException e) {
                    throw new RuntimeException(e);
                }

        }
    }
    public void removeCleint() throws IOException {
        clientHands.remove(this);
        broadcastmessage("server: "+clientusername+ " has left the chat");
    }
    public void closer(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) throws IOException {
        removeCleint();
        try {
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(bufferedWriter!=null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
