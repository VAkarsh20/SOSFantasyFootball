package com.example.daman.sosfantasyfootball;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RushingStatistics extends Fragment {
    private String[] stats = {"Rushing Attempts", "Rushing Yards", "Rushing TDs"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rushing_statistics, container, false);
        return rootView;
    }
}
