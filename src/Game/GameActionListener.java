package Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameActionListener implements ActionListener {
    private int row;
    private int cell;
    private GameButton button;

    GameActionListener(int row, int cell, GameButton gButton) {
        this.row = row;
        this.cell = cell;
        this.button = gButton;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GameBoard board = button.getBoard();
        if (board.isTurnable(row, cell)) {
            updateByPlayersData(board);
            if (board.checkWin()) {
                button.getBoard().getGame().ShowMessage("Вы выиграли!");
                board.emptyField();
                return;
            } else {
                board.getGame().passTurn();
            }
            if (board.isFull()) {
                board.getGame().ShowMessage("Ничья!");
                board.emptyField();
            } else {
                updateByAiData(board);

                if (board.isFull()) {
                    board.getGame().ShowMessage("Ничья!");
                    board.emptyField();
                }
            }
        } else {
            board.getGame().ShowMessage("Некорректный ход!");
        }
    }

    private void updateByPlayersData(GameBoard board) {
        board.updateGameField(row, cell);
        button.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

    }

    // Ход компьютера
    private void updateByAiData(GameBoard board) {
        int x, y;
        Random rnd = new Random();

        do {
            x = rnd.nextInt(GameBoard.dimension);

            y = rnd.nextInt(GameBoard.dimension);
        } while (!board.isTurnable(x, y));
//        Обновить матрицу игры
        board.updateGameField(x, y);
//        Обновить содержимое кнопки
        int cellIndex = GameBoard.dimension * x + y;
        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        if (board.checkWin()) {
            button.getBoard().getGame().ShowMessage("Выиграл компьютер!!");
            board.emptyField();
        } else {
            board.getGame().passTurn();
        }
    }


}
