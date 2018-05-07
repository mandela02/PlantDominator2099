package map.wayne.com.plantdominator2099.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import map.wayne.com.plantdominator2099.BackgroundSoundService;
import map.wayne.com.plantdominator2099.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText mEmailView;
    private EditText mPasswordView;
    private Boolean state = true;
    private Intent svc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        Button mEmailSignInButton = findViewById(R.id.login);
        svc = new Intent(this, BackgroundSoundService.class);
        startService(svc);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                myIntent.putExtra("name_1", mEmailView.getText().toString());
                myIntent.putExtra("state_1", state);
                LoginActivity.this.startActivity(myIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mybutton) {
            if (state) {
                stopService(svc);
                state = false;
                item.setIcon(R.drawable.ic_mute);
            } else {
                startService(svc);
                state = true;
                item.setIcon(R.drawable.ic_speaker);
            }
            // do something here
        }
        return super.onOptionsItemSelected(item);
    }
}

