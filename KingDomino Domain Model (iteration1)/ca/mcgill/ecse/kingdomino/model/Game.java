/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse.kingdomino.model;
import java.util.*;
import java.sql.Date;

// line 109 "../../../../../KingDomino.ump"
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private boolean endOfGame;

  //Game Associations
  private List<King> kings;
  private List<Round> rounds;
  private List<Player> players;
  private KingDomino kingDomino;
  private List<User> users;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(boolean aEndOfGame, KingDomino aKingDomino, User... allUsers)
  {
    endOfGame = aEndOfGame;
    kings = new ArrayList<King>();
    rounds = new ArrayList<Round>();
    players = new ArrayList<Player>();
    boolean didAddKingDomino = setKingDomino(aKingDomino);
    if (!didAddKingDomino)
    {
      throw new RuntimeException("Unable to create game due to kingDomino");
    }
    users = new ArrayList<User>();
    boolean didAddUsers = setUsers(allUsers);
    if (!didAddUsers)
    {
      throw new RuntimeException("Unable to create Game, must have 2 to 4 users");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEndOfGame(boolean aEndOfGame)
  {
    boolean wasSet = false;
    endOfGame = aEndOfGame;
    wasSet = true;
    return wasSet;
  }

  public boolean getEndOfGame()
  {
    return endOfGame;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isEndOfGame()
  {
    return endOfGame;
  }
  /* Code from template association_GetMany */
  public King getKing(int index)
  {
    King aKing = kings.get(index);
    return aKing;
  }

  public List<King> getKings()
  {
    List<King> newKings = Collections.unmodifiableList(kings);
    return newKings;
  }

  public int numberOfKings()
  {
    int number = kings.size();
    return number;
  }

  public boolean hasKings()
  {
    boolean has = kings.size() > 0;
    return has;
  }

  public int indexOfKing(King aKing)
  {
    int index = kings.indexOf(aKing);
    return index;
  }
  /* Code from template association_GetMany */
  public Round getRound(int index)
  {
    Round aRound = rounds.get(index);
    return aRound;
  }

  public List<Round> getRounds()
  {
    List<Round> newRounds = Collections.unmodifiableList(rounds);
    return newRounds;
  }

  public int numberOfRounds()
  {
    int number = rounds.size();
    return number;
  }

  public boolean hasRounds()
  {
    boolean has = rounds.size() > 0;
    return has;
  }

  public int indexOfRound(Round aRound)
  {
    int index = rounds.indexOf(aRound);
    return index;
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
  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfKingsValid()
  {
    boolean isValid = numberOfKings() >= minimumNumberOfKings() && numberOfKings() <= maximumNumberOfKings();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfKings()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfKings()
  {
    return 4;
  }
  /* Code from template association_AddMNToOnlyOne */
  public King addKing(King.KingColor aKingColor, Player aPlayer, CastleDomino aCastleDomino, Grid aGrid)
  {
    if (numberOfKings() >= maximumNumberOfKings())
    {
      return null;
    }
    else
    {
      return new King(aKingColor, aPlayer, aCastleDomino, aGrid, this);
    }
  }

  public boolean addKing(King aKing)
  {
    boolean wasAdded = false;
    if (kings.contains(aKing)) { return false; }
    if (numberOfKings() >= maximumNumberOfKings())
    {
      return wasAdded;
    }

    Game existingGame = aKing.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);

    if (isNewGame && existingGame.numberOfKings() <= minimumNumberOfKings())
    {
      return wasAdded;
    }

    if (isNewGame)
    {
      aKing.setGame(this);
    }
    else
    {
      kings.add(aKing);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeKing(King aKing)
  {
    boolean wasRemoved = false;
    //Unable to remove aKing, as it must always have a game
    if (this.equals(aKing.getGame()))
    {
      return wasRemoved;
    }

    //game already at minimum (3)
    if (numberOfKings() <= minimumNumberOfKings())
    {
      return wasRemoved;
    }
    kings.remove(aKing);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addKingAt(King aKing, int index)
  {  
    boolean wasAdded = false;
    if(addKing(aKing))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfKings()) { index = numberOfKings() - 1; }
      kings.remove(aKing);
      kings.add(index, aKing);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveKingAt(King aKing, int index)
  {
    boolean wasAdded = false;
    if(kings.contains(aKing))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfKings()) { index = numberOfKings() - 1; }
      kings.remove(aKing);
      kings.add(index, aKing);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addKingAt(aKing, index);
    }
    return wasAdded;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfRoundsValid()
  {
    boolean isValid = numberOfRounds() >= minimumNumberOfRounds() && numberOfRounds() <= maximumNumberOfRounds();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRounds()
  {
    return 1;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfRounds()
  {
    return 3;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Round addRound(boolean aEndOfRound, RegularDomino... allShuffledDominos)
  {
    if (numberOfRounds() >= maximumNumberOfRounds())
    {
      return null;
    }
    else
    {
      return new Round(aEndOfRound, this, allShuffledDominos);
    }
  }

  public boolean addRound(Round aRound)
  {
    boolean wasAdded = false;
    if (rounds.contains(aRound)) { return false; }
    if (numberOfRounds() >= maximumNumberOfRounds())
    {
      return wasAdded;
    }

    Game existingGame = aRound.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);

    if (isNewGame && existingGame.numberOfRounds() <= minimumNumberOfRounds())
    {
      return wasAdded;
    }

    if (isNewGame)
    {
      aRound.setGame(this);
    }
    else
    {
      rounds.add(aRound);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRound(Round aRound)
  {
    boolean wasRemoved = false;
    //Unable to remove aRound, as it must always have a game
    if (this.equals(aRound.getGame()))
    {
      return wasRemoved;
    }

    //game already at minimum (1)
    if (numberOfRounds() <= minimumNumberOfRounds())
    {
      return wasRemoved;
    }
    rounds.remove(aRound);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRoundAt(Round aRound, int index)
  {  
    boolean wasAdded = false;
    if(addRound(aRound))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRounds()) { index = numberOfRounds() - 1; }
      rounds.remove(aRound);
      rounds.add(index, aRound);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRoundAt(Round aRound, int index)
  {
    boolean wasAdded = false;
    if(rounds.contains(aRound))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRounds()) { index = numberOfRounds() - 1; }
      rounds.remove(aRound);
      rounds.add(index, aRound);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRoundAt(aRound, index);
    }
    return wasAdded;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfPlayersValid()
  {
    boolean isValid = numberOfPlayers() >= minimumNumberOfPlayers() && numberOfPlayers() <= maximumNumberOfPlayers();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPlayers()
  {
    return 4;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Player addPlayer(int aFinalScore, Date aFinishedDate, Player.GameResultType aGameResult, User aUser)
  {
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return null;
    }
    else
    {
      return new Player(aFinalScore, aFinishedDate, aGameResult, aUser, this);
    }
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return wasAdded;
    }

    Game existingGame = aPlayer.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);

    if (isNewGame && existingGame.numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasAdded;
    }

    if (isNewGame)
    {
      aPlayer.setGame(this);
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
    //Unable to remove aPlayer, as it must always have a game
    if (this.equals(aPlayer.getGame()))
    {
      return wasRemoved;
    }

    //game already at minimum (2)
    if (numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasRemoved;
    }
    players.remove(aPlayer);
    wasRemoved = true;
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
      existingKingDomino.removeGame(this);
    }
    kingDomino.addGame(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfUsers()
  {
    return 4;
  }
  /* Code from template association_AddMNToOptionalOne */
  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    if (numberOfUsers() >= maximumNumberOfUsers())
    {
      return wasAdded;
    }
    Game existingUnfinishedGame = aUser.getUnfinishedGame();
    if (existingUnfinishedGame != null && existingUnfinishedGame.numberOfUsers() <= minimumNumberOfUsers())
    {
      return wasAdded;
    }
    else if (existingUnfinishedGame != null)
    {
      existingUnfinishedGame.users.remove(aUser);
    }
    users.add(aUser);
    setUnfinishedGame(aUser,this);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    if (users.contains(aUser) && numberOfUsers() > minimumNumberOfUsers())
    {
      users.remove(aUser);
      setUnfinishedGame(aUser,null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_SetMNToOptionalOne */
  public boolean setUsers(User... newUsers)
  {
    boolean wasSet = false;
    if (newUsers.length < minimumNumberOfUsers() || newUsers.length > maximumNumberOfUsers())
    {
      return wasSet;
    }

    ArrayList<User> checkNewUsers = new ArrayList<User>();
    HashMap<Game,Integer> unfinishedGameToNewUsers = new HashMap<Game,Integer>();
    for (User aUser : newUsers)
    {
      if (checkNewUsers.contains(aUser))
      {
        return wasSet;
      }
      else if (aUser.getUnfinishedGame() != null && !this.equals(aUser.getUnfinishedGame()))
      {
        Game existingUnfinishedGame = aUser.getUnfinishedGame();
        if (!unfinishedGameToNewUsers.containsKey(existingUnfinishedGame))
        {
          unfinishedGameToNewUsers.put(existingUnfinishedGame, new Integer(existingUnfinishedGame.numberOfUsers()));
        }
        Integer currentCount = unfinishedGameToNewUsers.get(existingUnfinishedGame);
        int nextCount = currentCount - 1;
        if (nextCount < 2)
        {
          return wasSet;
        }
        unfinishedGameToNewUsers.put(existingUnfinishedGame, new Integer(nextCount));
      }
      checkNewUsers.add(aUser);
    }

    users.removeAll(checkNewUsers);

    for (User orphan : users)
    {
      setUnfinishedGame(orphan, null);
    }
    users.clear();
    for (User aUser : newUsers)
    {
      if (aUser.getUnfinishedGame() != null)
      {
        aUser.getUnfinishedGame().users.remove(aUser);
      }
      setUnfinishedGame(aUser, this);
      users.add(aUser);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_GetPrivate */
  private void setUnfinishedGame(User aUser, Game aUnfinishedGame)
  {
    try
    {
      java.lang.reflect.Field mentorField = aUser.getClass().getDeclaredField("unfinishedGame");
      mentorField.setAccessible(true);
      mentorField.set(aUser, aUnfinishedGame);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Issue internally setting aUnfinishedGame to aUser", e);
    }
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (kings.size() > 0)
    {
      King aKing = kings.get(kings.size() - 1);
      aKing.delete();
      kings.remove(aKing);
    }
    
    while (rounds.size() > 0)
    {
      Round aRound = rounds.get(rounds.size() - 1);
      aRound.delete();
      rounds.remove(aRound);
    }
    
    while (players.size() > 0)
    {
      Player aPlayer = players.get(players.size() - 1);
      aPlayer.delete();
      players.remove(aPlayer);
    }
    
    KingDomino placeholderKingDomino = kingDomino;
    this.kingDomino = null;
    if(placeholderKingDomino != null)
    {
      placeholderKingDomino.removeGame(this);
    }
    for(User aUser : users)
    {
      setUnfinishedGame(aUser,null);
    }
    users.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "endOfGame" + ":" + getEndOfGame()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "kingDomino = "+(getKingDomino()!=null?Integer.toHexString(System.identityHashCode(getKingDomino())):"null");
  }
}