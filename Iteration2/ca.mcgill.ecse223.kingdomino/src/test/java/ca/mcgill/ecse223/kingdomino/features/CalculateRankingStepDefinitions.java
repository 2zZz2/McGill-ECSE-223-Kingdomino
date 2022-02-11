package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class CalculateRankingStepDefinitions {
	
	
	Kingdomino kingdomino;
	Game game;

	
	
	public static ArrayList<ArrayList<Player>> rankingLists;
	
	@Given("the game is initialized for calculate ranking")
	public void the_game_is_initialized_for_calculate_ranking() {
	    // Write code here that turns the phrase above into concrete actions
		kingdomino = KingdominoApplication.getKingdomino();
		game = new Game(48, kingdomino);
		kingdomino.setCurrentGame(game);
		createAllDominoes(game);
		KingdominoApplication.setKingdomino(kingdomino);
		addDefaultUsersAndPlayers(game);

		
	}
		
	@Given("the players have no tiebreak")
	public void the_players_have_no_tiebreak() {
	    // Write code here that turns the phrase above into concrete actions
	    
		
		if (KingdominoController.areThereAnyTies(KingdominoController.sortPlayersByRanking(game))) {
			System.out.println("There are no ties");
		}
		
		
		
	}
	
	@When("calculate ranking is initiated")
	public void calculate_ranking_is_initiated() {
	    // Write code here that turns the phrase above into concrete actions
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		rankingLists = KingdominoController.calculateRanking(game);
	}
	
	@Then("player standings shall be the followings:")
	public void player_standings_shall_be_the_followings(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
		
		int counter = 0;
		PlayerColor pcolor = null;
		ArrayList<Player> rankedPlayerListAfterTiebreaks = new ArrayList<Player>();
		
		for (ArrayList<Player> playersAtRank : rankingLists) {
			for (Player player: playersAtRank) {
				rankedPlayerListAfterTiebreaks.add(player);
			}
		}
 
		Integer testRank = null;

		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			
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
			
			Integer rankPosition = Integer.decode(map.get("standing"));

			assertEquals(pcolor, rankedPlayerListAfterTiebreaks.get(counter).getColor()); // Testing for right player at position
						
			int ArrayListCounter = 1; // 1 not 0 because the rankings start at 1
			boolean breakStatus = false;
			
			for (ArrayList<Player> playersAtRank : rankingLists) {
				for (Player player: playersAtRank) {
					if (rankedPlayerListAfterTiebreaks.get(counter).equals(player)) {
						testRank = ArrayListCounter;
						breakStatus = true;
						break;
					}					
				}
				
				if (breakStatus) {
					break;
				}
				
				ArrayListCounter += 1;
				
			}
			
			assertEquals(rankPosition, testRank); // Testing for right rank of player
			counter += 1;	
				
		}
		
		rankingLists.clear();
	}
	
	@After	
	public void tearDown() {
		if(kingdomino!=null) {
			kingdomino.delete();
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
}