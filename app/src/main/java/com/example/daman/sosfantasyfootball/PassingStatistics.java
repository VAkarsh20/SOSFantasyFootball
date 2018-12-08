package com.example.daman.sosfantasyfootball;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import java.util.Arrays;

public class PassingStatistics extends Fragment {
    private String[] statsNames = {"Pass Attempts", "Pass Completions", "Passing Yards", "Passing TDs", "Interceptions", "Sacks"};
    private double[] passingStatsPlayer1;
    private double[] passingStatsPlayer2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        passingStatsPlayer1 = StatisticsTab.fetchStats(statsNames, StatisticsTab.getPlayer1());
        passingStatsPlayer2 = StatisticsTab.fetchStats(statsNames, StatisticsTab.getPlayer2());
        Log.d(StatisticsTab.TAG, Arrays.toString(passingStatsPlayer1));
        Log.d(StatisticsTab.TAG, Arrays.toString(passingStatsPlayer2));
        TableLayout toReplace = StatisticsTab.createTable(passingStatsPlayer1, passingStatsPlayer2, statsNames);
        return toReplace;
    }
}
