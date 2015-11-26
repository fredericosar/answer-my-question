package answer;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import rules.Scores;
import rules.AnswerRules.AType;
import logic.StoryClassifier;
import boilerplate.Question;
import boilerplate.Story;

public class WhyAnswerer {

    private Question question;
    private Story story;
    private StoryClassifier sc;
    private ArrayList<Integer> scores;

    public WhyAnswerer(Question question, Story story, StoryClassifier sc, ArrayList<Integer> scores) {
        this.question = question;
        this.story = story;
        this.sc = sc;
        this.scores = scores;
    }

    /**
     * Answer the WHY question
     */
    public void answer() {
        // getBestPositions();
        for (int i = 0; i < story.getBagsOfWords().size(); i++) {
            /* get sentence */
            String sentence = story.getSentence(i);
            /* rule #4 */
            if (CommonAnswerer.regexMatcher(sentence, "want")) {
                scores.set(i, scores.get(i) + Scores.GOOD_CLUE);
            }
            /* rule #5 */
            if (CommonAnswerer.regexMatcher(sentence, "so|because")) {
                scores.set(i, scores.get(i) + Scores.GOOD_CLUE);
            }
        }
        /* answer rules */
        String bestSentence = story.getSentence(CommonAnswerer.findBest(scores));
        /* rule by Fred #1 - look for words after because */
        LinkedHashSet<String> words = sc.findTYPE(bestSentence, AType.BECAUSE);
        if (!words.isEmpty()) {
            for (String word : words) {
                System.out.print(word + " ");
            }
        } else {
            System.out.print(bestSentence);
        }
        System.out.println();
    }

}
