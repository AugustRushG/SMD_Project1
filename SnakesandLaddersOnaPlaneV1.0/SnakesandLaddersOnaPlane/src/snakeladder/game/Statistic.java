package snakeladder.game;
import java.util.Map;
import java.util.HashMap;

public class Statistic{
    String playerName;
    Map<Integer,Integer> rollMap = new HashMap<>();
    int travelledUp;
    int travelledDown;

    public Statistic(String playerName){
        this.playerName = playerName;
    }

    public void rolled(int nb){
        if (rollMap.containsKey(nb)){
            rollMap.put(nb, rollMap.get(nb) + 1);
        } else {
            rollMap.put(nb, 1);
        }
    }

    public void up(){
        travelledUp++;
    }

    public void down(){
        travelledDown++;
    }

    public String toStringDice(){
        String stats = String.format("%s rolled: ", playerName);
        for (Map.Entry<Integer,Integer> entry : rollMap.entrySet()){
            String k = entry.getKey().toString();
            String v = entry.getValue().toString();
            String line = String.format("%s-%s ", k, v);
            stats+=line;
        }

        return stats;
    }

    public String toStringConnection(){
        String stats = String.format("%s traversed: up-%d, down-%d",playerName, travelledUp, travelledDown);
        return stats;
    }
}