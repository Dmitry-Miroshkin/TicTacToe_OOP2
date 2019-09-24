package Game;

import javax.swing.*;

class GameButton extends JButton {
    private GameBoard board;


    GameButton(int gameButtonIndex, GameBoard currentGameBoard) {
        board = currentGameBoard;

        int rowNum = gameButtonIndex / GameBoard.dimension;
        int cellNum = gameButtonIndex % GameBoard.dimension;
        setSize(GameBoard.cellSize - 5, GameBoard.cellSize - 5);
        addActionListener(new GameActionListener (rowNum, cellNum, this));
    }

    GameBoard getBoard() {
        return board;
    }

}

