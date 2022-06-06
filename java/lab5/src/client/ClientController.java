package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import javax.swing.DefaultListModel;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ClientController {
	
	private boolean isConnected;
	private Socket socket = null;
	private DataInputStream inp;
	private DataOutputStream out;
	
	private String nickname = null;
	private String serverAddress = null;
	private int port = 0;
	private DefaultListModel<String> participants;
	private DefaultListModel<String> messages;
	
	public ClientController() {
		isConnected = false;
	}
	
	public boolean isConnected() {
		return isConnected;
	}
	
	public void connect(String nickname, String serverAddress, String serverPort, DefaultListModel<String> participants, DefaultListModel<String> messages){
		this.nickname = nickname;
		this.serverAddress = serverAddress;
		this.port = Integer.parseInt(serverPort);
		this.participants = participants;
		this.messages = messages;
		try {
			socket = new Socket(serverAddress, port);
		} catch (IOException e) {
			return;
		}
		
		try {
			inp = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String xmlMessage = 
				  "<?xml version = \"1.0\"?>"
				+ "<command type=\"connect\">"
				+ 		nickname
				+ "</command>";
		sendXMLMessage(xmlMessage);
		System.out.println("hello");
		isConnected = true;
	}
	
	public void disconnect() {
		String xmlMessage = 
				  "<?xml version = \"1.0\"?>"
				+ "<command type=\"disconnect\">"
				+		nickname
				+ "</command>";
		sendXMLMessage(xmlMessage);
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isConnected = false;
	}
	
	public void send(String message) {
		String xmlMessage = 
				  "<?xml version = \"1.0\"?>"
				+ "<command type=\"message\">"
				+ 		message
				+ "</command>";
		sendXMLMessage(xmlMessage);
	}
	
	public void receive() {
		
		while (true) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (!isConnected) {
				continue;
			}
			int length = 0;
			
			try {
				length = inp.readInt();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (length <= 0) {
				continue;
			}
			byte[] serverMessageAsBytes = new byte[length];
			System.out.println("aa");
			try {
				inp.readFully(serverMessageAsBytes, 0, length);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String serverMessage = new String(serverMessageAsBytes, StandardCharsets.UTF_8);
			System.out.println("bb");
			Document doc = null;
			try {
				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(serverMessage)));
			} catch (SAXException | IOException | ParserConfigurationException e) {
				e.printStackTrace();
			}
			System.out.println("cc");
			doc.getDocumentElement().normalize();
			
			Element answerElement = (Element) (doc.getElementsByTagName("answer").item(0));
			String answerType = answerElement.getAttribute("type");
			String text = answerElement.getTextContent();
			System.out.println("CType: " + answerType);
			System.out.println("CText: " + text);
			if (answerType.equals("participants")) {
				NodeList nameList = doc.getElementsByTagName("name");
				participants.clear();
				participants.addElement("<html><font color=\"#0000ff\">Participants:</font></html>");
				for (int i = 0; i < nameList.getLength(); ++i) {
					Node nameNode = nameList.item(i);
					Element nameElement = (Element) nameNode;
					participants.addElement(nameElement.getTextContent());
				}
				return;
			}
			else {
				messages.addElement(text);
			}
		}
	}
	
	private void sendXMLMessage(String xmlMessage){
		byte[] xmlMessageAsBytes = xmlMessage.getBytes(StandardCharsets.UTF_8);
		try {
			out.writeInt(xmlMessageAsBytes.length);
			out.write(xmlMessageAsBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
