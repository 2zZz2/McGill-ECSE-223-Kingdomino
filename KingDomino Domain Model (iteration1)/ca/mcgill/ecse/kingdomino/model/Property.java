/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse.kingdomino.model;
import java.util.*;

// line 48 "../../../../../KingDomino.ump"
public class Property
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Property Attributes
  private LandscapeType landscape;
  private int landscapeSize;
  private int numberOfCrowns;
  private int score;

  //Property Associations
  private List<GridCell> gridCells;
  private Grid grid;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Property(LandscapeType aLandscape, int aLandscapeSize, int aNumberOfCrowns, int aScore, Grid aGrid)
  {
    landscape = aLandscape;
    landscapeSize = aLandscapeSize;
    numberOfCrowns = aNumberOfCrowns;
    score = aScore;
    gridCells = new ArrayList<GridCell>();
    boolean didAddGrid = setGrid(aGrid);
    if (!didAddGrid)
    {
      throw new RuntimeException("Unable to create property due to grid");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLandscape(LandscapeType aLandscape)
  {
    boolean wasSet = false;
    landscape = aLandscape;
    wasSet = true;
    return wasSet;
  }

  public boolean setLandscapeSize(int aLandscapeSize)
  {
    boolean wasSet = false;
    landscapeSize = aLandscapeSize;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumberOfCrowns(int aNumberOfCrowns)
  {
    boolean wasSet = false;
    numberOfCrowns = aNumberOfCrowns;
    wasSet = true;
    return wasSet;
  }

  public boolean setScore(int aScore)
  {
    boolean wasSet = false;
    score = aScore;
    wasSet = true;
    return wasSet;
  }

  public LandscapeType getLandscape()
  {
    return landscape;
  }

  public int getLandscapeSize()
  {
    return landscapeSize;
  }

  public int getNumberOfCrowns()
  {
    return numberOfCrowns;
  }

  public int getScore()
  {
    return score;
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
  /* Code from template association_GetOne */
  public Grid getGrid()
  {
    return grid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGridCells()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addGridCell(GridCell aGridCell)
  {
    boolean wasAdded = false;
    if (gridCells.contains(aGridCell)) { return false; }
    gridCells.add(aGridCell);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGridCell(GridCell aGridCell)
  {
    boolean wasRemoved = false;
    if (gridCells.contains(aGridCell))
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
  /* Code from template association_SetOneToMany */
  public boolean setGrid(Grid aGrid)
  {
    boolean wasSet = false;
    if (aGrid == null)
    {
      return wasSet;
    }

    Grid existingGrid = grid;
    grid = aGrid;
    if (existingGrid != null && !existingGrid.equals(aGrid))
    {
      existingGrid.removeProperty(this);
    }
    grid.addProperty(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    gridCells.clear();
    Grid placeholderGrid = grid;
    this.grid = null;
    if(placeholderGrid != null)
    {
      placeholderGrid.removeProperty(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "landscapeSize" + ":" + getLandscapeSize()+ "," +
            "numberOfCrowns" + ":" + getNumberOfCrowns()+ "," +
            "score" + ":" + getScore()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "landscape" + "=" + (getLandscape() != null ? !getLandscape().equals(this)  ? getLandscape().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "grid = "+(getGrid()!=null?Integer.toHexString(System.identityHashCode(getGrid())):"null");
  }
}