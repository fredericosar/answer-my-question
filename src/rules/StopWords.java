package rules;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class StopWords {

    private static HashSet<String> stopWords;

    public StopWords() {
        stopWords = new HashSet<String>();
        try (Scanner words = new Scanner(new File("resources/stopwords"))) {
            while (words.hasNext()) {
                stopWords.add(words.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isStopWord(String word) {
        return stopWords.contains(word);
    }

    public HashSet<String> getStopWords() {
        return stopWords;
    }

}
