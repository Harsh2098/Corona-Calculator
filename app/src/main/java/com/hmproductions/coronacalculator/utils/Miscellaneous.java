package com.hmproductions.coronacalculator.utils;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class Miscellaneous {

    public interface GetGraphDetails {
        ArrayList<Entry> getGraphDetails(int graphType);
    }

    public enum GraphType { RATIO, RADIUS, PRESSURE, TEMPERATURE }
}
