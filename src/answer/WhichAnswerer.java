package answer;

import java.util.ArrayList;

import boilerplate.*;

public class WhichAnswerer {

    private Story story;
    private ArrayList<Integer> scores;

    public WhichAnswerer(Story story, ArrayList<Integer> scores) {
        this.story = story;
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
