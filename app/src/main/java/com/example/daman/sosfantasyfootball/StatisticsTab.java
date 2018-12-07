package com.example.daman.sosfantasyfootball;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class StatisticsTab extends AppCompatActivity {

    private static final String TAG = "StatisticsTab";
    private SectionPageAdapter mSectionsAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_tabs);

        Intent i = getIntent();
        Player p1 = (Player) i.getSerializableExtra("player1");
        Player p2 = (Player) i.getSerializableExtra("player2");

        Log.d(TAG, p1.toString());
        Log.d(TAG, p2.toString());

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
        adapter.addFragment(new RecievingStatistics(), "Recieving");
        viewPager.setAdapter(adapter);
    }

}
