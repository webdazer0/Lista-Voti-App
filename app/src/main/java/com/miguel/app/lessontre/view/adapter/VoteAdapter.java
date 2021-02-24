package com.miguel.app.lessontre.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miguel.app.lessontre.R;
import com.miguel.app.lessontre.model.Vote;

import java.util.List;

public class VoteAdapter extends BaseAdapter {

    Context ctx;
    List<Vote> votes;
    LayoutInflater inflater;

    public VoteAdapter(Context context, List<Vote> votes) {
        this.ctx = context;
        this.votes = votes;
        this.inflater = LayoutInflater.from(ctx);
    }

    public void loadData(List<Vote> votes) {
        this.votes = votes;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return votes.size();
    }

    @Override
    public Object getItem(int position) {
        return votes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return votes.get(position).getPk();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.votes_element, null);

        TextView voto = (TextView)convertView.findViewById(R.id.lviVoto);

        if(votes.get(position).getScore() < 18) voto.setTextColor(Color.RED);

        voto.setText(votes.get(position).getScoreString());

        return convertView;
    }
}
