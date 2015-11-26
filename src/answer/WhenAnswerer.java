package answer;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import logic.StoryClassifier;
import rules.Scores;
import rules.AnswerRules.AType;
import boilerplate.Question;
import boilerplate.Story;

public class WhenAnswerer {

    private Question question;
    private Story story;
    private StoryClassifier sc;
    private ArrayList<Integer> scores;

    public WhenAnswerer(Question question, Story story, StoryClassifier sc, ArrayList<Integer> scores) {
        this.question = question;
        this.story = story;
        this.sc = sc;
        this.scores = scores;
    }

    /**
     * Answer the WHEN question
     */
    public void answer() {
        ArrayList<Integer> innerScore = new ArrayList<Integer>();
        for (int i = 0; i < story.getBagsOfWords().size(); i++) {
            innerScore.add(0);
            /* get sentence */
            String sentence = story.getSentence(i);
            /* rule #1 */
            if (!sc.findTYPE(sc.getNER(sentence), AType.DATE).isEmpty()) {
                innerScore.set(i, innerScore.get(i) + Scores.GOOD_CLUE + scores.get(i));
            }
            /* rule #2 */
            if (CommonAnswerer.regexMatcher(question.getQuestion(), "the last")) {
                if (CommonAnswerer.regexMatcher(sentence, "first|last|since|ago")) {
                    innerScore.set(i, innerScore.get(i) + Scores.SLAM_DUNK);
                }
            }
            /* rule #3 */
            if (CommonAnswerer.regexMatcher(question.getQuestion(), "start|begin")) {
                if (CommonAnswerer.regexMatcher(sentence, "start|begin|since|year")) {
                    innerScore.set(i, innerScore.get(i) + Scores.SLAM_DUNK);
                }
            }
        }
        /* answer */
        String bestSentence = story.getSentence(CommonAnswerer.findBest(innerScore));
        LinkedHashSet<String> tags = sc.findTYPE(sc.getNER(bestSentence), AType.DATE);
        if (!tags.isEmpty()) {
            for (String tag : tags)
                System.out.print(tag + " ");
        } else {
            System.out.print(bestSentence);
        }
        System.out.println();
    }

}
