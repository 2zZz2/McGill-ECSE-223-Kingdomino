package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RevealAndOrderDraftStepDefinitions {
	Kingdomino kingdomino;
	Game game;
	ArrayList<Domino> dominoes;
	Draft curDraft;
	Draft nextDraft;
	
	//Order and reveal
		@Given("the game is initialized for order next draft of dominoes")
		public void the_game_is_initialized_for_order_next_draft_of_dominoes() {
			kingdomino = new Kingdomino();
			game = new Game(48, kingdomino);
			kingdomino.setCurrentGame(game);
			KingdominoController.createAllDominoes(game);
		}

		@Given("the next draft is {string}")
		public void the_next_draft_is(String string) {
			String[] token = string.split(",");
			int firstId = Integer.parseInt(token[0]);
			int secondId =  Integer.parseInt(token[1]);
			int thirdId =  Integer.parseInt(token[2]);
			int fourthId =  Integer.parseInt(token[3]);
			
			
			Draft draft = new Draft(DraftStatus.FaceDown,game);
			draft.addIdSortedDomino(game.getAllDomino(firstId-1));
			draft.addIdSortedDomino(game.getAllDomino(secondId-1));
			draft.addIdSortedDomino(game.getAllDomino(thirdId-1));
			draft.addIdSortedDomino(game.getAllDomino(fourthId-1));
			game.setNextDraft(draft);
		}

		@Given("the dominoes in next draft are facing down")
		public void the_dominoes_in_next_draft_are_facing_down() {
			game.getNextDraft().setDraftStatus(DraftStatus.FaceDown);
		}

		@When("the ordering of the dominoes in the next draft is initiated")
		public void the_ordering_of_the_dominoes_in_the_next_draft_is_initiated() {
			KingdominoController.order(kingdomino.getCurrentGame().getNextDraft());
		}

		@Then("the status of the next draft is sorted")
		public void the_status_of_the_next_draft_is_sorted() {
			assertEquals(DraftStatus.Sorted,game.getNextDraft().getDraftStatus());
		}

		@Then("the order of dominoes in the draft will be {string}")
		public void the_order_of_dominoes_in_the_draft_will_be(String string) {
			String compare = game.getNextDraft().getIdSortedDomino(0).getId() + "";
			for (int i = 1; i < game.getNextDraft().numberOfIdSortedDominos(); i++) {
				compare += "," + game.getNextDraft().getIdSortedDomino(i).getId();
			}
			assertEquals(string, compare);
		}

		@Given("the game is initialized for reveal next draft of dominoes")
		public void the_game_is_initialized_for_reveal_next_draft_of_dominoes() {
			kingdomino = new Kingdomino();
			game = new Game(48, kingdomino);
			kingdomino.setCurrentGame(game);
			KingdominoController.createAllDominoes(game);
		}

		@Given("the dominoes in next draft are sorted")
		public void the_dominoes_in_next_draft_are_sorted() {
			KingdominoController.order(kingdomino.getCurrentGame().getNextDraft());
		}

		@When("the revealing of the dominoes in the next draft is initiated")
		public void the_revealing_of_the_dominoes_in_the_next_draft_is_initiated() {
			KingdominoController.reveal(kingdomino.getCurrentGame().getNextDraft());
		}

		@Then("the status of the next draft is face up")
		public void the_status_of_the_next_draft_is_face_up() {
			assertEquals(DraftStatus.FaceUp, game.getNextDraft().getDraftStatus());
		}
		
		@After
		public void tearDown() {
			if(kingdomino!=null) {
				kingdomino.delete();
			}
		}
}
