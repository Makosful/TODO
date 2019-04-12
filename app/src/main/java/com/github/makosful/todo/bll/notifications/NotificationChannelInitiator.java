package com.github.makosful.todo.bll.notifications;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;

public class NotificationChannelInitiator extends Application {

    /**
     * The idea of this class is to upkeep D-R-Y and only do this once.
     * The channels are meant for user friendly design, so that we can defer
     * DEFAULT notifications from IMPORTANT notifications, allowing user to pick which to mute
     */
    public static final String CHANNEL_1_ID = "Main notice channel";
    public static final String CHANNEL_2_ID = "Important notice channel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        // Check if we are on Oreo (26) SDK or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /**
             * Below are the two channels mentioned initially where we distribute notifications on.
             * Channel 1 will be the channel for DEFAULT notifications, where as
             * Channel 2 will be the channel for IMPORTANT notifications, allowing the user to mute default ones
             * and keep their alarms on their important notifications. This also allows the user to simply mute both channels.
             * This means that important notifications can, if permitted go off during normally restricted states.
             */

            // CHANNEL 1 | DEFAULT
            NotificationChannel chan1 = new NotificationChannel(CHANNEL_1_ID, "Default Notice", NotificationManager.IMPORTANCE_DEFAULT);
            chan1.enableLights(true);
            chan1.setLightColor(Color.BLUE);
            //sets the vibration pattern.
            /* Vibration patterns go as follows (in ms): [0] = delay, [1] = vibration_time [2] = delay, [3] = vibration_time etc.. */
            chan1.setVibrationPattern(new long[] {0, 500, 250, 500});
            chan1.setDescription("Level 1, for default notifications");

            // CHANNEL 2 | IMPORTANT
            NotificationChannel chan2 = new NotificationChannel(CHANNEL_2_ID, "Important Notice", NotificationManager.IMPORTANCE_HIGH);
            chan1.enableLights(true);
            chan1.setLightColor(Color.BLUE);
            //sets the vibration pattern
            chan1.setVibrationPattern(new long[] {0, 500, 250, 500, 250, 500});
            chan1.setDescription("Level 2, for important notifications");

            NotificationManager nM = getSystemService(NotificationManager.class);
            nM.createNotificationChannel(chan1);
            nM.createNotificationChannel(chan2);
        }
    }
}
