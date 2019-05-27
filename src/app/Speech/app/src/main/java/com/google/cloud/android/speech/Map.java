package com.google.cloud.android.speech;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker gps;
    private ArrayList<Marker> mMarkerList;
    private ArrayList<Node> NodeList;
    private Network network;

    private Button btnMyLocation;
    private Button btnSetUpdate;
    private Button btnGoMain;

    private static final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 11;

    public Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        btnMyLocation = (Button)findViewById(R.id.button);
        btnGoMain = (Button)findViewById(R.id.buttonGoSpeech);

        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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
        gps = new GPSTracker(Map.this);
        mMarkerList = new ArrayList<Marker>();

        // Get Pracelable ArrayList<Node> from MainActivity.class
        Intent intent = getIntent();
        NodeList = new ArrayList<Node>();
        NodeList = intent.getParcelableArrayListExtra("node_list");

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                String address = getAddress(latLng);
                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;

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
                for(final Marker marker : mMarkerList) {
                    Double touchPos = 1 / Math.pow(mMap.getCameraPosition().zoom, 3);
                    if(Math.abs(marker.getPosition().latitude - latLng.latitude) < touchPos && Math.abs(marker.getPosition().longitude - latLng.longitude) < touchPos) {
                        // set Dialog message
                        dialogBuilder.setTitle("알림")
                                .setMessage("위치 정보를 수정 또는 삭제하시겠습니까?");
                        dialogBuilder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            /*** Remove a Marker ***/
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mMarkerList.remove(marker);
                                marker.remove();
                                Toast.makeText(Map.this, "A marker is removed.", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("수정", new DialogInterface.OnClickListener() {
                            /*** Modify a Marker Information ***/
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // get activity_set_map_info.xml view
                                LayoutInflater promptli = LayoutInflater.from(Map.this);
                                View promptsView = promptli.inflate(R.layout.activity_set_map_info, null);

                                // set activity_set_map_info.xml to AlertDialog builder
                                AlertDialog.Builder promptDialogBuilder = new AlertDialog.Builder( Map.this);
                                promptDialogBuilder.setView(promptsView);
                                final EditText userInputLocName = (EditText)promptsView.findViewById(R.id.editTextLocationName);
                                final RadioGroup userInputDoorCheck = (RadioGroup)promptsView.findViewById(R.id.radioGroupDoorCheck);
                                final EditText userInputFloor = (EditText)promptsView.findViewById(R.id.editTextFloor);
                                final RadioGroup userInputNodeType = (RadioGroup)promptsView.findViewById(R.id.radioGroupNodeType);

                                // set Dialog message
                                promptDialogBuilder.setCancelable(false)
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            /*** Set Modifying Prompt Dialog ***/
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Set Info
                                                String setLocName = userInputLocName.getText().toString();
                                                Integer setDoorCheckId = userInputDoorCheck.getCheckedRadioButtonId();
                                                String setFloor = userInputFloor.getText().toString();
                                                Integer setNodeTypeId = userInputNodeType.getCheckedRadioButtonId();

                                                String setDoorCheck = "";
                                                switch (setDoorCheckId) {
                                                    case R.id.radioButtonIndoor:
                                                        setDoorCheck = "실내";
                                                        break;
                                                    case R.id.radioButtonOutdoor:
                                                        setDoorCheck = "실외";
                                                        break;
                                                }

                                                String setNodeType = "";
                                                switch (setNodeTypeId) {
                                                    case R.id.radioButtonNormalNode:
                                                        setNodeType = "일반";
                                                        break;
                                                    case R.id.radioButtonForkNode:
                                                        setNodeType = "갈림길";
                                                        break;
                                                }

                                                setLocInfo(marker, setLocName, setDoorCheck, setFloor, setNodeType);
                                                Toast.makeText(Map.this, "A marker is updated.", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                            /*** Cancel Modifying ***/
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                promptDialogBuilder.show();
                            }
                        }).setNeutralButton("취소", null);
                        dialogBuilder.show();
                        break;
                    }
                }
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String coordText = "latitude: " + marker.getPosition().latitude + "\nlongitude: " + marker.getPosition().longitude;
                Toast.makeText(Map.this, coordText, Toast.LENGTH_LONG).show();
                return false;
            }
        });

        btnMyLocation.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    Toast.makeText(Map.this,
                            "latitude :" + String.valueOf(latitude) + "\nlongitude :" + String.valueOf(longitude),
                            Toast.LENGTH_LONG).show();
                } else {
                    gps.showSettingsAlert();
                }
            }
        });

        btnGoMain.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set NodeList from mMarkerList
                setNodeListFromMarkerList();

                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("NodeList", NodeList);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }

        UiSettings mapSettings;
        mapSettings = mMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
        mapSettings.setTiltGesturesEnabled(true);
        mapSettings.setRotateGesturesEnabled(true);

        //LatLng HOUSE = new LatLng(37.5756132, 126.9237916);
        //Marker house = mMap.addMarker(new MarkerOptions().position(HOUSE).title("House").snippet("My House~ :)"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HOUSE, 18));
        LatLng first = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(first, 15));
        setMarkerListFromNodeList();
        //setNodeListFromMarkerList();
    }

    /* Request Permission */
    private static final int LOCATION_REQUEST_CODE = 101;

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

    protected void onStart() {
        super.onStart();
        getCurrentLocation();
        Toast.makeText(this, "latitude : " + latitude + "\nlongitude : " + longitude, Toast.LENGTH_SHORT).show();
    }

    public void getCurrentLocation() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
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

    public void setLocInfo(Marker marker, String locName, String doorCheck, String floor, String nodeType) {
        Marker setMarker;

        String title = locName, snippet = "";
        if (doorCheck == "실내") {
            title += " / " + floor;
            snippet = doorCheck + " / ";
        }
        snippet += nodeType;

        for (Marker mMarker : mMarkerList) {
            if (mMarker == marker) {
                setMarker = marker;
                setMarker.setTitle(title);
                setMarker.setSnippet(snippet);
                mMarkerList.remove(marker);
                mMarkerList.add(setMarker);
                break;
            }
        }
    }

    public ArrayList<Node> getNodeList() {
        return NodeList;
    }

    public void setNodeList(ArrayList<Node> NodeList) {
        this.NodeList = NodeList;
    }

    private void setMarkerListFromNodeList() {
        MarkerOptions markerOptions = new MarkerOptions();

        if (NodeList != null) {
            Iterator<Node> nodeIter = NodeList.iterator();
            while (nodeIter.hasNext()) {
                Node node = nodeIter.next();
                String nodeName = node.getNode_name();
                LatLng latLng = new LatLng(node.getPos_x(), node.getPos_y());
                String indoor = node.getIndoor();
                String floor = node.getFloor();
                String nodeType = node.getNode_type();

                String title = nodeName, snippet = "";
                if (indoor == "실내") {
                    title += " / " + floor;
                    snippet = indoor + " / ";
                }
                snippet += nodeType;

                markerOptions.position(latLng);
                markerOptions.title(title);
                markerOptions.snippet(snippet);

                Marker marker = mMap.addMarker(markerOptions);
                mMarkerList.add(marker);
            }
        }
    }

    private void setNodeListFromMarkerList() {
        Integer idx = 0;
        for (Marker marker : mMarkerList) {
            if (NodeList == null) {
                NodeList = new ArrayList<Node>();
            }
            if (idx >= NodeList.size()) {
                // Get marker data
                String[] title = marker.getTitle().split(" / ");
                String nodeName = title[0], nodeFloor = "";
                if (title.length > 1) {
                    nodeFloor = title[1];
                }
                String[] snippet = marker.getSnippet().split(" / ");
                String nodeIndoor = "", nodeType = "";
                if (snippet.length == 1) {
                    nodeType = snippet[0];
                } else if (snippet.length > 1) {
                    nodeIndoor = snippet[0];
                    nodeType = snippet[1];
                }

                // Set node data
                Node node = new Node(idx, nodeName, marker.getPosition().latitude, marker.getPosition().longitude, nodeIndoor, nodeFloor, nodeType);
                // Append a node to NodeList
                NodeList.add(node);
            }
            idx += 1;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}