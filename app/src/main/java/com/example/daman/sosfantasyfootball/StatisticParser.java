package com.example.daman.sosfantasyfootball;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StatisticParser {
    private final static String TAG = "SOS:StatisticParser";
    private final static String fileName = "idToStatRefernce.txt";
    public static Map<Integer, String> idToStatName;
    public static Map<String, Integer> statNameToId;
    private static Context statContext;

    private Map<String, Double> statistics;

    StatisticParser(JSONObject stats, Context context) {
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

    public Map<String, Double> getStatistics() {
        return this.statistics;
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
        List<String> reference = getLines();
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

    public static double completionPercentage(Player player) {
        if (player.isQuarterBack()) {
            StatisticParser playerStats = player.getStats();
            Map<String, Double> stats = playerStats.getStatistics();
            return stats.get("Pass Completions") / stats.get("Pass Attempts");
        } else {
            return 0.0;
        }
    }

    public static double passingAttemptsPerGame(Player player) {
        if (player.isQuarterBack()) {
            StatisticParser playerStats = player.getStats();
            Map<String, Double> stats = playerStats.getStatistics();
            return stats.get("Pass Attemps") / stats.get("Games Played");
        } else {
            return 0.0;
        }
    }

    public static double averageYardsPerAttempt(Player player) {
        if (player.isQuarterBack()) {
            StatisticParser playerStats = player.getStats();
            Map<String, Double> stats = playerStats.getStatistics();
            return stats.get("Passing Yards") / stats.get("Pass Attempts");
        } else {
            return 0.0;
        }
    }

    public static double passingYardsPerGame(Player player) {
        if (player.isQuarterBack()) {
            StatisticParser playerStats = player.getStats();
            Map<String, Double> stats = playerStats.getStatistics();
            return stats.get("Passing Yards") / stats.get("Games Played");
        } else {
            return 0.0;
        }
    }

    public static double touchdownPercentage(Player player) {
        if (player.isQuarterBack()) {
            StatisticParser playerStats = player.getStats();
            Map<String, Double> stats = playerStats.getStatistics();
            return stats.get("Pass Completions") / stats.get("Pass Attempts");
        } else {
            return 0.0;
        }
    }

    public static double interceptionPerPassingAttempt(Player player) {
        if (player.isQuarterBack()) {
            StatisticParser playerStats = player.getStats();
            Map<String, Double> stats = playerStats.getStatistics();
            return stats.get("Interceptions") / stats.get("Pass Attempts");
        } else {
            return 0.0;
        }
    }

    public static double rushYardsPerAttempt(Player player) {
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        if (!stats.containsKey("Rushing Yards") && !stats.containsKey("Rushing Attempts")) {
            return 0.0;
        }
        return stats.get("Rushing Yards") / stats.get("Rushing Attempts");

    }

    public static double rushYardsPerGame(Player player) {
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        if (!stats.containsKey("Rushing Yards") && !stats.containsKey("Games Played")) {
            return 0.0;
        }
        return stats.get("Rushing Yards") / stats.get("Games Played");
    }

    public static double yardsPerReception(Player player) {
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        if (!stats.containsKey("Rushing Yards") && !stats.containsKey("Receptions")) {
            return 0.0;
        }
        return stats.get("Recieving Yards") / stats.get("Receptions");
    }

    public static double recievingYardsPerGame(Player player) {
        StatisticParser playerStats = player.getStats();
        Map<String, Double> stats = playerStats.getStatistics();
        if (!stats.containsKey("Rushing Yards") && !stats.containsKey("Games Played")) {
            return 0.0;
        }
        return stats.get("Recieving Yards") / stats.get("Games Played");
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
        if (player.isQuarterBack()) {
            double a = correctBounds((StatisticParser.completionPercentage(player) - 0.3) * 5);
            double b = correctBounds((StatisticParser.averageYardsPerAttempt(player) - 3) * 0.25);
            double c = correctBounds(StatisticParser.touchdownPercentage(player) * 20);
            double d = correctBounds(2.375 - (StatisticParser.interceptionPerPassingAttempt(player) - 25));
            return ((a + b + c + d) / 6) * 100;
        }
        return 0.0;
    }


}
