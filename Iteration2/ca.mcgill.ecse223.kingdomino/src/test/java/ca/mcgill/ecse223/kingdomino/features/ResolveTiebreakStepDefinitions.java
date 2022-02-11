package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class ResolveTiebreakStepDefinitions {
	

	Kingdomino kingdomino;
	Game game;

	
	@Given("the game is initialized for resolve tiebreak")
	public void the_game_is_initialized_for_resolve_tiebreak() {
	    // Write code here that turns the phrase above into concrete actions
			kingdomino = new Kingdomino();
			KingdominoApplication.setKingdomino(kingdomino);
			game = new Game(48, kingdomino);
			kingdomino.setCurrentGame(game);
			createAllDominoes(game);
			KingdominoApplication.setKingdomino(kingdomino);
			
			
			for(int i =0; i < game.getNumberOfPlayers(); i++) {
                Player player = new Player(game);
                new Kingdom(player);
            }	


		
	}
	
	@Given("the players have the following two dominoes in their respective kingdoms:")
	public void the_players_have_the_following_two_dominoes_in_their_respective_kingdoms(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
		
		Player player = null;
		Kingdom kingdom;
		PlayerColor pcolor = null;
		int counter = 0;
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			// each iteration is one player
						
			switch(map.get("player")) {
            case "green":
                pcolor = PlayerColor.Green;
                break;
            case "blue":
                pcolor = PlayerColor.Blue;
                break;
            case "pink":
                pcolor = PlayerColor.Pink;
                break; 
            case "yellow":
                pcolor = PlayerColor.Yellow;
                break;
            case "yelow": // Because of typo in feature file
                pcolor = PlayerColor.Yellow;
                break;
            }
						
			game.getPlayer(counter).setColor(pcolor);
			player = game.getPlayer(counter);
			kingdom = player.getKingdom();

			
			Integer id1 = Integer.decode(map.get("domino1"));
			DirectionKind dir1 = getDirection(map.get("dominodir1"));
			Integer posx1 = Integer.decode(map.get("posx1"));
			Integer posy1 = Integer.decode(map.get("posy1"));
			
			Integer id2 = Integer.decode(map.get("domino2"));
			DirectionKind dir2 = getDirection(map.get("dominodir2"));
			Integer posx2 = Integer.decode(map.get("posx2"));
			Integer posy2 = Integer.decode(map.get("posy2"));

			// Add the dominoes to a player's kingdom
			Domino dominoToPlace1 = getdominoByID(id1);
			DominoInKingdom domInKingdom1 = new DominoInKingdom(posx1, posy1, kingdom, dominoToPlace1);
			domInKingdom1.setDirection(dir1);
			dominoToPlace1.setStatus(DominoStatus.PlacedInKingdom);
			
			Domino dominoToPlace2 = getdominoByID(id2);
			DominoInKingdom domInKingdom2 = new DominoInKingdom(posx2, posy2, kingdom, dominoToPlace2);
			domInKingdom2.setDirection(dir2);
			dominoToPlace2.setStatus(DominoStatus.PlacedInKingdom);
			
			
			KingdominoController.identifyProperty(kingdom);

						
			counter ++;
		}
		
		

	}
	
	@Then("player standings should be the followings:")
	public void player_standings_should_be_the_followings(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
		
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		ArrayList<ArrayList<Player>> rankingLists = KingdominoController.calculateRanking(game);
		
		PlayerColor pcolor = null;
						
		HashMap<PlayerColor , Integer> colorsToRanks = new HashMap<>(); 
		
		for (int i = 0; i < rankingLists.size(); i ++) {
						
			for (Player player : rankingLists.get(i)) {
				
				colorsToRanks.put(player.getColor(), i + 1);
			}
			
		}
	
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
						
			Integer rankPosition = Integer.decode(map.get("standing"));
			
			switch(map.get("player")) {
	        case "green":
	            pcolor = PlayerColor.Green;
	            break;
	        case "blue":
	            pcolor = PlayerColor.Blue;
	            break;
	        case "pink":
	            pcolor = PlayerColor.Pink;
	            break;
	        case "yellow":
	            pcolor = PlayerColor.Yellow;
	            break;
	        }
			
			assertEquals(rankPosition, colorsToRanks.get(pcolor)); // Testing for right rank of player
					
		}					
		
	}
	
	

	
	// Private helper methods:
	/////////////////////////
	/////////////////////////
	/////////////////////////
	
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


}
