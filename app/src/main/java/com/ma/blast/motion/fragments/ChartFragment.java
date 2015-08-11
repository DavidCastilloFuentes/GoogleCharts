package com.ma.blast.motion.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.ma.blast.motion.bin.Chart;

/**
 * Generic fragment for presenting a graph in the ViewPager
 */
public class ChartFragment extends Fragment {
    private Chart chart;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param chart Chart.
     * @return A new instance of fragment ChartFragment.
     */
    public static ChartFragment newInstance(Chart chart) {
        ChartFragment fragment = new ChartFragment();
        fragment.chart = chart;
        return fragment;
    }

    // Base Constructor
    public ChartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return getChart(inflater);
    }

    private View getChart(LayoutInflater inflater) {
        View view = null;
        ViewGroup.LayoutParams mLP = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        switch(chart.getType()) {
            case LINE: {
                LineData ld;
                LineDataSet lds;
                LineChart lc;
                view = lc = new LineChart(inflater.getContext());

                lds = new LineDataSet(chart.getYValues(), "RMS");
                lds.setAxisDependency(YAxis.AxisDependency.LEFT);
                ld = new LineData(chart.getXValues(), lds);
                lc.setData(ld);
                lc.setDescription("");
                lc.setLayoutParams(mLP);
            }
                break;
            case PIE:{
                PieData pd;
                PieChart pc;
                view = pc = new PieChart(inflater.getContext());
                pc.setUsePercentValues(true);

                PieDataSet pds;
                pds = new PieDataSet(chart.getYValues(), "");
                pds.setAxisDependency(YAxis.AxisDependency.LEFT);
                pds.setSliceSpace(3f);

                pd = new PieData(chart.getXValues(), pds);
                pc.setData(pd);
                pc.setDescription("");
                pc.setLayoutParams(mLP);
                pc.setRotationEnabled(false);
            }
                break;
            case BAR:
                BarData bd;
                BarChart bc;
                view = bc = new BarChart(inflater.getContext());

                BarDataSet bds;
                bds = new BarDataSet(chart.getMBar(), "Entries");
                bds.setAxisDependency(YAxis.AxisDependency.LEFT);


                bd = new BarData(chart.getXValues(), bds);

                bc.setData(bd);
                bc.setDescription("");
                bc.setLayoutParams(mLP);

                YAxis leftAxis = bc.getAxisLeft();
                leftAxis.setLabelCount(2);
                leftAxis.setStartAtZero(false);
                leftAxis.setAxisMinValue(-1.5f);
                leftAxis.setAxisMaxValue(1.5f);

                YAxis rightAxis = bc.getAxisRight();
                rightAxis.setDrawGridLines(false);
                rightAxis.setLabelCount(2);
                rightAxis.setStartAtZero(false);
                rightAxis.setAxisMinValue(-1.5f);
                rightAxis.setAxisMaxValue(1.5f);

                break;
        }
        return view;
    }
}
