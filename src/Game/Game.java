package Game;

public class Game {
    private GameBoard board;
    private GamePlayer[] gamePlayers= new GamePlayer[2];
    private int playersTurn = 0;

    public Game() {
        this.board = new GameBoard(this);
    }
}
