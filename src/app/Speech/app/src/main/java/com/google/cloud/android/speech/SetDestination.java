package com.google.cloud.android.speech;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Node;

public class SetDestination extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker gps;
    private ArrayList<Marker> mMarkerList;

    private Button btnMyLocation;
    private EditText editText;

    private EnterListener enterListener;
    private String s = "null";

    private static final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 11;

    //위치
    LatLng initLatLng = new LatLng(37.6108694, 126.99728890000006);
    public Double latitude = initLatLng.latitude;
    public Double longitude = initLatLng.longitude;

    //
    public String netResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_destination);

        btnMyLocation = (Button)findViewById(R.id.button);
        editText = (EditText)findViewById(R.id.editText);
        enterListener = new EnterListener();
        editText.setOnEditorActionListener(enterListener);

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
        startLocationService();
    }

    //////////////////////////////////////
    public void startLocationService(){
        long minTime = 10000;
        float minDistance = 0;
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }
                    @Override
                    public void onProviderEnabled(String provider) {
                    }
                    @Override
                    public void onProviderDisabled(String provider) {
                    }
                }
        );
    }

    ///////////////////////////////

    @Override
    protected void onStart(){
        super.onStart();

        sendPost("http://13.125.251.226:5000/insert", latitude, longitude);

        //통신
        Toast.makeText(SetDestination.this,
                "통신성공",
                Toast.LENGTH_LONG).show();

        //finish();
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
        gps = new GPSTracker(SetDestination.this);
        mMarkerList = new ArrayList<Marker>();


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
                for(Marker marker : mMarkerList) {
                    if(Math.abs(marker.getPosition().latitude - latLng.latitude) < 0.05 && Math.abs(marker.getPosition().longitude - latLng.longitude) < 0.05) {
                        Toast.makeText(SetDestination.this, "got clicked", Toast.LENGTH_SHORT).show(); //do some stuff
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
                Toast.makeText(SetDestination.this, coordText, Toast.LENGTH_LONG).show();
                return false;
            }
        });

        btnMyLocation.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gps.canGetLocation()) {
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    Toast.makeText(SetDestination.this,
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
            editText.setText("");
            return false;
        }
    }


    //통신 Code
    public void sendPost(final String url_, final double latitude, final  double longitude) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL(url_);
                    Log.e("hey","hey");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

//                    JSONObject jsonParam = new JSONObject();
//                    jsonParam.put("node_id",'1');
//                    jsonParam.put("node_type","CORNER");
//                    jsonParam.put("node_name","HOME");
//                    jsonParam.put("node_longitude",15);
//                    jsonParam.put("node_latitute",10);
//                    jsonParam.put("node_neighbors","1/2/3");

                    //노드 json넣기
                    Node node1=new Node(1,latitude, longitude, "CORNER", "1/2/3", "BUILDING");
                    //Node node2=new Node(2,1.5, 2.1, "GREENLIGHT", "1/2/3", "HOME");
                    //Node node3=new Node(3,1.3, 2.3, "CORNER", "1/2/3", "TOILET");

                    ArrayList<Node> nodeList=new ArrayList<>();
                    nodeList.add(node1);
                    //nodeList.add(node2);
                    //nodeList.add(node3);

                    JSONObject sendJsonObject=new JSONObject();

                    JSONArray jArray=new JSONArray();
                    for(int i=0; i<nodeList.size(); ++i)
                    {
                        JSONObject nodeJsonObject=new JSONObject();
                        //nodeJsonObject.put("node_id",nodeList.get(i).getNode_id());
                        nodeJsonObject.put("pos_x",nodeList.get(i).getPos_x());
                        nodeJsonObject.put("pos_y",nodeList.get(i).getPos_y());
                        //nodeJsonObject.put("node_type",nodeList.get(i).getNode_type());
                        //nodeJsonObject.put("node_neighbors",nodeList.get(i).getNode_neighbors());
                        //nodeJsonObject.put("node_name",nodeList.get(i).getNode_name());
                        jArray.put(nodeJsonObject);
                    }

                    //User 휴대폰 고유넘버
                    JSONObject userJsonObject=new JSONObject();
                    //userJsonObject.put("user_phonenumber","0001");

                    //sendJsonObject.put("User",userJsonObject);
                    sendJsonObject.put("Node",jArray);

                    ////

                    Log.i("JSON", sendJsonObject.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));

                    //여기서 보내는듯
                    os.writeBytes(sendJsonObject.toString());

                    os.flush();
                    os.close();

                    //여기서부터 Response Code
                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    InputStream is = null;

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        is = conn.getInputStream();// is is inputstream
                    } else {
                        is = conn.getErrorStream();
                    }


                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(
                                is, "UTF-8"), 8);
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        is.close();
                        String response = sb.toString();
                        //HERE YOU HAVE THE VALUE FROM THE SERVER
                        Log.d("Your Data", response);
                    } catch (Exception e) {
                        Log.e("Buffer Error", "Error converting result " + e.toString());
                    }


                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
    //Node code
    class Node
    {
        private int node_id;
        private String node_type;
        private String node_name;
        private double pos_x;
        private double pos_y;
        private String node_neighbors;

        public Node(int node_id_,double pos_x_, double pos_y_, String node_type_, String node_neighbors_, String node_name_)
        {
            node_id=node_id_;
            node_type=node_type_;
            node_name=node_name_;
            pos_x=pos_x_;
            pos_y=pos_y_;
            node_neighbors=node_neighbors_;
        }

        public int getNode_id() {
            return node_id;
        }

        public String getNode_type() {
            return node_type;
        }

        public String getNode_name() {
            return node_name;
        }

        public double getPos_x() {
            return pos_x;
        }

        public double getPos_y() {
            return pos_y;
        }

        public String getNode_neighbors() {
            return node_neighbors;
        }
    }

}