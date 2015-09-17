package com.example.emonidi.getup;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

/**
 * Created by emonidi on 16.9.2015 г..
 */
public class NotifyingState implements StatesInterface {
    Thread thread;
    Runnable runnable;
    Context context;
    TimerService.StateSetter stateSetter;

    public NotifyingState(Context context,TimerService.StateSetter stateSetter){
        this.context = context;
        this.stateSetter = stateSetter;
    }

    @Override
    public void Walking() {
        System.out.print("Not walking");
    }

    @Override
    public void Sitting() {
        System.out.print("Not sitting");
    }

    @Override
    public void Notifying() {
        Log.d("STATUS","ENTERED NOTIFIYING STATE");
        runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("STATE",stateSetter.getState());
                while (stateSetter.getState() == "NOTIFYING"){
                    Log.d("STATE","NOTIFICATION LOOP");
                    notification();
                    try {
                        thread.sleep(300000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                thread.interrupt();
            }
        };

        if(thread == null){
            thread = new Thread(runnable);
            thread.start();
        }else{
            thread.run();
        }
    }

    private void notification(){
        Log.d("STATUS","NOTIFIYING");
        Intent intent = new Intent(context,TimerService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

        NotificationCompat.Builder nb =
                new NotificationCompat.Builder(context)
                .setVibrate(new long[]{300,300,300})
                .setSmallIcon(R.drawable.walking_icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.walking))
                .setContentTitle("Get up!")
                .setContentText("You haven't moved for an hour.");

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(0101,nb.build());
    }
}

