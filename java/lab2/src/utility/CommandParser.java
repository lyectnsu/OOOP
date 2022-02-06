package utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import calculator.LoggerWrapper;
import exceptions.ParserException;

public class CommandParser {
	public Command parseCommand(String commandAsString) throws ParserException {
		
		LoggerWrapper loggerWrapper = null;
		
		try {
			loggerWrapper = LoggerWrapper.getInstance();
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}
		
		if (commandAsString == null) return null;
		
		Command commandToReturn = new Command();
		if (commandAsString.length() == 0 || commandAsString.charAt(0) == '#') {
			commandToReturn.setType(CommandType.NULL);
			loggerWrapper.logger.info("Command type set to NULL.");
			
			commandToReturn.setArgs(new ArrayList<String>());
			loggerWrapper.logger.info("Command arguments set.");
			return commandToReturn;
		}
		
		WordChecker wc = new WordChecker();
		
		String[] splittedCommand = commandAsString.split("\\s+"); 
		
		if (!wc.isAcceptableCommandName(splittedCommand[0])) {
			ParserException e = new ParserException("Unacceptable command name.", splittedCommand[0]); 
			loggerWrapper.logger.severe(e.toString());
			throw e;
		}
		
		String typeAsString = splittedCommand[0];
		if (typeAsString.equals("POP")) {
			commandToReturn.setType(CommandType.POP);
			loggerWrapper.logger.info("Command type set to POP.");
		}
		if (typeAsString.equals("PUSH")) {
			commandToReturn.setType(CommandType.PUSH);
			loggerWrapper.logger.info("Command type set to PUSH.");
		}
		if (typeAsString.equals("+")) {
			commandToReturn.setType(CommandType.PLUS);
			loggerWrapper.logger.info("Command type set to PLUS.");
		}
		if (typeAsString.equals("-")) {
			commandToReturn.setType(CommandType.MINUS);
			loggerWrapper.logger.info("Command type set to MINUS.");
		}
		if (typeAsString.equals("*")) {
			commandToReturn.setType(CommandType.MULTIPLY);
			loggerWrapper.logger.info("Command type set to MULTIPLY.");
		}
		if (typeAsString.equals("/")) {
			commandToReturn.setType(CommandType.DIVISION);
			loggerWrapper.logger.info("Command type set to DIVISION.");
		}
		if (typeAsString.equals("SQRT")) {
			commandToReturn.setType(CommandType.SQRT);
			loggerWrapper.logger.info("Command type set to SQRT.");
		}
		if (typeAsString.equals("PRINT")) {
			commandToReturn.setType(CommandType.PRINT);
			loggerWrapper.logger.info("Command type set to PRINT.");
		}
		if (typeAsString.equals("DEFINE")) {
			commandToReturn.setType(CommandType.DEFINE);
			loggerWrapper.logger.info("Command type set to DEFINE.");
		}
		
		String[] argsAsArray = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);
		ArrayList<String> argsAsList = new ArrayList<String>(Arrays.asList(argsAsArray));
		
		commandToReturn.setArgs(argsAsList);
		loggerWrapper.logger.info("Command arguments set.");
		return commandToReturn;
	}
}
