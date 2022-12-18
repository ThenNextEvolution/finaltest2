package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

import static com.example.demo.HelloController.*;
import static com.example.demo.chatController.serv;

public class client2 {
    private Socket socket;
    private Socket socket2;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String username;
    public static Message objmes;
    public static ObservableList<Message> client2list = FXCollections.observableArrayList();

    public void closer(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
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

    public client2(Socket socket, String username, Socket socket2){
        try {
            this.socket =socket;
            this.socket2 =socket2;
            this. bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.objectInputStream = new ObjectInputStream(socket2.getInputStream());
            this.objectOutputStream = new ObjectOutputStream(socket2.getOutputStream());
            this.username=username;

        } catch (IOException e) {
            closer(socket,bufferedReader,bufferedWriter);


        }
    }

    public void sendmessage(){
        System.out.println("sendmess");
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Message sen = new Message(3,"",": has connected");

            sen.name=username;
            System.out.println(sen.name);
            objectOutputStream.writeObject(sen);
            System.out.println("mess out");
            objectOutputStream.flush();
            System.out.println("mess out2");



            //Scanner  scanner = new Scanner(se);
//            while (socket.isConnected()){
//                //if(!Objects.equals(se, null)){
//
//                bufferedWriter.write(username+": "+ se);
//                bufferedWriter.newLine();
//                bufferedWriter.flush();;//}
//                System.out.println("done");
//
//
//
//            }



        } catch (Exception e) {
            System.out.println("clse");
            closer(socket,bufferedReader,bufferedWriter);
        }


    }
    public static Text thisone2;
    public void  listen(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgfrom;
                while (socket.isConnected()){
                    try {
                        msgfrom =bufferedReader.readLine();
                        objmes= (Message) objectInputStream.readObject();
                        thisone2= new Text(msgfrom);

                        System.out.println(msgfrom);



                        //System.out.println(msgfrom);
                    } catch (IOException e) {
                        closer(socket,bufferedReader,bufferedWriter);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }) .start();


    }
    public void  listen2() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message messagef;
                System.out.println("listen2");
                while (socket2.isConnected()){
                    //System.out.println("sock 2");
                    try {
                        if (objectInputStream.available()!=0){
                            try {
                                messagef = (Message) objectInputStream.readObject();
                            }
                            catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }


                            System.out.println("badd");
                        //objmes= (Message) objectInputStream.readObject();
                        if(messagef != null ){
                            serv.add((Message) messagef);
                            client2list.add((Message) messagef);
                            System.out.println(serv.get(0).message);
                            System.out.println("received");

                        }}

                        //System.out.println("received");





                        //System.out.println(msgfrom);
                    } catch (IOException e) {
                        closer(socket,bufferedReader,bufferedWriter);
                    }
                }
            }
        }) .start();}
//    public static void  main(String[] args) throws IOException {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("username");
//        //String username= scanner.nextLine();
//        String username="user1";
//        Socket socket1 =new Socket("localhost",9080);
//        Socket socket2 = new Socket("localhost",8080);
//        client2 client =new client2(socket1,username,socket2);
//        client2list.add(new Message(1,"start","client"));
//
//        //client.listen();
//        client.listen2();
//        client.listen();
//       client.sendmessage();
//
//    }

}
