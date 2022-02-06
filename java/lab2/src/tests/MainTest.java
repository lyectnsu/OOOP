package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculator.Main;

class MainTest {
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
	void testMain() {
		String absPathPrefix = "/home/lyect/Desktop/OOOP/java/lab2/src/test/";
		String[] file1 = {absPathPrefix + "testFile1.txt"};
		String[] file2 = {absPathPrefix + "testFile2.txt"};
		String[] file3 = {absPathPrefix + "testFile3.txt"};
		Main.main(file1);
		assertEquals("2.0\n", outContent.toString());
		outContent.reset();
		Main.main(file2);
		assertEquals("5.0\n", outContent.toString());
		outContent.reset();
		Main.main(file3);
		assertEquals("40.0\n", outContent.toString());
		outContent.reset();
	}
	
	@AfterEach
	public void restoreStreams() {
	    System.setOut(originalOut);
	    System.setErr(originalErr);
	}

}
