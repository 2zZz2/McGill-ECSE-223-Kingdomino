/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;

import java.util.ArrayList;
import java.util.Collections;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;

// line 3 "../../../../../Gameplay.ump"
public class Gameplay {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Gameplay State Machines
	public enum Gamestatus {
		Initializing, Ongoing, Over
	}

	public enum GamestatusInitializing {
		Null, CreatingFirstDraft, SelectingFirstDomino
	}

	public enum GamestatusOngoing {
		Null, CreatingNextDraft, PreplacingDomino, PlacingDomino, DiscardingDomino, SelectingDomino
	}

	private Gamestatus gamestatus;
	private GamestatusInitializing gamestatusInitializing;
	private GamestatusOngoing gamestatusOngoing;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Gameplay() {
		setGamestatusInitializing(GamestatusInitializing.Null);
		setGamestatusOngoing(GamestatusOngoing.Null);
		setGamestatus(Gamestatus.Initializing);
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public String getGamestatusFullName() {
		String answer = gamestatus.toString();
		if (gamestatusInitializing != GamestatusInitializing.Null) {
			answer += "." + gamestatusInitializing.toString();
		}
		if (gamestatusOngoing != GamestatusOngoing.Null) {
			answer += "." + gamestatusOngoing.toString();
		}
		return answer;
	}

	public Gamestatus getGamestatus() {
		return gamestatus;
	}

	public GamestatusInitializing getGamestatusInitializing() {
		return gamestatusInitializing;
	}

	public GamestatusOngoing getGamestatusOngoing() {
		return gamestatusOngoing;
	}

	private boolean __autotransition17__() {
		boolean wasEventProcessed = false;

		GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
		switch (aGamestatusInitializing) {
		case CreatingFirstDraft:
			exitGamestatusInitializing();
			setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
			wasEventProcessed = true;
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	public boolean selectedFirst() {
		boolean wasEventProcessed = false;

		GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
		switch (aGamestatusInitializing) {
		case SelectingFirstDomino:
			if (!(isSelectionValid())) {
				exitGamestatusInitializing();
				setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
				wasEventProcessed = true;
				break;
			}
			if (!(isCurrentPlayerTheLastInTurn())) {
				exitGamestatusInitializing();
				// line 12 "../../../../../Gameplay.ump"
				setNextPlayer();
				setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
				wasEventProcessed = true;
				break;
			}
			if (isCurrentPlayerTheLastInTurn()) {
				exitGamestatus();
				setGamestatusOngoing(GamestatusOngoing.CreatingNextDraft);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	private boolean __autotransition18__() {
		boolean wasEventProcessed = false;

		GamestatusOngoing aGamestatusOngoing = gamestatusOngoing;
		switch (aGamestatusOngoing) {
		case CreatingNextDraft:
			exitGamestatusOngoing();
			setGamestatusOngoing(GamestatusOngoing.PreplacingDomino);
			wasEventProcessed = true;
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	public boolean preplaced() {
		boolean wasEventProcessed = false;

		GamestatusOngoing aGamestatusOngoing = gamestatusOngoing;
		switch (aGamestatusOngoing) {
		case PreplacingDomino:
			if (isThereAValidPlacement()) {
				exitGamestatusOngoing();
				setGamestatusOngoing(GamestatusOngoing.PlacingDomino);
				wasEventProcessed = true;
				break;
			}
			if (!(isThereAValidPlacement())) {
				exitGamestatusOngoing();
				setGamestatusOngoing(GamestatusOngoing.DiscardingDomino);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	public boolean placed() {
		boolean wasEventProcessed = false;

		GamestatusOngoing aGamestatusOngoing = gamestatusOngoing;
		switch (aGamestatusOngoing) {
		case PlacingDomino:
			if (isCurrentTurnTheLastInGame() && isCurrentPlayerTheLastInTurn()) {
				exitGamestatus();
				// line 26 "../../../../../Gameplay.ump"

				setGamestatus(Gamestatus.Over);
				wasEventProcessed = true;
				break;
			}
			if (!(isCurrentTurnTheLastInGame())) {
				exitGamestatusOngoing();
				// line 27 "../../../../../Gameplay.ump"

				setGamestatusOngoing(GamestatusOngoing.SelectingDomino);
				wasEventProcessed = true;
				break;
			}
			if (isCurrentTurnTheLastInGame() && !(isCurrentPlayerTheLastInTurn())) {
				exitGamestatusOngoing();
				// line 28 "../../../../../Gameplay.ump"

				setGamestatusOngoing(GamestatusOngoing.PreplacingDomino);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	public boolean discarded() {
		boolean wasEventProcessed = false;

		GamestatusOngoing aGamestatusOngoing = gamestatusOngoing;
		switch (aGamestatusOngoing) {
		case DiscardingDomino:
			if (isCurrentTurnTheLastInGame() && isCurrentPlayerTheLastInTurn()) {
				exitGamestatus();
				// line 32 "../../../../../Gameplay.ump"

				setGamestatus(Gamestatus.Over);
				wasEventProcessed = true;
				break;
			}
			if (!(isCurrentTurnTheLastInGame())) {
				exitGamestatusOngoing();
				// line 33 "../../../../../Gameplay.ump"

				setGamestatusOngoing(GamestatusOngoing.SelectingDomino);
				wasEventProcessed = true;
				break;
			}
			if (isCurrentTurnTheLastInGame() && !(isCurrentPlayerTheLastInTurn())) {
				exitGamestatusOngoing();
				// line 34 "../../../../../Gameplay.ump"

				setGamestatusOngoing(GamestatusOngoing.PreplacingDomino);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	public boolean selected() {
		boolean wasEventProcessed = false;

		GamestatusOngoing aGamestatusOngoing = gamestatusOngoing;
		switch (aGamestatusOngoing) {
		case SelectingDomino:
			if (!(isSelectionValid())) {
				exitGamestatusOngoing();
				setGamestatusOngoing(GamestatusOngoing.SelectingDomino);
				wasEventProcessed = true;
				break;
			}
			if (isCurrentPlayerTheLastInTurn()) {
				exitGamestatusOngoing();
				// line 39 "../../../../../Gameplay.ump"

				setGamestatusOngoing(GamestatusOngoing.CreatingNextDraft);
				wasEventProcessed = true;
				break;
			}
			if (!(isCurrentPlayerTheLastInTurn())) {
				exitGamestatusOngoing();
				// line 40 "../../../../../Gameplay.ump"
				setNextPlayer();
				setGamestatusOngoing(GamestatusOngoing.PreplacingDomino);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	private void exitGamestatus() {
		switch (gamestatus) {
		case Initializing:
			exitGamestatusInitializing();
			break;
		case Ongoing:
			exitGamestatusOngoing();
			break;
		}
	}

	private void setGamestatus(Gamestatus aGamestatus) {
		gamestatus = aGamestatus;

		// entry actions and do activities
		switch (gamestatus) {
		case Initializing:
			if (gamestatusInitializing == GamestatusInitializing.Null) {
				setGamestatusInitializing(GamestatusInitializing.CreatingFirstDraft);
			}
			break;
		case Ongoing:
			if (gamestatusOngoing == GamestatusOngoing.Null) {
				setGamestatusOngoing(GamestatusOngoing.CreatingNextDraft);
			}
			break;
		case Over:
			// line 44 "../../../../../Gameplay.ump"
			calculateRanking();
			break;
		}
	}

	private void exitGamestatusInitializing() {
		switch (gamestatusInitializing) {
		case CreatingFirstDraft:
			setGamestatusInitializing(GamestatusInitializing.Null);
			break;
		case SelectingFirstDomino:
			setGamestatusInitializing(GamestatusInitializing.Null);
			break;
		}
	}

	private void setGamestatusInitializing(GamestatusInitializing aGamestatusInitializing) {
		gamestatusInitializing = aGamestatusInitializing;
		if (gamestatus != Gamestatus.Initializing && aGamestatusInitializing != GamestatusInitializing.Null) {
			setGamestatus(Gamestatus.Initializing);
		}

		// entry actions and do activities
		switch (gamestatusInitializing) {
		case CreatingFirstDraft:
			// line 8 "../../../../../Gameplay.ump"
			shuffleDominoPile();
			generateInitialPlayerOrder();
			__autotransition17__();
			break;
		}
	}

	private void exitGamestatusOngoing() {
		switch (gamestatusOngoing) {
		case CreatingNextDraft:
			setGamestatusOngoing(GamestatusOngoing.Null);
			break;
		case PreplacingDomino:
			setGamestatusOngoing(GamestatusOngoing.Null);
			break;
		case PlacingDomino:
			// line 29 "../../../../../Gameplay.ump"
			calculatePlayerScore();
			setGamestatusOngoing(GamestatusOngoing.Null);
			break;
		case DiscardingDomino:
			// line 35 "../../../../../Gameplay.ump"
			calculatePlayerScore();
			setGamestatusOngoing(GamestatusOngoing.Null);
			break;
		case SelectingDomino:
			setGamestatusOngoing(GamestatusOngoing.Null);
			break;
		}
	}

	private void setGamestatusOngoing(GamestatusOngoing aGamestatusOngoing) {
		gamestatusOngoing = aGamestatusOngoing;
		if (gamestatus != Gamestatus.Ongoing && aGamestatusOngoing != GamestatusOngoing.Null) {
			setGamestatus(Gamestatus.Ongoing);
		}

		// entry actions and do activities
		switch (gamestatusOngoing) {
		case CreatingNextDraft:
			// line 18 "../../../../../Gameplay.ump"
			createNextDraft();
			orderNextDraft();
			generateNextPlayerOrder();
			revealNextDraft();
			__autotransition18__();
			break;
		}
	}

	public void delete() {
	}

	/**
	 * Setter for test setup
	 */
	// line 56 "../../../../../Gameplay.ump"
	public void setGamestatus(String status) {
		switch (status) {
		case "CreatingFirstDraft":
			gamestatus = Gamestatus.Initializing;
			gamestatusInitializing = GamestatusInitializing.CreatingFirstDraft;
			break;
		case "SelectingFirstDomino":
			gamestatus = Gamestatus.Initializing;
			gamestatusInitializing = GamestatusInitializing.SelectingFirstDomino;
			break;
		case "CreatingNextDraft":
			gamestatus = Gamestatus.Ongoing;
			gamestatusOngoing = GamestatusOngoing.CreatingNextDraft;
			gamestatusInitializing = GamestatusInitializing.Null;
			break;
		case "PreplacingDomino":
			gamestatus = Gamestatus.Ongoing;
			gamestatusOngoing = GamestatusOngoing.PreplacingDomino;
			gamestatusInitializing = GamestatusInitializing.Null;
			break;
		case "PlacingDomino":
			gamestatus = Gamestatus.Ongoing;
			gamestatusOngoing = GamestatusOngoing.PlacingDomino;
			gamestatusInitializing = GamestatusInitializing.Null;
			break;
		case "DiscardingDomino":
			gamestatus = Gamestatus.Ongoing;
			gamestatusOngoing = GamestatusOngoing.DiscardingDomino;
			gamestatusInitializing = GamestatusInitializing.Null;
			break;
		case "SelectingDomino":
			gamestatus = Gamestatus.Ongoing;
			gamestatusOngoing = GamestatusOngoing.SelectingDomino;
			gamestatusInitializing = GamestatusInitializing.Null;
			break;
		case "Over":
			gamestatus = Gamestatus.Over;
		default:
			throw new RuntimeException("Invalid gamestatus string was provided: " + status);
		}
	}

	/**
	 * 
	 * 
	 * 
	 * Guards line 101 "../../../../../Gameplay.ump" line 100
	 * "../../../../../Gameplay.ump" line 100 "../../../../../Gameplay.ump"
	 */
	// line 106 "../../../../../Gameplay.ump"
	public boolean isCurrentPlayerTheLastInTurn() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		int numberOfPlayers = game.getPlayers().size();
		Draft draft = game.getCurrentDraft();
		if (getGamestatus().equals(Gamestatus.Ongoing)) {
			draft = game.getNextDraft();
		}
		return draft.getSelections().size() == numberOfPlayers;
	}

	/**
	 * 
	 * 
	 * line 109 "../../../../../Gameplay.ump" line 112 "../../../../../Gameplay.ump"
	 * line 115 "../../../../../Gameplay.ump"
	 */
	// line 122 "../../../../../Gameplay.ump"
	public boolean isCurrentTurnTheLastInGame() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Draft draft = game.getCurrentDraft();
		int numberOfDrafts = game.getAllDrafts().size();
		if (game.getAllDrafts().get(numberOfDrafts - 1).equals(draft))
			return true;
		else
			return false;
	}

	/**
	 * 
	 * 
	 * 
	 * You may need to add more guards here line 121 "../../../../../Gameplay.ump"
	 * line 127 "../../../../../Gameplay.ump" line 131 "../../../../../Gameplay.ump"
	 */
	// line 140 "../../../../../Gameplay.ump"
	public boolean isThereAValidPlacement() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Kingdom kingdom = game.getNextPlayer().getKingdom();
		if (kingdom.getTerritory(kingdom.numberOfTerritories() - 1) instanceof Castle) {
			return true;
		}
		DominoInKingdom lastPlacement = KingdominoController.getLastDomino();
		if (KingdominoController.discardDomino(lastPlacement.getDomino()))
			return false;
		else
			return true;
	}

	/**
	 * 
	 * 
	 * line 130 "../../../../../Gameplay.ump" line 142 "../../../../../Gameplay.ump"
	 * line 149 "../../../../../Gameplay.ump"
	 */
	// line 159 "../../../../../Gameplay.ump"
	public boolean isSelectionValid() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Draft draft = game.getNextDraft();
		if (getGamestatus().equals(Gamestatus.Initializing)) {
			draft = game.getCurrentDraft();
		}
		for (DominoSelection selection : draft.getSelections()) {
			if (selection.getPlayer().equals(game.getNextPlayer())) {
				return true;
			}
		}
		return false;
	}

	// line 175 "../../../../../Gameplay.ump"
	public void shuffleDominoPile() {
		try {
			if (KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers() == 2) {
				KingdominoController.shuffleDominos(KingdominoApplication.getKingdomino().getCurrentGame(),
						KingdominoController.generateRandomOrder(2));
			}

			if (KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers() == 3) {
				KingdominoController.shuffleDominos(KingdominoApplication.getKingdomino().getCurrentGame(),
						KingdominoController.generateRandomOrder(3));
			}

			if (KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers() == 4) {
				KingdominoController.shuffleDominos(KingdominoApplication.getKingdomino().getCurrentGame(),
						KingdominoController.generateRandomOrder(4));
			}
			KingdominoController.initiateDrafts(KingdominoApplication.getKingdomino().getCurrentGame(), 1);
		} catch (InvalidInputException e) {
			System.out.println("Fail to shuffle the dominoes with provided order and number of players!");
		}
	}

	// line 198 "../../../../../Gameplay.ump"
	public void generateInitialPlayerOrder() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		ArrayList<Integer> playerList = new ArrayList<>();

		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			playerList.add(i);
		}
		Collections.shuffle(playerList);
		KingdominoController.initialorder = playerList;
		KingdominoController.nextorder = 0;
		game.setNextPlayer(game.getPlayer(playerList.get(0)));
		KingdominoController.nextUI();
		KingdominoController.nextorder++;
	}

	// line 214 "../../../../../Gameplay.ump"
	public void generateNextPlayerOrder() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		ArrayList<Integer> order = new ArrayList<Integer>();
		for (int i = 0; i < game.getCurrentDraft().numberOfIdSortedDominos(); i++) {
			for (DominoSelection selection : game.getCurrentDraft().getSelections()) {
				if (selection.getDomino().equals(game.getCurrentDraft().getIdSortedDomino(i))) {
					Player player = selection.getPlayer();
					int index = game.indexOfPlayer(player);
					order.add(index);
				}
			}
		}
		KingdominoController.initialorder = order;
		KingdominoController.nextorder = 0;
		game.setNextPlayer(game.getPlayer(order.get(0)));
		KingdominoController.initialorder.get(0);
		KingdominoController.nextUI();
		KingdominoController.nextorder++;
	}

	// line 235 "../../../../../Gameplay.ump"
	public void createNextDraft() {
		KingdominoController.createNextDraft();
	}

	// line 240 "../../../../../Gameplay.ump"
	public void orderNextDraft() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		KingdominoController.order(kingdomino.getCurrentGame().getNextDraft());
		KingdominoController.orderDraftUI();
	}

	// line 247 "../../../../../Gameplay.ump"
	public void revealNextDraft() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		KingdominoController.reveal(kingdomino.getCurrentGame().getNextDraft());
		KingdominoController.revealDraftUI();
	}

	// line 254 "../../../../../Gameplay.ump"
	public void placeDomino(DominoInKingdom dominoToPlace, Player player) {
		KingdominoController.placeSelectedDomino(dominoToPlace, player);
	}

	// line 259 "../../../../../Gameplay.ump"
	public void calculatePlayerScore() {
		Player player = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer();
		KingdominoController.identifyProperty(player.getKingdom());
		KingdominoController.calculatePlayerScore(player);
	}

	// line 266 "../../../../../Gameplay.ump"
	public ArrayList<ArrayList<Player>> calculateRanking() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		KingdominoController.switchState();
		return KingdominoController.calculateRanking(game);
	}

	// line 273 "../../../../../Gameplay.ump"
	public void setNextPlayer() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		game.setNextPlayer(game.getPlayer(KingdominoController.initialorder.get(KingdominoController.nextorder)));
		KingdominoController.nextUI();
		KingdominoController.nextorder = KingdominoController.nextorder + 1;
	}

}