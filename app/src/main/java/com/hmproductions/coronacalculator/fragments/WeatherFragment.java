package com.hmproductions.coronacalculator.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.hmproductions.coronacalculator.R;
import com.hmproductions.coronacalculator.utils.Miscellaneous;

import java.util.ArrayList;


public class WeatherFragment extends Fragment {

    private Miscellaneous.GetGraphDetails graphDetails;

    ArrayList<Entry> temperatureEntries, pressureEntries;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            graphDetails = (Miscellaneous.GetGraphDetails) context;
        } catch (ClassCastException classCastException) {
            throw new ClassCastException(context.toString() + " must implement graph details handler.");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View customView =  inflater.inflate(R.layout.fragment_graph, container, false);

        LineChart lineChart = customView.findViewById(R.id.ratioLineChart);

        if (getContext() != null) {

            temperatureEntries = graphDetails.getGraphDetails(Miscellaneous.GraphType.TEMPERATURE.ordinal());
            pressureEntries = graphDetails.getGraphDetails(Miscellaneous.GraphType.PRESSURE.ordinal());

            LineDataSet pressureDataSet = new LineDataSet(pressureEntries, "Pressure Variation");
            pressureDataSet.setDrawCircles(false);
            pressureDataSet.setLineWidth(4);
            pressureDataSet.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

            LineDataSet temperatureDataSet = new LineDataSet(temperatureEntries, "Temperature Variation");
            temperatureDataSet.setDrawCircles(false);
            temperatureDataSet.setLineWidth(4);
            temperatureDataSet.setColor(ContextCompat.getColor(getContext(), R.color.neon_blue));

            LineData lineData = new LineData(temperatureDataSet);
            lineData.addDataSet(pressureDataSet);
            lineData.setDrawValues(false);

            lineChart.setData(lineData);
            lineChart.setContentDescription("");

            lineChart.getXAxis().setTextSize(10);
            lineChart.getXAxis().setAxisLineWidth(3);
            lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            lineChart.getXAxis().setAxisLineColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));

            lineChart.getAxisLeft().setTextSize(10);
            lineChart.getAxisLeft().setAxisLineWidth(3);
            lineChart.getAxisLeft().setAxisLineColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            lineChart.getAxisLeft().setAxisMinimum(0);

            lineChart.getAxisRight().setTextSize(10);
            lineChart.getAxisRight().setAxisLineWidth(3);
            lineChart.getAxisRight().setAxisLineColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            lineChart.getAxisRight().setAxisMinimum(0);

            lineChart.animateX(1500);
        }

        return customView;
    }
}
