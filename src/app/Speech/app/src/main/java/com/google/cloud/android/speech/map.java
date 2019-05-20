package com.google.cloud.android.speech;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.location.Address;

public class map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker gps;
    private ArrayList<Marker> mMarkerList;

    private Button btnMyLocation;
    private EditText editText;

    private EnterListener enterListener;
    private String s = "null";

    private static final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 11;

    //위치
    LatLng initLatLng = new LatLng(0.0, 0.0);
    public Double latitude = initLatLng.latitude;
    public Double longitude = initLatLng.longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_destination);

        btnMyLocation = (Button)findViewById(R.id.button);
        //editText = (EditText)findViewById(R.id.editText);
        enterListener = new EnterListener();
        //editText.setOnEditorActionListener(enterListener);

        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Check and Set GPS & Network Permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_COURSE_LOCATION);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    protected void onStart() {
        super.onStart();
        getLocation();
        Toast.makeText(this, "lati : " + latitude + "longi : " + longitude, Toast.LENGTH_SHORT).show();
    }

    //////////////////////////////////////
    public void getLocation(){
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    ///////////////////////////////

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        gps = new GPSTracker( map.this);
        mMarkerList = new ArrayList<Marker>();


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                String address = getAddress(latLng);
                latitude = latLng.latitude;
                longitude = latLng.longitude;

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(address);
                markerOptions.snippet("latitude: " + latitude.toString() + "\nlongitude: " + longitude.toString());

                Marker marker = mMap.addMarker(markerOptions);
                mMarkerList.add(marker);
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                for(Marker marker : mMarkerList) {
                    if(Math.abs(marker.getPosition().latitude - latLng.latitude) < 0.05 && Math.abs(marker.getPosition().longitude - latLng.longitude) < 0.05) {
                        Toast.makeText(map.this, "got clicked", Toast.LENGTH_SHORT).show(); //do some stuff
                        //mMarkerList.remove(marker);
                        setLocInfo(marker);
                        break;
                    }
                }
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String coordText = "latitude: " + marker.getPosition().latitude + "\nlongitude: " + marker.getPosition().longitude;
                Toast.makeText(map.this, coordText, Toast.LENGTH_LONG).show();
                return false;
            }
        });

        btnMyLocation.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    Toast.makeText(map.this,
                            "latitude :" + String.valueOf(latitude) + "\nlongitude :" + String.valueOf(longitude),
                            Toast.LENGTH_LONG).show();
                } else {
                    gps.showSettingsAlert();
                }
            }
        });

        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }

        UiSettings mapSettings;
        mapSettings = mMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
        mapSettings.setTiltGesturesEnabled(true);
        mapSettings.setRotateGesturesEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng first = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(first).title("Marker in here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(first));
    }

    private static final int LOCATION_REQUEST_CODE = 101;
    private String TAG = "MapDemo";

    protected void requestPermission(String permissionType, int requestCode) {
        int permission = ContextCompat.checkSelfPermission(this, permissionType);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permissionType}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Unable to show location - permission required", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private String getAddress(LatLng latLng) {
        String retAddr = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            Address obj = addresses.get(0);
            retAddr += obj.getFeatureName() + ": ";
            retAddr += obj.getAddressLine(0) + " (";
            retAddr += obj.getPostalCode() + ")";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retAddr;
    }

    public void setLocInfo(Marker marker) {
        String snippet = s;
        Marker setMarker;

        for (Marker mMarker : mMarkerList) {
            if (mMarker == marker) {
                setMarker = marker;
                setMarker.setSnippet(snippet);
                mMarkerList.remove(marker);
                mMarkerList.add(setMarker);
                break;
            }
        }
    }

    class EnterListener implements EditText.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            s = v.getText().toString();
            //editText.setText("");
            return false;
        }
    }

}
