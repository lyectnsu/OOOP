package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ChatServer {
	
	private final int PORT = 25565;
	public ChatServer() {
		 
	}
	public void start() {
		
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		ConcurrentHashMap<Socket, String> participantSockets = new ConcurrentHashMap<Socket, String>();
		
		try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		while (true) {
			try {
                socket = serverSocket.accept();
            } catch (IOException e) {}
			participantSockets.put(socket, "Unknown");
            new ServerThread(participantSockets, socket).start();
		}
	}
}