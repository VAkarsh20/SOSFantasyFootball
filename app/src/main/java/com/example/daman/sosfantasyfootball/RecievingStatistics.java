package com.example.daman.sosfantasyfootball;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;

public class RecievingStatistics extends Fragment {
    private String[] statsNames = {"Receptions", "Recieving Yards", "Recieving TDs"};
    private double[] recievingStatsPlayer1;
    private double[] recievingStatsPlayer2;
    private TableLayout tl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        recievingStatsPlayer1 = StatisticsTab.fetchStats(statsNames, StatisticsTab.getPlayer1());
        recievingStatsPlayer2 = StatisticsTab.fetchStats(statsNames, StatisticsTab.getPlayer2());
        Log.d(StatisticsTab.TAG, Arrays.toString(recievingStatsPlayer1));
        Log.d(StatisticsTab.TAG, Arrays.toString(recievingStatsPlayer2));
        TableLayout toReplace = StatisticsTab.createTable(recievingStatsPlayer1, recievingStatsPlayer2, statsNames);
        return toReplace;
    }




}
