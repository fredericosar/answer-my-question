package logic;

import java.util.ArrayList;
import java.util.Map.Entry;

import answer.AnswerChecker;
import boilerplate.*;

public class Controller {

	private Story story;
	private StoryClassifier sc;
	private Questions questions;
	private QuestionClassifier qc;
	private AnswerChecker ac;

	public Controller() throws Exception {
		/* create classifiers */
		qc = new QuestionClassifier();
		sc = new StoryClassifier();
		/* answer checker */
		ac = new AnswerChecker();
	}

	public void processQuestions() {
		/* generate bag of words for story */
		sc.generateBagOfWords(story);
		/* iterate through each story */
		for (Entry<String, Question> entry : questions.getQuestions().entrySet()) {
			/* print question id */
			System.out.println(Questions.QUESTION_ID + ": " + entry.getKey());
			/* tag question with its type */
			qc.regexMatcher(entry.getValue());
			/* generate bags of words for question */
			qc.generateBagOfWords(entry.getValue());
			/* generate NER */
			sc.getNER(story);
			/* generate intersection scores */
			ArrayList<Integer> scores = ScoreGenerator.getIntersectionScores(entry.getValue(), story);
			/* look for question type */
			switch (entry.getValue().getType()) {
			case WHO:
				/* print expected answer if in debug mode */
				if(Main.DEBUG_MODE){
					System.out.println(entry.getValue().getQuestion());
				}
				/* find a person tag */
				int max = 0;
				String ans = "";
				for (int i = 0; i < scores.size(); i++) {
					if(scores.get(i) > 0){
						for(String person : sc.findNER(story)){
							if(story.getSentence(i).contains(person)) {
								if(scores.get(i) > max){
									max = scores.get(i);
									ans = person;
								}
							}
						}
					}
				}
				System.out.println("Answer: " + ans);
				/* print expected answer if in debug mode */
				if(Main.DEBUG_MODE){
					ac.getAnswer(entry.getKey());
				}
				break;
			default:
				break;
			}
		}
	}

	/** Getters and Setters **/
	public void setStory(Story story) {
		this.story = story;
	}

	public void setQuestions(Questions questions) {
		this.questions = questions;
	}
}
