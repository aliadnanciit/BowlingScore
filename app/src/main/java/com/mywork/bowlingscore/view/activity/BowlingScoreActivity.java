package com.mywork.bowlingscore.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mywork.bowlingscore.R;
import com.mywork.bowlingscore.common.Logger;
import com.mywork.bowlingscore.view.adapter.GameAdapter;
import com.mywork.bowlingscore.viewmodel.BowlingScoreViewModel;

import java.util.ArrayList;
import java.util.List;

public class BowlingScoreActivity extends AppCompatActivity {
    final static String TAG = "BowlingScoreActivity";

    BowlingScoreViewModel viewModel;
    List<Integer> scoreOfFrames = new ArrayList<Integer>();

    TextView player;
    RecyclerView recycler;
    GameAdapter adapter = new GameAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bowling_score);

        initViews();

        viewModel = ViewModelProviders.of(this).get(BowlingScoreViewModel.class);
        viewModel.getOnGameScoreUpdate().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(@Nullable List<Integer> scoreList) {
                if(scoreList == null || scoreList.size() == 0) {
                    return;
                }
                scoreOfFrames.clear();
                scoreOfFrames.addAll(scoreList);

                logTotalScore();
                refreshScoreCard();
            }
        });

        for(int i = 0; i<15; i++) {
            viewModel.roll(1);
        }
    }

    private void initViews() {

        player = (TextView) findViewById(R.id.player);
        player.setText("Player 1");
        recycler = (RecyclerView) findViewById(R.id.score_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler.setAdapter(adapter);
    }

    private void refreshScoreCard() {
        adapter.updateScoreList(scoreOfFrames);
    }

    private void logTotalScore() {
        int totalScore = 0;
        for(int score : scoreOfFrames) {
            totalScore += score;
        }
        Logger.i(TAG, "Total Score so far : "+totalScore);
    }
}
