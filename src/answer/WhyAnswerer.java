package answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

import rules.Scores;
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
		for(int i = 0; i < story.getBagsOfWords().size(); i++){
			/* get sentence */
			String sentence = story.getSentence(i);
			/* rule #4 */
			if(CommonAnswerer.regexMatcher(sentence, "want")){
				scores.set(i, scores.get(i) + Scores.GOOD_CLUE);
			}
			/* rule #5 */
			if(CommonAnswerer.regexMatcher(sentence, "so|because")){
				scores.set(i, scores.get(i) + Scores.GOOD_CLUE);
			}
		}
		String bestSentence = story.getSentence(CommonAnswerer.findBest(scores));
		System.out.println(bestSentence);
	}
	
}
