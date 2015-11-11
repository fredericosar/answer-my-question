package logic;

import java.io.StringReader;
import java.util.*;
import java.util.regex.*;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import rules.*;
import rules.AnswerRules.AType;
import boilerplate.*;
import boilerplate.Question.QType;


public class QuestionClassifier {
	
	private Map<QType, List<String>> rules;
	private AbstractSequenceClassifier<CoreLabel> classifier;
	
	public QuestionClassifier(AbstractSequenceClassifier<CoreLabel> classifier){
		this.classifier = classifier;
		rules = new HashMap<QType, List<String>>();
		getRules();
	}

	/**
	 * Get all possible types for questions
	 */
	private void getRules() {
		for (QType type : QType.values()) {
			rules.put(type, QuestionRules.getRules(type));
		}
	}

	/**
	 * Regex Matcher for preset rules
	 */
	public void regexMatcher(Question question) {
//		boolean isMatch = false;
		for (QType type : rules.keySet()) {
			for (String regex : rules.get(type)) {
				Pattern pattern = Pattern.compile(regex);
				Matcher regexMatcher = pattern.matcher(question.getQuestion());
				while (regexMatcher.find()) {
					question.addType(type);
//					isMatch = true;
				}
			}
		}
		/* print question we didn't match */
		/* TODO: Remove on final version */
//		if (!isMatch)
//			System.out.println(question.getQuestion());
	}
	
	/**
	 * Generate bag of words
	 */
	public void generateBagOfWords(Question question) {
		/* process sentences */
		DocumentPreprocessor dp = new DocumentPreprocessor(new StringReader(question.getQuestion().replace("'", "")));
		for (List<HasWord> words : dp) {
			question.setBagOfWords(words);
		}
	}

	/**
	 * Get NER for the given question
	 */
	public String getNER(String text) {
		/* get NER for text */
		return classifier.classifyWithInlineXML(text);
	}
	
	/**
	 * Find NER tags based on the given rule type
	 */
	public ArrayList<String> findNER(String text, AType type) {
		ArrayList<String> tags = new ArrayList<>();
		for (String regex : AnswerRules.getNERRule(type)) {
			Pattern pattern = Pattern.compile(regex);
			Matcher regexMatcher = pattern.matcher(text);
			while (regexMatcher.find()) {
				tags.add(regexMatcher.group(1));
			}
		}
		return tags;
	}

}
