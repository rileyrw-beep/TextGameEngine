import java.util.*;

public class Player implements Nodeable, Moveable {
    private String displayid;
    private String inGameid;
    private Nodeable previousNode;
    private boolean canMoveTo;
    private ListOfNodes type;
    private Inventory inventory;




    public Player (String displayid, String inGameid, Nodeable startNode) {
        this.displayid = displayid;
        this.inGameid = inGameid;
        previousNode = startNode;
        type = ListOfNodes.PLAYER;
        canMoveTo = false;
        inventory = new Inventory();


    }

    public boolean[] performAction(String response, Board board, Game game) {
        boolean[] returnArray = {true, false};
        if ((response.equals("Move North") || response.equals("w")) && this.checkNorth(board)) {
            this.moveNorth(board, game);
            return returnArray;
        }
        else if ((response.equals("Move East") || response.equals("d")) && this.checkEast(board)) {
            this.moveEast(board, game);
            return returnArray;
        }
        else if ((response.equals("Move South") || response.equals("s")) && this.checkSouth(board)) {
            this.moveSouth(board, game);
            return returnArray;
        }
        else if ((response.equals("Move West") || response.equals("a")) && this.checkWest(board)) {
            this.moveWest(board, game);
            return returnArray;
        }
        else {
            returnArray[0] = false;
            return returnArray;
        }
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

    public boolean checkSouth(Board board) {
        int x = board.getCharPosX();
        int y = board.getCharPosY();
        y++;
        ArrayList<ArrayList<Nodeable>> boardList = board.getBoard();

        if (y>9) {
            Map map = board.getMap();
            return map.checkBoard(Direction.SOUTH, board.getCharPosX(), board.getCharPosY());
        }
        return boardList.get(y).get(x).getCanMoveTo();
    }
    public boolean checkEast(Board board) {
        int x = board.getCharPosX();
        int y = board.getCharPosY();
        x++;
        ArrayList<ArrayList<Nodeable>> boardList = board.getBoard();
        if (x>9) {
            Map map = board.getMap();
            return map.checkBoard(Direction.EAST, board.getCharPosX(), board.getCharPosY());
        }
        return boardList.get(y).get(x).getCanMoveTo();
    }
    public boolean checkNorth(Board board) {
        int x = board.getCharPosX();
        int y = board.getCharPosY();
        y--;
        ArrayList<ArrayList<Nodeable>> boardList = board.getBoard();

        if (y<0) {
            Map map = board.getMap();
            return map.checkBoard(Direction.NORTH, board.getCharPosX(), board.getCharPosY());
        }
        return boardList.get(y).get(x).getCanMoveTo();
    }
    public boolean checkWest(Board board) {
        int x = board.getCharPosX();
        int y = board.getCharPosY();
        x--;
        ArrayList<ArrayList<Nodeable>> boardList = board.getBoard();
        if (x<0) {
            Map map = board.getMap();
            return map.checkBoard(Direction.WEST, board.getCharPosX(), board.getCharPosY());
        }
        return boardList.get(y).get(x).getCanMoveTo();
    }

    public void moveSouth(Board board, Game game) {
        if(this.checkSouth(board)) {
            int x = board.getCharPosX();
            int prev = board.getCharPosY();
            int y = prev + 1;
            ArrayList<ArrayList<Nodeable>> boardList = board.getBoard();
            if (y >= 10) {
                Map map = board.getMap();
                ArrayList<ArrayList<Board>> mapBoard = map.getMapBoard();
                Board newBoard = mapBoard.get(map.getCurrentBoardY()+1).get(map.getCurrentBoardX());
                ArrayList<ArrayList<Nodeable>> newBoardList = newBoard.getBoard();
                Nodeable placeholder = newBoardList.get(0).get(x);
                board.addNode(x, prev, previousNode);
                previousNode = placeholder;
                newBoard.addNode(x, 0, this);
                newBoard.setCharPosX(x);
                newBoard.setCharPosY(0);
                map.setCurrentBoardX(map.getCurrentBoardX());
                map.setCurrentBoardY(map.getCurrentBoardY()+1);
                game.setBoard(newBoard);
                map.boardChanged();
                return;
            }
            Nodeable placeholder = boardList.get(y).get(x);
            board.addNode(x, prev, previousNode);
            previousNode = placeholder;
            board.addNode(x,y,this);
            board.setCharPosX(x);
            board.setCharPosY(y);
            return;
        }
        System.out.println("You cannot move to that spot! Try again.");

    }
    public void moveEast(Board board, Game game) {
        if(this.checkEast(board)) {
            int prev = board.getCharPosX();
            int y = board.getCharPosY();
            int x = prev + 1;
            ArrayList<ArrayList<Nodeable>> boardList = board.getBoard();
            if (x >= 10) {
                Map map = board.getMap();
                ArrayList<ArrayList<Board>> mapBoard = map.getMapBoard();
                Board newBoard = mapBoard.get(map.getCurrentBoardY()).get(map.getCurrentBoardX()+1);
                ArrayList<ArrayList<Nodeable>> newBoardList = newBoard.getBoard();
                Nodeable placeholder = newBoardList.get(y).get(0);
                board.addNode(prev, y, previousNode);
                previousNode = placeholder;
                newBoard.addNode(0, y, this);
                newBoard.setCharPosX(0);
                newBoard.setCharPosY(y);
                map.setCurrentBoardX(map.getCurrentBoardX()+1);
                map.setCurrentBoardY(map.getCurrentBoardY());
                game.setBoard(newBoard);
                map.boardChanged();
                return;
            }
            Nodeable placeholder = boardList.get(y).get(x);
            board.addNode(prev, y, previousNode);
            previousNode = placeholder;
            board.addNode(x,y,this);

            board.setCharPosX(x);
            board.setCharPosY(y);
            return;
        }
        System.out.println("You cannot move to that spot! Try again.");

    }
    public void moveNorth(Board board, Game game) {
        if(this.checkNorth(board)) {
            int x = board.getCharPosX();
            int prev = board.getCharPosY();
            int y = prev - 1;
            ArrayList<ArrayList<Nodeable>> boardList = board.getBoard();
            if (y < 0) {
                Map map = board.getMap();
                ArrayList<ArrayList<Board>> mapBoard = map.getMapBoard();
                Board newBoard = mapBoard.get(map.getCurrentBoardY()-1).get(map.getCurrentBoardX());
                ArrayList<ArrayList<Nodeable>> newBoardList = newBoard.getBoard();
                Nodeable placeholder = newBoardList.get(9).get(x);
                board.addNode(x, prev, previousNode);
                previousNode = placeholder;
                newBoard.addNode(x, 9, this);
                newBoard.setCharPosX(x);
                newBoard.setCharPosY(9);
                map.setCurrentBoardX(map.getCurrentBoardX());
                map.setCurrentBoardY(map.getCurrentBoardY()-1);
                game.setBoard(newBoard);
                map.boardChanged();
                return;
            }
            Nodeable placeholder = boardList.get(y).get(x);
            board.addNode(x, prev, previousNode);
            previousNode = placeholder;
            board.addNode(x,y,this);
            board.setCharPosX(x);
            board.setCharPosY(y);
            return;
        }
        System.out.println("You cannot move to that spot! Try again.");

    }
    public void moveWest(Board board, Game game) {
        if(this.checkWest(board)) {
            int prev = board.getCharPosX();
            int y = board.getCharPosY();
            int x = prev - 1;
            ArrayList<ArrayList<Nodeable>> boardList = board.getBoard();
            if (x < 0) {
                Map map = board.getMap();
                ArrayList<ArrayList<Board>> mapBoard = map.getMapBoard();
                Board newBoard = mapBoard.get(map.getCurrentBoardY()).get(map.getCurrentBoardX()-1);
                ArrayList<ArrayList<Nodeable>> newBoardList = newBoard.getBoard();
                Nodeable placeholder = newBoardList.get(y).get(9);
                board.addNode(prev, y, previousNode);
                previousNode = placeholder;
                newBoard.addNode(9, y, this);
                newBoard.setCharPosX(9);
                newBoard.setCharPosY(y);
                map.setCurrentBoardX(map.getCurrentBoardX()-1);
                map.setCurrentBoardY(map.getCurrentBoardY());
                game.setBoard(newBoard);
                map.boardChanged();
                return;
            }
            Nodeable placeholder = boardList.get(y).get(x);
            board.addNode(prev, y, previousNode);
            previousNode = placeholder;
            board.addNode(x,y,this);

            board.setCharPosX(x);
            board.setCharPosY(y);
            return;
        }
        System.out.println("You cannot move to that spot! Try again.");
    }

    public ListOfNodes getType() {
        return type;
    }

    public ArrayList<String> getActionList() {
        throw new IllegalArgumentException("How did you do this?");
    }

    public Inventory getInventory() {
        return inventory;
    }



    public boolean checkTeleport(Board board, int x, int y) {
        if (x < 0 || x > 9 || y < 0 || y > 9) return false;
        ArrayList<ArrayList<Nodeable>> boardList = board.getBoard();
        Nodeable desiredNode = boardList.get(y).get(x);
        return desiredNode.getCanMoveTo();
    }

    public void teleport(Board board, Game game, int x, int y) {
        if (checkTeleport(board, x, y)) {
            ArrayList<ArrayList<Nodeable>> boardList = board.getBoard();
            Nodeable desiredNode = boardList.get(y).get(x);
            Nodeable placeholder = previousNode;
            previousNode = desiredNode;
            board.addNode(board.getCharPosX(), board.getCharPosY(), placeholder);
            board.addNode(x, y, this);
            board.setCharPosX(x);
            board.setCharPosY(y);
        }
        else {
            System.out.println("You cannot teleport there.");
        }
    }


}