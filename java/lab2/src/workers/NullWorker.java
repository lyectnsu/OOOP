package workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import calculator.LoggerWrapper;

public class NullWorker implements Worker{
	LoggerWrapper loggerWrapper = null;
	ArrayList<String> args;
	
	public NullWorker(ArrayList<String> newArgs) {
		
		try {
			loggerWrapper = LoggerWrapper.getInstance();
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		
		this.args = newArgs;
		// every number of the arguments is acceptable
		loggerWrapper.logger.info(this.getClass().getName() + " constructed!");
	}
	
	public void execute(Stack<Double> stack, HashMap<String, Double> varBase) {
		// must do nothing
	}
}
