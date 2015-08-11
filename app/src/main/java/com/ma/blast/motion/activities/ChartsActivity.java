package com.ma.blast.motion.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.ma.blast.motion.R;
import com.ma.blast.motion.adapter.ChartAdapter;
import com.ma.blast.motion.bin.Chart;
import com.ma.blast.motion.utilities.Parser;

import java.util.ArrayList;
import java.util.List;

public class ChartsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    List<Chart> charts;
    List<String[]> buffer;

    TextView tv;

    ViewPager mViewPager;
    ChartAdapter mAdapter;

    RadioGroup indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        loadDB();
        loadUI();
    }

    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    private void loadUI() {
        mAdapter = new ChartAdapter(getSupportFragmentManager(), charts);
        mViewPager = (ViewPager) findViewById(R.id.charts);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);

        tv= (TextView)findViewById(R.id.title);
        tv.setText(charts.get(0).getName());

        indicator = (RadioGroup) findViewById(R.id.radioGroup);
        loadIndicator();
    }

    private void loadIndicator() {
            int dpAsPixels = getResources().getDimensionPixelSize(R.dimen.dot_margin_bt);
            for (int i = 0; i < charts.size(); i++) {
                RadioButton button = new RadioButton(this);
                button.setButtonDrawable(R.drawable.dot_selector_blue);
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.WRAP_CONTENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(dpAsPixels, 0, dpAsPixels, 0);
                button.setLayoutParams(params);
                indicator.addView(button);
            }

            ((RadioButton) indicator.getChildAt(0)).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_charts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_exit) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadDB() {
        buffer = Parser.read(getResources().openRawResource(R.raw.easton));
        charts = new ArrayList<>();
        charts.add(buildLineChart());
        charts.add(buildPieChartInfo());
        charts.add(buildBarChartInfo());
    }

    private Chart buildLineChart() {
        List<Entry> mEntries;
        List<String> mXLabels;
        int mCount = 0;

        mEntries = new ArrayList<>();
        mXLabels = new ArrayList<>();

        for (String[] i : buffer) {
            mEntries.add(new Entry(Parser.getFloat(i[17]), mCount));
            mXLabels.add(i[3].substring(0, 5));
            mCount++;
        }

        return new Chart(Chart.ChartType.LINE, getString(R.string.line_chart), mXLabels, mEntries);
    }

    private Chart buildPieChartInfo(){
        List<Entry> mEntries;
        List<String> mXLabels;
        int[] xyz;
        float mCount;
        float mEval;

        mEntries = new ArrayList<>();
        mXLabels = new ArrayList<>();

        mCount = buffer.size();
        xyz = new int[] {0,0,0};

        for (String[] tmpItem : buffer) {
            mEval = Parser.getFloat(tmpItem[16]);

            if (mEval == 0)
                xyz[0] += 1;
            else if (mEval < 0)
                xyz[1] += 1;
            else
                xyz[2] += 1;
        }

        mEntries.add(new Entry((xyz[0] / mCount), 0));
        mXLabels.add("Level");

        mEntries.add(new Entry((xyz[1] / mCount), 1));
        mXLabels.add("Down");

        mEntries.add(new Entry((xyz[2] / mCount), 2));
        mXLabels.add("Up");

        return new Chart(Chart.ChartType.PIE, getString(R.string.pie_chart), mXLabels, mEntries);
    }

    private Chart buildBarChartInfo(){
        List<BarEntry> mBarEntry;
        List<String> mXLabels;
        Chart chart;
        int mCount = 0;
        float temp;

        mBarEntry = new ArrayList<>();
        mXLabels = new ArrayList<>();

        for (String[] tmpItem : buffer) {
            temp = Parser.getFloat(tmpItem[16]);

            if (temp > 0 )
                mBarEntry.add(new BarEntry(1f, mCount));
            else if (temp < 0 )
                mBarEntry.add(new BarEntry(-1f, mCount));
            else
                mBarEntry.add(new BarEntry(temp, mCount));

            mXLabels.add(tmpItem[3].substring(0, 5));
            mCount++;
        }

        chart = new Chart(Chart.ChartType.BAR, getString(R.string.bar_chart), mXLabels, null);
        chart.setMBar(mBarEntry);

        return chart;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        tv.setText(charts.get(position).getName());
        ((RadioButton) indicator.getChildAt(position)).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
