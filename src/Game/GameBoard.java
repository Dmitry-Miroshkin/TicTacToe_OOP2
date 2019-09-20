package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {
    static int dimension = 3;
    static int cellSize = 150;
    private char[][] gameField;
    private GameButton[] gameButtons;
    static char nullSymbol = '\u0000';
    private Game game;

    public GameBoard(Game currentGame) {
        this.game = currentGame;
        initField();
    }

    private void initField() {
        setBounds(cellSize * dimension, cellSize * dimension, 400, 300);
        setTitle("Крестики-нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel();
        JButton newGameButton = new JButton("Новая игра");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emptyField();
            }
        });
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(newGameButton);
        controlPanel.setSize(cellSize * dimension, 150);
        JPanel gameFieldPanel = new JPanel();
        gameFieldPanel.setLayout(new GridLayout(dimension, dimension));
        gameFieldPanel.setSize(cellSize * dimension, cellSize * dimension);
        gameField = new char[dimension][dimension];
        gameButtons = new GameButton[dimension * dimension];
//        Инициализация поля
        for (int i = 0; i < (dimension * dimension); i++) {
            GameButton fieldButton = new GameButton(i, this);
            gameFieldPanel.add(fieldButton);
            gameButtons[i] = fieldButton;
        }
        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(gameFieldPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    void emptyField() {
        for (int i = 0; i < (dimension * dimension); i++) {
            gameButtons[i].setText("");

            int x = i / GameBoard.dimension;
            int y = i % GameBoard.dimension;
            gameField[x][y] = nullSymbol;
        }
    }

    Game getGame() {
        return game;
    }

    // Метод проверки доступности клетки для хода
    boolean isTurnable(int x, int y) {
        boolean result = false;
        if (gameField[x][y] == nullSymbol)
            result = true;
        return result;
    }

    void updateGameField(int x, int y) {
        gameField[y][x] = game.getCurrentPlayer().getPlayerSign();
    }

    boolean checkWin() {
        boolean result = false;
        char playerSymbol = game.getCurrentPlayer().getPlayerSign();
        if (checkWinDiag(playerSymbol) || chexkWinLines(playerSymbol)) {
            result = true;
        }
        return result;
    }

    private boolean chexkWinLines(char playerSymbol) {
        boolean cols = true;
        boolean rows = true;
        boolean result = false;
        for (int i = 0; i < (dimension * dimension); i++) {

            int x = i / GameBoard.dimension;
            int y = i % GameBoard.dimension;
            cols &= gameField[x][y] == playerSymbol;
            rows &= gameField[y][x] == playerSymbol;
            if (cols || rows) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean checkWinDiag(char playerSymbol) {
        boolean leftRight = true;
        boolean rightLeft = true;
        boolean result = false;
        for (int i = 0; i < dimension; i++) {

            leftRight &= (gameField[i][i] == playerSymbol);
            rightLeft &= (gameField[i][i] == playerSymbol);
        }
        if (leftRight || rightLeft) {
            result = true;
        }
        return result;
    }

    boolean isFull() {
        boolean result = true;
        for (int i = 0; i < (dimension * dimension); i++) {
            int x = i / GameBoard.dimension;
            int y = i % GameBoard.dimension;
            if (gameField[x][y] == nullSymbol) {
                result = false;
                break;
            }
        }
        return result;

    }

    public GameButton getButton(int buttonIndex) {
        return gameButtons[buttonIndex];
    }
}