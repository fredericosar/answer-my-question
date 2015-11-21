package rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnswerRules {
	/* Stanford's NER RULES and TYPES*/
	public enum AType {
		PERSON, LOCATION, DATE, MONEY, PERCENT,
		NUMBER;
	};
	/* Stanford's TAGs */
	private static final List<String> numberRule = Arrays.asList("(\\w+)_CD");
	
	/* Identify PERSON */
	private static final List<String> personRule = Arrays.asList("<PERSON>(.*?)</PERSON>");
	/* Identify LOCATION */
	private static final List<String> locationRule = Arrays.asList("<LOCATION>(.*?)</LOCATION>");
	/* Identify DATE */
	private static final List<String> dateRule = Arrays.asList("<DATE>(.*?)</DATE>", "<TIME>(.*?)</TIME>");
	/* Identify MONEY */
	private static final List<String> moneyRule = Arrays.asList("<MONEY>(.*?)</MONEY>");
	/* Identify MONEY */
	private static final List<String> percentRule = Arrays.asList("<PERCENT>(.*?)</<PERCENT>>");
	
	/**
	 ** Return a list of regex rules to identify the answer
	 **/
	public static List<String> getNERRule(AType type) {
		if(type == AType.PERSON) {
			return personRule;
		} else if(type == AType.LOCATION){
			return locationRule;
		} else if(type == AType.DATE){
			return dateRule;
		}else if(type == AType.MONEY){
			return moneyRule;
		}else if(type == AType.PERCENT){
			return percentRule;
		}else if(type == AType.NUMBER){
			return numberRule;
		}else {
			return new ArrayList<String>();
		}
	}
}
