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
            System.out.println();
            System.out.println("...");
            System.out.println();
            game.time(3);

            System.out.println("As you near the hobo, you catch a glance at his face. You note that it looks oddly familiar but you cannot put your finger on it.");
            System.out.println();
            game.time(4);

            System.out.println("You hear that he is muttering nonsense to himself. Something about birds and technology beyond your wildest dreams.");
            System.out.println();
            game.time(4);

            System.out.println("Game Tip: Text followed by a name and the symbol '*' is considered Dialogue.");
            System.out.println();
            System.out.println();
            game.time(2);


            System.out.println("Hobo (muttering) * 'Wings, if only I had wings...'");
            System.out.println();
            game.time(3);

            System.out.println("Dalton * 'Sorry, I didn't catch that.'");
            System.out.println();
            game.time(3);

            System.out.println("Hobo (aggravated) * 'YO WHO DO YOU THINK YOU'RE TALKIN' TO!'");
            System.out.println();
            game.time(3);

            System.out.println("Dalton (worried) * 'Woah woah man, I don't mean no harm.'");
            System.out.println();
            System.out.println();
            game.time(3);

            startFromBattle = true;
        }

        if (startFromBattle) {
            System.out.println("But the hobo did in fact mean harm. He charges at you with a vicious intent in his eyes.");
            System.out.println();
            game.time(3);

            System.out.println("Game Tip: Battling. This is your first battle, type out the actions as normal to do them.");
            System.out.println();
            game.time(4);

            System.out.println("Remember, your choice affects the course of the story.");
            System.out.println();
            game.time(3);

            ArrayList<String> hoboBattleList = new ArrayList<>();
            hoboBattleList.add("Dodge the Hobo");
            hoboBattleList.add("Charge Back");
            hoboBattleList.add("Distract the Hobo");
            hoboBattleList.add("Run Away");

            game.endText();
            String response = game.basicGameLoop("Battle: Dalton V Hobo", hoboBattleList);

            if (response.equals("Dodge the Hobo")) {
                System.out.println();
                System.out.println("...");
                System.out.println();
                game.time(3);

                System.out.println("You attempt to jump out of the way, but not having done any physical activity for years makes you too slow.");
                System.out.println();
                game.time(3);

                return game.gameOver("The hobo knocks you to ground and you lose consciousness on impact. You wake up several hours later with your shoes stolen and you miss the Tech Fair.", true, Ending.BAD_ENDING);

            } else if (response.equals("Run Away")) {
                System.out.println();
                System.out.println("...");
                System.out.println();
                game.time(3);

                System.out.println("You attempt to run away, and for a bit it works.");
                System.out.println();
                game.time(2);

                System.out.println("But, halfway back to your room you fall down, out of breath.");
                System.out.println();
                game.time(3);

                return game.gameOver("The hobo catches up to you and you are too exhausted to fight back. The hobo knocks you out and you wake up several hours later with your shoes stolen and you miss the Tech Fair.", true, Ending.BAD_ENDING);

            } else if (response.equals("Charge Back")) {
                System.out.println();
                System.out.println("...");
                System.out.println();
                game.time(3);

                System.out.println("As the hobo furiously charges at you, you decide to do the same.");
                System.out.println();
                game.time(2);

                System.out.println("Both you and the hobo run head first into each other.");
                System.out.println();
                game.time(2);

                System.out.println("Ouch.");
                System.out.println();
                game.time(1);

            } else if (response.equals("Distract the Hobo")) {
                System.out.println();
                System.out.println("...");
                System.out.println();
                game.time(3);

                System.out.println("As the hobo furiously charges at you, you have the bright idea to distract him.");
                System.out.println();
                game.time(3);

                System.out.println("You point behind the hobo.");
                System.out.println();
                System.out.println();
                game.time(2);

                System.out.println("Dalton (yelling) * 'What's that!'");
                System.out.println();
                System.out.println();
                game.time(2);

                System.out.println("The hobo momentarily stops in his tracks and cartoonishly looks behind him.");
                System.out.println();
                game.time(3);

                System.out.println("You use that time to throw a punch at the hobo's face.");
                System.out.println();
                game.time(2);

                System.out.println("However, seeming as you have never thrown a punch at anyone up until this point, it seems your attack hurt your hand just as much as it hurt the hobo.");
                System.out.println();
                game.time(4);

            }

            System.out.println();
            System.out.println("...");
            System.out.println();
            game.time(3);

            System.out.println("Both parties fall down to the ground in pain");
            System.out.println();
            game.time(3);

            System.out.println("The series of events that happen next can only be described as what some call a 'flurry.'");
            System.out.println();
            game.time(4);

            System.out.println("You grab his leg,");
            game.time(2);

            System.out.println("and he grabs your esophagus,");
            game.time(2);

            System.out.println("and you bite off his ear,");
            game.time(2);

            System.out.println("and he chews off your eyebrows,");
            game.time(2);

            System.out.println("and you take out his appendix,");
            game.time(2);

            System.out.println("and he gives you a colonic irrigation, yes indeed you better believe it.");
            System.out.println();
            game.time(3);

            System.out.println("After the tussle (that was so violent that the only thing an onlooker would have seen was erratic cloud of dust and appendages) you and the hobo regained your footing.");
            System.out.println();
            game.time(6);

            System.out.println("Both standing at about equal positions, each one knew that the next action would decide the winner.");
            System.out.println();
            game.time(4);

            System.out.println("You, knowing that you cannot disappoint your countless fans and not deliver on the next Hype 2 update, reach into your pocked and pull out what will be the deciding key to your victory: ");
            System.out.println();

            ArrayList<String> pocketItems = new ArrayList<>();
            pocketItems.add("Pocket Knife");
            pocketItems.add(".22 Magnum");
            pocketItems.add("A Bucket of KFC?");
            if (player.getInventory().containsItem(null, "Crazy Soda")) pocketItems.add("Crazy Soda");

            response = game.basicGameLoop("", pocketItems);
            if (response.equals("Pocket Knife")) {
                System.out.println();
                System.out.println("...");
                System.out.println();
                game.time(3);

                System.out.println("As you hold the pocket knife in your hand you feel your confidence return to you.");
                System.out.println();
                game.time(3);

                System.out.println("Knowing you have a weapon and he doesn't, you become over confident and rush at the hobo.");
                System.out.println();
                game.time(3);

                System.out.println("However, what you did not expect was that the hobo had a hand grenade, that which he pulled the pin and dropped it.");
                System.out.println();
                game.time(3);

                return game.gameOver("The explosion kills the both of you and you never get to go to the Tech Fair or make the next Hype 2 update.", true, Ending.BAD_ENDING);
            } else if (response.equals(".22 Magnum")) {
                System.out.println();
                System.out.println("...");
                System.out.println();
                game.time(3);

                System.out.println("As you hold the gun in your hand you smile knowing that this fight was over.");
                System.out.println();
                game.time(3);

                System.out.println("The hobo throws up his hands as you aim the gun at his head.");
                System.out.println();
                game.time(2);

                System.out.println("Without remorse, you pull the trigger.");
                System.out.println();
                System.out.println();
                game.time(3);

                System.out.println("But nothing happens.");
                System.out.println();
                game.time(3);

                System.out.println("Turns out, there was no bullets loaded.");
                System.out.println();
                game.time(3);

                return game.gameOver("Realizing this, the hobo lunges at you and knocks you out. You wake up several hours later without your shoes, having missed the tech fair", true, Ending.BAD_ENDING);
            } else if (response.equals("A Bucket of KFC?")) {
                System.out.println();
                System.out.println("...");
                System.out.println();
                game.time(3);

                System.out.println("Well, I certainly did not expect you to have a whole bucket of KFC just sitting in your pocket.");
                System.out.println();
                game.time(3);

                System.out.println("Not knowing what else to do with it, you begin to eat it at a pace never seen before.");
                System.out.println();
                game.time(3);

                System.out.println("You finish the whole bucket, leaving a bunch of greasy, slippery crumbs all over the ground.");
                System.out.println();
                game.time(3);
                System.out.println();

                System.out.println("The hobo, who hasn't eaten for days, proceeds to charge at you furiously for taking a snack break in the middle of the fight.");
                System.out.println();
                game.time(4);

                System.out.println("But, he slips on the slippery crumbs and falls head first into the wall, knocking himself out.");
                System.out.println();
                game.time(3);
            } else if (player.getInventory().containsItem(null, "Crazy Soda") && response.equals("Crazy Soda")) {
                System.out.println();
                System.out.println("...");
                System.out.println();
                game.time(3);

                System.out.println("*Special Option*");
                System.out.println();
                game.time(1);

                System.out.println("Holding the rancid, half drunken, probably moldy, nostril destroying, eye watering, radioactive, crazy soda in your hand you freeze, not knowing what to do.");
                System.out.println();
                game.time(4);

                ArrayList<String> drink = new ArrayList<>(List.of("Drink It"));
                game.basicGameLoop("", drink);

                System.out.println("You try to raise the can to your lips, but before it gets too close you falter. I kinda agree, this may not be the best battle plan against the hobo.");
                System.out.println();
                game.time(4);

                game.basicGameLoop("", drink);

                System.out.println("Again, you make a feeble attempt to down the forbidden beverage, but you accidentally get a whiff of it and you almost spill the drink. Maybe you should try and do something else.");
                System.out.println();
                game.time(5);

                game.basicGameLoop("", drink);

                System.out.println("Okay, you really want to do this? I mean, it is fine by me, but personally I would try any other option. Regardless you bring the musty, dusty, crusty can of cursed liquid to your lips, hold your nose and dump it down the hatch.");
                System.out.println();
                game.time(6);

                System.out.println("It has an odd lemony flavor, but out side of that it was the most disgusting thing you had ever drank in your life.");
                System.out.println();
                System.out.println();
                game.time(4);

                System.out.println("But suddenly, you begin to feel better, better than you had ever felt before.");
                System.out.println();
                game.time(3);

                System.out.println("You begin to levitate as you reconcile the fact that that *Crazy Soda* gave you such godly powers.");
                System.out.println();
                game.time(4);

                String crazyEnd = "You evaporate the hobo with your lazer vision, then swing out the building, and go on to be the greatest superhero of the world";
                return game.gameOver(crazyEnd, true, Ending.SPECIAL_ENDING);
            }
        }
        System.out.println("Astonished that the KFC bucket had worked, you brush yourself off, and continue on your way. You feel quite proud you just beat up a poor, old hobo.");
        System.out.println();
        game.time(4);

        Floor floor = new Floor();
        board.addNode(4, 5, floor);
        board.addNode(5, 5, floor);
        return returnArray;
    }


}
