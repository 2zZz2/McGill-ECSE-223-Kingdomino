package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.*;
/**this is the class that describes the step definitions of browse domino
 * 
 * @author weijiahuang
 */
public class BrowseDominoPileDefinitions {
	
	private Domino dominoSelected;
	private ArrayList<Domino>selectedByTerrainType;
	
	@Given("the program is started and ready for browsing dominoes")
	public void the_program_is_started_and_ready_for_browsing_dominoes() {
		KingdominoController.startAndPopulateGame();
	}

	@When("I initiate the browsing of all dominoes")
	public void i_initiate_the_browsing_of_all_dominoes() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		
	}

	@Then("all the dominoes are listed in increasing order of identifiers")
	public void all_the_dominoes_are_listed_in_increasing_order_of_identifiers() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (int i = 0; i < 47; i++) {
			if(game.getAllDomino(i).getId()>=game.getAllDomino(i+1).getId())
				fail();
		}
	}

	@When("I provide a domino ID {int}")
	public void i_provide_a_domino_ID(Integer int1) {
		dominoSelected = KingdominoController.getdominoByID(int1);
	}

	@Then("the listed domino has {string} left terrain")
	public void the_listed_domino_has_left_terrain(String string) {
		TerrainType expectedType = KingdominoController.getTerrainTypeFeatures(string);
		TerrainType actualType = dominoSelected.getLeftTile();
		assertEquals(expectedType,actualType);
	}

	@Then("the listed domino has {string} right terrain")
	public void the_listed_domino_has_right_terrain(String string) {
		TerrainType expectedType = KingdominoController.getTerrainTypeFeatures(string);
		TerrainType actualType = dominoSelected.getRightTile();
		assertEquals(expectedType,actualType);
		//throw new cucumber.api.PendingException();
	}

	@Then("the listed domino has {int} crowns")
	public void the_listed_domino_has_crowns(Integer int1) {
		Integer actualCrowns = dominoSelected.getLeftCrown()+dominoSelected.getRightCrown();
		assertEquals(int1,actualCrowns);
	}

	@When("I initiate the browsing of all dominoes of {string} terrain type")
	public void i_initiate_the_browsing_of_all_dominoes_of_terrain_type(String string) {
		selectedByTerrainType = KingdominoController.getDominoByTerrainType(string);
	}

	@Then("list of dominoes with IDs {string} should be shown")
	public void list_of_dominoes_with_IDs_should_be_shown(String string) {
		String ids = "";
		for(Domino d:selectedByTerrainType) {
			ids = ids+d.getId();
			if(selectedByTerrainType.indexOf(d)!=(selectedByTerrainType.size()-1)) {
				ids = ids+",";
			}
		}
		assertEquals(string,ids);
	}
	
	@After
	public void tearDown() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		if (kingdomino != null) {
			kingdomino.delete();
		}
	}
	
}
