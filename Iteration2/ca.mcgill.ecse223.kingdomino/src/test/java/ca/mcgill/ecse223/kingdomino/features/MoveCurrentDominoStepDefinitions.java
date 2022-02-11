package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MoveCurrentDominoStepDefinitions {

	DominoInKingdom tentativedik;

	@When("{string} removes his king from the domino {int}")
	public void removes_his_king_from_the_domino(String string, Integer int1) {
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

		Player player = KingdominoController.getPlayerByColor(color, game);
		game.setNextPlayer(player);

		KingdominoController.removeKingFromDomino(player, KingdominoController.getdominoByID(int1, game), game);
	}

	@Then("domino {int} should be tentative placed at position {int}:{int} of {string}'s kingdom with ErroneouslyPreplaced status")
	public void domino_should_be_tentative_placed_at_position_of_s_kingdom_with_ErroneouslyPreplaced_status(
			Integer int1, Integer int2, Integer int3, String string) {

		/*
		 * PlayerColor color = null; Game game =
		 * KingdominoApplication.getKingdomino().getCurrentGame(); if
		 * (string.trim().equals("pink")) { color = PlayerColor.Pink; } if
		 * (string.trim().equals("green")) { color = PlayerColor.Green; } if
		 * (string.trim().equals("blue")) { color = PlayerColor.Blue; } if
		 * (string.trim().equals("yellow")) { color = PlayerColor.Yellow; }
		 * 
		 * Player player = KingdominoController.getPlayerByColor(color, game);
		 * game.setNextPlayer(player);
		 */

		// Domino domino = KingdominoController.getdominoByID(int1, game);
		// KingdominoController.removeKingFromDomino(player, domino, game);

		// VERIFY!!! int1 NOT USED !!
		// DominoInKingdom dik = (DominoInKingdom)
		// game.getNextPlayer().getKingdom().getTerritory(game.getNextPlayer().getKingdom().getTerritories().size()-1);

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		// Kingom kingdom = player.getKingdom();
		DominoInKingdom dik = (DominoInKingdom) game.getNextPlayer().getKingdom()
				.getTerritory(game.getNextPlayer().getKingdom().getTerritories().size() - 1);
		int id = int1;
		int posx = int2;
		int posy = int3;
		assertEquals(posx, dik.getX());
		assertEquals(posy, dik.getY());
		assertEquals(dik.getDomino().getId(), id);
		Player curplayer = null;
		for (Player player : game.getPlayers()) {
			if (player.getColor().name().equalsIgnoreCase(string)) {
				curplayer = player;
			}
		}

		assertEquals(curplayer.getDominoSelection().getDomino().getStatus(), dik.getDomino().getStatus());
		/*
		 * int posx = int2; int posy = int3;
		 * 
		 * for(KingdomTerritory territory: player.getKingdom().getTerritories()) {
		 * if(territory instanceof DominoInKingdom) { assertEquals(((DominoInKingdom)
		 * territory).getX(),posx); assertEquals(((DominoInKingdom)
		 * territory).getY(),posy); } }
		 */
	}

	@When("{string} requests to move the domino {string}")
	public void requests_to_move_the_domino(String string, String string2) {
		// Write code here that turns the phrase above into concrete actions
		PlayerColor color = null;
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
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = KingdominoController.getPlayerByColor(color, game);

		DirectionKind dk = null;
		if (string2.trim().equals("left")) {
			dk = DirectionKind.Left;
		} else if (string2.trim().equals("right")) {
			dk = DirectionKind.Right;
		} else if (string2.trim().equals("up")) {
			dk = DirectionKind.Up;
		} else if (string2.trim().equals("down")) {
			dk = DirectionKind.Down;
		}

		KingdominoController.moveDomino(string2);

	}

	@Then("the new status of the domino is {string}")
	public void the_new_status_of_the_domino_is(String string) {
		// Write code here that turns the phrase above into concrete actions
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();

		assertEquals(getDominoStatus(string), tentativedik.getDomino().getStatus());
	}

	@Then("the domino {int} is still tentatively placed at position {int}:{int}")
	public void the_domino_is_still_tentatively_placed_at_position(Integer int1, Integer int2, Integer int3) {
		// Write code here that turns the phrase above into concrete actions
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		// Kingom kingdom = player.getKingdom();
		DominoInKingdom dik = (DominoInKingdom) game.getNextPlayer().getKingdom()
				.getTerritory(game.getNextPlayer().getKingdom().getTerritories().size() - 1);
		int id = int1;
		int posx = int2;
		int posy = int3;
		assertEquals(posx, dik.getX());
		assertEquals(posy, dik.getY());
		assertEquals(dik.getDomino().getId(), id);
	}

	@Then("the domino {int} should be tentatively placed at position {int}:{int} with direction {string}")
	public void the_domino_should_be_tentatively_placed_at_position_with_direction(Integer int1, Integer int2,
			Integer int3, String string) {
		// Write code here that turns the phrase above into concrete actions
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getNextPlayer();

		for (KingdomTerritory territory : player.getKingdom().getTerritories()) {
			if (territory instanceof DominoInKingdom) {
				DominoInKingdom dk = (DominoInKingdom) territory;
				if (dk.getDomino().getId() == int1) {
					tentativedik = dk;
				}

			}
		}

		assertEquals(int2.intValue(), tentativedik.getX());
		assertEquals(int3.intValue(), tentativedik.getY());

	}

	@Then("the domino should still have status {string}")
	public void the_domino_should_still_have_status(String string) {
		// Write code here that turns the phrase above into concrete actions

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		DominoInKingdom dik = (DominoInKingdom) game.getNextPlayer().getKingdom()
				.getTerritory(game.getNextPlayer().getKingdom().getTerritories().size() - 1);

		assertEquals(dik.getDomino().getStatus().name(), string);
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
		case "ErroneouslyPreplaced":
			return DominoStatus.ErroneouslyPreplaced;
		case "CorrectlyPreplaced":
			return DominoStatus.CorrectlyPreplaced;
		case "placedInKingdom":
			return DominoStatus.PlacedInKingdom;
		case "discarded":
			return DominoStatus.Discarded;
		default:
			throw new java.lang.IllegalArgumentException("Invalid domino status: " + status);
		}
	}
}
