package com.example.daman.sosfantasyfootball;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Player {
    private static String TAG = "SOS:PlayerClass";
    private String name;
    private String position;
    private String teamAbbr;
    private StatisticParser stats;
    private double seasonPts;
    private double seasonProjectedPts;
    private double weekPts;
    private double weekProjectedPts;

    public Player(JSONObject toParse, Context c) {
        try {
            this.name = toParse.getString("name");
            this.position = toParse.getString("position");
            this.teamAbbr = toParse.getString("teamAbbr");
            this.stats = new StatisticParser(toParse.getJSONObject("stats"), c);
            this.seasonPts = toParse.getDouble("seasonPts");
            this.seasonProjectedPts = toParse.getDouble("seasonProjectedPts");
            this.weekPts = toParse.getDouble("weekPts");
            this.weekProjectedPts = toParse.getDouble("weekProjectedPts");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    public static Map<String, Player> constructPlayerTree(JSONObject response, Context c) {
        JSONArray jsonPlayers = null;
        Map<String, Player> players = new TreeMap<String, Player>();
        try {
            jsonPlayers = response.getJSONArray("players");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        for (int i = 0; i < jsonPlayers.length(); i++) {
            try {
                Player p = new Player(jsonPlayers.getJSONObject(i), c);
                players.put(p.name, p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return players;
    }

}
