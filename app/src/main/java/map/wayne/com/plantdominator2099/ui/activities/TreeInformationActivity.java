package map.wayne.com.plantdominator2099.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import map.wayne.com.plantdominator2099.R;
import map.wayne.com.plantdominator2099.data.model.TreeData;

public class TreeInformationActivity extends AppCompatActivity {
    public static Intent getInstance(Context context, TreeData result) {
        Intent intent = new Intent(context, TreeInformationActivity.class);
        intent.putExtra("EXTRA_NAME", result);
        return intent;
    }

    private TextView mTextDes;
    private TextView mTextStatus;
    private ImageView mImageAva;
    private TreeData mTreeData;
    private ImageView mBtnEdit;
    private LineChart chart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_information);
        initView();
        getData();
        initToolbar();
        setView();
        chart = (LineChart) findViewById(R.id.chart_line);
        setData();
        Legend l = chart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        chart.setDescription("ml");
        chart.animateXY(2000, 2000);


    }

    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(mTreeData.getTreeName());

    }

    public void getData() {
        Intent intent = getIntent();
        mTreeData = intent.getParcelableExtra("EXTRA_NAME");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initView() {
        mTextDes = (TextView) findViewById(R.id.text_description);
        mTextStatus = (TextView) findViewById(R.id.text_status);
        mImageAva = (ImageView) findViewById(R.id.image_tree);
        mBtnEdit = findViewById(R.id.btn_edit_infomation);
        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TreeInformationActivity.this, EditTreeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setView() {
        mTextDes.setText("description: " + mTreeData.getDes());
        mImageAva.setImageResource(R.drawable.tree);
        mImageAva.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if(mTreeData.getStatus()==1) mTextStatus.setText("Status: All right");
        if(mTreeData.getStatus()==2) mTextStatus.setText("Status: Almost dead");
    }

    private ArrayList<Entry> setYAxisValues(){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(200, 0));
        yVals.add(new Entry(314, 1));
        yVals.add(new Entry(186, 2));
        yVals.add(new Entry(220, 3));
        yVals.add(new Entry(180.9f, 4));

        return yVals;
    }
    private ArrayList<String> setXAxisValues(){
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Jan 1st");
        xVals.add("Jan 2nd");
        xVals.add("Jan 3rd");
        xVals.add("Jan 4th");
        xVals.add("Jan 5th");

        return xVals;
    }

    private void setData() {
        ArrayList<String> xVals = setXAxisValues();

        ArrayList<Entry> yVals = setYAxisValues();

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "Amount of Water per day");
        set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        chart.setData(data);

    }

}
