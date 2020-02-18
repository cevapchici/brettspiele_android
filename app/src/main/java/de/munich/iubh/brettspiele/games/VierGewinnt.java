package de.munich.iubh.brettspiele.games;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import de.munich.iubh.brettspiele.R;

public class VierGewinnt extends AppCompatActivity {
    public static int player;
    private int winner;
    private final int ROWS = 4;
    private final int COLS = 5;
    ImageView[][] cells = new ImageView[ROWS][COLS];
    private ViewGroup boardView;
    private TextView currentPlayerDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vier_gewinnt);
        player = 1;
        winner = 0;
        boardView = findViewById(R.id.game_board);
        currentPlayerDisplay = findViewById(R.id.current_player);
        currentPlayerDisplay.setText("Spieler " + player + " ist an der Reihe!");
        currentPlayerDisplay.setTextColor(player == 1 ? getResources().getColor(R.color.red) : getResources().getColor(R.color.yellow));

        FloatingActionButton resetBtn = (FloatingActionButton) findViewById(R.id.fab);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });
        buildBoard();
    }

    private void restartGame() {
        buildBoard();
        winner = 0;
        player = 1;
        currentPlayerDisplay.setText("Spieler " + player + " ist an der Reihe!");
        currentPlayerDisplay.setTextColor(player == 1 ? getResources().getColor(R.color.red) : getResources().getColor(R.color.yellow));
    }

    private void buildBoard() {
        for (int r = 0; r<ROWS; r++) {
            ViewGroup row = (ViewGroup) ((ViewGroup) boardView).getChildAt(r);
            for (int c = 0; c<COLS; c++) {
                final ImageView imageView = (ImageView) row.getChildAt(c);
                imageView.setPadding(20, 20, 20, 20);

                imageView.setImageResource(R.drawable.white_circle);

                imageView.setTag(new Coordinates(r, c, 0));

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Coordinates coordinates = (Coordinates) imageView.getTag();
                        if (coordinates.player == 0 && (isLastRow(imageView) || hasStoneBelow(imageView))) {
                            switch (player) {
                                case 1:
                                    imageView.setImageResource(R.drawable.red);
                                    break;
                                case 2:
                                    imageView.setImageResource(R.drawable.yellow);
                            }
                            coordinates.player = player;
                            imageView.setTag(coordinates);

                            checkWin();

                            player = player == 1 ? 2 : 1;
                            currentPlayerDisplay.setText("Spieler " + player + " ist an der Reihe!");
                            currentPlayerDisplay.setTextColor(player == 1 ? getResources().getColor(R.color.red) : getResources().getColor(R.color.yellow));
                        }
                    }
                });
                cells[r][c] = imageView;
            }
        }
    }

    private boolean isLastRow(ImageView imageView) {
        boolean isLast = false;

        for(int p = 0; p<COLS; p++) {
            isLast = cells[3][p] == imageView;
            if (isLast) break;
        }
        return isLast;
    }

    private boolean hasStoneBelow(ImageView imageView) {
        if (!isLastRow(imageView)) {
            Coordinates coordinates = (Coordinates) imageView.getTag();
            Coordinates belowCoordinates = (Coordinates) cells[coordinates.x+1][coordinates.y].getTag();
            return belowCoordinates.player != 0;
        }
        return false;
    }

    private boolean checkWin() {
        verticalWin();
        horizontalWin();
        diagonalWin();

        if (winner != 0) {
            new AlertDialog.Builder(this)
                .setTitle("Spieler " + winner + " hat das Spiel gewonnen!")
                .setMessage("Starte jetzt ein neues Spiel!")
                .setPositiveButton("Neustart", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        restartGame();
                    }
                })
                .setIcon(android.R.drawable.btn_star_big_on)
                .setCancelable(false)
                .show();
        } else if (isDraw()) {
            new AlertDialog.Builder(this)
                .setTitle("Es steht unentschieden!")
                .setMessage("Starte jetzt ein neues Spiel!")
                .setPositiveButton("Neustart", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        restartGame();
                    }
                })
                .setIcon(android.R.drawable.btn_star_big_on)
                .setCancelable(false)
                .show();
        }
        return false;
    }

    private boolean isDraw() {
        ArrayList<Boolean> isFilled = new ArrayList<>();
        for(int r = 0; r<ROWS; r++) {
            for(int c = 0; c<COLS; c++) {
                Coordinates coordinates = (Coordinates) cells[r][c].getTag();
                isFilled.add(coordinates.player != 0);
            }
        }
        return !isFilled.contains(false);
    }

    private void diagonalWin() {
        ArrayList<Integer> columnColors_1 = new ArrayList<>();
        ArrayList<Integer> columnColors_2 = new ArrayList<>();
        ArrayList<Integer> columnColors_3 = new ArrayList<>();
        ArrayList<Integer> columnColors_4 = new ArrayList<>();

        for(int p = 1; p<=2; p++) {

            for(int r = ROWS-1; r>=0; r--) {
                Coordinates coordinates_1 = (Coordinates) cells[r][r].getTag();
                Coordinates coordinates_2 = (Coordinates) cells[r][r+1].getTag();

                columnColors_1.add(coordinates_1.player);
                columnColors_2.add(coordinates_2.player);
            }

            int[] helperNums = {3,2,1,0};

            for (int r = 0; r<ROWS; r++) {
                Coordinates coordinates_1 = (Coordinates) cells[helperNums[r]][r].getTag();
                Coordinates coordinates_2 = (Coordinates) cells[helperNums[r]][r+1].getTag();

                columnColors_3.add(coordinates_1.player);
                columnColors_4.add(coordinates_2.player);
            }

            int currentEnemy = p == 1 ? 2 : 1;

            if ((!columnColors_1.contains(currentEnemy) && !columnColors_1.contains(0)) || (!columnColors_2.contains(currentEnemy) && !columnColors_2.contains(0)) ||
                    (!columnColors_3.contains(currentEnemy) && !columnColors_3.contains(0)) || (!columnColors_4.contains(currentEnemy) && !columnColors_4.contains(0))) {
                winner = p;
                break;
            }

        }
    }

    private void verticalWin() {
        ArrayList<Integer> columnColors;

        for(int p = 1; p<=2; p++) {

            for(int c = 0; c<COLS; c++) {
                columnColors = new ArrayList<>();

                for(int r = 0; r<ROWS; r++) {
                    Coordinates coordinates = (Coordinates) cells[r][c].getTag();
                    columnColors.add(coordinates.player);
                }

                int currentEnemy = p == 1 ? 2 : 1;
                if (!columnColors.contains(currentEnemy) && !columnColors.contains(0)) {
                    winner = p;
                    break;
                }
            }
        }
    }

    private void horizontalWin() {
        ArrayList<Integer> columnColors;

        for(int p = 1; p<=2; p++) {

            for(int r = 0; r<ROWS; r++) {
                columnColors = new ArrayList<>();

                for(int c = 0; c<COLS-1; c++) {
                    Coordinates coordinates = (Coordinates) cells[r][c].getTag();
                    columnColors.add(coordinates.player);
                }

                int currentEnemy = p == 1 ? 2 : 1;
                if (!columnColors.contains(currentEnemy) && !columnColors.contains(0)) {
                    winner = p;
                    break;
                }
            }

            for(int r = 0; r<ROWS; r++) {
                columnColors = new ArrayList<>();

                for(int c = COLS-1; c>0; c--) {
                    Coordinates coordinates = (Coordinates) cells[r][c].getTag();
                    columnColors.add(coordinates.player);
                }

                int currentEnemy = p == 1 ? 2 : 1;
                if (!columnColors.contains(currentEnemy) && !columnColors.contains(0)) {
                    winner = p;
                    break;
                }
            }
        }
    }
}

class Coordinates {
    public int x, y, player;

    Coordinates(int x, int y, int player) {
        this.player = player;
        this.x = x;
        this.y = y;
    }
}
