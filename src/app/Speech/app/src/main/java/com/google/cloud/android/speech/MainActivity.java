/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.android.speech;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

//layout.setVisibility(View.VISIBLE); //View.INVISIBLE, View.GONE
public class MainActivity extends AppCompatActivity implements MessageDialogFragment.Listener {

    private static final String FRAGMENT_MESSAGE_DIALOG = "message_dialog";

    private static
    final String STATE_RESULTS = "results";

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1;

    private SpeechService mSpeechService;
    private VoiceRecorder mVoiceRecorder;
    private final VoiceRecorder.Callback mVoiceCallback = new VoiceRecorder.Callback() {

        //voiceRecorder Overriding
        @Override
        public void onVoiceStart() {
            showStatus(true);
            if (mSpeechService != null) {
                mSpeechService.startRecognizing(mVoiceRecorder.getSampleRate());
            }
        }

        @Override
        public void onVoice(byte[] data, int size) {
            if (mSpeechService != null) {
                mSpeechService.recognize(data, size);
            }
        }

        @Override
        public void onVoiceEnd() {
            showStatus(false);
            if (mSpeechService != null) {
                mSpeechService.finishRecognizing();
            }
        }
    };

    // Resource caches
    private int mColorHearing;
    private int mColorNotHearing;

    // View references
    private TextView progress;
    private TextView mStatus;
    private TextView mText;
    private ResultAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private String guideCurrentPosition;
    private String guideDestinationPosition;

    //
    public int stage;
    Bluetooth bluetooth;
    Network network;
    ArrayList<Node> node_list;
    Context context;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            mSpeechService = SpeechService.from(binder);
            mSpeechService.addListener(mSpeechServiceListener);
            mStatus.setVisibility(View.VISIBLE);
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mSpeechService = null;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stage = 0;
        network = new Network();

        //STT
        final Resources resources = getResources();
        final Resources.Theme theme = getTheme();
        mColorHearing = ResourcesCompat.getColor(resources, R.color.status_hearing, theme);
        mColorNotHearing = ResourcesCompat.getColor(resources, R.color.status_not_hearing, theme);

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mStatus = (TextView) findViewById(R.id.status);
        mText = (TextView) findViewById(R.id.text);
        progress = (TextView) findViewById(R.id.progress);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<String> results = savedInstanceState == null ? null :
                savedInstanceState.getStringArrayList(STATE_RESULTS);
        mAdapter = new ResultAdapter(results);
        mRecyclerView.setAdapter(mAdapter);

        guideCurrentPosition = "";
        guideDestinationPosition = "";

        //UI();
        //blue();
        bluetooth = new Bluetooth();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Prepare Cloud Speech API
        bindService(new Intent(this, SpeechService.class), mServiceConnection, BIND_AUTO_CREATE);

