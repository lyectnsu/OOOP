package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ServerThread extends Thread{
	private Socket clientSocket = null;
	private DataInputStream clientInp = null;
	private ConcurrentHashMap<Socket, String> participantSockets = null;
	
	private String clientName = null;
	ServerThread(ConcurrentHashMap<Socket, String> participantSockets, Socket socket){
		this.participantSockets = participantSockets;
		this.clientSocket = socket;
	}
	
	@Override
	public void run() {
		
		try {
			clientInp = new DataInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int badConnect = 0;
		while (true) {
			
			if (badConnect == 5) {
				broadcastMessage("Client \"" + clientName + "\" disconnected.");
				participantSockets.remove(clientSocket);
				broadcastParticipants();
				return;
			}
			
			int length = 0;
			
			String clientMessage = null;
			try {
				length = clientInp.readInt();
			} catch (IOException e) {
				badConnect += 1;
			}
			
			if (length <= 0) {
				continue;
			}
			badConnect = 0;
			byte[] clientMessageAsBytes = new byte[length];
			try {
				clientInp.readFully(clientMessageAsBytes, 0, length);
			} catch (IOException e) {
				e.printStackTrace();
			}
			clientMessage = new String(clientMessageAsBytes, StandardCharsets.UTF_8);
			
			Document doc = null;
			try {
				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(clientMessage)));
			} catch (SAXException | IOException | ParserConfigurationException e) {
				e.printStackTrace();
			}
			
			doc.getDocumentElement().normalize();
			
			Element commandElement = (Element) (doc.getElementsByTagName("command").item(0));
			String commandType = commandElement.getAttribute("type");
			String text = commandElement.getTextContent();
			System.out.println("Type: " + commandType);
			System.out.println("Text: " + text);
			switch (commandType){
				case "connect":
					clientName = text;
					participantSockets.put(clientSocket, clientName);
					broadcastMessage("Client \"" + clientName + "\" connected.");
					broadcastParticipants();
					break;
				case "message":
					broadcastMessage(clientName + ": " + text);
					break;
				case "disconnect":
					broadcastMessage("Client \"" + clientName + "\" disconnected.");
					participantSockets.remove(clientSocket);
					broadcastParticipants();
					try {
						clientSocket.close();
					} catch (IOException e) {}
					return;
				default:
					break;
			}
			
		}
	}
	
	private void broadcastMessage(String message) {
		String xmlMessage = 
				  "<?xml version = \"1.0\"?>"
				+ "<answer type=\"message\">"
				+ 		message
				+ "</answer>";
		for (Socket socket: participantSockets.keySet()) {
			sendXMLMessageToSocket(socket, xmlMessage);
		}
	}
	
	private void sendXMLMessageToSocket(Socket socket, String xmlMessage) {
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(socket.getOutputStream());
			byte[] xmlMessageAsBytes = xmlMessage.getBytes(StandardCharsets.UTF_8);
			out.writeInt(xmlMessageAsBytes.length);
			out.write(xmlMessageAsBytes);
		} catch (IOException e) {}
	}
	
	private void broadcastParticipants() {
		String xmlMessage = "<?xml version = \"1.0\"?><answer type=\"participants\">";
		for (String participantName : participantSockets.values()) {
			xmlMessage += "<name>" + participantName + "</name>";
		}
		xmlMessage += "</answer>";
		
		for (Socket socket: participantSockets.keySet()) {
			sendXMLMessageToSocket(socket, xmlMessage);
		}
	}
}
