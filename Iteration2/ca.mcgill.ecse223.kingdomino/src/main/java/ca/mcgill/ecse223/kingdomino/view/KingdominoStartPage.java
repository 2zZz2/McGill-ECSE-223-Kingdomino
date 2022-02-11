package ca.mcgill.ecse223.kingdomino.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;

public class KingdominoStartPage extends JFrame {
	
	//All buttons 
	private JButton startButton;
	private JButton loadButton;
	private JButton settingsButton;
	private JButton soundButton;
	private JButton rulesButton;
	private JButton playerButton;
	private static JFrame startFrame;
	public static KingdominoPlayerMenuFour playerFrame = new KingdominoPlayerMenuFour();
	
	//public static KingdominoGamePage gamePage = new KingdominoGamePage();
	public static KingdominoSettingsPage settingsFrame = new KingdominoSettingsPage();
	
	
	public KingdominoStartPage() {
		initComponents();
		refreshData();
		startFrame = this;
		playerFrame.setVisible(false);
	}
	
	private void initComponents() {

		this.setResizable(false);

		setLayout(null);
		setContentPane(new JLabel(new ImageIcon(AssetManager.getStartBackgroundImage())));
		Container c = getContentPane();
		c.setPreferredSize(new Dimension(1280,720));


		//Start button
		startButton = new JButton(new ImageIcon(AssetManager.getStartButtonImage()));
		startButton.setSize(new Dimension(320,145));
		startButton.setLocation(480,345);
		startButton.setOpaque(false);
		startButton.setContentAreaFilled(false);
		startButton.setBorderPainted(false);
		c.add(startButton);

		//Load button
		loadButton = new JButton(new ImageIcon(AssetManager.getLoadButtonImage()));
		loadButton.setSize(new Dimension(320,145));
		loadButton.setLocation(480,515);
		loadButton.setOpaque(false);
		loadButton.setContentAreaFilled(false);
		loadButton.setBorderPainted(false);
		c.add(loadButton);
		
		
		//Placing the rest of the buttons
		settingsButton = new JButton(new ImageIcon(AssetManager.getChestButtonImage()));
		settingsButton.setSize(new Dimension(75,75));
		settingsButton.setLocation(40,545);
		settingsButton.setOpaque(false);
		settingsButton.setContentAreaFilled(false);
		settingsButton.setBorderPainted(false);
		c.add(settingsButton);
		
		
		soundButton = new JButton(new ImageIcon(AssetManager.getSoundButtonImage()));
		soundButton.setSize(new Dimension(75,75));
		soundButton.setLocation(40,625);
		soundButton.setOpaque(false);
		soundButton.setContentAreaFilled(false);
		soundButton.setBorderPainted(false);
		c.add(soundButton);
		
		rulesButton = new JButton(new ImageIcon(AssetManager.getRuleButtonImage()));
		rulesButton.setSize(new Dimension(75,75));
		rulesButton.setLocation(40,465);
		rulesButton.setOpaque(false);
		rulesButton.setContentAreaFilled(false);
		rulesButton.setBorderPainted(false);
		c.add(rulesButton);
		
		
		playerButton = new JButton(new ImageIcon(AssetManager.getKingButtonImage()));
		playerButton.setSize(new Dimension(75,75));
		playerButton.setLocation(40,385);
		playerButton.setOpaque(false);
		playerButton.setContentAreaFilled(false);
		playerButton.setBorderPainted(false);
		c.add(playerButton);
		
		//Button actions
		
		//Starts the game and opens a new window
	
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KingdominoApplication.gamePage = new KingdominoGamePage();
				KingdominoController.startUI();
				KingdominoApplication.gamePage.setVisible(true);
				startFrame.setVisible(false);

			}
		});
		
		//Loads a valid save file
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
				KingdominoController.loadUI();
			}
		});
		
		//Opens the settings menu
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
				settingsFrame.setVisible(true);
			}
		});
		
		//Disable/enable the sound
		soundButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		
		//Opens the rules
		rulesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO

				new KingdominoRules().setVisible(true);

				KingdominoController.saveUI();


			}
		});
		
		
		playerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
				playerFrame.setVisible(true);
			}
		});
		
		pack();
	}
	
	private void refreshData() {
		
	}
	
	public static JFrame getFrame() {
		return startFrame;
	}
	
	
	
}
