package boilerplate;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;

public class Story {

	private String story;
	private String text;
	private List<List<HasWord>> bagsOfWords;
	
	public Story(String story) {
		this.story = story;
		bagsOfWords = new ArrayList<List<HasWord>>();
		/* get defined text */
		String[] splitText = story.split("TEXT:");
		text = splitText[1].trim();
	}

	/**
	 * Add new bag of word for this story
	 */
	public void addBagOfWords(List<HasWord> bagOfWords){
		bagsOfWords.add(bagOfWords);
	}
	
	/**
	 * Get a sentence for a given position
	 */
	public String getSentence(int i) {
		return Sentence.listToString(bagsOfWords.get(i));
	}

	
	/** Getters and Setters **/
	public String getStory() {
		return story;
	}

	public List<List<HasWord>> getBagsOfWords() {
		return bagsOfWords;
	}

	public String getText() {
		return text;
	}
}
