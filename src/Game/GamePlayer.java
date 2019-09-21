package Game;

public class GamePlayer {
    private char playerSign;
    private boolean realPlayer = true;

    public GamePlayer(boolean isRealPlayer, char playerSign) {
        this.playerSign = playerSign;
        this.realPlayer = isRealPlayer;
    }

    public char getPlayerSign() {
        return playerSign;
    }

    public boolean isRealPlayer() {
        return realPlayer;
    }
}
