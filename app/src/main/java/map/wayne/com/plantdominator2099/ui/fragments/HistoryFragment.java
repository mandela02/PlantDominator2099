package map.wayne.com.plantdominator2099.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import map.wayne.com.plantdominator2099.R;
import map.wayne.com.plantdominator2099.ui.activities.SampleMap;

public class HistoryFragment extends Fragment implements View.OnClickListener {
    private Button mButton_1;
    private Button mButton_2;
    private View v;

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
}
