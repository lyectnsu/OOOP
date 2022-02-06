package workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import calculator.LoggerWrapper;
import exceptions.WorkerException;

public class PrintWorker implements Worker{
	LoggerWrapper loggerWrapper = null;
	ArrayList<String> args;
	
	public PrintWorker(ArrayList<String> newArgs) throws WorkerException {
		
		try {
			loggerWrapper = LoggerWrapper.getInstance();
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		
		if (newArgs.size() >= 1) {
			WorkerException e = new WorkerException(this.getClass().getName(), "Too much arguments");
			loggerWrapper.logger.severe(e.toString());
			throw e;
		}
		this.args = newArgs;
		loggerWrapper.logger.info(this.getClass().getName() + " constructed!");
	}
	
	public void execute(Stack<Double> stack, HashMap<String, Double> varBase) throws WorkerException {
		if (stack.size() == 0) {
			WorkerException e = new WorkerException(this.getClass().getName(), 
					"Stack is empty. Can not print anything.");
			loggerWrapper.logger.severe(e.toString());
			throw e;
		}
		System.out.println(stack.peek());
	}
}
