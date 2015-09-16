package com.example.emonidi.getup;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.security.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Handler;

/**
 * Created by emonidi on 13.9.2015 г..
 */
public class TimerService extends IntentService {

    private String state;
    private Thread sittingThread;
    private Thread walkingThread;
    private Thread notifyingThread;

    public WalkingState walkingState;
    public SittingState sittingState;
    public NotifyingState notifyingState;

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

        StateSetter stateSetter = new StateSetter();

        sittingThread = new Thread("sittingThread");
        walkingThread = new Thread("walkingThread");
        notifyingThread = new Thread("notifyingThread");

        sittingState = new SittingState(stateSetter);

        stateSetter.setState("SITTING");

    }

    public class StateSetter {

        public StateSetter(){

        }

        public void setState(String state){
            switch (state){
                case "SITTING":
                    sittingState.Sitting();
                    break;
            }
        }
    }






}
