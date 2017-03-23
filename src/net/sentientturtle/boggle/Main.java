package net.sentientturtle.boggle;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sentientturtle.boggle.tree.WordTree;
import net.sentientturtle.boggle.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class Main extends Application {
    private GridPane pane;
    private Boggle boggle;
    private int getSize;
    private Stage newStage;
    private Scene scene;
    private Map<String, Collection<Pair<Integer, Integer>>> words;
    private ArrayList<Pair<Integer, Integer>> selectedLetters;
    private HBox hBox1;
    private boolean boolList = false;

    public Main() {
        selectedLetters = new ArrayList<>();
    }


    private void searchWords()throws IOException{
        WordTree wordTree = new WordTree();
        Scanner scanner = new Scanner(new File("rsc/wordlist.txt"));
        while(scanner.hasNextLine())
        {
            String word = scanner.nextLine();
            if (word.length() > 2) {    // Load only words larger than 3 characters
                wordTree.addWord(word);
            }
        }
        scanner.close();
        words = boggle.findWords(wordTree);
    }

    private void viewList(){
        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(words.keySet());
        listView.setItems(items);
        hBox1.getChildren().addAll(listView);
        listView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<String>) c -> {
            selectedLetters.clear();
            c.getList().stream().map((Function<String, Collection<Pair<Integer, Integer>>>) s -> words.get(s)).forEach(selectedLetters::addAll);
            drawBoard();
        });
    }

    private void drawBoard(){
        pane.getChildren().clear();
        for(int x = 0; x<boggle.playingField.length;x++){
            for(int y=0;y<boggle.playingField.length;y++){
                String character = Character.toString(boggle.playingField[x][y]);
                //System.out.println(character);
                Label text = new Label(character);
                text.setMinWidth(300/10.0);
                text.setMaxHeight(300/10.0);
                text.setAlignment(Pos.CENTER);
                if (selectedLetters.contains(new Pair<>(x, y))) {
                    text.setStyle("-fx-background-color: darkslategray");
                }
                pane.add(text,y,x);
            }
        }
    }



    private void createBoard(int size){
        boggle = new Boggle(size);
        try { searchWords();
        }catch (IOException ex){ System.out.println("IOExeption error!");}
        pane = new GridPane();
        drawBoard();
    }




    public static void main(String[] args) throws FileNotFoundException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        TextField textSize = new TextField();
        Button setField = new Button("Set");
        Label labelsize = new Label("Vul hier de grootte van het bord in:");

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


        FlowPane pane2 = new FlowPane();
        pane2.setVgap(10);
        pane2.setStyle("-fx-padding: 10px;");
        pane2.getChildren().addAll(labelsize,textSize, setField);



        //Create new scene
        scene = new Scene(borderPane,300,300);
        Scene scene2 = new Scene(pane2,300,80);
        newStage = new Stage();
        newStage.setScene(scene2);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setTitle("Test");
        // set the Scene to stage
        stage.setScene(scene);


        stage.setTitle("Boggle");

        newStage.show();


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
                if (!boolList){
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
                pane.setGridLinesVisible(true);
                scene = new Scene(borderPane);
                stage.setScene(scene);
                newStage.close();
                stage.show();
            }
        });
    }

}

