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
		/* find location on sentence */
		LinkedHashSet<String> words = sc.findTYPE(sc.getNER(bestSentence), AType.LOCATION);
		if(!words.isEmpty()){
			for(String word : words){
				System.out.print(word + " ");
			}
		}else{
			/* location not found */
			System.out.println(bestSentence);
		}
		System.out.println();
	}
}
