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
                boolean sillyMode = false;
                if (sillyMode) updateBySillyAiData(board);
                else updateBySmartAiData(board);
                if (board.checkWin()) {
                    button.getBoard().getGame().ShowMessage("Выиграл компьютер!!");
                    board.emptyField();
                } else {
                    board.getGame().passTurn();
                }
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

    // Ход глупого компьютера
    private void updateBySillyAiData(GameBoard board) {
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
    }
// ход умного Компьютера
//    логика поиска следующего хода.
//     сначала ищем вариант выигрышного хода человека и ходим туда
//    если такого нет, ищем вариант выигрышного хода компьютера и ходим туда
//    если и такого нет то sillyAi!
    private void updateBySmartAiData(GameBoard board) {
        boolean humWin = false;
        boolean compWin = false;
        for (int i = 0; i < (GameBoard.dimension * GameBoard.dimension); i++) {
            int x = i / GameBoard.dimension;
            int y = i % GameBoard.dimension;
            if (board.isTurnable(x, y)) {
                board.gameField[x][y] = 'X';
                if (board.chexkWinLines('X') || board.checkWinDiag('x')) {
//                    board.getGame().passTurn();
                    board.updateGameField(x, y);
                    int cellIndex = GameBoard.dimension * x + y;
                    board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));
                    humWin = true;
                    break;
                }

                board.gameField[x][y] = GameBoard.nullSymbol;
            }
        }
        if (!humWin) {
            for (int i = 0; i < (GameBoard.dimension * GameBoard.dimension); i++) {
                int x = i / GameBoard.dimension;
                int y = i % GameBoard.dimension;
                if (board.isTurnable(x, y)) {
                    board.gameField[x][y] = 'O';
                    if (board.checkWin()) {

                        int cellIndex = GameBoard.dimension * x + y;
                        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));                        compWin = true;
                        break;
                    }
                    board.gameField[x][y] = GameBoard.nullSymbol;
                }
            }

        }
        if (!compWin && !humWin) updateBySillyAiData(board);
    }

}
