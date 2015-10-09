package rules;

import java.util.*;

import boilerplate.*;
import boilerplate.Question.*;

public class QuestionRules {
	/** REGEX Identification */
	/* Identify WHEN */
	private static final List<String> whenRules = Arrays.asList("^When");
	
	/**
	 ** Return a list of rules for the 
	 **/
	public static List<String> getRules(Type type){
		if(type == Type.WHEN) return whenRules;
		else return null;
	}
}