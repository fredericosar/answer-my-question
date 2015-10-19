package boilerplate;

import java.util.*;

public class Question {

	private String question;
	private List<Type> types;

	public enum Type {
		WHICH, WHY, WHOSE, WHAT, HOW, WHEN, WHERE, WHO;
	};

	/**
	 * Creates a typed question
	 * based on a string given
	 */
	public Question(String question) {
		this.question = question;
		types = new ArrayList<Question.Type>();
	}

	/**
	 * Generate a Bag-of-Words
	 */
	private void bagOfWords(){
		
	}
	
	/**
	 * Return the type of the question
	 */
	public Type getType() {
		return types.get(0); //TODO: Allow multiple types
	}
	
	/* Getters and Setters */
	public String getQuestion() {
		return question;
	}

	public void addType(Type type) {
		types.add(type);
	}
}
