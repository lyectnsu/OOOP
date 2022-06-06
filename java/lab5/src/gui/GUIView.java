package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import client.ClientController;
import config.Config;
import net.miginfocom.swing.MigLayout;

public class GUIView extends JFrame implements Runnable{
	
	private Config config = null;
	private ClientController controller = null;
	// Panels
	private JPanel connectPanel = null;
	private JPanel participantsPanel = null;
	private JPanel messagePanel = null;
	
	// Labels
	private JLabel nicknameLabel = null;
	private JLabel serverAddressLabel = null;
	private JLabel serverPortLabel = null;
	
	// Text fields
	private JTextField nicknameTextField = null;
	private JTextField serverAddressTextField = null;
	private JTextField serverPortTextField = null;
	private JTextField messageTextField = null;
	
	// Buttons
	private JButton connectButton = null;
	private JButton sendButton = null;
	
	// Lists
	private JList<String> participantsList = null;
	private JList<String> messageList = null;
	
	// Scroll panes
	private JScrollPane messageScrollPane = null;
	private JScrollPane participantsScrollPane = null;
	
	// Another components
	private DefaultListModel<String> participants;
	private DefaultListModel<String> messages;
	
	public GUIView(Config config, ClientController controller){
		this.config = config;
		this.controller = controller;
		
		participants = new DefaultListModel<String>();
		messages = new DefaultListModel<String>();
	}
	
	private void init() {
		setSize(config.WINDOW_WIDTH, config.WINDOW_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("GigaChat");
		setLayout(new MigLayout("debug, fill"));
		setVisible(true);
	}
	
	private void initConnectFields() {
		connectPanel = new JPanel();
		connectPanel.setLayout(new MigLayout("debug, fill"));
		
		nicknameLabel = new JLabel("Nickname:");
		serverAddressLabel = new JLabel("Server IP:");
		serverPortLabel = new JLabel("Port:");
		
		nicknameTextField = new JTextField("aboba", 15);
		serverAddressTextField = new JTextField("127.0.0.1", 15);
		serverPortTextField = new JTextField("25565", 15);
		
		connectButton = new JButton("Connect");
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!controller.isConnected()) {
					connectActions();
				}
				else {
					disconnectActions();
				}
			}
		});
		connectPanel.add(connectButton, "grow, dock east");
		connectPanel.add(nicknameLabel, "grow");
		connectPanel.add(nicknameTextField, "span, grow, wrap");
		connectPanel.add(serverAddressLabel, "grow");
		connectPanel.add(serverAddressTextField, "grow");
		connectPanel.add(serverPortLabel, "grow, gap unrelated");
		connectPanel.add(serverPortTextField, "grow");
		
		add(connectPanel, "dock north");
	}
	
	private void initParticipantList() {
		participantsPanel = new JPanel(new MigLayout("debug, fill"));
		
		participantsList = new JList<String>(participants);
		participantsScrollPane = new JScrollPane(participantsList);
		participantsPanel.add(participantsScrollPane, "grow, push");
		
		add(participantsPanel, "w 30%, grow, dock west");
	}
	
	private void initMessageWindow() {
		messagePanel = new JPanel(new MigLayout("debug, fill"));
		
		messageList = new JList<String>(messages);
		messageScrollPane = new JScrollPane(messageList);
		messageTextField = new JTextField();
		
		sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (controller.isConnected()) {
					sendActions(); 
				}
				else {
					cannotSendActions();
				}
			}
		});
		messagePanel.add(messageScrollPane, "grow, push, span, wrap");
		messagePanel.add(messageTextField, "grow");
		messagePanel.add(sendButton);
		
		add(messagePanel, "w 70%, grow, push");
	}
	
	@Override
	public void run() {
		// Functions must be in this order
		// 	because SWING is a piece of shit and
		//	I have no time to replace it with
		// 	another framework :(
		init();
		initConnectFields();
		initParticipantList();
		initMessageWindow();
		
		addParticipant("Participants:");
		addMessage("Not connected");
	}
	
	private void connectActions() {
		
		nicknameTextField.setEnabled(false);
		serverAddressTextField.setEnabled(false);
		serverPortTextField.setEnabled(false);
		connectButton.setEnabled(false);
		
		String nickname = getNickname();
		String serverAddress = getServerAddress();
		String serverPort = getServerPort();
		
		messages.clear();
		addMessage("<html>Connecting...</html>");
		for (int connectTry = 1; connectTry <= config.CONNECT_TRIES; ++connectTry) {
			addMessage("<html>Try #" + Integer.toString(connectTry) + ": trying to connect...</html>");
			controller.connect(nickname, serverAddress, serverPort, participants, messages);
			if (controller.isConnected()) {
				break;
			}
		}
		
		if (controller.isConnected()) {
			connectButton.setText("Disconnect");
			connectButton.setEnabled(true);
			addMessage("<html><font color=\"#00ff00\">You are connected!</font></html>");	
		}
		else {
			nicknameTextField.setEnabled(true);
			serverAddressTextField.setEnabled(true);
			serverPortTextField.setEnabled(true);
			connectButton.setEnabled(true);
			addMessage("<html><font color=\"#ff0000\">Can't connect to server! Try later...</font></html>");
		}
	}
	
	private void disconnectActions() {
		
		connectButton.setEnabled(false);
		
		controller.disconnect();
		addMessage("<html><font color=\"#ff0000\">You are disconnected!</font></html>");	
		
		connectButton.setText("Connect");
		participants.clear();
		participants.addElement("<html><font color=\"#0000ff\">Participants:</font></html>");
		nicknameTextField.setEnabled(true);
		serverAddressTextField.setEnabled(true);
		serverPortTextField.setEnabled(true);
		connectButton.setEnabled(true);
	}
	
	private void sendActions() {
		String message = getMessageText();
		controller.send(message);
	}
	
	private void cannotSendActions() {
		addMessage("<html><font color=\"#ff0000\">You can't send messages because you're not connected!</font></html>");
	}
	
	private String getNickname() {
		return nicknameTextField.getText();
	}
	
	private String getServerAddress() {
		return serverAddressTextField.getText();
	}
	
	private String getServerPort() {
		return serverPortTextField.getText();
	}
	
	private String getMessageText() {
		return messageTextField.getText();
	}
	
	private void addMessage(String message) {
		messages.addElement(message);
		messageScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
	        public void adjustmentValueChanged(AdjustmentEvent e) {  
	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());
	            e.getAdjustable().removeAdjustmentListener(this);
	        }
	    });
	}
	
	private void addParticipant(String participant) {
		participants.addElement("<html><font color=\"#0000ff\">" + participant + "</font></html>");
		participantsScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
	        public void adjustmentValueChanged(AdjustmentEvent e) {  
	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());
	            e.getAdjustable().removeAdjustmentListener(this);
	        }
	    });
	}
	
}
