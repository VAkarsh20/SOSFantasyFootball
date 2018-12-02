package com.example.daman.sosfantasyfootball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SOS:Main";
    private JSONObject nflResponse;
    private static Map<String, Player> players;
    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(this);
        start_call();
        players = Player.constructPlayerTree(this.nflResponse, this);
        setContentView(R.layout.activity_main);
    }

    private void setNflResponse(JSONObject response) {
        this.nflResponse = response;
    }


    void start_call() {
        String URL = "https://api.fantasy.nfl.com/v1/players/stats?statType=seasonStats&season=2018&format=json";
        JSONObject toReturn = null;
        final JsonObjectRequest volleyRes = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    setNflResponse(response);
                    Log.d(TAG, response.toString());
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
