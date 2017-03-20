import net.sentientturtle.boggle.Boggle;
import net.sentientturtle.boggle.tree.WordTree;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class BoggleTest {
    @Test
    public void testWordSearch() throws FileNotFoundException {  // Test to see all found words are in the word list
        WordTree wordTree = new WordTree();
        HashSet<String> wordSet = new HashSet<>();
        Scanner scanner = new Scanner(new File("rsc/wordlist.txt"));
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            wordTree.addWord(word);
            wordSet.add(word);
        }
        scanner.close();

        Boggle boggle = new Boggle(15);
        List<String> words = boggle.findWords(wordTree);

        for (String word : words) {
            if (!wordSet.contains(word)) throw new AssertionError();
        }
    }
}
