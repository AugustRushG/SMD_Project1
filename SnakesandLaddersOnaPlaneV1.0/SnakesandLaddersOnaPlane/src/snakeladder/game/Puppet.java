package snakeladder.game;

import ch.aplu.jgamegrid.*;
import java.awt.Point;

public class Puppet extends Actor
{
  private GamePane gamePane;
  private NavigationPane navigationPane;
  private int cellIndex = 0;
  private int nbSteps;
  private Connection currentCon = null;
  private int y;
  private int dy;
  private boolean isAuto;
  private String puppetName;

  //modified
  private boolean isLowest = false;
  private boolean isBack = false;

  public void setBack(boolean isBack){
    this.isBack=isBack;
  }

  Puppet(GamePane gp, NavigationPane np, String puppetImage)
  {
    super(puppetImage);
    this.gamePane = gp;
    this.navigationPane = np;
  }

  public boolean isAuto() {
    return isAuto;
  }

  public void setAuto(boolean auto) {
    isAuto = auto;
  }

  public String getPuppetName() {
    return puppetName;
  }

  public void setPuppetName(String puppetName) {
    this.puppetName = puppetName;
  }


  // modified
  void go(int nbSteps)
  {
    if (cellIndex == 100)  // after game over
    {
      cellIndex = 0;
      setLocation(gamePane.startLocation);
    }
    this.nbSteps = nbSteps;
    // task 2, check if it's the lowest
    // steps will be the numberOfDice
    if ((nbSteps==navigationPane.getNumberOfDice())){
      isLowest=true;
    }
    else {
      isLowest=false;
    }

    setActEnabled(true);
  }

  void resetToStartingPoint() {
    cellIndex = 0;
    setLocation(gamePane.startLocation);
    setActEnabled(true);
  }

  int getCellIndex() {
    return cellIndex;
  }


  private void moveToCell(int nbSteps){
    if (nbSteps>0){
      cellIndex++;
    }
    else if (nbSteps<0){
      cellIndex--;
    }
    setLocation(GamePane.cellToLocation(cellIndex));
  }


  public void act()
  {
    if ((cellIndex / 10) % 2 == 0)
    {
      if (isHorzMirror())
        setHorzMirror(false);
    }
    else
    {
      if (!isHorzMirror())
        setHorzMirror(true);
    }

    // Animation: Move on connection
    // has to be not lowest and not going down by the snake
    if (currentCon != null && !(isLowest && currentCon.cellEnd-currentCon.cellStart < 0))
    {
      int x = gamePane.x(y, currentCon);
      setPixelLocation(new Point(x, y));
      y += dy;

      // Check end of connection
      if ((dy > 0 && (y - gamePane.toPoint(currentCon.locEnd).y) > 0)
        || (dy < 0 && (y - gamePane.toPoint(currentCon.locEnd).y) < 0))
      {
        gamePane.setSimulationPeriod(100);
        setActEnabled(false);
        setLocation(currentCon.locEnd);
        cellIndex = currentCon.cellEnd;
        setLocationOffset(new Point(0, 0));
        currentCon = null;
        navigationPane.prepareRoll(cellIndex, false);
      }
      return;
    }

    // Normal movement


    // add negative move method

    if (nbSteps != 0)
    {

      moveToCell(nbSteps);

      if (cellIndex == 100)  // Game over
      {
        setActEnabled(false);
        navigationPane.prepareRoll(cellIndex,false);
        return;
      }

      System.out.println("nbsteps is "+nbSteps);

      if (nbSteps >0) nbSteps--;
      if (nbSteps <0) nbSteps++;

      if (nbSteps == 0)
      {
        // Check if on connection start
        if ((currentCon = gamePane.getConnectionAt(getLocation())) != null
                && !(isLowest && currentCon.cellEnd-currentCon.cellStart < 0))
        {

          gamePane.setSimulationPeriod(50);
          y = gamePane.toPoint(currentCon.locStart).y;
          if (currentCon.locEnd.y > currentCon.locStart.y)
            dy = gamePane.animationStep;
          else
            dy = -gamePane.animationStep;
          if (currentCon instanceof Snake)
          {
            navigationPane.showStatus("Digesting...");
            navigationPane.playSound(GGSound.MMM);
          }
          else
          {
            navigationPane.showStatus("Climbing...");
            navigationPane.playSound(GGSound.BOING);
          }
        }
        else
        {
          System.out.println("this happened isback is "+isBack);
          setActEnabled(false);
          navigationPane.prepareRoll(cellIndex,isBack);
          if (isBack){
            setBack(false);
          }
        }
      }
    }
  }

}
