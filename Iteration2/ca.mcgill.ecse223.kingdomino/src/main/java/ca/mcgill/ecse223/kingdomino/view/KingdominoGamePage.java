package ca.mcgill.ecse223.kingdomino.view;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.Gamestatus;

public class KingdominoGamePage extends JFrame {

	// Coordinates of the player boards
	public ArrayList<JButton[][]> playerBoards = new ArrayList<>();
	// Player panels
	private ArrayList<JPanel> panelList = new ArrayList<>();

	// Number of players/current player
	private int numberOfPlayers = 4;
	private int currentPlayer;

	// Currently placed domino coordinates
	private ArrayList<int[]> dominoCoordinates = new ArrayList<>();

	// Current active panel direction
	public static String buttonDirection;

	// Current active panel
	public static DominoPanel curPanel;

	// Current Draft dominos
	private ArrayList<DominoPanel> curDraftList = new ArrayList<>();

	// NextDraft panels
	private ArrayList<DominoPanel> nextDraftList = new ArrayList<>();

	public static String firstPlayerScore = "0";
	public static String secondPlayerScore = "0";
	public static String thirdPlayerScore = "0";
	public static String fourthPlayerScore = "0";
	public static String firstBonusScore = "0";
	public static String secondBonusScore = "0";
	public static String thirdBonusScore = "0";
	public static String fourthBonusScore = "0";
	public String firstName = KingdominoPlayerMenuFour.firstName;
	public String secondName = KingdominoPlayerMenuFour.secondName;
	private String thirdName = KingdominoPlayerMenuFour.thirdName;
	private String fourthName = KingdominoPlayerMenuFour.fourthName;
	private Boolean isValidToPlace = true;
	private String playerString;
	public KingdominoGamePage gameFrame = this;
	private JButton exit;
	private JLabel messageLabel;
	private Boolean isMagnified = false;
	private JButton browseButton;
	JLabel nextPlayerMsg = new JLabel();
	JLabel nextPlayerMsg4;

	public KingdominoGamePage() {
		initComponents();
	}

