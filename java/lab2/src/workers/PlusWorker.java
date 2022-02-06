package workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import calculator.LoggerWrapper;
import exceptions.WorkerException;

public class PlusWorker implements Worker{
	LoggerWrapper loggerWrapper = null;
	ArrayList<String> args;
	
	public PlusWorker(ArrayList<String> newArgs) throws WorkerException {

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
		if (stack.size() < 2) {
			WorkerException e = new WorkerException(this.getClass().getName(), 
					"There is no operands in stack for execution");
			loggerWrapper.logger.severe(e.toString());
			throw e;
		}
		Double fArg = stack.pop();
		Double sArg = stack.pop();
		stack.push(fArg + sArg);
	}
}
