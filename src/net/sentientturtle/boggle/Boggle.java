package net.sentientturtle.boggle;

import net.sentientturtle.boggle.tree.TreeNode;
import net.sentientturtle.boggle.tree.WordTree;

import java.util.*;

public class Boggle {
    private Random random = new Random(5);
    public final char[][] playingField;

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

    // Complexity where n = total board tiles
    // n + sqrt(n)*sqrt(n) + sqrt(n)*sqrt(n)*X + 3 = n+n*X+3 =
    // 2n+n*(14 + Y + Y*(1/7)*(8^(Y+1)-1))+3
    // Where Y <= maximum word length (Max: 25, AVG from benchmarks: 3)
    // Worst case: T(n) = 1079398053227347477416266 n + 3
    // AVG case: T(n) = 1774 n + 3
    // Thus: O(n)
    public Collection<String> findWords(WordTree tree) {
        Collection<String> wordList = new HashSet<>();      // 1?
        boolean[][] lookedAt = new boolean[playingField.length][playingField.length];   // n
        for (int i = 0; i < playingField.length; i++) {     // sqrt(n)
            for (int j = 0; j < playingField.length; j++) { // sqrt(n)
                lookedAt[i][j] = false;                     // 1
            }
        }

        StringBuilder builder = new StringBuilder();        // 1
        builder.ensureCapacity(tree.getHeight());           // 1
        for (int x = 0; x < playingField.length; x++) {     // sqrt(n)
            for (int y = 0; y < playingField.length; y++) { // sqrt(n)
                findWordFrom(x, y, wordList, builder, lookedAt, tree.getRoot());    // X
            }
        }
        return wordList;
    }

    // Complexity: X = 14+Y+8X, where Y < maximum word length, recurses at most Y times, width the last run taking Y, thus:
    // recursion cases:
    // c0 = Y = Y
    // c1 = Y+8Y = Y+Y(8^1)
    // c2 = Y+8(Y+8Y) = Y+8Y+64Y = Y+Y(1+8+64) = Y+Y(8^0+8^1+8^2)
    // c3 = Y+8(Y+8(Y+8Y)) = Y+8Y+64Y+512 = Y+Y(8^0+8+64+512) = Y+Y(8^0+8^1+8^2+8^3)
    // ...
    // cY = Y+Y(8^0 + 8^1 + 8^2 + 8^3 + ... + 8^Y) = Y + Y * (1/7) * (8^(Y+1)-1)
    // X = 14 + Y + Y*(1/7)*(8^(Y+1)-1) =
    // Where Y <= maximum word length
    private void findWordFrom(int x, int y, Collection<String> wordList, StringBuilder wordStack, boolean[][] lookedAt, TreeNode subtree) {
        if ((x < 0) || (y < 0) || (x >= playingField.length) || (y >= playingField.length) || lookedAt[x][y]) return;   // 5
        lookedAt[x][y] = true;                                      // 1
        TreeNode node = subtree.getNode(playingField[x][y]);        // 2
        wordStack.append(playingField[x][y]);                       // 1; Capacity is ensured beforehand
        if (node != null) {                                         // 1
            if (node.isWord()) {                                    // 1
                wordList.add(wordStack.toString());                 // 1 (HashSet) + Y, where Y <= maximum word length
            }
            findWordFrom(x - 1, y - 1, wordList, wordStack, lookedAt, node);             // Top left        X
            findWordFrom(x - 1, y, wordList, wordStack, lookedAt, node);                    // Left            X
            findWordFrom(x - 1, y + 1, wordList, wordStack, lookedAt, node);             // Bottom left     X
            findWordFrom(x, y - 1, wordList, wordStack, lookedAt, node);                    // Top             X
            findWordFrom(x, y + 1, wordList, wordStack, lookedAt, node);                    // Bottom          X
            findWordFrom(x + 1, y - 1, wordList, wordStack, lookedAt, node);             // Top right       X
            findWordFrom(x + 1, y, wordList, wordStack, lookedAt, node);                    // Right           X
            findWordFrom(x + 1, y + 1, wordList, wordStack, lookedAt, node);             // Bottom right    X
        }
        wordStack.setLength(wordStack.length() - 1);                // 1; Capacity is ensured beforehand
        lookedAt[x][y] = false;                                     // 1
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
