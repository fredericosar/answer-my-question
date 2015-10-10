package logic;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rules.AnswerRules;
import boilerplate.Story;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class StoryClassifier {

	/* STANFORD NER */
	private AbstractSequenceClassifier<CoreLabel> classifier;
	private String serializedClassifier = "libraries/stanford-ner/classifiers/english.muc.7class.distsim.crf.ser.gz";
	private List<String> NERrules;

	public StoryClassifier() throws Exception {
		// load classifier
		classifier = CRFClassifier.getClassifier(serializedClassifier);
		// load NER Rules
		NERrules = new ArrayList<String>();
		getNERRules();
	}
	
	/**
	 * Classify story
	 */
	public void classifyStory(Story story) throws Exception {
		/* Stanford's NER */
		story.setNERStory(classifier.classifyWithInlineXML(story.getStory()));
	}

	private void getNERRules() {
		NERrules = AnswerRules.getNERRule();
	}

	/**
	 * Regex matcher on story's NER for preset rules
	 * on given story
	 */
	public void findNER(Story story) {
		for (String regex : NERrules) {
			Pattern pattern = Pattern.compile(regex);
			Matcher regexMatcher = pattern.matcher(story.getNERStory());
			while (regexMatcher.find()) {
				System.out.println(regexMatcher.group(1));
			}
		}
	}
}
