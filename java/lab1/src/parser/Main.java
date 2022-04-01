package parser;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		
		Scanner keyboardScanner = new Scanner(System.in);
		
		String fileName = keyboardScanner.nextLine();
		
		keyboardScanner.close();
		
		Parser pr = new Parser(fileName);
		
		pr.parse();
		pr.printStatistics();
		pr.printStatisticsToFile("/home/lyect/Desktop/OOOP/java/out.txt");
	}
}

// /home/lyect/Desktop/OOOP/java/test.txt