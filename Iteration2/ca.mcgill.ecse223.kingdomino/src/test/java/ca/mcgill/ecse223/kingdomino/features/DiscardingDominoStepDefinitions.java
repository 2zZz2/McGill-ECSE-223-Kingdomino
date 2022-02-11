package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.Gamestatus;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.GamestatusOngoing;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DiscardingDominoStepDefinitions {
	Player playerToCheck;
	@Given("the game is initialized for discarding domino")
	public void the_game_is_initialized_for_discarding_domino() {
		KingdominoController.startAndPopulateGame();
	}
	@Given("it is not the last turn of the game")
	public void it_is_not_the_last_turn_of_the_game() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for(int i=0; i<= 10; i++) {
			Draft draft = new Draft(DraftStatus.FaceUp,game);
		}
		game.setCurrentDraft(game.getAllDraft(0));
	}

	@Given("the current player is not the last player in the turn")
	public void the_current_player_is_not_the_last_player_in_the_turn() {
	    // Write code here that turns the phrase above into concrete actions
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft draft = game.getCurrentDraft();
		game.setNextPlayer(game.getPlayer(0));
		//DominoSelection randomSelection = new DominoSelection(game.getNextPlayer(),KingdominoController.getdominoByID(1),draft);
	}
	
	@Given("the current player is preplacing his\\/her domino with ID {int} at location {int}:{int} with direction {string}")
	public void the_current_player_is_preplacing_his_her_domino_with_ID_at_location_with_direction(Integer int1, Integer int2, Integer int3, String string) {
	    // Write code here that turns the phrase above into concrete actions
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player currentPlayer = game.getNextPlayer();
		playerToCheck = currentPlayer;
		Kingdom kingdom = currentPlayer.getKingdom();
		Domino domino = KingdominoController.getdominoByID(int1);
		DominoInKingdom newTerritory = new DominoInKingdom(int2, int3, kingdom, domino);
		newTerritory.setDirection(getDirection(string));
		kingdom.addTerritory(newTerritory);
		CalculatingPlayerScoreStepDefinitions.dnk = newTerritory;

	}
	
	@Given("it is impossible to place the current domino in his\\/her kingdom")
	public void it_is_impossible_to_place_the_current_domino_in_his_her_kingdom() {
	    // Write code here that turns the phrase above into concrete actions
		//System.out.println(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer().getUser());
		KingdominoApplication.getGameplay().setGamestatus("DiscardingDomino");
	}

	@When("the current player discards his\\/her domino")
	public void the_current_player_discards_his_her_domino() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		if(!CalculatingPlayerScoreStepDefinitions.calculating) {	
			game.setNextPlayer(playerToCheck);
			Kingdom kingdom = game.getNextPlayer().getKingdom();
			DominoInKingdom lastDomino = (DominoInKingdom) kingdom.getTerritory(kingdom.getTerritories().size()-1);
			Domino domino = lastDomino.getDomino();
			KingdominoController.discardDomino(domino);
		}else {
			KingdominoApplication.getGameplay().discarded();
		}

	}
	

	@Given("the current player is the last player in the turn")
	public void the_current_player_is_the_last_player_in_the_turn() {
	    // Write code here that turns the phrase above into concrete actionsGame game = KingdominoApplication.getKingdomino().getCurrentGame();
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft draft = game.getCurrentDraft();
		DominoSelection randomSelection1 = new DominoSelection(game.getPlayer(0),KingdominoController.getdominoByID(1),draft);
		DominoSelection randomSelection2 = new DominoSelection(game.getPlayer(1),KingdominoController.getdominoByID(2),draft);
		DominoSelection randomSelection3 = new DominoSelection(game.getPlayer(2),KingdominoController.getdominoByID(3),draft);
		DominoSelection randomSelection4 = new DominoSelection(game.getPlayer(3),KingdominoController.getdominoByID(4),draft);

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

}