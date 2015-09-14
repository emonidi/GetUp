package com.example.emonidi.getup;

import android.app.Activity;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

import java.security.Timestamp;

public class MainActivity extends Activity {

    private TextView mTextView;
    private TextView stepCountView;
    private int goal = 10000;
    Long lastStepTime;
    SensorManager sensorManager;
    SensorEventListener sensorListener;
    ComponentName intentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                stepCountView = (TextView) stub.findViewById(R.id.steps_count);
                Intent intent = new Intent(getApplicationContext(),TimerService.class);
                startService(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
