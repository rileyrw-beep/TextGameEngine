import java.util.*;

public class Prompt {

    private Nodeable currentNorth;
    private Nodeable currentEast;
    private Nodeable currentSouth;
    private Nodeable currentWest;

    private Boolean inBattle;

    private ArrayList<String> northActionList;
    private ArrayList<String> eastActionList;
    private ArrayList<String> southActionList;
    private ArrayList<String> westActionList;

    private ArrayList<Nodeable> npcList;


    public Prompt() {
        currentNorth = null;
        currentEast = null;
        currentSouth = null;
        currentWest = null;
        northActionList = null;
        eastActionList = null;
        southActionList = null;
        westActionList = null;
        inBattle = false;
        npcList = new ArrayList<Nodeable>();

    }

    public void enterBattle() {
        inBattle = true;
    }

    public void exitBattle() {
        inBattle = false;
    }

    public boolean[] help() {
        boolean[] help = new boolean[2];
        help[0] = true;
        help[1] = false;
        System.out.println("In this game, there are several universal actions: ");
        System.out.println("Moving in any direction (North, South, East, and West). Just simply type 'Move' + any direction.");
        System.out.println("Accessing the Legend. Just simply type 'Legend'.");
        System.out.println("And accessing your Inventory. Just simply type 'Inventory'.");
        System.out.println();
        return help;
    }

    public void clearNPCList() {
        npcList.clear();
    }

    public void fillNPCList(Board board, Player player) {
        ArrayList<ArrayList<Nodeable>> nodeList = board.getBoard();
        for (int i = 0; i < nodeList.size(); i++) {
            for (int k = 0; k < nodeList.get(i).size(); k++) {
                if (nodeList.get(i).get(k).getType() == ListOfNodes.NPC) {
                    npcList.add(nodeList.get(i).get(k));
                }
            }
        }


    }

    //different prompts:
    public void displayActions(Map map, Player player, Game game) {
        Board board = map.getCurrentBoard();
        ArrayList<ArrayList<Board>> mapList = map.getMapBoard();
        int x = board.getCharPosX();
        int y = board.getCharPosY();
        ArrayList<ArrayList<Nodeable>> boardList = board.getBoard();

        Wall wall = new Wall();
        if (y != 0) currentNorth = boardList.get(y - 1).get(x);
        else {
            Board desiredBoard = mapList.get(map.getCurrentBoardY() - 1).get(map.getCurrentBoardX());
            ArrayList<ArrayList<Nodeable>> desiredBoardList = desiredBoard.getBoard();
            currentNorth = desiredBoardList.get(9).get(x);
        }

        if (x != 9) currentEast = boardList.get(y).get(x + 1);
        else {
            Board desiredBoard = mapList.get(map.getCurrentBoardY()).get(map.getCurrentBoardX() + 1);
            ArrayList<ArrayList<Nodeable>> desiredBoardList = desiredBoard.getBoard();
            currentEast = desiredBoardList.get(y).get(0);
        }

        if (y != 9) currentSouth = boardList.get(y + 1).get(x);
        else {
            Board desiredBoard = mapList.get(map.getCurrentBoardY() + 1).get(map.getCurrentBoardX());
            ArrayList<ArrayList<Nodeable>> desiredBoardList = desiredBoard.getBoard();
            currentSouth = desiredBoardList.get(0).get(x);
        }

        if (x != 0) currentWest = boardList.get(y).get(x - 1);
        else {
            Board desiredBoard = mapList.get(map.getCurrentBoardY()).get(map.getCurrentBoardX() - 1);
            ArrayList<ArrayList<Nodeable>> desiredBoardList = desiredBoard.getBoard();
            currentWest = desiredBoardList.get(y).get(9);
        }


        northActionList = currentNorth.getActionList();
        eastActionList = currentEast.getActionList();
        southActionList = currentSouth.getActionList();
        westActionList = currentWest.getActionList();


        if (!northActionList.isEmpty()) {
            for (int i = 0; i < northActionList.size(); i++) {
                System.out.println("- " + northActionList.get(i));
            }
        }
        if (player.checkNorth(board)) {
            System.out.println("- Move North");
        }
        if (!northActionList.isEmpty() || player.checkNorth(board)) {
            //System.out.println();
        }


        if (!eastActionList.isEmpty()) {
            for (int i = 0; i < eastActionList.size(); i++) {
                System.out.println("- " + eastActionList.get(i));
            }
        }
        if (player.checkEast(board)) {
            System.out.println("- Move East");
        }
        if (!eastActionList.isEmpty() || player.checkEast(board)) {
            //System.out.println();
        }


        if (!southActionList.isEmpty()) {
            for (int i = 0; i < southActionList.size(); i++) {
                System.out.println("- " + southActionList.get(i));
            }
        }
        if (player.checkSouth(board)) {
            System.out.println("- Move South");
        }
        if (!southActionList.isEmpty() || player.checkSouth(board)) {
            //System.out.println();
        }


        if (!westActionList.isEmpty()) {
            for (int i = 0; i < westActionList.size(); i++) {
                System.out.println("- " + westActionList.get(i));
            }
        }
        if (player.checkWest(board)) {
            System.out.println("- Move West");
        }
        if (!westActionList.isEmpty() || player.checkWest(board)) {
            //System.out.println();
        }


    }

