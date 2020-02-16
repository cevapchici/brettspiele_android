package de.munich.iubh.brettspiele;

import android.app.ActionBar;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.munich.iubh.brettspiele.games.VierGewinnt;
import de.munich.iubh.brettspiele.games.SchiffeVersenken;
import de.munich.iubh.brettspiele.games.TicTacToe.TicTacToe;
import de.munich.iubh.brettspiele.helpers.GamesAdapter;
import de.munich.iubh.brettspiele.models.Game;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        centerAppBarTitle();

        Game[] games = {
                new Game(R.drawable.tic_tac_toe, TicTacToe.class),
                new Game(R.drawable.vier_gewinnt, VierGewinnt.class),
                new Game(R.drawable.schiffe_versenken, SchiffeVersenken.class),
                new Game(R.drawable.muehle, TicTacToe.class),
                new Game(R.drawable.mensch_aergere_dich_nicht, TicTacToe.class)
        };

        GridView gamesGrid = (GridView) findViewById(R.id.main_menu);
        GamesAdapter gamesAdapter = new GamesAdapter(this, games);

        gamesGrid.setAdapter(gamesAdapter);
    }

    private void centerAppBarTitle() {

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView = new TextView(this);

        textView.setText(R.string.app_name);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.actionBarTitle));

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }
}
