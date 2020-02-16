package de.munich.iubh.brettspiele.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;

import de.munich.iubh.brettspiele.R;
import de.munich.iubh.brettspiele.games.TicTacToe.TicTacToe;

public class OnFieldClickListener implements View.OnClickListener {
    private ImageView field;

    public OnFieldClickListener(ImageView field) {
        this.field = field;
    }

    @Override
    public void onClick(View v) {
        int player = TicTacToe.getPlayer();

        if (field.getBackground() == null) {
            switch (player) {
                case 1:
                    field.setBackgroundResource(R.drawable.o);
                    field.setTag(R.drawable.o);
                    this.checkForWin(1, v.getContext());
                    break;
                case 2:
                    field.setBackgroundResource(R.drawable.x);
                    field.setTag(R.drawable.x);
                    this.checkForWin(2, v.getContext());
                    break;
            }
        }

        TicTacToe.setPlayer(player == 1 ? 2 : 1);

        System.out.println("Pressed!");

    }

    private void checkForWin(int player, Context context) {
        if ((checkPlayerSymbol(player, 0, context) &&
            checkPlayerSymbol(player, 1, context) &&
            checkPlayerSymbol(player, 2, context)) ||

            (checkPlayerSymbol(player, 3, context) &&
            checkPlayerSymbol(player, 4, context) &&
            checkPlayerSymbol(player, 5, context)) ||

            (checkPlayerSymbol(player, 6, context) &&
            checkPlayerSymbol(player, 7, context) &&
            checkPlayerSymbol(player, 8, context)) ||

            (checkPlayerSymbol(player, 0, context) &&
            checkPlayerSymbol(player, 3, context) &&
            checkPlayerSymbol(player, 6, context)) ||

            (checkPlayerSymbol(player, 1, context) &&
            checkPlayerSymbol(player, 4, context) &&
            checkPlayerSymbol(player, 7, context)) ||

            (checkPlayerSymbol(player, 2, context) &&
            checkPlayerSymbol(player, 5, context) &&
            checkPlayerSymbol(player, 8, context)) ||

            (checkPlayerSymbol(player, 0, context) &&
            checkPlayerSymbol(player, 4, context) &&
            checkPlayerSymbol(player, 8, context)) ||

            (checkPlayerSymbol(player, 2, context) &&
            checkPlayerSymbol(player, 4, context) &&
            checkPlayerSymbol(player, 6, context)))
        {
            openWinDialog(player, context);
            System.out.println("Won!");

        } else if (!TicTacToe.hasEmptyFields()){
            openNoWinnerDialog(context);
            System.out.println();
        }

        System.out.println((checkPlayerSymbol(player, 0, context)));

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

    private boolean checkPlayerSymbol(int player, int position, Context context) {
        if (player == 1) {
            return TicTacToe.fields[position].getTag() == (Integer)R.drawable.o;
        } else if (player == 2) {
            return TicTacToe.fields[position].getTag() == (Integer)R.drawable.x;
        }

        return true;
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




}
