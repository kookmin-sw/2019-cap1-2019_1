package com.google.cloud.android.speech;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SetDestination extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_destination);

        textView = (TextView) findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.locationButton);
        button.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               startLocationService();
           }
        });

    }

    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "목적지 설정 화면이다.", Toast.LENGTH_SHORT).show();
    }

    public void startLocationService(){
        long minTime = 10000;
        float minDistance= 0 ;

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Double latitude = location.getLatitude();
                        Double longitude = location.getLongitude();

                        textView.setText("내 위치 : " + latitude + ", " + longitude);
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
                });
    }

    public void returnButton(View v){
        finish();
    }
}
