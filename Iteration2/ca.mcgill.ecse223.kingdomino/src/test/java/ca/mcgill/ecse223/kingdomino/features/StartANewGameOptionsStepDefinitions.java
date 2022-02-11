package ca.mcgill.ecse223.kingdomino.features;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class StartANewGameOptionsStepDefinitions {
	
	  @Given("the program is started and ready for starting a new game") 
	    public void the_program_is_started_and_ready_for_starting_a_new_game() {
	    	Kingdomino kingdomino = new Kingdomino();
	    	KingdominoApplication.setKingdomino(kingdomino);
	    	Game game = new Game(48, kingdomino);
	    	kingdomino.setCurrentGame(game);
	    }

	    @Given("there are four selected players")
	    public void there_are_four_selected_players() {
	    	Game game = KingdominoApplication.getKingdomino().getCurrentGame();
			game.setNumberOfPlayers(4);
			addDefaultUsersAndPlayers(game,4);
			game.setNextPlayer(game.getPlayer(0));
	    	
	    }

	    @Given("bonus options Harmony and MiddleKingdom are selected")
	    public void bonus_options_Harmony_and_MiddleKingdom_are_selected() {
	    	BonusOption bonusoption1 = new BonusOption("Harmony", KingdominoApplication.getKingdomino());
	    	BonusOption bonusoption2 = new BonusOption("MiddleKingdom", KingdominoApplication.getKingdomino());
	    	KingdominoApplication.getKingdomino().getCurrentGame().addSelectedBonusOption(bonusoption1);
	    	KingdominoApplication.getKingdomino().getCurrentGame().addSelectedBonusOption(bonusoption2);
	    }

	    @When("starting a new game is initiated")
	    public void starting_a_new_game_is_initiated() {
	        try{
	        	KingdominoController.startANewGame(KingdominoApplication.getKingdomino().getCurrentGame());
	        }catch(InvalidInputException e){
	        	System.out.println("Unable to start game due to wrong number of players added!");
	        }
	    }

	    @When("reveal first draft is initiated")
	    public void reveal_first_draft_is_initiated() {
	        try {
				KingdominoController.revealDraft(KingdominoApplication.getKingdomino().getCurrentGame(),1);
			} catch (Exception e) {
				System.out.println("Reveal draft failure");
			}
	    }

	    @Then("all kingdoms shall be initialized with a single castle")
	    public void all_kingdoms_shall_be_initialized_with_a_single_castle() {
	    	for (Player player : KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
	    		assertEquals(player.getKingdom().getTerritories().size(),1);
	    		assertTrue(player.getKingdom().getTerritory(0) instanceof Castle);
	    	}
	    }

	    @Then("all castle are placed at {int}:{int} in their respective kingdoms")
	    public void all_castle_are_placed_at_in_their_respective_kingdoms(Integer int1, Integer int2) {
	    	for (Player player : KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
	    		assertEquals(player.getKingdom().getTerritory(0).getX(),0);
	    		assertEquals(player.getKingdom().getTerritory(0).getY(),0);
	    	}
	    }

	    @Then("the first draft of dominoes is revealed")
	    public void the_first_draft_of_dominoes_is_revealed() {
	    	assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getAllDraft(0).getDraftStatus(),Draft.DraftStatus.FaceUp);
	    }

	    @Then("all the dominoes form the first draft are facing up")
	    public void all_the_dominoes_form_the_first_draft_are_facing_up() {
	    	assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getAllDraft(0).getDraftStatus(),Draft.DraftStatus.FaceUp);
	    }

	    @Then("all the players have no properties")
	    public void all_the_players_have_no_properties() {
	    	for (Player player : KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
	    		assertEquals(player.getKingdom().getProperties().size(),0);
	    	}
	    }

	    @Then("all player scores are initialized to zero")
	    public void all_player_scores_are_initialized_to_zero() {
	    	for (Player player : KingdominoApplication.getKingdomino().getCurrentGame().getPlayers()) {
	    		assertEquals(player.getTotalScore(),0);
	    	}
	    }

	    
		
		private boolean addDefaultUsersAndPlayers(Game game, int numberofplayers) {
			if (numberofplayers==4) {
				String[] userNames = { "User1", "User2", "User3", "User4" };
				for (int i = 0; i < userNames.length; i++) {
					User user = game.getKingdomino().addUser(userNames[i]);
					Player player = new Player(game);
					player.setUser(user);
					player.setColor(PlayerColor.values()[i]);
				}
			}else if (numberofplayers==3) {
				String[] userNames = { "User1", "User2", "User3" };
				for (int i = 0; i < userNames.length; i++) {
					User user = game.getKingdomino().addUser(userNames[i]);
					Player player = new Player(game);
					player.setUser(user);
					player.setColor(PlayerColor.values()[i]);
				}
			}else if (numberofplayers==2) {
				String[] userNames = { "User1", "User2" };
				for (int i = 0; i < userNames.length; i++) {
					User user = game.getKingdomino().addUser(userNames[i]);
					Player player = new Player(game);
					player.setUser(user);
					player.setColor(PlayerColor.values()[i]);
				}
			}else {
				return false;
			}
			return true;
		}
}
