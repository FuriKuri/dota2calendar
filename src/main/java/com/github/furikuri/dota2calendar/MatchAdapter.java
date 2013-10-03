package com.github.furikuri.dota2calendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MatchAdapter extends ArrayAdapter<Match> {
    private final Context context;
    private List<Match> values;

    public MatchAdapter(Context context, List<Match> objects) {
        super(context, R.id.match_list, objects);
        this.context = context;
        this.values = objects;
    }

    public void setNewMatches(List<Match> matches) {
        values.clear();
        values.addAll(matches);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_match, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.team1);
        textView.setText(values.get(position).getTeam1());
        TextView textView2 = (TextView) rowView.findViewById(R.id.team2);
        textView2.setText(values.get(position).getTeam2());
        TextView textView3 = (TextView) rowView.findViewById(R.id.tournament);
        textView3.setText(values.get(position).getTournament());
        Log.d("BLA", "return view");
        return rowView;
    }
}
