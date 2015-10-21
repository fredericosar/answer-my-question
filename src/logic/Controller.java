package logic;

import java.util.ArrayList;
import java.util.Map.Entry;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import answer.CommonAnswerer;
import answer.WhenAnswerer;
import answer.WhereAnswerer;
import answer.WhoAnswerer;
import boilerplate.*;

public class Controller {

	private Story story;
	private StoryClassifier sc;
	private Questions questions;
	private QuestionClassifier qc;

	public Controller() throws Exception {
		/* load NER classifier */
		String serializedClassifier = "libraries/stanford-ner/classifiers/english.muc.7class.distsim.crf.ser.gz";
		AbstractSequenceClassifier<CoreLabel> NERclassifier = null;
		try {
			NERclassifier = CRFClassifier.getClassifier(serializedClassifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* create classifiers */
		qc = new QuestionClassifier(NERclassifier);
		sc = new StoryClassifier(NERclassifier);
	}

	public void processQuestions() {
		/* generate bag of words for story */
		sc.generateBagOfWords(story);
		/* iterate through each story */
		for (Entry<String, Question> entry : questions.getQuestions().entrySet()) {
			String questionID = entry.getKey();
			Question question = entry.getValue();
			/* print question id */
			System.out.println(Questions.QUESTION_ID + ": " + questionID);
			/* tag question with its type */
			qc.regexMatcher(question);
			/* generate bags of words for question */
			qc.generateBagOfWords(question);
			/* POS tag */
			/* NER Classifier */
			sc.getNER(story);
			/* generate intersection scores */
			ArrayList<Integer> scores = CommonAnswerer.getIntersectionScores(question, story);
			/* look for question type */
			System.out.print("Answer:" );
			/* create a generic object */
			switch (entry.getValue().getType()) {
			case WHO:
				WhoAnswerer who = new WhoAnswerer(question, story, sc);
				who.setScores(scores);
				who.answer();
				break;
			case WHERE:
				WhereAnswerer where = new WhereAnswerer(question, story, sc);
				where.setScores(scores);
				where.answer();
				break;
			case WHEN:
				WhenAnswerer when = new WhenAnswerer(question, story, sc);
				when.setScores(scores);
				when.answer();
				break;
			default:
				System.out.println();
				break;
			}
			System.out.println();
		}
	}

	/** Getters and Setters **/
	public void setStory(Story story) {
		this.story = story;
	}

	public void setQuestions(Questions questions) {
		this.questions = questions;
	}
}
