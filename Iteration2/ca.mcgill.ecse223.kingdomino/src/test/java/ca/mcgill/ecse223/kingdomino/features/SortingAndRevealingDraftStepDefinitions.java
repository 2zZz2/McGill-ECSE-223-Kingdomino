package ca.mcgill.ecse223.kingdomino.features;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SortingAndRevealingDraftStepDefinitions {
	Game game;
	Kingdomino kingdomino;
	
	
	@Given("there is a next draft, face down")
	public void there_is_a_next_draft_face_down() {
		kingdomino = new Kingdomino();
		game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		
		addDefaultUsersAndPlayers(game);//adds the users and player classes to this particular game
		createAllDominoes(game); //add all the dominoes used to play => helper class
		game.setNextPlayer(game.getPlayer(0)); //sets first player
		KingdominoApplication.setKingdomino(kingdomino); //update the kingdominoGame to the kingDominoApp
		
		 Draft currentDraft = new Draft(DraftStatus.FaceDown,game);
		   Domino firstDomino = new Domino(10,TerrainType.Grass,TerrainType.Grass,0,game);
		   Domino secondDomino = new Domino(48,TerrainType.WheatField,TerrainType.Mountain,3,game);
		   Domino thirdDomino = new Domino(39,TerrainType.Grass,TerrainType.Swamp,1,game);
		   Domino fourthDomino = new Domino(5,TerrainType.Forest,TerrainType.Forest,0,game);
		   game.addAllDraft(currentDraft);
		   game.setCurrentDraft(currentDraft);
		   currentDraft.addIdSortedDomino(firstDomino);
		   currentDraft.addIdSortedDomino(secondDomino);
		   currentDraft.addIdSortedDomino(thirdDomino);
		   currentDraft.addIdSortedDomino(fourthDomino);
		
	  // KingdominoController.createNextDraft(game);
	   Draft nextDraft = new Draft(DraftStatus.FaceDown, game);
	   //nextDraft = game.getNextDraft();
	   //Draft currentDraft = new Draft(DraftStatus.FaceDown,game);
	   Domino firstDominoNext = new Domino(7,TerrainType.Lake,TerrainType.Lake,0,game);
	   Domino secondDominoNext = new Domino(2,TerrainType.WheatField,TerrainType.WheatField,0,game);
	   Domino thirdDominoNext = new Domino(13,TerrainType.WheatField,TerrainType.Forest,0,game);
	   Domino fourthDominoNext = new Domino(29,TerrainType.Grass,TerrainType.Lake,1,game);
	   game.addAllDraft(nextDraft);
	   game.setNextDraft(nextDraft);
	   nextDraft.addIdSortedDomino(firstDominoNext);
	   nextDraft.addIdSortedDomino(secondDominoNext);
	   nextDraft.addIdSortedDomino(thirdDominoNext);
	   nextDraft.addIdSortedDomino(fourthDominoNext);
	   
	   
	  
	   //System.out.println(game.getCurrentDraft().getIdSortedDominos().get(0).getId()+"CURRENT FIRST DOM");
	  // System.out.println(nextDraft.getIdSortedDominos().get(0).getId()+"NEXT FIRST DOM");
	  
	
	   
		// Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
	}
	@Given("all dominoes in current draft are selected")
	public void all_dominoes_in_current_draft_are_selected() {
		
		Draft currentDraft = game.getCurrentDraft();
		 /* Draft currentDraft = new Draft(DraftStatus.FaceDown,game);
		   Domino firstDomino = new Domino(10,TerrainType.Grass,TerrainType.Grass,0,game);
		   Domino secondDomino = new Domino(48,TerrainType.WheatField,TerrainType.Mountain,3,game);
		   Domino thirdDomino = new Domino(39,TerrainType.Grass,TerrainType.Swamp,1,game);
		   Domino fourthDomino = new Domino(5,TerrainType.Forest,TerrainType.Forest,0,game);
		  game.addAllDraft(currentDraft);
		   currentDraft.addIdSortedDomino(firstDomino);
		   currentDraft.addIdSortedDomino(secondDomino);
		   currentDraft.addIdSortedDomino(thirdDomino);
		   currentDraft.addIdSortedDomino(fourthDomino);*/
		   currentDraft.addSelection(game.getPlayer(0), currentDraft.getIdSortedDomino(2));
		   currentDraft.addSelection(game.getPlayer(1), currentDraft.getIdSortedDomino(1));
		   currentDraft.addSelection(game.getPlayer(2), currentDraft.getIdSortedDomino(0));
		   currentDraft.addSelection(game.getPlayer(3), currentDraft.getIdSortedDomino(3));
		 //current.Draft();
	   
		/*for(Domino aDomino : currentDraft.getIdSortedDominos()){
			currentDraft.addSelection(game.getPlayer(0), aDomino);
			while(game.hasNextPlayer()) {
			currentDraft.addSelection(game.getNextPlayer(), aDomino);
			}
			
		}*/
	
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
	}

	@When("next draft is sorted")
	public void next_draft_is_sorted() {
		Draft myNextDraft = game.getNextDraft();
	    KingdominoController.order(myNextDraft);
		//myNextDraft.setIdSortedDominos(newIdSortedDominos);
		
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
	}

	@When("next draft is revealed")
	public void next_draft_is_revealed() {
		Draft myNextDraft = game.getNextDraft();
		KingdominoController.reveal(myNextDraft);
	    // Write code here that turns the phrase above into concrete actions
	   // throw new cucumber.api.PendingException();
	}

	@Then("the next draft shall be sorted")
	public void the_next_draft_shall_be_sorted() {
		Draft myNextDraft = game.getNextDraft();
		Boolean isSorted = true;
		int prevId = myNextDraft.getIdSortedDomino(0).getId();
		for(Domino aDomino : myNextDraft.getIdSortedDominos()) {
			if(aDomino.getId()<prevId) {
				isSorted = false; 
				break;
			}
			prevId = aDomino.getId();
			
		}
	    assertEquals(true,isSorted);
		
	    // Write code here that turns the phrase above into concrete actions
	    //throw new cucumber.api.PendingException();
	}

	@Then("the next draft shall be facing up")
	public void the_next_draft_shall_be_facing_up() {
		Draft myNextDraft = game.getNextDraft();
		assertEquals(DraftStatus.FaceUp,myNextDraft.getDraftStatus());
	    // Write code here that turns the phrase above into concrete actions
	   // throw new cucumber.api.PendingException();
	}

	@Then("it shall be the player's turn with the lowest domino ID selection")
	public void it_shall_be_the_player_s_turn_with_the_lowest_domino_ID_selection() {
		int lowestId = game.getPlayer(0).getDominoSelection().getDomino().getId();
		int lowestIndex = 0;
		for(int i = 0;i<game.getNumberOfPlayers();i++) {
			if(game.getPlayer(i).getDominoSelection().getDomino().getId()<=lowestId) {
				lowestId = game.getPlayer(i).getDominoSelection().getDomino().getId();
				lowestIndex = i;
			}
			
		}
		game.setNextPlayer(game.getPlayer(lowestIndex));
	    // Write code here that turns the phrase above into concrete actions
	   // throw new cucumber.api.PendingException();
	}
	
	
	
	//helper methods
	
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
