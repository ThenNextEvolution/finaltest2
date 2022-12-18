package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public Pane back;
    public VBox vbox;
    @FXML
    public ListView<Message> serv = new ListView<>();
    public ListView<Message> ori;
    @FXML
   private ListView<Message> listView;
   @FXML
   private ListView<String> listViews;
    @FXML
    private Label welcomeText;
    @FXML
    private  TextFlow mes_view=new TextFlow();
    @FXML
    private TextArea inmes;
    @FXML
    private Stage room;
    @FXML
    private Button Send;
    public static String se;


    @FXML
    protected void onHelloButtonClick() throws IOException {
        welcomeText.setText("Welcome to JavaFX Application!");
        switchrooms();
    }

    @FXML
    private MediaView mid;

    @FXML private Button play,pause,reset;

    private  File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    private ListView<Message> listCell;
    public static  ObservableList<Message> mes =
            FXCollections.observableArrayList();
    public static final ObservableList<String> names =
            FXCollections.observableArrayList();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("in");


        //mid.setMediaPlayer(mediaPlayer);
        //autoserv();
        ori.getItems().addAll(mes);





    }
    public void makemes(Message me){
        mes.add(me);
    }
    public void autoserv(){
        Message message1 = new Message(1,"Public","get em");
        ListCell<Message> cel = new ListCell<>();
        cel.startEdit();
        cel.setText("gang");
        //cel.update
        cel.commitEdit(message1);
        mes.add(message1);
        ListCell<Message> kap = new ListCell<>();
        kap.setText("anng");
        // mes.add(kap);
        names.addAll( "Adam", "Alex", "Alfred", "Albert");
        listViews.getItems().addAll(names);

        //cel.updateListView(cel.getListView());
        //listView.setItems(mes);
        //listView.refresh();
        System.out.println(serv.getItems().size()+"hh");
        if (serv.getItems().size()!=0){
            ori.getItems().addAll(mes);
        serv.getItems().addAll(mes);}

    }



    public void switchrooms() throws IOException {
        room = (Stage) Stage.getWindows().get(0);
        System.out.println("win");


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("chatview.fxml"));

        Scene scene1 = new Scene(fxmlLoader.load(), 600, 600);

        System.out.println("win2");

        System.out.println("win3");
        scene1.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        System.out.println("win4");


        room.setScene((scene1));
        System.out.println("win5");
        room.setTitle("Chat");

        //makeconn();
        room.show();


        Stage newWindow = new Stage();
        newWindow.setTitle("New chat");
//Create view from FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chatview.fxml"));
//Set view in window
        newWindow.setScene(new Scene(loader.load()));
//Launch
        newWindow.show();


    }
    protected void makeconn() throws IOException {
        Message message = new Message(2,"mary","hope this works");
        Socket socketc =new Socket("localhost",9080);
        Socket socket2 =new Socket("localhost",8080);
        client2 me = new client2(socketc,"hold", socket2);
    }

    @FXML
    protected void sendmessage(ActionEvent event) throws IOException {
        se = inmes.getText();
        inmes.setText("");






    }
    public static Text holddd;

    public  void print(){
        Text hold = new Text("john");
        mes_view.getChildren().add(hold);
        //System.out.println(mes_view.getChildren().get(0));

    }



}

