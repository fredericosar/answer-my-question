package answer;

import java.util.ArrayList;
import java.util.List;

import rules.Scores;
import rules.StopWords;
import boilerplate.Question;
import boilerplate.Story;
import edu.stanford.nlp.ling.HasWord;

public class CommonAnswerer {

	/**
	 * Return an array of scores for the given bag
	 * based on bag for the story
	 */
	public static ArrayList<Integer> getIntersectionScores(Question question, Story story) {
		/* stop word */
		StopWords sw = new StopWords();
		/* get intersection score */
		ArrayList<Integer> intersectionScore = new ArrayList<Integer>();
		for(List<HasWord> bags : story.getBagsOfWords()){
			int score = 0;
			for(HasWord w1 : bags){
				for(HasWord w2 : question.getBagOfWords()){
					if(!sw.isStopWord(w1.word().toLowerCase())){
						if(w1.word().toLowerCase().equals(w2.word().toLowerCase())){
							score = score + Scores.OK;
						}
					}
				}
			}
			intersectionScore.add(score);
		}
		return intersectionScore;
	}
	
}
