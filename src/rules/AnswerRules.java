package rules;

import java.util.Arrays;
import java.util.List;

public class AnswerRules {
	/* Stanford's NER RULES and TYPES*/
	public enum Type {
		PERSON, LOCATION, DATE;
	};
	/* Identify PERSON */
	private static final List<String> personRule = Arrays.asList("<PERSON>(.*?)</PERSON>");
	/* Identify LOCATION */
	private static final List<String> locationRule = Arrays.asList("<LOCATION>(.*?)</LOCATION>");
	/* Identify DATE */
	private static final List<String> dateRule = Arrays.asList("<DATE>(.*?)</DATE>", "<TIME>(.*?)</TIME>");
	
	/**
	 ** Return a list of regex rules to identify the answer
	 **/
	public static List<String> getNERRule(Type type) {
		if(type == Type.PERSON) {
			return personRule;
		} else if(type == Type.LOCATION){
			return locationRule;
		} else if(type == Type.DATE){
			return dateRule;
		} else {
			return null;
		}
	}
}
