package answer;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import logic.StoryClassifier;
import rules.AnswerRules.AType;
import rules.Scores;
import boilerplate.Question;
import boilerplate.Story;

public class WhatAnswerer {

	private Question question;
	private Story story;
	private StoryClassifier sc;
	private ArrayList<Integer> scores;
	private MaxentTagger tagger;

	public WhatAnswerer(Question question, Story story, StoryClassifier sc, ArrayList<Integer> scores, MaxentTagger tagger) {
		this.question = question;
		this.story = story;
		this.scores = scores;
		this.sc = sc;
		this.tagger = tagger;
	}
	
	/**
	 * Answer the WHAT question
	 */
	public void answer(){
		for(int i = 0; i < story.getBagsOfWords().size(); i++){
			/* get sentence */
			String sentence = story.getSentence(i);
			/* rule #2 */
			if(CommonAnswerer.regexMatcher(question.getQuestion(), "January|February|March|April|May|June|July|August|September|October|November|December")){
				if(CommonAnswerer.regexMatcher(sentence, "today|yesterday|tomorrow|last night")){
					scores.set(i, scores.get(i) + Scores.CLUE);
				}
			}
			/* rule #3 */
			if(CommonAnswerer.regexMatcher(question.getQuestion(), "kind")){
				if(CommonAnswerer.regexMatcher(sentence, "call|from")){
					scores.set(i, scores.get(i) + Scores.GOOD_CLUE);
				}
			}
			/* rule #4 */
			if(CommonAnswerer.regexMatcher(question.getQuestion(), "name")){
				if(CommonAnswerer.regexMatcher(sentence, "name|call|known")){
					scores.set(i, scores.get(i) + Scores.SLAM_DUNK);
				}
			}
			/* rule 5 */
			// @TODO: Finish implementation 
//			if(CommonAnswerer.regexMatcher(question.getQuestion(), "name")){
//				LinkedHashSet<String> proper_nouns = sc.findTYPE(tagger.tagString(sentence), AType.PROPER_NOUN);
//				if(!proper_nouns.isEmpty()){
//					
//				}
//			}
		}
		/* answer */
		String bestSentence = story.getSentence(CommonAnswerer.findBest(scores));
		System.out.println(bestSentence);
	}
	
}
