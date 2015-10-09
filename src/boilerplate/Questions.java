package boilerplate;
import java.util.*;


public class Questions {

	private static final String QUESTION_ID = "QuestionID";
	private static final String QUESTION = "Question";
	
	private Map<String, Question> questions;
	private boolean hasID;
	private String temporaryID;
	
	/* Getters and Setters */
	public Map<String, Question> getQuestions() {
		return questions;
	}
	
	public Questions(){
		questions = new TreeMap<String, Question>();
		hasID = false;
	}
	
	public void addQuestion(String data){
		String[] text = data.split(":");
		if(text[0].equals(QUESTION) || text[0].equals(QUESTION_ID)){
			saveQuestion(text[1].trim());
		}
	}
	
	private void saveQuestion(String data){
		if(!hasID){
			temporaryID = data;
			hasID = true;
		}else{
			questions.put(temporaryID, new Question(data));
			hasID = false;
		}
	}
}