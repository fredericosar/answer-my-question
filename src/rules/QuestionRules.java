package rules;

import java.util.*;
import boilerplate.Question.*;

public class QuestionRules {
    /* REGEX Identification */
    /* Identify WHICH */
    private static final List<String> whichRules = Arrays.asList("^Which", ".*?\\bwhich\\b.*?");
    /* Identify WHY */
    private static final List<String> whyRules = Arrays.asList("^Why", ".*?\\bwhy\\b.*?");
    /* Identify WHAT */
    private static final List<String> whatRules = Arrays.asList("^What", ".*?\\bwhat\\b.*?");
    /* Identify HOW */
    private static final List<String> howRules = Arrays.asList("^How", ".*?\\bhow\\b.*?");
    /* Identify WHEN */
    private static final List<String> whenRules = Arrays.asList("^When", ".*?\\bwhen\\b.*?");
    /* Identify WHERE */
    private static final List<String> whereRules = Arrays.asList("^Where", ".*?\\bwhere\\b.*?");
    /* Identify WHO */
    private static final List<String> whoRules = Arrays.asList("^Who", ".*?\\bwho\\b.*?");

    /**
     ** Return a list of regex rules to identify the question
     **/
    public static List<String> getRules(QType type) {
        if (type == QType.WHICH)
            return whichRules;
        if (type == QType.WHY)
            return whyRules;
        if (type == QType.WHAT)
            return whatRules;
        if (type == QType.HOW)
            return howRules;
        if (type == QType.WHEN)
            return whenRules;
        if (type == QType.WHERE)
            return whereRules;
        if (type == QType.WHO)
            return whoRules;
        else
            return new ArrayList<String>();
    }
}