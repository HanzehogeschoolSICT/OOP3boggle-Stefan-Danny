package net.sentientturtle.boggle.tree;

public class WordTree {
    private TreeNode root;

    public WordTree() {
        root = new TreeNode();
    }

    public void addWord(String word) {
        root.addWord(word.toCharArray(), 0);
    }

    public boolean isWord(String word) {
        return root.isWord(word.toCharArray(), 0);
    }

    public TreeNode getRoot() {
        return root;
    }
}

