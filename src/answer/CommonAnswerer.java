package answer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rules.Scores;
import rules.StopWords;
import boilerplate.Question;
import boilerplate.Story;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.washington.cs.knowitall.morpha.MorphaStemmer;

public class CommonAnswerer {

    /**
     * Return an array of scores for the given bag based on bag for the story
     */
    public static ArrayList<Integer> getIntersectionScores(Question question, Story story, MaxentTagger tagger) {
        /* stop word */
        StopWords sw = new StopWords();
        /* get intersection score */
        ArrayList<Integer> intersectionScore = new ArrayList<Integer>();
        /* go on each sentence */
        for (List<HasWord> bags : story.getBagsOfWords()) {
            ArrayList<String> matchedSoFar = new ArrayList<String>();
            int score = 0;
            /* get each word on sentence */
            for (HasWord w1 : bags) {
                /* get each word on question */
                for (HasWord w2 : question.getBagOfWords()) {
                    if (!sw.isStopWord(w1.word().toLowerCase())) {
                        if (MorphaStemmer.stem((w1.word().toLowerCase())).equals(MorphaStemmer.stem((w2.word().toLowerCase())))) {
                            // if(w1.word().toLowerCase().equals(w2.word().toLowerCase())){
                            if (!matchedSoFar.contains((w1.word().toLowerCase()))) {
                                if (regexMatcher(tagger.tagString(w1.word()), "([^ ]+)_VB[A-Z]")) {
                                    score = score + Scores.CONFIDENT;
                                } else {
                                    score = score + Scores.CLUE;
                                }
                            }
                            matchedSoFar.add(w1.word().toLowerCase());
                        }
                    }
                }
            }
            intersectionScore.add(score);
        }
        return intersectionScore;
    }

    /**
     * Find best score
     */
    public static int findBest(ArrayList<Integer> scores) {
        int maxSoFar = -1;
        int best = -1;
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) > maxSoFar) {
                maxSoFar = scores.get(i);
                best = i;
            }
        }
        return best;
    }

    /**
     * Boolean Regex Matcher - Gets a SENTENCE and a REGEX and return true if
     * the REGEX is a match
     */
    public static boolean regexMatcher(String sentence, String regex) {
        Pattern pattern = Pattern.compile(regex.toLowerCase());
        Matcher regexMatcher = pattern.matcher(sentence.toLowerCase());
        return regexMatcher.find();
    }
}
