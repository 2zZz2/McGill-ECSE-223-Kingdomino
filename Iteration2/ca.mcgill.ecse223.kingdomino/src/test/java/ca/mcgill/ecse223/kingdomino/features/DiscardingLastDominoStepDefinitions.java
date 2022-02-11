package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.Gamestatus;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.GamestatusOngoing;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class DiscardingLastDominoStepDefinitions {
	@Given("the game is initialized for discarding last domino")
	public void the_game_is_initialized_for_discarding_last_domino() {
	    // Write code here that turns the phrase above into concrete actions
		KingdominoController.startAndPopulateGame();
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		//game.setTopDominoInPile(KingdominoController.getdominoByID(1));
	}

	@Given("it is the last turn of the game")
	public void it_is_the_last_turn_of_the_game() {
	    // Write code here that turns the phrase above into concrete actions
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for(int i=0; i<= 11; i++) {
			Draft draft = new Draft(DraftStatus.FaceUp,game);
		}
		game.setCurrentDraft(game.getAllDraft(game.getAllDrafts().size()-1));
	}

	@Then("the next player shall be placing his\\/her domino")
	public void the_next_player_shall_be_placing_his_her_domino() {
	    // Write code here that turns the phrase above into concrete actions
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		GamestatusOngoing actualStatus = KingdominoApplication.getGameplay().getGamestatusOngoing();
		GamestatusOngoing expectedStatus = Gameplay.GamestatusOngoing.PreplacingDomino;
		assertEquals(actualStatus,expectedStatus);
	}

	@Then("the game shall be finished")
	public void the_game_shall_be_finished() {
	    // Write code here that turns the phrase above into concrete actions
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Gamestatus actualStatus = KingdominoApplication.getGameplay().getGamestatus();
		Gamestatus expectedStatus = Gameplay.Gamestatus.Over;
		System.out.println(game.getAllDrafts().size());
		System.out.println(KingdominoApplication.getGameplay().isCurrentTurnTheLastInGame());
		System.out.println(KingdominoApplication.getGameplay().isCurrentPlayerTheLastInTurn());
		assertEquals(actualStatus,expectedStatus);
	}

	@Then("the final results after discard shall be computed")
	public void the_final_results_after_discard_shall_be_computed() {
		assertEquals(Gamestatus.Over, KingdominoApplication.getGameplay().getGamestatus());
	}
}