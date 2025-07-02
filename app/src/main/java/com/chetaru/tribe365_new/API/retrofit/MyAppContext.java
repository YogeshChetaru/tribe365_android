package com.chetaru.tribe365_new.API.retrofit;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.chetaru.tribe365_new.BuildConfig;
import com.chetaru.tribe365_new.utility.Utility;

import timber.log.Timber;

public class MyAppContext extends Application implements Application.ActivityLifecycleCallbacks {
    public static Boolean isAppBackGround = false, isAppForground = false;
    public static Boolean isAppStart = false, isAppStop = false;
    public final int timerRate = 500;
    public boolean isAppRunning = true;
    public String startInteractionDate = "";
    public String lastInteractionDate = "";
    Utility utility;
    BaseRequest baseRequest;
    long firstTime = 0L, secondTime = 0L;
    private int numStrated = 0;
    private String TAG = "AppLifeCycleTracker";
    private int numOfCreated = 0;


    public void setIsAppRunning(boolean value) {
        isAppRunning = value;
    }

    public boolean isAppRunning() {
        return isAppRunning;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //TimerTask task = new ScheduleTask();

        utility = new Utility();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        //registerActivityLifecycleCallbacks(new AppLifecycleTracker());
        registerActivityLifecycleCallbacks(this);
        if (BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }


    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        String timeDate = utility.getCurrentDate();
        // Log.d("appStatus", "App is Terminate disconnect time: " + timeDate);
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (numOfCreated == 0) {
            // Log.d(TAG,"onavtivityCreated: app started");
            String timeDate = utility.getCurrentDate();
            Log.d(TAG, "onavtivityCreated: app started:- " + timeDate);
            startInteractionDate = timeDate;
            isAppStart = true;
        }
        numOfCreated++;
        Log.d(TAG, "onActivityCreated: " + numOfCreated);

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (numStrated == 0) {
            // app went to foreground
            Log.d(TAG, "onActivityStarted: foreground");

        }
        numStrated++;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        // Log.d(TAG, "onActivityStarted: background");
        String timeDate = utility.getCurrentDate();
        Log.d(TAG, "background app :- " + timeDate + " number of create: " + numOfCreated);
        lastInteractionDate = timeDate;
        //if (startInteractionDate)
        isAppBackGround = true;
        isAppStart = false;
    }

    @Override
    public void onActivityResumed(Activity activity) {

        //Log.d(TAG, "onActivityStarted: foreground");
        String timeDate = utility.getCurrentDate();
        Log.d(TAG, "foreground app :- " + timeDate + " number of create: " + numOfCreated);
        startInteractionDate = timeDate;
        /*secondTime = System.currentTimeMillis();
        long time= System.currentTimeMillis();
        if (firstTime>=secondTime+60){}*/
        isAppForground = true;
        isAppBackGround = false;
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        numOfCreated--;
        Log.d(TAG, "onActivityDestroyed: " + numOfCreated);
        if (numOfCreated == 0) {

            isAppStop = true;
            isAppBackGround = false;
            isAppForground = false;
            // getOpenCloseAppStatus();
        }
        String timeDate = utility.getCurrentDate();
        Log.d(TAG, "Destroyed app :- " + timeDate);
        lastInteractionDate = timeDate;
        isAppBackGround = true;
    }

    /*public void getOpenCloseAppStatus() {
        baseRequest = new BaseRequest(this);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    //Gson gson = new Gson();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                //utility.showToast(this, message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                //utility.showToast(this, message);

            }
        });
        JsonObject object = Functions.getClient().getJsonMapObject(
                "startInteractionDate", startInteractionDate,
                "lastInteractionDate", lastInteractionDate
        );
        baseRequest.callAPIPost(1, object, getString(R.string.api_add_organisation));

    }*/
}


