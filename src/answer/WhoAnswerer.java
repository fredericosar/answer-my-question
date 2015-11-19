package answer;

import java.util.ArrayList;

import logic.QuestionClassifier;
import logic.StoryClassifier;
import boilerplate.*;
import rules.Scores;
import rules.AnswerRules.AType;

public class WhoAnswerer {

	private Question question;
	private QuestionClassifier qc;
	private Story story;
	private StoryClassifier sc;
	private ArrayList<Integer> scores;

	public WhoAnswerer(Question question, QuestionClassifier qc, Story story, StoryClassifier sc, ArrayList<Integer> scores) {
		this.question = question;
		this.qc = qc;
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
			/* rule from Rillof's paper #3 */
			if(sc.findNER(sc.getNER(sentence), AType.PERSON).isEmpty()){
				if(CommonAnswerer.regexMatcher(sentence, "name")){
					scores.set(i, scores.get(i) + Scores.GOOD_CLUE);
				}
			}
			/* rule from Rillof's paper  #4 */
			if(!sc.findNER(sc.getNER(sentence), AType.PERSON).isEmpty()){
				scores.set(i, scores.get(i) + Scores.GOOD_CLUE);
			}
//			/* rule by Fred #1 */
//			String NN = CommonAnswerer.regexMatcherSentence(question.getQuestion(), "is the (.*)?");
//			if(!NN.isEmpty()){
//				String[] test = NN.split("\\s");
//				if(CommonAnswerer.regexMatcher(sentence, test[0])){
//					scores.set(i, scores.get(i) + Scores.CONFIDENT);
//				}
//			}
		}
		/* answer */
		String bestSentence = story.getSentence(CommonAnswerer.findBest(scores));
		System.out.println(bestSentence);
	}
}
