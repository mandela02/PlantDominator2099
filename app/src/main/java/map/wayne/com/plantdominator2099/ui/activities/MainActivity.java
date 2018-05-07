package map.wayne.com.plantdominator2099.ui.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import map.wayne.com.plantdominator2099.BackgroundSoundService;
import map.wayne.com.plantdominator2099.R;
import map.wayne.com.plantdominator2099.ui.fragments.BuildFragment;
import map.wayne.com.plantdominator2099.ui.fragments.HistoryFragment;
import map.wayne.com.plantdominator2099.ui.fragments.MapFragment;

public class MainActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private View navHeader;
    private static final String TAG_MAP = "HUST map";
    private static final String TAG_BUILD = "Build your own route";
    private static final String TAG_HISTORY = "Your history";
    public static String CURRENT_TAG = TAG_MAP;
    private String activityTitles[] = {"HUST map", "Build your own route", "Your history",
        "Setting"};
    private boolean shouldLoadHomeFragOnBackPress = true;
    public static int navItemIndex = 0;
    private ImageView mImageUser;
    private TextView mTxtUserName;
    private TextView mTxtUserEmail;
    private Boolean state;
    private Intent svc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        svc = new Intent(this, BackgroundSoundService.class);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        //state = getIntent().getBooleanExtra("state_1", true);
        if(isMyServiceRunning(BackgroundSoundService.class)) state = true;
        else state = false;
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        getInformation();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_MAP;
            loadCurrentFragment();
        }
        setUpNavigationView();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_MAP;
                loadCurrentFragment();
                return;
            }
        }
        super.onBackPressed();
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void loadCurrentFragment() {
        selectNavMenu();
        setToolbarTitle();
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }
        Fragment fragment = getCurrentFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
            android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();
        drawer.closeDrawers();
        invalidateOptionsMenu();
    }

    private Fragment getCurrentFragment() {
        MapFragment mapFragment = new MapFragment();
        BuildFragment buildFragment = new BuildFragment();
        HistoryFragment historyFragment = new HistoryFragment();
        switch (navItemIndex) {
            case 0:
                return mapFragment;
            case 1:
                return buildFragment;
            case 2:
                return historyFragment;
            default:
                return mapFragment;
        }
    }

/*
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
    }*/

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void setUpNavigationView() {
        Menu menu = navigationView.getMenu();
        MenuItem m = menu.findItem(R.id.nav_setting);
        if (state) {
            m.setTitle("Turn off music");
            m.setIcon(R.drawable.ic_mute);
        } else {
            m.setTitle("Turn on music");
            m.setIcon(R.drawable.ic_speaker);
        }
        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_map:
                            navItemIndex = 0;
                            CURRENT_TAG = TAG_MAP;
                            break;
                        case R.id.nav_build:
                            navItemIndex = 1;
                            CURRENT_TAG = TAG_BUILD;
                            break;
                        case R.id.nav_histoty:
                            navItemIndex = 2;
                            CURRENT_TAG = TAG_HISTORY;
                            break;
                        case R.id.nav_setting:
/*
                            navItemIndex = 3;
                            CURRENT_TAG = TAG_SETTING;
*/
                            if (state) {
                                state = false;
                                menuItem.setIcon(R.drawable.ic_speaker);
                                menuItem.setTitle("Turn on music");
                                stopService(svc);
                            } else {
                                state = true;
                                menuItem.setIcon(R.drawable.ic_mute);
                                menuItem.setTitle("Turn off music");
                                startService(svc);
                            }
                            break;
                        case R.id.nav_exit:
                            finish();
                            break;
                        default:
                            navItemIndex = 0;
                    }
                    if (menuItem.isChecked()) {
                        menuItem.setChecked(false);
                    } else {
                        menuItem.setChecked(true);
                    }
                    menuItem.setChecked(true);
                    loadCurrentFragment();
                    return true;
                }
            });
        ActionBarDrawerToggle actionBarDrawerToggle =
            new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer,
                R.string.closeDrawer) {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }
            };
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public void getInformation() {
        Intent getIntent = getIntent();
        final String name = getIntent.getStringExtra("name_1");
        mImageUser = navHeader.findViewById(R.id.image_userimage);
        mTxtUserName = navHeader.findViewById(R.id.txt_username);
        mTxtUserEmail = navHeader.findViewById(R.id.txt_useremail);
        mTxtUserName.setText(name);
        mTxtUserEmail.setText(name + "@lex_os.io.mail.com");
        mImageUser.setImageResource(R.drawable.batman);
        navHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ProfileActivity.class);
                myIntent.putExtra("name_2", name);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }
}
