package com.example.daman.sosfantasyfootball;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

public class RecievingStatistics extends Fragment {
    private String[] stats = {"Receptions", "Recieving Yards", "Recieving TDs"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.receiving_statistics, container, false);
        return rootView;
    }

}