    public boolean[] doAction(String response, Map map, Player player, Game game) {
        Board board = map.getCurrentBoard();
        if (response.length() > 4) {
            if (response.substring(0, 4).equals("Move")) {
                return (player.performAction(response, board, game));
            }
        }
        if (response.length() == 1) return player.performAction(response, board, game);
        if (response.equals("No Fast")) {
            game.setTimeNumber(10000);
        }
        if (response.equals("Help")) {
            return help();
        }
        if (response.equals("Legend")) {
            board.printLegend();
            return new boolean[]{true, false};
        }
        if (response.equals("Inventory")) {
            System.out.println("Inventory: ");
            System.out.println();
            player.getInventory().printInventory();
            System.out.println();

            String get = "";

            if (!player.getInventory().getItems().isEmpty()) {
                outerOuterLoop:
                while (true) {
                    System.out.print("Type the name of the item you would like to see the details of: ");
                    outerLoop:
                    while (true) {
                        get = game.getInput().nextLine();
                        for (int i = 0; i < player.getInventory().getItems().size(); i++) {
                            if (get.equals(player.getInventory().getItems().get(i).getItemName())) {
                                System.out.println();
                                System.out.println();
                                System.out.println(player.getInventory().getItems().get(i).getItemDescription());
                                System.out.println();
                                break outerLoop;
                            }
                        }
                        System.out.println();
                        System.out.println();
                        System.out.print("Please enter a valid item: ");
                    }
                    System.out.println("Would you like to see the details of another item?");
                    ArrayList<String> options = new ArrayList<>(Arrays.asList("Yes", "No"));
                    String get2 = game.basicGameLoop("", options);
                    if (get2.equals("No")) {
                        break outerOuterLoop;
                    }
                    System.out.println();
                    player.getInventory().printInventory();
                    System.out.println();
                }
            } else {
                System.out.println("Your inventory is currently empty.");
                game.time(3);
            }
            return new boolean[]{true, false};
        }

        ArrayList<ArrayList<String>> listofLists = new ArrayList<ArrayList<String>>();
        listofLists.add(northActionList);
        listofLists.add(eastActionList);
        listofLists.add(southActionList);
        listofLists.add(westActionList);

        int longest = 0;
        for (int i = 0; i < 4; i++) {
            if (longest < listofLists.get(i).size()) {
                longest = listofLists.get(i).size();
            }
        }

        for (int i = 0; i < longest; i++) {
            if (i < northActionList.size()) {
                if (northActionList.get(i).equals(response)) {
                    return (currentNorth.performAction(response, board, game));
                }
            }
            if (i < eastActionList.size()) {
                if (eastActionList.get(i).equals(response)) {
                    return (currentEast.performAction(response, board, game));
                }
            }
            if (i < southActionList.size()) {
                if (southActionList.get(i).equals(response)) {
                    return (currentSouth.performAction(response, board, game));

                }
            }
            if (i < westActionList.size()) {
                if (westActionList.get(i).equals(response)) {
                    return (currentWest.performAction(response, board, game));

                }
            }
        }

        return new boolean[]{false, false};
    }

    public boolean[] displayBattleActions(Board board, Player player, Game game) {
        boolean[] returnArray = {false, true};
        //default return array, only runs to end stuff because if it is run and not changed, something went wrong.
        boolean startFromBattle = false;
        while (game.getCurrentChapter() == 1 && game.getCurrentAct()[game.getCurrentChapter() - 1] == 1) {
            returnArray = battleOneOne(game, player, board, startFromBattle);
            if (returnArray[0] == false && returnArray[1] == false) {
                //aka restart battle , it won't run the return statement
                startFromBattle = true;
            } else {
                //two options: false true : restart the act
                // or true false, we all good.
                return returnArray;
            }
        }
        return returnArray;
    }

    //the battles
    public boolean[] battleOneOne(Game game, Player player, Board board, boolean startFromBattle) {
        boolean[] returnArray = {true, false};

        if (!startFromBattle) {
            //implementation goes here for pre battle dialouge that should be skipped if battle retried.
            startFromBattle = true;
        }

        if (startFromBattle) {
            //implementation of battle, uses many basic game loops
        }
        return returnArray;
    }


}
