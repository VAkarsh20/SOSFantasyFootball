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

public class ReceivingStatistics extends Fragment {
    private String[] statsNames = {"Receptions", "Receiving Yards", "Receiving TDs", "Yards/Rec", "Rec/G", "Receiving Yards/G", "Receiving TDs/G"};
    private double[] receivingStatsPlayer1;
    private double[] receivingStatsPlayer2;
    private TableLayout tl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        receivingStatsPlayer1 = StatisticsTab.fetchStats(statsNames, StatisticsTab.getPlayer1());
        receivingStatsPlayer2 = StatisticsTab.fetchStats(statsNames, StatisticsTab.getPlayer2());
        Log.d(StatisticsTab.TAG, Arrays.toString(receivingStatsPlayer1));
        Log.d(StatisticsTab.TAG, Arrays.toString(receivingStatsPlayer2));
        TableLayout toReplace = StatisticsTab.createTable(receivingStatsPlayer1, receivingStatsPlayer2, statsNames);
        return toReplace;
    }




}
