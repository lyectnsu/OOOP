package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import utility.Command;
import utility.CommandType;

class CommandTest {

	@Test
	void testConstructor() {
		ArrayList<String> argsForCommand = new ArrayList<String>();
		argsForCommand.add("a");
		argsForCommand.add("1");
		argsForCommand.add("a1");
		
		CommandType ctForCommand = CommandType.DEFINE;
		
		Command command1 = new Command();
		Command command2 = new Command(ctForCommand);
		Command command3 = new Command(argsForCommand);
		Command command4 = new Command(ctForCommand, argsForCommand);
		
		// command #1
		assertNull(command1.getType());
		assertNull(command1.getArgs());
		
		command1.setType(ctForCommand);
		assertNotNull(command1.getType());
		assertEquals(command1.getType(), CommandType.DEFINE);
		
		command1.setArgs(argsForCommand);
		assertNotNull(command1.getArgs());
		assertEquals(command1.getArgs().size(), 3);
		assertEquals(command1.getArgs().get(2), "a1");
		
		// command #2
		assertNotNull(command2.getType());
		assertEquals(command2.getType(), CommandType.DEFINE);
		assertNull(command2.getArgs());
		
		// command #3
		assertNull(command3.getType());
		assertNotNull(command3.getArgs());
		assertEquals(command3.getArgs().size(), 3);
		assertEquals(command3.getArgs().get(2), "a1");
		
		// command #4
		assertNotNull(command4.getType());
		assertEquals(command4.getType(), CommandType.DEFINE);
		assertNotNull(command4.getArgs());
		assertEquals(command4.getArgs().size(), 3);
		assertEquals(command4.getArgs().get(2), "a1");
	}

}
