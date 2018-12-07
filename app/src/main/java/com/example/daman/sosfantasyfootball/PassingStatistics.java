package com.example.daman.sosfantasyfootball;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PassingStatistics extends Fragment {
    private String[] stats = {"Pass Attempts", "Pass Completions", "Passing Yards", "Passing TDs", "Interceptions", "Sacks"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.passing_statistics, container, false);
        return rootView;
    }
}
