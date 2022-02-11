/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse.kingdomino.model;

// line 41 "../../../../../KingDomino.ump"
public class King
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum KingColor { Yeslow, Blue, Green, Pink }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //King Attributes
  private KingColor kingColor;

  //King Associations
  private Player player;
  private CastleDomino castleDomino;
  private Grid grid;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public King(KingColor aKingColor, Player aPlayer, CastleDomino aCastleDomino, Grid aGrid, Game aGame)
  {
    kingColor = aKingColor;
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create king due to player");
    }
    if (aCastleDomino == null || aCastleDomino.getKing() != null)
    {
      throw new RuntimeException("Unable to create King due to aCastleDomino");
    }
    castleDomino = aCastleDomino;
    boolean didAddGrid = setGrid(aGrid);
    if (!didAddGrid)
    {
      throw new RuntimeException("Unable to create king due to grid");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create king due to game");
    }
  }

  public King(KingColor aKingColor, Player aPlayer, int aAngleOfRotationForCastleDomino, KingDomino aKingDominoForCastleDomino, Tile... allTilesForCastleDomino, Grid aGrid, Game aGame)
  {
    kingColor = aKingColor;
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create king due to player");
    }
    castleDomino = new CastleDomino(aAngleOfRotationForCastleDomino, aKingDominoForCastleDomino, this, allTilesForCastleDomino);
    boolean didAddGrid = setGrid(aGrid);
    if (!didAddGrid)
    {
      throw new RuntimeException("Unable to create king due to grid");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create king due to game");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setKingColor(KingColor aKingColor)
  {
    boolean wasSet = false;
    kingColor = aKingColor;
    wasSet = true;
    return wasSet;
  }

  public KingColor getKingColor()
  {
    return kingColor;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_GetOne */
  public CastleDomino getCastleDomino()
  {
    return castleDomino;
  }
  /* Code from template association_GetOne */
  public Grid getGrid()
  {
    return grid;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    //Must provide player to king
    if (aPlayer == null)
    {
      return wasSet;
    }

    //player already at maximum (2)
    if (aPlayer.numberOfKings() >= Player.maximumNumberOfKings())
    {
      return wasSet;
    }
    
    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      boolean didRemove = existingPlayer.removeKing(this);
      if (!didRemove)
      {
        player = existingPlayer;
        return wasSet;
      }
    }
    player.addKing(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGrid(Grid aGrid)
  {
    boolean wasSet = false;
    //Must provide grid to king
    if (aGrid == null)
    {
      return wasSet;
    }

    //grid already at maximum (2)
    if (aGrid.numberOfKings() >= Grid.maximumNumberOfKings())
    {
      return wasSet;
    }
    
    Grid existingGrid = grid;
    grid = aGrid;
    if (existingGrid != null && !existingGrid.equals(aGrid))
    {
      boolean didRemove = existingGrid.removeKing(this);
      if (!didRemove)
      {
        grid = existingGrid;
        return wasSet;
      }
    }
    grid.addKing(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to king
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (4)
    if (aGame.numberOfKings() >= Game.maximumNumberOfKings())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removeKing(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addKing(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Player placeholderPlayer = player;
    this.player = null;
    if(placeholderPlayer != null)
    {
      placeholderPlayer.removeKing(this);
    }
    CastleDomino existingCastleDomino = castleDomino;
    castleDomino = null;
    if (existingCastleDomino != null)
    {
      existingCastleDomino.delete();
    }
    Grid placeholderGrid = grid;
    this.grid = null;
    if(placeholderGrid != null)
    {
      placeholderGrid.removeKing(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeKing(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "kingColor" + "=" + (getKingColor() != null ? !getKingColor().equals(this)  ? getKingColor().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "castleDomino = "+(getCastleDomino()!=null?Integer.toHexString(System.identityHashCode(getCastleDomino())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "grid = "+(getGrid()!=null?Integer.toHexString(System.identityHashCode(getGrid())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}