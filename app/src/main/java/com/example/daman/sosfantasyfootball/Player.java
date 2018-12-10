package com.example.daman.sosfantasyfootball;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Player implements Serializable {
    private static String TAG = "SOS:PlayerClass";
    private String name;
    private String position;
    private String teamAbbr;
    private StatisticParser stats;
    private double seasonPts;
    private double seasonProjectedPts;
    private double weekPts;
    private double weekProjectedPts;

    public Player(JSONObject toParse) {
        try {
            this.name = toParse.getString("name");
            this.position = toParse.getString("position");
            this.teamAbbr = toParse.getString("teamAbbr");
            this.stats = new StatisticParser(toParse.getJSONObject("stats"));
            this.seasonPts = toParse.getDouble("seasonPts");
            this.seasonProjectedPts = toParse.getDouble("seasonProjectedPts");
            this.weekPts = toParse.getDouble("weekPts");
            this.weekProjectedPts = toParse.getDouble("weekProjectedPts");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public static Map<String, Player> constructPlayerTree(JSONObject response) {
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
                Player p = new Player(jsonPlayers.getJSONObject(i));
                addPositionStats(p);
                players.put(p.name, p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return players;
    }

    public static String formatName(String name) {
        if (name == null) {
            return name;
        }
        String[] arr = name.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    public static Player getPlayer(Map<String, Player> players, String name) {
        return players.get(name);
    }

    public static String getTAG() {
        return TAG;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    public String getPosition() {
        return this.position;
    }

    public static void addPositionStats(Player p) {
        StatisticParser.completionPercentage(p);
        StatisticParser.passingAttemptsPerGame(p);
        StatisticParser.averageYardsPerAttempt(p);
        StatisticParser.passingYardsPerGame(p);
        StatisticParser.touchdownPercentage(p);
        StatisticParser.interceptionPerPassingAttempt(p);
        StatisticParser.rushYardsPerAttempt(p);
        StatisticParser.rushYardsPerGame(p);
        StatisticParser.yardsPerReception(p);
        StatisticParser.receivingYardsPerGame(p);
        StatisticParser.passerRating(p);
        StatisticParser.passingTouchdownsPerGame(p);
        StatisticParser.rushingTouchdownsPerGame(p);
        StatisticParser.receivingTouchdownsPerGame(p);
        StatisticParser.rushingAttemptsPerGame(p);
        StatisticParser.receptionsPerGame(p);
        StatisticParser.interceptionsPerGame(p);
    }

    public StatisticParser getStats() {
        return this.stats;
    }

    public boolean isQuarterBack() {
        return this.position.equals("QB");
    }

    public boolean isRunningBack() {
        return this.position.equals("RB");
    }

    public boolean isWideReciever() {
        return this.position.equals("WR");
    }

    public boolean isDEF() {
        return this.position.equals("DEF");
    }

    public boolean isKicker() {
        return this.position.equals("K");
    }

    public boolean isTE() {
        return this.position.equals("TE");
    }

    public String getName() {
        return name;
    }

    public String getTeamAbbr() {
        return teamAbbr;
    }

    public double getSeasonPts() {
        return seasonPts;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", teamAbbr='" + teamAbbr + '\'' +
                ", stats=" + stats +
                ", seasonPts=" + seasonPts +
                ", seasonProjectedPts=" + seasonProjectedPts +
                ", weekPts=" + weekPts +
                ", weekProjectedPts=" + weekProjectedPts +
                '}';
    }

    public double getSeasonProjectedPts() {
        return seasonProjectedPts;
    }

    public double getWeekPts() {
        return weekPts;
    }

    public double getWeekProjectedPts() {
        return weekProjectedPts;
    }
}
