package com.ma.blast.motion.bin;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.List;

public class Chart {
    public enum ChartType {LINE, PIE, BAR}

    List<String> xValues;
    List<Entry> yValues;
    List<BarEntry> mBar;
    ChartType type;
    String name;

    public Chart(ChartType type, List<String> xValues, List<Entry> yValues) {
        this.type = type;
        this.xValues = xValues;
        this.yValues = yValues;
    }

    public Chart(ChartType type, String name, List<String> xValues, List<Entry> yValues) {
        this(type, xValues, yValues);
        this.name = name;
    }

    public ChartType getType() {
        return type;
    }

    public void setType(ChartType type) {
        this.type = type;
    }

    public List<Entry> getYValues() {
        return yValues;
    }

    public List<String> getXValues() {
        return xValues;
    }

    public List<BarEntry> getMBar() {
        return mBar;
    }

    public void setMBar(List<BarEntry> mBar) {
        this.mBar = mBar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
