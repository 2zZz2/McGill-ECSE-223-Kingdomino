package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
/**
 * this class describes the step definitions of verify castle adjacency
 * @author weijiahuang
 *
 */
public class VerifyCastleAdjacencyDefinitions {
	Boolean status;
	@Given("the game is initialized for castle adjacency")
	public void the_game_is_initialized_for_castle_adjacency() {
		KingdominoController.startAndPopulateGame();
	}

	@Given("the current player preplaced the domino with ID {int} at position {int}:{int} and direction {string}")
	public void the_current_player_preplaced_the_domino_with_ID_at_position_and_direction(Integer int1, Integer int2, Integer int3, String string) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino domino = KingdominoController.getdominoByID(int1);
		Player player = game.getPlayer(0);
		Kingdom kingdom = player.getKingdom();
		DominoInKingdom newDomino = new DominoInKingdom(int2,int3,kingdom,domino);
		DirectionKind direction = KingdominoController.getDirection(string);
		newDomino.setDirection(direction);
		kingdom.addTerritory(newDomino);
	}

	@When("check castle adjacency is initiated")
	public void check_castle_adjacency_is_initiated() {
		DominoInKingdom lastDomino = KingdominoController.getLastDomino();
		Kingdom kingdom = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0).getKingdom();
		status = KingdominoController.satisfyCastleAdjacency(lastDomino,kingdom);
	}

	@Then("the castle\\/domino adjacency is {string}")
	public void the_castle_domino_adjacency_is(String string) {
		String actualAdjacency = "";
		if(status)
			actualAdjacency = "valid";
		else actualAdjacency = "invalid";
		assertEquals(actualAdjacency,string);
	}
	
	@After
	public void tearDown() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		if (kingdomino != null) {
			kingdomino.delete();
		}
	}
}
