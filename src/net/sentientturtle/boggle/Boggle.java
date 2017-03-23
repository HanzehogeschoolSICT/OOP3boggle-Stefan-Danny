package net.sentientturtle.boggle;

import net.sentientturtle.boggle.tree.TreeNode;
import net.sentientturtle.boggle.tree.WordTree;
import net.sentientturtle.boggle.util.Pair;

import java.util.*;

public class Boggle {
    private Random random = new Random(5);
    public char[][] playingField;





    public Boggle(int fieldSize) {
        assert fieldSize > 0;
        this.playingField = new char[fieldSize][fieldSize];
        randomize();
    }

    public void randomize() {
        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField[i].length; j++) {
                playingField[i][j] = (char) (random.nextInt(26) + 97);
            }
        }
    }

    public Map<String, Collection<Pair<Integer, Integer>>> findWords(WordTree tree) {
        return findWords(tree, new HashMap<>(), new ArrayDeque<>());
    }

    public Map<String, Collection<Pair<Integer, Integer>>> findWords(WordTree tree, Map<String, Collection<Pair<Integer, Integer>>> wordMap, Deque<Pair<Integer, Integer>> positionStack) {
        boolean[][] lookedAt = new boolean[playingField.length][playingField.length];
        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField.length; j++) {
                lookedAt[i][j] = false;
            }
        }

        StringBuilder builder = new StringBuilder();
        for (int x = 0; x < playingField.length; x++) {
            for (int y = 0; y < playingField.length; y++) {
                findWordFrom(x, y, wordMap, builder, positionStack, lookedAt, tree.getRoot());
            }
        }
        return wordMap;
    }

    private void findWordFrom(int x, int y, Map<String, Collection<Pair<Integer, Integer>>> wordMap, StringBuilder wordStack, Deque<Pair<Integer, Integer>> positionStack, boolean[][] lookedAt, TreeNode subtree) {
        if ((x < 0) || (y < 0) || (x >= playingField.length) || (y >= playingField.length) || lookedAt[x][y]) return;
        lookedAt[x][y] = true;
        positionStack.addLast(new Pair<>(x, y));
        TreeNode node = subtree.getNode(playingField[x][y]);
        wordStack.append(playingField[x][y]);
        if (node != null) {
            if (node.isWord()) {
                wordMap.put(wordStack.toString(), new ArrayList<>(positionStack));
            }
            findWordFrom(x - 1, y - 1, wordMap, wordStack, positionStack, lookedAt, node);             // Top left
            findWordFrom(x - 1, y, wordMap, wordStack, positionStack, lookedAt, node);                    // Left
            findWordFrom(x - 1, y + 1, wordMap, wordStack, positionStack, lookedAt, node);             // Bottom left
            findWordFrom(x, y - 1, wordMap, wordStack, positionStack, lookedAt, node);                    // Top
            findWordFrom(x, y + 1, wordMap, wordStack, positionStack, lookedAt, node);                    // Bottom
            findWordFrom(x + 1, y - 1, wordMap, wordStack, positionStack, lookedAt, node);             // Top right
            findWordFrom(x + 1, y, wordMap, wordStack, positionStack, lookedAt, node);                    // Right
            findWordFrom(x + 1, y + 1, wordMap, wordStack, positionStack, lookedAt, node);             // Bottom right
        }
        wordStack.setLength(wordStack.length() - 1);
        if (!positionStack.removeLast().is(x, y)) throw new IllegalStateException("Position stack corrupt!");
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
