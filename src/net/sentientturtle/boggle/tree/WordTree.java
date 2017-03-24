package net.sentientturtle.boggle.tree;

public class WordTree {
    private TreeNode root;
    private int height;

    public WordTree() {
        root = new TreeNode();
        height = 0;
    }

    public void addWord(String word) {
        root.addWord(word.toCharArray(), 0);
        if (word.length() > height) height = word.length(); // Cheesy way of tracking tree height;
        // Words are only ever added, and the tree's height is equal to the longest word's length
    }

    public boolean isWord(String word) {
        return root.isWord(word.toCharArray(), 0);
    }

    public TreeNode getRoot() {
        return root;
    }

    public int getHeight() {
        return height;
    }
}

