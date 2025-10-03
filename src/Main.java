//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //make a game
        Game game = new Game();
        //ask for player name
        game.askPlayerName();

        //start the game
        game.startGame();

        //chapter loop stuff (actually a switch case would make more sense for this because of the fall through
        if (game.getCurrentChapter() == 1) {
            game.chapterOne();
        }
    }
}
