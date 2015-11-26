package logic;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map.Entry;

import opennlp.tools.parser.*;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import answer.CommonAnswerer;
import answer.HowAnswerer;
import answer.WhatAnswerer;
import answer.WhenAnswerer;
import answer.WhereAnswerer;
import answer.WhichAnswerer;
import answer.WhoAnswerer;
import answer.WhoseAnswerer;
import answer.WhyAnswerer;
import boilerplate.*;

public class Controller {

    private Story story;
    private StoryClassifier sc;
    private Questions questions;
    private QuestionClassifier qc;
    private MaxentTagger tagger;
    private Parser parser;

    public Controller() throws Exception {
        /* load NER classifier */
        String serializedClassifier = "libraries/stanford-ner/classifiers/english.muc.7class.distsim.crf.ser.gz";
        AbstractSequenceClassifier<CoreLabel> NERclassifier = null;
        try {
            NERclassifier = CRFClassifier.getClassifier(serializedClassifier);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* load POSTAGGER models */
        String posModel = "libraries/stanford-postagger/models/english-bidirectional-distsim.tagger";
        tagger = new MaxentTagger(posModel);
        /* load Parser */
        InputStream modelIn = new FileInputStream("libraries/openNLP-parser/models/en-parser-chunking.bin");
        ParserModel model = new ParserModel(modelIn);
        parser = ParserFactory.create(model);
        /* create classifiers */
        qc = new QuestionClassifier(NERclassifier);
        sc = new StoryClassifier(NERclassifier);
    }

    public void processQuestions() {
        /* generate bag of words for story */
        sc.generateBagOfWords(story);
        /* iterate through each story */
        for (Entry<String, Question> entry : questions.getQuestions().entrySet()) {
            String questionID = entry.getKey();
            Question question = entry.getValue();
            /* print question id */
            System.out.println(Questions.QUESTION_ID + ": " + questionID);
            /* tag question with its type */
            qc.regexMatcher(question);
            /* generate bags of words for question */
            qc.generateBagOfWords(question);
            /* generate intersection scores */
            ArrayList<Integer> scores = CommonAnswerer.getIntersectionScores(question, story, tagger);
            /* look for question type */
            System.out.print("Answer: ");
            /* create a generic object */
            switch (entry.getValue().getType()) {
            case WHO:
                WhoAnswerer who = new WhoAnswerer(question, story, sc, scores, tagger);
                who.answer();
                break;
            case WHERE:
                WhereAnswerer where = new WhereAnswerer(story, sc, scores);
                where.answer();
                break;
            case WHEN:
                WhenAnswerer when = new WhenAnswerer(question, story, sc, scores);
                when.answer();
                break;
            case WHAT:
                WhatAnswerer what = new WhatAnswerer(question, story, sc, scores, tagger);
                what.answer();
                break;
            case HOW:
                HowAnswerer how = new HowAnswerer(question, story, sc, scores, tagger);
                how.answer();
                break;
            case WHY:
                WhyAnswerer why = new WhyAnswerer(question, story, sc, scores);
                why.answer();
                break;
            case WHICH:
                WhichAnswerer which = new WhichAnswerer(question, story, sc, scores);
                which.answer();
                break;
            case WHOSE:
                WhoseAnswerer whose = new WhoseAnswerer(question, story, sc, scores);
                whose.answer();
                break;
            default:
                System.out.println();
                break;
            }
            System.out.println();
        }
    }

    /** Getters and Setters **/
    public void setStory(Story story) {
        this.story = story;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }
}
