package tests;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import exceptions.ParserException;
import utility.CommandParser;
import utility.CommandType;

class CommandParserTest {

	@Test
	void testParseCommand() throws ParserException {
		CommandParser cp = new CommandParser();
		assertNull(cp.parseCommand(null));
		
		assertEquals(cp.parseCommand("#").getType(), CommandType.NULL);
		assertTrue(cp.parseCommand("#").getArgs().isEmpty());
		
		assertEquals(cp.parseCommand("").getType(), CommandType.NULL);
		assertTrue(cp.parseCommand("").getArgs().isEmpty());
		
		assertEquals(cp.parseCommand("POP").getType(), CommandType.POP);
		assertTrue(cp.parseCommand("POP").getArgs().isEmpty());
		
		assertEquals(cp.parseCommand("PUSH a").getType(), CommandType.PUSH);
		assertEquals(cp.parseCommand("PUSH 1").getType(), CommandType.PUSH);
		assertEquals(cp.parseCommand("PUSH a").getArgs().size(), 1);
		assertEquals(cp.parseCommand("PUSH 1").getArgs().size(), 1);
		assertEquals(cp.parseCommand("PUSH a").getArgs().get(0), "a");
		assertEquals(cp.parseCommand("PUSH 1").getArgs().get(0), "1");
		
		assertEquals(cp.parseCommand("+").getType(), CommandType.PLUS);
		assertTrue(cp.parseCommand("+").getArgs().isEmpty());
		
		assertEquals(cp.parseCommand("-").getType(), CommandType.MINUS);
		assertTrue(cp.parseCommand("-").getArgs().isEmpty());
		
		assertEquals(cp.parseCommand("*").getType(), CommandType.MULTIPLY);
		assertTrue(cp.parseCommand("*").getArgs().isEmpty());
		
		assertEquals(cp.parseCommand("/").getType(), CommandType.DIVISION);
		assertTrue(cp.parseCommand("/").getArgs().isEmpty());
		
		assertEquals(cp.parseCommand("SQRT").getType(), CommandType.SQRT);
		assertTrue(cp.parseCommand("SQRT").getArgs().isEmpty());
		
		assertEquals(cp.parseCommand("PRINT").getType(), CommandType.PRINT);
		assertTrue(cp.parseCommand("PRINT").getArgs().isEmpty());
		
		assertEquals(cp.parseCommand("DEFINE a 1").getType(), CommandType.DEFINE);
		assertEquals(cp.parseCommand("DEFINE a 1").getArgs().size(), 2);
		assertEquals(cp.parseCommand("DEFINE a 1").getArgs().get(0), "a");
		assertEquals(cp.parseCommand("DEFINE a 1").getArgs().get(0), "1");
		
	}

}
