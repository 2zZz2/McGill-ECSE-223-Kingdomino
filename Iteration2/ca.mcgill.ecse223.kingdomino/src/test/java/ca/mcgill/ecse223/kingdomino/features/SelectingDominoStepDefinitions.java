package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.GamestatusOngoing;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * 
 * @author Viet Tran
 *
 */
public class SelectingDominoStepDefinitions {
	public static boolean overwriteDraft = false;
	Game game;
	Gameplay gameplay;
	boolean selecting = false;

	@Given("the game has been initialized for selecting domino")
	public void the_game_has_been_initialized_for_selecting_domino() {
		// Initialize the game
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		KingdominoController.createAllDominoes(game);
		KingdominoApplication.getGameplay().setGamestatus("SelectingDomino");
		gameplay = KingdominoApplication.getGameplay();
		Draft curdraft = new Draft(DraftStatus.FaceUp, game);
		game.setNextDraft(curdraft);
		overwriteDraft = true;
		selecting = true;
	}

	@Given("the order of players is {string}")
	public void the_order_of_players_is(String string) {
		ArrayList<Integer> order = new ArrayList<>();
		String[] players = string.split(",");
		for (String string1 : players) {
			for (int i = 0; i < game.getNumberOfPlayers(); i++) {
				if (game.getPlayer(i).getColor().name().equalsIgnoreCase(string1)) {
					order.add(i);
				}
			}
		}
		KingdominoController.initialorder = order;
		Player player = game.getPlayer(KingdominoController.initialorder.get(0));
		game.setNextPlayer(player);
		KingdominoController.nextorder = 1;

	}

	@Given("the next draft has the dominoes with ID {string}")
	public void the_current_draft_has_the_dominoes_with_ID(String string) {
		String[] dominos = string.split(",");

		for (int i = 0; i < dominos.length; i++) {
			game.getNextDraft().addIdSortedDomino(KingdominoController.getdominoByID(Integer.decode(dominos[i])));
			KingdominoController.getdominoByID(Integer.decode(dominos[i])).setStatus(DominoStatus.InNextDraft);
		}

	}

	@Given("the {string} is selecting his\\/her domino with ID {int}")
	public void the_is_selecting_his_her_domino_with_ID(String string, Integer int1) {
		int index = 0;
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			if (game.getPlayer(i).getColor().name().equalsIgnoreCase(string)) {
				game.setNextPlayer(game.getPlayer(i));
				index = i;
			}
		}
		for(int i = 0; i < KingdominoController.initialorder.size(); i++) {
			if(KingdominoController.initialorder.get(i) == index) {
				KingdominoController.nextorder = i+1;
			}
		}
		
		
		KingdominoController.placeKingOnDomino(int1, getPlayerFromString(string), game.getNextDraft());
	}

	@And("the validation of domino selection returns {string}")
	public void the_validation_of_domino_selection_returns(String expectedValidationResultString) {
		gameplay = KingdominoApplication.getGameplay();
		boolean expectedValidationResult = true;
		if ("success".equalsIgnoreCase(expectedValidationResultString.trim())) {
			expectedValidationResult = true;
		} else if ("error".equalsIgnoreCase(expectedValidationResultString.trim())) {
			expectedValidationResult = false;
		} else {
			throw new IllegalArgumentException(
					"Unknown validation result string \"" + expectedValidationResultString + "\"");
		}
		boolean actualValidationResult = false;

		// TODO call here the guard function from the statemachine and store the result
		actualValidationResult = gameplay.isSelectionValid();

		// Check the precondition prescribed by the scenario
		assertEquals(expectedValidationResult, actualValidationResult);
	}

	@When("the {string} player completes his\\/her domino selection")
	public void the_player_completes_his_her_domino_selection(String string) {
		if (selecting) {
			game.setCurrentDraft(game.getNextDraft());
			KingdominoApplication.getGameplay().selected();
		} else {
			KingdominoApplication.getGameplay().selectedFirst();
		}

	}

	@Then("the {string} player shall be {string} his\\/her domino")
	public void the_player_shall_be_his_her_domino(String string, String string2) {
		// Write code here that turns the phrase above into concrete actions
		assertEquals(getPlayerFromString(string), game.getNextPlayer());
		assertEquals(getGameStatusOngoing(string2), gameplay.getGamestatusOngoing());
	}

	@Given("the {string} player is selecting his\\/her first domino of the game with ID {int}")
	public void the_player_is_selecting_his_her_first_domino_of_the_game_with_ID(String string, Integer int1) {
		game.setCurrentDraft(game.getNextDraft());
		game.setNextDraft(null);
		gameplay.setGamestatus("SelectingFirstDomino");
		game.setNextPlayer(getPlayerFromString(string));
		KingdominoController.placeKingOnDomino(int1, getPlayerFromString(string), game.getCurrentDraft());
	}

	@Then("a new draft shall be available")
	public void a_new_draft_shall_be_available() {
		assertTrue(game.getNextDraft() != null);
	}

	@Then("the draft shall be revealed")
	public void the_draft_shall_be_revealed() {
		assertEquals(DraftStatus.FaceUp, game.getNextDraft().getDraftStatus());
	}

	private static void addDefaultUsersAndPlayers(Game game) {
		String[] userNames = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < userNames.length; i++) {
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}

	private Player getPlayerFromString(String string) {
		for (Player player : game.getPlayers()) {
			if (player.getColor().name().equalsIgnoreCase(string)) {
				return player;
			}
		}
		return null;
	}

	private Gameplay.GamestatusOngoing getGameStatusOngoing(String string) {
		switch (string) {
		case "placing":
			return GamestatusOngoing.PreplacingDomino;
		case "selecting":
			return GamestatusOngoing.SelectingDomino;
		default:
			return null;
		}
	}

}