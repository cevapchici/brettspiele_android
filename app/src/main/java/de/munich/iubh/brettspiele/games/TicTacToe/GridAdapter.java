package de.munich.iubh.brettspiele.games.TicTacToe;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

public class GridAdapter extends BaseAdapter {
    private Context context;

    public GridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageButton button;

        if (convertView == null) {
            button = new ImageButton(context);
            button.setLayoutParams(new GridView.LayoutParams(85, 85));
        } else {
            button = (ImageButton) convertView;
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return button;
    }
}
