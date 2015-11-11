package answer;

import java.util.ArrayList;

import logic.StoryClassifier;
import boilerplate.*;
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
	public void answer() {
		int bestSentence = 0;
		/* goes on each sentence */
		for(int i = 0; i < story.getBagsOfWords().size(); i++){
			String sentenceNER = sc.getNER(story.getSentence(i));
			/* looks for a LOCATION TAG */
			if(!sc.findNER(sentenceNER, AType.LOCATION).isEmpty()){
				/* check for best score */
				bestSentence = (scores.get(i) > scores.get(bestSentence)) ? i : bestSentence;
			}
		}
		/* Print LOCATION on best sentence - @TODO: fix printing first */
		try{
			System.out.println(sc.findNER(sc.getNER(story.getSentence(bestSentence)), AType.LOCATION).get(0));
		}catch(IndexOutOfBoundsException e){
			System.out.println();
		}
	}

}
