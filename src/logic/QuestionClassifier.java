package logic;

import java.io.StringReader;
import java.util.*;
import java.util.regex.*;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import rules.*;
import boilerplate.*;
import boilerplate.Question.Type;


public class QuestionClassifier {

	private AbstractSequenceClassifier<CoreLabel> classifier;
	private Map<Type, List<String>> rules;
	
	public QuestionClassifier(AbstractSequenceClassifier<CoreLabel> classifier){
		this.classifier = classifier;
		rules = new HashMap<Type, List<String>>();
		getRules();
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
		/* print question we didn't match */
		/* TODO: Remove on final version */
		if (!isMatch)
			System.out.println(question.getQuestion());
	}

	/**
	 * Generate bag of words
	 */
	public void generateBagOfWords(Question question) {
		/* process sentences */
		DocumentPreprocessor dp = new DocumentPreprocessor(new StringReader(question.getQuestion()));
		for (List<HasWord> words : dp) {
			question.setBagOfWords(words);
		}
	}
	
	/**
	 * Get NER for the given story
	 */
	public void getNER(Question question) {
		/* classify story */
		question.setNER(classifier.classifyWithInlineXML(question.getQuestion()));
	}

}
