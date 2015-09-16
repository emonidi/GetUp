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

    public SittingState(TimerService.StateSetter stateSetter){

        this.stateSetter = stateSetter;


    }

    @Override
    public void Walking() {
        System.out.print("Not walking");
    }

    @Override
    public void Sitting() {

        final Time time = new Time();

        time.setToNow();
        final long lastStepTime = time.toMillis(true);
        runnable = new Runnable() {
            @Override
            public void run() {
                time.setToNow();
                long now = time.toMillis(true);
                Log.d("SITTING","Las step time: "+ String.valueOf(lastStepTime)+"| Now: "+ String.valueOf(now));

                if(now < (lastStepTime+60000)){
                    Log.d("TIME",String.valueOf(now < (lastStepTime+60000)));
                    try {
                        thread.sleep(100);
                        this.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    stateSetter.setState("NOTIFYING");
                }

            }
        };

        if(thread == null){
            thread = new Thread(runnable);
        }
        thread.start();
    }

    @Override
    public void Notifying() {
        System.out.print("Not notifiyng");
    }
}
