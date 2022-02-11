package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class VerifyNoOverlappingStepDefinitions {
	Boolean isValid;
	DominoInKingdom myTerritory;
	 @Given("the game is initialized to check domino overlapping")
	    public void the_game_is_initialized_to_check_domino_overlapping() {
	        // Write code here that turns the phrase above into concrete actions
	        //throw new cucumber.api.PendingException();
		 // Initialize empty game
			Kingdomino kingdomino = new Kingdomino();
			Game game = new Game(48, kingdomino);
			game.setNumberOfPlayers(4);
			kingdomino.setCurrentGame(game);
		//populate game
			addDefaultUsersAndPlayers(game);//adds the users and player classes to this particular game
			createAllDominoes(game); //add all the dominoes used to play => helper class
			game.setNextPlayer(game.getPlayer(0)); //sets first player!!
			KingdominoApplication.setKingdomino(kingdomino); //update the kingdominoGame to the kingDominoApp
	    }

	    @When("check current preplaced domino overlapping is initiated")
	    public void check_current_preplaced_domino_overlapping_is_initiated() {
	        // Write code here that turns the phrase above into concrete actions
	        //throw new cucumber.api.PendingException();
	    	DominoInKingdom lastDomino = KingdominoController.getLastDomino();
	    	Kingdom kingdom = KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer().getKingdom();
			isValid = KingdominoController.noOverlapping(lastDomino, kingdom);
	    }

	    @Then("the current-domino\\/existing-domino overlapping is {string}")
	    public void the_current_domino_existing_domino_overlapping_is(String string) {
	        // Write code here that turns the phrase above into concrete actions
	        //throw new cucumber.api.PendingException();
	    	String result;
	    	if(isValid) {
	    		result = "valid";
	    	
	    	}else {
	    		result ="invalid";
	    	}
	    	assertEquals(result,string);
	    
	    }

	    
	    
	    ////////////////////////////////////
        ////// private helper methods ///////
        ////////////////////////////////////


	   
	    
	    private void addDefaultUsersAndPlayers(Game game) {
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

	
		
		private void createAllDominoes(Game game) {
			try {
				BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
				String line = "";
				String delimiters = "[:\\+()]";
				while ((line = br.readLine()) != null) {
					String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
					int dominoId = Integer.decode(dominoString[0]);
					TerrainType leftTerrain = getTerrainType(dominoString[1]);
					TerrainType rightTerrain = getTerrainType(dominoString[2]);
					int numCrown = 0;
					if (dominoString.length > 3) {
						numCrown = Integer.decode(dominoString[3]);
					}
					new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new java.lang.IllegalArgumentException(
						"Error occured while trying to read alldominoes.dat: " + e.getMessage());
			}
		}

		private Domino getdominoByID(int id) {
			Game game = KingdominoApplication.getKingdomino().getCurrentGame();
			for (Domino domino : game.getAllDominos()) {
				if (domino.getId() == id) {
					return domino;
				}
			}
			throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
		}

		private TerrainType getTerrainType(String terrain) {
			switch (terrain) {
			case "W":
				return TerrainType.WheatField;
			case "F":
				return TerrainType.Forest;
			case "M":
				return TerrainType.Mountain;
			case "G":
				return TerrainType.Grass;
			case "S":
				return TerrainType.Swamp;
			case "L":
				return TerrainType.Lake;
			default:
				throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
			}
		}

		private DirectionKind getDirection(String dir) {
			switch (dir) {
			case "up":
				return DirectionKind.Up;
			case "down":
				return DirectionKind.Down;
			case "left":
				return DirectionKind.Left;
			case "right":
				return DirectionKind.Right;
			default:
				throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
			}
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
			case "erroneouslyPreplaced":
				return DominoStatus.ErroneouslyPreplaced;
			case "correctlyPreplaced":
				return DominoStatus.CorrectlyPreplaced;
			case "placedInKingdom":
				return DominoStatus.PlacedInKingdom;
			case "discarded":
				return DominoStatus.Discarded;
			default:
				throw new java.lang.IllegalArgumentException("Invalid domino status: " + status);
			}
		}
		
		private TerrainType getTerrainTypeFull(String terrain) {
			switch (terrain) {
			case "wheat":
				return TerrainType.WheatField;
			case "forest":
				return TerrainType.Forest;
			case "mountain":
				return TerrainType.Mountain;
			case "grass":
				return TerrainType.Grass;
			case "swamp":
				return TerrainType.Swamp;
			case "lake":
				return TerrainType.Lake;
			default:
				throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
			}
		}



}