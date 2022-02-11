/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse.kingdomino.model;

// line 9 "../../../../../KingDomino.ump"
public class Tile
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum LandscapeType { Wheatfield, Lake, Forest, Grass, Mountain, Swamp, All }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tile Attributes
  private LandscapeType landscape;
  private int numberOfCrowns;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tile(LandscapeType aLandscape, int aNumberOfCrowns)
  {
    landscape = aLandscape;
    numberOfCrowns = aNumberOfCrowns;
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

  public boolean setNumberOfCrowns(int aNumberOfCrowns)
  {
    boolean wasSet = false;
    numberOfCrowns = aNumberOfCrowns;
    wasSet = true;
    return wasSet;
  }

  public LandscapeType getLandscape()
  {
    return landscape;
  }

  public int getNumberOfCrowns()
  {
    return numberOfCrowns;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "numberOfCrowns" + ":" + getNumberOfCrowns()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "landscape" + "=" + (getLandscape() != null ? !getLandscape().equals(this)  ? getLandscape().toString().replaceAll("  ","    ") : "this" : "null");
  }
}