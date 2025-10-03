import java.util.*;

public class Door implements Nodeable {
    private String displayid;
    private String inGameid;
    private boolean canMoveTo;
    ListOfNodes type;
    private ArrayList<String> actionList;
    private boolean isLocked;
    private Item key;

    public Door(boolean isOpen, boolean isLocked) {
        if (isOpen) {
            displayid="]";
            inGameid = "Opened Door";
            canMoveTo = true;
            actionList = new ArrayList<String>();
            actionList.add("Close Door");
        }
        else {
            displayid = "[";
            inGameid = "Closed Door";
            canMoveTo = false;
            actionList = new ArrayList<String>();
            actionList.add("Open Door");
        }
        type = ListOfNodes.DOOR;
        this.isLocked = isLocked;
    }

    public Door(boolean isOpen, boolean isLocked, Item key) {
        if (isOpen) {
            displayid="]";
            inGameid = "Opened Door";
            canMoveTo = true;
            actionList = new ArrayList<String>();
            actionList.add("Close Door");
        }
        else {
            displayid = "[";
            inGameid = "Closed Door";
            canMoveTo = false;
            actionList = new ArrayList<String>();
            actionList.add("Open Door");
        }
        type = ListOfNodes.DOOR;
        this.isLocked = isLocked;
        this.key = key;
        if (isLocked) {
            actionList.add("Unlock Door");
        }
        else actionList.add("Lock Door");
    }

    public String getDisplayid() {
        return displayid;
    }

    public String getInGameid() {
        return inGameid;
    }

    public boolean getCanMoveTo() {
        return canMoveTo;
    }


    public boolean[] performAction(String response, Board board, Game game) {
        boolean[] returnArray = {true, false};
        if (response.equals("Open Door")) {
            if (isLocked) {
                System.out.println("This door is locked, you cannot do that.");
                return returnArray;
            }
            else if (!isLocked) {
                canMoveTo = true;
                displayid = "]";
                inGameid = "Opened Door";
                actionList.clear();
                actionList.add("Close Door");
                return returnArray;
            }
        }
        else if (response.equals("Close Door")) {
            canMoveTo = false;
            inGameid = "Closed Door";
            displayid = "[";
            actionList.clear();
            actionList.add("Open Door");
            return returnArray;
        }
        else if (response.equals("Lock Door")) {
            lockDoor();
            return returnArray;
        }
        else if (response.equals("Unlock Door")) {
            if (key!=null) {
                if ((game.getPlayer().getInventory().containsItem(key, "Key"))) {
                    unlockDoor();
                    actionList.remove("Unlock Door");
                    game.getPlayer().getInventory().removeItem(key);
                    System.out.println("Door Unlocked!");
                }
                else {
                    System.out.println("You need a key to unlock the door.");
                }
            }
            else {
                unlockDoor();
            }
            return returnArray;
        }
        else {
            returnArray[0] = false;
            return returnArray;
        }
        return returnArray;
    }


    public ListOfNodes getType() {
        return type;
    }

    public ArrayList<String> getActionList() {
        return actionList;
    }

    public void lockDoor() {
        isLocked = true;
    }

    public void unlockDoor() {
        isLocked = false;
    }
}
