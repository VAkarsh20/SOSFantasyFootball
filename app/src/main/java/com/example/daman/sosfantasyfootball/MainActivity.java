package com.example.daman.sosfantasyfootball;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.TreeMap;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "SOS:Main";
    private JSONObject nflResponse;
    private Map<String, Player> players;
    private RequestQueue requestQueue;

    private EditText player1;
    private EditText player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.requestQueue = Volley.newRequestQueue(this);
        start_call();
        readCache();
        setContentView(R.layout.test_view);

        player1 = (EditText) this.findViewById(R.id.p1);
        player2 = (EditText) this.findViewById(R.id.p2);

        Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityStatistics(v);
            }
        });
    }


    public void openActivityStatistics(View v) {
        try {
            Map<String, Player> players = readCache();
            if (players == null) {
                return;
            }
            String player1Name = Player.formatName(player1.getText().toString());
            String player2Name = Player.formatName(player2.getText().toString());
            boolean validP1 = checkEnteredString(players, player1Name);
            boolean validP2 = checkEnteredString(players, player2Name);
            if (validP1 && validP2) {
                Player p1 = Player.getPlayer(players, player1Name);
                Player p2 = Player.getPlayer(players, player2Name);
                Intent i = new Intent(this, StatisticsTab.class);
                i.putExtra("player1", p1);
                i.putExtra("player2", p2);
                startActivity(i);
            } else {
                if (validP1 && !validP2) {
                    player2.setError("Player not found.");
                } else if (validP2 && !validP1) {
                    player1.setError("Player not found.");
                } else {
                    player1.setError("Player not found.");
                    player2.setError("Player not found.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkEnteredString(Map<String, Player> players, String name) {
        return players.containsKey(name);
    }

    private Map<String, Player> readCache() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(new File(getCacheDir(), "") + "cacheFile.srl")));
            Map<String, Player> players = (Map<String, Player>) in.readObject();
            in.close();
            return players;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void start_call() {
        String URL = "https://api.fantasy.nfl.com/v1/players/stats?statType=seasonStats&season=2018&format=json";
        JSONObject toReturn = null;
        final JsonObjectRequest volleyRes = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ObjectOutput out = new ObjectOutputStream(new FileOutputStream(new File(getCacheDir(), "") + "cacheFile.srl"));
                    Map<String, Player> players = Player.constructPlayerTree(response);
                    out.writeObject(players);
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.w(TAG, e.toString());
            }
        });
        requestQueue.add(volleyRes);
    }
}
