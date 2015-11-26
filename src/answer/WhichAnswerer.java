package answer;

import java.util.ArrayList;

import logic.StoryClassifier;
import boilerplate.*;
import rules.Scores;
import rules.AnswerRules.AType;

public class WhichAnswerer {

    private Question question;
    private Story story;
    private StoryClassifier sc;
    private ArrayList<Integer> scores;

    public WhichAnswerer(Question question, Story story, StoryClassifier sc, ArrayList<Integer> scores) {
        this.question = question;
        this.story = story;
        this.sc = sc;
        this.scores = scores;
    }

    /**
     * Answer the WHICH question
     */
    public void answer() {
        String bestSentence = story.getSentence(CommonAnswerer.findBest(scores));
        System.out.println(bestSentence);
    }
}
