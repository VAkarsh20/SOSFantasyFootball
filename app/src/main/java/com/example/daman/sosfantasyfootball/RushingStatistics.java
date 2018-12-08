package com.example.daman.sosfantasyfootball;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import java.util.Arrays;

public class RushingStatistics extends Fragment {
    private String[] statsNames = {"Rushing Attempts", "Rushing Yards", "Rushing TDs"};
    private double[] rushingStatsPlayer1;
    private double[] rushingStatsPlayer2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rushingStatsPlayer1 = StatisticsTab.fetchStats(statsNames, StatisticsTab.getPlayer1());
        rushingStatsPlayer2 = StatisticsTab.fetchStats(statsNames, StatisticsTab.getPlayer2());
        Log.d(StatisticsTab.TAG, Arrays.toString(rushingStatsPlayer1));
        Log.d(StatisticsTab.TAG, Arrays.toString(rushingStatsPlayer2));
        TableLayout toReplace = StatisticsTab.createTable(rushingStatsPlayer1, rushingStatsPlayer2, statsNames);
        return toReplace;
    }
}
