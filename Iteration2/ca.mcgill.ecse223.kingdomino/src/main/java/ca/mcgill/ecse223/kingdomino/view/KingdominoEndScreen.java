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

	public class KingdominoEndScreen extends JFrame {

	    public JLabel firstPlayerNameBox;
	    public JLabel secondPlayerNameBox;
	    public JLabel thirdPlayerNameBox;
	    public JLabel fourthPlayerNameBox;
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

	    public JButton exit;
	    public JFrame playerMenuFrame = this;
	    public String firstName = KingdominoPlayerMenuFour.getFirstName();
		public String secondName = KingdominoPlayerMenuFour.getSecondName();
		private String thirdName = KingdominoPlayerMenuFour.getThirdName();
		private String fourthName = KingdominoPlayerMenuFour.getFourthName();
		public static String firstPlayerScore = "0";
		public static String secondPlayerScore = "0";
		public static String thirdPlayerScore = "0";
		public static String fourthPlayerScore = "0";
		public static String firstBonusScore = "0";
		public static String secondBonusScore = "0";
		public static String thirdBonusScore = "0";
		public static String fourthBonusScore = "0";
		public static String firstRankingRes = "0";
		public static String secondRankingRes = "0";
		public static String thirdRankingRes = "0";
		public static String fourthRankingRes = "0";
		

	    public KingdominoEndScreen() {
	        initComponents();
	    }

	    private void initComponents() {
	        setLayout(null);
	        setContentPane(new JLabel(new ImageIcon(AssetManager.getGameBackgroundImage())));
	        Container c = getContentPane();
	        c.setPreferredSize(new Dimension(1280, 720));
	        
	        if(firstName == null) {
	        	firstName = "PLAYER 1";
	        }
	        if(secondName == null) {
	        	secondName = "PLAYER 2";
	        }
	        if(thirdName == null) {
	            thirdName = "PLAYER 3";
	        }
	        if(fourthName == null) {
	        	fourthName = "PLAYER 4";
	        }
	        
	        firstPlayerColor = new JLabel(new ImageIcon(AssetManager.getFirstPlayerColor()));
	        firstPlayerColor.setLocation(8, 325);
	        firstPlayerColor.setSize(new Dimension(311,72));
	        c.add(firstPlayerColor);
	        
	        secondPlayerColor = new JLabel(new ImageIcon(AssetManager.getSecondPlayerColor()));
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
	        
	        
	        firstPlayerIcon = new JLabel(new ImageIcon(AssetManager.getFirstPlayerIcon()));
	        firstPlayerIcon.setLocation(85,15);
	        firstPlayerIcon.setSize(new Dimension(147,300));
	        c.add(firstPlayerIcon);
	        
	        secondPlayerIcon = new JLabel(new ImageIcon(AssetManager.getSecondPlayerIcon()));
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
 //NAMES
	        
	        firstPlayerNameBox = new JLabel(firstName);
	        firstPlayerNameBox.setFont(new Font("pixelType",Font.BOLD,60));
	        firstPlayerNameBox.setForeground(new Color(252,217,51));
	        firstPlayerNameBox.setLocation(25, 413);
	        firstPlayerNameBox.setSize(new Dimension(220,72));
	        firstPlayerNameBox.setHorizontalAlignment(JLabel.CENTER);
	        firstPlayerNameBox.setOpaque(false);
	        //firstPlayerNameBox.setContentAreaFilled(false);
	        firstPlayerNameBox.setBackground(new java.awt.Color(255, 255, 255, 0));
	        c.add(firstPlayerNameBox);
	        
	        
	        
	        
	        secondPlayerNameBox = new JLabel(secondName);
	        secondPlayerNameBox.setFont(new Font("pixelType",Font.BOLD,60));
	        secondPlayerNameBox.setForeground(new Color(252,217,51));
	        secondPlayerNameBox.setLocation(348, 413);
	        secondPlayerNameBox.setSize(new Dimension(220,72));
	        secondPlayerNameBox.setHorizontalAlignment(JLabel.CENTER);
	        secondPlayerNameBox.setOpaque(false);
	        //secondPlayerNameBox.setContentAreaFilled(false);
	        secondPlayerNameBox.setBackground(new java.awt.Color(255, 255, 255, 0));
	        c.add(secondPlayerNameBox);
	     
	       
	        
	        thirdPlayerNameBox = new JLabel(thirdName);
	        thirdPlayerNameBox.setFont(new Font("pixelType",Font.BOLD,60));
	        thirdPlayerNameBox.setForeground(new Color(252,217,51));
	        thirdPlayerNameBox.setLocation(669, 413);
	        thirdPlayerNameBox.setSize(new Dimension(220,72));
	        thirdPlayerNameBox.setHorizontalAlignment(JLabel.CENTER);
	        //thirdPlayerNameBox.setOpaque(false);
	        //thirdPlayerNameBox.setContentAreaFilled(false);
	       // thirdPlayerNameBox.setBackground(new java.awt.Color(255, 255, 255, 0));
	        c.add(thirdPlayerNameBox);
	      
	    
	        
	        fourthPlayerNameBox = new JLabel(fourthName);
	        fourthPlayerNameBox.setFont(new Font("pixelType",Font.BOLD,60));
	        fourthPlayerNameBox.setForeground(new Color(252,217,51));
	        fourthPlayerNameBox.setLocation(994, 413);
	        fourthPlayerNameBox.setSize(new Dimension(220,72));
	        fourthPlayerNameBox.setHorizontalAlignment(JLabel.CENTER);
	        //fourthPlayerNameBox.setContentAreaFilled(false);
	        fourthPlayerNameBox.setBackground(new java.awt.Color(255, 255, 255, 0));
	        c.add(fourthPlayerNameBox);
	        
	        firstPlayerName = new JLabel(new ImageIcon(AssetManager.getPlayerNameBox()));
	        firstPlayerName.setLocation(8, 413);
	        firstPlayerName.setSize(new Dimension(311,72));
	        c.add(firstPlayerName);
	        
	        secondPlayerName = new JLabel(new ImageIcon(AssetManager.getPlayerNameBox()));
	        secondPlayerName.setLocation(331, 413);
	        secondPlayerName.setSize(new Dimension(311,72));
	        c.add(secondPlayerName);
	        
	        thirdPlayerName = new JLabel(new ImageIcon(AssetManager.getPlayerNameBox()));
	        thirdPlayerName.setLocation(652, 413);
	        thirdPlayerName.setSize(new Dimension(311,72));
	        c.add(thirdPlayerName);
	        
	        fourthPlayerName = new JLabel(new ImageIcon(AssetManager.getPlayerNameBox()));
	        fourthPlayerName.setLocation(977, 413);
	        fourthPlayerName.setSize(new Dimension(311,72));
	        c.add(fourthPlayerName);
	        
	        JLabel firstRanking = new JLabel("Ranking");
	        firstRanking.setFont(new Font("pixelType",Font.PLAIN,48));
	        firstRanking.setLocation(12, 520);
	        firstRanking.setSize(new Dimension(200,50));
	        firstRanking.setForeground(new Color(252,217,51));
	        c.add(firstRanking);
	        JLabel secondRanking = new JLabel("Ranking");
	        secondRanking.setFont(new Font("pixelType",Font.PLAIN,48));
	        secondRanking.setLocation(335, 520);
	        secondRanking.setSize(new Dimension(200,50));
	        secondRanking.setForeground(new Color(252,217,51));
	        c.add(secondRanking);
	        JLabel thirdRanking = new JLabel("Ranking");
	        thirdRanking.setFont(new Font("pixelType",Font.PLAIN,48));
	        thirdRanking.setLocation(656, 520);
	        thirdRanking.setSize(new Dimension(200,50));
	        thirdRanking.setForeground(new Color(252,217,51));
	        c.add(thirdRanking);
	        JLabel fourthRanking = new JLabel("Ranking");
	        fourthRanking.setFont(new Font("pixelType",Font.PLAIN,48));
	        fourthRanking.setLocation(981, 520);
	        fourthRanking.setSize(new Dimension(200,50));
	        fourthRanking.setForeground(new Color(252,217,51));
	        c.add(fourthRanking);
	        
	       KingdominoController.displayEndScore();
	       KingdominoController.displayRanking();
	        
	        JLabel firstScore = new JLabel("Score");
	        firstScore.setFont(new Font("pixelType",Font.PLAIN,48));
	        firstScore.setLocation(12, 561);
	        firstScore.setSize(new Dimension(200,50));
	        firstScore.setForeground(new Color(252,217,51));
	        c.add(firstScore);
	        JLabel secondScore = new JLabel("Score");
	        secondScore.setFont(new Font("pixelType",Font.PLAIN,48));
	        secondScore.setLocation(335, 561);
	        secondScore.setSize(new Dimension(200,50));
	        secondScore.setForeground(new Color(252,217,51));
	        c.add(secondScore);
	        JLabel thirdScore = new JLabel("Score");
	        thirdScore.setFont(new Font("pixelType",Font.PLAIN,48));
	        thirdScore.setLocation(656, 561);
	        thirdScore.setSize(new Dimension(200,50));
	        thirdScore.setForeground(new Color(252,217,51));
	        c.add(thirdScore);
	        JLabel fourthScore = new JLabel("Score");
	        fourthScore.setFont(new Font("pixelType",Font.PLAIN,48));
	        fourthScore.setLocation(981, 561);
	        fourthScore.setSize(new Dimension(200,50));
	        fourthScore.setForeground(new Color(252,217,51));
	        c.add(fourthScore);
	        
	        JLabel firstScoreResult = new JLabel(firstPlayerScore);
	        firstScoreResult.setFont(new Font("pixelType",Font.PLAIN,48));
	        firstScoreResult.setLocation(187, 561);
	        firstScoreResult.setSize(new Dimension(200,50));
	        firstScoreResult.setForeground(new Color(252,217,51));
	        c.add(firstScoreResult);
	        JLabel secondScoreResult = new JLabel(secondPlayerScore);
	        secondScoreResult.setFont(new Font("pixelType",Font.PLAIN,48));
	        secondScoreResult.setLocation(510, 561);
	        secondScoreResult.setSize(new Dimension(200,50));
	        secondScoreResult.setForeground(new Color(252,217,51));
	        c.add(secondScoreResult);
	        JLabel thirdScoreResult = new JLabel(thirdPlayerScore);
	        thirdScoreResult.setFont(new Font("pixelType",Font.PLAIN,48));
	        thirdScoreResult.setLocation(831, 561);
	        thirdScoreResult.setSize(new Dimension(200,50));
	        thirdScoreResult.setForeground(new Color(252,217,51));
	        c.add(thirdScoreResult);
	        JLabel fourthScoreResult = new JLabel(fourthPlayerScore);
	        fourthScoreResult.setFont(new Font("pixelType",Font.PLAIN,48));
	        fourthScoreResult.setLocation(1156, 561);
	        fourthScoreResult.setSize(new Dimension(200,50));
	        fourthScoreResult.setForeground(new Color(252,217,51));
	        c.add(fourthScoreResult);
	        
	        
	        
	        
	        
	        
	        
	        JLabel firstBonus = new JLabel("Bonus");
	        firstBonus.setFont(new Font("pixelType",Font.PLAIN,48));
	        firstBonus.setLocation(12, 602);
	        firstBonus.setSize(new Dimension(200,50));
	        firstBonus.setForeground(new Color(252,217,51));
	        c.add(firstBonus);
	        JLabel secondBonus = new JLabel("Bonus");
	        secondBonus.setFont(new Font("pixelType",Font.PLAIN,48));
	        secondBonus.setLocation(335, 602);
	        secondBonus.setSize(new Dimension(200,50));
	        secondBonus.setForeground(new Color(252,217,51));
	        c.add(secondBonus);
	        JLabel thirdBonus = new JLabel("Bonus");
	        thirdBonus.setFont(new Font("pixelType",Font.PLAIN,48));
	        thirdBonus.setLocation(656, 602);
	        thirdBonus.setSize(new Dimension(200,50));
	        thirdBonus.setForeground(new Color(252,217,51));
	        c.add(thirdBonus);
	        JLabel fourthBonus = new JLabel("Bonus");
	        fourthBonus.setFont(new Font("pixelType",Font.PLAIN,48));
	        fourthBonus.setLocation(981, 602);
	        fourthBonus.setSize(new Dimension(200,50));
	        fourthBonus.setForeground(new Color(252,217,51));
	        c.add(fourthBonus);
	        
	        JLabel firstBonusResult = new JLabel(firstBonusScore);
	        firstBonusResult.setFont(new Font("pixelType",Font.PLAIN,48));
	        firstBonusResult.setLocation(187, 602);
	        firstBonusResult.setSize(new Dimension(200,50));
	        firstBonusResult.setForeground(new Color(252,217,51));
	        c.add(firstBonusResult);
	        JLabel secondBonusResult = new JLabel(secondBonusScore);
	        secondBonusResult.setFont(new Font("pixelType",Font.PLAIN,48));
	        secondBonusResult.setLocation(510, 602);
	        secondBonusResult.setSize(new Dimension(200,50));
	        secondBonusResult.setForeground(new Color(252,217,51));
	        c.add(secondBonusResult);
	        JLabel thirdBonusResult = new JLabel(thirdBonusScore);
	        thirdBonusResult.setFont(new Font("pixelType",Font.PLAIN,48));
	        thirdBonusResult.setLocation(831, 602);
	        thirdBonusResult.setSize(new Dimension(200,50));
	        thirdBonusResult.setForeground(new Color(252,217,51));
	        c.add(thirdBonusResult);
	        JLabel fourthBonusResult = new JLabel(fourthBonusScore);
	        fourthBonusResult.setFont(new Font("pixelType",Font.PLAIN,48));
	        fourthBonusResult.setLocation(1156, 602);
	        fourthBonusResult.setSize(new Dimension(200,50));
	        fourthBonusResult.setForeground(new Color(252,217,51));
	        c.add(fourthBonusResult);
	        
	        JLabel firstRankingResult = new JLabel(firstRankingRes);
	        firstRankingResult.setFont(new Font("pixelType",Font.PLAIN,48));
	        firstRankingResult.setLocation(187, 602);
	        firstRankingResult.setSize(new Dimension(200,50));
	        firstRankingResult.setForeground(new Color(252,217,51));
	        c.add(firstRankingResult);
	        JLabel secondRankingResult = new JLabel(secondRankingRes);
	        secondRankingResult.setFont(new Font("pixelType",Font.PLAIN,48));
	        secondRankingResult.setLocation(510, 602);
	        secondRankingResult.setSize(new Dimension(200,50));
	        secondRankingResult.setForeground(new Color(252,217,51));
	        c.add(secondRankingResult);
	        JLabel thirdRankingResult = new JLabel(thirdRankingRes);
	        thirdRankingResult.setFont(new Font("pixelType",Font.PLAIN,48));
	        thirdRankingResult.setLocation(831, 602);
	        thirdRankingResult.setSize(new Dimension(200,50));
	        thirdRankingResult.setForeground(new Color(252,217,51));
	        c.add(thirdRankingResult);
	        JLabel fourthRankingResult = new JLabel(fourthRankingRes);
	        fourthRankingResult.setFont(new Font("pixelType",Font.PLAIN,48));
	        fourthRankingResult.setLocation(1156, 602);
	        fourthRankingResult.setSize(new Dimension(200,50));
	        fourthRankingResult.setForeground(new Color(252,217,51));
	        c.add(fourthRankingResult);
	        
	       
	        pack();
	    }
	}
	        
	        