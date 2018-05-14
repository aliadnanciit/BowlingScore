package com.mywork.bowlingscore.viewmodel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ali_d on 4/11/18.
 */

public class BowlingScoreViewModelTest {

    BowlingScoreViewModel viewModel;

    @Before
    public void setup() {
        viewModel = new BowlingScoreViewModel();
    }

    @Test
    public void allMiss() {

        for(int balls = 0; balls < 20; balls++) {
            viewModel.roll(0);
        }
        assertEquals(0, viewModel.scoreSoFar());
    }

    @Test
    public void FirstMissFrameScore() {

        for(int balls = 0; balls < 2; balls++) {
            viewModel.roll(1);
        }
        assertEquals(2, viewModel.scoreSoFar());
    }
}
