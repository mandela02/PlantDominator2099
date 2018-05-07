package map.wayne.com.plantdominator2099.ui.fragments;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import map.wayne.com.plantdominator2099.R;
import map.wayne.com.plantdominator2099.data.database.TreeDataSource;
import map.wayne.com.plantdominator2099.data.model.TreeData;
import map.wayne.com.plantdominator2099.data.value.Const;
import map.wayne.com.plantdominator2099.ui.activities.DirectionsJSONParser;
import map.wayne.com.plantdominator2099.ui.activities.EditTreeActivity;
import map.wayne.com.plantdominator2099.ui.activities.MarkerDrawer;
import map.wayne.com.plantdominator2099.ui.activities.TreeInformationActivity;

import static android.content.Context.LOCATION_SERVICE;

public class BuildFragment extends Fragment implements OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener, View.OnClickListener,
    GoogleMap.OnMapClickListener, TextToSpeech.OnInitListener {
    private TextToSpeech TTS;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private MapView mapView;
    private GoogleMap mMap;
    private TreeData mResult;
    private List<TreeData> mTree;
    private List<TreeData> mTreeData;
    private List<LatLng> mMarkerPoints;
    private TreeDataSource mDatabase;
    private GoogleApiClient mGoogleApiClient;
    private Location mMyLocation;
    private LocationRequest mLocationRequest;
    private Polyline mLine;
    private int i;
    private Marker mWater_1;
    private Marker mWater_2;
    private Marker mWater_3;
    private Marker mWater_4;
    private Marker mWater_5;
    private View v;
    private ImageView mBtnLocation;
    private ImageView mBtnDefaultRoute;
    private ImageView mImageApprove;
    private ImageView mBtnStopDefaultRoute;
    private RelativeLayout mRelativeTree;
    private RelativeLayout mRelativeWater;
    private RelativeLayout mRelativeButton;
    private TextView mTextTreeName;
    private TextView mTextTreeDistance;
    private TextView mTextWater;
    private ImageView mBtnTreeEdit;
    private ImageView mBtnTreeInformation;
    private Boolean isStarted = false;
    private Boolean isStartWatering = false;
    private ProgressBar mProgressRed;
    private ProgressBar mProgressBlue;
    private Button mButtonIrrigation;

    public BuildFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
/*
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(),
            new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                    mMyLocation = location;
                    }
                }
            });
*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_map, container, false);
        TTS = new TextToSpeech(getContext(), this);
        AlertDialog.Builder builder =
            new AlertDialog.Builder(getActivity());
        builder.setTitle("Welcome to \"build your own route\"")
            .setMessage("Choose your tree, then click \"start\" to begin the irrigation protocol")
            .setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        TTS.stop();
                    }
                })
            .setIcon(android.R.drawable.ic_dialog_map)
            .show();
        mapView = v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        initView();
        return v;
    }

    public void initView() {
        mTree = new ArrayList<>();
        mTreeData = new ArrayList<>();
        mMarkerPoints = new ArrayList<>();
        mDatabase = new TreeDataSource(getActivity().getApplicationContext());
        addTree();
        mBtnLocation = v.findViewById(R.id.btn_location);
        mBtnLocation.setOnClickListener(this);
        mBtnDefaultRoute = v.findViewById(R.id.btn_setDefaultRoute);
        mBtnDefaultRoute.setOnClickListener(this);
        mBtnStopDefaultRoute = v.findViewById(R.id.btn_stopRoute);
        mBtnStopDefaultRoute.setOnClickListener(this);
        mRelativeTree = v.findViewById(R.id.relative_tree);
        mRelativeWater = v.findViewById(R.id.relative_water);
        mRelativeButton = v.findViewById(R.id.relative_button);
        mTextTreeName = v.findViewById(R.id.text_treeName);
        mTextWater = v.findViewById(R.id.text_water);
        mTextTreeDistance = v.findViewById(R.id.text_distance);
        mBtnTreeEdit = v.findViewById(R.id.btn_treeEdit);
        mBtnTreeEdit.setOnClickListener(this);
        mBtnTreeInformation = v.findViewById(R.id.btn_treeInfor);
        mBtnTreeInformation.setOnClickListener(this);
        mProgressRed = v.findViewById(R.id.progressRed);
        mProgressRed.setProgress(0);
        mProgressRed.getProgressDrawable()
            .setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        mProgressBlue = v.findViewById(R.id.progressBlue);
        mProgressBlue.setProgress(70);
        mProgressBlue.getProgressDrawable()
            .setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
        mButtonIrrigation = v.findViewById(R.id.btn_irrigation);
        mButtonIrrigation.setOnClickListener(this);
        mImageApprove = v.findViewById(R.id.image_approve);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng bachkhoa = new LatLng(21.004911, 105.844158);
        setDb();
        setupMap();
        googleMap.setOnMarkerClickListener(this);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(bachkhoa));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMyLocationEnabled(true);
        mMyLocation = getLastKnownLocation();
        //getLastLocationNewMethod();
        googleMap.setOnMapClickListener(this);
        setWaterSpot();
    }
    // TODO: Rename method, update argument and hook method into UI event

    public void addTree() {
        mTree.add(new TreeData(1, "Tree_1", 21.004517, 105.843447, 1, 1, "Bamboo"));
        mTree.add(new TreeData(2, "Tree_2", 21.003879, 105.844043, 1, 2, "Bonsai"));
        mTree.add(new TreeData(3, "Tree_3", 21.003883, 105.844131, 2, 2, "Bonsai"));
        mTree.add(new TreeData(4, "Tree_4", 21.004197, 105.844754, 1, 3, "Flower"));
        mTree.add(new TreeData(5, "Tree_5", 21.006535, 105.842971, 1, 4, "Grass"));
        mTree.add(new TreeData(6, "Tree_6", 21.006526, 105.843310, 2, 4, "Grass"));
        mTree.add(new TreeData(7, "Tree_7", 21.006071, 105.842985, 2, 4, "Grass"));
        mTree.add(new TreeData(8, "Tree_8", 21.006046, 105.843297, 1, 4, "Grass"));
        mTree.add(new TreeData(9, "Tree_9", 21.005200, 105.842931, 1, 5, "Old tree"));
        mTree.add(new TreeData(10, "Tree_10", 21.005205, 105.843269, 2, 5, "Old tree"));
        mTree.add(new TreeData(11, "Tree_11", 21.005196, 105.842281, 1, 5, "Old tree"));
        mTree.add(new TreeData(12, "Tree_12", 21.004590, 105.844487, 1, 6, "Pine"));
        mTree.add(new TreeData(13, "Tree_13", 21.005378, 105.844710, 2, 8, "Pine"));
        mTree.add(new TreeData(14, "Tree_14", 21.004799, 105.844343, 1, 9, "Tree"));
        mTree.add(new TreeData(15, "Tree_15", 21.004830, 105.843956, 2, 9, "Tree"));
        mTree.add(new TreeData(16, "Tree_16", 21.004947, 105.843386, 1, 9, "Tree"));
        mTree.add(new TreeData(17, "Tree_17", 21.004421, 105.843599, 1, 10, "Williow"));
    }

    public void setDb() {
        mDatabase = new TreeDataSource(getActivity().getApplicationContext());
        for (i = 0; i < mTree.size(); i++) {
            if (!mDatabase.isIndb(mTree.get(i).getId()))
                mDatabase.insertTree(mTree.get(i));
        }
        mTreeData = mDatabase.getAllTree();
        for (i = 0; i < mTreeData.size(); i++) {
            mTreeData.get(i).setChoosen(false);
        }
    }

    public void setupMap() {
        //getCurrLocation();
/*        mMap.addMarker(new MarkerOptions().position(new LatLng(mMyLocation.getLatitude(),
            mMyLocation.getLongitude())).title("MyLocation"));*/
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

    public void setWaterSpot() {
        mWater_1 = mMap.addMarker(new MarkerOptions().title("WaterSpot_1").position(new LatLng
            (21.004705,
                105.844675)).icon(BitmapDescriptorFactory.fromResource(R.drawable.water_spot)));
        mWater_2 = mMap.addMarker(
            new MarkerOptions().title("WaterSpot_2").position(new LatLng(21.004049, 105.844549))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.water_spot)));
        mWater_3 = mMap.addMarker(
            new MarkerOptions().title("WaterSpot_3").position(new LatLng(21.005756, 105.842630))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.water_spot)));
        mWater_4 = mMap.addMarker(
            new MarkerOptions().title("WaterSpot_4").position(new LatLng(21.006802, 105.843074))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.water_spot)));
        mWater_5 = mMap.addMarker(
            new MarkerOptions().title("WaterSpot_5").position(new LatLng(21.006203, 105.844561))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.water_spot)));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mRelativeTree.setVisibility(View.VISIBLE);
        mImageApprove.setVisibility(View.GONE);
        if (marker.getTitle().equals(mWater_1.getTitle()) ||
            marker.getTitle().equals(mWater_2.getTitle()) ||
            marker.getTitle().equals(mWater_3.getTitle()) ||
            marker.getTitle().equals(mWater_4.getTitle()) ||
            marker.getTitle().equals(mWater_5.getTitle())
            ) {
            mTextTreeName.setText("Water Spot");
            mBtnTreeEdit.setVisibility(View.GONE);
            mBtnTreeInformation.setVisibility(View.GONE);
            mRelativeWater.setVisibility(View.GONE);
            mTextWater.setVisibility(View.GONE);
            if (marker.getTitle().equals(mWater_1.getTitle()))
                mTextTreeDistance.setText("Distance: " + Integer
                    .toString((int) distanceOfTwoPoint(new LatLng(mMyLocation.getLatitude(),
                        mMyLocation
                            .getLongitude()), new LatLng(mWater_1.getPosition().latitude,
                        mWater_1.getPosition().longitude))) + " m");
            else if (marker.getTitle().equals(mWater_2.getTitle()))
                mTextTreeDistance.setText("Distance: " + Integer
                    .toString(
                        (int) distanceOfTwoPoint(new LatLng(mMyLocation.getLatitude(), mMyLocation
                            .getLongitude()), new LatLng(mWater_2.getPosition().latitude,
                            mWater_2.getPosition().longitude))) + " m");
            else if (marker.getTitle().equals(mWater_3.getTitle()))
                mTextTreeDistance.setText("Distance: " + Integer
                    .toString(
                        (int) distanceOfTwoPoint(new LatLng(mMyLocation.getLatitude(), mMyLocation
                            .getLongitude()), new LatLng(mWater_3.getPosition().latitude,
                            mWater_3.getPosition().longitude))) + " m");
            else if (marker.getTitle().equals(mWater_4.getTitle()))
                mTextTreeDistance.setText("Distance: " + Integer
                    .toString(
                        (int) distanceOfTwoPoint(new LatLng(mMyLocation.getLatitude(), mMyLocation
                            .getLongitude()), new LatLng(mWater_4.getPosition().latitude,
                            mWater_4.getPosition().longitude))) + " m");
            else if (marker.getTitle().equals(mWater_5.getTitle()))
                mTextTreeDistance.setText("Distance: " + Integer
                    .toString(
                        (int) distanceOfTwoPoint(new LatLng(mMyLocation.getLatitude(), mMyLocation
                            .getLongitude()), new LatLng(mWater_5.getPosition().latitude,
                            mWater_5.getPosition().longitude))) + " m");
        }
        for (int i = 0; i < mTreeData.size(); i++) {
            TreeData tree = mTreeData.get(i);
            if (marker.getTitle().equals(tree.getTreeName())) {
                mResult = tree;
                if (mResult.getStatus() == 1) {
                    mTextWater.setText("Fully Irrigated");
                    mRelativeWater.setVisibility(View.GONE);
                }
                if (mResult.getStatus() == 2) {
                    mRelativeWater.setVisibility(View.VISIBLE);
                    mTextWater.setText("Need: 200 ml");
                }
                mTextTreeName.setText(mResult.getTreeName());
                mProgressRed.setProgress(0);
                mBtnTreeEdit.setVisibility(View.VISIBLE);
                mBtnTreeInformation.setVisibility(View.VISIBLE);
                mTextWater.setVisibility(View.VISIBLE);
                float d = distanceOfTwoPoint(new LatLng(mMyLocation.getLatitude(), mMyLocation
                    .getLongitude
                        ()), new LatLng(mResult.getLat(), mResult.getLong()));
                mTextTreeDistance.setText("Distance: " + Integer.toString((int) d) + " m");
                if (!isStarted) {
                    mRelativeWater.setVisibility(View.GONE);
                    if (!tree.isChoosen()) {
                        MarkerDrawer.DrawMarkerChoosen(tree, marker);
                        tree.setChoosen(true);
                    } else {
                        MarkerDrawer.DrawMarkerMarker(tree, marker);
                        tree.setChoosen(false);
                    }
                }
            }
        }
        mBtnLocation.setVisibility(View.GONE);
        if (isStarted) mBtnStopDefaultRoute.setVisibility(View.GONE);
        else mBtnDefaultRoute.setVisibility(View.GONE);
        return false;
    }

