/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse.kingdomino.model;
import java.util.*;

// line 21 "../../../../../KingDomino.ump"
public class RegularDomino extends Domino
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RegularDomino Attributes
  private int id;
  private int randomOrder;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RegularDomino(int aAngleOfRotation, KingDomino aKingDomino, int aId, int aRandomOrder, Tile... allTiles)
  {
    super(aAngleOfRotation, aKingDomino, allTiles);
    id = aId;
    randomOrder = aRandomOrder;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setRandomOrder(int aRandomOrder)
  {
    boolean wasSet = false;
    randomOrder = aRandomOrder;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public int getRandomOrder()
  {
    return randomOrder;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "randomOrder" + ":" + getRandomOrder()+ "]";
  }
}