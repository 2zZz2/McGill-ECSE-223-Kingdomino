/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse.kingdomino.model;
import java.util.*;

// line 28 "../../../../../KingDomino.ump"
public class CastleDomino extends Domino
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CastleDomino Associations
  private King king;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CastleDomino(int aAngleOfRotation, KingDomino aKingDomino, King aKing, Tile... allTiles)
  {
    super(aAngleOfRotation, aKingDomino, allTiles);
    if (aKing == null || aKing.getCastleDomino() != null)
    {
      throw new RuntimeException("Unable to create CastleDomino due to aKing");
    }
    king = aKing;
  }

  public CastleDomino(int aAngleOfRotation, KingDomino aKingDomino, KingColor aKingColorForKing, Player aPlayerForKing, Grid aGridForKing, Game aGameForKing, Tile... allTiles)
  {
    super(aAngleOfRotation, aKingDomino, allTiles);
    king = new King(aKingColorForKing, aPlayerForKing, this, aGridForKing, aGameForKing);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public King getKing()
  {
    return king;
  }

  public void delete()
  {
    King existingKing = king;
    king = null;
    if (existingKing != null)
    {
      existingKing.delete();
    }
    super.delete();
  }

}