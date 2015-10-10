package rules;

import java.util.Arrays;
import java.util.List;

public class AnswerRules {
	/* Stanford's NER RULES */
	/* Identify PERSON */
	private static final List<String> personRule = Arrays.asList("<PERSON>.*?</PERSON>");
	/* Identify LOCATION */
	private static final List<String> locationRule = Arrays.asList("<LOCATION>(.*?)</LOCATION>");
	
	/**
	 ** Return a list of regex rules to identify the answer
	 **/
	public static List<String> getNERRule() {
		return locationRule;
	}
}
