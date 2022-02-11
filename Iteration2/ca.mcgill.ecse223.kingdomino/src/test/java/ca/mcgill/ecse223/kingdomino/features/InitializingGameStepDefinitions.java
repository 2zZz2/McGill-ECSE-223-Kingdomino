
package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.GamestatusInitializing;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
/**
 * 
 * @author Chang
 *
 */
public class InitializingGameStepDefinitions {
	@Given("the game has not been started")
	public void the_game_has_not_been_started() {
    	Kingdomino kingdomino = new Kingdomino();
    	KingdominoApplication.setKingdomino(kingdomino);
    	Game game = new Game(48, kingdomino);
    	kingdomino.setCurrentGame(game);
    	KingdominoController.addDefaultUsersAndPlayers(game, 4);
    	KingdominoController.createCastles(game);
    	KingdominoController.createAllDominoes(game);
	}

	@When("start of the game is initiated")
	public void start_of_the_game_is_initiated() {
    	Gameplay gameplay = new Gameplay();
    	KingdominoApplication.setGameplay(gameplay);
	}

	@Then("the pile shall be shuffled")
	public void the_pile_shall_be_shuffled() {
		assertTrue(KingdominoApplication.getKingdomino().getCurrentGame().getTopDominoInPile().hasNextDomino());
	}

	@Then("the first draft shall be on the table")
	public void the_first_draft_shall_be_on_the_table() {
		assertTrue(KingdominoApplication.getKingdomino().getCurrentGame().hasCurrentDraft());
	}

	@Then("the first draft shall be revealed")
	public void the_first_draft_shall_be_revealed() {
		assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getDraftStatus(), DraftStatus.FaceUp);
	}

	@Then("the initial order of players shall be determined")
	public void the_initial_order_of_players_shall_be_determined() {
		assertTrue(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer()!=null);
	}

	@Then("the first player shall be selecting his\\/her first domino of the game")
	public void the_first_player_shall_be_selecting_his_her_first_domino_of_the_game() {
		assertEquals(KingdominoApplication.getGameplay().getGamestatusInitializing(), GamestatusInitializing.SelectingFirstDomino);
	}

	@Then("the second draft shall be on the table, face down")
	public void the_second_draft_shall_be_on_the_table_face_down() {
		assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getTopDominoInPile().getStatus(),DominoStatus.InNextDraft);
	}
}
