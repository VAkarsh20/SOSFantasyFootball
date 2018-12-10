package com.example.daman.sosfantasyfootball;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StatisticParser implements Serializable {
    private final static String temp = "1-Games Played\n" +
            "2-Pass Attempts\n" +
            "3-Pass Completions\n" +
            "5-Passing Yards\n" +
            "6-Passing TDs\n" +
            "7-Interceptions\n" +
            "8-Sacks\n" +
            "13-Rushing Attempts\n" +
            "14-Rushing Yards\n" +
            "15-Rushing TDs\n" +
            "20-Receptions\n" +
            "21-Receiving Yards\n" +
            "22-Receiving TDs\n" +
            "31-Fumbles\n" +
            "32-Fumbles Lost";
    private final static String TAG = "SOS:StatisticParser";
    private final static String fileName = "idToStatRefernce.txt";
    public static Map<Integer, String> idToStatName;
    public static Map<String, Integer> statNameToId;
    private static Context statContext;

    private Map<String, Double> statistics;


    StatisticParser(JSONObject stats) {
        if (idToStatName == null || statNameToId == null) {
            referenceCreator();
        }
        Map<String, Double> toSet = new HashMap<String, Double>();
        Iterator<String> keys = stats.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            try {
                double measure = stats.getDouble(key);
                String statName = idToStatName.get(Integer.valueOf(key));
                toSet.put(statName, measure);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        this.statistics = toSet;
    }

    public static Map<String, Player> readCache() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(new File(MainActivity.getContext().getFilesDir(), "") + "cacheFile.srl")));
            Map<String, Player> players = (Map<String, Player>) in.readObject();
            in.close();
            return players;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTemp() {
        return temp;
    }

    public static String getTAG() {
        return TAG;
    }

    public static String getFileName() {
        return fileName;
    }

    public static Map<Integer, String> getIdToStatName() {
        return idToStatName;
    }

    public static Map<String, Integer> getStatNameToId() {
        return statNameToId;
    }

    public static Context getStatContext() {
        return statContext;
    }

    public static void referenceCreator() {
        StatisticParser.idToStatName = StatisticParser.idToStatCreator();
        Map<String, Integer> reverseMap = new HashMap<String, Integer>();
        for (Map.Entry<Integer, String> entry : idToStatName.entrySet()) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }
        StatisticParser.statNameToId = reverseMap;
    }

    public static Map<Integer, String> idToStatCreator() throws IllegalArgumentException {
        Map<Integer, String> idToStatName = new TreeMap<>();
        //List<String> reference = getLines();
        String[] reference = temp.split("\n");
        for (String s : reference) {
            String[] parts = s.split("-");
            if (parts.length != 2) {
                throw new IllegalArgumentException();
            }
            idToStatName.put(Integer.parseInt(parts[0].trim()), parts[1].trim());
        }
        return idToStatName;
    }

    public static List<String> getLines() {
        List<String> lines = new ArrayList<String>();
        AssetManager am = statContext.getAssets();
        try {
            InputStream is = am.open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null)
                lines.add(line);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return lines;
    }

    public static boolean validNumber(double toCheck) {
        return toCheck == Double.POSITIVE_INFINITY || toCheck == Double.NEGATIVE_INFINITY || toCheck == Double.NaN;
    }

    public static double completionPercentage(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        toReturn = stats.getOrDefault("Pass Completions", 0.0) / stats.getOrDefault("Pass Attempts", 0.0);
        if (validNumber(toReturn)) {
            toReturn = 0.0;
        }
        stats.put("Completion Percentage", toReturn * 100);
        return toReturn;
    }

    public static double passingAttemptsPerGame(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        toReturn = stats.getOrDefault("Pass Attempts", 0.0) / stats.getOrDefault("Games Played", 0.0);
        if (validNumber(toReturn)) {
            toReturn = 0.0;
        }
        stats.put("Passing Attempts/G", toReturn);
        return toReturn;
    }

    public static double averageYardsPerAttempt(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        toReturn = stats.getOrDefault("Passing Yards", 0.0) / stats.getOrDefault("Pass Attempts", 0.0);
        if (validNumber(toReturn)) {
            toReturn = 0.0;
        }
        stats.put("Average Yards/A", toReturn);
        return toReturn;
    }

    public static double passingYardsPerGame(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        toReturn = stats.getOrDefault("Passing Yards", 0.0) / stats.getOrDefault("Games Played", 0.0);
        if (validNumber(toReturn)) {
            toReturn = 0.0;
        }
        stats.put("Passing Yards/G", toReturn);
        return toReturn;
    }

    public static double touchdownPercentage(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        toReturn = stats.getOrDefault("Passing TDs", 0.0) / stats.getOrDefault("Pass Attempts", 0.0);
        if (validNumber(toReturn)) {
            toReturn = 0.0;
        }
        stats.put("Touchdown %", toReturn * 100);
        return toReturn;
    }

    public static double interceptionPerPassingAttempt(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        toReturn = stats.getOrDefault("Interceptions", 0.0) / stats.getOrDefault("Pass Attempts", 0.0);
        if (validNumber(toReturn)) {
            toReturn = 0.0;
        }
        stats.put("Interceptions/A", toReturn);
        return toReturn;
    }

    public static double rushYardsPerAttempt(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        toReturn = stats.getOrDefault("Rushing Yards", 0.0) / stats.getOrDefault("Rushing Attempts", 0.0);
        if (validNumber(toReturn)) {
            toReturn = 0.0;
        }
        stats.put("Rushing Yards/A", toReturn);
        return toReturn;
    }

    public static double rushYardsPerGame(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        toReturn = stats.getOrDefault("Rushing Yards", 0.0) / stats.getOrDefault("Games Played", 0.0);
        if (validNumber(toReturn)) {
            toReturn = 0.0;
        }
        stats.put("Rushing Yards/G", toReturn);
        return toReturn;
    }

    public static double yardsPerReception(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        toReturn = stats.getOrDefault("Receiving Yards", 0.0) / stats.getOrDefault("Receptions", 0.0);
        if (validNumber(toReturn)) {
            toReturn = 0.0;
        }
        stats.put("Yards/Rec", toReturn);
        return toReturn;
    }

    public static double receivingYardsPerGame(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        toReturn = stats.getOrDefault("Receiving Yards", 0.0) / stats.getOrDefault("Games Played", 0.0);
        if (validNumber(toReturn)) {
            toReturn = 0.0;
        }
        stats.put("Receiving Yards/G", toReturn);
        return toReturn;
    }

    public static double correctBounds(double num) {
        if (num > 2.375) {
            return 2.375;
        } else if (num < 0) {
            return 0;
        } else {
            return num;
        }
    }

    public static double passerRating(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        if (player.isQuarterBack()) {
            double a = correctBounds((StatisticParser.completionPercentage(player) - 0.3) * 5);
            double b = correctBounds((StatisticParser.averageYardsPerAttempt(player) - 3) * 0.25);
            double c = correctBounds(StatisticParser.touchdownPercentage(player) * 20);
            double d = correctBounds(2.375 - (StatisticParser.interceptionPerPassingAttempt(player) - 25));
            toReturn = ((a + b + c + d) / 6) * 100;
        }
        stats.put("Passer Rating", toReturn);
        return toReturn;
    }

    public static double passingTouchdownsPerGame(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        toReturn = stats.getOrDefault("Passing TDs", 0.0) / stats.getOrDefault("Games Played", 0.0);
        if (validNumber(toReturn)) {
            toReturn = 0.0;
        }
        stats.put("Passing TDs/G", toReturn);
        return toReturn;
    }

    public static double rushingTouchdownsPerGame(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        toReturn = stats.getOrDefault("Rushing TDs", 0.0) / stats.getOrDefault("Games Played", 0.0);
        if (validNumber(toReturn)) {
            toReturn = 0.0;
        }
        stats.put("Rushing TDs/G", toReturn);
        return toReturn;
    }

    public static double receivingTouchdownsPerGame(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        toReturn = stats.getOrDefault("Receiving TDs", 0.0) / stats.getOrDefault("Games Played", 0.0);
        if (validNumber(toReturn)) {
            toReturn = 0.0;
        }
        stats.put("Receiving TDs/G", toReturn);
        return toReturn;
    }

    public static double rushingAttemptsPerGame(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        toReturn = stats.getOrDefault("Rushing Attempts", 0.0) / stats.getOrDefault("Games Played", 0.0);
        if (validNumber(toReturn)) {
            toReturn = 0.0;
        }
        stats.put("Rushing Attempts/G", toReturn);
        return toReturn;
    }

    public static double receptionsPerGame(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        toReturn = stats.getOrDefault("Receptions", 0.0) / stats.getOrDefault("Games Played", 0.0);
        if (validNumber(toReturn)) {
            toReturn = 0.0;
        }
        stats.put("Rec/G", toReturn);
        return toReturn;
    }

    public static double interceptionsPerGame(Player player) {
        double toReturn = 0.0;
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        toReturn = stats.getOrDefault("Interceptions", 0.0) / stats.getOrDefault("Games Played", 0.0);
        if (validNumber(toReturn)) {
            toReturn = 0.0;
        }
        stats.put("Interceptions/G", toReturn);
        return toReturn;
    }

    @Override
    public String toString() {
        return "StatisticParser{" +
                "statistics=" + statistics +
                '}';
    }

    public Map<String, Double> getStatistics() {
        return this.statistics;
    }


}
