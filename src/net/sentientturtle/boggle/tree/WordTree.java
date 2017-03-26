package net.sentientturtle.boggle.tree;

/**
 * Tree to hold a list of words
 */
public class WordTree {
    private TreeNode root;
    private int height;

    /**
     * Creates a new WordTree
     */
    public WordTree() {
        root = new TreeNode();
        height = 0;
    }

    /**
     * Adds a word to this WordTree
     * @param word Word to add
     */
    public void addWord(String word) {
        root.addWord(word.toCharArray(), 0);
        if (word.length() > height) height = word.length(); // Cheesy way of tracking tree height;
        // Words are only ever added, and the tree's height is equal to the longest word's length
    }

    /**
     * Checks if the given word has been added to this WordTree
     * @param word Word to search
     * @return True if the given word is in this tree, false otherwise
     */
    public boolean isWord(String word) {
        return root.isWord(word.toCharArray(), 0);
    }

    /**
     * Gets the root node of this WordTree
     * @return Root node of this WordTree
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * Returns the height of this WordTree, which is equal to the length of the longest added word
     * @return Height of this WordTree
     */
    public int getHeight() {
        return height;
    }
}

