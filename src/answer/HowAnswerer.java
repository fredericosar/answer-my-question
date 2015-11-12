package answer;

import java.util.ArrayList;

import logic.StoryClassifier;
import boilerplate.*;
import rules.Scores;
import rules.AnswerRules.AType;

public class HowAnswerer {

	private Question question;
	private Story story;
	private StoryClassifier sc;
	private ArrayList<Integer> scores;

	public HowAnswerer(Question question, Story story, StoryClassifier sc, ArrayList<Integer> scores) {
		this.question = question;
		this.story = story;
		this.sc = sc;
		this.scores = scores;
	}

	/**
	 * Answer the HOW question
	 */
	public void answer(){
		String bestSentence = story.getSentence(CommonAnswerer.findBest(scores));
		System.out.println(bestSentence);
	}
}
