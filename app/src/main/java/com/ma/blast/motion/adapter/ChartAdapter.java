package com.ma.blast.motion.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ma.blast.motion.bin.Chart;
import com.ma.blast.motion.fragments.ChartFragment;

import java.util.List;

public class ChartAdapter extends FragmentStatePagerAdapter {
    List<Chart> charts;

    public ChartAdapter(FragmentManager fm, List<Chart> charts) {
        super(fm);
        this.charts = charts;
    }

    @Override
    public Fragment getItem(int position) {
        return ChartFragment.newInstance(charts.get(position));
    }

    @Override
    public int getCount() {
        return charts.size();
    }
}
