package net.sentientturtle.boggle;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import net.sentientturtle.boggle.tree.TreeNode;
import net.sentientturtle.boggle.tree.WordTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Boggle {
    private Random random = new Random(5);
    public char[][] playingField;





    public Boggle(int fieldSize) {
        assert fieldSize > 0;
        this.playingField = new char[fieldSize][fieldSize];
        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField[i].length; j++) {
                playingField[i][j] = (char) (random.nextInt(26) + 97);
            }
        }
    }

    public void randomize() {
        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField[i].length; j++) {
                playingField[i][j] = (char) (random.nextInt(26) + 97);
            }
        }
    }

    public List<String> findWords(WordTree tree) {
        ArrayList<String> wordList = new ArrayList<>();
        boolean[][] lookedAt = new boolean[playingField.length][playingField.length];
        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField.length; j++) {
                lookedAt[i][j] = false;
            }
        }

        StringBuilder builder = new StringBuilder();
        for (int x = 0; x < playingField.length; x++) {
            for (int y = 0; y < playingField.length; y++) {
                findWordFrom(x, y, wordList, builder, lookedAt, tree.getRoot());
            }
        }
        return wordList;
    }

    private void findWordFrom(int x, int y, List<String> wordList, StringBuilder wordStack, boolean[][] lookedAt, TreeNode subtree) {
        if ((x < 0) || (y < 0) || (x >= playingField.length) || (y >= playingField.length) || lookedAt[x][y]) return;
        lookedAt[x][y] = true;
        TreeNode node = subtree.getNode(playingField[x][y]);
        wordStack.append(playingField[x][y]);
        if (node != null) {
            if (node.isWord()) {
                wordList.add(wordStack.toString());
            }
            findWordFrom(x - 1, y - 1, wordList, wordStack, lookedAt, node);             // Top left
            findWordFrom(x - 1, y, wordList, wordStack, lookedAt, node);                    // Left
            findWordFrom(x - 1, y + 1, wordList, wordStack, lookedAt, node);             // Bottom left
            findWordFrom(x, y - 1, wordList, wordStack, lookedAt, node);                    // Top
            findWordFrom(x, y + 1, wordList, wordStack, lookedAt, node);                    // Bottom
            findWordFrom(x + 1, y - 1, wordList, wordStack, lookedAt, node);             // Top right
            findWordFrom(x + 1, y, wordList, wordStack, lookedAt, node);                    // Right
            findWordFrom(x + 1, y + 1, wordList, wordStack, lookedAt, node);             // Bottom right
        }
        wordStack.setLength(wordStack.length() - 1);
        lookedAt[x][y] = false;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int x = 0; x < playingField.length; x++) {
            for (int y = 0; y < playingField.length; y++) {
                builder.append(playingField[x][y]);
                if (y < playingField.length - 1) builder.append(',').append(' ');
            }
            builder.append(']').append('\n');
            if (x < playingField.length - 1) builder.append('[');
        }
        return builder.toString();
    }
}
