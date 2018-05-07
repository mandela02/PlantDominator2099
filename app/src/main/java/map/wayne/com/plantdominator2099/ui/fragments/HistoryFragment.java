package map.wayne.com.plantdominator2099.ui.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

import map.wayne.com.plantdominator2099.R;
import map.wayne.com.plantdominator2099.ui.activities.SampleMap;

public class HistoryFragment extends Fragment implements View.OnClickListener {
    private Button mButton_1;
    private Button mButton_2;
    private View v;
    private BarChart mChart;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_history, container, false);
        mButton_1 = v.findViewById(R.id.tv24);
        mButton_2 = v.findViewById(R.id.tv34);
        mButton_1.setOnClickListener(this);
        mButton_2.setOnClickListener(this);
        mChart = v.findViewById(R.id.chart);
        BarData data = new BarData(getXAxisValues(), getDataSet());
        mChart.setData(data);
        mChart.animateXY(2000, 2000);
        mChart.invalidate();
        return v;
    }

    @Override
    public void onClick(View v) {
        Intent myIntent = new Intent(getContext(), SampleMap.class);
        switch (v.getId()) {
            case R.id.tv24:
                startActivity(myIntent);
                break;
            case R.id.tv34:
                startActivity(myIntent);
                break;
        }
    }

    private ArrayList<IBarDataSet> getDataSet() {
        ArrayList<IBarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(9, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(10, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(11, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(4, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(3, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(7, 5); // Jun
        valueSet1.add(v1e6);        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Tree " +
            "Number");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN 1st");
        xAxis.add("JAN 2nd");
        xAxis.add("JAN 3rd");
        xAxis.add("JAN 4th");
        xAxis.add("JAN 5th");
        xAxis.add("JAN 6th");
        return xAxis;
    }
}
