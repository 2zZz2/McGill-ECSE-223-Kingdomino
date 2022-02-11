package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.application.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProvideUserProflieStepDefinitions {
	public int[] userstatus;
	public List<User> userlist;
	public boolean hasCreated = true;
	
    @Given("the program is started and ready for providing user profile")
    public void the_program_is_started_and_ready_for_providing_user_profile() {
    	Kingdomino kingdomino = new Kingdomino();
    	KingdominoApplication.setKingdomino(kingdomino);
    }

    @Given("there are no users exist")
    public void there_are_no_users_exist() {
        if (KingdominoApplication.getKingdomino().hasUsers()!= false) {
        	for (int i=KingdominoApplication.getKingdomino().numberOfUsers()-1;i>=0;i--) {
        		User deleteuser = KingdominoApplication.getKingdomino().getUser(i);
        		KingdominoApplication.getKingdomino().removeUser(deleteuser);
        	}
        }
    }

    @When("I provide my username {string} and initiate creating a new user")
    public void i_provide_my_username_and_initiate_creating_a_new_user(String string) {
    	hasCreated = true;
    	try {
    		KingdominoController.createNewUsers(string, KingdominoApplication.getKingdomino(),0,0);
    	}catch(InvalidInputException e){
    		hasCreated = false;
    	}
    }

    @Then("the user {string} shall be in the list of users")
    public void the_user_shall_be_in_the_list_of_users(String string) {
    	boolean hasuser = false;
    	for(User user: KingdominoApplication.getKingdomino().getUsers()) {
    		if (user.getName().compareTo(string)==0) {
    			hasuser = true;
    		}
    	}
    	assertTrue(hasuser);
    }

    @Given("the following users exist:")
    public void the_following_users_exist(io.cucumber.datatable.DataTable dataTable) {
    	if (KingdominoApplication.getKingdomino().hasUsers()!= false) {
        	for (int i=KingdominoApplication.getKingdomino().numberOfUsers()-1;i>=0;i--) {
        		User deleteuser = KingdominoApplication.getKingdomino().getUser(i);
        		KingdominoApplication.getKingdomino().removeUser(deleteuser);
        	}
        }
    	List<Map<String, String>> valueMaps = dataTable.asMaps();
    	try {
		for (Map<String, String> map : valueMaps) {
			String username = map.get("name");
			KingdominoController.createNewUsers(username, KingdominoApplication.getKingdomino(),0,0);
		}    	
    	}catch(InvalidInputException e) {
    		System.out.println("Setup failure!");
    	}
    }

    @Then("the user creation shall {string}")
    public void the_user_creation_shall(String string) {
    	  String result;
    	  if (hasCreated == true) {
            	result = "succeed";
            }else {
            	result = "fail";
            }
          assertEquals(result,string);
    }

    @When("I initiate the browsing of all users")
    public void i_initiate_the_browsing_of_all_users() {
    	userlist = KingdominoController.browserUsers(KingdominoApplication.getKingdomino());
    }

    @Then("the users in the list shall be in the following alphabetical order:")
    public void the_users_in_the_list_shall_be_in_the_following_alphabetical_order(io.cucumber.datatable.DataTable dataTable) {
    	List<Map<String, String>> valueMaps = dataTable.asMaps();
		int counter = 0;
    	for (Map<String, String> map : valueMaps) {
			String username = map.get("name");
			int placeinlist = Integer.decode(map.get("placeinlist"));
			assertEquals(username,KingdominoApplication.getKingdomino().getUser(counter).getName());
			assertEquals(placeinlist,userlist.indexOf(KingdominoApplication.getKingdomino().getUser(counter))+1);
			counter++;
    	}   
    }

    @Given("the following users exist with their game statistics:")
    public void the_following_users_exist_with_their_game_statistics(io.cucumber.datatable.DataTable dataTable) throws InvalidInputException {
    	if (KingdominoApplication.getKingdomino().hasUsers()!= false) {
        	for (int i=KingdominoApplication.getKingdomino().numberOfUsers()-1;i>=0;i--) {
        		User deleteuser = KingdominoApplication.getKingdomino().getUser(i);
        		KingdominoApplication.getKingdomino().removeUser(deleteuser);
        	}
        }
    	List<Map<String, String>> valueMaps = dataTable.asMaps();
		try {
			for (Map<String, String> map : valueMaps) {
				String username = map.get("name");
				int playedGames = Integer.decode(map.get("playedGames"));
				int wonGames = Integer.decode(map.get("wonGames"));
				KingdominoController.createNewUsers(username, KingdominoApplication.getKingdomino(),playedGames,wonGames);
			}  
		}catch(InvalidInputException e) {
			System.out.println("Setup failure!");
		}
    }

    @When("I initiate querying the game statistics for a user {string}")
    public void i_initiate_querying_the_game_statistics_for_a_user(String string) {
    	try{
    		userstatus = KingdominoController.queryUserGameStatus(string, KingdominoApplication.getKingdomino());
    	}catch(InvalidInputException e) {
    		userstatus[0]=-1;
    		userstatus[0]=-1;
    	}
    }

    @Then("the number of games played by {string} shall be {int}")
    public void the_number_of_games_played_by_shall_be(String string, Integer int1) {
    	int playedgames = int1;
    	assertEquals(userstatus[0],playedgames);
    }

    @Then("the number of games won by {string} shall be {int}")
    public void the_number_of_games_won_by_shall_be(String string, Integer int1) {
    	int wongames = int1;
    	assertEquals(userstatus[1],wongames);
    }
}
