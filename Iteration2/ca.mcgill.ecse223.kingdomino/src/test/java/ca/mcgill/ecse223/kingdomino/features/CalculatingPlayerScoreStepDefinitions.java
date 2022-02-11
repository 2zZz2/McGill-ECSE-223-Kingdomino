package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;

import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.Gamestatus;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;

import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatingPlayerScoreStepDefinitions {
		
	Kingdomino kingdomino;
	Game game;
	Player player;
	Kingdom kingdom;
	Integer playerScore;
	Gameplay gameplay;
	public static boolean calculating = false;
	public static DominoInKingdom dnk;
	
	@Given("the game is initialized for calculating player score")
	public void the_game_is_initialized_for_calculating_player_score() {
	    // Write code here that turns the phrase above into concrete actions

		kingdomino = new Kingdomino();
		game = new Game(48, kingdomino);
		kingdomino.setCurrentGame(game);
    	KingdominoController.createAllDominoes(game);
		KingdominoApplication.setKingdomino(kingdomino);
    	KingdominoController.addDefaultUsersAndPlayers(game, 4);
    	KingdominoController.createCastles(game);
		player = game.getPlayer(0);
		game.setNextPlayer(player);
    	gameplay = new Gameplay();
    	KingdominoApplication.setGameplay(gameplay);
    	gameplay.setGamestatus("PlacingDomino");
    	calculating = true;
    	
    }

	@Given("the current player has no dominoes in his\\/her kingdom yet")
	public void the_current_player_has_no_dominoes_in_his_her_kingdom_yet() {
	    // Write code here that turns the phrase above into concrete actions
		for (KingdomTerritory kT: player.getKingdom().getTerritories()) {
			player.getKingdom().removeTerritory(kT);
		}
	}

	@Given("the score of the current player is {int}")
	public void the_score_of_the_current_player_is(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
		playerScore = int1;
	}

	@When("the current player places his\\/her domino")
	public void the_current_player_places_his_her_domino() {
//    	gameplay.setGamestatus("PlacingDomino");
	    KingdominoApplication.getGameplay().placed();
	}

	@Then("the score of the current player shall be {int}")
	public void the_score_of_the_current_player_shall_be(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
		playerScore = player.getTotalScore();
		assertEquals(int1, game.getNextPlayer().getPropertyScore());
	    
	}

	@Given("the game has no bonus options selected")
	public void the_game_has_no_bonus_options_selected() {
	    // Write code here that turns the phrase above into concrete actions
		for (BonusOption bonusOption : player.getGame().getSelectedBonusOptions()) {
			player.getGame().removeSelectedBonusOption(bonusOption);
		}
	}

	@Given("the current player is placing his\\/her domino with ID {int}")
	public void the_current_player_is_placing_his_her_domino_with_ID(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
    	gameplay.setGamestatus("PreplacingDomino");
    	
    	Domino domino = KingdominoController.getdominoByID(int1);
    	
    	Draft draft = new Draft(DraftStatus.FaceUp, game);
    	draft.addIdSortedDomino(domino);
    	   	
    	new DominoSelection(player, domino, draft);
  
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
		case "InPile":
			return DominoStatus.InPile;
		case "excluded":
			return DominoStatus.Excluded;
		case "InCurrentDraft":
			return DominoStatus.InCurrentDraft;
		case "InNextDraft":
			return DominoStatus.InNextDraft;
		case "ErroneouslyPreplaced":
			return DominoStatus.ErroneouslyPreplaced;
		case "CorrectlyPreplaced":
			return DominoStatus.CorrectlyPreplaced;
		case "PlacedInKingdom":
			return DominoStatus.PlacedInKingdom;
		case "Discarded":
			return DominoStatus.Discarded;
		default:
			throw new java.lang.IllegalArgumentException("Invalid domino status: " + status);
		}

	}
}