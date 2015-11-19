package logic;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rules.AnswerRules;
import rules.AnswerRules.AType;
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
		
		/* tests */
//		try {
//			InputStream is = new FileInputStream("libraries/openNLP/models/en-sent.bin");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		/* process sentences */
		DocumentPreprocessor dp = new DocumentPreprocessor(new StringReader(story.getText().replace("'", "")));
		for (List<HasWord> sentence : dp) {
			story.addBagOfWords(sentence);
		}
	}
	
	/**
	 * Get NER for the given text
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
