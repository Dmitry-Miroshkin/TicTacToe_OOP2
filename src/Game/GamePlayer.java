package Game;

class GamePlayer {
    private char playerSign;
    private boolean realPlayer;

    GamePlayer(boolean isRealPlayer, char playerSign) {
        this.playerSign = playerSign;
        this.realPlayer = isRealPlayer;
    }

    char getPlayerSign() {
        return playerSign;
    }

    public boolean isRealPlayer() {
        return realPlayer;
    }
}
