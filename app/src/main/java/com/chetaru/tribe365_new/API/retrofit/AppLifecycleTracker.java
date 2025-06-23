package com.chetaru.tribe365_new.API.retrofit;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.chetaru.tribe365_new.utility.Utility;

public class AppLifecycleTracker implements ActivityLifecycleCallbacks {
    public String startInteractionDate = "";
    public String lastInteractionDate = "";
    BaseRequest baseRequest;
    Utility utility;
    Context mContext;
    private int numStrated = 0;
    private String TAG = "AppLifeCycleTracker";
    private int numOfCreated = 0;

    public AppLifecycleTracker() {
        //mContext=this;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        utility = new Utility();
        if (numOfCreated == 0) {
            Log.d(TAG, "onavtivityCreated: app started");
            String timeDate = utility.getCurrentDate();
            Log.d(TAG, "onavtivityCreated: app started:- " + timeDate);
            startInteractionDate = timeDate;
        }
        numOfCreated++;
        //Log.d(TAG, "onActivityCreated: " + numOfCreated);

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

        //Log.d(TAG, "onActivityStarted: background");
        String timeDate = utility.getCurrentDate();
        Log.d(TAG, "background app :- " + timeDate);
        lastInteractionDate = timeDate;
        //getOpenCloseAppStatus();
    }

    @Override
    public void onActivityResumed(Activity activity) {

        //Log.d(TAG, "onActivityStarted: foreground");
        String timeDate = utility.getCurrentDate();
        Log.d(TAG, "foreground app :- " + timeDate);
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
        //  Log.d(TAG,"onActivityDestroyed: "+numOfCreated);
        if (numOfCreated == 0) {
            String timeDate = utility.getCurrentDate();
            Log.d(TAG, "Destroyed app :- " + timeDate);
            lastInteractionDate = timeDate;
            //getOpenCloseAppStatus();
        }
    }

    /*public void getOpenCloseAppStatus(){
        baseRequest= new BaseRequest(this);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try{
                    Gson gson=new Gson();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                utility.showToast(AppLifecycleTracker.this,message);
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                utility.showToast(AppLifecycleTracker.this,message);

            }
        });
        JsonObject object=Functions.getClient().getJsonMapObject(
                "startInteractionDate",startInteractionDate,
                "lastInteractionDate",lastInteractionDate
        );
       baseRequest.callAPIPost(1,object,AppLifecycleTracker.this.getString(R.string.api_add_organisation));
    }*/
}
