package logic;

import boilerplate.*;
import static boilerplate.Question.Type;

public class Controller {

	private StoryClassifier sc;
	private Story story;
	private Questions questions;

	public Controller(StoryClassifier sc, Story story, Questions questions) throws Exception {
		this.sc = sc;
		this.story = story;
		this.questions = questions;
		// answer the question
		answerQuestions();
	}

	private void answerQuestions() {
		QuestionClassifier qc = new QuestionClassifier();
		for (Question question : questions.getQuestions().values()) {
			// tag question with its type
			qc.regexMatcher(question);
			// answer question based on the type
			if (question.getType() == Type.WHERE){
				System.out.println(question.getQuestion());
//				sc.findNER(story);
			}
		}
	}
}
