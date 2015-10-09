package rules;

import java.util.*;
import boilerplate.Question.*;

public class QuestionRules {
	/** REGEX Identification */
	/* Identify WHICH */
	private static final List<String> whichRules = Arrays.asList("^Which");
	/* Identify WHY */
	private static final List<String> whyRules = Arrays.asList("^Why");
	/* Identify WHOSE */
	private static final List<String> whoseRules = Arrays.asList("^Whose");
	/* Identify WHAT */
	private static final List<String> whatRules = Arrays.asList("^What");
	/* Identify HOW */
	private static final List<String> howRules = Arrays.asList("^How");
	/* Identify WHEN */
	private static final List<String> whenRules = Arrays.asList("^When");
	/* Identify WHERE */
	private static final List<String> whereRules = Arrays.asList("^Where");
	/* Identify WHO */
	private static final List<String> whoRules = Arrays.asList("^Who");

	/**
	 ** Return a list of rules for the
	 **/
	public static List<String> getRules(Type type) {
		if (type == Type.WHICH)
			return whichRules;
		if (type == Type.WHY)
			return whyRules;
		if (type == Type.WHOSE)
			return whoseRules;
		if (type == Type.WHAT)
			return whatRules;
		if (type == Type.HOW)
			return howRules;
		if (type == Type.WHEN)
			return whenRules;
		if (type == Type.WHERE)
			return whereRules;
		else
			return whoRules;
	}
}