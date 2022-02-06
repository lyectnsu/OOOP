package workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import calculator.LoggerWrapper;
import exceptions.WorkerException;

public class PushWorker implements Worker{
	LoggerWrapper loggerWrapper = null;
	ArrayList<String> args;
	
	public PushWorker(ArrayList<String> newArgs) throws WorkerException {
		try {
			loggerWrapper = LoggerWrapper.getInstance();
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		
		this.args = newArgs;
		if (args.isEmpty()) {
			WorkerException e = new WorkerException(this.getClass().getName(), "Too few arguments");
			loggerWrapper.logger.severe(e.toString());
			throw e;
		}
		if (args.size() > 1) {
			WorkerException e = new WorkerException(this.getClass().getName(), "Too much arguments");
			loggerWrapper.logger.severe(e.toString());
			throw e;
		}
		loggerWrapper.logger.info(this.getClass().getName() + " constructed!");
	}
	
	public void execute(Stack<Double> stack, HashMap<String, Double> varBase) throws WorkerException {
		String argumentAsString = args.get(0);
		Double argument = null;
		try {
			argument = Double.parseDouble(argumentAsString);
		}
		catch (NumberFormatException nfe){
			if (varBase.containsKey(argumentAsString)) {
				argument = varBase.get(argumentAsString);
			}
			else {
				WorkerException e = new WorkerException(this.getClass().getName(), 
						"Undefined variable.");
				loggerWrapper.logger.severe(e.toString());
				throw e;
			}
		}
		stack.push(argument);
	}
}
