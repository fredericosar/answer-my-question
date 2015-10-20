package boilerplate;

import java.util.*;

import edu.stanford.nlp.ling.HasWord;

public class Question {

	private String question;
	private List<Type> types;
	private List<HasWord> bagOfWords;
	
	public enum Type {
		WHICH, WHY, WHOSE, WHAT, HOW, WHEN, WHERE, WHO;
	};

	/**
	 * Creates a typed question
	 * based on a string given,
	 * a bag of words
	 */
	public Question(String question) {
		this.question = question;
		types = new ArrayList<Question.Type>();
	}
	
	/**
	 * Return the type of the question
	 */
	public Type getType() {
		return types.get(0); /* TODO: Allow multiple types */
	}
	
	/** Getters and Setters **/
	public String getQuestion() {
		return question;
	}

	public void addType(Type type) {
		types.add(type);
	}
	
	public void setBagOfWords(List<HasWord> bagOfWords){
		this.bagOfWords = bagOfWords;
	}
	
	public List<HasWord> getBagOfWords() {
		return bagOfWords;
	}
	
}
