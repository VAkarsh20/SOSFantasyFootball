package com.example.daman.sosfantasyfootball;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import java.util.Arrays;

public class Prediction extends Fragment {
    private String[] statsNames = {"Total Season Points", "Projected Season Points", "Our Week Prediction"};
    private double[] predictionStatsPlayer1;
    private double[] predictionStatsPlayer2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        predictionStatsPlayer1 = fetchProjections(StatisticsTab.getPlayer1());
        predictionStatsPlayer2 = fetchProjections(StatisticsTab.getPlayer2());
        Log.d(StatisticsTab.TAG, Arrays.toString(predictionStatsPlayer1));
        Log.d(StatisticsTab.TAG, Arrays.toString(predictionStatsPlayer2));
        TableLayout toReplace = StatisticsTab.createTable(predictionStatsPlayer1, predictionStatsPlayer2, statsNames);
        return toReplace;
    }

    public double[] fetchProjections(Player p) {
        double[] toReturn = {p.getSeasonPts(), p.getSeasonProjectedPts(), p.getWeekProjectedPts()};
        return toReturn;
    }
}