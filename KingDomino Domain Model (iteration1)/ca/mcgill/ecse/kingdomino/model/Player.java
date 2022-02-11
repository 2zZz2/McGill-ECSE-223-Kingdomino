/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse.kingdomino.model;
import java.sql.Date;
import java.util.*;

// line 117 "../../../../../KingDomino.ump"
public class Player
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum GameResultType { Won, Lost, Drew }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private int finalScore;
  private Date finishedDate;
  private GameResultType gameResult;

  //Player Associations
  private List<King> kings;
  private User user;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(int aFinalScore, Date aFinishedDate, GameResultType aGameResult, User aUser, Game aGame)
  {
    finalScore = aFinalScore;
    finishedDate = aFinishedDate;
    gameResult = aGameResult;
    kings = new ArrayList<King>();
    boolean didAddUser = setUser(aUser);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create player due to user");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create player due to game");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFinalScore(int aFinalScore)
  {
    boolean wasSet = false;
    finalScore = aFinalScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setFinishedDate(Date aFinishedDate)
  {
    boolean wasSet = false;
    finishedDate = aFinishedDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setGameResult(GameResultType aGameResult)
  {
    boolean wasSet = false;
    gameResult = aGameResult;
    wasSet = true;
    return wasSet;
  }

  public int getFinalScore()
  {
    return finalScore;
  }

  public Date getFinishedDate()
  {
    return finishedDate;
  }

  public GameResultType getGameResult()
  {
    return gameResult;
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
  /* Code from template association_GetOne */
  public User getUser()
  {
    return user;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
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
    return 1;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfKings()
  {
    return 2;
  }
  /* Code from template association_AddMNToOnlyOne */
  public King addKing(King.KingColor aKingColor, CastleDomino aCastleDomino, Grid aGrid, Game aGame)
  {
    if (numberOfKings() >= maximumNumberOfKings())
    {
      return null;
    }
    else
    {
      return new King(aKingColor, this, aCastleDomino, aGrid, aGame);
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

    Player existingPlayer = aKing.getPlayer();
    boolean isNewPlayer = existingPlayer != null && !this.equals(existingPlayer);

    if (isNewPlayer && existingPlayer.numberOfKings() <= minimumNumberOfKings())
    {
      return wasAdded;
    }

    if (isNewPlayer)
    {
      aKing.setPlayer(this);
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
    //Unable to remove aKing, as it must always have a player
    if (this.equals(aKing.getPlayer()))
    {
      return wasRemoved;
    }

    //player already at minimum (1)
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
  /* Code from template association_SetOneToMany */
  public boolean setUser(User aUser)
  {
    boolean wasSet = false;
    if (aUser == null)
    {
      return wasSet;
    }

    User existingUser = user;
    user = aUser;
    if (existingUser != null && !existingUser.equals(aUser))
    {
      existingUser.removePlayer(this);
    }
    user.addPlayer(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to player
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (4)
    if (aGame.numberOfPlayers() >= Game.maximumNumberOfPlayers())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removePlayer(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addPlayer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=kings.size(); i > 0; i--)
    {
      King aKing = kings.get(i - 1);
      aKing.delete();
    }
    User placeholderUser = user;
    this.user = null;
    if(placeholderUser != null)
    {
      placeholderUser.removePlayer(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayer(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "finalScore" + ":" + getFinalScore()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "finishedDate" + "=" + (getFinishedDate() != null ? !getFinishedDate().equals(this)  ? getFinishedDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "gameResult" + "=" + (getGameResult() != null ? !getGameResult().equals(this)  ? getGameResult().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}