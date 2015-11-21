package answer;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import logic.StoryClassifier;
import boilerplate.*;
import rules.Scores;
import rules.AnswerRules.AType;

public class WhereAnswerer {

	private Story story;
	private StoryClassifier sc;
	private ArrayList<Integer> scores;

	public WhereAnswerer(Story story, StoryClassifier sc, ArrayList<Integer> scores) {
		this.story = story;
		this.sc = sc;
		this.scores = scores;
	}

	/**
	 * Answer the WHERE question
	 */
	public void answer(){
		for(int i = 0; i < story.getBagsOfWords().size(); i++){
			/* get sentence */
			String sentence = story.getSentence(i);
			/* rule #2 */
			if(CommonAnswerer.regexMatcher(sentence, "in|on|near|inside")){
				scores.set(i, scores.get(i) + Scores.GOOD_CLUE);
			}
			/* rule #3 */
			if(!sc.findTYPE(sc.getNER(sentence), AType.LOCATION).isEmpty()){
				scores.set(i, scores.get(i) + Scores.CONFIDENT);
			}
		}
		/* answer */
		String bestSentence = story.getSentence(CommonAnswerer.findBest(scores));
		LinkedHashSet<String> tags = sc.findTYPE(sc.getNER(bestSentence), AType.LOCATION);
		if(!tags.isEmpty()){
			for(String tag : tags) System.out.print(tag + " ");
		}else{
			System.out.print(bestSentence);
		}
		System.out.println();
	}
}
