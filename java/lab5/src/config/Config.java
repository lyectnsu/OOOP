package config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Config {
	
	public int WINDOW_WIDTH = 700;
	public int WINDOW_HEIGHT = 700;
	public int CONNECT_TRIES = 5;
	
	public Config(String pathToConfigFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(pathToConfigFile));
		
		String line;
		while ((line = br.readLine()) != null) {
			String[] kwarg = line.split("=");
			if (kwarg.length == 2) {
				String k = kwarg[0];
				String v = kwarg[1];
				
				switch (k) {
					case "windowWidth":
						WINDOW_WIDTH = Integer.parseInt(v);
						break;
					case "windowHeight":
						WINDOW_HEIGHT = Integer.parseInt(v);
						break;
					case "connectTries":
						CONNECT_TRIES = Integer.parseInt(v);
					default:
						break;
				}
			}
		}
		br.close();
	}
}
