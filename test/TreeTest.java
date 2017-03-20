import net.sentientturtle.boggle.tree.WordTree;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TreeTest {
    @Test
    public void testTree() throws FileNotFoundException {
        List<String> wordList = new ArrayList<>();
        WordTree wordTree = new WordTree();
        Scanner scanner = new Scanner(new File("rsc/wordlist.txt"));
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            wordList.add(word);
            wordTree.addWord(word);
        }
        scanner.close();

        for (String s : wordList) assert wordTree.isWord(s);
    }
}
