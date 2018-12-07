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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "SOS:Main";
    private JSONObject nflResponse;
    private Map<String, Player> players;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestQueue = Volley.newRequestQueue(this);
        start_call();
        readCache();
        //Log.d(TAG, this.nflResponse.toString());
        setContentView(R.layout.activity_main);
        Button submit = findViewById(R.id.button);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        openActivityStatistics(v);
    }

    public void openActivityStatistics(View v) {
        try {
            Map<String, Player> players = readCache();
            if (players == null) {
                return;
            }
//            EditText p1Name = (EditText) v.findViewById(R.id.player1);
//            EditText p2Name = (EditText) v.findViewById(R.id.player2);
//            Player p1 = Player.getPlayer(players, p1Name.getText().toString());
//            Player p2 = Player.getPlayer(players, p2Name.getText().toString());
            Intent i = new Intent(this, StatisticsTab.class);
//            i.putExtra("player1", p1);
//            i.putExtra("player2", p2);
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, Player> readCache() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(new File(getCacheDir(), "") + "cacheFile.srl")));
            Map<String, Player> players = (Map<String, Player>) in.readObject();
            in.close();
            return players;
//            Player p = players.get("Russell Wilson");
//            Log.d(TAG, Double.toString(StatisticParser.completionPercentage(p)));
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
                    //setNflResponse(response);
                    //Log.d(TAG, response.toString());
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
