package com.mywork.bowlingscore.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mywork.bowlingscore.common.BowlingScore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ali_d on 4/11/18.
 */

public class BowlingScoreViewModel extends ViewModel implements BowlingScore {
    MutableLiveData<List<Integer>> onGameScoreUpdate = new MutableLiveData<>();

    private int scoreOfBall[] = new int[TOTAL_BALLS_IN_GAME];
    private int currentBall;

    public BowlingScoreViewModel() {
        Arrays.fill(scoreOfBall, NONE);
        currentBall = 0;
    }

    public int scoreSoFar() {
        List<Integer> list = calculateScoreOfCompletedFrames();
        int scoreSoFar = 0;
        for(int frameScore : list) {
            scoreSoFar += frameScore;
        }
        return scoreSoFar;
    }

    // take pins
    // should return score of completed frames
    public List<Integer> roll(int pins) {
        // save current ball score
        scoreOfBall[currentBall++] = pins;

        // calculate score of frames
        List<Integer> list = calculateScoreOfCompletedFrames();
        onGameScoreUpdate.setValue(list);

        return list;
    }

    private List<Integer> calculateScoreOfCompletedFrames() {
        List<Integer> scoreOfFrameList = new ArrayList<Integer>();

        // loop through 10 frames and find score of frame if frame is complete
        int indexOfFirstBallOfFrame = 0, scoreOfFrame;
        for(int frame = 0; frame < TOTAL_FRAMES_IN_GAME; frame++) {
            scoreOfFrame = 0; // init score every time

            if(scoreOfBall[indexOfFirstBallOfFrame] == 10) { // strike
                scoreOfFrame = 10;
                int bonus = calculateBonus(NUMBER_OF_BALLS_ON_STRIKE);
                if(bonus == NONE) {
                    return scoreOfFrameList;
                }
                scoreOfFrame += bonus;
            }
            else if(scoreOfBall[indexOfFirstBallOfFrame] + scoreOfBall[indexOfFirstBallOfFrame+1] == 10) { // spare
                int bonus = calculateBonus(NUMBER_OF_BALLS_ON_SPARE);
                if(bonus == NONE) {
                    return scoreOfFrameList;
                }
                scoreOfFrame += bonus;
            }
            else if(scoreOfBall[indexOfFirstBallOfFrame] + scoreOfBall[indexOfFirstBallOfFrame+1] < 10) { // this is miss
                if(scoreOfBall[indexOfFirstBallOfFrame] == NONE || scoreOfBall[indexOfFirstBallOfFrame+1] == NONE) {
                    return scoreOfFrameList;
                }
                scoreOfFrame = scoreOfBall[indexOfFirstBallOfFrame] + scoreOfBall[indexOfFirstBallOfFrame+1];
            }
            indexOfFirstBallOfFrame += 2; // move to next frame

            scoreOfFrameList.add(scoreOfFrame); // add score of frame
        }
        return scoreOfFrameList;
    }
    private int calculateBonus(int howManyNextBalls) {
        int bonusScore = 0, ballIndex = currentBall;

        while(howManyNextBalls > 0 && ballIndex < TOTAL_BALLS_IN_GAME) {
            if(scoreOfBall[ballIndex] == NONE) { // Score other than 0-10 means user has not played these balls or frame
                return NONE;
            }
            bonusScore += scoreOfBall[ballIndex++];
            howManyNextBalls--;
        }

        if(howManyNextBalls != 0) {
            return NONE;
        }

        return bonusScore;
    }

    public MutableLiveData<List<Integer>> getOnGameScoreUpdate() {
        return onGameScoreUpdate;
    }

    public void setOnGameScoreUpdate(List<Integer> framesScoreList) {
        this.onGameScoreUpdate.setValue(framesScoreList);
    }

}
