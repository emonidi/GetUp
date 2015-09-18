package com.example.emonidi.getup;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.concurrent.ExecutorService;

/**
 * Created by emonidi on 16.9.2015 г..
 */
public class WalkingState implements StatesInterface {

    private int steps;
    private Context context;
    private TimerService.StateSetter stateSetter;
    private Runnable runnable;
    private Thread thread;
    private ExecutorService executorService;

    public WalkingState(final Context context, final TimerService.StateSetter stateSetter,ExecutorService executorService){
        this.context = context;
        this.stateSetter = stateSetter;
        this.executorService = executorService;

        steps = 0;
        Runnable sensorRunnable = new Runnable() {
            Context mContext = context;
            @Override
            public void run() {
                SensorManager sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
                Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                sensorManager.registerListener(new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        steps++;
                        Log.d("STEPS: ",String.valueOf(steps));
                        if(steps > 10){
                            stateSetter.setState("WALKING");
                        }
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {

                    }
                },sensor,100);
            }
        };

        Thread sensorThread = new Thread(sensorRunnable);
        sensorThread.start();
    }

    @Override
    public void Walking() {

        runnable = new Runnable() {
            @Override
            public void run() {
                while (stateSetter.getState() == "WALKING"){
                    Log.d("STATUS","WALKING");
                    int lastStepCount = steps;
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(steps <= lastStepCount){
                        stateSetter.setState("SITTING");
                    }
                }
            }
        };

        this.executorService.execute(runnable);
    }

    @Override
    public void Sitting() {
        System.out.print("Not sitting");
    }

    @Override
    public void Notifying() {
        System.out.print("Not notifying");
    }
}
