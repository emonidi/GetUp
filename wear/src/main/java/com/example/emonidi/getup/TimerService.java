package com.example.emonidi.getup;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.security.Timestamp;
import java.sql.Time;
import java.util.Date;
import java.util.Timer;

/**
 * Created by emonidi on 11.9.2015 г..
 */
public class TimerService extends IntentService {
    Thread thread;
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
        final Long timestamp = intent.getLongExtra("last_step_time",0);
        Log.d("Timestamt", String.valueOf(timestamp));
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("Running","runnable");
                Long now = new Date().getTime();
                if(now > timestamp+10000){
                    Log.d("Start an hour countdown","NOW");

                }else{
                    this.run();
                }

            }
        };


    }
}
