package ca.mcgill.ecse223.kingdomino.controller;

import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.Gamestatus;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.view.AssetManager;
import ca.mcgill.ecse223.kingdomino.view.DominoPanel;
import ca.mcgill.ecse223.kingdomino.view.KingdominoEndScreen;
import ca.mcgill.ecse223.kingdomino.view.KingdominoGamePage;
import ca.mcgill.ecse223.kingdomino.view.KingdominoPlayerMenuFour;
import ca.mcgill.ecse223.kingdomino.view.UIDominos;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;

public class KingdominoController {
	// Boolean to overwrite the previous file
	private static boolean overwrite = false;

	public static List<Integer> initialorder = new ArrayList<Integer>();
	public static int nextorder;
	private static boolean rotate = false;
	public static Boolean castleAdjacency = true;
	public static Boolean neighborAdjacency = true;
	public static Boolean noOverlap = true;
	public static Boolean satisfyGridSize = true;

	// UI methods
	public static void switchState() {
		KingdominoApplication.endPage = new KingdominoEndScreen();
		KingdominoApplication.endPage.setVisible(true);
	}

	/**
	 * Method saves the current game
	 * 
	 * @Author Viet
	 */
	public static void saveUI() {
		// TODO save statemachine status
		JFileChooser jc = new JFileChooser();
		jc.setCurrentDirectory(new File("./save"));
		int r = jc.showSaveDialog(null);

		if (r == JFileChooser.APPROVE_OPTION) {
			saveGame(jc.getSelectedFile().getAbsolutePath());
		}

	}

	/**
	 * Method loads a game
	 * 
	 * @author Viet
	 */
	public static void loadUI() {

		String validation = null;

		JFileChooser jc = new JFileChooser();
		jc.setCurrentDirectory(new File("./save"));
		int r = jc.showOpenDialog(null);
		if (r == JFileChooser.APPROVE_OPTION) {
			String path = jc.getSelectedFile().getAbsolutePath();
			validation = loadGame(path);
		}
		if (validation != null && validation.equalsIgnoreCase("valid file")) {
			KingdominoApplication.gamePage = new KingdominoGamePage();
			KingdominoApplication.gamePage.setVisible(true);
			// Add elements to the UI
			Game game = KingdominoApplication.getKingdomino().getCurrentGame();
			// Recreate player boards
			KingdominoGamePage gamePage = KingdominoApplication.gamePage;
			for (int i = 0; i < game.getNumberOfPlayers(); i++) {
				Player player = game.getPlayer(i);
				for (KingdomTerritory territory : player.getKingdom().getTerritories()) {
					if (territory instanceof DominoInKingdom) {
						DominoInKingdom dnk = (DominoInKingdom) territory;
						int x = dnk.getX();
						int y = dnk.getY();
						String direction = dnk.getDirection().name();
						DominoPanel panel = UIDominos.getUIDominoById(dnk.getDomino().getId());
						dnk.getDomino().setStatus(DominoStatus.PlacedInKingdom);
						int boardXCoordinates = x + 4;
						int boardYCoordinates = gamePage.gameToUICoordinates(y);

						// Get the domino id and its attributes

						// Place the domino on the board
						JButton[][] playerBoards = gamePage.getPlayerBoards().get(i);

						// Place random picture for now
						switch (direction) {
						case "Up":
							playerBoards[boardYCoordinates][boardXCoordinates].setIcon(panel.getLeftTile().getIcon());
							playerBoards[boardYCoordinates - 1][boardXCoordinates]
									.setIcon(panel.getRightTile().getIcon());
							playerBoards[boardYCoordinates][boardXCoordinates].setTransferHandler(null);
							playerBoards[boardYCoordinates - 1][boardXCoordinates].setTransferHandler(null);
							break;
						case "Down":
							playerBoards[boardYCoordinates][boardXCoordinates].setIcon(panel.getLeftTile().getIcon());
							playerBoards[boardYCoordinates + 1][boardXCoordinates]
									.setIcon(panel.getRightTile().getIcon());
							playerBoards[boardYCoordinates][boardXCoordinates].setTransferHandler(null);
							playerBoards[boardYCoordinates + 1][boardXCoordinates].setTransferHandler(null);
							break;
						case "Left":
							playerBoards[boardYCoordinates][boardXCoordinates].setIcon(panel.getLeftTile().getIcon());
							playerBoards[boardYCoordinates][boardXCoordinates - 1]
									.setIcon(panel.getRightTile().getIcon());
							playerBoards[boardYCoordinates][boardXCoordinates].setTransferHandler(null);
							playerBoards[boardYCoordinates][boardXCoordinates - 1].setTransferHandler(null);
							break;
						case "Right":
							playerBoards[boardYCoordinates][boardXCoordinates].setIcon(panel.getLeftTile().getIcon());
							playerBoards[boardYCoordinates][boardXCoordinates + 1]
									.setIcon(panel.getRightTile().getIcon());
							playerBoards[boardYCoordinates][boardXCoordinates].setTransferHandler(null);
							playerBoards[boardYCoordinates][boardXCoordinates + 1].setTransferHandler(null);
							break;
						}

					}
				}
			}

			gamePage.emptyCurrentDraft();
			gamePage.emptyNextDraft();
			// Remake the drafts
			for (Domino domino : game.getCurrentDraft().getIdSortedDominos()) {
				if (!domino.getStatus().equals(DominoStatus.PlacedInKingdom)) {
					int id = domino.getId();
					DominoPanel panel = UIDominos.getUIDominoById(id);
					gamePage.getCurDraftList().add(panel);
				}
			}

			if (game.getNextDraft() != null) {
				for (Domino domino : game.getNextDraft().getIdSortedDominos()) {
					int id = domino.getId();
					DominoPanel panel = UIDominos.getUIDominoById(id);
					gamePage.getNextDraftList().add(panel);
				}
			}

			gamePage.remakeCurDraft();
			gamePage.remakeNextDraft();
			for (int i = 0; i < game.getNumberOfPlayers(); i++) {
				if (game.getNextPlayer().equals(game.getPlayer(i))) {
					gamePage.setCurrentPlayer(i);
				}
			}
			gamePage.refreshData();

		}

	}

