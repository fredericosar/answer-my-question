package answer;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import com.sun.corba.se.impl.logging.OMGSystemException;

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
			if(sc.findTYPE(sc.getNER(sentence), AType.PERSON).isEmpty()){
				if(CommonAnswerer.regexMatcher(sentence, "name")){
					scores.set(i, scores.get(i) + Scores.GOOD_CLUE);
				}
			}
			/* rule from Rillof's paper  #4 */
			if(!sc.findTYPE(sc.getNER(sentence), AType.PERSON).isEmpty()){
				scores.set(i, scores.get(i) + Scores.GOOD_CLUE);
			}
		}
		
		/* answer */
		String bestSentence = story.getSentence(CommonAnswerer.findBest(scores));
		if(CommonAnswerer.regexMatcher(question.getQuestion(), "is the (.*)?")){
			LinkedHashSet<String> tags = sc.findTYPE(sc.getNER(bestSentence), AType.PERSON);
			if(!tags.isEmpty()){
				for(String tag : tags) System.out.print(tag + " ");
			}else{
				System.out.print(bestSentence);
			}
			System.out.println();
		}else {
			System.out.println(bestSentence);
		}
	}
}
