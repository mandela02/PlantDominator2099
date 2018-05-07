package map.wayne.com.plantdominator2099.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import map.wayne.com.plantdominator2099.R;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        String value = intent.getStringExtra("name_2"); //if it's a string you stored.
        TextView textName = findViewById(R.id.text_name);
        TextView textID = findViewById(R.id.text_id);
        textName.setText("Name: " + value);
        if (value.equals("batman")) textID.setText("Secret ID: Lao Công");
        if (value.equals("superman")) textID.setText("Secret ID: Cộng Tác Viên");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

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

}
