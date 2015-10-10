package boilerplate;

public class Story {

	private String story;
	private String NERStory;
	
	public Story(String story) {
		this.story = story;
	}

	/* Getters and Setters */
	public String getStory() {
		return story;
	}

	public String getNERStory() {
		return NERStory;
	}
	
	public void setNERStory(String NERStory) {
		this.NERStory = NERStory;
	}

}
