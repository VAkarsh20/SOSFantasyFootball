package com.example.daman.sosfantasyfootball;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class StatisticsTab extends AppCompatActivity {

    private static final String TAG = "StatisticsTab";
    private SectionPageAdapter mSectionsAdapter;
	private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_tabs);
		
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