        //음성인식 사용해도 되는지 확인창
        // Start listening to voices
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            startVoiceRecorder();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)) {
            showPermissionMessageDialog();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }

    }

    @Override
    protected void onStop() {
        // Stop listening to voice
        stopVoiceRecorder();

        // Stop Cloud Speech API
        mSpeechService.removeListener(mSpeechServiceListener);
        unbindService(mServiceConnection);
        mSpeechService = null;

        super.onStop();
    }

    //종료 되기 전에 데이터 저장
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAdapter != null) {
            outState.putStringArrayList(STATE_RESULTS, mAdapter.getResults());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (permissions.length == 1 && grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startVoiceRecorder();
            } else {
                showPermissionMessageDialog();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_file:
                mSpeechService.recognizeInputStream(getResources().openRawResource(R.raw.audio));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startVoiceRecorder() {
        if (mVoiceRecorder != null) {
            mVoiceRecorder.stop();
        }
        mVoiceRecorder = new VoiceRecorder(mVoiceCallback);
        mVoiceRecorder.start();
    }

    private void stopVoiceRecorder() {
        if (mVoiceRecorder != null) {
            mVoiceRecorder.stop();
            mVoiceRecorder = null;
        }
    }

    private void showPermissionMessageDialog() {
        MessageDialogFragment
                .newInstance(getString(R.string.permission_message))
                .show(getSupportFragmentManager(), FRAGMENT_MESSAGE_DIALOG);
    }

    private void showStatus(final boolean hearingVoice) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStatus.setTextColor(hearingVoice ? mColorHearing : mColorNotHearing);
            }
        });
    }

    @Override
    public void onMessageDialogDismissed() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                REQUEST_RECORD_AUDIO_PERMISSION);
    }

    /////////////////////////////////////////////////////////////////////////////
    ////////////////여기서 스트링 주고 받자 ///////////////////////////////////////
    ////////////////↓↓↓↓↓↓↓↓↓↓↓////////////////////////////////////////
    private final SpeechService.Listener mSpeechServiceListener =
            new SpeechService.Listener() {
                @Override
                public void onSpeechRecognized(final String text, final boolean isFinal) {
                    if (isFinal) {
                        mVoiceRecorder.dismiss();
                    }
                    if (mText != null && !TextUtils.isEmpty(text)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isFinal) {
                                    mText.setText(null);
                                    mAdapter.addResult(text);
                                    //s = text;
                                    recognizeSpeaking(text); // SST 기능 활성화
                                    mRecyclerView.smoothScrollToPosition(0);
                                } else {
                                    mText.setText(text);
                                }
                            }
                        });
                    }
                }
            };

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_result, parent, false));
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    private static class ResultAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final ArrayList<String> mResults = new ArrayList<>();

        ResultAdapter(ArrayList<String> results) { // 생성자
            if (results != null) {
                mResults.addAll(results);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setText(mResults.get(position));
        }

        @Override
        public int getItemCount() {
            return mResults.size();
        }

        void addResult(String result) {
            mResults.add(0, result);
            notifyItemInserted(0);
        }

        public ArrayList<String> getResults() {
            return mResults;
        }

    }


    //Button
    public void onClickGuidePath(View v){
        guidePath(" ", " ");
    }

    public void onClickCancelPath(View v){
        //Intent intent = new Intent(this, CancelPath.class);
        //startActivity(intent);

        //전체 노드 확인 test
        String identification="User";
        String phone_origin_number="0xx0";
        node_list = network.requestGetAllNodes(identification,phone_origin_number);

        String temp = "";
        Iterator<Node> iter = node_list.iterator();
        while(iter.hasNext()){
            Node n = iter.next();
            temp += n.getNode_id() + " " + n.getPos_x() + " " + n.getPos_y() + "\n";
        }
        progress.setText(temp);
    }

    public void onClickEnrollNode(View v){
        Intent intent = new Intent(this, EnrollNode.class);
        startActivity(intent);
    }

    public void onClickModifiedNode(View v){
        Intent intent = new Intent(this, ModifiedNode.class);
        startActivity(intent);
    }

    public void onClickPrivacyLocation(View v){
        Intent intent = new Intent(this, PrivacyLocation.class);
        startActivity(intent);
    }

    public void onClickMapButton(View v){
        Intent intent = new Intent(this, map.class);
        startActivity(intent);
    }

    public void onClickTest(View v){
        stopVoiceRecorder();
        //Intent intent = new Intent(this, test.class);
        //startActivity(intent);
    }
    //Button

    public void textToSpeech(String sentence){
        stopVoiceRecorder();
        Intent intent = new Intent(this, TTS.class);

        intent.putExtra("sentence", sentence);

        startActivityForResult(intent, 0);
    }

    public void recognizeSpeaking(String sentence){
        if(stage==0){
            switch(sentence){
                case "설명 듣기" :
                    textToSpeech("사용하실수 있는 기능으로 길 안내, 위치 저장, 도우미 버전 이 있습니다.");
                    break;

                case "도우미 버전" :
                    textToSpeech("음성 안내 모드를 종료하겠습니다.");
                    stopVoiceRecorder();
                    break;

                case "위치 저장" :
                    stage = 10;
                    textToSpeech("저장하실 장소명을 말해주세요.");
                    break;

                case "길 안내" :
                    stage = 100;
                    textToSpeech("길안내 시스템을 시작하겠습니다. 현재 계신곳을 말해주세요.");
                    progress.setText("길안내 시스템");
                    break;
            }
        }
        else if(stage>=100){
            if(stage==100){
                //bluetooth.sendCommand(sentence);
                String s = "현재 계신곳이 \'" + sentence + "\' 맞으시다면 네 라고 말해주세요";
                textToSpeech(s);
                progress.setText("현재 계신 곳 : " + sentence);
                guideCurrentPosition = sentence;
                stage = 101;
            }
            else if(stage==101){
                if(sentence.contentEquals("네") ||
                        sentence.contentEquals("맞아요") ||
                        sentence.contentEquals("네 맞아요")){
                    textToSpeech("목적지를 말해주세요");

                    stage = 102;
                }
                else{
                    textToSpeech("현재 계신곳을 천천히 말해주세요.");
                    stage = 100;
                }
            }
            else if(stage==102){
                String s = "목적지가 \'" + sentence + "\'이 맞으시다면 네 라고 말해주세요";
                textToSpeech(s);
                progress.setText("목적지 : " + sentence);
                guideDestinationPosition = sentence;
                stage = 103;
            }
            else if(stage==103){
                if(sentence.contentEquals("네") ||
                        sentence.contentEquals("맞아요") ||
                        sentence.contentEquals("네 맞아요")){

                    textToSpeech("경로 탐색을 시작하겠습니다. 잠시만 기다려주세요.");
                    guidePath(guideCurrentPosition, guideDestinationPosition);
                    stage = 0;
                    progress.setText("...");
                }
                else{
                    textToSpeech("목적지를 천천히 말해주세요.");
                    stage = 102;
                }
            }
        }
        else if(stage>=10){

        }
    }

    //startVoiceRecorder()
    //stopVoiceRecorder()
    //textToSpeech("하고싶은말")
    //recognizeSpeaking()
    public void sleep(int time){
        try{
            Thread.sleep(time);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void UI(){
        textToSpeech("어떤 기능을 원하세요? 무슨 기능이 있는지 알고싶으면 설명듣기 라고 말해주세요. ");
    }

    public void guidePath(String curPos, String destPos){
        Intent intent = new Intent(this, SetDestination.class);
        int cnt = 0, curid = 0, destid = 0;
        String temp = "";
        Iterator<Node> iter = node_list.iterator();
        while(iter.hasNext()){
            Node n = iter.next();
            if(curPos.contentEquals(n.getNode_name())){
                cnt++;
                curid = n.getNode_id();
            }
            else if(destPos.contentEquals(n.getNode_name())){
                cnt++;
                destid = n.getNode_id();
            }
            if(cnt==2){
                break;
            }
        }

        intent.putExtra("currentPostion", curid);
        intent.putExtra("destinationPosition", destPos);

        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            startVoiceRecorder();
        }
    }

    public void onClickYong(View v){
        Toast.makeText(this, "눌럿ㄷ", Toast.LENGTH_SHORT).show();
        String ss = bluetooth.sendCommand("yong");
        progress.setText(ss);
    }

}
