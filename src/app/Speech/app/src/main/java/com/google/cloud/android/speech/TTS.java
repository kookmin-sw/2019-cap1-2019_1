package com.google.cloud.android.speech;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TTS extends AppCompatActivity {

    private TextToSpeech tts; //Text To Speech
    private String sentence;
    TextView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);

        progress = (TextView) findViewById(R.id.progress);

        Intent intent = getIntent(); //데이터수신
        sentence = intent.getExtras().getString("sentence");
        progress.setText(sentence);
        Toast.makeText(this, sentence, Toast.LENGTH_SHORT).show();

        try{
            Thread.sleep(500);
        } catch(Exception e){
            e.printStackTrace();
        }

        //TTS
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status){
                if(status == TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(Locale.KOREA);
                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(TTS.this, "이 언어는 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        tts.setPitch(1.0f);
                        tts.setSpeechRate(1.0f);
                        speech();
                    }
                }
            }
        });
    }


    public void sleep(int time){
        try{
            Thread.sleep(time);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void speech(){
        boolean speakingEnd;
        tts.speak(sentence, TextToSpeech.QUEUE_FLUSH, null, null);
        do{
            speakingEnd = tts.isSpeaking();
        } while(speakingEnd);

        finish();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //TTS 삭제
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    protected void onStop(){
        setResult(1);
        super.onStop();
    }
}