import java.util.*;

public class Floor implements Nodeable {

    private String displayid;
    private String inGameid;
    private boolean canMoveTo;
    private ListOfNodes type;
    private ArrayList<String> actionList;


    public Floor() {
        displayid = "-";
        inGameid = "Floor";
        canMoveTo = true;
        type = ListOfNodes.FLOOR;
        actionList = new ArrayList<String>();
    }

    public String getDisplayid() {
        return displayid;
    }

    public boolean[] performAction(String response, Board board, Game game) {
        System.out.println("asd");
        boolean[] returnArray = {false, false};
        return returnArray;
    }

    public String getInGameid() {
        return inGameid;
    }

    public boolean getCanMoveTo() {
        return canMoveTo;
    }

    public ListOfNodes getType() {
        return type;
    }

    public ArrayList<String> getActionList() {
        return actionList;
    }
}