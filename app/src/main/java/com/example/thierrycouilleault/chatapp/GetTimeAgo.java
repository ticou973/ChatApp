package com.example.thierrycouilleault.chatapp;

import android.app.Application;
import android.content.Context;


/**
 * Created by thierrycouilleault on 08/03/2018.
 */

public class GetTimeAgo extends Application {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;



    public static String getTimeAgo(long time, Context ctx) {

        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        String just_now = ctx.getString(R.string.just_now);
        String minute_ago = ctx.getString(R.string.minute_ago);
        String minutes_ago = ctx.getString(R.string.minutes_ago);
        String hours_ago = ctx.getString(R.string.hours_ago);
        String days_ago = ctx.getString(R.string.days_ago);
        String seen = ctx.getString(R.string.seen);
        String hour_ago = ctx.getString(R.string.hour_ago);
        String yesterday = ctx.getString(R.string.yesterday);

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return just_now;
        } else if (diff < 2 * MINUTE_MILLIS) {
            return seen + " " + minute_ago;
        } else if (diff < 50 * MINUTE_MILLIS) {
            return seen + " " + diff / MINUTE_MILLIS + " " + minutes_ago;
        } else if (diff < 90 * MINUTE_MILLIS) {
            return seen + " " + hour_ago;
        } else if (diff < 24 * HOUR_MILLIS) {
            return seen + " " + diff / HOUR_MILLIS + " " + hours_ago;
        } else if (diff < 48 * HOUR_MILLIS) {
            return yesterday;
        } else {
            return seen + " " + diff / DAY_MILLIS + " " + days_ago;
        }
    }

}
