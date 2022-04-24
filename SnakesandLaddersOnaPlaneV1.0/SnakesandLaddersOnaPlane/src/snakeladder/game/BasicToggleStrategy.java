package snakeladder.game;

import ch.aplu.jgamegrid.Location;

public class BasicToggleStrategy implements ToggleStrategy{
    public boolean checkIfToggle(NavigationPane np, GamePane gp){
        int numberOfDice = np.getNumberOfDice();
        int nextPlayer = gp.getCurrentPuppetIndex() == 0 ? 1 : 0;
        int snakeCount = 0;
        int ladderCount = 0;
        int currentCell = gp.getAllPuppets().get(nextPlayer).getCellIndex();
        for (int i = 0; i < currentCell + numberOfDice * 6; i++){
            if (gp.getConnectionAt(new Location(gp.cellToLocation(i))) instanceof Snake){
                snakeCount += 1;
                System.out.println(snakeCount);
            }
            if (gp.getConnectionAt(new Location(gp.cellToLocation(i))) instanceof Ladder){
                ladderCount += 1;
                System.out.println(ladderCount);
            }
        }

        return (ladderCount >= snakeCount);
    }
}
