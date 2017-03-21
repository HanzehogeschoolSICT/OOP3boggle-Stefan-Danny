package net.sentientturtle.boggle;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import net.sentientturtle.boggle.tree.WordTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {
    public static void main(String[] args) throws FileNotFoundException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Boggle boggle = new Boggle(15);
        GridPane pane = new GridPane();



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




        //Create new scene
        Scene scene = new Scene(pane);

        // set the Scene to stage
        stage.setScene(scene);

        stage.setTitle("Boggle");

        stage.show();



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
//        System.out.println(boggle.toString());
        List<String> words = boggle.findWords(wordTree);
        System.out.println(words);
    }
}

