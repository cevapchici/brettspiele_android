package de.munich.iubh.brettspiele.helpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import de.munich.iubh.brettspiele.R;
import de.munich.iubh.brettspiele.models.Game;

public class GamesAdapter extends BaseAdapter {
    private final Context mContext;
    private final Game[] games;

    public GamesAdapter(Context context, Game[] games) {
        this.mContext = context;
        this.games = games;
    }

    @Override
    public int getCount() {
        return games.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;

        if (convertView == null) {
            gridView = new View(mContext);
            gridView = layoutInflater.inflate(R.layout.layout_menu_card_view_content, null);

            CardView gameCard = (CardView) gridView.findViewById(R.id.game_card);
            ImageView gameIcon = (ImageView) gridView.findViewById(R.id.game_icon);

            gameIcon.setImageResource(games[position].getIcon());

            gameCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openGame = new Intent(mContext, games[position].getGame());
                    mContext.startActivity(openGame);
                }
            });

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}
