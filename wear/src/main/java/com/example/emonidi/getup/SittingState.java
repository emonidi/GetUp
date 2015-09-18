package com.example.emonidi.getup;

import android.app.IntentService;
import android.app.Service;
import android.text.format.Time;
import android.util.Log;

import java.security.Timestamp;
import java.util.Timer;
import java.util.concurrent.ExecutorService;

/**
 * Created by emonidi on 16.9.2015 г..
 */
public class SittingState implements StatesInterface {

    private TimerService.StateSetter stateSetter;
    private Thread thread;
    private Runnable runnable;
    private Timestamp now;
    private long lastStepTime;
    private Time time;
    private ExecutorService executorService;

    public SittingState(TimerService.StateSetter stateSetter,ExecutorService executorService) {

        this.executorService = executorService;
        this.stateSetter = stateSetter;
        time = new Time();

    }

    @Override
    public void Walking() {
        System.out.print("Not walking");
    }

    @Override
    public void Sitting() {


        time.setToNow();
        lastStepTime = time.toMillis(true);
        runnable = new Runnable() {
            @Override
            public void run() {

                while (stateSetter.getState() == "SITTING") {
                    Log.d("STATUS","SITTING");
                    time.setToNow();
                    long now = time.toMillis(true);
                    if(now > (lastStepTime + 60000)){
                        stateSetter.setState("NOTIFYING");
                        return;
                    }

                    try {
                       Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        this.executorService.execute(runnable);

    }

    @Override
    public void Notifying() {
        System.out.print("Not notifiyng");
    }
}
