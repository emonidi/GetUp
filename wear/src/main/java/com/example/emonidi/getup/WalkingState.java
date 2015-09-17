package com.example.emonidi.getup;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by emonidi on 16.9.2015 г..
 */
public class WalkingState implements StatesInterface {

    private int steps;
    private Context context;
    private TimerService.StateSetter stateSetter;
    private Runnable runnable;
    private Thread thread;

    public WalkingState(final Context context, final TimerService.StateSetter stateSetter){
        this.context = context;
        this.stateSetter = stateSetter;

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
                },sensor,0);
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
                    int lastStepCount = steps;
                    try {
                        thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(steps <= lastStepCount){
                        stateSetter.setState("SITTING");
                    }
                }
            }
        };

        if(thread == null){
            thread = new Thread(runnable);
            thread.start();
        }else{
            thread.run();
        }
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
