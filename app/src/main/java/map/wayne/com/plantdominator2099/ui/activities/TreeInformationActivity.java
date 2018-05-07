package map.wayne.com.plantdominator2099.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_information);
        initView();
        getData();
        initToolbar();
        setView();
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

}
