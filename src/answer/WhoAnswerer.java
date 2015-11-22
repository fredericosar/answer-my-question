package answer;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import com.sun.corba.se.impl.logging.OMGSystemException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import logic.QuestionClassifier;
import logic.StoryClassifier;
import boilerplate.*;
import rules.Scores;
import rules.AnswerRules.AType;

public class WhoAnswerer {

	private Question question;
	private Story story;
	private StoryClassifier sc;
	private ArrayList<Integer> scores;
	private MaxentTagger tagger;

	public WhoAnswerer(Question question, Story story, StoryClassifier sc, ArrayList<Integer> scores, MaxentTagger tagger) {
		this.question = question;
		this.story = story;
		this.sc = sc;
		this.scores = scores;
		this.tagger = tagger;
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
		/* get word after who */
		LinkedHashSet<String> nextWho = sc.findTYPE(question.getQuestion(), AType.WHO_NEXT);
		/* find person */
		if(CommonAnswerer.regexMatcher(question.getQuestion(), "is the (.*)?")){
			LinkedHashSet<String> tags = sc.findTYPE(sc.getNER(bestSentence), AType.PERSON);
			if(!tags.isEmpty()){
				for(String tag : tags) {
					System.out.print(tag + " ");
				}
			}else{
				System.out.println(bestSentence);
			}
//		}else if(!nextWho.isEmpty()){
		}else{
//			String next = nextWho.iterator().next();
			/* check if verb */
//			if(CommonAnswerer.regexMatcher(tagger.tagString(next), "([^ ]+)_VB[A-Z]*")){
//				System.out.println(bestSentence);
//			} else{
				System.out.println(bestSentence);
//			}
		}
	}
}
