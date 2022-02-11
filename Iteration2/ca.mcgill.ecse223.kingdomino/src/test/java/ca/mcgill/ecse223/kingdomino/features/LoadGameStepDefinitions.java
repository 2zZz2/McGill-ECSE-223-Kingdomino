package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoadGameStepDefinitions {

	Kingdomino kingdomino;
	Game game;
	Draft curDraft;
	Draft nextDraft;
	String fileStatus;

	@Given("the game is initialized for load game")
	public void the_game_is_initialized_for_load_game() {
		kingdomino = KingdominoApplication.getKingdomino();
	}

	@When("I initiate loading a saved game from {string}")
	public void i_initiate_loading_a_saved_game_from(String string) {
		fileStatus = KingdominoController.loadGame(string);
		game = kingdomino.getCurrentGame();
		curDraft = game.getCurrentDraft();
		nextDraft = game.getNextDraft();
	}

	@When("each tile placement is valid")
	public void each_tile_placement_is_valid() {
		for (Player player : kingdomino.getCurrentGame().getPlayers()) {
			for (KingdomTerritory territory : player.getKingdom().getTerritories()) {
				if (territory instanceof DominoInKingdom) {
					DominoInKingdom domino = (DominoInKingdom) territory;
					assertEquals(true, KingdominoController.satisfyGridSize(domino, player.getKingdom()));

					// Verify overlap
					for (KingdomTerritory territories : player.getKingdom().getTerritories()) {
						if (territories instanceof DominoInKingdom) {
							DominoInKingdom dominoKingdom = (DominoInKingdom) territories;
							if (!dominoKingdom.equals(domino)) {
								assertEquals(true, KingdominoController.doesDominoOverlap(dominoKingdom, domino.getX(),
										domino.getY(), domino.getDirection()));
							}

						}

					}
				}
			}
		}

	}

	@When("the game result is not yet final")
	public void the_game_result_is_not_yet_final() {
		assertEquals(true, KingdominoController.gameInProgress());
	}

	@Then("it shall be player {int}'s turn")
	public void it_shall_be_player_s_turn(Integer int1) {
		int pTurn = 0;
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			if (game.getNextPlayer().equals(game.getPlayer(i))) {
				pTurn = i + 1;
			}
		}

		assertEquals(int1, pTurn);
	}

	@Then("each of the players should have the corresponding tiles on their grid:")
	public void each_of_the_players_should_have_the_corresponding_tiles_on_their_grid(
			io.cucumber.datatable.DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.

		String p1Tiles;
		String p2Tiles;
		String p3Tiles;
		String p4Tiles;

		List<String> rows = dataTable.asList();
		p1Tiles = rows.get(3);
		p2Tiles = rows.get(5);
		p3Tiles = rows.get(7);
		p4Tiles = rows.get(9);

		List<String> allPTiles = new ArrayList<>();
		allPTiles.add(p1Tiles);
		allPTiles.add(p2Tiles);
		allPTiles.add(p3Tiles);
		allPTiles.add(p4Tiles);
		int counter = 0;
		StringJoiner territoryJoiner = null;
		for (Player player : game.getPlayers()) {
			territoryJoiner = new StringJoiner(",");
			for (KingdomTerritory territory : player.getKingdom().getTerritories()) {
				if (territory instanceof DominoInKingdom) {
					DominoInKingdom dominoInKingdom = (DominoInKingdom) territory;
					territoryJoiner.add(dominoInKingdom.getDomino().getId() + "");
				}
			}
			assertEquals(allPTiles.get(counter), territoryJoiner.toString());
			counter++;
		}
	}

	@Then("each of the players should have claimed the corresponding tiles:")
	public void each_of_the_players_should_have_claimed_the_corresponding_tiles(
			io.cucumber.datatable.DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.

		List<String> rows = dataTable.asList();
		String p1Claimed = rows.get(3);
		String p2Claimed = rows.get(5);
		String p3Claimed = rows.get(7);
		String p4Claimed = rows.get(9);
		List<String> claimedTiles = new ArrayList<>();
		claimedTiles.add(p1Claimed);
		claimedTiles.add(p2Claimed);
		claimedTiles.add(p3Claimed);
		claimedTiles.add(p4Claimed);

		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			assertEquals(claimedTiles.get(i), game.getPlayer(i).getDominoSelection().getDomino().getId() + "");
		}
	}

	@Then("tiles {string} shall be unclaimed on the board")
	public void tiles_shall_be_unclaimed_on_the_board(String string) {
		StringJoiner compare = new StringJoiner(",");

		for (Domino domino : nextDraft.getIdSortedDominos()) {
			if (!domino.hasDominoSelection()) {
				compare.add(domino.getId() + "");
			}
		}

		assertEquals(string, compare.toString());
	}

	@Then("the game shall become ready to start")
	public void the_game_shall_become_ready_to_start() {
		assertEquals(true, KingdominoController.gameInProgress());
	}

	@Then("the game shall notify the user that the loaded game is invalid")
	public void the_game_shall_notify_the_user_that_the_loaded_game_is_invalid() {
		assertEquals("Invalid", fileStatus);
	}

	@After
	public void tearDown() {
		if (kingdomino != null) {
			kingdomino.delete();
		}

	}

}
