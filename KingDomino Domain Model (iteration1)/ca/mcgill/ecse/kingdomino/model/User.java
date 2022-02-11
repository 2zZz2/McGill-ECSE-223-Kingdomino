/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse.kingdomino.model;
import java.util.*;
import java.sql.Date;

// line 79 "../../../../../KingDomino.ump"
public class User
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, User> usersByName = new HashMap<String, User>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String name;
  private boolean lastGameFinished;
  private int numberOfGamesWon;
  private int numberOfGamesLost;
  private int numberOfGamesDrew;
  private int numberOfGamesPlayed;
  private float winningRate;

  //User Associations
  private Game unfinishedGame;
  private List<Player> players;
  private KingDomino kingDomino;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aName, boolean aLastGameFinished, int aNumberOfGamesWon, int aNumberOfGamesLost, int aNumberOfGamesDrew, int aNumberOfGamesPlayed, float aWinningRate, KingDomino aKingDomino)
  {
    lastGameFinished = aLastGameFinished;
    numberOfGamesWon = aNumberOfGamesWon;
    numberOfGamesLost = aNumberOfGamesLost;
    numberOfGamesDrew = aNumberOfGamesDrew;
    numberOfGamesPlayed = aNumberOfGamesPlayed;
    winningRate = aWinningRate;
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name");
    }
    players = new ArrayList<Player>();
    boolean didAddKingDomino = setKingDomino(aKingDomino);
    if (!didAddKingDomino)
    {
      throw new RuntimeException("Unable to create user due to kingDomino");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    String anOldName = getName();
    if (hasWithName(aName)) {
      return wasSet;
    }
    name = aName;
    wasSet = true;
    if (anOldName != null) {
      usersByName.remove(anOldName);
    }
    usersByName.put(aName, this);
    return wasSet;
  }

  public boolean setLastGameFinished(boolean aLastGameFinished)
  {
    boolean wasSet = false;
    lastGameFinished = aLastGameFinished;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumberOfGamesWon(int aNumberOfGamesWon)
  {
    boolean wasSet = false;
    numberOfGamesWon = aNumberOfGamesWon;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumberOfGamesLost(int aNumberOfGamesLost)
  {
    boolean wasSet = false;
    numberOfGamesLost = aNumberOfGamesLost;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumberOfGamesDrew(int aNumberOfGamesDrew)
  {
    boolean wasSet = false;
    numberOfGamesDrew = aNumberOfGamesDrew;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumberOfGamesPlayed(int aNumberOfGamesPlayed)
  {
    boolean wasSet = false;
    numberOfGamesPlayed = aNumberOfGamesPlayed;
    wasSet = true;
    return wasSet;
  }

  public boolean setWinningRate(float aWinningRate)
  {
    boolean wasSet = false;
    winningRate = aWinningRate;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template attribute_GetUnique */
  public static User getWithName(String aName)
  {
    return usersByName.get(aName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithName(String aName)
  {
    return getWithName(aName) != null;
  }

  public boolean getLastGameFinished()
  {
    return lastGameFinished;
  }

  public int getNumberOfGamesWon()
  {
    return numberOfGamesWon;
  }

  public int getNumberOfGamesLost()
  {
    return numberOfGamesLost;
  }

  public int getNumberOfGamesDrew()
  {
    return numberOfGamesDrew;
  }

  public int getNumberOfGamesPlayed()
  {
    return numberOfGamesPlayed;
  }

  public float getWinningRate()
  {
    return winningRate;
  }
  /* Code from template association_GetOne */
  public Game getUnfinishedGame()
  {
    return unfinishedGame;
  }

  public boolean hasUnfinishedGame()
  {
    boolean has = unfinishedGame != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_GetOne */
  public KingDomino getKingDomino()
  {
    return kingDomino;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Player addPlayer(int aFinalScore, Date aFinishedDate, Player.GameResultType aGameResult, Game aGame)
  {
    return new Player(aFinalScore, aFinishedDate, aGameResult, this, aGame);
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    User existingUser = aPlayer.getUser();
    boolean isNewUser = existingUser != null && !this.equals(existingUser);
    if (isNewUser)
    {
      aPlayer.setUser(this);
    }
    else
    {
      players.add(aPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayer, as it must always have a user
    if (!this.equals(aPlayer.getUser()))
    {
      players.remove(aPlayer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setKingDomino(KingDomino aKingDomino)
  {
    boolean wasSet = false;
    if (aKingDomino == null)
    {
      return wasSet;
    }

    KingDomino existingKingDomino = kingDomino;
    kingDomino = aKingDomino;
    if (existingKingDomino != null && !existingKingDomino.equals(aKingDomino))
    {
      existingKingDomino.removeUser(this);
    }
    kingDomino.addUser(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    usersByName.remove(getName());
    if (unfinishedGame != null)
    {
      if (unfinishedGame.numberOfUsers() <= 2)
      {
        unfinishedGame.delete();
      }
      else
      {
        Game placeholderUnfinishedGame = unfinishedGame;
        this.unfinishedGame = null;
        placeholderUnfinishedGame.removeUser(this);
      }
    }
    for(int i=players.size(); i > 0; i--)
    {
      Player aPlayer = players.get(i - 1);
      aPlayer.delete();
    }
    KingDomino placeholderKingDomino = kingDomino;
    this.kingDomino = null;
    if(placeholderKingDomino != null)
    {
      placeholderKingDomino.removeUser(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "lastGameFinished" + ":" + getLastGameFinished()+ "," +
            "numberOfGamesWon" + ":" + getNumberOfGamesWon()+ "," +
            "numberOfGamesLost" + ":" + getNumberOfGamesLost()+ "," +
            "numberOfGamesDrew" + ":" + getNumberOfGamesDrew()+ "," +
            "numberOfGamesPlayed" + ":" + getNumberOfGamesPlayed()+ "," +
            "winningRate" + ":" + getWinningRate()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "unfinishedGame = "+(getUnfinishedGame()!=null?Integer.toHexString(System.identityHashCode(getUnfinishedGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "kingDomino = "+(getKingDomino()!=null?Integer.toHexString(System.identityHashCode(getKingDomino())):"null");
  }
}