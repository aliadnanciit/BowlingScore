package com.mywork.bowlingscore.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mywork.bowlingscore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ali_d on 4/11/18.
 */

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private List<Integer> scores = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView scoreOfFrame;
        public ViewHolder(View v) {
            super(v);
            scoreOfFrame = v.findViewById(R.id.score_of_frame);
        }
    }

    public GameAdapter() {
    }

    public void updateScoreList(List<Integer> scores) {
        if(scores == null || scores.size() == 0) {
            return;
        }

        // best way to verify change here then notifiy only specific items instead of all items

        this.scores = scores;
        notifyDataSetChanged();
    }

    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bowling_score_of_frame, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.scoreOfFrame.setText(""+scores.get(position));
    }

    @Override
    public int getItemCount() {
        if(scores == null) {
            return 0;
        }
        return scores.size();
    }
}