/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse.kingdomino.model;
import java.util.*;

// line 101 "../../../../../KingDomino.ump"
public class Round
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Round Attributes
  private boolean endOfRound;

  //Round Associations
  private List<Turn> turns;
  private List<RegularDomino> shuffledDominos;
  private List<Grid> grids;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Round(boolean aEndOfRound, Game aGame, RegularDomino... allShuffledDominos)
  {
    endOfRound = aEndOfRound;
    turns = new ArrayList<Turn>();
    shuffledDominos = new ArrayList<RegularDomino>();
    boolean didAddShuffledDominos = setShuffledDominos(allShuffledDominos);
    if (!didAddShuffledDominos)
    {
      throw new RuntimeException("Unable to create Round, must have 24 to 48 shuffledDominos");
    }
    grids = new ArrayList<Grid>();
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create round due to game");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEndOfRound(boolean aEndOfRound)
  {
    boolean wasSet = false;
    endOfRound = aEndOfRound;
    wasSet = true;
    return wasSet;
  }

  public boolean getEndOfRound()
  {
    return endOfRound;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isEndOfRound()
  {
    return endOfRound;
  }
  /* Code from template association_GetMany */
  public Turn getTurn(int index)
  {
    Turn aTurn = turns.get(index);
    return aTurn;
  }

  public List<Turn> getTurns()
  {
    List<Turn> newTurns = Collections.unmodifiableList(turns);
    return newTurns;
  }

  public int numberOfTurns()
  {
    int number = turns.size();
    return number;
  }

  public boolean hasTurns()
  {
    boolean has = turns.size() > 0;
    return has;
  }

  public int indexOfTurn(Turn aTurn)
  {
    int index = turns.indexOf(aTurn);
    return index;
  }
  /* Code from template association_GetMany */
  public RegularDomino getShuffledDomino(int index)
  {
    RegularDomino aShuffledDomino = shuffledDominos.get(index);
    return aShuffledDomino;
  }

  public List<RegularDomino> getShuffledDominos()
  {
    List<RegularDomino> newShuffledDominos = Collections.unmodifiableList(shuffledDominos);
    return newShuffledDominos;
  }

  public int numberOfShuffledDominos()
  {
    int number = shuffledDominos.size();
    return number;
  }

  public boolean hasShuffledDominos()
  {
    boolean has = shuffledDominos.size() > 0;
    return has;
  }

  public int indexOfShuffledDomino(RegularDomino aShuffledDomino)
  {
    int index = shuffledDominos.indexOf(aShuffledDomino);
    return index;
  }
  /* Code from template association_GetMany */
  public Grid getGrid(int index)
  {
    Grid aGrid = grids.get(index);
    return aGrid;
  }

  public List<Grid> getGrids()
  {
    List<Grid> newGrids = Collections.unmodifiableList(grids);
    return newGrids;
  }

  public int numberOfGrids()
  {
    int number = grids.size();
    return number;
  }

  public boolean hasGrids()
  {
    boolean has = grids.size() > 0;
    return has;
  }

  public int indexOfGrid(Grid aGrid)
  {
    int index = grids.indexOf(aGrid);
    return index;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfTurnsValid()
  {
    boolean isValid = numberOfTurns() >= minimumNumberOfTurns() && numberOfTurns() <= maximumNumberOfTurns();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTurns()
  {
    return 6;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfTurns()
  {
    return 12;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Turn addTurn(int aOrderOfTurns, King aCurrentKing, RegularDomino[] allDominoInLine, King[] allKingInLine, DominoPlacement[] allDominoPlacements)
  {
    if (numberOfTurns() >= maximumNumberOfTurns())
    {
      return null;
    }
    else
    {
      return new Turn(aOrderOfTurns, aCurrentKing, this, allDominoInLine, allKingInLine, allDominoPlacements);
    }
  }

  public boolean addTurn(Turn aTurn)
  {
    boolean wasAdded = false;
    if (turns.contains(aTurn)) { return false; }
    if (numberOfTurns() >= maximumNumberOfTurns())
    {
      return wasAdded;
    }

    Round existingRound = aTurn.getRound();
    boolean isNewRound = existingRound != null && !this.equals(existingRound);

    if (isNewRound && existingRound.numberOfTurns() <= minimumNumberOfTurns())
    {
      return wasAdded;
    }

    if (isNewRound)
    {
      aTurn.setRound(this);
    }
    else
    {
      turns.add(aTurn);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTurn(Turn aTurn)
  {
    boolean wasRemoved = false;
    //Unable to remove aTurn, as it must always have a round
    if (this.equals(aTurn.getRound()))
    {
      return wasRemoved;
    }

    //round already at minimum (6)
    if (numberOfTurns() <= minimumNumberOfTurns())
    {
      return wasRemoved;
    }
    turns.remove(aTurn);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTurnAt(Turn aTurn, int index)
  {  
    boolean wasAdded = false;
    if(addTurn(aTurn))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTurns()) { index = numberOfTurns() - 1; }
      turns.remove(aTurn);
      turns.add(index, aTurn);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTurnAt(Turn aTurn, int index)
  {
    boolean wasAdded = false;
    if(turns.contains(aTurn))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTurns()) { index = numberOfTurns() - 1; }
      turns.remove(aTurn);
      turns.add(index, aTurn);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTurnAt(aTurn, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfShuffledDominos()
  {
    return 24;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfShuffledDominos()
  {
    return 48;
  }
  /* Code from template association_AddUnidirectionalMN */
  public boolean addShuffledDomino(RegularDomino aShuffledDomino)
  {
    boolean wasAdded = false;
    if (shuffledDominos.contains(aShuffledDomino)) { return false; }
    if (numberOfShuffledDominos() < maximumNumberOfShuffledDominos())
    {
      shuffledDominos.add(aShuffledDomino);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeShuffledDomino(RegularDomino aShuffledDomino)
  {
    boolean wasRemoved = false;
    if (!shuffledDominos.contains(aShuffledDomino))
    {
      return wasRemoved;
    }

    if (numberOfShuffledDominos() <= minimumNumberOfShuffledDominos())
    {
      return wasRemoved;
    }

    shuffledDominos.remove(aShuffledDomino);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalMN */
  public boolean setShuffledDominos(RegularDomino... newShuffledDominos)
  {
    boolean wasSet = false;
    ArrayList<RegularDomino> verifiedShuffledDominos = new ArrayList<RegularDomino>();
    for (RegularDomino aShuffledDomino : newShuffledDominos)
    {
      if (verifiedShuffledDominos.contains(aShuffledDomino))
      {
        continue;
      }
      verifiedShuffledDominos.add(aShuffledDomino);
    }

    if (verifiedShuffledDominos.size() != newShuffledDominos.length || verifiedShuffledDominos.size() < minimumNumberOfShuffledDominos() || verifiedShuffledDominos.size() > maximumNumberOfShuffledDominos())
    {
      return wasSet;
    }

    shuffledDominos.clear();
    shuffledDominos.addAll(verifiedShuffledDominos);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addShuffledDominoAt(RegularDomino aShuffledDomino, int index)
  {  
    boolean wasAdded = false;
    if(addShuffledDomino(aShuffledDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShuffledDominos()) { index = numberOfShuffledDominos() - 1; }
      shuffledDominos.remove(aShuffledDomino);
      shuffledDominos.add(index, aShuffledDomino);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveShuffledDominoAt(RegularDomino aShuffledDomino, int index)
  {
    boolean wasAdded = false;
    if(shuffledDominos.contains(aShuffledDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShuffledDominos()) { index = numberOfShuffledDominos() - 1; }
      shuffledDominos.remove(aShuffledDomino);
      shuffledDominos.add(index, aShuffledDomino);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addShuffledDominoAt(aShuffledDomino, index);
    }
    return wasAdded;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfGridsValid()
  {
    boolean isValid = numberOfGrids() >= minimumNumberOfGrids() && numberOfGrids() <= maximumNumberOfGrids();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGrids()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfGrids()
  {
    return 4;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Grid addGrid(boolean aEndOfRound, boolean aHasDiscarded, boolean aCastleCentered, int aTotalScore)
  {
    if (numberOfGrids() >= maximumNumberOfGrids())
    {
      return null;
    }
    else
    {
      return new Grid(aEndOfRound, aHasDiscarded, aCastleCentered, aTotalScore, this);
    }
  }

  public boolean addGrid(Grid aGrid)
  {
    boolean wasAdded = false;
    if (grids.contains(aGrid)) { return false; }
    if (numberOfGrids() >= maximumNumberOfGrids())
    {
      return wasAdded;
    }

    Round existingRound = aGrid.getRound();
    boolean isNewRound = existingRound != null && !this.equals(existingRound);

    if (isNewRound && existingRound.numberOfGrids() <= minimumNumberOfGrids())
    {
      return wasAdded;
    }

    if (isNewRound)
    {
      aGrid.setRound(this);
    }
    else
    {
      grids.add(aGrid);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGrid(Grid aGrid)
  {
    boolean wasRemoved = false;
    //Unable to remove aGrid, as it must always have a round
    if (this.equals(aGrid.getRound()))
    {
      return wasRemoved;
    }

    //round already at minimum (2)
    if (numberOfGrids() <= minimumNumberOfGrids())
    {
      return wasRemoved;
    }
    grids.remove(aGrid);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGridAt(Grid aGrid, int index)
  {  
    boolean wasAdded = false;
    if(addGrid(aGrid))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGrids()) { index = numberOfGrids() - 1; }
      grids.remove(aGrid);
      grids.add(index, aGrid);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGridAt(Grid aGrid, int index)
  {
    boolean wasAdded = false;
    if(grids.contains(aGrid))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGrids()) { index = numberOfGrids() - 1; }
      grids.remove(aGrid);
      grids.add(index, aGrid);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGridAt(aGrid, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to round
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (3)
    if (aGame.numberOfRounds() >= Game.maximumNumberOfRounds())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removeRound(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addRound(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (turns.size() > 0)
    {
      Turn aTurn = turns.get(turns.size() - 1);
      aTurn.delete();
      turns.remove(aTurn);
    }
    
    shuffledDominos.clear();
    while (grids.size() > 0)
    {
      Grid aGrid = grids.get(grids.size() - 1);
      aGrid.delete();
      grids.remove(aGrid);
    }
    
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeRound(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "endOfRound" + ":" + getEndOfRound()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}