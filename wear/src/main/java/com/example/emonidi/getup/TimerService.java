package com.example.emonidi.getup;

import android.app.IntentService;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.security.Timestamp;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by emonidi on 13.9.2015 г..
 */
public class TimerService extends IntentService {

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private ArrayBlockingQueue steps;
    private Thread consumerStepThread;
    private Thread sittingThread;
    private String status;
    boolean hourHasPassed = false;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public TimerService(String name) {
        super(name);
    }

    public TimerService(){
        super("TimerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Service started",String.valueOf(new Date().getTime()));
        steps = new ArrayBlockingQueue<Timestamp>(100);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                steps.add(event.timestamp);
                Log.d("STATUS", "ADDING TO QEUE");
                Log.d("QEUE SIZE WHEN ADDED",String.valueOf(steps.size()));
                startSittingTimer();
                setStatus("WALKING");
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(sensorEventListener, sensor, 0);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("STATUS","POLLING");
                    steps.poll(15, TimeUnit.SECONDS);

                    if(steps.size() < 1){
                        consumerStepThread.interrupt();
                        setStatus("SITTING");
                        startSittingTimer();
                    }
                    Log.d("QEUE",steps.toString());
                } catch (InterruptedException e) {
                    Log.d("STATUS","SITTING");
                }

                try {
                    consumerStepThread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        consumerStepThread = new Thread(runnable);
        consumerStepThread.start();
        consumerStepThread.run();
    }

    private void setStatus(String status){
        this.status = status;
    }


    private void startSittingTimer(){

        Runnable sittingRunnable = new Runnable() {
            @Override
            public void run() {
                if(hourHasPassed){
                    setNotification();
                    try {
                        sittingThread.sleep(18000);
                        this.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        sittingThread.sleep(3600000);
                        hourHasPassed = true;
                        this.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        sittingThread = new Thread(sittingRunnable);
        sittingThread.start();
        sittingThread.run();
    }


    private void stopSittingTimer(){
        if(sittingThread != null && !sittingThread.isInterrupted()){
            sittingThread.interrupt();
        }
    }

    private void setNotification(){

    }
}
