package com.example.myapplication1;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {
    TextView textView;
    TextView StateView;
    TextView SetCountView;
    TimerTask timerTask;
    Timer timer = new Timer();

    SeekBar seekVolumn;

    int ExTime_1;
    int ExTime_2;
    int RestTime_1;
    int RestTime_2;
    int setTime;

    String SetCount;
    String ExCount;
    String RestCount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        Intent intent = getIntent();
        SetCount = intent.getStringExtra("세트");
        ExCount = intent.getStringExtra("운동시간");
        RestCount = intent.getStringExtra("휴식시간");

        StateView = findViewById(R.id.textView);
        textView = findViewById(R.id.textView2);
        SetCountView = findViewById(R.id.textView8);

        SetCountView.setText(SetCount);
        textView.setText(RestCount);

        seekVolumn = (SeekBar) findViewById(R.id.seekBar);
        final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int nMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int nCurrentVolumn = audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);

        seekVolumn.setMax(nMax);
        seekVolumn.setProgress(nCurrentVolumn);

        seekVolumn.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    public void clickHandler(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                startTimerTask();
                break;
            case R.id.btnReset:
                stopTimerTask();
                break;
        }
    }

    private void startTimerTask() {
        stopTimerTask();
        setTime = Integer.parseInt(SetCount);
        ExTime_1 = Integer.parseInt(ExCount.split(":")[0]);
        ExTime_2 = Integer.parseInt(ExCount.split(":")[1]);
        RestTime_1 = Integer.parseInt(RestCount.split(":")[0]);
        RestTime_2 = Integer.parseInt(RestCount.split(":")[1]);

        timerTask = new TimerTask() {
            boolean ExerciseState = false;
            boolean RestState = true;
            @Override
            public void run() {
                SetCountView.setText(Integer.toString(setTime));
                if (setTime == 0) {
                    MediaPlayer player = MediaPlayer.create(TimerActivity.this, R.raw.done);
                    player.start();
                    StateView.setText("운동 끝");
                    timerTask.cancel();
                }
                if (RestState == true && setTime != 0) {
                    if (RestTime_1 > 0 && RestTime_2 == 0) {
                        RestTime_1--;
                        RestTime_2 = 60;
                        StateView.setText("준비");
                        textView.setText(String.format("%02d", RestTime_1) + ":" + String.format("%02d", RestTime_2));
                    }
                    if (RestTime_2 >0 ) {
                        RestTime_2 -= 1;
                        StateView.setText("준비");
                        textView.setText(String.format("%02d", RestTime_1) + ":" + String.format("%02d", RestTime_2));
                    }
                    if (RestTime_1 == 0 && RestTime_2 == 0) {
                        textView.setText(String.format("%02d", RestTime_1) + ":" + String.format("%02d", RestTime_2));
                        MediaPlayer player2 = MediaPlayer.create(TimerActivity.this,R.raw.exercise);
                        player2.start();
                        ExerciseState = true;
                        RestState = false;
                        RestTime_1 = Integer.parseInt(RestCount.split(":")[0]);
                        RestTime_2 = Integer.parseInt(RestCount.split(":")[1]);
                        SetCountView.setText(Integer.toString(setTime));
                    }
                }
                if (ExerciseState == true && setTime != 0) {
                    if (ExTime_1 > 0 && ExTime_2 == 0) {
                        ExTime_1--;
                        ExTime_2 = 60;
                        StateView.setText("운동시간");
                        textView.setText(String.format("%02d", ExTime_1) + ":" + String.format("%02d", ExTime_2));
                    }
                    if (ExTime_2 >0 ) {
                        ExTime_2 -= 1;
                        StateView.setText("운동시간");
                        textView.setText(String.format("%02d", ExTime_1) + ":" + String.format("%02d", ExTime_2));
                    }
                    if (ExTime_1 == 0 && ExTime_2 == 0) {
                        MediaPlayer player2 = MediaPlayer.create(TimerActivity.this,R.raw.exercise);
                        player2.start();
                        textView.setText(String.format("%02d", ExTime_1) + ":" + String.format("%02d", ExTime_2));
                        RestState = true;
                        ExerciseState = false;
                        ExTime_1 = Integer.parseInt(ExCount.split(":")[0]);
                        ExTime_2 = Integer.parseInt(ExCount.split(":")[1]);
                        setTime --;
                        SetCountView.setText(Integer.toString(setTime));
                    }
                }
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private void stopTimerTask() {
        if (timerTask != null)
        {
            StateView.setText("준비");
            textView.setText(RestCount);
            SetCountView.setText(SetCount);
            timerTask.cancel();
            timerTask = null;
        }
    }
}

