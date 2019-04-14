package com.google.cloud.android.speech;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SetDestination extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_destination);
    }

    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "목적지 설정 화면이다.", Toast.LENGTH_SHORT).show();
    }

    public void returnButton(View v){
        finish();
    }
}
