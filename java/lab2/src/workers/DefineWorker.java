package workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import calculator.LoggerWrapper;
import exceptions.WorkerException;
import utility.WordChecker;

public class DefineWorker implements Worker{
	LoggerWrapper loggerWrapper = null;
	ArrayList<String> args;
	
	public DefineWorker(ArrayList<String> newArgs) throws WorkerException {

		try {
			loggerWrapper = LoggerWrapper.getInstance();
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		
		if (newArgs.size() < 2) {
			WorkerException e = new WorkerException(this.getClass().getName(), "Too few arguments");
			loggerWrapper.logger.severe(e.toString());
			throw e;
		}
		if (newArgs.size() > 2) {
			WorkerException e = new WorkerException(this.getClass().getName(), "Too much arguments");
			loggerWrapper.logger.severe(e.toString());
			throw e;
		}
		this.args = newArgs;
		loggerWrapper.logger.info(this.getClass().getName() + " constructed!");
	}
	
	public void execute(Stack<Double> stack, HashMap<String, Double> varBase) throws WorkerException {
		WordChecker wc = new WordChecker();
		String fArg = args.get(0); // variable name as String
		String sArg = args.get(1); // value as String
		
		if (wc.isAcceptableVariableName(fArg)) {
			try {
				Double sArgAsDouble = varBase.containsKey(fArg) ? varBase.get(fArg) : Double.parseDouble(sArg);
				varBase.put(fArg, sArgAsDouble);
			}
			catch (NumberFormatException nfe){
				WorkerException e = new WorkerException(this.getClass().getName(), "Value of parameter is not numeric");
				loggerWrapper.logger.severe(e.toString());
				throw e;
			}
		}
		else {
			WorkerException e = new WorkerException(this.getClass().getName(), "Unacceptable variable name");
			loggerWrapper.logger.severe(e.toString());
			throw e;
		}
	}
}
