package parser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Parser {
	private Scanner fileScanner;
	private int wordsTotal;
	private ArrayList<Entry<String, Integer>> sortedItems;
	
	public Parser(String fileName){
		fileScanner = null;
		sortedItems = null;
		wordsTotal = 0;
		if (fileName == null) {
			System.err.println("Filename is \"NULL\"!");
			return;
		}
		Path pathToFile = Paths.get(fileName);
		try {
			fileScanner = new Scanner(pathToFile);
		}
		catch (IOException e) {
			System.err.println("Error while opening file " + e.getLocalizedMessage());
		}
	}
	
	public void parse(){
		if (fileScanner == null) {
			System.err.println("File wasn't opened!");
			return;
		}
		HashMap<String, Integer> freq = new HashMap<String, Integer>();
		
		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			
			String[] splittedLine = line.split("[^a-zA-Z0-9]+");
			
			for (String word : splittedLine) {
				int count = freq.containsKey(word) ? freq.get(word) : 0;
				freq.put(word, count + 1);
			}
		}
		
		for (int i : freq.values()) {
			wordsTotal += i;
		}
		sortedItems = new ArrayList<>(freq.entrySet());
		sortedItems.sort(Entry.comparingByKey());
		Collections.reverse(sortedItems);
        sortedItems.sort(Entry.comparingByValue());
        Collections.reverse(sortedItems);
	}
	
	public void printStatistics() {
		
		if (sortedItems == null) {
			System.err.println("File hasn't been parsed yet!");
			return;
		}
        
        for (Entry<String, Integer> entry : sortedItems) {
        	System.out.print(entry.getKey() + "; ");
        	System.out.print(entry.getValue() + "; ");
        	System.out.format(Locale.US, "%.2f%%\n", entry.getValue().floatValue() / wordsTotal * 100);
        }
	}
	
	public void printStatisticsToFile(String fileName){
		if (sortedItems == null) {
			System.err.println("File hasn't been parsed yet!");
			return;
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Entry<String, Integer> entry : sortedItems) {
			try {
	        	writer.write(entry.getKey() + "; ");
	        	writer.write(entry.getValue() + "; ");
	        	String percFreq = String.format(Locale.US, "%.2f%%\n", entry.getValue().floatValue() / wordsTotal * 100);
	        	writer.write(percFreq);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
        }
	    try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
