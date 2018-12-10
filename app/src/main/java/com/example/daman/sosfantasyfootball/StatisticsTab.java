package com.example.daman.sosfantasyfootball;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Map;

public class StatisticsTab extends AppCompatActivity {

    public static final String TAG = "StatisticsTab";
    private static Player player1;
    private static Player player2;
    private SectionPageAdapter mSectionsAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_tabs);

        Intent i = getIntent();
        player1 = (Player) i.getSerializableExtra("player1");
        player2 = (Player) i.getSerializableExtra("player2");

        Log.d(TAG, player1.toString());
        Log.d(TAG, player2.toString());

        mSectionsAdapter = new SectionPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new PassingStatistics(), "Passing");
        adapter.addFragment(new RushingStatistics(), "Rushing");
        adapter.addFragment(new ReceivingStatistics(), "Receiving");
        adapter.addFragment(new Prediction(), "Prediction");
        viewPager.setAdapter(adapter);
    }

    public static double[] fetchStats(String[] keys, Player player) {
        double[] stats = new double[keys.length];
        Map<String, Double> playerStatsMap = player.getStats().getStatistics();
        for (int i = 0; i < keys.length; i++) {
            stats[i] = playerStatsMap.getOrDefault(keys[i], 0.0);
        }
        return stats;
    }

    public static Player getPlayer1() {
        return player1;
    }

    public static Player getPlayer2() {
        return player2;
    }

    public static TableLayout createTable(double[] statsPlayer1, double[] statsPlayer2, String[] statsNames) {
        TableLayout toReturn = new TableLayout(MainActivity.getContext());
        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        toReturn.setGravity(Gravity.FILL_HORIZONTAL);
        lp.gravity = Gravity.FILL_HORIZONTAL;
        DecimalFormat df = new DecimalFormat("#.##");
        toReturn.setLayoutParams(lp);
        for (int i = 0; i < statsNames.length + 1; i++) {
            if (i == 0) {
                toReturn.addView(createRow(StatisticsTab.getPlayer1().getName(), "", StatisticsTab.getPlayer2().getName()));
            } else {
                toReturn.addView(createRow(df.format(statsPlayer1[i - 1]), statsNames[i - 1], df.format(statsPlayer2[i - 1])));
            }
        }
        return toReturn;
    }

    public static TableRow createRow(String p1Stat, String statName, String p2Stat) {
        TableRow toReturn = new TableRow(MainActivity.getContext());
        TableRow.LayoutParams trtl
                = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        toReturn.setGravity(Gravity.CENTER_HORIZONTAL);
        trtl.gravity = Gravity.CENTER_HORIZONTAL;
        TextView col1 = createTextView(p1Stat, 0);
        TextView col2 = createTextView(statName, 1);
        TextView col3 = createTextView(p2Stat, 2    );
        //int p1StatInt = Integer.parseInt(p1Stat);
        //int p2StatInt = Integer.parseInt(p2Stat);
        if (statName.equals("")) {

        } else if (statName.equals("Interceptions") || statName.equals("Sacks") || statName.equals("Interceptions/G")
                || statName.equals("Interceptions/A")) {
            if (p1Stat.compareTo(p2Stat) < 0) {
                col1.setTextColor(Color.GREEN);
                col3.setTextColor(Color.RED);
            } else if (p2Stat.compareTo(p1Stat) < 0) {
                col1.setTextColor(Color.RED);
                col3.setTextColor(Color.GREEN);
            }
        } else {
            if (p1Stat.compareTo(p2Stat) > 0) {
                col1.setTextColor(Color.GREEN);
                col3.setTextColor(Color.RED);
            } else if (p2Stat.compareTo(p1Stat) < 0) {
                col1.setTextColor(Color.RED);
                col3.setTextColor(Color.GREEN);
            }
        }
        toReturn.addView(col1);
        toReturn.addView(col2);
        toReturn.addView(col3);
        return toReturn;
    }

    public static TextView createTextView(String text, int column) {
        TableRow.LayoutParams tvlp
                = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(MainActivity.getContext());
        if (column == 1) {
            tvlp.gravity = Gravity.CENTER_HORIZONTAL;
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
        } else if (column == 2) {
            tvlp.gravity = Gravity.RIGHT;
            tv.setGravity(Gravity.RIGHT);
        }

        tv.setPadding(24,16,24,16);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        tv.setLayoutParams(tvlp);

        return tv;
    }

}
