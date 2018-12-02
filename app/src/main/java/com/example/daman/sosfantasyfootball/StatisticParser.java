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

    public static void referenceCreator() {
        StatisticParser.idToStatName = StatisticParser.idToStatCreator();
        Map<String, Integer> reverseMap =  new HashMap<String, Integer>();
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

}
