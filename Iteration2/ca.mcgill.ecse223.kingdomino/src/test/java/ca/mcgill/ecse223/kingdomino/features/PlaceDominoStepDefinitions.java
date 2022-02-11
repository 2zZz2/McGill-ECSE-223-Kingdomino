package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PlaceDominoStepDefinitions {
	DominoInKingdom tentativedik;
	
	@Given("the game is initialized for move current domino")
	public void the_game_is_initialized_for_move_current_domino() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		KingdominoController.addDefaultUsersAndPlayers(game,4);
		for (Player player: game.getPlayers()) {
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
		KingdominoController.createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
		Draft draft = new Draft(DraftStatus.FaceUp,game);
		game.setCurrentDraft(draft);
	}
	
	@Given("it is {string}'s turn")
	public void it_is_s_turn(String string) {
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
		game.setNextPlayer(KingdominoController.getPlayerByColor(color, game));
	}
	
	@Given("the {string}'s kingdom has the following dominoes:")
	public void the_s_kingdom_has_the_following_dominoes(String string, io.cucumber.datatable.DataTable dataTable) {
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
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			DirectionKind direction = null;
			int dominoid = Integer.decode(map.get("domino"));
			String dominodir = map.get("domino");
			if (dominodir.trim().toLowerCase().equals("right")) {
				direction = DirectionKind.Right;
			}
			if (dominodir.trim().toLowerCase().equals("left")) {
				direction = DirectionKind.Left;
			}
			if (dominodir.trim().toLowerCase().equals("up")) {
				direction = DirectionKind.Up;
			}
			if (dominodir.trim().toLowerCase().equals("down")) {
				direction = DirectionKind.Down;
			}
			
			int posx = Integer.decode(map.get("posx"));
			int posy = Integer.decode(map.get("posy"));	
			Domino domino = KingdominoController.getdominoByID(dominoid, game);
			domino.setStatus(DominoStatus.PlacedInKingdom);
			DominoInKingdom dik = new DominoInKingdom(posx, posy, player.getKingdom(), domino);
			dik.setDirection(direction);
			game.getNextPlayer().getKingdom().addTerritory(dik);
			
		}
	}
	
	@Given("{string} has selected domino {int}")
	public void has_selected_domino(String string, Integer int1) {
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
		game.setNextPlayer(player);
		Domino selectedomino = KingdominoController.getdominoByID(int1, game);
		
		//Set the selection/current draft
		game.getCurrentDraft().addIdSortedDomino(KingdominoController.getdominoByID(int1));
		KingdominoController.getdominoByID(int1).setStatus(DominoStatus.InCurrentDraft);
		KingdominoController.order(game.getCurrentDraft());
		new DominoSelection(game.getNextPlayer(), KingdominoController.getdominoByID(int1), game.getCurrentDraft());
		game.getCurrentDraft().addIdSortedDomino(KingdominoController.getdominoByID(int1));
		
	}
	
	@Given("domino {int} is tentatively placed at position {int}:{int} with direction {string}")
	public void domino_is_tentatively_placed_at_position_with_direction(Integer int1, Integer int2, Integer int3, String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getNextPlayer();
		DirectionKind direction = null;
		if (string.trim().toLowerCase().equals("right")) {
			direction = DirectionKind.Right;
		}
		if (string.trim().toLowerCase().equals("left")) {
			direction = DirectionKind.Left;
		}
		if (string.trim().toLowerCase().equals("up")) {
			direction = DirectionKind.Up;
		}
		if (string.trim().toLowerCase().equals("down")) {
			direction = DirectionKind.Down;
		}
		Domino domino = KingdominoController.getdominoByID(int1, game);
		tentativedik = new DominoInKingdom(int2, int3, player.getKingdom(), domino);
		tentativedik.setDirection(direction);
	}
	
	@Given("domino {int} is in {string} status")
	public void domino_is_in_status(Integer int1, String string) {
		DominoStatus status = null;
		
		if (string.trim().equals("Excluded")) {
			status=DominoStatus.Excluded;
		}
		if (string.trim().equals("Inpile")) {
			status=DominoStatus.InPile;
		}
		if (string.trim().equals("InNextDraft")) {
			status=DominoStatus.InNextDraft;
		}
		if (string.trim().equals("InCurrentDraft")) {
			status=DominoStatus.InCurrentDraft;
		}
		if (string.trim().equals("CorrectlyPreplaced")) {
			status=DominoStatus.CorrectlyPreplaced;
		}
		if (string.trim().equals("ErroneouslyPreplaced")) {
			status=DominoStatus.ErroneouslyPreplaced;
		}
		if (string.trim().equals("PlacedInKingdom")) {
			status=DominoStatus.PlacedInKingdom;
		}
		if (string.trim().equals("Discarded")) {
			status=DominoStatus.Discarded;
		}
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino domino = KingdominoController.getdominoByID(int1, game);
		domino.setStatus(status);
	}
	
	@When("{string} requests to place the selected domino {int}")
	public void requests_to_place_the_selected_domino(String string, Integer int1) {
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
		KingdominoController.placeSelectedDomino(tentativedik, player);
		
	}
	
	@Then("{string}'s kingdom should now have domino {int} at position {int}:{int} with direction {string}")
	public void s_kingdom_should_now_have_domino_at_position_with_direction(String string, Integer int1, Integer int2, Integer int3, String string2) {
		boolean dominofound = false;
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
		int dominoid = int1;
		int posx = int2;
		int posy = int3;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = KingdominoController.getPlayerByColor(color, game);
		for(KingdomTerritory territory:player.getKingdom().getTerritories()) {
			if(territory instanceof DominoInKingdom) {
				if (((DominoInKingdom) territory).getDomino().getId()==dominoid) {
					dominofound = true;
					assertEquals(((DominoInKingdom) territory).getX(),posx);
					assertEquals(((DominoInKingdom) territory).getY(),posy);
					assertEquals(((DominoInKingdom) territory).getDirection().toString().toLowerCase(),string2.trim());
				}
			}
		}
		assertTrue(dominofound);
		
	}
}
