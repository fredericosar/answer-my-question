package answer;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.washington.cs.knowitall.morpha.MorphaStemmer;
import logic.StoryClassifier;
import boilerplate.*;
import rules.Scores;
import rules.AnswerRules.AType;

public class WhoAnswerer {

    private Question question;
    private Story story;
    private StoryClassifier sc;
    private ArrayList<Integer> scores;
    private MaxentTagger tagger;

    public WhoAnswerer(Question question, Story story, StoryClassifier sc, ArrayList<Integer> scores, MaxentTagger tagger) {
        this.question = question;
        this.story = story;
        this.sc = sc;
        this.scores = scores;
        this.tagger = tagger;
    }

    /**
     * Answer the WHO question
     */
    public void answer() {
        for (int i = 0; i < story.getBagsOfWords().size(); i++) {
            /* get sentence */
            String sentence = story.getSentence(i);
            /* rule from Rillof's paper #3 */
            if (sc.findTYPE(sc.getNER(sentence), AType.PERSON).isEmpty()) {
                if (CommonAnswerer.regexMatcher(sentence, "name")) {
                    scores.set(i, scores.get(i) + Scores.GOOD_CLUE);
                }
            }
            /* rule from Rillof's paper #4 */
            if (!sc.findTYPE(sc.getNER(sentence), AType.PERSON).isEmpty()) {
                scores.set(i, scores.get(i) + Scores.GOOD_CLUE);
            }
        }

        /* answer */
        String bestSentence = story.getSentence(CommonAnswerer.findBest(scores));
        /* find person */
        if (CommonAnswerer.regexMatcher(question.getQuestion(), "is the (.*)?")) {
            LinkedHashSet<String> words = sc.findTYPE(sc.getNER(bestSentence), AType.PERSON);
            if (!words.isEmpty()) {
                for (String word : words) {
                    bestSentence = word + " ";
                }
            }
        } else {
            /* Promising - needs improvement */
            /* get word after who */
            LinkedHashSet<String> nextWho = sc.findTYPE(question.getQuestion(), AType.WHO_NEXT);
            if (!nextWho.isEmpty()) {
                String[] wordsMatched = nextWho.iterator().next().split("\\s");
                int startingPosition = 0;
                if (CommonAnswerer.regexMatcher(tagger.tagString(wordsMatched[startingPosition]), "([^ ]+)_VB[A-Z]*")) {
                    String result = "";
                    String[] wordsSentence = bestSentence.split("\\s");
                    for (int i = 0; i < wordsSentence.length; i++) {
                        if (MorphaStemmer.morpha(wordsSentence[i], false).equals(MorphaStemmer.morpha(wordsMatched[startingPosition], false))) {
                            if (!MorphaStemmer.morpha(wordsSentence[i], false).equals("be")) {
                                startingPosition++;
                            }
                        } else {
                            if (startingPosition != 0) {
                                break;
                            }
                            result += wordsSentence[i] + " ";
                        }
                    }
                    if (startingPosition > 0) {
                        bestSentence = result;
                    }
                }
            }
        }
        /* print the best sentence */
        System.out.println(bestSentence);
    }
}
