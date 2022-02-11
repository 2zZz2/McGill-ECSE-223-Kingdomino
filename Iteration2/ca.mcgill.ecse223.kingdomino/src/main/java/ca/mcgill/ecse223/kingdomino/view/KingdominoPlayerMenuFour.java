package ca.mcgill.ecse223.kingdomino.view;

	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.WindowAdapter;
	import java.awt.event.WindowEvent;
	import java.io.File;
	import java.awt.font.*;

	import javax.swing.*;

import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;

	public class KingdominoPlayerMenuFour extends JFrame {

	    public JTextField firstPlayerNameBox;
	    public JTextField secondPlayerNameBox;
	    public JTextField thirdPlayerNameBox;
	    public JTextField fourthPlayerNameBox;
	    public JLabel firstPlayerName;
	    public JLabel secondPlayerName;
	    public JLabel thirdPlayerName;
	    public JLabel fourthPlayerName;
	    public JLabel firstPlayerColor;
	    public JLabel secondPlayerColor;
	    public JLabel thirdPlayerColor;
	    public JLabel fourthPlayerColor;
	    private JLabel firstPlayerIcon;
	    private JLabel secondPlayerIcon;
	    private JLabel thirdPlayerIcon;
	    private JLabel fourthPlayerIcon;
	    public static String firstName;
	    public static String secondName;
	    public static String thirdName;
	    public static String fourthName;
	    public static String firstGamesWon = "0";
		public static String secondGamesWon = "0";
		public static String thirdGamesWon = "0";
		public static String fourthGamesWon = "0";
		public static String firstGamesPlayed = "0";
		public static String secondGamesPlayed = "0";
		public static String thirdGamesPlayed = "0";
		public static String fourthGamesPlayed = "0";
	    public JButton exit;
	    public JFrame playerMenuFrame = this;

	    public KingdominoPlayerMenuFour() {
	        initComponents();
	    }

	    private void initComponents() {
	        setLayout(null);
	        setContentPane(new JLabel(new ImageIcon(AssetManager.getGameBackgroundImage())));
	        Container c = getContentPane();
	        c.setPreferredSize(new Dimension(1280, 720));
	        
	        
	        
	        JLabel nextPlayerMsg;
	        
	    	nextPlayerMsg = new JLabel("click on the crown to change the name");
			nextPlayerMsg.setLocation(120,560);
			nextPlayerMsg.setSize(new Dimension(1000,200));
			nextPlayerMsg.setForeground(new Color(70, 232, 142));
			nextPlayerMsg.setFont(new Font("pixelType", Font.PLAIN, 38));
			c.add(nextPlayerMsg);
	        
	        firstPlayerColor = new JLabel(new ImageIcon(AssetManager.getSecondPlayerColor()));
	        firstPlayerColor.setLocation(8, 325);
	        firstPlayerColor.setSize(new Dimension(311,72));
	        c.add(firstPlayerColor);
	        
	        secondPlayerColor = new JLabel(new ImageIcon(AssetManager.getFirstPlayerColor()));
	        secondPlayerColor.setLocation(331, 325);
	        secondPlayerColor.setSize(new Dimension(311,72));
	        c.add(secondPlayerColor);
	        
	        thirdPlayerColor = new JLabel(new ImageIcon(AssetManager.getThirdPlayerColor()));
	        thirdPlayerColor.setLocation(652, 325);
	        thirdPlayerColor.setSize(new Dimension(311,72));
	        c.add(thirdPlayerColor);
	        
	        fourthPlayerColor = new JLabel(new ImageIcon(AssetManager.getFourthPlayerColor()));
	        fourthPlayerColor.setLocation(977, 325);
	        fourthPlayerColor.setSize(new Dimension(311,72));
	        c.add(fourthPlayerColor);
	        
	        
	        firstPlayerIcon = new JLabel(new ImageIcon(AssetManager.getSecondPlayerIcon()));
	        firstPlayerIcon.setLocation(85,15);
	        firstPlayerIcon.setSize(new Dimension(147,300));
	        c.add(firstPlayerIcon);
	        
	        secondPlayerIcon = new JLabel(new ImageIcon(AssetManager.getFirstPlayerIcon()));
	        secondPlayerIcon.setLocation(408,15);
	        secondPlayerIcon.setSize(new Dimension(147,300));
	        c.add(secondPlayerIcon);
	        
	        thirdPlayerIcon = new JLabel(new ImageIcon(AssetManager.getThirdPlayerIcon()));
	        thirdPlayerIcon.setLocation(721,15);
	        thirdPlayerIcon.setSize(new Dimension(147,300));
	        c.add(thirdPlayerIcon);
	        
	        fourthPlayerIcon = new JLabel(new ImageIcon(AssetManager.getFourthPlayerIcon()));
	        fourthPlayerIcon.setLocation(1039,15);
	        fourthPlayerIcon.setSize(new Dimension(147,300));
	        c.add(fourthPlayerIcon);
	        
	        
	        
	        firstPlayerNameBox = new JTextField(10);
	        firstPlayerNameBox.setFont(new Font("pixelType",Font.BOLD,60));
	        firstPlayerNameBox.setForeground(new Color(252,217,51));
	        firstPlayerNameBox.setLocation(25, 413);
	        firstPlayerNameBox.setSize(new Dimension(220,72));
	        firstPlayerNameBox.setHorizontalAlignment(JTextField.CENTER);
	        firstPlayerNameBox.setOpaque(false);
	        //firstPlayerNameBox.setContentAreaFilled(false);
	        firstPlayerNameBox.setBackground(new java.awt.Color(255, 255, 255, 0));
	        c.add(firstPlayerNameBox);
	        
	        
	        
	        
	        secondPlayerNameBox = new JTextField(10);
	        secondPlayerNameBox.setFont(new Font("pixelType",Font.BOLD,60));
	        secondPlayerNameBox.setForeground(new Color(252,217,51));
	        secondPlayerNameBox.setLocation(348, 413);
	        secondPlayerNameBox.setSize(new Dimension(220,72));
	        secondPlayerNameBox.setHorizontalAlignment(JTextField.CENTER);
	        secondPlayerNameBox.setOpaque(false);
	        //secondPlayerNameBox.setContentAreaFilled(false);
	        secondPlayerNameBox.setBackground(new java.awt.Color(255, 255, 255, 0));
	        c.add(secondPlayerNameBox);
	     
	       
	        
	        thirdPlayerNameBox = new JTextField(10);
	        thirdPlayerNameBox.setFont(new Font("pixelType",Font.BOLD,60));
	        thirdPlayerNameBox.setForeground(new Color(252,217,51));
	        thirdPlayerNameBox.setLocation(669, 413);
	        thirdPlayerNameBox.setSize(new Dimension(220,72));
	        thirdPlayerNameBox.setHorizontalAlignment(JTextField.CENTER);
	        thirdPlayerNameBox.setOpaque(false);
	        //thirdPlayerNameBox.setContentAreaFilled(false);
	        thirdPlayerNameBox.setBackground(new java.awt.Color(255, 255, 255, 0));
	        c.add(thirdPlayerNameBox);
	      
	    
	        
	        fourthPlayerNameBox = new JTextField(10);
	        fourthPlayerNameBox.setFont(new Font("pixelType",Font.BOLD,60));
	        fourthPlayerNameBox.setForeground(new Color(252,217,51));
	        fourthPlayerNameBox.setLocation(994, 413);
	        fourthPlayerNameBox.setSize(new Dimension(220,72));
	        fourthPlayerNameBox.setHorizontalAlignment(JTextField.CENTER);
	        fourthPlayerNameBox.setOpaque(false);
	        //fourthPlayerNameBox.setContentAreaFilled(false);
	        fourthPlayerNameBox.setBackground(new java.awt.Color(255, 255, 255, 0));
	        c.add(fourthPlayerNameBox);
	      
	        JButton firstUpdateName = new JButton(".");
	        firstUpdateName.setLocation(245, 420);
	        firstUpdateName.setSize(new Dimension(60,60));
	        firstUpdateName.setOpaque(false);
	        firstUpdateName.setContentAreaFilled(false);
	        firstUpdateName.setBorderPainted(false);
	        firstUpdateName.addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent e){
	            	firstName = firstPlayerNameBox.getText();
	            }
	        });
	        c.add(firstUpdateName);
	        firstPlayerName = new JLabel(new ImageIcon(AssetManager.getPlayerNameBox()));
	        firstPlayerName.setLocation(8, 413);
	        firstPlayerName.setSize(new Dimension(311,72));
	        c.add(firstPlayerName);
	      
	        
	        JButton secondUpdateName = new JButton(".");
	        secondUpdateName.setLocation(568, 420);
	        secondUpdateName.setSize(new Dimension(60,60));
	        secondUpdateName.setOpaque(false);
	        secondUpdateName.setContentAreaFilled(false);
	        secondUpdateName.setBorderPainted(false);
	        secondUpdateName.addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent e){
	            	secondName = secondPlayerNameBox.getText();
	            }
	        });
	        c.add(secondUpdateName);
	        secondPlayerName = new JLabel(new ImageIcon(AssetManager.getPlayerNameBox()));
	        secondPlayerName.setLocation(331, 413);
	        secondPlayerName.setSize(new Dimension(311,72));
	        c.add(secondPlayerName);
	        
	        JButton thirdUpdateName = new JButton(".");
	        thirdUpdateName.setLocation(889, 420);
	        thirdUpdateName.setSize(new Dimension(60,60));
	        thirdUpdateName.setOpaque(false);
	        thirdUpdateName.setContentAreaFilled(false);
	        thirdUpdateName.setBorderPainted(false);
	        thirdUpdateName.addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent e){
	            	thirdName = thirdPlayerNameBox.getText();
	            }
	        });
	        c.add(thirdUpdateName);
	        thirdPlayerName = new JLabel(new ImageIcon(AssetManager.getPlayerNameBox()));
	        thirdPlayerName.setLocation(652, 413);
	        thirdPlayerName.setSize(new Dimension(311,72));
	        c.add(thirdPlayerName);
	        
	        JButton fourthUpdateName = new JButton(".");
	        fourthUpdateName.setLocation(1214, 420);
	        fourthUpdateName.setSize(new Dimension(60,60));
	        fourthUpdateName.setOpaque(false);
	        fourthUpdateName.setContentAreaFilled(false);
	        fourthUpdateName.setBorderPainted(false);
	        fourthUpdateName.addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent e){
	            	fourthName = fourthPlayerNameBox.getText();
	            }
	        });
	        c.add(fourthUpdateName);
	        fourthPlayerName = new JLabel(new ImageIcon(AssetManager.getPlayerNameBox()));
	        fourthPlayerName.setLocation(977, 413);
	        fourthPlayerName.setSize(new Dimension(311,72));
	        c.add(fourthPlayerName);
	        
	        JLabel firstGamesWonBtn = new JLabel("Games Won");
	        firstGamesWonBtn.setFont(new Font("pixelType",Font.PLAIN,48));
	        firstGamesWonBtn.setLocation(12, 520);
	        firstGamesWonBtn.setSize(new Dimension(200,50));
	        firstGamesWonBtn.setForeground(new Color(252,217,51));
	        c.add(firstGamesWonBtn);
	        JLabel secondGamesWonBtn = new JLabel("Games Won");
	        secondGamesWonBtn.setFont(new Font("pixelType",Font.PLAIN,48));
	        secondGamesWonBtn.setLocation(335, 520);
	        secondGamesWonBtn.setSize(new Dimension(200,50));
	        secondGamesWonBtn.setForeground(new Color(252,217,51));
	        c.add(secondGamesWonBtn);
	        JLabel thirdGamesWonBtn = new JLabel("Games Won");
	        thirdGamesWonBtn.setFont(new Font("pixelType",Font.PLAIN,48));
	        thirdGamesWonBtn.setLocation(656, 520);
	        thirdGamesWonBtn.setSize(new Dimension(200,50));
	        thirdGamesWonBtn.setForeground(new Color(252,217,51));
	        c.add(thirdGamesWonBtn);
	        JLabel fourthGamesWonBtn = new JLabel("Games Won");
	        fourthGamesWonBtn.setFont(new Font("pixelType",Font.PLAIN,48));
	        fourthGamesWonBtn.setLocation(981, 520);
	        fourthGamesWonBtn.setSize(new Dimension(200,50));
	        fourthGamesWonBtn.setForeground(new Color(252,217,51));
	        c.add(fourthGamesWonBtn);
	        
	        JLabel firstTotalGames = new JLabel("Games Played");
	        firstTotalGames.setFont(new Font("pixelType",Font.PLAIN,48));
	        firstTotalGames.setLocation(12, 561);
	        firstTotalGames.setSize(new Dimension(200,50));
	        firstTotalGames.setForeground(new Color(252,217,51));
	        c.add(firstTotalGames);
	        JLabel secondTotalGames = new JLabel("Games Played");
	        secondTotalGames.setFont(new Font("pixelType",Font.PLAIN,48));
	        secondTotalGames.setLocation(335, 561);
	        secondTotalGames.setSize(new Dimension(200,50));
	        secondTotalGames.setForeground(new Color(252,217,51));
	        c.add(secondTotalGames);
	        JLabel thirdTotalGames = new JLabel("Games Played");
	        thirdTotalGames.setFont(new Font("pixelType",Font.PLAIN,48));
	        thirdTotalGames.setLocation(656, 561);
	        thirdTotalGames.setSize(new Dimension(200,50));
	        thirdTotalGames.setForeground(new Color(252,217,51));
	        c.add(thirdTotalGames);
	        JLabel fourthTotalGames = new JLabel("Games Played");
	        fourthTotalGames.setFont(new Font("pixelType",Font.PLAIN,48));
	        fourthTotalGames.setLocation(981, 561);
	        fourthTotalGames.setSize(new Dimension(200,50));
	        fourthTotalGames.setForeground(new Color(252,217,51));
	        c.add(fourthTotalGames);
	        
	       
	        
	        
	        //user data
	     //   KingdominoController.displayUserStats();
	        
	        JLabel firstWinnings = new JLabel(firstGamesWon);
	        firstWinnings.setFont(new Font("pixelType",Font.PLAIN,48));
	        firstWinnings.setLocation(187, 520);
	        firstWinnings.setSize(new Dimension(200,50));
	        firstWinnings.setForeground(new Color(252,217,51));
	        c.add(firstWinnings);
	        JLabel secondWinnings = new JLabel(secondGamesWon);
	        secondWinnings.setFont(new Font("pixelType",Font.PLAIN,48));
	        secondWinnings.setLocation(510, 520);
	        secondWinnings.setSize(new Dimension(200,50));
	        secondWinnings.setForeground(new Color(252,217,51));
	        c.add(secondWinnings);
	        JLabel thirdWinnings = new JLabel(thirdGamesWon);
	        thirdWinnings.setFont(new Font("pixelType",Font.PLAIN,48));
	        thirdWinnings.setLocation(831, 520);
	        thirdWinnings.setSize(new Dimension(200,50));
	        thirdWinnings.setForeground(new Color(252,217,51));
	        c.add(thirdWinnings);
	        JLabel fourthWinnings = new JLabel(fourthGamesWon);
	        fourthWinnings.setFont(new Font("pixelType",Font.PLAIN,48));
	        fourthWinnings.setLocation(1156, 520);
	        fourthWinnings.setSize(new Dimension(200,50));
	        fourthWinnings.setForeground(new Color(252,217,51));
	        c.add(fourthWinnings);
	        
	        
	        JLabel firstPlays = new JLabel(firstGamesPlayed);
	        firstPlays.setFont(new Font("pixelType",Font.PLAIN,48));
	        firstPlays.setLocation(207, 561);
	        firstPlays.setSize(new Dimension(200,50));
	        firstPlays.setForeground(new Color(252,217,51));
	        c.add(firstPlays);
	        JLabel secondPlays = new JLabel(secondGamesPlayed);
	        secondPlays.setFont(new Font("pixelType",Font.PLAIN,48));
	        secondPlays.setLocation(530, 561);
	        secondPlays.setSize(new Dimension(200,50));
	        secondPlays.setForeground(new Color(252,217,51));
	        c.add(secondPlays);
	        JLabel thirdPlays = new JLabel(thirdGamesPlayed);
	        thirdPlays.setFont(new Font("pixelType",Font.PLAIN,48));
	        thirdPlays.setLocation(851, 561);
	        thirdPlays.setSize(new Dimension(200,50));
	        thirdPlays.setForeground(new Color(252,217,51));
	        c.add(thirdPlays);
	        JLabel fourthPlays = new JLabel(fourthGamesPlayed);
	        fourthPlays.setFont(new Font("pixelType",Font.PLAIN,48));
	        fourthPlays.setLocation(1176, 561);
	        fourthPlays.setSize(new Dimension(200,50));
	        fourthPlays.setForeground(new Color(252,217,51));
	        c.add(fourthPlays);
	        
	        
	        //ACTIONS
	        
	      /*  firstPlayerNameBox.addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent e){
	            	firstName = firstPlayerNameBox.getText();
	            }
	        });*/
	        secondPlayerNameBox.addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent e){
	            	secondName = secondPlayerNameBox.getText();
	            }
	        });
	        thirdPlayerNameBox.addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent e){
	            	thirdName = thirdPlayerNameBox.getText();
	            }
	        });
	        fourthPlayerNameBox.addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent e){
	            	fourthName = fourthPlayerNameBox.getText();
	            }
	        });

	        exit = new JButton("X");
			exit.setSize(new Dimension(30, 30));
			exit.setForeground(Color.red);
			exit.setLocation(1220, 80);
			c.add(exit);
			

	        // Button actions
			
			exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO
			
					playerMenuFrame.setVisible(false);
				}
			});
	        
	        
	        //adding information of games won and points in player menu
	        
	        
	        
	        
	       
	        
	        
	        pack();
	        
	        
	    }
	    public static String getFirstName() {
	    	return firstName;
	    }
	    public static String getSecondName() {
	    	return secondName;
	    }
	    public static String getThirdName() {
	    	return thirdName;
	    }
	    public static String getFourthName() {
	    	return fourthName;
	    }
}
