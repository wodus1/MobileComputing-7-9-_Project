package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView setCount;
    TextView exTime;
    TextView restTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCount = findViewById(R.id.textView4);
        exTime = findViewById(R.id.TimeText1);
        restTime = findViewById(R.id.TimeText2);

        Button moveButton=(Button)findViewById(R.id.button6);
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),TimerActivity.class);
                intent.putExtra("세트", setCount.getText().toString());
                intent.putExtra("운동시간", exTime.getText().toString());
                intent.putExtra("휴식시간", restTime.getText().toString());
                startActivity(intent);
            }
        });

        ImageButton calendarButton = (ImageButton)findViewById(R.id.imageButton8);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CalendarActivity.class);
                startActivity(intent);
            }
        });

    }

    public void clickHandler(View view)
    {
        int count1 = Integer.parseInt(setCount.getText().toString());
        int count2_1 = Integer.parseInt(exTime.getText().toString().split(":")[0]);
        int count2_2 = Integer.parseInt(exTime.getText().toString().split(":")[1]);
        int count3_1 = Integer.parseInt(restTime.getText().toString().split(":")[0]);
        int count3_2 = Integer.parseInt(restTime.getText().toString().split(":")[1]);

        switch(view.getId())
        {
            case R.id.imageButton:
                if(count1 >0 ) {
                    count1--;
                    setCount.setText(String.valueOf(count1));
                }
                break;
            case R.id.imageButton2:
                count1++;
                setCount.setText(String.valueOf(count1));
                break;
            case R.id.imageButton3:
                if(count2_1 >0 && count2_2 == 0) {
                    count2_1--;
                    count2_2=60;
                    exTime.setText(String.format("%02d",count2_1) + ":" + String.format("%02d",count2_2));
                }
                if(count2_2 > 0)
                {
                    count2_2--;
                    exTime.setText(String.format("%02d",count2_1) + ":" + String.format("%02d",count2_2));
                }
                break;
            case R.id.imageButton4:
                count2_2++;
                if(count2_2 == 60 )
                {
                    count2_2=0;
                    count2_1++;
                }
                exTime.setText(String.format("%02d",count2_1) + ":" + String.format("%02d",count2_2));
                break;
            case R.id.imageButton5:
                if(count3_1 >0 && count3_2 == 0) {
                    count3_1--;
                    count3_2=60;
                    restTime.setText(String.format("%02d",count3_1) + ":" + String.format("%02d",count3_2));
                }
                if(count3_2 > 0)
                {
                    count3_2--;
                    restTime.setText(String.format("%02d",count3_1) + ":" + String.format("%02d",count3_2));
                }
                break;
            case R.id.imageButton6:
                count3_2++;
                if(count3_2 == 60 )
                {
                    count3_2=0;
                    count3_1++;
                }
                restTime.setText(String.format("%02d",count3_1) + ":" + String.format("%02d",count3_2));
                break;
        }
    }
}