package boilerplate;

import java.util.*;

public class Question {

	private String question;
	private List<Type> types;

	public enum Type {
		WHICH, WHY, WHOSE, WHAT, HOW, WHEN, WHERE, WHO;
	};

	public Question(String question) {
		this.question = question;
		types = new ArrayList<Question.Type>();
	}

	/* Getters and Setters */
	public String getQuestion() {
		return question;
	}

	public void addType(Type type) {
		types.add(type);
	}

	/**
	 * Return the type of the question
	 */
	public Type getType() {
		return types.get(0);
	}
}
