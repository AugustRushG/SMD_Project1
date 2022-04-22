package snakeladder.game;

import java.util.ArrayList;
import java.util.List;

/**
 * diceRoller
 */
public class DiceRoller {
    private List<Die> diceList;
    private NavigationPane np;
    private int gotRolled;

    public DiceRoller(NavigationPane np){
        this.np=np;
        this.diceList=new ArrayList<>();
        this.gotRolled=0;
    }

    public void roll(int nb){
        int size=diceList.size();
        Die dice=new Die(nb,this,size+1);
        diceList.add(dice);
        gotRolled+=nb;

    }

    //check if the index of this dice is the total number
    public void finishedRolling(int index){
        //if equal to the total dice, start moving
        //clear after finished
        if (index==np.getNumberOfDice()){
            np.startMoving(gotRolled);
            reset();
        }
    }

    public void reset(){
        this.gotRolled=0;
        this.diceList.clear();
    }

    public List<Die> getAllDice(){
        return this.diceList;
    }

}
