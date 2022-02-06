package workers;

import exceptions.WorkerException;
import utility.Command;
import utility.CommandType;

public class WorkerFactory {
	public static Worker createWorker(Command command) throws WorkerException {
		Worker workerToReturn = null;
		if (command.getType() == CommandType.NULL) {
			workerToReturn = new NullWorker(command.getArgs());
		}
		if (command.getType() == CommandType.POP) {
			workerToReturn = new PopWorker(command.getArgs());
		}
		if (command.getType() == CommandType.PUSH) {
			workerToReturn = new PushWorker(command.getArgs());
		}
		if (command.getType() == CommandType.PLUS) {
			workerToReturn = new PlusWorker(command.getArgs());
		}
		if (command.getType() == CommandType.MINUS) {
			workerToReturn = new MinusWorker(command.getArgs());
		}
		if (command.getType() == CommandType.MULTIPLY) {
			workerToReturn = new MultiplyWorker(command.getArgs());
		}
		if (command.getType() == CommandType.DIVISION) {
			workerToReturn = new DivisionWorker(command.getArgs());
		}
		if (command.getType() == CommandType.SQRT) {
			workerToReturn = new SqrtWorker(command.getArgs());
		}
		if (command.getType() == CommandType.PRINT) {
			workerToReturn = new PrintWorker(command.getArgs());
		}
		if (command.getType() == CommandType.DEFINE) {
			workerToReturn = new DefineWorker(command.getArgs());
		}
		return workerToReturn;
	}
}
