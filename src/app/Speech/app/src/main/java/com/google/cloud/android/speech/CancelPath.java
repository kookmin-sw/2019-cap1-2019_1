package com.google.cloud.android.speech;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CancelPath extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_path);
    }

    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "경로 취소 화면", Toast.LENGTH_SHORT).show();
    }

    public void returnButton(View v){
        finish();
    }
}
