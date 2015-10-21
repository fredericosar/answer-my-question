package logic;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rules.AnswerRules;
import rules.AnswerRules.Type;
import boilerplate.Story;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;

public class StoryClassifier {
	
	private AbstractSequenceClassifier<CoreLabel> classifier;
	
	public StoryClassifier(AbstractSequenceClassifier<CoreLabel> classifier){
		this.classifier = classifier;
	}
	
	/**
	 * Generate bags of words
	 */
	public void generateBagOfWords(Story story) {
		/* process sentences */
		DocumentPreprocessor dp = new DocumentPreprocessor(new StringReader(story.getText()));
		for (List<HasWord> sentence : dp) {
			story.addBagOfWords(sentence);
		}
	}
	
	/**
	 * Get NER for the given story
	 */
	public void getNER(Story story) {
		/* classify story */
		story.setNER(classifier.classifyWithInlineXML(story.getStory()));
	}
	
	/**
	 * Find NER tags based on the given rule type
	 */
	public ArrayList<String> findNER(Story story, Type type) {
		ArrayList<String> tags = new ArrayList<>();
		for (String regex : AnswerRules.getNERRule(type)) {
			Pattern pattern = Pattern.compile(regex);
			Matcher regexMatcher = pattern.matcher(story.getNERStory());
			while (regexMatcher.find()) {
				tags.add(regexMatcher.group(1));
			}
		}
		return tags;
	}
}
