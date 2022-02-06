package workers;

import java.util.HashMap;
import java.util.Stack;

import exceptions.WorkerException;

public interface Worker {
	public abstract void execute(Stack<Double> stack, HashMap<String, Double> varBase) throws WorkerException;
}
