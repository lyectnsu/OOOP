package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.Controller;
import model.Box;
import model.Config;

public class GUIView extends JFrame implements Runnable {
	Controller controller;
	
	public GUIView(Controller _controller) {
		controller = _controller;
		addKeyListener(controller);
	}
	
	public void init() {
		setSize(Config.WIDTH  * Config.SIZE,
				Config.HEIGHT * Config.SIZE + 37); // Set size of a window
		getContentPane().setBackground(Color.GREEN); // Set background color
		setDefaultCloseOperation(EXIT_ON_CLOSE); // When "(X)" clicked, process is being killed
		setTitle("Homemade TETRIS"); // Set title
		//setResizable(false); // Set window with static size (can't pull the edge and stretch it)
		setLayout(null); // No layout. Every element will be set as I want
		setVisible(true); // Show everything
		
		Box[][] grid = controller.getGrid();
		for (int x = 0; x < Config.WIDTH; ++x) {
			for (int y = 0; y < Config.HEIGHT; ++y) {
				add(grid[x][y], 0);
			}
		}
	}
	
	public void gameOver(int score, int scoreLinesDeleted) {
		String stringScore = Integer.toString(score);
		String stringScoreLinesDeleted = Integer.toString(scoreLinesDeleted);
		
		String htmlText = "<html>GAME OVER<br/><br/>YOUR SCORE:<br/>" +
						  stringScore +
						  "<br/><br/>LINES DELETED:<br/>" +
						  stringScoreLinesDeleted +
						  "</html>";
		
		JPanel gameOverPanel = new JPanel();
		JLabel gameOverText = new JLabel(htmlText);
		gameOverText.setFont(new Font("Verdana",1,30));
		gameOverPanel.setBounds(50, 150, 350, 500);
		gameOverPanel.setBackground(Config.MENU_COLOR);
		gameOverPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
		gameOverPanel.add(gameOverText);
		gameOverPanel.setVisible(true);
		add(gameOverPanel, 1);
		revalidate();
		repaint();
	}
	
	@Override
	public void run() {
		init();
	}
	
}
