/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse.kingdomino.model;
import java.util.*;

// line 56 "../../../../../KingDomino.ump"
public class Grid
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Grid Attributes
  private boolean endOfRound;
  private boolean hasDiscarded;
  private boolean castleCentered;
  private int totalScore;

  //Grid Associations
  private List<GridCell> gridCells;
  private List<Property> properties;
  private List<King> kings;
  private Round round;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Grid(boolean aEndOfRound, boolean aHasDiscarded, boolean aCastleCentered, int aTotalScore, Round aRound)
  {
    endOfRound = aEndOfRound;
    hasDiscarded = aHasDiscarded;
    castleCentered = aCastleCentered;
    totalScore = aTotalScore;
    gridCells = new ArrayList<GridCell>();
    properties = new ArrayList<Property>();
    kings = new ArrayList<King>();
    boolean didAddRound = setRound(aRound);
    if (!didAddRound)
    {
      throw new RuntimeException("Unable to create grid due to round");
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

  public boolean setHasDiscarded(boolean aHasDiscarded)
  {
    boolean wasSet = false;
    hasDiscarded = aHasDiscarded;
    wasSet = true;
    return wasSet;
  }

  public boolean setCastleCentered(boolean aCastleCentered)
  {
    boolean wasSet = false;
    castleCentered = aCastleCentered;
    wasSet = true;
    return wasSet;
  }

  public boolean setTotalScore(int aTotalScore)
  {
    boolean wasSet = false;
    totalScore = aTotalScore;
    wasSet = true;
    return wasSet;
  }

  public boolean getEndOfRound()
  {
    return endOfRound;
  }

  public boolean getHasDiscarded()
  {
    return hasDiscarded;
  }

  public boolean getCastleCentered()
  {
    return castleCentered;
  }

  public int getTotalScore()
  {
    return totalScore;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isEndOfRound()
  {
    return endOfRound;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isHasDiscarded()
  {
    return hasDiscarded;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isCastleCentered()
  {
    return castleCentered;
  }
  /* Code from template association_GetMany */
  public GridCell getGridCell(int index)
  {
    GridCell aGridCell = gridCells.get(index);
    return aGridCell;
  }

  public List<GridCell> getGridCells()
  {
    List<GridCell> newGridCells = Collections.unmodifiableList(gridCells);
    return newGridCells;
  }

  public int numberOfGridCells()
  {
    int number = gridCells.size();
    return number;
  }

  public boolean hasGridCells()
  {
    boolean has = gridCells.size() > 0;
    return has;
  }

  public int indexOfGridCell(GridCell aGridCell)
  {
    int index = gridCells.indexOf(aGridCell);
    return index;
  }
  /* Code from template association_GetMany */
  public Property getProperty(int index)
  {
    Property aProperty = properties.get(index);
    return aProperty;
  }

  public List<Property> getProperties()
  {
    List<Property> newProperties = Collections.unmodifiableList(properties);
    return newProperties;
  }

  public int numberOfProperties()
  {
    int number = properties.size();
    return number;
  }

  public boolean hasProperties()
  {
    boolean has = properties.size() > 0;
    return has;
  }

  public int indexOfProperty(Property aProperty)
  {
    int index = properties.indexOf(aProperty);
    return index;
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
  public Round getRound()
  {
    return round;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGridCells()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfGridCells()
  {
    return 25;
  }
  /* Code from template association_AddOptionalNToOne */
  public GridCell addGridCell(int aXCoordinate, int aYCoordinate, Tile aTile)
  {
    if (numberOfGridCells() >= maximumNumberOfGridCells())
    {
      return null;
    }
    else
    {
      return new GridCell(aXCoordinate, aYCoordinate, aTile, this);
    }
  }

  public boolean addGridCell(GridCell aGridCell)
  {
    boolean wasAdded = false;
    if (gridCells.contains(aGridCell)) { return false; }
    if (numberOfGridCells() >= maximumNumberOfGridCells())
    {
      return wasAdded;
    }

    Grid existingGrid = aGridCell.getGrid();
    boolean isNewGrid = existingGrid != null && !this.equals(existingGrid);
    if (isNewGrid)
    {
      aGridCell.setGrid(this);
    }
    else
    {
      gridCells.add(aGridCell);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGridCell(GridCell aGridCell)
  {
    boolean wasRemoved = false;
    //Unable to remove aGridCell, as it must always have a grid
    if (!this.equals(aGridCell.getGrid()))
    {
      gridCells.remove(aGridCell);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGridCellAt(GridCell aGridCell, int index)
  {  
    boolean wasAdded = false;
    if(addGridCell(aGridCell))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGridCells()) { index = numberOfGridCells() - 1; }
      gridCells.remove(aGridCell);
      gridCells.add(index, aGridCell);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGridCellAt(GridCell aGridCell, int index)
  {
    boolean wasAdded = false;
    if(gridCells.contains(aGridCell))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGridCells()) { index = numberOfGridCells() - 1; }
      gridCells.remove(aGridCell);
      gridCells.add(index, aGridCell);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGridCellAt(aGridCell, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfProperties()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Property addProperty(LandscapeType aLandscape, int aLandscapeSize, int aNumberOfCrowns, int aScore)
  {
    return new Property(aLandscape, aLandscapeSize, aNumberOfCrowns, aScore, this);
  }

  public boolean addProperty(Property aProperty)
  {
    boolean wasAdded = false;
    if (properties.contains(aProperty)) { return false; }
    Grid existingGrid = aProperty.getGrid();
    boolean isNewGrid = existingGrid != null && !this.equals(existingGrid);
    if (isNewGrid)
    {
      aProperty.setGrid(this);
    }
    else
    {
      properties.add(aProperty);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeProperty(Property aProperty)
  {
    boolean wasRemoved = false;
    //Unable to remove aProperty, as it must always have a grid
    if (!this.equals(aProperty.getGrid()))
    {
      properties.remove(aProperty);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPropertyAt(Property aProperty, int index)
  {  
    boolean wasAdded = false;
    if(addProperty(aProperty))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProperties()) { index = numberOfProperties() - 1; }
      properties.remove(aProperty);
      properties.add(index, aProperty);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePropertyAt(Property aProperty, int index)
  {
    boolean wasAdded = false;
    if(properties.contains(aProperty))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProperties()) { index = numberOfProperties() - 1; }
      properties.remove(aProperty);
      properties.add(index, aProperty);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPropertyAt(aProperty, index);
    }
    return wasAdded;
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
  public King addKing(King.KingColor aKingColor, Player aPlayer, CastleDomino aCastleDomino, Game aGame)
  {
    if (numberOfKings() >= maximumNumberOfKings())
    {
      return null;
    }
    else
    {
      return new King(aKingColor, aPlayer, aCastleDomino, this, aGame);
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

    Grid existingGrid = aKing.getGrid();
    boolean isNewGrid = existingGrid != null && !this.equals(existingGrid);

    if (isNewGrid && existingGrid.numberOfKings() <= minimumNumberOfKings())
    {
      return wasAdded;
    }

    if (isNewGrid)
    {
      aKing.setGrid(this);
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
    //Unable to remove aKing, as it must always have a grid
    if (this.equals(aKing.getGrid()))
    {
      return wasRemoved;
    }

    //grid already at minimum (1)
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
  /* Code from template association_SetOneToAtMostN */
  public boolean setRound(Round aRound)
  {
    boolean wasSet = false;
    //Must provide round to grid
    if (aRound == null)
    {
      return wasSet;
    }

    //round already at maximum (4)
    if (aRound.numberOfGrids() >= Round.maximumNumberOfGrids())
    {
      return wasSet;
    }
    
    Round existingRound = round;
    round = aRound;
    if (existingRound != null && !existingRound.equals(aRound))
    {
      boolean didRemove = existingRound.removeGrid(this);
      if (!didRemove)
      {
        round = existingRound;
        return wasSet;
      }
    }
    round.addGrid(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (gridCells.size() > 0)
    {
      GridCell aGridCell = gridCells.get(gridCells.size() - 1);
      aGridCell.delete();
      gridCells.remove(aGridCell);
    }
    
    while (properties.size() > 0)
    {
      Property aProperty = properties.get(properties.size() - 1);
      aProperty.delete();
      properties.remove(aProperty);
    }
    
    for(int i=kings.size(); i > 0; i--)
    {
      King aKing = kings.get(i - 1);
      aKing.delete();
    }
    Round placeholderRound = round;
    this.round = null;
    if(placeholderRound != null)
    {
      placeholderRound.removeGrid(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "endOfRound" + ":" + getEndOfRound()+ "," +
            "hasDiscarded" + ":" + getHasDiscarded()+ "," +
            "castleCentered" + ":" + getCastleCentered()+ "," +
            "totalScore" + ":" + getTotalScore()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "round = "+(getRound()!=null?Integer.toHexString(System.identityHashCode(getRound())):"null");
  }
}