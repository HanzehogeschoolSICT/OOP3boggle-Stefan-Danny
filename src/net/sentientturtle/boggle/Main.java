package net.sentientturtle.boggle;

import net.sentientturtle.boggle.tree.WordTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        WordTree wordTree = new WordTree();
        HashSet<String> wordSet = new HashSet<>();
        Scanner scanner = new Scanner(new File("rsc/wordlist.txt"));
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            wordTree.addWord(word);
            wordSet.add(word);
        }
        scanner.close();

        scanner = new Scanner(new File("rsc/wordlist.txt"));
        while (scanner.hasNextLine()) {
            if (!wordTree.isWord(scanner.nextLine())) throw new AssertionError();
        }
        scanner.close();

        Boggle boggle = new Boggle(5);
        System.out.println(boggle.toString());
        List<String> words = boggle.findWords(wordTree);
        System.out.println(words);

        for (String word : words) {
            if (!wordSet.contains(word)) throw new AssertionError();
        }
    }
}
