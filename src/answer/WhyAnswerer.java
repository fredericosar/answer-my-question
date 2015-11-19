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
		/* rule #1 */
//		getTopScores(.20); 
		/* rule #2 and #3 */
		for(int position : getBestPositions()){
			try{
				scores.set(position - 1, scores.get(position - 1) + Scores.CLUE);
			} catch(IndexOutOfBoundsException e) {}
			try{
				scores.set(position + 1, scores.get(position + 1) + Scores.GOOD_CLUE);
			} catch(IndexOutOfBoundsException e) {}
		}
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

	/**
	 * Update top scores and keep the top 'percent'
	 */
	private void getTopScores(double percent) {
		int tops = (int) (scores.size() * percent);
		PriorityQueue<Integer> topScores = new PriorityQueue<>(tops + 1);
		for (int num : scores) {
			topScores.add(num);
			if (topScores.size() > tops + 1)
				topScores.poll();
		}
		
		for(int i = 0; i < story.getBagsOfWords().size(); i++){
			if(topScores.contains(scores.get(i))){
				scores.set(i, Scores.CLUE);
			}else{
				scores.set(i, 0);
			}
		}	
	}
	
	/**
	 * Get best positions of array list
	 */
	private ArrayList<Integer> getBestPositions(){
		ArrayList<Integer> positions = new ArrayList<Integer>();
		for(int i = 0; i < story.getBagsOfWords().size(); i++){
			if(scores.get(i) == Scores.CLUE) positions.add(i);
		}
		return positions;
	}
	
}
