package com.example.emonidi.getup;

import android.app.IntentService;
import android.app.Service;
import android.text.format.Time;
import android.util.Log;

import java.security.Timestamp;
import java.util.Timer;

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

    public SittingState(TimerService.StateSetter stateSetter) {

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
                    time.setToNow();
                    long now = time.toMillis(true);
                    if(now > (lastStepTime + 3600000)){
                        //call return in the runnable
                        thread.interrupt();
                        stateSetter.setState("NOTIFYING");
                        try {
                            thread.sleep(6000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        if (thread == null) {
            thread = new Thread(runnable);
            thread.start();
        } else {
            thread.run();
        }

    }

    @Override
    public void Notifying() {
        System.out.print("Not notifiyng");
    }
}
