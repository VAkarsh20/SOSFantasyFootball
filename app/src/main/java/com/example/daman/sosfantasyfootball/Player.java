package com.example.daman.sosfantasyfootball;

import android.util.Log;

import org.json.JSONObject;

public class Player {
    private static String TAG = "SOS:PlayerClass";
    private String name;
    private String position;
    private String teamAbbr;
    private JSONObject stats;
    private double seasonPts;
    private double seasonProjectedPts;
    private double weekPts;
    private double weekProjectedPts;

    public Player(JSONObject toParse) {
        try {
            this.name = toParse.getString("name");
            this.position = toParse.getString("position");
            this.teamAbbr = toParse.getString("teamAbbr");
            this.stats = toParse.getJSONObject("stats");
            this.seasonPts = toParse.getDouble("seasonPts");
            this.seasonProjectedPts = toParse.getDouble("seasonProjectedPts");
            this.weekPts = toParse.getDouble("weekPts");
            this.weekProjectedPts = toParse.getDouble("weekProjectedPts");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
