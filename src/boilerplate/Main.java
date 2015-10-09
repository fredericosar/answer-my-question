package boilerplate;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import logic.*;

public class Main {

	private static final String STORY = ".story";
	private static final String QUESTION = ".questions";
	
	public static void main(String[] args) throws Exception {
		// Argument handling
		if (args.length < 1)
			throw new IllegalArgumentException("An arguments is required. <inputfile>");

		// File Handling
		File inputFile = new File(args[0]);
		if (!inputFile.isFile())
			throw new IllegalArgumentException("The file path is not valid.");

		Scanner fileScanner = new Scanner(inputFile);
		String directory = fileScanner.next();
		
		// iterate on each story
		while(fileScanner.hasNext()){
			// read StoryID
			String storyID = fileScanner.next();
			
			// save history
			File fStory = new File(directory + storyID + STORY);
			Story story = new Story(new String(Files.readAllBytes(fStory.toPath())));
			
			// save questions
			Questions questions = new Questions();
			File question = new File(directory + storyID + QUESTION);
			Files.lines(question.toPath()).forEachOrdered(questions::addQuestion);

			// send it to controller
			new Controller(story, questions);
		}

		fileScanner.close();
	}

}