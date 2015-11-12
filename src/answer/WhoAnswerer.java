package answer;

import java.util.ArrayList;

import logic.StoryClassifier;
import boilerplate.*;
import rules.Scores;
import rules.AnswerRules.AType;

public class WhoAnswerer {

	private Story story;
	private StoryClassifier sc;
	private ArrayList<Integer> scores;

	public WhoAnswerer(Story story, StoryClassifier sc, ArrayList<Integer> scores) {
		this.story = story;
		this.sc = sc;
		this.scores = scores;
	}

	/**
	 * Answer the WHO question
	 */
	public void answer(){
		for(int i = 0; i < story.getBagsOfWords().size(); i++){
			/* get sentence */
			String sentence = story.getSentence(i);
			/* rule #2 */
			if(sc.findNER(sc.getNER(sentence), AType.PERSON).isEmpty()){
				if(!sc.findNER(sc.getNER(sentence), AType.PERSON).isEmpty()){
					scores.set(i, scores.get(i) + Scores.CONFIDENT);
				}
			}
			/* rule #3 */
			if(sc.findNER(sc.getNER(sentence), AType.PERSON).isEmpty()){
				if(CommonAnswerer.regexMatcher(sentence, "name")){
					scores.set(i, scores.get(i) + Scores.GOOD_CLUE);
				}
			}
			/* rule #4 */
			if(!sc.findNER(sc.getNER(sentence), AType.PERSON).isEmpty()){
				scores.set(i, scores.get(i) + Scores.GOOD_CLUE);
			}
		}
		/* answer */
		String bestSentence = story.getSentence(CommonAnswerer.findBest(scores));
//		ArrayList<String> tags = sc.findNER(sc.getNER(bestSentence), AType.PERSON);
//		if(!tags.isEmpty()){
//			for(String tag : tags) System.out.print(tag + " ");
//		}else{
			System.out.print(bestSentence);
//		}
		System.out.println();
	}
}
