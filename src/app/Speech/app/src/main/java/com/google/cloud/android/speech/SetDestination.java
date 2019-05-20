package com.google.cloud.android.speech;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.LinkedHashSet;


public class SetDestination extends AppCompatActivity {

    Network network;
    Bluetooth bluetooth;
    LinkedHashSet<Integer> node_list;

    private int currentPosition;
    private int destinationPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_destination);

        Intent intent = getIntent();
        currentPosition = intent.getExtras().getInt("currentPosition");
        destinationPosition = intent.getExtras().getInt("destinationPosition");

        network = new Network();
        bluetooth = new Bluetooth();

        Toast.makeText(this, "curPos : " + currentPosition + "\n destPos : " + destinationPosition, Toast.LENGTH_SHORT).show();

        String identification="User";
        String phone_origin_number="0xx0";
        node_list = network.requestPath(identification,phone_origin_number, currentPosition, destinationPosition);



        //network.sendPost();  //목적지 설정하는거 형식
        //경로 받아서 여기서 이제 TTS 해주면 될 듯.
    }
}