	public static void updatePlayerUI() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			User user = game.getPlayer(i).getUser();
			int won = user.getWonGames();
			int player = user.getPlayedGames();
			// setData on ui
		}
	}

	/**
	 * Method updates the UI and changes the player
	 * 
	 * @author Viet
	 */
	public static void nextUI() {
		if (KingdominoApplication.gamePage != null) {
			KingdominoApplication.gamePage.setCurrentPlayer(KingdominoController.initialorder.get(nextorder));
			KingdominoApplication.gamePage.refreshData();
		}
	}

	/**
	 * Method starts the game from the UI
	 */
	public static void startUI() {
		KingdominoGamePage gamePage = KingdominoApplication.gamePage;
		Game game = new Game(48, KingdominoApplication.getKingdomino());
		KingdominoApplication.getKingdomino().setCurrentGame(game);
		addDefaultUsersAndPlayers(game, 4);
		createCastles(game);
		createAllDominoes(game);
		Gameplay gameplay = new Gameplay();
		KingdominoApplication.setGameplay(gameplay);
		gamePage.emptyCurrentDraft();
		gamePage.emptyNextDraft();
		gamePage.remakeNextDraft();
		for (Domino domino : game.getCurrentDraft().getIdSortedDominos()) {
			int id = domino.getId();
			gamePage.getCurDraftList().add(UIDominos.getUIDominoById(id));
		}
		gamePage.remakeCurDraft();
		gamePage.setNumberOfPlayers(game.getNumberOfPlayers());
		gamePage.remakeBoards();
		gamePage.setCurrentPlayer(initialorder.get(0));
		gamePage.add(gamePage.getPanelList().get(gamePage.getCurrentPlayer()));
	}

	/**
	 * This method will be called when player choose to select a domino from the UI
	 * 
	 * @param a int id to indicate the domino id
	 * @param a DominoPanel which will be add a colored border on after successful
	 *          selection
	 * @return void
	 * @author Chang Zhou
	 */
	public static void selectDominoUI(int id, DominoPanel dominoPanel) {
		KingdominoGamePage gamePage = KingdominoApplication.gamePage;

		Draft draft = null;
		if (KingdominoApplication.getGameplay().getGamestatus() == Gamestatus.Initializing) {
			draft = KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft();
		} else {
			draft = KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft();
		}
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		int playerIndex = gamePage.getCurrentPlayer();
		// try to select
		if (selectDomino(id, game.getPlayer(playerIndex), draft)) {
			// unable the selection button
			dominoPanel.setSelection(true);
			dominoPanel.setHandler();
			dominoPanel.getLeftTile().getComponentPopupMenu()
					.remove(dominoPanel.getLeftTile().getComponentPopupMenu().getComponent(2));
			// update the border color
			if (game.getPlayer(playerIndex).getColor() == PlayerColor.Pink) {
				dominoPanel.getLeftTile().setBorder(BorderFactory.createLineBorder(Color.pink, 5));
				dominoPanel.getRightTile().setBorder(BorderFactory.createLineBorder(Color.pink, 5));
			} else if (game.getPlayer(playerIndex).getColor() == PlayerColor.Yellow) {
				dominoPanel.getLeftTile().setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
				dominoPanel.getRightTile().setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
			} else if (game.getPlayer(playerIndex).getColor() == PlayerColor.Blue) {
				dominoPanel.getLeftTile().setBorder(BorderFactory.createLineBorder(Color.blue, 5));
				dominoPanel.getRightTile().setBorder(BorderFactory.createLineBorder(Color.blue, 5));
			} else {
				dominoPanel.getLeftTile().setBorder(BorderFactory.createLineBorder(Color.green, 5));
				dominoPanel.getRightTile().setBorder(BorderFactory.createLineBorder(Color.green, 5));
			}

			// only update the current player to the next player if it is in the
			// initializing state
			if (KingdominoApplication.getGameplay().getGamestatus() == Gamestatus.Initializing) {
				KingdominoApplication.getGameplay().selectedFirst();
			} else {
				KingdominoApplication.getGameplay().selected();
			}
		}
	}

	/**
	 * @author weijiahuang this method discard the domino from the UI
	 * @param id of the domino
	 * @return a boolean indicating if the domino is discarded
	 */
	public static boolean discardDominoUI(int id) {
		Domino domino = getdominoByID(id);
		KingdominoGamePage gamePage = KingdominoApplication.gamePage;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player currentPlayer = game.getPlayer(gamePage.getCurrentPlayer());
		Kingdom kingdom = currentPlayer.getKingdom();
		DominoInKingdom placement = (DominoInKingdom) kingdom.getTerritory(kingdom.getTerritories().size() - 1);
		boolean match = satisfyAll(kingdom, placement);
		for (int i = -4; i <= 4; i++) {
			for (int j = -4; j <= 4; j++) {
				placement.setX(i);
				placement.setY(j);

				placement.setDirection(DirectionKind.Up);
				if (satisfyAll(kingdom, placement)) {
					match = true;
					break;
				}

				placement.setDirection(DirectionKind.Down);
				if (satisfyAll(kingdom, placement)) {
					match = true;
					break;
				}

				placement.setDirection(DirectionKind.Left);
				if (satisfyAll(kingdom, placement)) {
					match = true;
					break;
				}

				placement.setDirection(DirectionKind.Right);
				if (satisfyAll(kingdom, placement)) {
					match = true;
					break;
				}
			}
			if (match)
				break;
		}
		if (match) {
			domino.setStatus(DominoStatus.ErroneouslyPreplaced);
			return false;
		} else {
			domino.setStatus(DominoStatus.Discarded);
			// remove domino from the draft
			for (DominoPanel panel : gamePage.getCurDraftList()) {
				if (panel.getId() == domino.getId()) {
					gamePage.getCurDraftList().remove(panel);
				}
			}
			gamePage.remakeCurDraft();

			KingdominoApplication.getGameplay().discarded();
			return true;
		}

	}

	/**
	 * Method that allows the current player to move the last domino he placed
	 * 
	 * @author Viet
	 * 
	 */
	public static void moveDominoUI() {
		KingdominoGamePage gamePage = KingdominoApplication.gamePage;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Kingdom kingdom = game.getNextPlayer().getKingdom();
		if (kingdom.getTerritories().size() > 1) {
			DominoInKingdom dnk = (DominoInKingdom) kingdom.getTerritory(kingdom.numberOfTerritories() - 1);
			String direction = dnk.getDirection().name().toLowerCase();
			int x = dnk.getX();
			int y = dnk.getY();
			int boardXCoordinates = x + 4;
			int boardYCoordinates = gamePage.gameToUICoordinates(y);
			new DominoSelection(game.getNextPlayer(), dnk.getDomino(), game.getCurrentDraft());
			JButton left = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
					boardYCoordinates, boardXCoordinates);
			JButton right = null;

			switch (direction) {
			case "up":
				right = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
						boardYCoordinates - 1, boardXCoordinates);
				break;
			case "down":
				right = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
						boardYCoordinates + 1, boardXCoordinates);
				break;
			case "left":
				right = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
						boardYCoordinates, boardXCoordinates - 1);
				break;
			case "right":
				right = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
						boardYCoordinates, boardXCoordinates + 1);
				break;
			}
			left.setIcon(new ImageIcon(AssetManager.getPressedButtonImage()));
			right.setIcon(new ImageIcon(AssetManager.getPressedButtonImage()));
			gamePage.resetHandler(left);
			gamePage.resetHandler(right);
			// create new domino panel and remove the icons from the board/reset handler
			// need to call the place method

			DominoPanel panel = UIDominos.getUIDominoById(dnk.getDomino().getId());
			panel.setLocation(245, 500);
			gamePage.add(panel);
			gamePage.repaint();
			dnk.delete();
		}

	}

	/**
	 * Place a king on a domino by making a new DominoSelection object and add it to
	 * the draft given, verifying the existence of the domino with id provided in
	 * the draft, if domino has already been chosen, do nothing.
	 * 
	 * @param dominoID a domino id which the player desired to place his king on
	 * @param player   a player who wants to choose a domino in draft
	 * @param draft    a draft where we choose domino from
	 * @return boolean to represent the success or fail of the process
	 * @author Chang Zhou
	 */
	public static boolean selectDomino(int dominoID, Player player, Draft draft) {
		boolean exist = false;
		boolean hasbeenplaced = false;
		int index = 0;
		int counter = 0;
		for (Domino domino : draft.getIdSortedDominos()) {
			if (dominoID == domino.getId()) {
				exist = true;
				index = counter;
			}
			counter++;
		}
		for (DominoSelection dominoselection : draft.getSelections()) {
			if (dominoID == dominoselection.getDomino().getId()) {
				hasbeenplaced = true;
			}
		}
		if (exist == true && hasbeenplaced == false) {
			DominoSelection selection = new DominoSelection(player, getdominoByID(dominoID, draft.getGame()), draft);
			draft.addSelectionAt(selection, index);
			player.setDominoSelection(selection);
			getdominoByID(dominoID, draft.getGame()).setDominoSelection(selection);
			KingdominoApplication.getGameplay().selected();
			return true;
		}
		return false;
	}

	/**
	 * This method will be called from the state machine action ongoing stage to
	 * reveal Next Draft the next draft will be display at the bottom row
	 * 
	 * @return void
	 * @author Chang Zhou and Viet
	 */
	public static void revealDraftUI() {
		KingdominoGamePage gamePage = KingdominoApplication.gamePage;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		if (game.getNextDraft().getDraftStatus().equals(DraftStatus.FaceUp)) {
			for (DominoPanel panel : gamePage.getNextDraftList()) {
				DominoPanel tempPanel = UIDominos.getUIDominoById(panel.getId());
				panel.getLeftTile().setIcon(tempPanel.getLeftTile().getIcon());
				panel.getRightTile().setIcon(tempPanel.getRightTile().getIcon());
			}
		}
		gamePage.repaint();
	}

	/**
	 * This method will be called from the state machine action ongoing stage to
	 * order Next Draft; the previous next draft will become the current draft and
	 * will be display at the upper row
	 * 
	 * @return void
	 * @author Chang Zhou and Viet
	 */
	public static void orderDraftUI() {
		KingdominoGamePage gamePage = KingdominoApplication.gamePage;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		gamePage.emptyNextDraft();
		for (int i = 0; i < game.getNextDraft().numberOfIdSortedDominos(); i++) {
			int id = game.getNextDraft().getIdSortedDomino(i).getId();
			gamePage.getNextDraftList().add(UIDominos.getUIDominoById(id));
		}
		gamePage.emptyCurrentDraft();
		for (int i = 0; i < game.getCurrentDraft().numberOfIdSortedDominos(); i++) {
			int id = game.getCurrentDraft().getIdSortedDomino(i).getId();
			gamePage.getCurDraftList().add(UIDominos.getUIDominoById(id));
		}
		gamePage.remakeNextDraft();
		gamePage.remakeCurDraft();
		for (DominoPanel panel : gamePage.getCurDraftList()) {
			DominoPanel tempPanel = UIDominos.getUIDominoById(panel.getId());
			panel.getLeftTile().setIcon(tempPanel.getLeftTile().getIcon());
			panel.getRightTile().setIcon(tempPanel.getRightTile().getIcon());
		}
		KingdominoApplication.getGameplay();
		gamePage.repaint();
	}

	/**
	 * Method rotates the last domino the player placed on the board
	 * 
	 * @author Viet
	 * @param string Rotation direction
	 */
	public static void rotateUI(String string) {
		rotate = true;
		boolean rotateStatus = false;
		KingdominoGamePage gamePage = KingdominoApplication.gamePage;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		DominoInKingdom prevdnk = null;
		DominoInKingdom dnk = null;
		String prevDirection = null;
		String curDirection = null;
		Kingdom kingdom = game.getNextPlayer().getKingdom();
		gamePage.setCurrentPlayer(3);

		prevdnk = (DominoInKingdom) kingdom.getTerritory(kingdom.numberOfTerritories() - 1);
		prevDirection = prevdnk.getDirection().name();
		System.out.println("Previous domino" + prevdnk);

		if (string.equalsIgnoreCase("clockwise")) {
			rotateStatus = rotateDomino("clockwise", game.getNextPlayer());
			// Check to see if the domino has rotated
			dnk = (DominoInKingdom) kingdom.getTerritory(kingdom.numberOfTerritories() - 1);
			curDirection = dnk.getDirection().name();
			System.out.println(curDirection);
			System.out.println(game.getNextPlayer().getKingdom().getTerritories());
			if (!curDirection.equalsIgnoreCase(prevDirection)) {
				// find the domino
				int x = prevdnk.getX();
				int y = prevdnk.getY();
				int boardXCoordinates = x + 4;
				int boardYCoordinates = gamePage.gameToUICoordinates(y);
				JButton right = null;

				// New Direction
				switch (curDirection) {
				case "Right":
					right = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
							boardYCoordinates - 1, boardXCoordinates);
					JButton button = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
							boardYCoordinates, boardXCoordinates + 1);
					button.setIcon(right.getIcon());
					button.setTransferHandler(null);
					right.setIcon(new ImageIcon(AssetManager.getPressedButtonImage()));
					// Reset handler
					if (rotateStatus) {
						gamePage.resetHandler(right);
					}
					gamePage.repaint();
					break;
				case "Left":
					right = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
							boardYCoordinates + 1, boardXCoordinates);
					JButton button1 = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
							boardYCoordinates, boardXCoordinates - 1);
					button1.setIcon(right.getIcon());
					button1.setTransferHandler(null);
					right.setIcon(new ImageIcon(AssetManager.getPressedButtonImage()));
					// Reset handler
					if (rotateStatus) {
						gamePage.resetHandler(right);
					}
					gamePage.repaint();
					break;
				case "Down":
					right = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
							boardYCoordinates, boardXCoordinates + 1);
					JButton button2 = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
							boardYCoordinates + 1, boardXCoordinates);
					button2.setIcon(right.getIcon());
					button2.setTransferHandler(null);
					right.setIcon(new ImageIcon(AssetManager.getPressedButtonImage()));
					// Reset handler
					if (rotateStatus) {
						gamePage.resetHandler(right);
					}
					gamePage.repaint();
					break;
				case "Up":
					right = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
							boardYCoordinates, boardXCoordinates - 1);
					JButton button3 = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
							boardYCoordinates - 1, boardXCoordinates);
					button3.setIcon(right.getIcon());
					button3.setTransferHandler(null);
					right.setIcon(new ImageIcon(AssetManager.getPressedButtonImage()));
					// Reset handler
					if (rotateStatus) {
						gamePage.resetHandler(right);
					}
					gamePage.repaint();
					break;
				}

			}

		} else {
			rotateStatus = rotateDomino("counterClockwise", game.getNextPlayer());
			// Check to see if the domino has rotated
			dnk = (DominoInKingdom) kingdom.getTerritory(kingdom.numberOfTerritories() - 1);
			curDirection = dnk.getDirection().name();
			System.out.println(curDirection);
			System.out.println(game.getNextPlayer().getKingdom().getTerritories());
			if (!curDirection.equalsIgnoreCase(prevDirection)) {
				// find the domino
				int x = prevdnk.getX();
				int y = prevdnk.getY();
				int boardXCoordinates = x + 4;
				int boardYCoordinates = gamePage.gameToUICoordinates(y);
				JButton right = null;

				// New Direction
				switch (curDirection) {
				case "Right":
					right = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
							boardYCoordinates + 1, boardXCoordinates);
					JButton button = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
							boardYCoordinates, boardXCoordinates + 1);
					button.setIcon(right.getIcon());
					button.setTransferHandler(null);
					right.setIcon(new ImageIcon(AssetManager.getPressedButtonImage()));
					// Reset handler
					if (rotateStatus) {
						gamePage.resetHandler(right);
					}
					gamePage.repaint();
					break;
				case "Left":
					right = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
							boardYCoordinates - 1, boardXCoordinates);
					JButton button1 = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
							boardYCoordinates, boardXCoordinates - 1);
					button1.setIcon(right.getIcon());
					button1.setTransferHandler(null);
					right.setIcon(new ImageIcon(AssetManager.getPressedButtonImage()));
					// Reset handler
					if (rotateStatus) {
						gamePage.resetHandler(right);
					}
					gamePage.repaint();
					break;
				case "Down":
					right = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
							boardYCoordinates, boardXCoordinates - 1);
					JButton button2 = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
							boardYCoordinates + 1, boardXCoordinates);
					button2.setIcon(right.getIcon());
					button2.setTransferHandler(null);
					right.setIcon(new ImageIcon(AssetManager.getPressedButtonImage()));
					// Reset handler
					if (rotateStatus) {
						gamePage.resetHandler(right);
					}
					gamePage.repaint();
					break;
				case "Up":
					right = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
							boardYCoordinates, boardXCoordinates + 1);
					JButton button3 = gamePage.getButton(gamePage.getPlayerBoards().get(gamePage.getCurrentPlayer()),
							boardYCoordinates - 1, boardXCoordinates);
					button3.setIcon(right.getIcon());
					button3.setTransferHandler(null);
					right.setIcon(new ImageIcon(AssetManager.getPressedButtonImage()));
					// Reset handler
					if (rotateStatus) {
						gamePage.resetHandler(right);
					}
					gamePage.repaint();
					break;
				}

			}
		}
	}

	/**
	 * @author Viet and Riad
	 *  Method places the domino on the board
	 * @param x         coordinate
	 * @param y         coordinate
	 * @param direction to place
	 * @return if the domino has been placed
	 */
	public static boolean placeDominoUI(int x, int y, String direction) {
		// Booleans that state which conditions have been satisfied

		KingdominoApplication.getGameplay().preplaced();
		// accessing ui/model elements
		KingdominoGamePage gamePage = KingdominoApplication.gamePage;
		int playerIndex = gamePage.getCurrentPlayer();
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Kingdom kingdom = game.getPlayer(playerIndex).getKingdom();
		Player player = game.getPlayer(playerIndex);
		Domino domino = player.getDominoSelection().getDomino();
		DominoInKingdom dnk = new DominoInKingdom(x, y, kingdom, domino);
		dnk.setDirection(getDirection(direction));
		gamePage.refreshData();

		castleAdjacency = satisfyCastleAdjacency(dnk, kingdom);
		neighborAdjacency = satisfyNeighborAdjacency(dnk, kingdom);
		noOverlap = noOverlapping(dnk, kingdom);
		satisfyGridSize = satisfyGridSize(dnk, kingdom);

		if (satisfyAll(player.getKingdom(), dnk)) {
			// Remove the current domino from the draft
			for (DominoPanel panel : gamePage.getCurDraftList()) {
				if (panel.getId() == domino.getId()) {
					gamePage.remove(panel);
				}
			}

			gamePage.repaint();
			game.getNextPlayer().getDominoSelection().delete();
			KingdominoApplication.getGameplay().placed();

			return true;
		} else if (!noOverlap) {
			// set label and return false
			gamePage.getMessageLabel().setText("overlap");
			gamePage.getMessageLabel().setVisible(true);
			// Remove the domino from the kingdom
			dnk.delete();
			return false;
		} else if (!neighborAdjacency) {
			gamePage.getMessageLabel().setText(" same type");
			gamePage.getMessageLabel().setVisible(true);
			dnk.delete();
			return false;
		} else if (!satisfyGridSize) {
			gamePage.getMessageLabel().setText("5 tiles wide");
			gamePage.getMessageLabel().setVisible(true);
			dnk.delete();
			return false;
		} else if (!castleAdjacency) {
			// set the label and return false
			gamePage.getMessageLabel().setText(" castle");
			gamePage.getMessageLabel().setVisible(true);
			dnk.delete();
			return false;
		}

		return false;
	}
/**
 * @author Riad
 * Method displays score
 */
	public static void displayScore() {
		Player firstPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0);
		KingdominoGamePage.firstPlayerScore = Integer.toString(calculatePlayerScore(firstPlayer));
		KingdominoGamePage.firstBonusScore = Integer.toString(calculateBonusScore(firstPlayer));

		Player secondPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1);
		KingdominoGamePage.secondPlayerScore = Integer.toString(calculatePlayerScore(secondPlayer));
		KingdominoGamePage.firstBonusScore = Integer.toString(calculateBonusScore(secondPlayer));

		Player thirdPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2);
		KingdominoGamePage.thirdPlayerScore = Integer.toString(calculatePlayerScore(thirdPlayer));
		KingdominoGamePage.firstBonusScore = Integer.toString(calculateBonusScore(thirdPlayer));

		Player fourthPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3);
		KingdominoGamePage.fourthPlayerScore = Integer.toString(calculatePlayerScore(fourthPlayer));
		KingdominoGamePage.firstBonusScore = Integer.toString(calculateBonusScore(fourthPlayer));

	}
/**
 * @author Riad
 * Method displays end score
 */
	public static void displayEndScore() {
		Player firstPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0);
		KingdominoEndScreen.firstPlayerScore = Integer.toString(calculatePlayerScore(firstPlayer));
		KingdominoEndScreen.firstBonusScore = Integer.toString(calculateBonusScore(firstPlayer));

		Player secondPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1);
		KingdominoEndScreen.secondPlayerScore = Integer.toString(calculatePlayerScore(secondPlayer));
		KingdominoEndScreen.firstBonusScore = Integer.toString(calculateBonusScore(secondPlayer));

		Player thirdPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2);
		KingdominoEndScreen.thirdPlayerScore = Integer.toString(calculatePlayerScore(thirdPlayer));
		KingdominoEndScreen.firstBonusScore = Integer.toString(calculateBonusScore(thirdPlayer));

		Player fourthPlayer = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3);
		KingdominoEndScreen.fourthPlayerScore = Integer.toString(calculatePlayerScore(fourthPlayer));
		KingdominoEndScreen.firstBonusScore = Integer.toString(calculateBonusScore(fourthPlayer));

	}
/**
 * @author Riad
 * method displays user Statistics in player menu
 */
	public static void displayUserStats() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		User firstUser = game.getPlayer(0).getUser();
		KingdominoPlayerMenuFour.firstGamesWon = Integer.toString(firstUser.getWonGames());
		KingdominoPlayerMenuFour.firstGamesPlayed = Integer.toString(firstUser.getPlayedGames());

		User secondUser = game.getPlayer(1).getUser();
		KingdominoPlayerMenuFour.secondGamesWon = Integer.toString(secondUser.getWonGames());
		KingdominoPlayerMenuFour.secondGamesPlayed = Integer.toString(secondUser.getPlayedGames());

		User thirdUser = game.getPlayer(2).getUser();
		KingdominoPlayerMenuFour.thirdGamesWon = Integer.toString(thirdUser.getWonGames());
		KingdominoPlayerMenuFour.thirdGamesPlayed = Integer.toString(thirdUser.getPlayedGames());

		User fourthUser = game.getPlayer(3).getUser();
		KingdominoPlayerMenuFour.fourthGamesWon = Integer.toString(fourthUser.getWonGames());
		KingdominoPlayerMenuFour.fourthGamesPlayed = Integer.toString(fourthUser.getPlayedGames());

	}
