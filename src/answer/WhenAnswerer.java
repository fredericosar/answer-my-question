package answer;

import java.util.ArrayList;

import logic.StoryClassifier;
import rules.AnswerRules.AType;
import boilerplate.Story;

public class WhenAnswerer {

	private Story story;
	private StoryClassifier sc;
	private ArrayList<Integer> scores;

	public WhenAnswerer(Story story, StoryClassifier sc, ArrayList<Integer> scores) {
		this.story = story;
		this.sc = sc;
		this.scores = scores;
	}

	/**
	 * Answer the WHEN question
	 */
	public void answer() {
		int bestSentence = 0;
		/* goes on each sentence */
		for(int i = 0; i < story.getBagsOfWords().size(); i++){
			String sentence = story.getSentence(i);
			String sentenceNER = sc.getNER(sentence);
			/* looks for a DATE TAG */
			if(!sc.findNER(sentenceNER, AType.DATE).isEmpty()){
				/* check for best score */
				bestSentence = (scores.get(i) > scores.get(bestSentence)) ? i : bestSentence;
			}
		}
		/* Print DATE on best sentence - @TODO: fix printing first */
		try{
			System.out.println(sc.findNER(sc.getNER(story.getSentence(bestSentence)), AType.DATE).get(0));
		}catch(IndexOutOfBoundsException e){
			System.out.println();
		}
	}

}
