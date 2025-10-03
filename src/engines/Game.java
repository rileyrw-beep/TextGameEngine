import java.security.spec.RSAOtherPrimeInfo;
import java.util.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game {
    //keep track of player
    private String playerName;
    private Player currentPlayer;

    //objects needed to do work
    private final Scanner input;
    private final Prompt prompt;

    //keep track of chapter/acts
    private int currentChapter;
    private int latestChapter;
    private int[] currentAct;
    private int[] latestActs;
    private final int[] totalActs;
    private Board currentBoard;

    //convenience.
    private int timeNumber;

    //constructor
    public Game() {

        playerName = "";
        input = new Scanner(System.in);
        prompt = new Prompt();
        currentChapter = 0;
        latestChapter = 0;
        currentAct = new int[8];
        latestActs = new int[8];
        totalActs = new int[]{2, 0, 0, 0, 0, 0, 0, 0};
        timeNumber = 0;

    }


    //getter for the user input if needed in prompt.
    public Scanner getInput() {
        return input;
    }

    //getter and setters for playerName
    public void setPlayerName(String newName) {
        playerName = newName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayer(Player player) {
        currentPlayer = player;
    }

    public Player getPlayer() {
        return currentPlayer;
    }

    public void setBoard(Board board) {
        currentBoard = board;
    }

    public Board getBoard() {
        return currentBoard;
    }

    //getters for chapters and acts
    public int getCurrentChapter() {
        return currentChapter;
    }

    public int[] getCurrentAct() {
        return currentAct;
    }

    public int getLatestChapter() {
        return latestChapter;
    }

    public int[] getLatestAct() {
        return latestActs;
    }

    //setters for chapters and acts
    public void setCurrentChapter(int newChapter) {
        currentChapter = newChapter;
    }

    public void setCurrentAct(int newAct, int chapter) {
        currentAct[chapter - 1] = newAct;
    }

    //some random convenient methods to make things easier
    public void setTimeNumber(int newTimeNumber) {
        timeNumber = newTimeNumber;
    }

    public void endText() {
        System.out.println();
        System.out.println();

    }

    public void startFromSpecificAct() {
        while (true) {
            System.out.println();
            System.out.print("Enter your desired Chapter number:");
            try {
                System.out.println();
                int desiredChapter = input.nextInt();
                System.out.println();
                if (desiredChapter >= 1 && desiredChapter <= latestChapter) {
                    currentChapter = desiredChapter;
                    break;
                } else {
                    System.out.println("That is not a valid Chapter number, please try again.");
                }
            } catch (Exception InputMismatchException) {
                System.out.println("Please enter a valid integer.");
            }
        }
        while (true) {
            System.out.println();
            System.out.println("Enter your desired Act number:");
            try {
                System.out.println();
                int desiredAct = input.nextInt();
                System.out.println();
                if (desiredAct >= 1 && desiredAct <= latestActs[currentChapter - 1]) {
                    currentAct[currentChapter - 1] = desiredAct;
                    break;
                } else {
                    System.out.println("That is not a valid Act number, please try again.");
                }
            } catch (Exception InputMismatchException) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public void time(int sec) {
        if (timeNumber == 200) {
            return;
        }
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    //the beginning and end of game methods
    public boolean[] gameOver(String deathText, boolean inBattle, Ending ending) {
        boolean[] returnArray = {false, true};
        System.out.println(deathText);
        this.time(3);
        System.out.println();
        System.out.println("...");
        System.out.println();
        System.out.println("GAME OVER");
        System.out.println();
        time(2);
        if (ending == Ending.BAD_ENDING) System.out.println("BAD ENDING");
        if (ending == Ending.GOOD_ENDING) System.out.println("GOOD ENDING");
        if (ending == Ending.SPECIAL_ENDING) System.out.println("SPECIAL ENDING");
        if (ending == Ending.TRUE_ENDING) System.out.println("TRUE ENDING");
        this.time(2);

        System.out.println();
        System.out.println("Would you like to restart from the last act or end the game?");
        System.out.println();

        ArrayList<String> optionList = new ArrayList<String>();
        optionList.add("Restart From Last Act");
        optionList.add("Restart From Specific Act");
        optionList.add("End Game");
        if (inBattle) optionList.add("Restart Battle");

        String response = this.basicGameLoop("", optionList);
        if (response.equals("Restart From Last Act")) {
            return returnArray;
        }
        if (response.equals("Restart From Specific Act")) {
            startFromSpecificAct();
            return returnArray;
        }
        if (response.equals("Restart Battle")) {
            returnArray[1] = false;
            return returnArray;
        }
        if (response.equals("End Game")) {
            System.exit(0);
        }
        returnArray[1] = false;
        return returnArray;
    }

    public void askPlayerName() {
        this.endText();
        System.out.println("Welcome to /insert game name/");
        System.out.println();
        System.out.println("Please enter your name:");
        String get = input.nextLine();
        this.setPlayerName(get);
        System.out.println();
        System.out.println("Hello, " + playerName);
        if (get.equals("fast")) {
            timeNumber = 200;
        }
        this.endText();
    }

    public void startGame() {
        System.out.println("Enter the word 'begin' below to start the game.");
        String get = "";
        while (true) {
            get = input.nextLine();
            System.out.println();
            if (get.equals("begin") || get.equals("/jump to chapter keyword/")) {
                break;
            }
            System.out.println("Sorry, that was not a valid name. Please try again.");
            System.out.println();
        }
        if (get.equals("begin")) {
            System.out.println("Starting game...");
            currentChapter = 1;
            currentAct[currentChapter - 1] = 1;
            latestChapter = 1;
            latestActs[latestChapter - 1] = 1;
        } else {
            System.out.println("/custom jump to chapter text/");
            System.out.println();
            System.out.println("Choose your desired Chapter and Act below: ");
            System.out.println();
            int chap = -1;
            int act = -1;
            while (true) {
                try {
                    System.out.print("Chapter: ");
                    chap = input.nextInt();
                    if (chap >= 1 && chap <= 8) {
                        currentChapter = chap;
                        break;
                    } else {
                        System.out.println("Try Again");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Try Again");
                }
            }
            System.out.println();

            // add all the other act numbers / change the current Chap 1 Act 1 number if I add more than 2.
            while (true) {
                try {
                    System.out.print("Act: ");
                    act = input.nextInt();
                    if (act >= 1 && act <= totalActs[currentChapter - 1]) {
                        currentAct[currentChapter - 1] = act;
                        break;
                    } else {
                        System.out.println("Try Again");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Try Again");
                }
            }

        }
        time(3);
        this.endText();
        System.out.println();
        System.out.println();
        time(2);
    }

    //the game loops
    public String basicGameLoop(String battleName, ArrayList<String> optionList) {
        if (!battleName.isEmpty()) {
            System.out.println(battleName);
            System.out.println();
            time(2);
            System.out.println("-FIGHT-");
            System.out.println();
            time(2);
        }

        for (int i = 0; i < optionList.size(); i++) {
            if (!optionList.get(i).isEmpty()) {
                System.out.println("- " + optionList.get(i));
            }
        }
        System.out.println();

        String userInput = "";
        while (true) {
            userInput = input.nextLine();
            System.out.println();
            if (optionList.contains(userInput)) break;
            else {
                System.out.println("I don't know that one, try again.");
                System.out.println();
            }
        }
        return userInput;
    }

    public boolean advancedGameLoop(Map map, Player player, int messageCounter, String message, int endBoardX, int endBoardY, int endX, int endY, int battleX, int battleY) {
        Board thisBoard = map.getCurrentBoard();
        String get = "";
        boolean[] boolArray = new boolean[2];
        thisBoard.printBoard();
        boolean canPrintBoard = true;
        int counter = 0;
        int boardCounter;
        // false false -> battle again
        // true false -> good, continue
        // false true -> retart from last act
        while (true) {
            Board board = map.getCurrentBoard();
            if ((board.getCharPosX() == battleX && board.getCharPosY() == battleY) || (battleX == -1 && board.getCharPosY() == battleY) || (battleY == 1 && board.getCharPosX() == battleX)) {
                boolArray = prompt.displayBattleActions(board, player, this);
                if (boolArray[1]) {
                    return false;
                }
                return true;
            }
            if (counter == messageCounter) {
                System.out.println(message);
                System.out.println();
            }
            prompt.displayActions(map, player, this);
            System.out.println();

            get = input.nextLine();
            System.out.println();
            boardCounter = map.getBoardChangeCounter();
            boolArray = prompt.doAction(get, map, player, this);
            if (!boolArray[0]) {
                if (boolArray[1]) {
                    return false;
                }
                canPrintBoard = false;
                if (get.length() > 4) {
                    if (get.startsWith("Move")) {
                        System.out.println("You cannot move to that spot! Try again.");
                    } else {
                        System.out.println("I don't know that one right now, try again.");
                    }
                } else {
                    System.out.println("I don't know that one right now, try again.");
                }
                System.out.println();
            }

            if (map.getCurrentBoardX() == endBoardX && map.getCurrentBoardY() == endBoardY) {
                if ((board.getCharPosX() == endX && board.getCharPosY() == endY) || (endX == -1 && board.getCharPosY() == endY) || (endY == -1 && board.getCharPosX() == endX)) {
                    break;
                }
            }
            if (canPrintBoard) {
                board = map.getCurrentBoard();
                board.printBoard();
                if (map.getBoardChangeCounter() != boardCounter) {
                    System.out.println();
                    System.out.println("Entering... " + board.getBoardName());
                    System.out.println();

                }
            }
            canPrintBoard = true;
            counter++;
        }
        return true;
    }

    //Chapter 1 acts.
    public void chapOneActOne() {
        //act implementation goes here
    }

    public void chapOneActTwo() {
        //implementation goes here
    }

    //chapter 1 method
    public void chapterOne() {
        System.out.println("Chapter 1: /chapter 1 name/");
        time(3);
        System.out.println();

        while (currentChapter == 1 && currentAct[currentChapter - 1] == 1) {
            chapOneActOne();
        }
        while (currentChapter == 1 && currentAct[currentChapter - 1] == 2) {
            chapOneActTwo();
        }
    }
}

