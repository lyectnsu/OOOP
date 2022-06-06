package client;

import javax.swing.SwingUtilities;

import config.Config;
import gui.GUIView;

public class ChatClient {
	public ChatClient() {
	}
	
	public void start() {
		Config config = null;
		try{
			config = new Config("/home/lyect/Desktop/OOOP/java/lab5/src/config/config.cfg");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		ClientController controller = new ClientController();
		GUIView view = new GUIView(config, controller);
		
		SwingUtilities.invokeLater(view);
		
		while (true) {
			controller.receive();
		}
		
	}
}
