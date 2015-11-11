package boilerplate;

import java.util.*;
import edu.stanford.nlp.ling.HasWord;

public class Question {

	private String question;
	private List<QType> types;
	private List<HasWord> bagOfWords;
	
	public enum QType {
		WHICH, WHY, WHOSE, WHAT, HOW, WHEN, WHERE, WHO, UNKNOWN;
	};

	/**
	 * Creates a typed question
	 * based on a string given,
	 * a bag of words
	 */
	public Question(String question) {
		this.question = question;
		types = new ArrayList<QType>();
	}
	
	/**
	 * Return the type of the question
	 */
	public QType getType() {
		try{
			return types.get(0); /* TODO: Allow multiple types */
		}catch(IndexOutOfBoundsException e){
			return QType.UNKNOWN;
		}
	}
	
	/** Getters and Setters **/
	public String getQuestion() {
		return question;
	}

	public void addType(QType type) {
		types.add(type);
	}
	
	public void setBagOfWords(List<HasWord> bagOfWords){
		this.bagOfWords = bagOfWords;
	}
	
	public List<HasWord> getBagOfWords() {
		return bagOfWords;
	}
}
