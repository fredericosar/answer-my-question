package answer;

import java.util.ArrayList;

import logic.StoryClassifier;
import boilerplate.*;
import rules.AnswerRules.Type;

public class WhoAnswerer {

	private Question question;
	private Story story;
	private StoryClassifier sc;
	private ArrayList<Integer> scores;

	public WhoAnswerer(Question question, Story story, StoryClassifier sc) {
		this.question = question;
		this.story = story;
		this.sc = sc;
	}

	/**
	 * Answer the why question
	 */
	public void answer() {
		int max = 0;
		String ans = "";
		for (int i = 0; i < scores.size(); i++) {
			if (scores.get(i) > 0) {
				/* look for a person tag */
				for (String person : sc.findNER(story, Type.PERSON)) {
					if (story.getSentence(i).contains(person)) {
						if (scores.get(i) > max) {
							max = scores.get(i);
							ans = person;
						}
					}
				}
			}
		}
		System.out.println(ans);
	}

	/** Getters and Setters **/
	public void setScores(ArrayList<Integer> scores) {
		this.scores = scores;
	}

}
