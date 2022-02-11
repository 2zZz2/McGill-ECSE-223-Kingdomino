/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse.kingdomino.model;
import java.util.*;

// line 91 "../../../../../KingDomino.ump"
public class Turn
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Turn Attributes
  private int orderOfTurns;

  //Turn Associations
  private List<RegularDomino> dominoInLine;
  private List<King> kingInLine;
  private King currentKing;
  private Turn nextTurn;
  private List<DominoPlacement> dominoPlacements;
  private Round round;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Turn(int aOrderOfTurns, King aCurrentKing, Round aRound, RegularDomino[] allDominoInLine, King[] allKingInLine, DominoPlacement[] allDominoPlacements)
  {
    orderOfTurns = aOrderOfTurns;
    dominoInLine = new ArrayList<RegularDomino>();
    boolean didAddDominoInLine = setDominoInLine(allDominoInLine);
    if (!didAddDominoInLine)
    {
      throw new RuntimeException("Unable to create Turn, must have 3 to 4 dominoInLine");
    }
    kingInLine = new ArrayList<King>();
    boolean didAddKingInLine = setKingInLine(allKingInLine);
    if (!didAddKingInLine)
    {
      throw new RuntimeException("Unable to create Turn, must have 3 to 4 kingInLine");
    }
    if (!setCurrentKing(aCurrentKing))
    {
      throw new RuntimeException("Unable to create Turn due to aCurrentKing");
    }
    dominoPlacements = new ArrayList<DominoPlacement>();
    boolean didAddDominoPlacements = setDominoPlacements(allDominoPlacements);
    if (!didAddDominoPlacements)
    {
      throw new RuntimeException("Unable to create Turn, must have 3 to 4 dominoPlacements");
    }
    boolean didAddRound = setRound(aRound);
    if (!didAddRound)
    {
      throw new RuntimeException("Unable to create turn due to round");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOrderOfTurns(int aOrderOfTurns)
  {
    boolean wasSet = false;
    orderOfTurns = aOrderOfTurns;
    wasSet = true;
    return wasSet;
  }

  public int getOrderOfTurns()
  {
    return orderOfTurns;
  }
  /* Code from template association_GetMany */
  public RegularDomino getDominoInLine(int index)
  {
    RegularDomino aDominoInLine = dominoInLine.get(index);
    return aDominoInLine;
  }

  public List<RegularDomino> getDominoInLine()
  {
    List<RegularDomino> newDominoInLine = Collections.unmodifiableList(dominoInLine);
    return newDominoInLine;
  }

  public int numberOfDominoInLine()
  {
    int number = dominoInLine.size();
    return number;
  }

  public boolean hasDominoInLine()
  {
    boolean has = dominoInLine.size() > 0;
    return has;
  }

  public int indexOfDominoInLine(RegularDomino aDominoInLine)
  {
    int index = dominoInLine.indexOf(aDominoInLine);
    return index;
  }
  /* Code from template association_GetMany */
  public King getKingInLine(int index)
  {
    King aKingInLine = kingInLine.get(index);
    return aKingInLine;
  }

  public List<King> getKingInLine()
  {
    List<King> newKingInLine = Collections.unmodifiableList(kingInLine);
    return newKingInLine;
  }

  public int numberOfKingInLine()
  {
    int number = kingInLine.size();
    return number;
  }

  public boolean hasKingInLine()
  {
    boolean has = kingInLine.size() > 0;
    return has;
  }

  public int indexOfKingInLine(King aKingInLine)
  {
    int index = kingInLine.indexOf(aKingInLine);
    return index;
  }
  /* Code from template association_GetOne */
  public King getCurrentKing()
  {
    return currentKing;
  }
  /* Code from template association_GetOne */
  public Turn getNextTurn()
  {
    return nextTurn;
  }

  public boolean hasNextTurn()
  {
    boolean has = nextTurn != null;
    return has;
  }
  /* Code from template association_GetMany */
  public DominoPlacement getDominoPlacement(int index)
  {
    DominoPlacement aDominoPlacement = dominoPlacements.get(index);
    return aDominoPlacement;
  }

  public List<DominoPlacement> getDominoPlacements()
  {
    List<DominoPlacement> newDominoPlacements = Collections.unmodifiableList(dominoPlacements);
    return newDominoPlacements;
  }

  public int numberOfDominoPlacements()
  {
    int number = dominoPlacements.size();
    return number;
  }

  public boolean hasDominoPlacements()
  {
    boolean has = dominoPlacements.size() > 0;
    return has;
  }

  public int indexOfDominoPlacement(DominoPlacement aDominoPlacement)
  {
    int index = dominoPlacements.indexOf(aDominoPlacement);
    return index;
  }
  /* Code from template association_GetOne */
  public Round getRound()
  {
    return round;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDominoInLine()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfDominoInLine()
  {
    return 4;
  }
  /* Code from template association_AddUnidirectionalMN */
  public boolean addDominoInLine(RegularDomino aDominoInLine)
  {
    boolean wasAdded = false;
    if (dominoInLine.contains(aDominoInLine)) { return false; }
    if (numberOfDominoInLine() < maximumNumberOfDominoInLine())
    {
      dominoInLine.add(aDominoInLine);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeDominoInLine(RegularDomino aDominoInLine)
  {
    boolean wasRemoved = false;
    if (!dominoInLine.contains(aDominoInLine))
    {
      return wasRemoved;
    }

    if (numberOfDominoInLine() <= minimumNumberOfDominoInLine())
    {
      return wasRemoved;
    }

    dominoInLine.remove(aDominoInLine);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalMN */
  public boolean setDominoInLine(RegularDomino... newDominoInLine)
  {
    boolean wasSet = false;
    ArrayList<RegularDomino> verifiedDominoInLine = new ArrayList<RegularDomino>();
    for (RegularDomino aDominoInLine : newDominoInLine)
    {
      if (verifiedDominoInLine.contains(aDominoInLine))
      {
        continue;
      }
      verifiedDominoInLine.add(aDominoInLine);
    }

    if (verifiedDominoInLine.size() != newDominoInLine.length || verifiedDominoInLine.size() < minimumNumberOfDominoInLine() || verifiedDominoInLine.size() > maximumNumberOfDominoInLine())
    {
      return wasSet;
    }

    dominoInLine.clear();
    dominoInLine.addAll(verifiedDominoInLine);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDominoInLineAt(RegularDomino aDominoInLine, int index)
  {  
    boolean wasAdded = false;
    if(addDominoInLine(aDominoInLine))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDominoInLine()) { index = numberOfDominoInLine() - 1; }
      dominoInLine.remove(aDominoInLine);
      dominoInLine.add(index, aDominoInLine);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDominoInLineAt(RegularDomino aDominoInLine, int index)
  {
    boolean wasAdded = false;
    if(dominoInLine.contains(aDominoInLine))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDominoInLine()) { index = numberOfDominoInLine() - 1; }
      dominoInLine.remove(aDominoInLine);
      dominoInLine.add(index, aDominoInLine);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDominoInLineAt(aDominoInLine, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfKingInLine()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfKingInLine()
  {
    return 4;
  }
  /* Code from template association_AddUnidirectionalMN */
  public boolean addKingInLine(King aKingInLine)
  {
    boolean wasAdded = false;
    if (kingInLine.contains(aKingInLine)) { return false; }
    if (numberOfKingInLine() < maximumNumberOfKingInLine())
    {
      kingInLine.add(aKingInLine);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeKingInLine(King aKingInLine)
  {
    boolean wasRemoved = false;
    if (!kingInLine.contains(aKingInLine))
    {
      return wasRemoved;
    }

    if (numberOfKingInLine() <= minimumNumberOfKingInLine())
    {
      return wasRemoved;
    }

    kingInLine.remove(aKingInLine);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalMN */
  public boolean setKingInLine(King... newKingInLine)
  {
    boolean wasSet = false;
    ArrayList<King> verifiedKingInLine = new ArrayList<King>();
    for (King aKingInLine : newKingInLine)
    {
      if (verifiedKingInLine.contains(aKingInLine))
      {
        continue;
      }
      verifiedKingInLine.add(aKingInLine);
    }

    if (verifiedKingInLine.size() != newKingInLine.length || verifiedKingInLine.size() < minimumNumberOfKingInLine() || verifiedKingInLine.size() > maximumNumberOfKingInLine())
    {
      return wasSet;
    }

    kingInLine.clear();
    kingInLine.addAll(verifiedKingInLine);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addKingInLineAt(King aKingInLine, int index)
  {  
    boolean wasAdded = false;
    if(addKingInLine(aKingInLine))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfKingInLine()) { index = numberOfKingInLine() - 1; }
      kingInLine.remove(aKingInLine);
      kingInLine.add(index, aKingInLine);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveKingInLineAt(King aKingInLine, int index)
  {
    boolean wasAdded = false;
    if(kingInLine.contains(aKingInLine))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfKingInLine()) { index = numberOfKingInLine() - 1; }
      kingInLine.remove(aKingInLine);
      kingInLine.add(index, aKingInLine);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addKingInLineAt(aKingInLine, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCurrentKing(King aNewCurrentKing)
  {
    boolean wasSet = false;
    if (aNewCurrentKing != null)
    {
      currentKing = aNewCurrentKing;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setNextTurn(Turn aNewNextTurn)
  {
    boolean wasSet = false;
    nextTurn = aNewNextTurn;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDominoPlacements()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfDominoPlacements()
  {
    return 4;
  }
  /* Code from template association_AddMNToOptionalOne */
  public boolean addDominoPlacement(DominoPlacement aDominoPlacement)
  {
    boolean wasAdded = false;
    if (dominoPlacements.contains(aDominoPlacement)) { return false; }
    if (numberOfDominoPlacements() >= maximumNumberOfDominoPlacements())
    {
      return wasAdded;
    }
    Turn existingTurn = aDominoPlacement.getTurn();
    if (existingTurn != null && existingTurn.numberOfDominoPlacements() <= minimumNumberOfDominoPlacements())
    {
      return wasAdded;
    }
    else if (existingTurn != null)
    {
      existingTurn.dominoPlacements.remove(aDominoPlacement);
    }
    dominoPlacements.add(aDominoPlacement);
    setTurn(aDominoPlacement,this);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDominoPlacement(DominoPlacement aDominoPlacement)
  {
    boolean wasRemoved = false;
    if (dominoPlacements.contains(aDominoPlacement) && numberOfDominoPlacements() > minimumNumberOfDominoPlacements())
    {
      dominoPlacements.remove(aDominoPlacement);
      setTurn(aDominoPlacement,null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_SetMNToOptionalOne */
  public boolean setDominoPlacements(DominoPlacement... newDominoPlacements)
  {
    boolean wasSet = false;
    if (newDominoPlacements.length < minimumNumberOfDominoPlacements() || newDominoPlacements.length > maximumNumberOfDominoPlacements())
    {
      return wasSet;
    }

    ArrayList<DominoPlacement> checkNewDominoPlacements = new ArrayList<DominoPlacement>();
    HashMap<Turn,Integer> turnToNewDominoPlacements = new HashMap<Turn,Integer>();
    for (DominoPlacement aDominoPlacement : newDominoPlacements)
    {
      if (checkNewDominoPlacements.contains(aDominoPlacement))
      {
        return wasSet;
      }
      else if (aDominoPlacement.getTurn() != null && !this.equals(aDominoPlacement.getTurn()))
      {
        Turn existingTurn = aDominoPlacement.getTurn();
        if (!turnToNewDominoPlacements.containsKey(existingTurn))
        {
          turnToNewDominoPlacements.put(existingTurn, new Integer(existingTurn.numberOfDominoPlacements()));
        }
        Integer currentCount = turnToNewDominoPlacements.get(existingTurn);
        int nextCount = currentCount - 1;
        if (nextCount < 3)
        {
          return wasSet;
        }
        turnToNewDominoPlacements.put(existingTurn, new Integer(nextCount));
      }
      checkNewDominoPlacements.add(aDominoPlacement);
    }

    dominoPlacements.removeAll(checkNewDominoPlacements);

    for (DominoPlacement orphan : dominoPlacements)
    {
      setTurn(orphan, null);
    }
    dominoPlacements.clear();
    for (DominoPlacement aDominoPlacement : newDominoPlacements)
    {
      if (aDominoPlacement.getTurn() != null)
      {
        aDominoPlacement.getTurn().dominoPlacements.remove(aDominoPlacement);
      }
      setTurn(aDominoPlacement, this);
      dominoPlacements.add(aDominoPlacement);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_GetPrivate */
  private void setTurn(DominoPlacement aDominoPlacement, Turn aTurn)
  {
    try
    {
      java.lang.reflect.Field mentorField = aDominoPlacement.getClass().getDeclaredField("turn");
      mentorField.setAccessible(true);
      mentorField.set(aDominoPlacement, aTurn);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Issue internally setting aTurn to aDominoPlacement", e);
    }
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDominoPlacementAt(DominoPlacement aDominoPlacement, int index)
  {  
    boolean wasAdded = false;
    if(addDominoPlacement(aDominoPlacement))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDominoPlacements()) { index = numberOfDominoPlacements() - 1; }
      dominoPlacements.remove(aDominoPlacement);
      dominoPlacements.add(index, aDominoPlacement);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDominoPlacementAt(DominoPlacement aDominoPlacement, int index)
  {
    boolean wasAdded = false;
    if(dominoPlacements.contains(aDominoPlacement))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDominoPlacements()) { index = numberOfDominoPlacements() - 1; }
      dominoPlacements.remove(aDominoPlacement);
      dominoPlacements.add(index, aDominoPlacement);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDominoPlacementAt(aDominoPlacement, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setRound(Round aRound)
  {
    boolean wasSet = false;
    //Must provide round to turn
    if (aRound == null)
    {
      return wasSet;
    }

    //round already at maximum (12)
    if (aRound.numberOfTurns() >= Round.maximumNumberOfTurns())
    {
      return wasSet;
    }
    
    Round existingRound = round;
    round = aRound;
    if (existingRound != null && !existingRound.equals(aRound))
    {
      boolean didRemove = existingRound.removeTurn(this);
      if (!didRemove)
      {
        round = existingRound;
        return wasSet;
      }
    }
    round.addTurn(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    dominoInLine.clear();
    kingInLine.clear();
    currentKing = null;
    nextTurn = null;
    while (dominoPlacements.size() > 0)
    {
      DominoPlacement aDominoPlacement = dominoPlacements.get(dominoPlacements.size() - 1);
      aDominoPlacement.delete();
      dominoPlacements.remove(aDominoPlacement);
    }
    
    Round placeholderRound = round;
    this.round = null;
    if(placeholderRound != null)
    {
      placeholderRound.removeTurn(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "orderOfTurns" + ":" + getOrderOfTurns()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "currentKing = "+(getCurrentKing()!=null?Integer.toHexString(System.identityHashCode(getCurrentKing())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "round = "+(getRound()!=null?Integer.toHexString(System.identityHashCode(getRound())):"null");
  }
}