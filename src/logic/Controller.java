package logic;

import boilerplate.*;

public class Controller {

	private Story story;
	private StoryClassifier sc;
	private Questions questions;
	private QuestionClassifier qc;

	public Controller() throws Exception {
		/* create classifiers */
		qc = new QuestionClassifier();
		sc = new StoryClassifier();
	}

	public void processQuestions() {
		/* generate bag of words for story */
		sc.generateBagOfWords(story);
		/* iterate through each story */
		for (Question question : questions.getQuestions().values()) {
			/* tag question with its type */
			qc.regexMatcher(question);
			/* generate bags of words for question */
			qc.generateBagOfWords(question);
			/* generate NER */
			sc.getNER(story);
			/* generate intersection scores */
			sc.getIntersectionScores(question, story);
			/* look for question type */
			switch (question.getType()) {
			case WHO:
				System.out.println(question.getQuestion());
				/* find a person tag */
				int max = 0;
				String ans = "";
				for (int i = 0; i < story.getIntersectionScore().size(); i++) {
					if(story.getIntersectionScore().get(i) > 0){
						for(String person : sc.findNER(story)){
							if(story.getSentence(i).contains(person)) {
								if(story.getIntersectionScore().get(i) > max){
									max = story.getIntersectionScore().get(i);
									ans = person;
								}
							}
						}
					}
				}
				System.out.println(ans);
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
