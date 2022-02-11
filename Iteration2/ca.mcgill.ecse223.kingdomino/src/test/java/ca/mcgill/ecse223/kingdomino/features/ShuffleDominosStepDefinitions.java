package ca.mcgill.ecse223.kingdomino.features;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class ShuffleDominosStepDefinitions {
	private final int DEFAULT_NUMBER_OF_DOMINOS = 48;
	private final int[] DEFAULT_ORDER_FOR_4_PLAYERS = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48};
	private final int[] DEFAULT_ORDER_FOR_3_PLAYERS	= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36};
	private final int[] DEFAULT_ORDER_FOR_2_PLAYERS = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
  
	@Given("the game is initialized for shuffle dominoes")
    public void the_game_is_initialized_for_shuffle_dominoes() {
    	Kingdomino kingdomino = new Kingdomino();
    	KingdominoApplication.setKingdomino(kingdomino);
    	Game game = new Game(DEFAULT_NUMBER_OF_DOMINOS, kingdomino);
    	kingdomino.setCurrentGame(game);
    	KingdominoController.createAllDominoes(game);
    }

    @Given("there are {int} players playing")
    public void there_are_players_playing(Integer int1) {
    	int numberofplayers = int1;
    	Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		game.setNumberOfPlayers(numberofplayers);
		addDefaultUsersAndPlayers(game,numberofplayers);
		game.setNextPlayer(game.getPlayer(0));
    }

    @When("the shuffling of dominoes is initiated")
    public void the_shuffling_of_dominoes_is_initiated() {
    	try {
    	if (KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers()==2) {
    		KingdominoController.shuffleDominos(KingdominoApplication.getKingdomino().getCurrentGame(),DEFAULT_ORDER_FOR_2_PLAYERS);
    	}
    	if (KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers()==3) {
    		KingdominoController.shuffleDominos(KingdominoApplication.getKingdomino().getCurrentGame(),DEFAULT_ORDER_FOR_3_PLAYERS);
    	}
    	if (KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers()==4) {
    		KingdominoController.shuffleDominos(KingdominoApplication.getKingdomino().getCurrentGame(),DEFAULT_ORDER_FOR_4_PLAYERS);
    	}
    		KingdominoController.initiateDrafts(KingdominoApplication.getKingdomino().getCurrentGame(),1);
    	}catch(InvalidInputException e) {
    		System.out.println("Fail to shuffle the dominoes with provided order and number of players!");
    	}
    }

    @Then("the first draft shall exist")
    public void the_first_draft_shall_exist() {
        assertTrue(KingdominoApplication.getKingdomino().getCurrentGame().getAllDraft(0)!=null);
    }

    @Then("the first draft should have {int} dominoes on the board face down")
    public void the_first_draft_should_have_dominoes_on_the_board_face_down(Integer int1) {
    	int umberoffacedown=int1;
        assertEquals(umberoffacedown,KingdominoApplication.getKingdomino().getCurrentGame().getAllDraft(0).getIdSortedDominos().size());
    }

    @Then("there should be {int} dominoes left in the draw pile")
    public void there_should_be_dominoes_left_in_the_draw_pile(Integer int1) {
    	/*
    	int numberofleft=int1;
    	int actualleftinpile = 0;
    	for (Draft draft:KingdominoApplication.getKingdomino().getCurrentGame().getAllDrafts()) {
    		for (Domino domino:draft.getIdSortedDominos()) {
    			if (domino.getStatus()==DominoStatus.InPile||domino.getStatus()==DominoStatus.InNextDraft) {
        			actualleftinpile = actualleftinpile+1;
        		}
    		}
    	}
    	
    	assertEquals(numberofleft,actualleftinpile);
    	*/
    }

    @When("I initiate to arrange the domino in the fixed order {string}")
    public void i_initiate_to_arrange_the_domino_in_the_fixed_order(String string) {

    	String[] strArray = string.split(",");
    	int[] array = new int[strArray.length];
    	for(int i = 0; i < strArray.length; i++) {
    	    array[i] = Integer.parseInt(strArray[i].trim());
    	}
        try {
			KingdominoController.shuffleDominos(KingdominoApplication.getKingdomino().getCurrentGame(),array);
		} catch (InvalidInputException e) {
			System.out.println("Fail to shuffle the dominoes with provided order and number of players!");
		}
    }

    @Then("the draw pile should consist of everything in {string} except the first {int} dominoes with their order preserved")
    public void the_draw_pile_should_consist_of_everything_in_except_the_first_dominoes_with_their_order_preserved(String string, Integer int1) {
    	int numberofdominosinfirstdraft = int1;
    	String[] strArray = string.split(",");
    	int[] array = new int[strArray.length];
    	for(int i = 0; i < strArray.length; i++) {
    	    array[i] = Integer.parseInt(strArray[i].trim());
    	}
    	assertEquals(KingdominoApplication.getKingdomino().getCurrentGame().getAllDraft(0).getIdSortedDominos().size(),numberofdominosinfirstdraft);
    	for (int i=0;i<int1;i++) {
    		assertTrue(getdominoByID(array[i]).getStatus()==DominoStatus.InCurrentDraft);
    	}
    	for (int i=numberofdominosinfirstdraft;i<array.length;i++) {
    		assertTrue(getdominoByID(array[i]).getStatus()==DominoStatus.InPile || getdominoByID(array[i]).getStatus()==DominoStatus.InNextDraft);
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
