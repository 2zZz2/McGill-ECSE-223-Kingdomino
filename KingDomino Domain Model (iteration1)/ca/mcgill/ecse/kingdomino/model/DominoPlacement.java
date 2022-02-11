/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse.kingdomino.model;
import java.util.*;

// line 66 "../../../../../KingDomino.ump"
public class DominoPlacement
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DominoPlacement Attributes
  private boolean isValid;
  private int tempPositionX1;
  private int tempPositionY1;
  private int tempPositionX2;
  private int tempPositionY2;

  //DominoPlacement Associations
  private Grid grid;
  private RegularDomino regularDomino;
  private List<Tile> tiles;
  private List<GridCell> gridCells;
  private King king;
  private Turn turn;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DominoPlacement(boolean aIsValid, int aTempPositionX1, int aTempPositionY1, int aTempPositionX2, int aTempPositionY2, Grid aGrid, RegularDomino aRegularDomino, King aKing, Tile[] allTiles, GridCell[] allGridCells)
  {
    isValid = aIsValid;
    tempPositionX1 = aTempPositionX1;
    tempPositionY1 = aTempPositionY1;
    tempPositionX2 = aTempPositionX2;
    tempPositionY2 = aTempPositionY2;
    if (!setGrid(aGrid))
    {
      throw new RuntimeException("Unable to create DominoPlacement due to aGrid");
    }
    if (!setRegularDomino(aRegularDomino))
    {
      throw new RuntimeException("Unable to create DominoPlacement due to aRegularDomino");
    }
    tiles = new ArrayList<Tile>();
    boolean didAddTiles = setTiles(allTiles);
    if (!didAddTiles)
    {
      throw new RuntimeException("Unable to create DominoPlacement, must have 2 tiles");
    }
    gridCells = new ArrayList<GridCell>();
    boolean didAddGridCells = setGridCells(allGridCells);
    if (!didAddGridCells)
    {
      throw new RuntimeException("Unable to create DominoPlacement, must have 2 gridCells");
    }
    if (!setKing(aKing))
    {
      throw new RuntimeException("Unable to create DominoPlacement due to aKing");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsValid(boolean aIsValid)
  {
    boolean wasSet = false;
    isValid = aIsValid;
    wasSet = true;
    return wasSet;
  }

  public boolean setTempPositionX1(int aTempPositionX1)
  {
    boolean wasSet = false;
    tempPositionX1 = aTempPositionX1;
    wasSet = true;
    return wasSet;
  }

  public boolean setTempPositionY1(int aTempPositionY1)
  {
    boolean wasSet = false;
    tempPositionY1 = aTempPositionY1;
    wasSet = true;
    return wasSet;
  }

  public boolean setTempPositionX2(int aTempPositionX2)
  {
    boolean wasSet = false;
    tempPositionX2 = aTempPositionX2;
    wasSet = true;
    return wasSet;
  }

  public boolean setTempPositionY2(int aTempPositionY2)
  {
    boolean wasSet = false;
    tempPositionY2 = aTempPositionY2;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsValid()
  {
    return isValid;
  }

  public int getTempPositionX1()
  {
    return tempPositionX1;
  }

  public int getTempPositionY1()
  {
    return tempPositionY1;
  }

  public int getTempPositionX2()
  {
    return tempPositionX2;
  }

  public int getTempPositionY2()
  {
    return tempPositionY2;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsValid()
  {
    return isValid;
  }
  /* Code from template association_GetOne */
  public Grid getGrid()
  {
    return grid;
  }
  /* Code from template association_GetOne */
  public RegularDomino getRegularDomino()
  {
    return regularDomino;
  }
  /* Code from template association_GetMany */
  public Tile getTile(int index)
  {
    Tile aTile = tiles.get(index);
    return aTile;
  }

  public List<Tile> getTiles()
  {
    List<Tile> newTiles = Collections.unmodifiableList(tiles);
    return newTiles;
  }

  public int numberOfTiles()
  {
    int number = tiles.size();
    return number;
  }

  public boolean hasTiles()
  {
    boolean has = tiles.size() > 0;
    return has;
  }

  public int indexOfTile(Tile aTile)
  {
    int index = tiles.indexOf(aTile);
    return index;
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
  public King getKing()
  {
    return king;
  }
  /* Code from template association_GetOne */
  public Turn getTurn()
  {
    return turn;
  }

  public boolean hasTurn()
  {
    boolean has = turn != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setGrid(Grid aNewGrid)
  {
    boolean wasSet = false;
    if (aNewGrid != null)
    {
      grid = aNewGrid;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setRegularDomino(RegularDomino aNewRegularDomino)
  {
    boolean wasSet = false;
    if (aNewRegularDomino != null)
    {
      regularDomino = aNewRegularDomino;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfTiles()
  {
    return 2;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTiles()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfTiles()
  {
    return 2;
  }
  /* Code from template association_SetUnidirectionalN */
  public boolean setTiles(Tile... newTiles)
  {
    boolean wasSet = false;
    ArrayList<Tile> verifiedTiles = new ArrayList<Tile>();
    for (Tile aTile : newTiles)
    {
      if (verifiedTiles.contains(aTile))
      {
        continue;
      }
      verifiedTiles.add(aTile);
    }

    if (verifiedTiles.size() != newTiles.length || verifiedTiles.size() != requiredNumberOfTiles())
    {
      return wasSet;
    }

    tiles.clear();
    tiles.addAll(verifiedTiles);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfGridCells()
  {
    return 2;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGridCells()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfGridCells()
  {
    return 2;
  }
  /* Code from template association_SetUnidirectionalN */
  public boolean setGridCells(GridCell... newGridCells)
  {
    boolean wasSet = false;
    ArrayList<GridCell> verifiedGridCells = new ArrayList<GridCell>();
    for (GridCell aGridCell : newGridCells)
    {
      if (verifiedGridCells.contains(aGridCell))
      {
        continue;
      }
      verifiedGridCells.add(aGridCell);
    }

    if (verifiedGridCells.size() != newGridCells.length || verifiedGridCells.size() != requiredNumberOfGridCells())
    {
      return wasSet;
    }

    gridCells.clear();
    gridCells.addAll(verifiedGridCells);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setKing(King aNewKing)
  {
    boolean wasSet = false;
    if (aNewKing != null)
    {
      king = aNewKing;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    grid = null;
    regularDomino = null;
    tiles.clear();
    gridCells.clear();
    king = null;
    if (turn != null)
    {
        Turn placeholderTurn = turn;
        this.turn = null;
        placeholderTurn.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isValid" + ":" + getIsValid()+ "," +
            "tempPositionX1" + ":" + getTempPositionX1()+ "," +
            "tempPositionY1" + ":" + getTempPositionY1()+ "," +
            "tempPositionX2" + ":" + getTempPositionX2()+ "," +
            "tempPositionY2" + ":" + getTempPositionY2()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "grid = "+(getGrid()!=null?Integer.toHexString(System.identityHashCode(getGrid())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "regularDomino = "+(getRegularDomino()!=null?Integer.toHexString(System.identityHashCode(getRegularDomino())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "king = "+(getKing()!=null?Integer.toHexString(System.identityHashCode(getKing())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "turn = "+(getTurn()!=null?Integer.toHexString(System.identityHashCode(getTurn())):"null");
  }
}