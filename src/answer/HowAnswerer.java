package answer;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import logic.StoryClassifier;
import boilerplate.*;
import rules.Scores;
import rules.AnswerRules.AType;

public class HowAnswerer {

    private Question question;
    private Story story;
    private StoryClassifier sc;
    private ArrayList<Integer> scores;
    private MaxentTagger tagger;

    public HowAnswerer(Question question, Story story, StoryClassifier sc, ArrayList<Integer> scores, MaxentTagger tagger) {
        this.question = question;
        this.story = story;
        this.sc = sc;
        this.scores = scores;
        this.tagger = tagger;
    }

    /**
     * Answer the HOW question
     */
    public void answer() {

        for (int i = 0; i < story.getBagsOfWords().size(); i++) {
            /* get sentence */
            String sentence = story.getSentence(i);
            /* rule by Fred #1 */
            if (CommonAnswerer.regexMatcher(question.getQuestion(), "How much")) {
                if (CommonAnswerer.regexMatcher(question.getQuestion(), "cost|pay")) {
                    if (!sc.findTYPE(sc.getNER(sentence), AType.MONEY).isEmpty()) {
                        scores.set(i, scores.get(i) + Scores.SLAM_DUNK);
                    }
                }
            }
            /* rule by Fred #2 */
            if (CommonAnswerer.regexMatcher(question.getQuestion(), "How many")) {
                if (!sc.findTYPE(tagger.tagString(sentence), AType.NUMBER).isEmpty()) {
                    scores.set(i, scores.get(i) + Scores.SLAM_DUNK);
                }
            }
        }

        /* answer rules */
        String bestSentence = story.getSentence(CommonAnswerer.findBest(scores));
        /*
         * BIG RECAL to PRECISION TRADE OFF HERE
         */
        String taggedSentence = tagger.tagString(bestSentence);
        if (CommonAnswerer.regexMatcher(question.getQuestion(), "How many|How long")) {
            LinkedHashSet<String> tags = sc.findTYPE(taggedSentence, AType.NUMBER);
            if (!tags.isEmpty()) {
                for (String tag : tags)
                    System.out.print(tag + " ");
            } else {
                System.out.print(bestSentence);
            }
            System.out.println();
        } else if (CommonAnswerer.regexMatcher(question.getQuestion(), "How much")) {
            LinkedHashSet<String> tags = sc.findTYPE(sc.getNER(bestSentence), AType.MONEY);
            if (!tags.isEmpty()) {
                for (String tag : tags)
                    System.out.print(tag + " ");
            } else {
                System.out.print(bestSentence);
            }
            System.out.println();
        } else {
            System.out.println(bestSentence);
        }
    }
}
