package com.marcoscg.buggysample;

import android.app.Application;

import com.marcoscg.buggy.Buggy;

/**
 * Created by @MarcosCGdev on 12/12/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Buggy.init(this)
                .withEmail("marcoscgdev@gmail.com")
                .withSubject("Bug report for " + getResources().getString(R.string.app_name))
                .start();
    }

}