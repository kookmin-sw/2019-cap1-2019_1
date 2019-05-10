package com.google.cloud.android.speech;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class SetDestination extends AppCompatActivity {

    Network network;

    private String currentPosition;
    private String destinationPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_destination);

        Intent intent = getIntent();
        currentPosition = intent.getExtras().getString("currentPosition");
        destinationPosition = intent.getExtras().getString("destinationPosition");

        network = new Network();

        Toast.makeText(this, "curPos : " + currentPosition + "\n destPos : " + destinationPosition, Toast.LENGTH_SHORT).show();
        //network.sendPost();  //목적지 설정하는거 형식
        //경로 받아서 여기서 이제 TTS 해주면 될 듯.
    }
}