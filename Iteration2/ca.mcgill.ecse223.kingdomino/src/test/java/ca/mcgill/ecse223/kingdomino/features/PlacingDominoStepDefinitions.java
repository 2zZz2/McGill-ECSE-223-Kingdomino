package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.GamestatusOngoing;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Player;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PlacingDominoStepDefinitions {
	@Given("the game has been initialized for placing domino")
	public void the_game_has_been_initialized_for_placing_domino() {
		KingdominoController.startAndPopulateGame();
		KingdominoApplication.getGameplay().setGamestatus("PlacingDomino");
	}


	@Given("the preplaced domino has the status {string}")
	public void the_preplaced_domino_has_the_status(String string) {
		DominoStatus status = null;
		
		if (string.trim().equalsIgnoreCase("Excluded")) {
			status=DominoStatus.Excluded;
		}
		if (string.trim().equalsIgnoreCase("Inpile")) {
			status=DominoStatus.InPile;
		}
		if (string.trim().equalsIgnoreCase("InNextDraft")) {
			status=DominoStatus.InNextDraft;
		}
		if (string.trim().equalsIgnoreCase("InCurrentDraft")) {
			status=DominoStatus.InCurrentDraft;
		}
		if (string.trim().equalsIgnoreCase("CorrectlyPreplaced")) {
			status=DominoStatus.CorrectlyPreplaced;
		}
		if (string.trim().equalsIgnoreCase("ErroneouslyPreplaced")) {
			status=DominoStatus.ErroneouslyPreplaced;
		}
		if (string.trim().equalsIgnoreCase("PlacedInKingdom")) {
			status=DominoStatus.PlacedInKingdom;
		}
		if (string.trim().equalsIgnoreCase("Discarded")) {
			status=DominoStatus.Discarded;
		}
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();

		Domino domino;
		if(CalculatingPlayerScoreStepDefinitions.calculating) {
			domino = CalculatingPlayerScoreStepDefinitions.dnk.getDomino();
		}else {
			 domino = KingdominoController.getLastDomino().getDomino();
		}

		domino.setStatus(status);
	}

	@Then("this player now shall be making his\\/her domino selection")
	public void this_player_now_shall_be_making_his_her_domino_selection() {
		GamestatusOngoing actualStatus = KingdominoApplication.getGameplay().getGamestatusOngoing();
		GamestatusOngoing expectedStatus = Gameplay.GamestatusOngoing.SelectingDomino;
		assertEquals(actualStatus,expectedStatus);
	}


}