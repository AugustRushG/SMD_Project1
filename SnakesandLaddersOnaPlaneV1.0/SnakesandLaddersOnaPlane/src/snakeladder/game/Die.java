package snakeladder.game;

import ch.aplu.jgamegrid.Actor;

public class Die extends Actor
{

  private int nb;
  private DiceRoller diceRoller;
  private int index;

  Die(int nb, DiceRoller diceRoller, int index )
  {
    super("sprites/pips" + nb + ".gif", 7);

    this.diceRoller=diceRoller;
    this.index=index;
    this.nb=nb;
  }

  //start moving once the roll is over
  public void act()
  {
    showNextSprite();
    if (getIdVisible() == 6)
    {
      setActEnabled(false);

      //tell dice roller this dice is finished
      diceRoller.finishedRolling(index);

    }
  }

}
