package com.example.demo;

import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import javafx.collections.FXCollections;

public class ClientHand implements Runnable {
    public static ArrayList<ClientHand> clientHands =new ArrayList<>();
    private Socket socket;
    private Socket socket1;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientusername;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    public static ObservableList<Message> serverView = FXCollections.observableArrayList();

    public ClientHand(Socket socket, Socket socket1) throws IOException {
        try {
            this.socket =socket;
            this.socket1=socket1;
            this. bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.objectOutputStream = new ObjectOutputStream((socket1.getOutputStream()));
            this.objectInputStream = new ObjectInputStream(socket1.getInputStream());
            this.clientusername = bufferedReader.readLine();
            clientHands.add(this);
           String ser =  String.format("%s  has entered the chat",clientusername);
            System.out.println(ser);
//
            serverView.add(new Message(0,"server","has entered the chat"));
//            System.out.println("recALL");

            Message join = new Message(0, "","has entered the chat");
            join.name = clientusername;
            System.out.println(join.name);
            System.out.println("send join mess maybe");

            broadcasting("server: "+clientusername+ "  has entered the chat",join);
            System.out.println("sent join mess");
            Message bo = new Message(1,"server","has entered the chat");
            System.out.println("dpone withbroad");
            serverView.add(bo);
            System.out.println("cliethold size"+serverView.size());

            System.out.println("yooooooooooo"+serverView.get(0).message);

        } catch (Exception e) {
            closer(socket,bufferedReader,bufferedWriter);
        }
    }

    @Override
    public void run() {
        messouts();
    String messagefromc;
//    while(socket.isConnected()){
//        try {
//            messagefromc = bufferedReader.readLine();
//            System.out.println("sock");
//            Message mess = (Message) objectInputStream.readObject();
//            broadcasting(messagefromc,mess);
//        } catch (IOException e) {
//            try {
//                closer(socket,bufferedReader,bufferedWriter);
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//            break;
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
    }
     public void messouts(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket1.isConnected()){
                    try {

                        System.out.println("sock");
                        Message mess = (Message) objectInputStream.readObject();
                        String messagefromc ="";
                        broadcasting(messagefromc,mess);
                    } catch (IOException e) {
                        try {
                            closer(socket,bufferedReader,bufferedWriter);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
     }


    public void broadcasting(String outgoing_message, Message message) throws IOException {
//        for (ClientHand clientHand:clientHands) {
//            try {
//                if(!clientHand.clientusername.equals(clientusername)){
//                    clientHand.bufferedWriter.write(outgoing_message);
//                    clientHand.bufferedWriter.newLine();
//                    clientHand.bufferedWriter.flush();
//
//
//                }
//            } catch (Exception e) {
//                closer(socket,bufferedReader,bufferedWriter);
//            }}
//            System.out.println("serv loop");

        for (ClientHand clientHand2:clientHands) {
            System.out.println("sending");
            //clientHand2.objectOutputStream.writeObject(new Message(0,"server"," connected"));
            clientHand2.objectOutputStream.flush();
            //System.out.println("flushed");

                try { String nam = clientusername;
//
                    serverView.add(message);
                    System.out.println(serverView.size()+"size");
                    clientHand2.objectOutputStream.writeObject(message);
                    //clientHand2.objectOutputStream.writeObject(bo);
                    clientHand2.objectOutputStream.flush();System.out.println("flushed");}
                catch (IOException e) {
                    throw new RuntimeException(e);
                }

        }
        System.out.println("serv loop end");
    }
    public void removeCleint() throws IOException {
        clientHands.remove(this);
        Message left = new Message(0, "","has left the chat");
        left.name = clientusername;
        broadcasting("server: "+clientusername+ " has left the chat",left);
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
