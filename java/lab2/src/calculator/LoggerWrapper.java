package calculator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerWrapper {
	
	public static final Logger logger = Logger.getLogger("GlobalLogger");

	private static LoggerWrapper instance = null;
	
	public static LoggerWrapper getInstance() throws SecurityException, IOException {
		if(instance == null) {
			prepareLogger();
			instance = new LoggerWrapper ();
		}
		return instance;
	}
  
	private static void prepareLogger() throws SecurityException, IOException {
		Path loggingFolder = Paths.get(System.getProperty("user.dir"), "log");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yyyy::HH:mm:ss");  
	    Date date = new Date();
		String loggingFileName = formatter.format(date) + ".log";
		
		Path pathToLoggingFile = Paths.get(loggingFolder.toString(), loggingFileName);
		
		
		FileHandler myFileHandler = new FileHandler(pathToLoggingFile.toString());
		myFileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(myFileHandler);
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.FINE);
	}
}
