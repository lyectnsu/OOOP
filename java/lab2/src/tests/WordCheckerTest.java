package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import utility.WordChecker;

class WordCheckerTest {

	@Test
	void testIsAcceptableCommandName() {
		WordChecker wc = new WordChecker();
		assertTrue(wc.isAcceptableCommandName("#"));
		assertTrue(wc.isAcceptableCommandName("PUSH"));
		assertTrue(wc.isAcceptableCommandName("POP"));
		assertTrue(wc.isAcceptableCommandName("+"));
		assertTrue(wc.isAcceptableCommandName("-"));
		assertTrue(wc.isAcceptableCommandName("*"));
		assertTrue(wc.isAcceptableCommandName("/"));
		assertTrue(wc.isAcceptableCommandName("SQRT"));
		assertTrue(wc.isAcceptableCommandName("PRINT"));
		assertTrue(wc.isAcceptableCommandName("DEFINE"));
		
		assertFalse(wc.isAcceptableCommandName("PUS"));
		assertFalse(wc.isAcceptableCommandName("print"));
		assertFalse(wc.isAcceptableCommandName("abcd"));
		assertFalse(wc.isAcceptableCommandName("+1"));
		assertFalse(wc.isAcceptableCommandName("SQRT "));
	}

	@Test
	void testIsAcceptableVariableName() {
		WordChecker wc = new WordChecker();
		assertFalse(wc.isAcceptableCommandName("#"));
		assertFalse(wc.isAcceptableCommandName("PUSH"));
		assertFalse(wc.isAcceptableCommandName("POP"));
		assertFalse(wc.isAcceptableCommandName("+"));
		assertFalse(wc.isAcceptableCommandName("-"));
		assertFalse(wc.isAcceptableCommandName("*"));
		assertFalse(wc.isAcceptableCommandName("/"));
		assertFalse(wc.isAcceptableCommandName("SQRT"));
		assertFalse(wc.isAcceptableCommandName("PRINT"));
		assertFalse(wc.isAcceptableCommandName("DEFINE"));
		assertFalse(wc.isAcceptableCommandName("+1"));
		assertFalse(wc.isAcceptableCommandName("SQRT "));
		assertFalse(wc.isAcceptableCommandName("a1"));
		assertFalse(wc.isAcceptableCommandName("bcd "));
		assertFalse(wc.isAcceptableCommandName("ldz+af"));
		assertFalse(wc.isAcceptableCommandName("4g"));
		
		assertTrue(wc.isAcceptableCommandName("PUS"));
		assertTrue(wc.isAcceptableCommandName("print"));
		assertTrue(wc.isAcceptableCommandName("abcd"));
	}

}
