package answer;

import java.io.*;
import java.util.*;

public class AnswerChecker {

	private Map<String, String> answers;
	
	public AnswerChecker() {
		answers = new HashMap<String, String>();
		try (Scanner words = new Scanner(new File("answers"))) {
			boolean isQuestion = true;
			String question = null, answer = null;
			while (words.hasNextLine()) {
				if(isQuestion) {
					question = words.nextLine().split("QuestionID: ")[1];
					isQuestion = false;
				} else {
					answer = words.nextLine().split("Answer:")[1];
					isQuestion = true;
				}
				answers.put(question, answer);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void getAnswer(String questionID){
		System.out.println("Expected:" + answers.get(questionID));
	}
	
}
