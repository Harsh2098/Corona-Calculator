package com.hmproductions.coronacalculator;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    public static final String ENTRIES = "entries";

    ArrayList<Entry> entries;
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        setTitle("Corona Characteristics");

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lineChart = findViewById(R.id.ratioLineChart);

        entries = getIntent().getParcelableArrayListExtra(ENTRIES);

        LineDataSet lineDataSet = new LineDataSet(entries, "Height to Spacing Ratio");

        lineDataSet.setDrawCircles(false);
        lineDataSet.setLineWidth(4);
        lineDataSet.setColor(Color.parseColor("#FF8800"));

        LineData lineData = new LineData(lineDataSet);
        lineData.setDrawValues(false);

        lineChart.setData(lineData);
        lineChart.setContentDescription("");

        lineChart.getXAxis().setTextSize(10);
        lineChart.getXAxis().setAxisLineWidth(3);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setAxisLineColor(Color.parseColor("#00ddff"));
        lineChart.getXAxis().setAxisMaximum((float) 100);

        lineChart.getAxisLeft().setTextSize(10);
        lineChart.getAxisLeft().setAxisLineWidth(3);
        lineChart.getAxisLeft().setAxisLineColor(Color.parseColor("#00ddff"));
        lineChart.getAxisLeft().setAxisMinimum(0);

        lineChart.getAxisRight().setTextSize(10);
        lineChart.getAxisRight().setAxisLineWidth(3);
        lineChart.getAxisRight().setAxisLineColor(Color.parseColor("#00ddff"));
        lineChart.getAxisRight().setAxisMinimum(0);

        lineChart.animateX(1500);
    }
}
