import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

public class App extends JFrame {
    JPanel panel = new JPanel();
    JTextField statusbar = new JTextField("Player 1's Turn");
    JTextField player1 = new JTextField();
    JTextField player2 = new JTextField();
    int turns = 0, score1 = 0, score2 = 0;
    JButton[][] buttons = new JButton[3][3];
    int[][] values = new int[3][3];
    int winner = -1;
    boolean gameover = false;

    App() {
        JFrame f = new JFrame();
        f.setTitle("Tic Tac Toe");
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setSize(500, 500);
        f.setLocationRelativeTo(null);

        statusbar.setFont(new Font("Arial", Font.BOLD, 20));
        statusbar.setHorizontalAlignment(JTextField.CENTER);
        statusbar.setEditable(false);

        player1.setEditable(false);
        player1.setHorizontalAlignment(JTextField.CENTER);
        player1.setText("Player 1: " + score1);

        player2.setEditable(false);
        player2.setHorizontalAlignment(JTextField.CENTER);
        player2.setText("Player 2: " + score1);

        JPanel scorePanel = new JPanel(new GridLayout(1, 2));
        scorePanel.add(player1);
        scorePanel.add(player2);

        panel.setLayout(new GridLayout(3, 3, 4, 4));
        panel.setBackground(Color.pink);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(new Buttonclick(i, j));
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 30));
                buttons[i][j].setBackground(Color.WHITE);
                values[i][j] = -1;
                panel.add(buttons[i][j]);
            }

        }

        f.add(statusbar, BorderLayout.NORTH);
        f.add(panel, BorderLayout.CENTER);
        f.add(scorePanel, BorderLayout.SOUTH);
        f.setVisible(true);

        JOptionPane.showMessageDialog(f, "Player 1 - O \nPlayer 2 - X");
    }

    public void resetGame() {
        turns = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                values[i][j] = -1;
            }
        }
        statusbar.setText("Player 1's turn");
        player1.setText("Player 1: " + score1);
        player2.setText("Player 2: " + score2);
        gameover = false;
        winner = -1;

    }

    public static void main(String[] args) throws Exception {
        new App();
    }

    public class Buttonclick implements ActionListener {
        int i;
        int j;

        Buttonclick(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (turns % 2 == 0) {
                buttons[i][j].setText("O");
                buttons[i][j].setEnabled(false);
                values[i][j] = 0;
                turns++;

                Timer timer1 = new Timer();
                TimerTask task1 = new TimerTask() {
                    @Override
                    public void run() {
                        statusbar.setText("Player 2's turn");
                        timer1.cancel();
                    }
                };
                timer1.schedule(task1, 200);

                System.out.println(turns);
            } else {
                buttons[i][j].setText("X");
                buttons[i][j].setEnabled(false);
                values[i][j] = 1;
                turns++;

                Timer timer2 = new Timer();
                TimerTask task2 = new TimerTask() {
                    @Override
                    public void run() {
                        statusbar.setText("Player 1's turn");
                        timer2.cancel();
                    }
                };
                timer2.schedule(task2, 200);

                System.out.println(turns);
            }

            // Game Logic

            // check rows
            for (int i = 0; i < 3; i++) {
                if (values[i][0] == values[i][1] && values[i][1] == values[i][2]) {
                    if (values[i][0] == 0) {
                        score1++;
                        gameover = true;
                        winner = 0;
                    } else if (values[i][0] == 1) {
                        score2++;
                        gameover = true;
                        winner = 1;
                    }
                }
            }

            // check columns
            for (int i = 0; i < 3; i++) {
                if (values[0][i] == values[1][i] && values[1][i] == values[2][i]) {
                    if (values[0][i] == 0) {
                        score1++;
                        gameover = true;
                        winner = 0;
                    } else if (values[0][i] == 1) {
                        score2++;
                        gameover = true;
                        winner = 1;
                    }
                }
            }

            // check diagonally
            if (values[0][0] == values[1][1] && values[1][1] == values[2][2]) {
                if (values[0][0] == 0) {
                    score1++;
                    gameover = true;
                    winner = 0;
                } else if (values[0][0] == 1) {
                    score2++;
                    gameover = true;
                    winner = 1;
                }
            }
            if (values[0][2] == values[1][1] && values[1][1] == values[2][0]) {
                if (values[0][2] == 0) {
                    score1++;
                    gameover = true;
                    winner = 0;
                } else if (values[0][2] == 1) {
                    score2++;
                    gameover = true;
                    winner = 1;
                }
            }

            // check if all cells are filled
            int flag = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (values[i][j] == -1) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 1)
                    break;
            }
            if (flag == 0)
                gameover = true;

            // what to do if gameover
            if (gameover) {
                if (winner == 0) {
                    int option = JOptionPane.showConfirmDialog(null,
                            "Player 1 Won! Would you like to restart the game?",
                            "Game End", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        resetGame();
                    } else {
                        System.exit(0);
                    }
                } else if (winner == 1) {
                    int option = JOptionPane.showConfirmDialog(null,
                            "Player 2 Won! Would you like to restart the game?",
                            "Game End", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        resetGame();
                    } else {
                        System.exit(0);
                    }
                } else {
                    int option = JOptionPane.showConfirmDialog(null,
                            "It's a draw! Would you like to restart the game?",
                            "Game End", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        resetGame();
                    } else {
                        System.exit(0);
                    }
                }
            }
        }
    }

}
