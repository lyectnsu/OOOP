package lab1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import parser.Parser;

class ParserTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@BeforeEach
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}
	
	@Test
	void parserTest() {
		String str = null;
		Parser parser = new Parser(str);
		assertEquals("Filename is \"NULL\"!\n", errContent.toString());
		errContent.reset();
		parser.parse();
		assertEquals("File wasn't opened!\n", errContent.toString());
		errContent.reset();
		parser.printStatistics();
		assertEquals("File hasn't been parsed yet!\n", errContent.toString());
		errContent.reset();
	}
	
	@Test
	void readOnlyFileTest() {
		Parser parser = new Parser("/some/read/only/file");
		assertEquals("Error while opening file /some/read/only/file\n", errContent.toString());
		errContent.reset();
	}
	
	@AfterEach
	public void restoreStreams() {
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	}

}
