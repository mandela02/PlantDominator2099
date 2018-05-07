package map.wayne.com.plantdominator2099.ui.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import map.wayne.com.plantdominator2099.R;
import map.wayne.com.plantdominator2099.data.database.TreeDataSource;
import map.wayne.com.plantdominator2099.data.model.TreeData;

public class SampleMap extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private List<TreeData> mTreeData;
    private TreeDataSource mDatabase;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mTreeData = new ArrayList<>();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("History Map");

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng bachkhoa = new LatLng(21.004911, 105.844158);
        setDb();
        setupMap();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bachkhoa));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
    }

    public void setDb() {
        mDatabase = new TreeDataSource(getApplicationContext());
        mTreeData = mDatabase.getAllTree();
    }

    public void setupMap() {
        for (i = 0; i < mTreeData.size(); i++) {
            TreeData tree = mTreeData.get(i);
            LatLng latLng = new LatLng(tree.getLat(), tree.getLong());
            MarkerOptions options = new MarkerOptions();
            options.position(latLng)
                .title(tree.getTreeName())
                .snippet(tree.getDes());
            MarkerDrawer.DrawMarker(tree, options);
            mMap.addMarker(options);
        }
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
