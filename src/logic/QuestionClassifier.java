package logic;

import java.util.*;
import java.util.regex.*;

import rules.*;
import boilerplate.*;
import boilerplate.Question.Type;

public class QuestionClassifier {

//	private Question question;
	private Map<Type, List<String>> rules;

	public QuestionClassifier() {
		rules = new HashMap<Type, List<String>>();
		getRules();
	}

	/** Getters and Setters **/
	public void setQuestion(Question question) {
//		this.question = question;
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
	public void regexMatcher(Question question) {
		boolean isMatch = false;
		for (Type type : rules.keySet()) {
			for (String regex : rules.get(type)) {
				Pattern pattern = Pattern.compile(regex);
				Matcher regexMatcher = pattern.matcher(question.getQuestion());
				while (regexMatcher.find()) {
					question.addType(type);
					isMatch = true;
				}
			}
		}
		if (!isMatch)
			System.out.println(question.getQuestion());
	}

}