/**
 * @author Riad
 * method displays ranking
 */
	public static void displayRanking() {
		ArrayList<ArrayList<Player>> rankingArray = new ArrayList<ArrayList<Player>>();
		rankingArray = calculateRanking(KingdominoApplication.getKingdomino().getCurrentGame());
		// KingdominoEndScreen.firstRanking=rankingArray.get(
		for (int i = 0; i < rankingArray.size(); i++) {
			for (int j = 0; j < rankingArray.get(i).size(); j++) {
				if (rankingArray.get(i).get(j) == KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0)) {
					KingdominoEndScreen.firstRankingRes = Integer.toString(i);

				}
			}

		}

		for (int i = 0; i < rankingArray.size(); i++) {
			for (int j = 0; j < rankingArray.get(i).size(); j++) {
				if (rankingArray.get(i).get(j) == KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1)) {
					KingdominoEndScreen.secondRankingRes = Integer.toString(i);

				}
			}
		}

		for (int i = 0; i < rankingArray.size(); i++) {
			for (int j = 0; j < rankingArray.get(i).size(); j++) {
				if (rankingArray.get(i).get(j) == KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2)) {
					KingdominoEndScreen.thirdRankingRes = Integer.toString(i);

				}
			}
		}
		for (int i = 0; i < rankingArray.size(); i++) {
			for (int j = 0; j < rankingArray.get(i).size(); j++) {
				if (rankingArray.get(i).get(j) == KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3)) {
					KingdominoEndScreen.fourthRankingRes = Integer.toString(i);

				}
			}
		}

	}
	/**
	 * @author Riad
	 * @param player
	 * method verifies grid size
	 */
	public static Boolean verifyGridSize(int player) {
		Boolean isValid = true;
		DominoInKingdom currentSelection = KingdominoController.getLastDomino();

		switch (player) {
		case 1:
			isValid = satisfyGridSize(currentSelection,
					KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0).getKingdom());
		case 2:
			isValid = satisfyGridSize(currentSelection,
					KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1).getKingdom());
		case 3:
			isValid = satisfyGridSize(currentSelection,
					KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2).getKingdom());
		case 4:
			isValid = satisfyGridSize(currentSelection,
					KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3).getKingdom());
		default:
			isValid = true;
		}
		return isValid;
	}
	
	/**
	 * @author Riad
	 * @param player
	 * method verifies ovelapping
	 */

	public static Boolean verifyNoOverlapping(int player) {
		Boolean isValid = true;
		DominoInKingdom currentSelection = KingdominoController.getLastDomino();

		switch (player) {
		case 1:
			isValid = noOverlapping(currentSelection,
					KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0).getKingdom());
		case 2:
			isValid = noOverlapping(currentSelection,
					KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1).getKingdom());
		case 3:
			isValid = noOverlapping(currentSelection,
					KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2).getKingdom());
		case 4:
			isValid = noOverlapping(currentSelection,
					KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3).getKingdom());
		default:
			isValid = true;
		}
		return isValid;
	}
	
	/**
	 * @author Riad
	 * @param player
	 * method verify neighbor adjacency 
	 */
	public static Boolean verifyNeighborAdjacency(int player) {
		Boolean isValid = true;
		DominoInKingdom currentSelection = KingdominoController.getLastDomino();

		switch (player) {
		case 1:
			isValid = satisfyNeighborAdjacency(currentSelection,
					KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0).getKingdom());
		case 2:
			isValid = satisfyNeighborAdjacency(currentSelection,
					KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1).getKingdom());
		case 3:
			isValid = satisfyNeighborAdjacency(currentSelection,
					KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2).getKingdom());
		case 4:
			isValid = satisfyNeighborAdjacency(currentSelection,
					KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3).getKingdom());
		default:
			isValid = true;
		}
		return isValid;

	}
	
	/**
	 * @author Riad
	 * @param player
	 * method verifies castle adjacency
	 */

	public static Boolean verifyCastleAdjacency(int player) {
		Boolean isValid = true;
		DominoInKingdom currentSelection = KingdominoController.getLastDomino();

		switch (player) {
		case 1:
			isValid = satisfyCastleAdjacency(currentSelection,
					KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0).getKingdom());
		case 2:
			isValid = satisfyCastleAdjacency(currentSelection,
					KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1).getKingdom());
		case 3:
			isValid = satisfyCastleAdjacency(currentSelection,
					KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2).getKingdom());
		case 4:
			isValid = satisfyCastleAdjacency(currentSelection,
					KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3).getKingdom());
		default:
			isValid = true;
		}
		return isValid;

	}

	/**
	 * Start a game by creating castles for each player, generating the random order
	 * and sort dominoes by order into drafts, initiating the next player and the
	 * first draft.This method is for testing purpose for deliverable2 only without
	 * connecting to the state machine
	 * 
	 * @param game a game whose numberOfPlayers has already been set and Players has
	 *             already been added.
	 * @throws InvalidInputException if the game has more than 4 users or less than
	 *                               2 users
	 * @return void
	 * @author Chang Zhou
	 */
	public static void startANewGame(Game game) throws InvalidInputException {
		if (game.numberOfPlayers() > 4 || game.numberOfPlayers() < 4) {
			throw new InvalidInputException("The game must has a valid number of players before starting the game!");
		}
		createCastles(game);
		createAllDominoes(game);
		int[] order = generateRandomOrder(game.numberOfPlayers());
		shuffleDominos(game, order);
		game.setNextPlayer(game.getPlayer(0));
	}

	public static void startGame(Game game) throws InvalidInputException {
		if (game.numberOfPlayers() > 4 || game.numberOfPlayers() < 4) {
			throw new InvalidInputException("The game must has a valid number of players before starting the game!");
		}
		createCastles(game);
		createAllDominoes(game);
		KingdominoApplication.getGameplay();
	}

	/**
	 * Create Kingdom and Castles for all players in the given game, the Castle is
	 * always placed at 0,0 in the Kingdom
	 * 
	 * @param game a game whose numberOfPlayers has already been set and Players has
	 *             already been added.
	 * @return void
	 * @author Chang Zhou
	 */
	public static void createCastles(Game game) {
		for (Player player : game.getPlayers()) {
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}

	/**
	 * Create player from user and add player to a game
	 * 
	 * @param game  a game which the new player will be added to
	 * @param user  a user which the player will be created from
	 * @param color a color that the user wants to use during the game
	 * @throws InvalidInputException if the game has already 4 users or more than 4
	 *                               users
	 * @return void
	 * @author Chang Zhou
	 */
	public static void addPlayers(Game game, User user, PlayerColor color) throws InvalidInputException {
		if (game.numberOfPlayers() >= 4) {
			throw new InvalidInputException("Number of Players cannot exceed 4!");
		}
		for (Player player : game.getPlayers()) {
			if (player.getColor() == color) {
				throw new InvalidInputException("Color has already been choosed!");
			}
		}

		Player player = new Player(game);
		user.addPlayerInGame(player);
		game.addPlayer(player);
		player.setColor(color);
		player.setUser(user);
	}

	/**
	 * Create a user in the given kingdomino if the name provided is not duplicated
	 * and is consist of number or letters
	 * 
	 * @param username    a user name which the new user would like to use
	 * @param kingdomino  a kingdomino that the user will be added to
	 * @param playedgames a number that represent the game a user has played, always
	 *                    set to zero in real application, can be set to other
	 *                    values for testing objective
	 * @param wongames    a number that represent the game a user has won, always
	 *                    set to zero in real application, can be set to other
	 *                    values for testing objective
	 * @throws InvalidInputException if the user name provided is empty or space or
	 *                               duplicated or not consists of letters and
	 *                               numbers
	 * @return void
	 * @author Chang Zhou
	 */
	public static void createNewUsers(String username, Kingdomino kingdomino, int playedgames, int wongames)
			throws InvalidInputException {
		username = username.trim();
		if (username == null || username.equals("")) {
			throw new InvalidInputException("Name cannot be empty");
		}
		char[] chararray = username.toCharArray();
		for (char c : chararray) {
			if (Character.isLetter(c) == false && Character.isDigit(c) == false) {
				throw new InvalidInputException("Name must consists of letters and numbers");
			}
		}
		for (User user : kingdomino.getUsers()) {
			if (user.getName().toLowerCase().compareTo(username.toLowerCase()) == 0) {
				throw new InvalidInputException("Name already exists!");
			}
		}

		User user = new User(username, KingdominoApplication.getKingdomino());
		user.setPlayedGames(playedgames);
		user.setWonGames(wongames);
		kingdomino.addUser(user);

		return;
	}

	/**
	 * Query the user's game status in history
	 * 
	 * @param username   a user name to see game status
	 * @param kingdomino a kingdomino that the user is in
	 * @throws InvalidInputException if the user name provided is not a user's user
	 *                               name in the given kingdomino
	 * @return an int array, which int[0] stored the player's number of game played
	 *         and int[1] stored the number of game won
	 * @author Chang Zhou
	 */
	public static int[] queryUserGameStatus(String username, Kingdomino kingdomino) throws InvalidInputException {
		int[] status = { 0, 0 };
		for (User user : kingdomino.getUsers()) {
			if (user.getName().toLowerCase().compareTo(username.toLowerCase()) == 0) {
				status[0] = user.getPlayedGames();
				status[1] = user.getWonGames();
				return status;
			}
		}
		throw new InvalidInputException("User not found!");
	}

	/**
	 * Browser all users in the kingdomino given in which the user names will be
	 * sorted in an alphabetical way
	 * 
	 * @param kingdomino a kingdomino for browser users
	 * @return an list of User Objects, sorted by its user name
	 * @author Chang Zhou
	 */
	public static List<User> browserUsers(Kingdomino kingdomino) {
		List<User> userlist = new ArrayList<User>(kingdomino.getUsers());
		Comparator<User> comaperator = new Comparator<User>() {
			@Override
			public int compare(User user1, User user2) {
				return user1.getName().compareTo(user2.getName());
			}
		};
		Collections.sort(userlist, comaperator);
		return userlist;
	}

	/**
	 * Set Game Option for a game
	 * 
	 * @param game   a game for setting its options
	 * @param option an option name in string for the game to be set
	 * @return void
	 * @author Chang Zhou
	 */
	public static void setGameOptions(Game game, String option) {
		for (BonusOption bonusoptionsetalready : game.getSelectedBonusOptions()) {
			if (bonusoptionsetalready.getOptionName().toLowerCase().equals(option)) {
				return;
			}
		}
		BonusOption bonusoption = new BonusOption(option, game.getKingdomino());
		game.addSelectedBonusOption(bonusoption);

	}

	/**
	 * Generate a random order for dominoes in game, if player number is 2, generate
	 * an array of 24 if player number is 2, generate an array of 36 if player
	 * number is 2, generate an array of 48
	 * 
	 * @param numberofplayer the number of players in a game
	 * @return an int array which stores the id for domino to be placed each
	 *         position in game
	 * @author Chang Zhou
	 */
	public static int[] generateRandomOrder(int numberofplayers) {
		List<Integer> order = new ArrayList<Integer>();
		int[] specificorder;
		if (numberofplayers == 2) {
			specificorder = new int[24];
		} else if (numberofplayers == 3) {
			specificorder = new int[36];
		} else {
			specificorder = new int[48];
		}

		for (int i = 1; i <= 48; i++) {
			order.add(i);
		}

		Collections.shuffle(order);

		for (int i = 0; i < numberofplayers * 12; i++) {
			specificorder[i] = order.get(i);
		}
		return specificorder;
	}

	/**
	 * Shuffle dominoes with the random order provided This method need to work with
	 * generateRandomOrder and can be implement as one method latter This method and
	 * the generateRandomOrder are implemented separately now for testing objectives
	 * if player number is 2, generate an 6 Drafts with 4 dominoes in each draft if
	 * player number is 3, generate an 12 Drafts with 3 dominoes in each draft if
	 * player number is 4, generate an 12 Drafts with 4 dominoes in each draft
	 * 
	 * @param game a game to place random order dominoes in drafts
	 * @return void
	 * @author Chang Zhou
	 * @throws InvalidInputException if number of players and number of dominoes
	 *                               used not matched!
	 */

	public static void shuffleDominos(Game game, int[] order) throws InvalidInputException {

		game.setTopDominoInPile(getdominoByID(order[0]));

		if (game.numberOfPlayers() * 12 != order.length) {
			throw new InvalidInputException("Player numbers and number of dominoes used not matched!");
		}
		for (int i = 0; i < order.length - 1; i++) {
			getdominoByID(order[i], game).setNextDomino(getdominoByID(order[i + 1], game));
			getdominoByID(order[i], game).setStatus(DominoStatus.InPile);
		}
		for (int i = order.length - 1; i > 0; i--) {
			getdominoByID(order[i], game).setPrevDomino(getdominoByID(order[i - 1], game));
		}
		Draft draft = new Draft(DraftStatus.FaceDown, game);
		if (order.length == 24 || order.length == 48) {
			for (int i = 0; i < 4; i++) {
				getdominoByID(order[i], game).setStatus(DominoStatus.InCurrentDraft);
				draft.addIdSortedDomino(getdominoByID(order[i], game));
			}
			for (int i = 4; i < 8; i++) {
				getdominoByID(order[i], game).setStatus(DominoStatus.InNextDraft);
			}
			game.setCurrentDraft(draft);
			order(draft);
			reveal(draft);
			game.setTopDominoInPile(getdominoByID(order[4], game));
		} else {
			for (int i = 0; i < 3; i++) {
				getdominoByID(order[i], game).setStatus(DominoStatus.InCurrentDraft);
				draft.addIdSortedDomino(getdominoByID(order[i], game));
			}
			for (int i = 3; i < 6; i++) {
				getdominoByID(order[i], game).setStatus(DominoStatus.InNextDraft);
			}
			game.setCurrentDraft(draft);
			order(draft);
			reveal(draft);
			game.setTopDominoInPile(getdominoByID(order[3], game));
		}
		// KingdominoApplication.getGameplay().draftReady();

		/*
		 * if (order.length == 24 || order.length == 48) { for (int i = 0; i <
		 * order.length; i = i + 4) { Draft draft = new
		 * Draft(Draft.DraftStatus.FaceDown, game); for (int j = i; j < i + 4; j++) {
		 * getdominoByID(order[j], game).setStatus(DominoStatus.InPile);
		 * draft.addIdSortedDomino(getdominoByID(order[j], game)); } } } else { for (int
		 * i = 0; i < order.length; i = i + 3) { Draft draft = new
		 * Draft(Draft.DraftStatus.FaceDown, game); for (int j = i; j < i + 3; j = j +
		 * 1) { getdominoByID(order[j], game).setStatus(DominoStatus.InPile);
		 * draft.addIdSortedDomino(getdominoByID(order[j], game)); } } }
		 */

	}

	/**
	 * Initiate a draft by setting the status of all dominoes in draft of a given
	 * index to InCurrentDraft, set the current draft in the game to the draft with
	 * the given index-1, set the next draft in the game to the draft with the given
	 * index if it is not the last draft.
	 * 
	 * @param game  a game where we want to initiate the draft in it
	 * @param index an index to represent the position of the draft to initiate,
	 *              must between [1,12] when we have three or four users, must
	 *              between [1,6] when we have three users
	 * @return void
	 * @throws InvalidInputException if the index given is out of boundary
	 * @author Chang Zhou
	 */
	public static void initiateDrafts(Game game, int index) throws InvalidInputException {
		if (index > game.numberOfAllDrafts() || index < 1) {
			throw new InvalidInputException("Cannot initaite due to incorrect index!");
		}
		for (Domino domino : game.getAllDraft(index - 1).getIdSortedDominos()) {
			domino.setStatus(DominoStatus.InCurrentDraft);
		}
		game.setCurrentDraft(game.getAllDraft(index - 1));
		if (index < game.numberOfAllDrafts()) {
			game.setNextDraft(game.getAllDraft(index));
		}
	}

	/**
	 * Reveal the draft with a specific index in a game
	 * 
	 * @param game  a game where we want to reveal the draft in it
	 * @param index an index to represent the position of the draft to reveal, must
	 *              between [1,12] when we have three or four users, must between
	 *              [1,6] when we have three users
	 * @return void
	 * @throws InvalidInputException if the index given is out of boundary
	 * @author Chang Zhou
	 */
	public static void revealDraft(Game game, int index) throws InvalidInputException {
		if (index > game.numberOfAllDrafts() || index < 1) {
			throw new InvalidInputException("Cannot reveal draft due to incorrect index!");
		}
		game.getAllDraft(index - 1).setDraftStatus(Draft.DraftStatus.FaceUp);
	}

	/**
	 * Find a player by a player color provided
	 * 
	 * @param color a player color that we want to find
	 * @param game  a game where we want to find color from it
	 * @return a Player with the color desired, null if doesn't exist
	 * @author Chang Zhou
	 */
	public static Player getPlayerByColor(PlayerColor color, Game game) {
		for (Player player : game.getPlayers()) {
			if (player.getColor() == color) {
				return player;
			}
		}
		return null;
	}

	/**
	 * Place a king on a domino by making a new DominoSelection object and add it to
	 * the draft given, verifying the existence of the domino with id provided in
	 * the draft, if domino has already been chosen, do nothing.
	 * 
	 * @param dominoID a domino id which the player desired to place his king on
	 * @param player   a player who wants to choose a domino in draft
	 * @param draft    a draft where we choose domino from
	 * @return void
	 * @author Chang Zhou
	 */
	public static void placeKingOnDomino(int dominoID, Player player, Draft draft) {
		boolean exist = false;
		boolean hasbeenplaced = false;
		int index = 0;
		int counter = 0;
		for (Domino domino : draft.getIdSortedDominos()) {
			if (dominoID == domino.getId()) {
				exist = true;
				index = counter;
			}
			counter++;
		}
		for (DominoSelection dominoselection : draft.getSelections()) {
			if (dominoID == dominoselection.getDomino().getId()) {
				hasbeenplaced = true;
			}
		}
		if (exist == true && hasbeenplaced == false) {
			DominoSelection selection = new DominoSelection(player, getdominoByID(dominoID, draft.getGame()), draft);
			draft.addSelectionAt(selection, index);
			player.setDominoSelection(selection);
			getdominoByID(dominoID, draft.getGame()).setDominoSelection(selection);
			// KingdominoApplication.getGameplay().selected();
		}
	}

	/**
	 * Verify and place selected domino in a player's kingdom
	 * 
	 * @param dik    that contains the domino and the position information in it
	 * @param player a player who wants to place a domino in kingdom
	 * @return void
	 */
	public static void placeSelectedDomino(DominoInKingdom dik, Player player) {
		if (dik.getDomino().getStatus() == DominoStatus.CorrectlyPreplaced) {
			player.getKingdom().addTerritory(dik);
			dik.setKingdom(player.getKingdom());
			dik.getDomino().setStatus(DominoStatus.PlacedInKingdom);
		}
	}

	/**
	 * Save a text file to a specified location
	 * 
	 * @param textName Filename that the user's save location
	 * @return a string indicating if the process was successful
	 * @author Viet
	 */
	public static String saveGame(String textName) {
		// Retrieve the user and the instance they want to save
		Game savedGame = KingdominoApplication.getKingdomino().getCurrentGame();

		if (savedGame == null) {
			return null;
		}

		int counter = 1;
		int draftSize = 4;
		if (savedGame.getNumberOfPlayers() == 3) {
			draftSize = 3;
		}

		// All the elements to be saved

		StringJoiner claimedJoiner = new StringJoiner(",");
		StringJoiner unclaimedJoiner = new StringJoiner(",");
		StringJoiner pileJoiner = new StringJoiner(",");
		StringJoiner colorJoiner = new StringJoiner(",");
		StringJoiner userJoiner = new StringJoiner(",");

		// Retrieve the claimed dominoes
		for (int i = 0; i < savedGame.getNumberOfPlayers(); i++) {
			Player curPlayer = savedGame.getPlayer(i);
			if (curPlayer.hasDominoSelection()) {
				claimedJoiner.add("" + curPlayer.getDominoSelection().getDomino().getId());
			}
		}

		// Retrieve the unclaimed dominoes
		Draft draft = savedGame.getNextDraft();

		for (Domino domino : draft.getIdSortedDominos()) {
			if (!domino.hasDominoSelection()) {
				unclaimedJoiner.add("" + domino.getId());
			}
		}

		File file = new File(textName);

		// Send to text file
		BufferedWriter writer = null;
		if (file.isFile() && !overwrite) {
			return "File already exists";
		}
		try {
			writer = new BufferedWriter(new FileWriter(textName));

			for (Player player : savedGame.getPlayers()) {
				userJoiner.add(player.getUser().getName());
			}
			writer.write("User: " + userJoiner.toString() + "\n");

			// Save the dominoPile
			Domino curDomino = savedGame.getTopDominoInPile();
			while (curDomino != null) {
				pileJoiner.add("" + curDomino.getId());
				curDomino = curDomino.getNextDomino();
			}
			writer.write("Pile: " + pileJoiner.toString() + "\n");

			// Find a players claimed dominoes
			writer.write("C: " + claimedJoiner.toString() + "\n");
			writer.write("U: " + unclaimedJoiner.toString() + "\n");

			// Go through each player in the game and retrieve their attributes
			for (Player player : savedGame.getPlayers()) {

				// Save the color
				if (player.getColor() != null) {
					colorJoiner.add(player.getColor().name());
				}
				StringJoiner tileJoiner = new StringJoiner(",");
				// Go through a player's properties to find his dominos
				// Check that a player has dominos in his kingdom
				if (player.hasKingdom()) {
					for (int i = 0; i < player.getKingdom().numberOfTerritories(); i++) {
						// Check if the territory is a DominoInKingdom
						if (player.getKingdom().getTerritory(i) instanceof DominoInKingdom) {
							DominoInKingdom dominoInKingdom = (DominoInKingdom) player.getKingdom().getTerritory(i);
							Domino playerDomino = dominoInKingdom.getDomino();

							String direction = "";
							// Translate direction into 1 letter
							switch (dominoInKingdom.getDirection().name()) {
							case "Up":
								direction = "U";
								break;
							case "Down":
								direction = "D";
								break;
							case "Left":
								direction = "L";
								break;
							case "Right":
								direction = "R";
								break;

							default:
								break;
							}

							// Separate each players tiles with commas
							tileJoiner.add((playerDomino.getId() + "@(" + dominoInKingdom.getX() + ","
									+ dominoInKingdom.getY() + "," + direction + ")"));
						}
					}
				}
				writer.write("P" + counter + ": " + tileJoiner.toString() + "\n");
				counter++;
			}

			writer.write("Colors: " + colorJoiner.toString() + "\n");
			writer.write("Gamestatus: " + KingdominoApplication.getGameplay().getGamestatusFullName());
			writer.close();
		} catch (IOException ex) {

		}
		return "File saved sucessfully";

	}

	/**
	 * Method that replicates the UI's ability to enable a user to overwrite a file
	 * 
	 * @param filename Filename that the user's save location
	 * @author Viet
	 */
	public static void overwriteFile(String filename) {
		overwrite = true;
		saveGame(filename);
	}

	/**
	 * Method that loads a game based on a file
	 * 
	 * @param fileName a string indicating the file's location
	 * @return a string indicating if the process was successful
	 * @author Viet
	 */
	public static String loadGame(String fileName) {
		Scanner in = null;
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		System.out.println(fileName);
		// Save all the dominoes in use
		ArrayList<Domino> allDomino = new ArrayList<>();
		ArrayList<Domino> p1List = new ArrayList<>();
		ArrayList<Domino> p2List = new ArrayList<>();
		ArrayList<Domino> p3List = new ArrayList<>();
		ArrayList<Domino> p4List = new ArrayList<>();

		// Saved the claimed dominoes
		ArrayList<String> claimedDominoes = new ArrayList<>();
		ArrayList<String> unclaimedDominoes = new ArrayList<>();
		ArrayList<String> p1Tiles = new ArrayList<>();
		ArrayList<String> p2Tiles = new ArrayList<>();
		ArrayList<String> p3Tiles = new ArrayList<>();
		ArrayList<String> p4Tiles = new ArrayList<>();
		String[] colorString = null;
		String[] userString = null;
		String[] pileDominoes = null;
		String[] gameState = null;

		// Open file
		try {
			in = new Scanner(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Read File until the end
		while (in.hasNextLine()) {

			String curString = in.nextLine();

			// Remove all whiteSpaces
			curString = curString.replaceAll("[\\s()]", "");

			// Split the string at specific chars
			String delimiter = "[@(),:.]";
			String[] lineString = curString.split(delimiter);

			switch (lineString[0]) {
			case "Pile":
				pileDominoes = lineString;
				break;
			case "C":
				Collections.addAll(claimedDominoes, lineString);
				break;
			case "U":
				Collections.addAll(unclaimedDominoes, lineString);
				break;
			case "P1":
				Collections.addAll(p1Tiles, lineString);
				break;
			case "P2":
				Collections.addAll(p2Tiles, lineString);
				break;
			case "P3":
				Collections.addAll(p3Tiles, lineString);
				break;
			case "P4":
				Collections.addAll(p4Tiles, lineString);
				break;
			case "Colors":
				colorString = lineString;
				break;
			case "User":
				userString = lineString;
				break;
			case "Gamestatus":
				gameState = lineString;
				break;
			}

		}
		in.close();

		// Set up the game with the correct player count
		int playerCount;
		int dominoCount;
		int draftSize;

		if (!p1Tiles.isEmpty() && !p2Tiles.isEmpty() && !p3Tiles.isEmpty() && !p4Tiles.isEmpty()) {
			playerCount = 4;
			dominoCount = 48;
			draftSize = 4;
		} else if (!p1Tiles.isEmpty() && !p2Tiles.isEmpty() && !p3Tiles.isEmpty()) {
			playerCount = 3;
			dominoCount = 36;
			draftSize = 3;
		} else {
			playerCount = 2;
			dominoCount = 24;
			draftSize = 4;
		}

		Game game = new Game(dominoCount, kingdomino);
		kingdomino.setCurrentGame(game);
		game.setNumberOfPlayers(playerCount);
		createAllDominoes(game);

		if (userString != null) {
			for (int i = 0; i < playerCount; i++) {
				Player player = new Player(game);
				User user = new User(userString[i + 1], kingdomino);
				player.setUser(user);
			}
		} else {
			addDefaultUsersAndPlayers(game, playerCount);
		}
		for (Player player : game.getPlayers()) {
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}

		// Place dominoes corresponding to a player
		// Returns invalid if the domino placement is wrong
		for (int i = 0; i < playerCount; i++) {

			// If the list is returned null that means one of the dominoes were not placed
			// correctly
			// Load game will then return a string stating the game is invalid
			switch (i) {
			case 0:
				p1List = createPlayerDominoes(game.getPlayer(i), p1Tiles, claimedDominoes);
				break;
			case 1:
				p2List = createPlayerDominoes(game.getPlayer(i), p2Tiles, claimedDominoes);
				break;
			case 2:
				p3List = createPlayerDominoes(game.getPlayer(i), p3Tiles, claimedDominoes);
				break;
			case 3:
				p4List = createPlayerDominoes(game.getPlayer(i), p4Tiles, claimedDominoes);
				break;
			}
		}
		if (p4List == null || p3List == null || p2List == null || p1List == null) {
			return "Invalid";
		}
		allDomino.addAll(p1List);
		allDomino.addAll(p2List);
		allDomino.addAll(p3List);
		allDomino.addAll(p4List);
		// Find the largest list
		int x = (p1List.size() >= p2List.size()) ? p1List.size() : p2List.size();
		x = (x >= p3List.size()) ? x : p3List.size();
		x = (x >= p4List.size()) ? x : p4List.size();

		// Create all the drafts based on the player tiles
		for (int i = 0; i < x; i++) {
			Draft draft = new Draft(DraftStatus.FaceUp, game);
			// Check if the list is smaller than the others
			// If the current list is smaller than the biggest one it cannot add a domino to
			// the draft
			if ((p1List.size() < x) && i == x - 1) {

			} else {
				draft.addIdSortedDomino(p1List.get(i));
			}

			if ((p2List.size() < x) && i == x - 1) {

			} else {
				draft.addIdSortedDomino(p2List.get(i));
			}

			if ((p3List.size() < x) && i == x - 1) {

			} else {
				draft.addIdSortedDomino(p3List.get(i));
			}

			if (p4List.size() < x && i == x - 1) {

			} else {
				draft.addIdSortedDomino(p4List.get(i));
			}

			// Sort and reveal the previous drafts
			order(draft);
			reveal(draft);
		}

		// if unclaimed is == to the player count => next draft
		unclaimedDominoes.remove(0);
		claimedDominoes.remove(0);

		/*
		 * If there are the same amount of unclaimed dominoes as dominoes in a draft
		 * then these dominoes must be in the next draft hence we create the current
		 * draft with the claimed dominoes and the next draft with the unclaimed ones
		 */
		if (unclaimedDominoes.size() == draftSize && unclaimedDominoes != null) {
			Draft curDraft = new Draft(DraftStatus.FaceUp, game);
			Draft nextDraft = new Draft(DraftStatus.FaceDown, game);
			int prevDominoId = 0;
			int dominoId;
			int pTurn = 0;

			// Cycle through the unclaimedList
			for (int i = 0; i < unclaimedDominoes.size(); i++) {

				// If the size of the unclaimed list is the same as the number of players
				// then it implies that all these dominoes will be in the next draft
				dominoId = Integer.parseInt(unclaimedDominoes.get(i)) - 1;
				game.getAllDomino(dominoId).setStatus(DominoStatus.InNextDraft);
				nextDraft.addIdSortedDomino((game.getAllDomino(dominoId)));
				allDomino.add(game.getAllDomino(dominoId));

				// The current draft is based on the dominoes in the claimed pile
				if (i < claimedDominoes.size()) {
					dominoId = Integer.parseInt(claimedDominoes.get(i)) - 1;
					game.getAllDomino(dominoId).setStatus(DominoStatus.InCurrentDraft);
					curDraft.addIdSortedDomino((game.getAllDomino(dominoId)));
					allDomino.add(game.getAllDomino(dominoId));
				}
				if (prevDominoId != 0) {
					pTurn = (dominoId < prevDominoId) ? i : i - 1;
				}
				prevDominoId = dominoId;

				// Set the selection
				new DominoSelection(game.getPlayer(4 - i), game.getAllDomino(dominoId), curDraft);
			}

			game.setNextPlayer(game.getPlayer(pTurn));

			// Order the drafts so that they respect the rules
			order(curDraft);
			order(nextDraft);

			game.setCurrentDraft(curDraft);
			game.setNextDraft(nextDraft);
		}
		// If the unclaimed pile is not the same size as the player count
		// it implies that the unclaimed dominoes are in the current draft
		// The claimed dominoes will be in the next
		else if (unclaimedDominoes != null) {
			Draft currentDraft = game.getAllDraft(game.numberOfAllDrafts() - 1);

			// Set the status of the dominoes in the current draft
			for (Domino domino : currentDraft.getIdSortedDominos()) {
				if (!domino.getStatus().equals(DominoStatus.PlacedInKingdom)) {
					domino.setStatus(DominoStatus.InCurrentDraft);
				}
			}

			Draft nextDraft = new Draft(DraftStatus.FaceUp, game);

			for (int i = 0; i < (claimedDominoes.size()); i++) {
				int dominoId = Integer.parseInt(claimedDominoes.get(i)) - 1;
				Domino curDomino = game.getAllDomino(dominoId);
				allDomino.add(curDomino);
				// Subtract the size of both list to determine how many dominoes belong in the
				// next draft

				if (i >= claimedDominoes.size() - unclaimedDominoes.size()) {
					nextDraft.addIdSortedDomino(curDomino);
					// Set selections
					curDomino.setStatus(DominoStatus.InNextDraft);
					new DominoSelection(game.getPlayer(4 - i), curDomino, nextDraft);
				}
				// The rest of the dominoes should be in the current draft
				else {
					currentDraft.addIdSortedDomino(curDomino);
					curDomino.setStatus(DominoStatus.InCurrentDraft);
					new DominoSelection(game.getPlayer(i), curDomino, currentDraft);
				}
			}

			// Set the next player
			game.setNextPlayer(game.getPlayer(claimedDominoes.size() - unclaimedDominoes.size()));
			// add unclaimed dominoes to the next draft
			for (int i = 0; i < unclaimedDominoes.size(); i++) {
				int dominoId = Integer.parseInt(unclaimedDominoes.get(i)) - 1;
				Domino curDomino = (game.getAllDomino(dominoId));
				curDomino.setStatus(DominoStatus.InNextDraft);
				nextDraft.addIdSortedDomino(curDomino);
				allDomino.add(curDomino);
			}

			// Sort the drafts
			order(currentDraft);
			order(nextDraft);

			game.setCurrentDraft(currentDraft);
			game.setNextDraft(nextDraft);
		}

		// If player was not set compare the unclaimed tiles and the player with the
		// lowest number will be next

		// Set the player color
		if (colorString != null) {
			for (int i = 1; i < colorString.length; i++) {
				game.getPlayer(i - 1).setColor(playerColor(colorString[i]));
			}
		}
		if (gameState != null) {
			KingdominoApplication.getGameplay().setGamestatus(gameState[2]);
		}
		if (pileDominoes != null) {
			game.setTopDominoInPile(game.getAllDomino(Integer.parseInt(pileDominoes[1]) - 1));
			Domino curDomino = game.getTopDominoInPile();
			Domino prevDomino = null;
			int dominoId = 0;

			// Iterate through the pile and set the links
			for (int i = 2; i < pileDominoes.length; i++) {
				dominoId = Integer.parseInt(pileDominoes[i]) - 1;
				curDomino.setPrevDomino(prevDomino);
				if (prevDomino != null) {
					prevDomino.setNextDomino(curDomino);
				}
				prevDomino = curDomino;
				curDomino = game.getAllDomino(dominoId);

			}
		}

		// Set the game state
		return "Valid File";
	}

	/**
	 * Helper method for load game, retrieves dominoes of the text file and creates
	 * dominos in kingdom
	 * 
	 * @param player    Player the load game method is creating the dominoes for
	 * @param arrayList arrayList with that players dominoes
	 * @param claimed   arraylist of claimed dominoes
	 * @return a Player's dominoes
	 * @author Viet
	 */
	private static ArrayList<Domino> createPlayerDominoes(Player player, ArrayList<String> arrayList,
			ArrayList<String> claimed) {
		arrayList.remove(0);
		ArrayList<String> claimedCopy = new ArrayList<>(claimed);
		claimedCopy.remove(0);
		ArrayList<Domino> dominoList = new ArrayList<>();
		int dominoId;
		int x;
		int y;
		DominoInKingdom domino1 = null;
		new Castle(0, 0, player.getKingdom(), player);

		// TODO Verify the validity of the tiles placements when putting them on the
		// Verify that there is no overlap between the previous and the next domino
		for (int i = 0; i <= arrayList.size(); i++) {
			if (i % 4 == 0 & i != 0) {
				dominoId = Integer.parseInt(arrayList.get(i - 4)) - 1;
				x = Integer.parseInt(arrayList.get(i - 3));
				y = Integer.parseInt(arrayList.get(i - 2));
				Domino domino = player.getGame().getAllDomino(dominoId);

				// Verify if out of bounds
				if (x > 4 || x < -4 || y < -4 || y > 4) {
					return null;
				}
				// Check if the domino already exists
				for (String s : claimedCopy) {
					if (domino.getId() == Integer.decode(s)) {
						return null;
					}
				}

				DominoInKingdom kingdomDomino = new DominoInKingdom(x, y, player.getKingdom(), domino);
				kingdomDomino.setDirection(dominoDirection(arrayList.get(i - 1)));
				domino1 = kingdomDomino;
				boolean castle = satisfyCastleAdjacency(domino1, player.getKingdom());

				boolean same = satisfyNeighborAdjacency(domino1, player.getKingdom());
				if (!same && !castle) {
					return null;
				}
				dominoList.add(domino);

				// Verify overlap
				for (KingdomTerritory territories : player.getKingdom().getTerritories()) {
					if (territories instanceof DominoInKingdom) {
						DominoInKingdom dominoKingdom = (DominoInKingdom) territories;
						if (!doesDominoOverlap(dominoKingdom, kingdomDomino.getX(), kingdomDomino.getY(),
								kingdomDomino.getDirection())) {

							if (!dominoKingdom.equals(kingdomDomino)) {
								return null;
							}
						}
					}
				}
				kingdomDomino.getDomino().setStatus(DominoStatus.PlacedInKingdom);
			}
		}

		// Check overlap

		return dominoList;

	}

	/**
	 * Method that create the next draft by taking the domino at the top of the pile
	 * and those following it
	 * 
	 * @author Viet
	 */
	public static void createNextDraft() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game curGame = kingdomino.getCurrentGame();
		int numOfDominoes = 0;
		Domino curDomino = curGame.getTopDominoInPile();

		Draft nextDraft;

		// Get the number of players
		numOfDominoes = curGame.getNumberOfPlayers();

		if (numOfDominoes == 2) {
			numOfDominoes = 4;
		}

		// Create the new draft
		nextDraft = new Draft(DraftStatus.FaceDown, curGame);

		// Draft the dominoes and set the top one
		if (curDomino != null) {
			for (int i = 0; i < numOfDominoes; i++) {
				nextDraft.addIdSortedDomino(curDomino);
				curDomino.setStatus(DominoStatus.InNextDraft);
				curDomino = curDomino.getNextDomino();

				// Set the top domino on the last iteration
				if (i == numOfDominoes - 1) {
					curGame.setTopDominoInPile(curDomino);
				}
			}
		}
		// If the pile is empty then there is no next draft
		else {
			nextDraft = null;
		}

		// Set the current draft and the next one
		if (curGame.getNextDraft() != null) {
			curGame.setCurrentDraft(curGame.getNextDraft());
			for (int i = 0; i < curGame.getCurrentDraft().numberOfIdSortedDominos(); i++) {
				curGame.getCurrentDraft().getIdSortedDomino(i).setStatus(DominoStatus.InCurrentDraft);
			}
		}
		curGame.setNextDraft(nextDraft);

	}

	/**
	 * Orders a specific draft
	 * 
	 * @param draft Draft to be ordered
	 * @author Viet
	 */
	public static void order(Draft draft) {
		List<Domino> dominoList = new ArrayList<Domino>(draft.getIdSortedDominos());

		// Compare the dominoes in the list to make sure they are sorted
		Collections.sort(dominoList, new Comparator<Domino>() {
			@Override
			public int compare(Domino o1, Domino o2) {
				return Integer.compare(o1.getId(), o2.getId());
			}

		});

		// Add the sorted dominos back into the draft
		for (int i = 0; i < dominoList.size(); i++) {
			// Add the sorted dominos to the list
			draft.addOrMoveIdSortedDominoAt(dominoList.get(i), i);
		}
		draft.setDraftStatus(DraftStatus.Sorted);
		// KingdominoApplication.getGameplay().draftReady();
	}

	/**
	 * If the draft is sorted and it is the next draft then reveal it
	 * 
	 * @param draft draft to be revealed
	 * @author Viet
	 */
	public static void reveal(Draft draft) {
		// If the next draft is sorted then reveal it
		if (draft.getDraftStatus().equals(DraftStatus.Sorted)) {
			draft.setDraftStatus(DraftStatus.FaceUp);
		}
	}

	// Helper methods
	public static void generateForShuffle(int numberofplayers, List<Domino> list) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Collections.shuffle(list);

		kingdomino.getCurrentGame().setTopDominoInPile(list.get(0));

		Domino curDomino = kingdomino.getCurrentGame().getTopDominoInPile();

		Domino prevDomino = curDomino;

		for (int i = 1; i < list.size(); i++) {
			curDomino = list.get(i);
			prevDomino.setNextDomino(curDomino);
			curDomino.setPrevDomino(prevDomino);
		}
	}

	/**
	 * Helper method that checks if the current game is still ongoing
	 * 
	 * @return the game status
	 * @author Viet
	 */
	public static boolean gameInProgress() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		if (game.hasTopDominoInPile() && game.getAllDrafts().size() != 12) {
			return true;
		}

		return false;
	}

	/**
	 * Helper method for load game
	 * 
	 * @param string Take a string direction
	 * @return the direction kind of that string
	 * @author Viet
	 */
	public static DirectionKind dominoDirection(String string) {
		// Set the direction of the domino
		switch (string) {
		case "U":
			return DirectionKind.Up;
		case "D":
			return DirectionKind.Down;
		case "L":
			return DirectionKind.Left;
		case "R":
			return DirectionKind.Right;
		default:
			return null;
		}
	}

	/**
	 * Helper method for load game
	 * 
	 * @param string Color string
	 * @return Player color associated to that string
	 * @author Viet
	 */
	private static PlayerColor playerColor(String string) {
		switch (string) {
		case "Blue":
			return PlayerColor.Blue;
		case "Green":
			return PlayerColor.Green;
		case "Pink":
			return PlayerColor.Pink;
		case "Yellow":
			return PlayerColor.Yellow;
		default:
			return null;
		}
	}

	/**
	 * @Author : Riad El Mahmoudy this method takes in a full kingdom and returns
	 *         the properties in it stores the tiles in a 2D array as well as the
	 *         dominos, the number of crowns and the terrain type places each array
	 *         element according to the convention
	 * 
	 *         it iterates through these array 25 times as 25 is the maximum number
	 *         of properties a player could have while iterating each iteration gets
	 *         one property and adds it to the property list
	 * 
	 *         checks if the tile is detached if none of its neighbors is part of a
	 *         property
	 * 
	 *         checks if the the current tile has the same terraintype as the
	 *         property we're currently working on
	 * 
	 *         when adding it updates the number of crowns, size, dominoes and type
	 * 
	 * @return myProperties: list of properties
	 * @param kingdom : the kingdom where we want to identify properties
	 */

	public static List<Property> identifyProperty(Kingdom kingdom) {
		TerrainType[][] territoryArray = new TerrainType[11][11];
		int[][] numberOfCrowns = new int[11][11];
		DominoInKingdom[][] dominoArray = new DominoInKingdom[11][11];
		Property property = new Property(kingdom);
		List<Property> myProperties = new ArrayList<Property>();

		Boolean[][] tileArray = new Boolean[11][11];
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				tileArray[i][j] = false;
			}

		}
		for (KingdomTerritory territory : kingdom.getTerritories()) {
			if (territory instanceof DominoInKingdom) {
				int x = territory.getX();
				int y = territory.getY();

				// problem
				TerrainType leftType = ((DominoInKingdom) territory).getDomino().getLeftTile();
				TerrainType rightType = ((DominoInKingdom) territory).getDomino().getRightTile();
				territoryArray[x + 5][y + 5] = leftType;
				dominoArray[x + 5][y + 5] = ((DominoInKingdom) territory);
				numberOfCrowns[x + 5][y + 5] = ((DominoInKingdom) territory).getDomino().getLeftCrown();

				if (((DominoInKingdom) territory).getDirection() == DirectionKind.Up) {
					dominoArray[x + 5][y + 6] = ((DominoInKingdom) territory);
					territoryArray[x + 5][y + 6] = rightType;
					numberOfCrowns[x + 5][y + 6] = ((DominoInKingdom) territory).getDomino().getRightCrown();

				} else if (((DominoInKingdom) territory).getDirection() == DirectionKind.Down) {
					dominoArray[x + 5][y + 4] = ((DominoInKingdom) territory);
					territoryArray[x + 5][y + 4] = rightType;
					numberOfCrowns[x + 5][y + 4] = ((DominoInKingdom) territory).getDomino().getRightCrown();

				} else if (((DominoInKingdom) territory).getDirection() == DirectionKind.Left) {
					dominoArray[x + 4][y + 5] = ((DominoInKingdom) territory);
					territoryArray[x + 4][y + 5] = rightType;
					numberOfCrowns[x + 4][y + 5] = ((DominoInKingdom) territory).getDomino().getRightCrown();

				} else if (((DominoInKingdom) territory).getDirection() == DirectionKind.Right) {
					dominoArray[x + 6][y + 5] = ((DominoInKingdom) territory);
					territoryArray[x + 6][y + 5] = rightType;
					numberOfCrowns[x + 6][y + 5] = ((DominoInKingdom) territory).getDomino().getRightCrown();

				}

			}

		}

		List<List<DominoInKingdom>> pList = new ArrayList<List<DominoInKingdom>>();
		Boolean checkEveryDomino;
		int sum = 0;
		for (int a = 0; a < pList.size(); a++) {
			sum += pList.get(a).size();
		}

		int h = 0;
		while (h < 25) {

			List<DominoInKingdom> dominoList = new ArrayList<DominoInKingdom>();
			Property tProp = new Property(kingdom);
			int numCrowns = 0;
			TerrainType propType = null;
			int pSize = 0;

			Boolean[][] proArray = new Boolean[11][11];
			for (int i = 0; i < 11; i++) {
				for (int j = 0; j < 11; j++) {
					proArray[i][j] = false;
				}
			}

			for (int k = 0; k < 9; k++) {
				for (int j = 0; j < 9; j++) {

					// System.out.println("new loop");
					Boolean doesPropertyExist = false;

					if (dominoArray[k][j] == null) {
						continue;
					}
					Boolean isInProperty = false; // check if its in our property
					for (int b = 0; b < pList.size(); b++) {
						for (DominoInKingdom aDomino : pList.get(b)) {

							if (aDomino != null && aDomino.equals(dominoArray[k][j])) {
								isInProperty = true;
							}
						}
					}

					Boolean isN1InProperty = false; // check if its in our property
					for (int b = 0; b < pList.size(); b++) {
						for (DominoInKingdom aDomino : pList.get(b)) {

							if (aDomino != null && aDomino.equals(dominoArray[k + 1][j])) {
								isN1InProperty = true;
							}
						}
					}

					Boolean isN2InProperty = false; // check if its in our property
					for (int b = 0; b < pList.size(); b++) {
						for (DominoInKingdom aDomino : pList.get(b)) {

							if (aDomino != null && aDomino.equals(dominoArray[k][j + 1])) {
								isN2InProperty = true;
							}
						}
					}

					Boolean isN3InProperty = false; // check if its in our property
					for (int b = 0; b < pList.size(); b++) {
						for (DominoInKingdom aDomino : pList.get(b)) {

							if (aDomino != null && aDomino.equals(dominoArray[k - 1][j])) {
								isN3InProperty = true;
							}
						}
					}

					Boolean isN4InProperty = false; // check if its in our property
					for (int b = 0; b < pList.size(); b++) {
						for (DominoInKingdom aDomino : pList.get(b)) {

							if (aDomino != null && aDomino.equals(dominoArray[k][j - 1])) {
								isN4InProperty = true;
							}
						}
					}

					for (int u = 0; u < pList.size(); u++) {
						for (DominoInKingdom aDomino : pList.get(u)) {
							if (aDomino.equals(dominoArray[k][j])) {

								doesPropertyExist = true;
								break;
							}

						}

					}

					if (dominoList.size() == 0 && !tileArray[k][j]) {

						propType = territoryArray[k][j];

						dominoList.add(dominoArray[k][j]);
						tileArray[k][j] = true;
						proArray[k][j] = true;
						tProp.addIncludedDomino(dominoArray[k][j].getDomino());

						numCrowns += numberOfCrowns[k][j];
						pSize++;
						tProp.setSize(pSize);
						tProp.setPropertyType(territoryArray[k][j]);

					} else if (dominoList.size() != 0) {

						if (territoryArray[k][j] != propType) {

							continue;
						}

						if (!proArray[k + 1][j] && !proArray[k][j + 1] && !proArray[k - 1][j] && !proArray[k][j - 1]) {

							continue;
						}

					}

					if (territoryArray[k][j].equals(territoryArray[k + 1][j]) && !tileArray[k + 1][j]
							&& !proArray[k + 1][j]) {
						dominoList.add(dominoArray[k + 1][j]);
						tileArray[k + 1][j] = true;
						proArray[k + 1][j] = true;
						tProp.addIncludedDomino(dominoArray[k + 1][j].getDomino());

						numCrowns += numberOfCrowns[k + 1][j];
						pSize++;
						tProp.setSize(pSize);
						tProp.setPropertyType(territoryArray[k + 1][j]);
					}
					if (territoryArray[k][j].equals(territoryArray[k - 1][j]) && !tileArray[k - 1][j]
							&& !proArray[k - 1][j]) {
						dominoList.add(dominoArray[k - 1][j]);
						tileArray[k - 1][j] = true;
						proArray[k - 1][j] = true;
						tProp.addIncludedDomino(dominoArray[k - 1][j].getDomino());

						numCrowns += numberOfCrowns[k - 1][j];
						pSize++;
						tProp.setSize(pSize);
						tProp.setPropertyType(territoryArray[k - 1][j]);
					}
					if (territoryArray[k][j].equals(territoryArray[k][j + 1]) && !tileArray[k][j + 1]
							&& !proArray[k][j + 1]) {
						dominoList.add(dominoArray[k][j + 1]);
						tileArray[k][j + 1] = true;
						proArray[k][j + 1] = true;
						tProp.addIncludedDomino(dominoArray[k][j + 1].getDomino());

						numCrowns += numberOfCrowns[k][j + 1];
						pSize++;
						tProp.setSize(pSize);
						tProp.setPropertyType(territoryArray[k][j + 1]);
					}
					if (territoryArray[k][j].equals(territoryArray[k][j - 1]) && !tileArray[k][j - 1]
							&& !proArray[k][j - 1]) {
						dominoList.add(dominoArray[k][j - 1]);
						tileArray[k][j - 1] = true;
						proArray[k][j - 1] = true;
						if (!territoryArray[k][j].equals(territoryArray[k][j - 1])) {
							proArray[k][j - 1] = false;
						}
						tProp.addIncludedDomino(dominoArray[k][j - 1].getDomino());

						numCrowns += numberOfCrowns[k][j - 1];
						pSize++;
						tProp.setSize(pSize);
						tProp.setPropertyType(territoryArray[k][j - 1]);
					}

				}

			}
			pList.add(dominoList);

			myProperties.add(tProp);
			tProp.setCrowns(numCrowns);

			h++;
		}
		List<Property> myPropertiesTrim = new ArrayList<Property>();
		for (Property aProperty : myProperties) {
			if (aProperty.getIncludedDominos().size() != 0) {
				myPropertiesTrim.add(aProperty);
			}
		}

		return myPropertiesTrim;

	}

	/**
	 * @author Riad El Mahmoudy returns the size of the property list of our kingdom
	 * @param kingdom
	 * @return
	 */
	public static int calculateNumberOfProperties(Kingdom kingdom) {

		return kingdom.getProperties().size();

	}

	/**
	 * @author Riad El Mahmoudy This method returns a list of property type for each
	 *         property in a kingdom
	 * @param kingdom
	 * @return list of property types of our properties
	 */
	public static List<String> calculatePropertyType(Kingdom kingdom) {
		List<String> typeList = new ArrayList<String>();
		// if(kingdom.getProperties().size()!=0 ) {
		for (Property aProperty : identifyProperty(kingdom)) {
			if (aProperty.getPropertyType() != null) {
				typeList.add(aProperty.getPropertyType().toString());
			}
		}
		// }
		System.out.println(typeList.size());
		return typeList;
	}

	/**
	 * @author Riad El Mahmoudy This method returns a list of crowns for each
	 *         property in a kingdom
	 * @param kingdom
	 * @return
	 */
	public static List<Integer> calculateNumberOfCrowns(Kingdom kingdom) {
		List<Integer> crownList = new ArrayList<Integer>();
		for (Property aProperty : identifyProperty(kingdom)) {
			// if(aProperty.getCrowns()!=0) {
			crownList.add((Integer) aProperty.getCrowns());
			// }
		}
		return crownList;
	}

	/**
	 * @author Riad El Mahmoudy This method returns a list of the sizes for each
	 *         property in a kingdom
	 * @param kingdom
	 * @return
	 */
	public static List<Integer> calculatePropertySize(Kingdom kingdom) {
		List<Integer> sizeList = new ArrayList<Integer>();
		for (Property aProperty : identifyProperty(kingdom)) {
			if (aProperty.getSize() != 0) {
				sizeList.add((Integer) aProperty.getSize());
			}
		}
		return sizeList;
	}

	/**
	 * This method returns the current player in the game.
	 * 
	 * @param color
	 * @param game
	 * @return Player
	 */
	public static Player curPlayer(PlayerColor color, Game game) {

		Player curPlayer = null;
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			for (PlayerColor p : PlayerColor.values()) {
				if (game.getPlayer(i).getColor() == color) {
					curPlayer = game.getPlayer(i);

				}
			}
		}
		return curPlayer;
	}

	public static void removeKingFromDomino(Player player, Domino domino, Game game) {

		if (player.getDominoSelection().getDomino() == domino) {
			DominoInKingdom domKing = new DominoInKingdom(0, 0, player.getKingdom(), domino);
			player.getKingdom().addTerritory(domKing);
			domino.setStatus(DominoStatus.ErroneouslyPreplaced);
		} else {
			throw new IllegalArgumentException("Player Is Not on This Domino");
		}

	}

	/**
	 * This methods moves a Domino from its current position
	 * 
	 * @param player
	 * @param dir
	 */
	public static void moveDomino(String dir) {
		boolean isIncorrect = false;
		if (!dir.equals("down") && !dir.equals("up") && !dir.equals("right") && !dir.equals("left")) {
			throw new IllegalArgumentException("Illegal argument");
		}

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		// List<KingdomTerritory> kindgomt =
		// game.getNextPlayer().getKingdom().getTerritories();

		boolean domHasK = false;
		for (KingdomTerritory kingdomTerritory : game.getNextPlayer().getKingdom().getTerritories()) {
			if (kingdomTerritory instanceof DominoInKingdom) {
				domHasK = true;
			}

		}
		if (domHasK == false) {
			throw new RuntimeException("No Domino in Kingdom");
		}

		DominoInKingdom dik = (DominoInKingdom) game.getNextPlayer().getKingdom()
				.getTerritory(game.getNextPlayer().getKingdom().getTerritories().size() - 1);
		DominoInKingdom initialdk = dik;
		String mov = dik.getDirection().name().toLowerCase();
		boolean right = false;
		boolean left = false;
		int xInit = dik.getX();
		int yInit = dik.getY();

		switch (dir) {
		case "down":
			left = dik.setY(dik.getY() - 1);

			break;
		case "up":
			left = dik.setY(dik.getY() + 1);

			break;
		case "right":
			left = dik.setX(dik.getX() + 1);

			break;
		case "left":
			left = dik.setX(dik.getX() - 1);
			break;
		}

		switch (mov) {
		case "down":
			right = dik.getY() - 1 > -5;

			break;
		case "up":
			right = dik.getY() + 1 < 5;

			break;
		case "right":
			right = dik.getX() + 1 < 5;

			break;
		case "left":
			right = dik.getX() - 1 > -5;
			break;

		}

		if (right == false || left == false) {
			dik.setY(yInit);
			dik.setX(xInit);
			// throw new IllegalArgumentException("Domino can't be placed");
		}

		if (right == true && left == true) {
			// verify if overlapping
			boolean overlap = true;

			for (KingdomTerritory kingdomTerritory : game.getNextPlayer().getKingdom().getTerritories()) {
				if (kingdomTerritory instanceof DominoInKingdom) {
					DominoInKingdom dk = (DominoInKingdom) kingdomTerritory;
					if (!dk.equals(dik)) {
						overlap = doesDominoOverlap(dik, dk.getX(), dk.getY(), dk.getDirection());
						if (!overlap) {
							break;
						}
					}
				}

			}

			if (overlap == false) {
				dik.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
				isIncorrect = true;
				// throw new IllegalArgumentException("Domino can't be placed due to
				// overlaping");
			}
			boolean grid = doesDominoStayInGrid(dik, dik.getY() + 1, dik.getY() - 1, dik.getX() - 1, dik.getX() + 1);

			if (grid == false) {
				dik.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
				isIncorrect = true;
				// throw new IllegalArgumentException("Domino can't be placed due to grid
				// size");

			}
			boolean dominoAdj = satisfyNeighborAdjacency(dik, game.getNextPlayer().getKingdom());
			boolean castleAdj = satisfyCastleAdjacency(dik, game.getNextPlayer().getKingdom());
			if (dominoAdj == false && castleAdj == false) {
				dik.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
				isIncorrect = true;
				// throw new IllegalArgumentException("Domino can't be placed due to
				// adjacency");

			}
			if (!isIncorrect) {
				dik.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
			}
		}

	}

	/**
	 * This method rotates a domino according to a direction.
	 * 
	 * @param direction
	 * @param player
	 */
	public static boolean rotateDomino(String direction, Player player) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();

		Game game = kingdomino.getCurrentGame();
		Domino curDomino = player.getDominoSelection().getDomino();
		DirectionKind directionKind = null;
		DominoInKingdom kingdomDomino = null;

		for (KingdomTerritory territory : player.getKingdom().getTerritories()) {
			if (territory instanceof DominoInKingdom) {
				DominoInKingdom dominoKingdom = (DominoInKingdom) territory;

				// Match the current domino with the domino in kingdom
				if (dominoKingdom.getDomino().getId() == curDomino.getId()) {
					directionKind = dominoKingdom.getDirection();
					kingdomDomino = dominoKingdom;
				}

			}
		}
		if (rotate) {
			kingdomDomino = getLastDomino(game.getNextPlayer());
			directionKind = kingdomDomino.getDirection();
		}

		// Check what direction they want to rotate the domino in
		if (direction.equalsIgnoreCase("clockwise")) {
			switch (directionKind) {
			case Right:
				// Check if changing the direction of this domino affects anything
				// Check if the domino is still in the grid
				// Check if it overlaps
				// Iterate through each domino in kingdom to check for interference
				return isRotationValid(kingdomDomino, DirectionKind.Down, player);
			case Left:
				return isRotationValid(kingdomDomino, DirectionKind.Up, player);
			case Down:
				return isRotationValid(kingdomDomino, DirectionKind.Left, player);
			case Up:
				return isRotationValid(kingdomDomino, DirectionKind.Right, player);
			}
		} else {
			switch (directionKind) {
			case Right:
				// Check if changing the direction of this domino affects anything
				// Check if the domino is still in the grid
				// Check if it overlaps
				return isRotationValid(kingdomDomino, DirectionKind.Up, player);
			case Left:
				return isRotationValid(kingdomDomino, DirectionKind.Down, player);
			case Down:
				return isRotationValid(kingdomDomino, DirectionKind.Right, player);
			case Up:
				return isRotationValid(kingdomDomino, DirectionKind.Left, player);
			}
		}
		return false;

	}

	/**
	 * This method verifies if a rotation is valid.
	 * 
	 * @param dk
	 * @param dir
	 * @param player
	 * @return
	 */

	private static boolean isRotationValid(DominoInKingdom dk, DirectionKind dir, Player player) {
		boolean inside = true;
		boolean noOverlap = true;
		boolean insideDir = true;
		DirectionKind initialDir = dk.getDirection();
		inside = satisfyGridSize(dk, player.getKingdom());

		// Check out of bounds with new direction
		dk.setDirection(dir);
		insideDir = satisfyGridSize(dk, player.getKingdom());
		if (!inside && !dk.getDomino().getStatus().equals(DominoStatus.InCurrentDraft)) {
			dk.setDirection(initialDir);
			return false;
		}

		noOverlap = noOverlapping(dk, player.getKingdom());
		if (noOverlap && insideDir && (dk.getDomino().getStatus().equals(DominoStatus.InCurrentDraft) || rotate)) {
			dk.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
			return true;
		} else if (dk.getDomino().getStatus().equals(DominoStatus.InCurrentDraft)) {
			dk.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
			return false;
		}
		if (rotate) {
			dk.setDirection(initialDir);
		}

		return false;
	}

	/**
	 * This method verifies is the grid size is satisfied.
	 * 
	 * @param territory
	 * @param kingdom
	 * @return
	 */

	public static boolean satisfyGridSize(DominoInKingdom territory, Kingdom kingdom) {
		int lowerBound = 0;
		int upperBound = 0;
		int leftBound = 0;
		int rightBound = 0;
		for (KingdomTerritory aterritory : kingdom.getTerritories()) {
			if (aterritory instanceof DominoInKingdom) {
				if (aterritory.getX() >= rightBound) {
					rightBound = aterritory.getX();
					if (((DominoInKingdom) aterritory).getDirection() == DirectionKind.Right) {
						rightBound = aterritory.getX() + 1;
					}

				}
				if (aterritory.getX() <= leftBound) {

					leftBound = aterritory.getX();
					if (((DominoInKingdom) aterritory).getDirection() == DirectionKind.Left) {
						leftBound = aterritory.getX() - 1;
					}

				}
				if (aterritory.getY() >= upperBound) {
					upperBound = aterritory.getY();
					if (((DominoInKingdom) aterritory).getDirection() == DirectionKind.Up) {
						upperBound = aterritory.getY() + 1;
					}

				}
				if (aterritory.getY() <= lowerBound) {

					lowerBound = aterritory.getY();
					if (((DominoInKingdom) aterritory).getDirection() == DirectionKind.Down) {
						lowerBound = aterritory.getY() - 1;
					}

				}
			}
		}
		if (territory == null) {
			if ((upperBound - lowerBound) > 4) {
				return false;
			}
			if ((rightBound - leftBound) > 4) {
				return false;
			}
		} else {
			return KingdominoController.doesDominoStayInGrid(territory, upperBound, lowerBound, leftBound, rightBound);

		}
		return false;
	}

	/**
	 * @Author Riad El Mahmoudy
	 * 
	 *         this method checks if the domino overlaps with another domino,
	 *         meaning if it's legal to place it or not by checking every case where
	 *         it would not be valid to place a domino relatively to an already
	 *         placed one
	 * 
	 * @param territory
	 * @param X         xposition of the domino
	 * @param Y         yposition of the domino
	 * @param dir       direction of the domino
	 * 
	 * @return isValid false if not valid, true if valid
	 */
	public static Boolean doesDominoOverlap(DominoInKingdom territory, int X, int Y, DirectionKind dir) {
		Boolean isValid = true;
		int x = territory.getX();
		int y = territory.getY();
		DirectionKind territoryDir = territory.getDirection();

		if ((x == X) && (y == Y)) {
			isValid = false;
		}

		if ((x == X + 1) && (y == Y && territoryDir == DirectionKind.Left)) {
			isValid = false;
		}

		if ((x == X - 1 && y == Y && territoryDir == DirectionKind.Right)) {
			isValid = false;
		}

		if ((y == Y + 1 && x == X && territoryDir == DirectionKind.Down)) {
			isValid = false;
		}

		if ((y == Y - 1 && x == X && territoryDir == DirectionKind.Up)) {
			isValid = false;
		}

		if (dir == DirectionKind.Right && (x == X + 1) && (y == Y)) {
			isValid = false;
		}

		if (dir == DirectionKind.Left && (x == X - 1) && (y == Y)) {
			isValid = false;
		}
		if (dir == DirectionKind.Up && (y == Y + 1) && (x == X)) {
			isValid = false;
		}
		if (dir == DirectionKind.Down && (y == Y - 1) && (x == X)) {
			isValid = false;
		}

		if ((dir == DirectionKind.Right && (y == Y + 1 && x == X + 1 && territoryDir == DirectionKind.Down))
				|| (dir == DirectionKind.Right && (y == Y && x == X + 2 && territoryDir == DirectionKind.Left))
				|| (dir == DirectionKind.Right && (y == Y - 1 && x == X + 1 && territoryDir == DirectionKind.Up))

		) {
			isValid = false;
		}

		if ((dir == DirectionKind.Up && (y == Y + 2 && x == X && territoryDir == DirectionKind.Down))
				|| (dir == DirectionKind.Up && (y == Y + 1 && x == X + 1 && territoryDir == DirectionKind.Left))
				|| (dir == DirectionKind.Up && (y == Y + 1 && x == X - 1 && territoryDir == DirectionKind.Right))

		) {
			isValid = false;
		}

		if ((dir == DirectionKind.Left && (y == Y && x == X - 2 && territoryDir == DirectionKind.Right))
				|| (dir == DirectionKind.Left && (y == Y + 1 && x == X - 1 && territoryDir == DirectionKind.Down))
				|| (dir == DirectionKind.Left && (y == Y - 1 && x == X - 1 && territoryDir == DirectionKind.Up))

		) {
			isValid = false;
		}

		if ((dir == DirectionKind.Down && (y == Y - 2 && x == X && territoryDir == DirectionKind.Up))
				|| (dir == DirectionKind.Down && (y == Y - 1 && x == X + 1 && territoryDir == DirectionKind.Left))
				|| (dir == DirectionKind.Down && (y == Y - 1 && x == X - 1 && territoryDir == DirectionKind.Right))

		) {
			isValid = false;
		}

		// }
		// }

		return isValid;
	}

	/**
	 * @author Riad El Mahmoudy This method checks if the domino grid (aka kingdom)
	 *         is always a 5x5 grid and that if we want to place a domino outside of
	 *         that bound we can't. We do this by checking the bounds relatively to
	 *         the bounds dynamically defined
	 * @param territory  :the territory we want to place bounds of that kingdom :
	 * @param upperBound
	 * @param lowerBound
	 * @param leftBound
	 * @param rightBound
	 * @return isValid false if not valid, true if valid
	 */

	public static Boolean doesDominoStayInGrid(DominoInKingdom territory, int upperBound, int lowerBound, int leftBound,
			int rightBound) {
		Boolean isValid = true;

		DirectionKind territoryDir = (territory).getDirection();

		if (upperBound - lowerBound > 4) {
			isValid = false;
		}
		if (rightBound - leftBound > 4) {
			isValid = false;
		}

		if (territory.getY() > upperBound) {
			isValid = false;
		} else if (territory.getY() < lowerBound) {
			isValid = false;
		} else if (territory.getX() > rightBound) {
			isValid = false;
		} else if (territory.getX() < leftBound) {
			isValid = false;
		}
		if ((territoryDir.equals(DirectionKind.Up)) && territory.getY() >= upperBound) {
			isValid = false;
		} else if ((territoryDir.equals(DirectionKind.Down)) && territory.getY() <= lowerBound) {
			isValid = false;
		} else if ((territoryDir.equals(DirectionKind.Right)) && territory.getX() >= rightBound) {
			isValid = false;
		} else if ((territoryDir.equals(DirectionKind.Left)) && territory.getX() <= leftBound) {
			isValid = false;
		}

		return isValid;
	}

	/**
	 * check if the domino placement satisfies castle adjacency
	 * 
	 * @author weijiahuang
	 * @param the dominoInKingdom that has just been added
	 * @param the kingdom that needed to be checked
	 * @return a boolean that shows if castle adjacency is satisfied
	 */
	public static Boolean satisfyCastleAdjacency(DominoInKingdom lastDomino, Kingdom kingdom) {
		///////////// verify the tentative placement is near the castle///////
		if (!((Math.abs(lastDomino.getX()) <= 2) && (Math.abs(lastDomino.getY()) <= 2)))
			return false;
		if (lastDomino.getX() == 0 && lastDomino.getY() == 1 && !(lastDomino.getDirection() == DirectionKind.Down)) {
			return true;
		}
		if (lastDomino.getX() == 0 && lastDomino.getY() == -1 && !(lastDomino.getDirection() == DirectionKind.Up)) {
			return true;
		}
		if (lastDomino.getY() == 0 && lastDomino.getX() == 1 && !(lastDomino.getDirection() == DirectionKind.Left)) {
			return true;
		}
		if (lastDomino.getY() == 0 && lastDomino.getX() == -1 && !(lastDomino.getDirection() == DirectionKind.Right)) {
			return true;
		}
		if (lastDomino.getX() == -1 && lastDomino.getY() == 1 && ((lastDomino.getDirection() == DirectionKind.Right)
				|| (lastDomino.getDirection() == DirectionKind.Down))) {
			return true;
		}
		if (lastDomino.getX() == -1 && lastDomino.getY() == -1 && ((lastDomino.getDirection() == DirectionKind.Right)
				|| (lastDomino.getDirection() == DirectionKind.Up))) {
			return true;
		}
		if (lastDomino.getX() == 1 && lastDomino.getY() == 1 && ((lastDomino.getDirection() == DirectionKind.Left)
				|| (lastDomino.getDirection() == DirectionKind.Down))) {
			return true;
		}
		if (lastDomino.getX() == 1 && lastDomino.getY() == -1 && ((lastDomino.getDirection() == DirectionKind.Left)
				|| (lastDomino.getDirection() == DirectionKind.Up))) {
			return true;
		}
		if (lastDomino.getX() == -2 && lastDomino.getY() == 0 && lastDomino.getDirection() == DirectionKind.Right) {
			return true;
		}
		if (lastDomino.getX() == 2 && lastDomino.getY() == 0 && lastDomino.getDirection() == DirectionKind.Left) {
			return true;
		}
		if (lastDomino.getY() == -2 && lastDomino.getX() == 0 && lastDomino.getDirection() == DirectionKind.Up) {
			return true;
		}
		if (lastDomino.getY() == 2 && lastDomino.getX() == 0 && lastDomino.getDirection() == DirectionKind.Down) {
			return true;
		}
		return false;

	}

	/**
	 * check if the domino placement satisfies neighbor adjacency
	 * 
	 * @author weijiahuang
	 * @param the dominoInKingdom that has just been added
	 * @param the kingdom that needed to be checked
	 * @return a boolean that shows if neioghbor adjacency is satisfied
	 */
	public static Boolean satisfyNeighborAdjacency(DominoInKingdom lastDomino, Kingdom kingdom) {
		ArrayList<DominoInKingdom> existingDomino = new ArrayList<DominoInKingdom>();
		for (int i = 0; i <= kingdom.getTerritories().size() - 2; i++) {
			if (kingdom.getTerritory(i) instanceof DominoInKingdom) {
				existingDomino.add((DominoInKingdom) kingdom.getTerritory(i));
			}
		}
		ArrayList<int[]> occupiedCells = new ArrayList<int[]>();// existing cells occupied
		ArrayList<int[]> dominoCells = new ArrayList<int[]>(); // cells occupied by the new domino
		int[] dominoOccupied = { lastDomino.getX(), lastDomino.getY() };
		dominoCells.add(dominoOccupied);
		switch (lastDomino.getDirection()) {
		case Up: {
			int[] cell = { lastDomino.getX(), lastDomino.getY() + 1 };
			dominoCells.add(cell);
			break;
		}
		case Down: {
			int[] cell = { lastDomino.getX(), lastDomino.getY() - 1 };
			dominoCells.add(cell);
			break;
		}
		case Left: {
			int[] cell = { lastDomino.getX() - 1, lastDomino.getY() };
			dominoCells.add(cell);
			break;
		}
		case Right: {
			int[] cell = { lastDomino.getX() + 1, lastDomino.getY() };
			dominoCells.add(cell);

		}
		}
		for (DominoInKingdom kt : existingDomino) {
			int[] arr = { kt.getX(), kt.getY() };
			occupiedCells.add(arr);
			switch (kt.getDirection()) {
			case Up: {
				int[] arr2 = { kt.getX(), kt.getY() + 1 };
				occupiedCells.add(arr2);
				break;
			}
			case Down: {
				int[] arr2 = { kt.getX(), kt.getY() - 1 };
				occupiedCells.add(arr2);
				break;
			}
			case Left: {
				int[] arr2 = { kt.getX() - 1, kt.getY() };
				occupiedCells.add(arr2);
				break;
			}
			case Right: {
				int[] arr2 = { kt.getX() + 1, kt.getY() };
				occupiedCells.add(arr2);
				break;
			}
			}
		}
		for (int[] last : dominoCells) {
			for (int[] existing : occupiedCells) {
				if (Math.abs(existing[0] - last[0]) + Math.abs(existing[1] - last[1]) == 1) {// adjacent cells need to
																								// be checked
					TerrainType existingType = getCellLandscape(kingdom, existing);
					TerrainType newType;
					if (lastDomino.getX() == last[0] && lastDomino.getY() == last[1])
						newType = lastDomino.getDomino().getLeftTile();
					else
						newType = lastDomino.getDomino().getRightTile();
					if (existingType == newType) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * get the terraintype from a particular cell, helper method for
	 * satisfyNeighborAdjacency
	 * 
	 * @author weijiahuang
	 * @param the kingdom that needs to be checked
	 * @param the cell which is described by an array that needs to be checked
	 *            terrain type
	 * @return the terrain tuype of that particular cell
	 */
	private static TerrainType getCellLandscape(Kingdom kingdom, int[] cell) {
		ArrayList<DominoInKingdom> existingDomino = new ArrayList<DominoInKingdom>();
		for (int i = 0; i <= kingdom.getTerritories().size() - 2; i++) {
			if (kingdom.getTerritory(i) instanceof DominoInKingdom) {
				existingDomino.add((DominoInKingdom) kingdom.getTerritory(i));
			}
		}
		for (DominoInKingdom existing : existingDomino) {
			if (existing.getX() == cell[0] && existing.getY() == cell[1])
				return existing.getDomino().getLeftTile();
			if (existing.getX() == cell[0] && existing.getY() + 1 == cell[1]
					&& existing.getDirection() == DirectionKind.Up)
				return existing.getDomino().getRightTile();
			if (existing.getX() == cell[0] && existing.getY() - 1 == cell[1]
					&& existing.getDirection() == DirectionKind.Down)
				return existing.getDomino().getRightTile();
			if (existing.getX() + 1 == cell[0] && existing.getY() == cell[1]
					&& existing.getDirection() == DirectionKind.Right)
				return existing.getDomino().getRightTile();
			if (existing.getX() - 1 == cell[0] && existing.getY() == cell[1]
					&& existing.getDirection() == DirectionKind.Left)
				return existing.getDomino().getRightTile();
		}
		return null;
	}

	/**
	 * return the last domino which is the domino that has just been added
	 * 
	 * @author weijiahuang
	 * @return the DominoInKingdom that has just been added
	 */
	public static DominoInKingdom getLastDomino() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Kingdom kingdom = game.getNextPlayer().getKingdom();
		DominoInKingdom lastDomino = (DominoInKingdom) kingdom.getTerritory(kingdom.getTerritories().size() - 1);
		return lastDomino;
	}

	public static DominoInKingdom getLastDomino(Player player) {
		Kingdom kingdom = player.getKingdom();
		DominoInKingdom lastDomino = (DominoInKingdom) kingdom.getTerritory(kingdom.getTerritories().size() - 1);
		return lastDomino;
	}

	/**
	 * iterate through all the territories and check if a placement has overlapping
	 * 
	 * @author weijiahuang
	 * @param the     new domino placement
	 * @param kingdom
	 * @return a boolean describing if there is any overlapping with a given domino
	 *         placement
	 */
	public static Boolean noOverlapping(DominoInKingdom domino, Kingdom kingdom) {
		boolean isValid = true;
		if (domino.getX() == 0 && domino.getY() == 0) {
			return false;
		}
		// Kingdom kingdom = game.getPlayer(0).getKingdom();
		ArrayList<DominoInKingdom> existingDomino = new ArrayList<DominoInKingdom>();
		for (int i = 1; i <= kingdom.getTerritories().size() - 2; i++) {
			existingDomino.add((DominoInKingdom) kingdom.getTerritory(i));

		}
		for (DominoInKingdom territory : existingDomino) {
			isValid = KingdominoController.doesDominoOverlap(territory, domino.getX(), domino.getY(),
					domino.getDirection());
			if (isValid == false) {
				return false;
			}

		}
		return true;
	}

	/**
	 * check if a domino placement satisfies all the requirements
	 * 
	 * @author weijiahuang
	 * @param the kingdom that needs to be checked
	 * @param the domino placement that needs to be checked
	 * @return a boolean which describes if the placement is legal
	 */
	public static Boolean satisfyAll(Kingdom kingdom, DominoInKingdom domino) {
		Boolean satisfyCastle = satisfyCastleAdjacency(domino, kingdom);
		Boolean satisfyNeighbor = satisfyNeighborAdjacency(domino, kingdom);
		Boolean noOverlapping = noOverlapping(domino, kingdom);
		Boolean satisfySize = satisfyGridSize(domino, kingdom);
		if ((satisfyCastle || satisfyNeighbor) && noOverlapping && satisfySize)
			return true;
		else
			return false;
	}

	/**
	 * set the status of a domino based on the verification
	 * 
	 * @author weijiahuang
	 * @param the domino placement
	 * @param the kingdom that needs to be checked
	 */
	public static void setStatus(DominoInKingdom domino, Kingdom kingdom) {
		boolean satisfy = satisfyAll(kingdom, domino);
		if (satisfy) {
			domino.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
		} else {
			domino.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
			kingdom.removeTerritory(domino);
		}
	}

	/**
	 * set the status of a domino when a player attempts to discard a domino
	 * 
	 * @author weijiahuang
	 * @param the domino that the player wants to discard
	 */
	public static boolean discardDomino(Domino domino) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Kingdom kingdom = game.getNextPlayer().getKingdom();
		DominoInKingdom placement = KingdominoController.getLastDomino();
		boolean match = satisfyAll(kingdom, placement);
		int x = placement.getX();
		int y = placement.getY();

		for (int i = -4; i <= 4; i++) {
			for (int j = -4; j <= 4; j++) {
				placement.setX(i);
				placement.setY(j);

				placement.setDirection(DirectionKind.Up);
				if (satisfyAll(kingdom, placement)) {
					match = true;
					break;
				}

				placement.setDirection(DirectionKind.Down);
				if (satisfyAll(kingdom, placement)) {
					match = true;
					break;
				}

				placement.setDirection(DirectionKind.Left);
				if (satisfyAll(kingdom, placement)) {
					match = true;
					break;
				}

				placement.setDirection(DirectionKind.Right);
				if (satisfyAll(kingdom, placement)) {
					match = true;
					break;
				}
			}
			if (match)
				break;
		}
		if (match) {
			domino.setStatus(DominoStatus.ErroneouslyPreplaced);
			placement.setX(x);
			placement.setY(y);
			return false;
		} else {
			domino.setStatus(DominoStatus.Discarded);
			KingdominoApplication.getGameplay().discarded();
			return true;
		}

	}

	public static boolean addDefaultUsersAndPlayers(Game game, int numberofplayers) {
		if (numberofplayers == 4) {
			String[] userNames = { "User1", "User2", "User3", "User4" };
			for (int i = 0; i < userNames.length; i++) {
				User user = game.getKingdomino().addUser(userNames[i]);
				Player player = new Player(game);
				player.setUser(user);
				player.setColor(PlayerColor.values()[i]);
			}
		} else if (numberofplayers == 3) {
			String[] userNames = { "User1", "User2", "User3" };
			for (int i = 0; i < userNames.length; i++) {
				User user = game.getKingdomino().addUser(userNames[i]);
				Player player = new Player(game);
				player.setUser(user);
				player.setColor(PlayerColor.values()[i]);
			}
		} else if (numberofplayers == 2) {
			String[] userNames = { "User1", "User2" };
			for (int i = 0; i < userNames.length; i++) {
				User user = game.getKingdomino().addUser(userNames[i]);
				Player player = new Player(game);
				player.setUser(user);
				player.setColor(PlayerColor.values()[i]);
			}
		} else {
			return false;
		}
		return true;
	}

	public static void createAllDominoes(Game game) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
			String line = "";
			String delimiters = "[:\\+()]";
			while ((line = br.readLine()) != null) {
				String[] dominoString = line.split(delimiters);
				int dominoId = Integer.decode(dominoString[0]);
				TerrainType leftTerrain = getTerrainType(dominoString[1]);
				TerrainType rightTerrain = getTerrainType(dominoString[2]);
				int numCrown = 0;
				if (dominoString.length > 3) {
					numCrown = Integer.decode(dominoString[3]);
				}
				new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException(
					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
		}
	}

	public static Domino getdominoByID(int id, Game game) {
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}

	public static TerrainType getTerrainType(String terrain) {
		switch (terrain) {
		case "W":
			return TerrainType.WheatField;
		case "F":
			return TerrainType.Forest;
		case "M":
			return TerrainType.Mountain;
		case "G":
			return TerrainType.Grass;
		case "S":
			return TerrainType.Swamp;
		case "L":
			return TerrainType.Lake;
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
	}

	public static DirectionKind getDirection(String dir) {
		switch (dir) {
		case "up":
			return DirectionKind.Up;
		case "down":
			return DirectionKind.Down;
		case "left":
			return DirectionKind.Left;
		case "right":
			return DirectionKind.Right;
		default:
			throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
		}
	}

	private DominoStatus getDominoStatus(String status) {
		switch (status) {
		case "inPile":
			return DominoStatus.InPile;
		case "excluded":
			return DominoStatus.Excluded;
		case "inCurrentDraft":
			return DominoStatus.InCurrentDraft;
		case "inNextDraft":
			return DominoStatus.InNextDraft;
		case "erroneouslyPreplaced":
			return DominoStatus.ErroneouslyPreplaced;
		case "correctlyPreplaced":
			return DominoStatus.CorrectlyPreplaced;
		case "placedInKingdom":
			return DominoStatus.PlacedInKingdom;
		case "discarded":
			return DominoStatus.Discarded;
		default:
			throw new java.lang.IllegalArgumentException("Invalid domino status: " + status);
		}

	}

	// Overload method
	public static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}

	/**
	 * start and populate a game this method is copied from the code given in the
	 * discard domino definition
	 */
	public static void startAndPopulateGame() {
		// Intialize empty game
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		KingdominoController.addDefaultUsersAndPlayers(game);
		KingdominoController.createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}

	private static void addDefaultUsersAndPlayers(Game game) {
		String[] userNames = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < userNames.length; i++) {
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}

	/**
	 * similar to getTerrainType but different words are used to match the statement
	 * in features
	 * 
	 * @author weijiahuang
	 * @param the string that describes terrain
	 * @return the terrain type
	 */
	public static TerrainType getTerrainTypeFeatures(String terrain) {
		switch (terrain) {
		case "wheat":
			return TerrainType.WheatField;
		case "forest":
			return TerrainType.Forest;
		case "mountain":
			return TerrainType.Mountain;
		case "grass":
			return TerrainType.Grass;
		case "swamp":
			return TerrainType.Swamp;
		case "lake":
			return TerrainType.Lake;
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
	}

	/**
	 * get all the dominos with a given terrain type
	 * 
	 * @author weijiahuang
	 * @param terrainType
	 * @return an arraylist of dominos of that given terrain type
	 */
	public static ArrayList<Domino> getDominoByTerrainType(String terrainType) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		ArrayList<Domino> dominosWithTerrainType = new ArrayList<Domino>();
		for (Domino d : game.getAllDominos()) {
			if (d.getLeftTile().equals(getTerrainTypeFeatures(terrainType))
					|| (d.getRightTile().equals(getTerrainTypeFeatures(terrainType)))) {
				dominosWithTerrainType.add(d);
			}
		}

		return dominosWithTerrainType;
	}

	/**
	 * Gets the property score of a player's kingdom
	 * 
	 * @param player -- Takes one player as input
	 * @return -- the property score of a player's kingdom
	 * 
	 * 
	 * @author Oliver Stappas
	 * 
	 */
	public static int calculatePropertyAttributes(Kingdom kingdom) {
		int score = 0;
		for (Property property : kingdom.getProperties()) {

			score += property.getCrowns() * property.getSize();
		}
		return score;
	}

	/**
	 * Gets the total number of crowns in a player's kingdom
	 * 
	 * @param player -- Takes one player as input
	 * @return -- the total number of crowns in that player's kingdom
	 * 
	 * 
	 * @author OliverStappas
	 * 
	 */

	private static int getTotalCrowns(Player player) {
		int crownTotal = 0;
		for (Property property : player.getKingdom().getProperties()) { // Iterating through all the properties to get
																		// the total number of crowns
			crownTotal += property.getCrowns();
		}
		return crownTotal;
	}

	/**
	 * 
	 * Gets the size of a player's largest property
	 * 
	 * @param player -- Takes one player as input
	 * @return -- the the size of a player's largest property
	 * 
	 * 
	 * @author OliverStappas
	 * 
	 * 
	 */
	private static int getLargestPropertySize(Player player) {
		int largestPropertySize = 0;
		for (Property property : player.getKingdom().getProperties()) { // Iterating through properties to find largest
			if (property.getSize() > largestPropertySize) {
				largestPropertySize = property.getSize();
			}
		}
		return largestPropertySize;
	}

	/**
	 * 
	 * Method that returns a sorted array of players in a given game based on score,
	 * then the different tiebreaks if necessary
	 * 
	 * @param game -- The current game
	 * @return -- An array of the players sorted by score and tiebreaks
	 * 
	 * @author OliverStappas
	 * 
	 */
	public static Player[] sortPlayersByRanking(Game game) {

		int playerCount = game.getNumberOfPlayers();
		Player[] playerArray = new Player[playerCount];

		// Creating an array of all players to sort

		for (int i = 0; i < playerCount; i++) {
			playerArray[i] = game.getPlayer(i);

		}

		// Sorting by the different tie-breakers
		Arrays.sort(playerArray,
				Comparator.comparing(KingdominoController::calculatePlayerScore)
						.thenComparing(KingdominoController::getLargestPropertySize)
						.thenComparing(KingdominoController::getTotalCrowns).reversed());

		return playerArray;

	}

	/**
	 * 
	 * Checks if there are ties or not in a array of players already sorted by score
	 * and tiebreakers if necessary (this method is used for the cucumber feature
	 * test)
	 * 
	 * @param sortedPlayers -- an array of players sorted by score and tiebreaks if
	 *                      necessary
	 * @return -- returns true if there are any ties between any players
	 * 
	 * 
	 * @author OliverStappas
	 */
	public static boolean areThereAnyTies(Player[] sortedPlayers) {
		int counter = 0;
		Player current = sortedPlayers[0];
		Player next = sortedPlayers[1];

		while (counter < sortedPlayers.length) { // Go through the sorted player array and checks adjacent players to
													// see if they are tied in score

			if (calculatePlayerScore(current) == calculatePlayerScore(next)) {
				return true;
			}

			Player temp = next;

			if (counter != sortedPlayers.length - 1) {
				next = sortedPlayers[counter + 1];
			}

			current = temp;

			counter += 1;

		}

		return false;
	}

	/**
	 * 
	 * // Check if two players are still tied after the two tiebreaks, by checking
	 * if they are tied based on the three tie checks (score, propertySize,
	 * totalCrowns)
	 * 
	 * @param player1 - A first player
	 * @param player2 - A second player
	 * @return - Returns true if the 2 players are still tied after every tiebreak,
	 *         false otherwise
	 * 
	 * @author OliverStappas
	 * 
	 */
	private static boolean isStillTied(Player player1, Player player2) {
		return (calculatePlayerScore(player1) == calculatePlayerScore(player2)
				&& getLargestPropertySize(player1) == getLargestPropertySize(player2)
				&& getTotalCrowns(player1) == getTotalCrowns(player2));
	}

	/**
	 * 
	 * Calculates the bonus score of a given player based on which bonus options
	 * were used during the game.
	 * 
	 * 
	 * @param player -- Takes one player as input
	 * @return -- The bonus score of the player
	 * 
	 * @author OliverStappas
	 * 
	 */
	public static int calculateBonusScore(Player player) {

		int bonus = 0;

		// If there is a domino at each extremity
		boolean hasDominoAtXMinus2 = false;
		boolean hasDominoAtXPlus2 = false;
		boolean hasDominoAtYMinus2 = false;
		boolean hasDominoAtYPlus2 = false;

		for (BonusOption bonusOption : player.getGame().getSelectedBonusOptions()) {
			// Checking if there is a domino at the max and min of x and y determines
			// if the player should get the bonus points of MiddleKingdom

			if (bonusOption.getOptionName().equalsIgnoreCase("MiddleKingdom")
					|| bonusOption.getOptionName().equalsIgnoreCase("Middle Kingdom")) {
				for (KingdomTerritory territory : player.getKingdom().getTerritories()) {

					// Checking if there is a domino at each extremity
					if (territory instanceof DominoInKingdom) {
						DominoInKingdom dominoInKingdom = (DominoInKingdom) territory;
						DirectionKind direction = dominoInKingdom.getDirection();

						if (direction.equals(DirectionKind.Up)) {

							if (territory.getX() == -2) {
								hasDominoAtXMinus2 = true;
							}

							else if (territory.getX() == 2) {
								hasDominoAtXPlus2 = true;
							}

							if (territory.getY() == -2) {
								hasDominoAtYMinus2 = true;
							}

							else if (territory.getY() + 1 == 2) {
								hasDominoAtYPlus2 = true;
							}
						}

						if (direction.equals(DirectionKind.Right)) {

							if (territory.getX() == -2) {
								hasDominoAtXMinus2 = true;
							}

							else if (territory.getX() + 1 == 2) {
								hasDominoAtXPlus2 = true;
							}

							if (territory.getY() == -2) {
								hasDominoAtYMinus2 = true;
							}

							else if (territory.getY() == 2) {
								hasDominoAtYPlus2 = true;
							}

						}

						if (direction.equals(DirectionKind.Left)) {

							if (territory.getX() - 1 == -2) {
								hasDominoAtXMinus2 = true;
							}

							else if (territory.getX() == 2) {
								hasDominoAtXPlus2 = true;
							}

							if (territory.getY() == -2) {
								hasDominoAtYMinus2 = true;
							}

							else if (territory.getY() == 2) {
								hasDominoAtYPlus2 = true;
							}

						}

						if (direction.equals(DirectionKind.Down)) {

							if (territory.getX() == -2) {
								hasDominoAtXMinus2 = true;
							}

							else if (territory.getX() == 2) {
								hasDominoAtXPlus2 = true;
							}

							if (territory.getY() - 1 == -2) {
								hasDominoAtYMinus2 = true;
							}

							else if (territory.getY() == 2) {
								hasDominoAtYPlus2 = true;
							}

						}

					}
				}

				if (hasDominoAtXMinus2 && hasDominoAtXPlus2 && hasDominoAtYMinus2 && hasDominoAtYPlus2) {
					bonus += 10;
				}
			}

			if (bonusOption.getOptionName().equalsIgnoreCase("Harmony")
					&& player.getKingdom().getTerritories().size() >= 12) {
				bonus += 5;
			}

		}

		player.setBonusScore(bonus);
		return bonus;
	}

	/**
	 * 
	 * Calculates the total score (propertyScore + bonusScore) of a player
	 * 
	 * @param player -- Takes one player as input
	 * @return -- The total score of the player
	 * 
	 * 
	 * @author OliverStappas
	 * 
	 */
	public static int calculatePlayerScore(Player player) {

		int propertyScore = calculatePropertyAttributes(player.getKingdom());
		player.setPropertyScore(propertyScore);
		return (propertyScore + calculateBonusScore(player));

	}

	/**
	 * 
	 * Calculates and returns the ranking of all the players, taking into account
	 * tiebreaks. The ranking is a 2 dimension ArrayList of players, where the
	 * indexes of the sub-ArrayLists represent the ranking of the players - 1
	 * 
	 * @param game -- The current game that we want to get the player ranking of
	 * @return -- The 2 dimension ArrayList of player ranks
	 * 
	 * @author OliverStappas
	 */
	public static ArrayList<ArrayList<Player>> calculateRanking(Game game) {

		Player[] sortedPlayerArray = sortPlayersByRanking(game);

		ArrayList<ArrayList<Player>> rankingLists = new ArrayList<ArrayList<Player>>();

		rankingLists.add(new ArrayList<Player>());
		rankingLists.get(0).add(sortedPlayerArray[0]); // The first player added at rank 1

		for (int i = 1; i < sortedPlayerArray.length; i++) { // For every player

			Player current = sortedPlayerArray[i];

			if (!isStillTied(sortedPlayerArray[i - 1], current)) { // If the players aren't tied

				rankingLists.add(new ArrayList<Player>()); // Create a new rank (sub-ArrayList)

			}

			rankingLists.get(rankingLists.size() - 1).add(current); // Add the current player to their rank
		}

		return rankingLists; // The index of the sub-ArrayLists of players is the rank of the players in that
								// sub-ArrayList - 1
	}

}
