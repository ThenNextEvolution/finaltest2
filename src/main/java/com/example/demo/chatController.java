package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//import static com.example.demo.ClientHand.serverView;
import static com.example.demo.ClientHand.serverView;
import static com.example.demo.client2.client2list;
import static com.example.demo.client2.objmes;

public class chatController implements Initializable {
    public Pane back;
    //@FXML
    public VBox vbox = new VBox();
    public ScrollPane scrool;
    private List<Label> messages = new ArrayList<>();




    @FXML
    private ListView<Message> listView;

    @FXML
    private ListView<String> listViews;


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
    protected void onHelloButtonClick() {

        print();
    }

    @FXML

    public static  ObservableList<Message> mes =
            FXCollections.observableArrayList();
    public static final ObservableList<String> names =
            FXCollections.observableArrayList();
    public static ObservableList<Message> serv =
            FXCollections.observableArrayList();
    public static ArrayList<Message> roomList ;
    @FXML
    private ListView<Message> listView2;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            makeconn();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("in");
        System.out.println("this far");
        listView2.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> messageListView) {
                return new ListCell<>(){
                    @Override
                    public void updateItem(Message message, boolean empty) {
                        super.updateItem(message, empty);
                        if (empty || message == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            //setText(message.name + "/" + message.message);
                            GridPane grid = new GridPane();


                            grid.getStylesheets().add("com/example/demo/style.css");
                            grid.getStyleClass().add("grid");
                            grid.setHgap(10);
                            grid.setVgap(4);
                            grid.setPadding(new Insets(0,10,0,10));

                            Label icon = new Label();
                            Image img = new Image("C:\\Users\\eyita\\IdeaProjects\\demo\\src\\imgs\\cool-neon-blue-profile-picture-u9y9ydo971k9mdcf.jpg");
                            ImageView view = new ImageView(img);
                            view.setFitHeight(40);
                            view.setPreserveRatio(true);
                            icon.setGraphic(view);
                            grid.add(icon,0,0,1,2);



                            Label mess= new Label();
                            mess.setText(message.name);
                            setGraphic(grid);
                            mess.getStyleClass().add("name");
                            grid.add(mess,1,0,1,1);

                            Label sub= new Label();
                            sub.setText(message.message);
                            setGraphic(grid);
                            sub.getStyleClass().add("mes");
                            grid.add(sub,1,1);
                        }
                    }
                };
            }


        });



        scrool.setContent(vbox);
        vbox.getStyleClass().add("chatbox");
        System.out.println("this far");

        listView.getItems().add(new Message(2,"jam","test message 1"));
        listView2.getItems().add(new Message(3,"ben","test message 2"));
        listView2.getItems().addAll(serv);
        listView2.getItems().addAll(serverView);
        //listView.getItems().add(new Message(2,"ken","test message 3"));
        //listView2.getItems().add(objmes);
        //listView.getItems().addAll(serv);
        //roomList.add(new Message(0,"test","testing"));
       // listView.getItems().addAll(serverView.getItems());


       // vbox.getChildren().addAll(listView);
        vbox.getChildren().addAll(listView2);
       // vbox.getChildren().add(new ListCell<>());




        listView.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> messageListView) {
                return new ListCell<>(){
                    @Override
                    public void updateItem(Message message, boolean empty) {
                        super.updateItem(message, empty);
                        if (empty || message == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            //setText(message.name + "/" + message.message);
                            GridPane grid = new GridPane();


                            grid.getStylesheets().add("com/example/demo/style.css");
                            grid.getStyleClass().add("grid");
                            grid.setHgap(10);
                            grid.setVgap(4);
                            grid.setPadding(new Insets(0,10,0,10));

                            Label icon = new Label();
                            Image img = new Image("C:\\Users\\eyita\\IdeaProjects\\demo\\src\\imgs\\cool-neon-blue-profile-picture-u9y9ydo971k9mdcf.jpg");
                            ImageView view = new ImageView(img);
                            view.setFitHeight(40);
                            view.setPreserveRatio(true);
                            icon.setGraphic(view);
                            grid.add(icon,0,0,1,2);



                            Label mess= new Label();
                            mess.setText(message.name);
                            setGraphic(grid);
                            mess.getStyleClass().add("name");
                            grid.add(mess,1,0,1,1);

                            Label sub= new Label();
                            sub.setText(message.message);
                            setGraphic(grid);
                            sub.getStyleClass().add("mes");
                            grid.add(sub,1,1);
                        }
                    }
                };
            }


        });
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setOnMouseClicked(new EventHandler<Event>(){
            @Override
            public void handle(Event event){
                ObservableList<Message> cell = listView.getSelectionModel().getSelectedItems();
                try {
                    System.out.println(cell.get(0));
                    switchrooms(cell,listView.getScene());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }
    public void makemes(Message me){
        mes.add(me);
    }
    public void autoserv(){
        Message message1 = new Message(1,"Public","get em");

        mes.add(message1);

        names.addAll( "Adam", "Alex", "Alfred", "Albert");
        listViews.getItems().addAll(names);

        //cel.updateListView(cel.getListView());
        //listView.setItems(mes);
        //listView.refresh();
        System.out.println(serv.size()+"hh");
//        if (serv.getItems().size()!=0){
//serv.
//            serv.getItems().addAll(mes);}
//
    }



    public void switchrooms(ObservableList<Message> cell, Scene roo) throws IOException {
        room = (Stage) roo.getRoot().getScene().getWindow();


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("chatview.fxml"));
        Scene scene1 = new Scene(fxmlLoader.load(), 600, 600);


        room.setScene((scene1));
        room.setTitle(cell.get(0).name);
        Text hold2 = new Text(cell.get(0).message);
        //listView.getItems().addAll(serverView.getItems());







        makeconn();
        room.show();
        print();

    }
    protected void makeconn() throws IOException {
        Message message = new Message(2,"mary","hope this works");
        Socket socketc =new Socket("localhost",9080);
        Socket socket2 =new Socket("localhost",8080);
        client2 me = new client2(socketc,"hold", socket2);
        me.listen2();
        me.listen();
        me.sendmessage();

        System.out.println("serv conn");
    }

    @FXML
    protected void sendmessage(ActionEvent event) throws IOException {
        se = inmes.getText();
        inmes.setText("");






    }
    public static Text holddd;

    public  static void print(){
        Text hold = new Text("john");
       //Window.getWindows().
        //listView.getItems().add(serverView.getItems().get(0));
        //System.out.println(mes_view.getChildren().get(0));

    }



}

