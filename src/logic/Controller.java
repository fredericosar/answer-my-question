package logic;

import boilerplate.*;
import static boilerplate.Question.Type;

public class Controller {

	private Story story;
	private Questions questions;

	public Controller(Story story, Questions questions) {
		this.story = story;
		this.questions = questions;
		startAnswering();
	}

	private void startAnswering() {
		QuestionClassification qc = new QuestionClassification();
		for (Question question : questions.getQuestions().values()) {
			qc.test(question);
		}
	}
}
