package net.sentientturtle.boggle.tree;

public class TreeNode {
    private TreeNode[] nodes;
    private boolean isWord;

    TreeNode() {
        nodes = new TreeNode[26];
        isWord = false;
    }

    void addWord(char[] word, int index) {
        if (index < word.length - 1) {
            if (nodes[word[index] - 97] == null) {
                nodes[word[index] - 97] = new TreeNode();
            }
            nodes[word[index] - 97].addWord(word, index + 1);
        } else {
            if (nodes[word[index] - 97] == null) {
                nodes[word[index] - 97] = new TreeNode();
            }
            nodes[word[index] - 97].isWord = true;
        }
    }

    boolean isWord(char[] chars, int index) {
        if (index == chars.length) {
            return isWord;
        } else {
            return nodes[chars[index] - 97].isWord(chars, index + 1);
        }
    }

    public TreeNode getNode(char c) {
        return nodes[c - 97];
    }

    public boolean isWord() {
        return isWord;
    }
}
