package com.google.cloud.android.speech;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PrivacyLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_location);
    }

    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "이름 등록 실행", Toast.LENGTH_SHORT).show();
    }

    public void returnButton(View v){
        finish();
    }
}
