package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SelectingFirstDominoStepDefinitions {
	Gameplay gameplay = KingdominoApplication.getGameplay();
	public static boolean select = false;

	@Given("the game has been initialized for selecting first domino")
	public void the_game_has_been_initialized_for_selecting_first_domino() {
		Kingdomino kingdomino = new Kingdomino();
		KingdominoApplication.setKingdomino(kingdomino);
		Game game = new Game(48, kingdomino);
		kingdomino.setCurrentGame(game);
		KingdominoController.addDefaultUsersAndPlayers(game, 4);
		KingdominoController.createCastles(game);
		KingdominoController.createAllDominoes(game);
		gameplay.setGamestatus("SelectingFirstDomino");
		select = true;
	}

	@Given("the initial order of players is {string}")
	public void the_initial_order_of_players_is(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
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

	@Given("the current draft has the dominoes with ID {string}")
	public void the_current_draft_has_the_dominoes_with_ID(String string) {
		String[] dominos = string.split(",");
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft draft = new Draft(DraftStatus.FaceUp, game);
		game.setCurrentDraft(draft);
		for (int i = 0; i < dominos.length; i++) {
			game.getCurrentDraft().addIdSortedDomino(KingdominoController.getdominoByID(Integer.decode(dominos[i])));
			KingdominoController.getdominoByID(Integer.decode(dominos[i])).setStatus(DominoStatus.InCurrentDraft);
		}
	}

	@Given("the {string} player is selecting his\\/her domino with ID {int}")
	public void the_player_is_selecting_his_her_domino_with_ID(String string, Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		int index = 0;
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			if (game.getPlayer(i).getColor().name().equalsIgnoreCase(string)) {
				game.setNextPlayer(game.getPlayer(i));
				index = i;
			}
		}
		for (int i = 0; i < KingdominoController.initialorder.size(); i++) {
			if (KingdominoController.initialorder.get(i) == index) {
				KingdominoController.nextorder = i + 1;
			}
		}
		KingdominoController.placeKingOnDomino(int1, getPlayerFromString(string), game.getCurrentDraft());
	}

	@Then("the {string} player shall be selecting his\\/her domino")
	public void the_player_shall_be_selecting_his_her_domino(String string) {
		assertEquals(string.trim(), KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer().getColor()
				.toString().toLowerCase());
	}

	@Given("the {string} player is selecting his\\/her first domino with ID {int}")
	public void the_player_is_selecting_his_her_first_domino_with_ID(String string, Integer int1) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		game.setNextPlayer(getPlayerFromString(string));
		KingdominoController.placeKingOnDomino(int1, getPlayerFromString(string), game.getCurrentDraft());
	}

	@When("the {string} player completes his\\/her domino selection of the game")
	public void the_player_completes_his_her_domino_selection_of_the_game(String string) {
		KingdominoApplication.getGameplay().selectedFirst();
	}

	@Then("a new draft shall be available, face down")
	public void a_new_draft_shall_be_available_face_down() {
		assertEquals(KingdominoApplication.getGameplay().getGamestatus(), Gameplay.Gamestatus.Ongoing);
	}

	private Player getPlayerFromString(String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Player player : game.getPlayers()) {
			if (player.getColor().name().equalsIgnoreCase(string)) {
				return player;
			}
		}
		return null;
	}

}