	private void initComponents() {
		this.add(nextPlayerMsg);
		this.setResizable(false);
		setContentPane(new JLabel(new ImageIcon(AssetManager.getGameBackgroundImage())));
		// Use the default border layout but add more panels
		Container c = getContentPane();
		c.setPreferredSize(new Dimension(1280, 720));
		setLayout(null);
		nextPlayerMsg4 = new JLabel("Start Playing!");
		nextPlayerMsg4.setLocation(250, 580);
		nextPlayerMsg4.setSize(new Dimension(500, 200));
		nextPlayerMsg4.setForeground(new Color(70, 232, 142));
		nextPlayerMsg4.setFont(new Font("pixelType", Font.PLAIN, 38));
		this.add(nextPlayerMsg4);

		messageLabel = new JLabel();
		messageLabel.setBounds(1010, 130, 110, 43);
		messageLabel.setVisible(false);
		c.add(messageLabel);

		// JLabel nextPlayerMsg;

		// adding nextplayer message

		// Pop up menu with options
		JMenuBar options = new JMenuBar();
		// Rotating
		JMenu menu = new JMenu("Menu");
		JMenuItem rotateCw = new JMenuItem("rotate clockwise");
		rotateCw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KingdominoController.rotateUI("clockwise");
			}
		});

		// Rotating
		JMenuItem rotateCcw = new JMenuItem("rotate counter clockwise");
		rotateCcw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KingdominoController.rotateUI("counter clockwise");
			}
		});
		menu.add(rotateCw);
		menu.add(rotateCcw);

		// Saving
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KingdominoController.saveUI();
			}
		});
		menu.add(save);
		
		JMenuItem move = new JMenuItem("move");
		move.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KingdominoController.moveDominoUI();
			}
		});
		menu.add(move);
		
		options.add(menu);
		this.getContentPane().add(options);
		this.setJMenuBar(options);

		// All the playerboards
		for (int i = 0; i < numberOfPlayers; i++) {
			JButton[][] board = new JButton[9][9];
			JPanel boardPanel = new JPanel();
			boardPanel.setLayout(new GridLayout(9, 9));
			boardPanel.setBounds(260, 0, 750, 500);
			panelList.add(boardPanel);

			for (int k = 0; k < 9; k++) {
				for (int j = 0; j < 9; j++) {
					if (k == 4 && j == 4) {
						JButton castle = new JButton(new ImageIcon(AssetManager.getCastleImage()));
						castle.setOpaque(false);
						castle.setContentAreaFilled(false);
						castle.setBorderPainted(false);
						board[k][j] = castle;
						boardPanel.add(castle);
						// castle.setEnabled(false);
					} else {
						JButton button = new JButton(new ImageIcon(AssetManager.getPressedButtonImage()));
						board[k][j] = button;
						boardPanel.add(button);
						button.setTransferHandler(new ValueImportTransferHandler());
					}
				}
			}
			playerBoards.add(board);
			boardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			c.add(boardPanel);
		}

		c.add(panelList.get(currentPlayer));

		int j = 0;
		// add the panels to the screen at the correct position
		for (int i = 0; i < 8; i++) {

			DominoPanel panel = new DominoPanel("right", new ImageIcon(AssetManager.getForestTile()),
					new ImageIcon(AssetManager.getWaterTile()));
			if (i <= 3) {
				panel.setBounds(260 + i * 194, 510, 166, 53);
			} else {
				panel.setBounds(260 + j * 194, 625, 166, 53);
				j++;
			}
			curDraftList.add(panel);
			c.add(panel);
		}

		//

		// frame for borders
		JPanel verticalRightBorder = new JPanel();
		verticalRightBorder.setBounds(1111, 0, 13, 720);
		verticalRightBorder.setBackground(new Color(29, 105, 88));
		c.add(verticalRightBorder);

		JPanel horizontalRightBorder = new JPanel();
		horizontalRightBorder.setBounds(1111, 360, 169, 11);
		horizontalRightBorder.setBackground(new Color(29, 105, 88));
		c.add(horizontalRightBorder);

		JPanel verticalLeftBorder = new JPanel();
		verticalLeftBorder.setBounds(156, 0, 13, 720);
		verticalLeftBorder.setBackground(new Color(29, 105, 88));
		c.add(verticalLeftBorder);

		JPanel horizontalLeftBorder = new JPanel();
		horizontalLeftBorder.setBounds(0, 360, 156, 11);
		horizontalLeftBorder.setBackground(new Color(29, 105, 88));
		c.add(horizontalLeftBorder);

		// Draft Buttons
		JToggleButton magnifierButn = new JToggleButton(new ImageIcon(AssetManager.getMagnifierImage()));
		magnifierButn.setBounds(1040, 561, 46, 63);
		magnifierButn.setOpaque(false);
		magnifierButn.setContentAreaFilled(false);
		magnifierButn.setBorderPainted(false);
		magnifierButn.setLocation(1035, 561);
		c.add(magnifierButn);
		magnifierButn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				AbstractButton button = (AbstractButton) e.getSource();
				Boolean isPressed = button.getModel().isSelected();
				if (isPressed) {
					isMagnified = true;
					magnifierButn.setBorderPainted(true);
					magnifierButn.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.RED));
				} else {
					magnifierButn.setBorderPainted(false);
					isMagnified = false;
				}

			}
		});

		JButton soundButn = new JButton(new ImageIcon(AssetManager.getSoundButtonImage()));
		soundButn.setBounds(185, 561, 70, 70);
		soundButn.setOpaque(false);
		soundButn.setContentAreaFilled(false);
		soundButn.setBorderPainted(false);
		soundButn.setLocation(185, 561);
		soundButn.setPreferredSize(new Dimension(20, 20));
		c.add(soundButn);
		soundButn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO

				KingdominoStartPage.settingsFrame.setVisible(true);
			}
		});

		JButton crestButn = new JButton(new ImageIcon(AssetManager.getChestButtonImage()));
		crestButn.setBounds(1035, 641, 70, 70);
		crestButn.setOpaque(false);
		crestButn.setContentAreaFilled(false);
		crestButn.setBorderPainted(false);
		crestButn.setLocation(1035, 641);
		c.add(crestButn);
		crestButn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				KingdominoStartPage.settingsFrame.setVisible(true);
			}
		});

		JButton ruleButn = new JButton(new ImageIcon(AssetManager.getRuleButtonImage()));
		ruleButn.setBounds(185, 641, 70, 70);
		ruleButn.setOpaque(false);
		ruleButn.setContentAreaFilled(false);
		ruleButn.setBorderPainted(false);
		ruleButn.setLocation(185, 641);
		ruleButn.setPreferredSize(new Dimension(20, 20));
		c.add(ruleButn);
		// Opens the rules
		ruleButn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				new KingdominoRules().setVisible(true);
			}
		});

		// browse dominos
		browseButton = new JButton();// add image icon later
		browseButton.setText("Browse");
		browseButton.setFont(new Font("Dialog", Font.PLAIN, 7));
		browseButton.setBounds(185, 481, 70, 70);
		browseButton.setLocation(185, 481);
		browseButton.setPreferredSize(new Dimension(20, 20));
		browseButton.setForeground(new Color(252, 193, 51));
		browseButton.setBackground(Color.cyan);
		browseButton.setOpaque(true);
		browseButton.setBorderPainted(false);
		c.add(browseButton);
		browseButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				browseButtonActionPerformed(evt);
			}

			private void browseButtonActionPerformed(ActionEvent evt) {
				new KingdominoBrowsingPage().setVisible(true);
			}
		});

		// ADDING TOP BORDERS

		// adding borders: TOP RIGHT
		JPanel topRightBorder = new JPanel();
		topRightBorder.setLayout(new GridLayout(4, 1));
		topRightBorder.setBounds(1124, 0, 156, 360);

		JLabel goldKingIcon = new JLabel(new ImageIcon(AssetManager.getGreenKing()), JLabel.CENTER);
		topRightBorder.add(goldKingIcon);
		if (secondName == null) {
			secondName = "PLAYER 2";
		}
		JLabel player3 = new JLabel(secondName, JLabel.CENTER);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		;
		ge.registerFont(AssetManager.getPixelType());

		player3.setFont(new Font("pixelType", Font.BOLD, 48));
		player3.setPreferredSize(new Dimension(156, 40));
		player3.setBounds(1150, 52, 127, 24);
		player3.setForeground(new Color(252, 193, 51));
		player3.setBackground(Color.cyan);
		topRightBorder.add(player3);

		JButton secondScoreButton = new JButton(secondPlayerScore + " / " + secondBonusScore);
		secondScoreButton.setBounds(1175, 10, 156, 40);
		secondScoreButton.setForeground(new Color(252, 193, 51));
		secondScoreButton.setPreferredSize(new Dimension(57, 25));
		secondScoreButton.setLocation(1200, 180);
		secondScoreButton.setFont(new Font("pixelType", Font.BOLD, 45));
		topRightBorder.add(secondScoreButton);
		secondScoreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				KingdominoController.displayScore();
				repaint();
				secondScoreButton.setText(secondPlayerScore + " / " + secondBonusScore);
				repaint();
			}
		});

		JButton smallButton = new JButton(new ImageIcon(AssetManager.getSmallBoard()));
		smallButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				if (isMagnified) {
					new firstPlayerBoardWindow(2).setVisible(true);
				}
			}
		});
		topRightBorder.add(smallButton);

		topRightBorder.setBackground(new Color(57, 136, 152));
		c.add(topRightBorder);

		// adding borders BOTTOM RIGHT
		JPanel bottomRightBorder = new JPanel();
		bottomRightBorder.setLayout(new GridLayout(4, 1));
		bottomRightBorder.setBounds(1124, 360, 156, 360);
		JLabel greenKingIcon = new JLabel(new ImageIcon(AssetManager.getRedKing()), JLabel.CENTER);
		// goldKingIcon.setBounds(1175,10,156,100);

		bottomRightBorder.add(greenKingIcon);
		if (fourthName == null) {
			fourthName = "PLAYER 4";
		}
		JLabel player4 = new JLabel(fourthName, JLabel.CENTER);
		// File pixelType2 = new
		// File("/Users/mac/Documents/GitHub/ecse223-group-project-08/Iteration2/ca.mcgill.ecse223.kingdomino/Assets/Fonts/Pixeltype.ttf");

		player4.setFont(new Font("pixelType", Font.BOLD, 48));
		player4.setPreferredSize(new Dimension(156, 40));

		player4.setBounds(1150, 52, 127, 24);
		player4.setForeground(new Color(252, 193, 51));
		player4.setBackground(Color.cyan);
		bottomRightBorder.add(player4);

		JButton fourthScoreButton = new JButton(fourthPlayerScore + " / " + fourthBonusScore);
		fourthScoreButton.setBounds(1175, 10, 156, 40);
		fourthScoreButton.setForeground(new Color(252, 193, 51));
		fourthScoreButton.setPreferredSize(new Dimension(57, 25));
		fourthScoreButton.setLocation(1200, 180);
		fourthScoreButton.setFont(new Font("pixelType", Font.BOLD, 45));
		bottomRightBorder.add(fourthScoreButton);
		fourthScoreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				KingdominoController.displayScore();
				repaint();
				fourthScoreButton.setText(fourthPlayerScore + " / " + fourthBonusScore);
				repaint();
			}
		});

		JButton smallButton1 = new JButton(new ImageIcon(AssetManager.getSmallBoard()));
		smallButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				if (isMagnified) {
					new firstPlayerBoardWindow(4).setVisible(true);
				}
			}
		});
		bottomRightBorder.add(smallButton1);

		bottomRightBorder.setBackground(new Color(57, 136, 152));
		c.add(bottomRightBorder);

		// ADDING LEFT ONES

		// adding borders: TOP LEFT: first player
		JPanel topLeftBorder = new JPanel();
		topLeftBorder.setLayout(new GridLayout(4, 1));
		topLeftBorder.setBounds(0, 0, 156, 360);
		goldKingIcon = new JLabel(new ImageIcon(AssetManager.getBlueKing()), JLabel.CENTER);
		topLeftBorder.add(goldKingIcon);

		if (firstName == null) {
			firstName = "PLAYER 1";
		}
		JLabel player1 = new JLabel(firstName, JLabel.CENTER);
		player1.setFont(new Font("pixelType", Font.BOLD, 48));
		player1.setPreferredSize(new Dimension(156, 40));
		player1.setBounds(1150, 52, 127, 24);
		player1.setForeground(new Color(252, 193, 51));
		player1.setBackground(Color.cyan);
		topLeftBorder.add(player1);

		JButton firstScoreButton = new JButton(firstPlayerScore + " / " + firstBonusScore);
		firstScoreButton.setBounds(1175, 10, 156, 40);
		firstScoreButton.setForeground(new Color(252, 193, 51));
		firstScoreButton.setFont(new Font("pixelType", Font.BOLD, 45));
		firstScoreButton.setPreferredSize(new Dimension(57, 25));
		firstScoreButton.setLocation(1200, 180);
		topLeftBorder.add(firstScoreButton);
		firstScoreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				KingdominoController.displayScore();
				repaint();
				firstScoreButton.setText(firstPlayerScore + " / " + firstBonusScore);
				repaint();
			}
		});

		JButton smallButton2 = new JButton(new ImageIcon(AssetManager.getSmallBoard()));
		smallButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				if (isMagnified) {
					new firstPlayerBoardWindow(1).setVisible(true);
				}
			}
		});
		topLeftBorder.add(smallButton2);
		topLeftBorder.setBackground(new Color(57, 136, 152));
		c.add(topLeftBorder);

		// adding borders BOTTOM LEFT
		JPanel bottomLeftBorder = new JPanel();
		bottomLeftBorder.setLayout(new GridLayout(4, 1));
		bottomLeftBorder.setBounds(0, 360, 156, 360);
		greenKingIcon = new JLabel(new ImageIcon(AssetManager.getGoldKing()), JLabel.CENTER);
		bottomLeftBorder.add(greenKingIcon);

		if (thirdName == null) {
			thirdName = "PLAYER 3";
		}
		JLabel player2 = new JLabel(thirdName, JLabel.CENTER);
		player2.setFont(new Font("pixelType", Font.BOLD, 48));
		player2.setPreferredSize(new Dimension(156, 40));

		player2.setBounds(1150, 52, 127, 24);
		player2.setForeground(new Color(252, 193, 51));
		player2.setBackground(Color.cyan);
		bottomLeftBorder.add(player2);

		JButton thirdScoreButton = new JButton(thirdPlayerScore + " / " + thirdBonusScore);
		thirdScoreButton.setBounds(1175, 10, 156, 40);
		thirdScoreButton.setForeground(new Color(252, 193, 51));
		thirdScoreButton.setPreferredSize(new Dimension(57, 25));
		thirdScoreButton.setFont(new Font("pixelType", Font.BOLD, 45));
		thirdScoreButton.setLocation(1200, 180);
		bottomLeftBorder.add(thirdScoreButton);
		thirdScoreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				KingdominoController.displayScore();
				repaint();
				thirdScoreButton.setText(thirdPlayerScore + " / " + thirdBonusScore);
				repaint();
			}
		});

		bottomLeftBorder.setBackground(new Color(57, 136, 152));
		JButton smallButton3 = new JButton(new ImageIcon(AssetManager.getSmallBoard()));
		smallButton3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				if (isMagnified) {
					new firstPlayerBoardWindow(3).setVisible(true);
				}
			}
		});
		bottomLeftBorder.add(smallButton3);
		c.add(bottomLeftBorder);

		// Action that is performed upon the window closing
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// KingdominoStartPage.getFrame().setVisible(true);
				e.getWindow().dispose();
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
				gameFrame.setVisible(false);
			}
		});

		pack();
	}

	public void refreshData() {
		for (JPanel panel : panelList) {
			this.remove(panel);
		}

	

		switch (getCurrentPlayer()) {
		case 0:
			nextPlayerMsg4.setText("Blue King Playing!");
			/*
			 * JLabel nextPlayerMsg = new JLabel("Green King Playing!");
			 * nextPlayerMsg.setLocation(250,580); nextPlayerMsg.setSize(new
			 * Dimension(500,200)); nextPlayerMsg.setForeground(new Color(70, 232, 142));
			 * nextPlayerMsg.setFont(new Font("pixelType", Font.PLAIN, 38));
			 * c.add(nextPlayerMsg);
			 */
			break;
		case 1:
			nextPlayerMsg4.setText("Green King Playing!");
			/*
			 * JLabel nextPlayerMsg1 = new JLabel("Blue King Playing!");
			 * nextPlayerMsg1.setLocation(250,580); nextPlayerMsg1.setSize(new
			 * Dimension(500,200)); nextPlayerMsg1.setForeground(new Color(70, 232, 142));
			 * nextPlayerMsg1.setFont(new Font("pixelType", Font.PLAIN, 38));
			 * c.add(nextPlayerMsg1);
			 */
			break;
		case 2:
			nextPlayerMsg4.setText("Gold King Playing!");
			/*
			 * JLabel nextPlayerMsg2 = new JLabel("Gold King Playing!");
			 * nextPlayerMsg2.setLocation(250,580); nextPlayerMsg2.setSize(new
			 * Dimension(500,200)); nextPlayerMsg2.setForeground(new Color(70, 232, 142));
			 * nextPlayerMsg2.setFont(new Font("pixelType", Font.PLAIN, 38));
			 * c.add(nextPlayerMsg2);
			 */
			break;
		case 3:
			nextPlayerMsg4.setText("Red King Playing!");
			/*
			 * JLabel nextPlayerMsg3 = new JLabel("Red King Playing!");
			 * nextPlayerMsg3.setLocation(250,580); nextPlayerMsg3.setSize(new
			 * Dimension(500,200)); nextPlayerMsg3.setForeground(new Color(70, 232, 142));
			 * nextPlayerMsg3.setFont(new Font("pixelType", Font.PLAIN, 38));
			 * c.add(nextPlayerMsg3);
			 */
			break;

		}
		repaint();
		this.add(panelList.get(currentPlayer));
		JLabel nextPlayerMsg;
		repaint();
	}

	public void remakeBoards() {
		// remake all the player boards
		this.getContentPane().remove(panelList.get(currentPlayer));
		panelList.removeAll(panelList);
		playerBoards.removeAll(playerBoards);
		for (int i = 0; i < numberOfPlayers; i++) {
			JButton[][] board = new JButton[9][9];
			JPanel boardPanel = new JPanel();
			boardPanel.setLayout(new GridLayout(9, 9));
			boardPanel.setBounds(260, 0, 750, 500);
			panelList.add(boardPanel);

			for (int k = 0; k < 9; k++) {
				for (int j = 0; j < 9; j++) {
					if (k == 4 && j == 4) {
						JButton castle = new JButton(new ImageIcon(AssetManager.getCastleImage()));
						board[k][j] = castle;
						boardPanel.add(castle);
						castle.setEnabled(false);
					} else {
						JButton button = new JButton(new ImageIcon(AssetManager.getPressedButtonImage()));
						board[k][j] = button;
						boardPanel.add(button);
						button.setTransferHandler(new ValueImportTransferHandler());
					}
				}
			}
			playerBoards.add(board);
		}
	}

	public void emptyCurrentDraft() {
		for (DominoPanel cur : curDraftList) {
			this.getContentPane().remove(cur);
		}
		curDraftList.removeAll(curDraftList);
	}

	public void emptyNextDraft() {
		for (DominoPanel cur : nextDraftList) {
			this.getContentPane().remove(cur);
		}
		nextDraftList.removeAll(nextDraftList);
	}

	public void remakeCurDraft() {
		for (int i = 0; i < curDraftList.size(); i++) {
			DominoPanel panel = curDraftList.get(i);
			panel.setBounds(260 + i * 194, 510, 166, 53);
			this.getContentPane().add(panel);
		}
	}

	public void remakeNextDraft() {
		for (int i = 0; i < nextDraftList.size(); i++) {
			DominoPanel panel = nextDraftList.get(i);
			panel.setBounds(260 + i * 194, 625, 166, 53);
			this.getContentPane().add(panel);
		}

	}

	public void resetHandler(JButton button) {
		button.setTransferHandler(new ValueImportTransferHandler());
	}

	public JButton getButton(JButton[][] board, int x, int y) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j].equals(board[x][y])) {
					return board[i][j];
				}
			}
		}

		return null;
	}

	public int[] getButtonCoordinates(Component component, JButton[][] board) {
		if (component instanceof JButton) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if (component == board[i][j]) {
						return new int[] { i, j };
					}
				}
			}
		}
		return null;
	}

	public int gameToUICoordinates(int y) {

		for (int i = 0; i < 9; i++) {
			if (i == 4 - y) {
				return i;
			}
		}
		return y;
	}

	// Getter and setters

	// Getter and setters

	public ArrayList<JButton[][]> getPlayerBoards() {
		return playerBoards;
	}

	public ArrayList<JPanel> getPanelList() {
		return panelList;
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public ArrayList<DominoPanel> getCurDraftList() {
		return curDraftList;
	}

	public ArrayList<DominoPanel> getNextDraftList() {
		return nextDraftList;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void setMessageLabel(JLabel messageLabel) {
		this.messageLabel = messageLabel;
	}

	public JLabel getMessageLabel() {
		return this.messageLabel;
	}

}
