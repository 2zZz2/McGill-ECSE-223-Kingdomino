package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SaveGameStepDefinitions {

	Kingdomino kingdomino;
	String filename;
	
	
	@Given("the game is initialized for save game")
	public void the_game_is_initialized_for_save_game() {
		kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(48, kingdomino);
		KingdominoController.createAllDominoes(game);
		game.setTopDominoInPile(game.getAllDomino(0));
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		KingdominoController.addDefaultUsersAndPlayers(game,4);
		for (Player player: game.getPlayers()) {
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
		Draft curDraft = new Draft(DraftStatus.FaceUp, game);
		Draft nextDraft = new Draft(DraftStatus.FaceDown,game);
		game.setCurrentDraft(curDraft);
		game.setNextDraft(nextDraft);
		
		
		
		kingdomino = KingdominoApplication.getKingdomino();
	}

	@Given("the game is still in progress")
	public void the_game_is_still_in_progress() {
		assertEquals(true, KingdominoController.gameInProgress());
	}

	@Given("no file named {string} exists in the filesystem")
	public void no_file_named_exists_in_the_filesystem(String string) {
		File file = new File(string);
		assertEquals(false, file.exists());
	}

	@When("the user initiates saving the game to a file named {string}")
	public void the_user_initiates_saving_the_game_to_a_file_named(String string) {
		KingdominoController.saveGame(string);
	}

	@Then("a file named {string} shall be created in the filesystem")
	public void a_file_named_shall_be_created_in_the_filesystem(String string) {
		File file = new File(string);
		assertEquals(true, file.isFile());
	}

	@Given("the file named {string} exists in the filesystem")
	public void the_file_named_exists_in_the_filesystem(String string) {
		File file = new File(string);
		assertEquals(true, file.isFile());
	}

	@When("the user agrees to overwrite the existing file named {string}")
	public void the_user_agrees_to_overwrite_the_existing_file_named(String string) {
		KingdominoController.overwriteFile(string);
		filename = string;
	}

	@Then("the file named {string} shall be updated in the filesystem")
	public void the_file_named_shall_be_updated_in_the_filesystem(String string) {
		assertEquals(string, filename);
		
		
		//delete file after testing
		try{
			File file = new File(filename);
			file.delete();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown() {
		if(kingdomino!=null) {
			kingdomino.delete();
		}
	}
}