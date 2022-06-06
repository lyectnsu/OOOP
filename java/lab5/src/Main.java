

import client.ChatClient;
import server.ChatServer;

public class Main {
	
	private static final String WORKING_MODE = "Client";
	
	public static void ClientFunction() {
		ChatClient client = new ChatClient();
		client.start();
	}
	
	public static void ServerFunction() {
		ChatServer server = new ChatServer();
		server.start();
	}
	
	public static void main(String[] args) {
		switch (WORKING_MODE){
			case "Client":
				ClientFunction();
				break;
			case "Server":
				ServerFunction();
				break;
			default:
				break;
		}
	}

}