/*
    public void setUpRelative() {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ABOVE, mRelativeTree.getId());
        mRelativeButton.setLayoutParams(lp);
    }
*/

    @Override
    public void onMapClick(LatLng latLng) {
        mRelativeTree.setVisibility(View.GONE);
        mBtnLocation.setVisibility(View.VISIBLE);
        if (isStarted) mBtnStopDefaultRoute.setVisibility(View.VISIBLE);
        else mBtnDefaultRoute.setVisibility(View.VISIBLE);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        request();
    }

    public void request() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
            Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getActivity().getApplicationContext(), "permission denied",
                        Toast.LENGTH_LONG)
                        .show();
                }
                return;
            }
            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    private LocationManager mLocationManager;

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getActivity().getApplicationContext().getSystemService
            (LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
            Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean isInBk(double latitude, double longitude) {
        if (latitude < 21.007335 ||
            latitude > 21.003451 ||
            longitude < 105.845575 ||
            longitude > 105.841380)
            return true;
        else return false;
    }

    public float distanceOfTwoPoint(LatLng p1, LatLng p2) {
        float[] results = new float[1];
        Location.distanceBetween(p1.latitude, p1.longitude,
            p2.latitude, p2.longitude,
            results);
        return results[0];
    }

    public void getDirection() {
        mMarkerPoints.add(new LatLng(mMyLocation.getLatitude(), mMyLocation.getLongitude()));
        mMarkerPoints.add(new LatLng(mWater_2.getPosition().latitude, mWater_2.getPosition()
            .longitude));
        for (i = 0; i < mTreeData.size(); i++) {
            TreeData tree = mTreeData.get(i);
            if (tree.isChoosen()) {
                LatLng latLng = new LatLng(tree.getLat(), tree.getLong());
                mMarkerPoints.add(latLng);
            }
        }
        float minDistance = 9999999;
        int minPoint;
        for (i = 2; i < mMarkerPoints.size() - 1; i++) {
            minPoint = i + 1;
            for (int j = i + 1; j < mMarkerPoints.size(); j++) {
                if (distanceOfTwoPoint(mMarkerPoints.get(i), mMarkerPoints.get(j)) <= minDistance) {
                    minPoint = j;
                    minDistance = distanceOfTwoPoint(mMarkerPoints.get(i), mMarkerPoints.get(j));
                }
            }
            minDistance = 9999999;
            if (minPoint != i + 1)
                Collections.swap(mMarkerPoints, i + 1, minPoint);
        }
        for (i = 0; i < mMarkerPoints.size() - 1; i++) {
            LatLng origin = (LatLng) mMarkerPoints.get(i);
            LatLng dest = (LatLng) mMarkerPoints.get(i + 1);
            drawlinebetweentwopoint(origin, dest);
        }
    }

    public void drawlinebetweentwopoint(LatLng origin, LatLng dest) {
        String url = getDirectionsUrl(origin, dest);
        BuildFragment.DownloadTask downloadTask = new BuildFragment.DownloadTask();
        downloadTask.execute(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_location:
                mMap.setMyLocationEnabled(true);
                LatLng latLng =
                    new LatLng(mMyLocation.getLatitude(), mMyLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
                if (!isInBk(latitude, longitude)) {
                    AlertDialog.Builder builder =
                        new AlertDialog.Builder(getActivity().getApplicationContext());
                    builder.setTitle("Sorry!")
                        .setMessage("You are not in the territory of HUST")
                        .setPositiveButton(android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    TTS.stop();
                                }
                            })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                    TTS.speak("You are not in the territory of hust", TextToSpeech.QUEUE_FLUSH,
                        null);
                }
                break;
            case R.id.btn_stopRoute:
                AlertDialog.Builder builder_2 =
                    new AlertDialog.Builder(getActivity());
                builder_2.setTitle("Are you sure?")
                    .setMessage("Watered Tree: 0" + "\n" + "Tree remain: " + String.valueOf
                        (mTreeData.size()) + "\n" + "Are you sure to cancel misson?")
                    .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                isStarted = !isStarted;
                                mMap.clear();
                                mTreeData.clear();
                                mMarkerPoints.clear();
                                setDb();
                                setupMap();
                                setWaterSpot();
                                mBtnDefaultRoute.setVisibility(View.VISIBLE);
                                mBtnStopDefaultRoute.setVisibility(View.GONE);
                                TTS.stop();

                            }
                        })
                    .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TTS.stop();
                            }
                        })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
                TTS.speak("You have " + String.valueOf
                        (mTreeData.size()) + " trees left. Are you sure to cancel " +
                        "misson?",
                    TextToSpeech.QUEUE_FLUSH, null);

                break;
            case R.id.btn_setDefaultRoute:
                AlertDialog.Builder builder_1 =
                    new AlertDialog.Builder(getActivity());
                builder_1.setTitle("Are you sure?")
                    .setMessage("Begin to water the chosen trees?")
                    .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                isStarted = !isStarted;
                                mMap.clear();
                                for (i = mTreeData.size() - 1; i >= 0; i--) {
                                    if (!mTreeData.get(i).isChoosen())
                                        mTreeData.remove(mTreeData.get(i));
                                }
                                mWater_2 = mMap.addMarker(
                                    new MarkerOptions().title("WaterSpot_2").position(new LatLng
                                        (21.004049, 105.844549))
                                        .icon(BitmapDescriptorFactory
                                            .fromResource(R.drawable.water_spot)));
                                setupMap();
                                getDirection();
                                Toast.makeText(getActivity().getApplicationContext(), "Done",
                                    Toast.LENGTH_SHORT)
                                    .show();
                                mBtnDefaultRoute.setVisibility(View.GONE);
                                mBtnStopDefaultRoute.setVisibility(View.VISIBLE);
                                TTS.stop();
                            }
                        })
                    .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TTS.stop();

                            }
                        })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
                TTS.speak("do you want to begin with your chosen trees?",
                    TextToSpeech.QUEUE_FLUSH, null);

                break;
            case R.id.btn_irrigation:
                final int progress = mProgressBlue.getProgress();
                ValueAnimator animator = ValueAnimator.ofInt(0, mProgressRed.getMax());
                animator.setDuration(5000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mProgressRed.setProgress((Integer) animation.getAnimatedValue());
                        ValueAnimator animator_2 = ValueAnimator.ofInt(progress, progress - 10);
                        animator_2.setDuration(2000);
                        animator_2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                mProgressBlue.setProgress((Integer) animation.getAnimatedValue());
                            }
                        });
                        animator_2.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                mProgressBlue.setProgress(progress - 10);
                            }
                        });
                        animator_2.start();
                    }
                });
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        // start your activity here
                        mRelativeWater.setVisibility(View.GONE);
                        mImageApprove.setVisibility(View.VISIBLE);
                        if (isStarted) {
                            if (mTreeData.size() - 1 != 0) {
                                mMap.clear();
                                for (i = mTreeData.size() - 1; i >= 0; i--) {
                                    if (mTreeData.get(i).getTreeName()
                                        .equals(mResult.getTreeName()))
                                        mTreeData.remove(mTreeData.get(i));
                                }
                                for (i = mMarkerPoints.size() - 1; i >= 0; i--) {
                                    if (mMarkerPoints.get(i).latitude == mResult.getLat())
                                        mMarkerPoints.remove(mMarkerPoints.get(i));
                                }
                                mWater_2 = mMap.addMarker(
                                    new MarkerOptions().title("WaterSpot_2").position(new LatLng
                                        (21.004049, 105.844549))
                                        .icon(BitmapDescriptorFactory
                                            .fromResource(R.drawable.water_spot)));
                                setupMap();
                                getDirection();
                            } else {
                                AlertDialog.Builder builder_4 =
                                    new AlertDialog.Builder(getActivity());
                                builder_4.setTitle("Hurray!")
                                    .setMessage("Congratulation, you finish your quest")
                                    .setPositiveButton(android.R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                isStarted = !isStarted;
                                                mMap.clear();
                                                mTreeData.clear();
                                                mMarkerPoints.clear();
                                                setDb();
                                                setupMap();
                                                setWaterSpot();
                                                mBtnDefaultRoute.setVisibility(View.VISIBLE);
                                                mBtnStopDefaultRoute.setVisibility(View.GONE);
                                                mRelativeTree.setVisibility(View.GONE);
                                                TTS.stop();
                                            }
                                        })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                                TTS.speak("Congratulation, you finish your quest",
                                    TextToSpeech.QUEUE_FLUSH, null);

                            }
                        }
                    }
                });
                animator.start();
                break;
            case R.id.btn_treeEdit:
                Intent intent_1 = new Intent(getContext(), EditTreeActivity.class);
                startActivity(intent_1);
                break;
            case R.id.btn_treeInfor:
                getActivity().startActivityForResult(TreeInformationActivity.getInstance
                    (getContext(), mResult), Const.RequestCode.REQUEST_CODE_INFOMATION);
                break;
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (TTS != null) {
            TTS.stop();
            TTS.shutdown();
        }
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (TTS != null) {
            TTS.stop();
            TTS.shutdown();
        }
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = TTS.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                TTS.speak(
                    "Welcome to \"build your own route\". Choose your tree, then click \"start\" to begin the irrigation protocol ",
                    TextToSpeech.QUEUE_FLUSH,
                    null);
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }
    //_________________

    private class ParserTask
        extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = new ArrayList<>();
            PolylineOptions lineOptions = new PolylineOptions();
            lineOptions.width(5);
            lineOptions.color(Color.BLUE);
            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {// Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);
                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
            }
            // Drawing polyline in the Google Map for the i-th route
            mLine = mMap.addPolyline(lineOptions);
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=walking";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("error", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {
        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {
            // For storing data from web service
            String data = "";
            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            BuildFragment.ParserTask parserTask = new ParserTask();
            //Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }
}
