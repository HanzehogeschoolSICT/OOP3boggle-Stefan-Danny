import net.sentientturtle.boggle.Boggle;
import net.sentientturtle.boggle.tree.WordTree;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

public class BoggleTest {
    private WordTree wordTree;
    private HashSet<String> wordSet;

    public BoggleTest() throws FileNotFoundException {
        wordTree = new WordTree();
        wordSet = new HashSet<>();
        Scanner scanner = new Scanner(new File("rsc/wordlist.txt"));
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            wordTree.addWord(word);
            wordSet.add(word);
        }
        scanner.close();
    }

    @Test
    public void testWordSearch() {  // Test to see all found words are in the word list
        Boggle boggle = new Boggle(15);
        Collection<String> words = boggle.findWords(wordTree);

        for (String word : words) assert wordSet.contains(word);
    }

    @Test
    public void benchmarkSearch() {
        Boggle boggle;
        for (int i = 64; i <= 1600; i+= 64) {
            boggle = new Boggle(i);
            System.gc();
            long current = System.currentTimeMillis();
            boggle.findWords(wordTree);
            long duration = (System.currentTimeMillis() - current);
            System.out.println(i + "\t" + (i * i) + "\t" + duration);
        }
    }
}
