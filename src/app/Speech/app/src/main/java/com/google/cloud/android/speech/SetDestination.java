package com.google.cloud.android.speech;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedHashSet;


public class SetDestination extends AppCompatActivity {

    //Network network;
    //Bluetooth bluetooth;
    //LinkedHashSet<Integer> node_list;
    int cnt;

    private int currentPosition;
    private int destinationPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_destination);

        //Intent intent = getIntent();
        //currentPosition = intent.getExtras().getInt("currentPosition");
        //destinationPosition = intent.getExtras().getInt("destinationPosition");

        cnt = 0;
        //network = new Network();
        //bluetooth = new Bluetooth();

        //Toast.makeText(this, "curPos : " + currentPosition + "\n destPos : " + destinationPosition, Toast.LENGTH_SHORT).show();

        //String identification="User";
        //String phone_origin_number="0xx0";
        //node_list = network.requestPath(identification,phone_origin_number, currentPosition, destinationPosition);


        //network.sendPost();  //목적지 설정하는거 형식
        //경로 받아서 여기서 이제 TTS 해주면 될 듯.
    }


    public void setCommand(View v){
        cnt++;
        if(cnt==1){
            Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
            return ;
        }
        else if(cnt==2){
            Toast.makeText(this, "left", Toast.LENGTH_SHORT).show();
        }
        else if(cnt==3){
            Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();
        }
        else if(cnt==4){
            Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
        }
        else if(cnt==5){
            finish();
        }
    }
}