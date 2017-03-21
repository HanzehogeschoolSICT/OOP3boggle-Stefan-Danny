package net.sentientturtle.boggle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sentientturtle.boggle.tree.WordTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    private GridPane pane;
    private Boggle boggle;
    private int getSize;
    Button setField;
    Stage newStage;
    Stage stage;
    BorderPane borderPane;
    Scene scene;
    List<String> words;
    HBox hBox1;
    private boolean boolList = false;
    ListView<String> listView;
    ObservableList<String> items;
    WordTree wordTree;
    HashSet<String> wordSet;
    Scanner scanner;


    private void searchWords()throws IOException{

        wordTree = new WordTree();
        wordSet = new HashSet<>();
        scanner = new Scanner(new File("rsc/wordlist.txt"));
        while(scanner.hasNextLine())

        {
            String word = scanner.nextLine();
            if (word.length() > 2) {    // Load only words larger than 3 characters
                wordTree.addWord(word);
                wordSet.add(word);
            }
        }
        scanner.close();
        words = boggle.findWords(wordTree);
        System.out.println(words);

    }

    private void viewList(){
        listView = new ListView<String>();
        items = FXCollections.observableArrayList();
        for (String e : words) {
            System.out.println(e);
            items.add(e);
        }

        listView.setItems(items);
        hBox1.getChildren().addAll(listView);


    }



    private void createBoard(int size){
        boggle = new Boggle(size);
        try {
            searchWords();
        }catch (IOException ex){
            System.out.println("IOExeption error!");
        }

        pane = new GridPane();



        for(int i = 0; i<boggle.playingField.length;i++){
            for(int j=0;j<boggle.playingField.length;j++){
                String character = Character.toString(boggle.playingField[i][j]);
                System.out.println(character);
                Label text = new Label(character);
                text.setMinWidth(300/10.0);
                text.setMaxWidth(300/10.0);
                text.setMinHeight(300/10.0);
                text.setMaxHeight(300/10.0);
                text.setAlignment(Pos.CENTER);
                pane.add(text,j,i);
            }

        }
        pane.setGridLinesVisible(true);
        System.out.println(boggle.toString());

    }




    public static void main(String[] args) throws FileNotFoundException {
        launch(args);
    }




    @Override
    public void start(Stage stage) throws IOException {

        createBoard(3);








        TextField textSize = new TextField();
        setField = new Button("Set");

//        setField.setOnAction(e-> ButtonClicked(e));


        //Create MenuBar
        MenuBar menuBar = new MenuBar();

        //create Menus
        Menu menuItems = new Menu("menu");

        MenuItem newBoard = new MenuItem("Create new Board");
        RadioMenuItem viewWords = new RadioMenuItem("Toggle words");
        MenuItem closeApp = new MenuItem("Close");

        menuItems.getItems().addAll(newBoard, viewWords, closeApp);

        menuBar.getMenus().add(menuItems);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(textSize, setField);

        hBox1 = new HBox();

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(hBox);
        borderPane.setTop(menuBar);







//        Boggle boggle = new Boggle(15);
//        GridPane pane = new GridPane();
//
//
//
//        for(int i = 0; i<boggle.playingField.length;i++){
//            for(int j=0;j<boggle.playingField.length;j++){
//                String character = Character.toString(boggle.playingField[i][j]);
//                System.out.println(character);
//                Label text = new Label(character);
//                text.setMinWidth(300/10.0);
//                text.setMaxWidth(300/10.0);
//                text.setMinHeight(300/10.0);
//                text.setMaxHeight(300/10.0);
//                text.setAlignment(Pos.CENTER);
//                pane.add(text,j,i);
//        }
//
//        }



        FlowPane pane2 = new FlowPane();
        pane2.setVgap(10);
        pane2.setStyle("-fx-background-color: red;-fx-padding: 10px;");
        pane2.getChildren().addAll(textSize, setField);



        //Create new scene
        scene = new Scene(borderPane);
        Scene scene2 = new Scene(pane2,200,100);
        newStage = new Stage();
        newStage.setScene(scene2);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle("Test");
        // set the Scene to stage
        stage.setScene(scene);

        stage.setTitle("Boggle");

        stage.show();


        newBoard.setOnAction(event -> newStage.showAndWait());

        closeApp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });


        viewWords.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    searchWords();
                }catch (IOException ex){
                    System.out.println("IO exception error");
                }

                hBox1 = new HBox();
                BorderPane borderPane = new BorderPane();
                borderPane.setCenter(pane);
                borderPane.setBottom(hBox);
                borderPane.setTop(menuBar);
                if (boolList ==false){
                    viewList();
                    borderPane.setRight(hBox1);
                    boolList = true;
                }else{
                    boolList = false;
                }
                scene = new Scene(borderPane);
                stage.setScene(scene);

            }
        });

        setField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getSize = Integer.parseInt(textSize.getText());

                createBoard(getSize);
                BorderPane borderPane = new BorderPane();
                borderPane.setCenter(pane);
                borderPane.setBottom(hBox);
                borderPane.setTop(menuBar);
                scene = new Scene(borderPane);
                stage.setScene(scene);
                newStage.close();
//                try {
//                    searchWords();
//                }catch (IOException ex){
//                    System.out.println("IOExeption error!");
//                }
            }
        });





    WordTree wordTree = new WordTree();
    HashSet<String> wordSet = new HashSet<>();
    Scanner scanner = new Scanner(new File("rsc/wordlist.txt"));
        while(scanner.hasNextLine())

    {
        String word = scanner.nextLine();
        if (word.length() > 2) {    // Load only words larger than 3 characters
            wordTree.addWord(word);
            wordSet.add(word);
        }
    }
        scanner.close();

//        Boggle boggle = new Boggle(15);
        System.out.println(boggle.toString());
        List<String> words = boggle.findWords(wordTree);
        System.out.println(words);
    }

}

