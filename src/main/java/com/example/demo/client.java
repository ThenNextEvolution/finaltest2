package com.example.demo;

import javafx.scene.text.Text;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private ObjectInputStream objectInputStream;

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

    public client(Socket socket, String username, Socket socket2){
        try {
            this.socket =socket;
            this. bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username=username;
            this.objectInputStream = new ObjectInputStream(socket2.getInputStream());

        } catch (IOException e) {
            closer(socket,bufferedReader,bufferedWriter);


        }
    }

    public void sendmessage(){
            try {
                    bufferedWriter.write(username);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                Scanner  scanner = new Scanner(System.in);
                while (socket.isConnected()){
                    String message = scanner.nextLine();
                    bufferedWriter.write(username+": "+ message);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();;


                }



            } catch (Exception e) {
                closer(socket,bufferedReader,bufferedWriter);
            }


    }
    public static Text thisone;
    public void  listen(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgfrom;
                while (socket.isConnected()){
                    try {
                        msgfrom =bufferedReader.readLine();
                        thisone= new Text(msgfrom);



                        if (msgfrom!=null){
                        System.out.println(msgfrom);}
                    } catch (IOException e) {
                        closer(socket,bufferedReader,bufferedWriter);
                    }
                }
            }
        }) .start();




    }
    public static void  main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("username");
        String username= scanner.nextLine();
        //String username="user1";
        Socket socket1 =new Socket("localhost",9080);
        Socket socket2 =new Socket("localhost",8080);
        client client =new client(socket1,username,socket2);
        client.listen();
        client.sendmessage();
    }

}
