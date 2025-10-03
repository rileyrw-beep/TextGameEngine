import java.util.*;

public interface Nodeable {


    String getDisplayid();

    String getInGameid();

    boolean getCanMoveTo();

    ListOfNodes getType();

    public ArrayList<String> getActionList();

    boolean[] performAction(String response, Board board, Game game);




}