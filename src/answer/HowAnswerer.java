package answer;

import java.util.ArrayList;

import logic.StoryClassifier;
import boilerplate.Question;
import boilerplate.Story;

public class HowAnswerer {

	private Question question;
	private Story story;
	private StoryClassifier sc;
	private ArrayList<Integer> scores;
	
	public enum HType {
		MUCH, MANY;
	};
	
	private HType myType;
	
	
	public HowAnswerer(Question question, Story story, StoryClassifier sc, ArrayList<Integer> scores) {
		this.question = question;
		this.story = story;
		this.sc = sc;
		this.scores = scores;
		defineType();
	}

	public void defineType() {
		if (question.getQuestion().toLowerCase().contains("how much")) {
			myType = HType.MUCH;
		} else if (question.getQuestion().toLowerCase().contains("how many")) {
			myType = HType.MANY;
		}
	}

	/**
	 * Answer the HOW question base on its type
	 */
	public void answer() {
//		/* goes on each sentence */
		for(int i = 0; i < story.getBagsOfWords().size(); i++){
			/* MANY */
			if(myType == HType.MANY){
			}
		}
	}
	
}
