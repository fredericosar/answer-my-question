package logic;

import java.util.*;
import java.util.regex.*;

import rules.*;
import boilerplate.*;
import boilerplate.Question.Type;

public class QuestionClassification {

	private Question question;
	private Map<Type, List<String>> rules;

	public QuestionClassification() {
		rules = new HashMap<Type, List<String>>();
		getRules();
	}

	/** Getters and Setters **/
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * Get all possible types for questions
	 */
	private void getRules() {
		for (Type type : Type.values()) {
			rules.put(type, QuestionRules.getRules(type));
		}
	}

	/**
	 * Regex Matcher for preset rules
	 */
	public void test(Question question) {
		
		for(Type type : rules.keySet()){
			if(rules.get(type) != null) 
			for(String regex : rules.get(type)){
				Pattern pattern = Pattern.compile(regex);
				Matcher regexMatcher = pattern.matcher(question.getQuestion());
				while (regexMatcher.find()) {
					System.out.println(regexMatcher.group());
			}
		}
	}

}