package ca.mcgill.ecse223.kingdomino.features;


import static org.junit.Assert.assertEquals;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;

public class SetGameOptionsStepDefinitions {


	 @Given("the game is initialized for set game options")
	    public void the_game_is_initialized_for_set_game_options() {
		 	Kingdomino kingdomino = new Kingdomino();
	    	KingdominoApplication.setKingdomino(kingdomino);
	    }

	    @When("set game options is initiated")
	    public void set_game_options_is_initiated() {
	    	Game game = new Game(48, KingdominoApplication.getKingdomino());
	    	KingdominoApplication.getKingdomino().setCurrentGame(game);
	    }

	    @When("the number of players is set to {int}")
	    public void the_number_of_players_is_set_to(Integer int1) {
	    	KingdominoApplication.getKingdomino().getCurrentGame().setNumberOfPlayers(int1);
	    }

	    @When("Harmony {string} selected as bonus option")
	    public void harmony_selected_as_bonus_option(String string) {
	    	KingdominoController.setGameOptions(KingdominoApplication.getKingdomino().getCurrentGame(), "Harmony");
	    }

	    @When("Middle Kingdom {string} selected as bonus option")
	    public void middle_Kingdom_selected_as_bonus_option(String string) {
	    	KingdominoController.setGameOptions(KingdominoApplication.getKingdomino().getCurrentGame(), "MiddleKingdom");
	    }

	    @Then("the number of players shall be {int}")
	    public void the_number_of_players_shall_be(Integer int1) {
	    	int numberofplayers = int1;
	    	assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers(), numberofplayers);
	    }

	    @Then("Harmony {string} an active bonus")
	    public void harmony_an_active_bonus(String string) {
	    	assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getSelectedBonusOption(0).getOptionName(), "Harmony");
	    
	    }

	    @Then("Middle Kingdom {string} an active bonus")
	    public void middle_Kingdom_an_active_bonus(String string) {
	    	assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getSelectedBonusOption(1).getOptionName(), "MiddleKingdom");
	    }
}
