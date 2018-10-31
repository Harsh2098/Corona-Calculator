package com.hmproductions.coronacalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.github.mikephil.charting.data.Entry;
import com.hmproductions.coronacalculator.utils.Miscellaneous;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity implements Miscellaneous.GetGraphDetails {

    public static final String ENTRIES = "entries";

    ArrayList<Entry> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        setTitle("Corona Characteristics");

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Binding view for Tabbed Fragments
        ViewPager viewPager = findViewById(R.id.contentViewPager);
        TabLayout tabLayout = findViewById(R.id.tabHeadings);

        entries = getIntent().getParcelableArrayListExtra(ENTRIES);

        // Set adapter to viewpager and custom colors to tabLayout
        viewPager.setAdapter(new ContentAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.colorAccent));
    }

    @Override
    public ArrayList<Entry> getGraphDetails() {
        return entries;
    }
}
