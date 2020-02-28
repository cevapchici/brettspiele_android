package de.munich.iubh.brettspiele.games.TicTacToe;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import de.munich.iubh.brettspiele.R;
import de.munich.iubh.brettspiele.helpers.OnFieldClickListener;

public class TicTacToe extends AppCompatActivity {
    private static int player = 1;
    public static ImageView[] fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        initalizeFields();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    private void initalizeFields() {
        ImageView f_1_1 = (ImageView) findViewById(R.id.field_1_1);
        ImageView f_1_2 = (ImageView) findViewById(R.id.field_1_2);
        ImageView f_1_3 = (ImageView) findViewById(R.id.field_1_3);
        ImageView f_2_1 = (ImageView) findViewById(R.id.field_2_1);
        ImageView f_2_2 = (ImageView) findViewById(R.id.field_2_2);
        ImageView f_2_3 = (ImageView) findViewById(R.id.field_2_3);
        ImageView f_3_1 = (ImageView) findViewById(R.id.field_3_1);
        ImageView f_3_2 = (ImageView) findViewById(R.id.field_3_2);
        ImageView f_3_3 = (ImageView) findViewById(R.id.field_3_3);

        fields = new ImageView[]{f_1_1, f_1_2, f_1_3, f_2_1, f_2_2, f_2_3, f_3_1, f_3_2, f_3_3};

        for (ImageView field : fields) {
            field.setOnClickListener(new OnFieldClickListener(field));
        }

    }

    public static int getPlayer() {
        return player;
    }

    public static void setPlayer(int updatedPlayer) {
        player = updatedPlayer;
        System.out.println(player);
    }

    public static void resetGame() {
        player = 1;
        for (ImageView field : fields) {
            field.setBackgroundResource(0);
        }
    }

    private static void openWinDialog(int winner, Context context) {
        final AlertDialog.Builder winDialog = new AlertDialog.Builder(context);
        winDialog.setTitle(R.string.game_end);
        winDialog.setMessage("Spieler " + winner + " hat das Spiel gewonnen!");

        winDialog.setCancelable(false);
        winDialog.setPositiveButton(R.string.restart, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TicTacToe.resetGame();
                dialog.cancel();
            }
        });
        winDialog.show();
    }

    private static boolean checkPlayerSymbol(int player, int position) {
        return fields[position].getTooltipText() == String.valueOf(player);
    }

    private static void openNoWinnerDialog(Context context) {
        AlertDialog.Builder noWinnerDialog = new AlertDialog.Builder(context);
        noWinnerDialog.setTitle(R.string.game_end);
        noWinnerDialog.setMessage("Das Spiel steht unentschieden!");

        noWinnerDialog.setCancelable(false);
        noWinnerDialog.setPositiveButton(R.string.restart, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TicTacToe.resetGame();
                dialog.cancel();
            }
        });
        noWinnerDialog.show();
    }

    public static boolean hasEmptyFields() {
        ArrayList<Boolean> empty = new ArrayList();

        for (ImageView btn : fields) {
            if (btn.getBackground() == null) {
                empty.add(true);
            } else {
                empty.add(false);
            }
        }

        return empty.contains(true);
    }

}
