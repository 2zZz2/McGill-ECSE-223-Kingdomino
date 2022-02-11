/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse.kingdomino.model;
import java.util.*;

// line 16 "../../../../../KingDomino.ump"
public class Domino
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Domino Attributes
  private int angleOfRotation;

  //Domino Associations
  private List<Tile> tiles;
  private KingDomino kingDomino;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Domino(int aAngleOfRotation, KingDomino aKingDomino, Tile... allTiles)
  {
    angleOfRotation = aAngleOfRotation;
    tiles = new ArrayList<Tile>();
    boolean didAddTiles = setTiles(allTiles);
    if (!didAddTiles)
    {
      throw new RuntimeException("Unable to create Domino, must have 1 to 2 tiles");
    }
    boolean didAddKingDomino = setKingDomino(aKingDomino);
    if (!didAddKingDomino)
    {
      throw new RuntimeException("Unable to create domino due to kingDomino");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAngleOfRotation(int aAngleOfRotation)
  {
    boolean wasSet = false;
    angleOfRotation = aAngleOfRotation;
    wasSet = true;
    return wasSet;
  }

  public int getAngleOfRotation()
  {
    return angleOfRotation;
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
  /* Code from template association_GetOne */
  public KingDomino getKingDomino()
  {
    return kingDomino;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTiles()
  {
    return 1;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfTiles()
  {
    return 2;
  }
  /* Code from template association_AddUnidirectionalMN */
  public boolean addTile(Tile aTile)
  {
    boolean wasAdded = false;
    if (tiles.contains(aTile)) { return false; }
    if (numberOfTiles() < maximumNumberOfTiles())
    {
      tiles.add(aTile);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeTile(Tile aTile)
  {
    boolean wasRemoved = false;
    if (!tiles.contains(aTile))
    {
      return wasRemoved;
    }

    if (numberOfTiles() <= minimumNumberOfTiles())
    {
      return wasRemoved;
    }

    tiles.remove(aTile);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalMN */
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

    if (verifiedTiles.size() != newTiles.length || verifiedTiles.size() < minimumNumberOfTiles() || verifiedTiles.size() > maximumNumberOfTiles())
    {
      return wasSet;
    }

    tiles.clear();
    tiles.addAll(verifiedTiles);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTileAt(Tile aTile, int index)
  {  
    boolean wasAdded = false;
    if(addTile(aTile))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTiles()) { index = numberOfTiles() - 1; }
      tiles.remove(aTile);
      tiles.add(index, aTile);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTileAt(Tile aTile, int index)
  {
    boolean wasAdded = false;
    if(tiles.contains(aTile))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTiles()) { index = numberOfTiles() - 1; }
      tiles.remove(aTile);
      tiles.add(index, aTile);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTileAt(aTile, index);
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
      existingKingDomino.removeDomino(this);
    }
    kingDomino.addDomino(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    tiles.clear();
    KingDomino placeholderKingDomino = kingDomino;
    this.kingDomino = null;
    if(placeholderKingDomino != null)
    {
      placeholderKingDomino.removeDomino(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "angleOfRotation" + ":" + getAngleOfRotation()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "kingDomino = "+(getKingDomino()!=null?Integer.toHexString(System.identityHashCode(getKingDomino())):"null");
  }
}