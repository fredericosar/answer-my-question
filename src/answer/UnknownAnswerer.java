package answer;

import java.util.ArrayList;

import boilerplate.*;

public class UnknownAnswerer {

    private Story story;
    private ArrayList<Integer> scores;

    public UnknownAnswerer(Story story, ArrayList<Integer> scores) {
        this.story = story;
        this.scores = scores;
    }

    /**
     * Answer the UNKNOWN question
     */
    public void answer() {
        String bestSentence = story.getSentence(CommonAnswerer.findBest(scores));
        System.out.println(bestSentence);
    }
}
