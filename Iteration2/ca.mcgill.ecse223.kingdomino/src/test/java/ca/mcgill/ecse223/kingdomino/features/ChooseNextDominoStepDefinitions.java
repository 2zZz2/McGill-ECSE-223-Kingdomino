package ca.mcgill.ecse223.kingdomino.features;



import static org.junit.Assert.assertEquals;
import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class ChooseNextDominoStepDefinitions {


	@Given("the game is initialized for choose next domino")
	public void the_game_is_initialized_for_choose_next_domino() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		KingdominoController.addDefaultUsersAndPlayers(game,4);
		KingdominoController.createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}



	@Given("the next draft is sorted with dominoes {string}")
	public void the_next_draft_is_sorted_with_dominoes(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft nextdraft = new Draft(DraftStatus.FaceUp,game);
		String[] strArray = string.split(",");
		for (int i=0 ; i< strArray.length;i++) {
			int id = Integer.parseInt(strArray[i].trim());
			nextdraft.addIdSortedDomino(KingdominoController.getdominoByID(id, game));
		}
		game.setNextDraft(nextdraft);
	}


	@Then("current player king now is on {string}")
	public void current_player_king_now_is_on(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getNextPlayer();
		int dominoiddesired = Integer.parseInt(string.trim());
		int dominoidreal = 0;
		for (DominoSelection dominoselection:game.getNextDraft().getSelections()) {
			if (dominoselection.getPlayer()==player) {
				dominoidreal = dominoselection.getDomino().getId();
			}
		}
		assertEquals(dominoiddesired,dominoidreal);
	}


	@Then("the selection for next draft is now equal to {string}")
	public void the_selection_for_next_draft_is_now_equal_to(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		String[] strArray = string.split(",");
		String[] curselection = new String[4];
		curselection[0]="none";
		curselection[1]="none";
		curselection[2]="none";
		curselection[3]="none";
		int counter = 0;
		for (DominoSelection dominoselection:game.getNextDraft().getSelections()) {
			for(int i = 0;i<4;i++) {
				if(dominoselection.getDomino()==game.getNextDraft().getIdSortedDomino(i)) {
					counter = i;
				}
			}		
			curselection[counter]=dominoselection.getPlayer().getColor().toString().toLowerCase();
		}
		for(int i=0;i<4;i++) {
			assertEquals(strArray[i],curselection[i]);
		}
	}


	@Then("the selection for the next draft selection is still {string}")
	public void the_selection_for_the_next_draft_selection_is_still(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		String[] strArray = string.split(",");
		String[] curselection = new String[4];
		curselection[0]="none";
		curselection[1]="none";
		curselection[2]="none";
		curselection[3]="none";
		int counter = 0;
		for (DominoSelection dominoselection:game.getNextDraft().getSelections()) {
			for(int i = 0;i<4;i++) {
				if(dominoselection.getDomino()==game.getNextDraft().getIdSortedDomino(i)) {
					counter = i;
				}
			}		
			curselection[counter]=dominoselection.getPlayer().getColor().toString().toLowerCase();
			System.out.println(dominoselection.getPlayer().getColor().toString());
		}
		for(int i=0;i<4;i++) {
			assertEquals(strArray[i],curselection[i]);
		}
	}

	
	@Given("player's domino selection {string}")
	public void player_s_domino_selection(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		String[] strArray = string.split(",");
		Draft draft = game.getNextDraft();
		if(SelectingFirstDominoStepDefinitions.select) {
			draft = game.getCurrentDraft();
		}
		
		for (int i=0;i<strArray.length;i++){
			if (strArray[i].trim().equals("green")) {
				DominoSelection selection1 = new DominoSelection(KingdominoController.getPlayerByColor(Player.PlayerColor.Green, game),draft.getIdSortedDomino(i),draft);
				draft.addSelectionAt(selection1, i);
			}else if (strArray[i].trim().equals("yellow")) {
				DominoSelection selection1 = new DominoSelection(KingdominoController.getPlayerByColor(Player.PlayerColor.Yellow, game),draft.getIdSortedDomino(i),draft);
				draft.addSelectionAt(selection1, i);
			}else if (strArray[i].trim().equals("pink")) {
				DominoSelection selection1 = new DominoSelection(KingdominoController.getPlayerByColor(Player.PlayerColor.Pink, game),draft.getIdSortedDomino(i),draft);
				draft.addSelectionAt(selection1, i);
			}else if (strArray[i].trim().equals("blue")) {
				DominoSelection selection1 = new DominoSelection(KingdominoController.getPlayerByColor(Player.PlayerColor.Blue, game),draft.getIdSortedDomino(i),draft);
				draft.addSelectionAt(selection1, i);
			}else {
			}
		}
}

	@Given("the current player is {string}")
	public void the_current_player_is(String string) {
		PlayerColor color = null;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		if (string.trim().equals("pink")) {
			color = PlayerColor.Pink;
		}
		if (string.trim().equals("green")) {
			color = PlayerColor.Green;
		}
		if (string.trim().equals("blue")) {
			color = PlayerColor.Blue;
		}
		if (string.trim().equals("yellow")) {
			color = PlayerColor.Yellow;
		}
		game.setNextPlayer(KingdominoController.getPlayerByColor(color,game));
	}

	@When("current player chooses to place king on {int}")
	public void current_player_chooses_to_place_king_on(Integer int1) {
		int id = int1;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		KingdominoController.placeKingOnDomino(id, game.getNextPlayer(),game.getNextDraft());

	}



}