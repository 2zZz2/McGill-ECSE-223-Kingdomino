package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.Property;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

public class IdentifyPropertiesStepDefinitions {
	List<Property> receivedProperty= new ArrayList<Property>();
	List<String> receivedList = new ArrayList<String>();
	
	List<String> receivedTypeList = new ArrayList<String>();
	
	
	 @Given("the game is initialized for identify properties")
	    public void the_game_is_initialized_for_identify_properties() {
		// Intialize empty game
			Kingdomino kingdomino = new Kingdomino();
			Game game = new Game(48, kingdomino);
			game.setNumberOfPlayers(4);
			kingdomino.setCurrentGame(game);
		//populate game
			addDefaultUsersAndPlayers(game);//adds the users and player classes to this particular game
			createAllDominoes(game); //add all the dominoes used to play => helper class
			game.setNextPlayer(game.getPlayer(0)); //sets first player!!
			KingdominoApplication.setKingdomino(kingdomino); //update the kingdominoGame to the kingDominoApp
	   	
		 
	        // Write code here that turns the phrase above into concrete actions
	       // throw new cucumber.api.PendingException();
	    }
	
          
	 @When("the properties of the player are identified")
	    public void the_properties_of_the_player_are_identified() {
		 
		  receivedProperty = KingdominoController.identifyProperty(KingdominoApplication.getKingdomino().
				 getCurrentGame().getPlayer(0).getKingdom());
		  
		  for(Property aProperty : receivedProperty) {
			  List<Integer> tempList = new ArrayList<Integer>();
			  for(Domino aDomino : aProperty.getIncludedDominos()) {
				  tempList.add(aDomino.getId());
			     Collections.sort(tempList);
			  }
			  String res="";
			  for(int i=0;i<tempList.size();i++) {
			  res+=(tempList.get(i)+",");
			  }
			  if(res!=null && res.length()>0) {
			  res=res.substring(0,res.length()-1);
			  receivedList.add(res);
			  }
			  
			  if(aProperty.getPropertyType()!=null) {
			  receivedTypeList.add(aProperty.getPropertyType().toString());
			  }
		  }
			
	        // Write code here that turns the phrase above into concrete actions
	       // throw new cucumber.api.PendingException();
	    }

	 @Then("the player shall have the following properties:")
	    public void the_player_shall_have_the_following_properties(io.cucumber.datatable.DataTable dataTable) {
	        // Write code here that turns the phrase above into concrete actions
	        // For automatic transformation, change DataTable to one of
	        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	        // Double, Byte, Short, Long, BigInteger or BigDecimal.
	        //
	        // For other transformations you can register a DataTableType.
		 List<String> typeList=new ArrayList<String>();
		 List<String> dominoList =new ArrayList<String>();
			List<Map<String, String>> valueMaps = dataTable.asMaps();
			for (Map<String, String> map : valueMaps) {
				// Get values from cucumber table
				String type = (map.get("type")).trim();
				typeList.add(getTerrainTypeFull(type).toString());
				List<Integer> dList = new ArrayList<Integer>();
				String[] dArray =(map.get("dominoes")).trim().split(","); 
				for(int i =0;i<dArray.length;i++) {
				    dList.add(Integer.parseInt(dArray[i]));
				}
				Collections.sort(dList);
				 String res="";
				  for(int i=0;i<dList.size();i++) {
				  res+=(dList.get(i)+",");
				  }
				  if(res!=null && res.length()>0) {
				  res=res.substring(0,res.length()-1);
				dominoList.add(res);
				  }
			}
			Collections.sort(typeList);
			Collections.sort(receivedTypeList);
			Collections.sort(dominoList);
			Collections.sort(receivedList);
		assertEquals(typeList, receivedTypeList);
		assertEquals(dominoList, receivedList);
		 
	        //throw new cucumber.api.PendingException();
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