/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse.kingdomino.model;

// line 35 "../../../../../KingDomino.ump"
public class GridCell
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GridCell Attributes
  private int xCoordinate;
  private int yCoordinate;

  //GridCell Associations
  private Tile tile;
  private Grid grid;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GridCell(int aXCoordinate, int aYCoordinate, Tile aTile, Grid aGrid)
  {
    xCoordinate = aXCoordinate;
    yCoordinate = aYCoordinate;
    if (!setTile(aTile))
    {
      throw new RuntimeException("Unable to create GridCell due to aTile");
    }
    boolean didAddGrid = setGrid(aGrid);
    if (!didAddGrid)
    {
      throw new RuntimeException("Unable to create gridCell due to grid");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setXCoordinate(int aXCoordinate)
  {
    boolean wasSet = false;
    xCoordinate = aXCoordinate;
    wasSet = true;
    return wasSet;
  }

  public boolean setYCoordinate(int aYCoordinate)
  {
    boolean wasSet = false;
    yCoordinate = aYCoordinate;
    wasSet = true;
    return wasSet;
  }

  public int getXCoordinate()
  {
    return xCoordinate;
  }

  public int getYCoordinate()
  {
    return yCoordinate;
  }
  /* Code from template association_GetOne */
  public Tile getTile()
  {
    return tile;
  }
  /* Code from template association_GetOne */
  public Grid getGrid()
  {
    return grid;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setTile(Tile aNewTile)
  {
    boolean wasSet = false;
    if (aNewTile != null)
    {
      tile = aNewTile;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGrid(Grid aGrid)
  {
    boolean wasSet = false;
    //Must provide grid to gridCell
    if (aGrid == null)
    {
      return wasSet;
    }

    //grid already at maximum (25)
    if (aGrid.numberOfGridCells() >= Grid.maximumNumberOfGridCells())
    {
      return wasSet;
    }
    
    Grid existingGrid = grid;
    grid = aGrid;
    if (existingGrid != null && !existingGrid.equals(aGrid))
    {
      boolean didRemove = existingGrid.removeGridCell(this);
      if (!didRemove)
      {
        grid = existingGrid;
        return wasSet;
      }
    }
    grid.addGridCell(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    tile = null;
    Grid placeholderGrid = grid;
    this.grid = null;
    if(placeholderGrid != null)
    {
      placeholderGrid.removeGridCell(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "xCoordinate" + ":" + getXCoordinate()+ "," +
            "yCoordinate" + ":" + getYCoordinate()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "tile = "+(getTile()!=null?Integer.toHexString(System.identityHashCode(getTile())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "grid = "+(getGrid()!=null?Integer.toHexString(System.identityHashCode(getGrid())):"null");
  }
}