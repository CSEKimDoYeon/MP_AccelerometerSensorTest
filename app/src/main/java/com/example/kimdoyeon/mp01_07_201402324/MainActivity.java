package com.example.kimdoyeon.mp01_07_201402324;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SensorManager sm;
    SensorEventListener accL;
    Sensor accSensor;
    MediaPlayer mMediaPlayer;

    float acc_X;
    float acc_Y;
    float acc_Z;

    boolean playing = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE); // SensorManager 인스턴스
        accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // 가속도 센서
        accL = new accListener(); // 가속도 센서 리스너 인스턴스

       mMediaPlayer = MediaPlayer.create(this, R.raw.old_pop);
    }

    @Override
    public void onResume() {
        super.onResume();
        sm.registerListener(accL, accSensor, SensorManager.SENSOR_DELAY_NORMAL); // 가속도 센서 리스너
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private class accListener implements SensorEventListener {
        public void onSensorChanged(SensorEvent event) {  // 가속도 센서 값이 바뀔때마다 호출

            acc_X = event.values[0];
            acc_Y = event.values[1];
            acc_Z = event.values[2];

            Log.e("SENSOR", "  [0] = " + acc_X
                    + ", [1] = " + acc_Y
                    + ", [2] = " + acc_Z);

            if (playing == false && acc_Y < -9.7){
                playing = true;
                Toast toast = Toast.makeText(getApplicationContext(), "Music Service가 시작되었습니다.", Toast.LENGTH_LONG);
                toast.show();
                mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.old_pop);
                mMediaPlayer.start();
            }

            if (playing == true && acc_Y > 9.7){
                playing = false;
                Toast toast = Toast.makeText(getApplicationContext(), "Music Service가 중지되었습니다.", Toast.LENGTH_LONG);
                toast.show();
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        }
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }
}