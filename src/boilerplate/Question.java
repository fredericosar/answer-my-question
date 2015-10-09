package boilerplate;

public class Question {

	private String question;

	public enum Type {
		WHICH, WHY, WHOSE, WHAT, HOW, WHEN, WHERE, WHO;
	};

	public Question(String question) {
		this.question = question;
	}

	public String getQuestion() {
		return question;
	}
	
}
