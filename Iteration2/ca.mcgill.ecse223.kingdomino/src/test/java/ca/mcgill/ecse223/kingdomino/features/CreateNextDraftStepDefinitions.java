package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateNextDraftStepDefinitions {

	Kingdomino kingdomino = KingdominoApplication.getKingdomino();
	Game game;
	Draft curDraft;
	Draft nextDraft;

	@Given("the game is initialized to create next draft")
	public void the_game_is_initialized_to_create_next_draft() {
		game = new Game(48, kingdomino);
		kingdomino.setCurrentGame(game);
		KingdominoController.createAllDominoes(game);
		
	}

	@Given("there has been {int} drafts created")
	public void there_has_been_drafts_created(Integer int1) {
		for(int i = 0; i < int1; i++) {
			new Draft(DraftStatus.FaceUp,game);
		}
	}

	@Given("there is a current draft")
	public void there_is_a_current_draft() {
		curDraft = game.getAllDraft(game.numberOfAllDrafts()-2);
		curDraft.addIdSortedDomino(KingdominoController.getdominoByID(1));
		curDraft.addIdSortedDomino(KingdominoController.getdominoByID(2));
		curDraft.addIdSortedDomino(KingdominoController.getdominoByID(3));
		curDraft.addIdSortedDomino(KingdominoController.getdominoByID(4));
		game.setCurrentDraft(curDraft);
	}
	
	@Given("there is a next draft")
	public void there_is_a_next_draft() {
		nextDraft = game.getAllDraft(game.numberOfAllDrafts()-1);
		nextDraft.addIdSortedDomino(KingdominoController.getdominoByID(5));
		nextDraft.addIdSortedDomino(KingdominoController.getdominoByID(6));
		nextDraft.addIdSortedDomino(KingdominoController.getdominoByID(7));
		nextDraft.addIdSortedDomino(KingdominoController.getdominoByID(8));
		game.setNextDraft(nextDraft);
	}
	@Given("the top {int} dominoes in my pile have the IDs {string}")
	public void the_top_dominoes_in_my_pile_have_the_IDs(Integer int1, String string) {
		String[] split = string.split(",");
		int dominoId =  Integer.parseInt(split[0])-1;
		game.setTopDominoInPile(game.getAllDomino(dominoId));
		Domino curDomino = null;
		Domino prevDomino = null;
		for(int i = 0; i < int1; i++) {
			dominoId =  Integer.parseInt(split[i])-1;
			curDomino = game.getAllDomino(dominoId);
			if(prevDomino != null) {
				prevDomino.setNextDomino(curDomino);
				curDomino.setPrevDomino(prevDomino);
			}
			prevDomino = curDomino;
		}
	}

	@When("create next draft is initiated")
	public void create_next_draft_is_initiated() {
		KingdominoController.createNextDraft();
	}

	@Then("a new draft is created from dominoes {string}")
	public void a_new_draft_is_created_from_dominoes(String string) {
		String compare = game.getAllDraft(game.numberOfAllDrafts()-1).getIdSortedDomino(0).getId() + "";
		for (int i = 1; i < game.getAllDraft(game.numberOfAllDrafts()-1).numberOfIdSortedDominos(); i++) {
			compare += "," + game.getAllDraft(game.numberOfAllDrafts()-1).getIdSortedDomino(i).getId();
		}
		assertEquals(string, compare);
	}

	@Then("the next draft now has the dominoes {string}")
	public void the_next_draft_now_has_the_dominoes(String string) {
		String compare = game.getNextDraft().getIdSortedDomino(0).getId() + "";
		for (int i = 1; i < game.getNextDraft().numberOfIdSortedDominos(); i++) {
			compare += "," + game.getNextDraft().getIdSortedDomino(i).getId();
		}
		assertEquals(string, compare);
	}

	@Then("the dominoes in the next draft are face down")
	public void the_dominoes_in_the_next_draft_are_face_down() {
		assertEquals(DraftStatus.FaceDown, game.getNextDraft().getDraftStatus());
	}

	@Then("the top domino of the pile is ID {int}")
	public void the_top_domino_of_the_pile_is_ID(Integer int1) {
		assertEquals(int1, game.getTopDominoInPile().getId());
	}

	@Then("the former next draft is now the current draft")
	public void the_former_next_draft_is_now_the_current_draft() {
		assertEquals(nextDraft,game.getCurrentDraft());
	}

	@Given("this is a {int} player game")
	public void this_is_a_player_game(Integer int1) {
		game.setNumberOfPlayers(int1);
	}

	@Then("the pile is empty")
	public void the_pile_is_empty() {
		assertEquals(null, game.getTopDominoInPile());
	}

	@Then("there is no next draft")
	public void there_is_no_next_draft() {
		assertEquals(null, game.getNextDraft());
	}

	
	@After
	public void tearDown() {
		if(kingdomino!=null) {
			kingdomino.delete();
		}
	}
}
