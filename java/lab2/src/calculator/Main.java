package calculator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

import exceptions.ParserException;
import exceptions.WorkerException;
import utility.Command;
import utility.CommandParser;
import workers.WorkerFactory;
import workers.Worker;

public class Main {
	
	public static void main(String[] args){
		
		LoggerWrapper loggerWrapper = null;
		
		try {
			loggerWrapper = LoggerWrapper.getInstance();
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}
		
		String fileName = null;
		if (args.length == 0) {
			Scanner keyboardScanner = new Scanner(System.in);
			
			fileName = keyboardScanner.nextLine();
			
			keyboardScanner.close();
			loggerWrapper.logger.info("Path to file with commands came from keyboard.");
		}
		else {
			fileName = args[0];
			loggerWrapper.logger.info("Path to file with commands came from arg line.");
		}
		
		Stack<Double> stack = new Stack<Double>();
		HashMap<String, Double> varBase = new HashMap<String, Double>();
		
		WorkerFactory workerFactory = new WorkerFactory();
		
		CommandParser commandParser = new CommandParser();
		
		Path pathToFile = Paths.get(fileName);
		try {
			Scanner fileScanner = new Scanner(pathToFile);
			while (fileScanner.hasNextLine()) {
				Command command = commandParser.parseCommand(fileScanner.nextLine());
				Worker worker = WorkerFactory.createWorker(command);
				worker.execute(stack, varBase);
			}
			fileScanner.close();
		}
		catch (IOException | ParserException | WorkerException e) {
			System.err.println(e.toString());
		}
		loggerWrapper.logger.fine("All commands have been executed!");
	}

}

// /home/lyect/Desktop/OOOP/java/lab2/src/tests/testFile1.txt

