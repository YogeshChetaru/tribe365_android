package com.chetaru.tribe365_new.API.retrofit;

/**
 * Created by Prakhar on 11/17/2016.
 */
public interface RequestReciever {
    void onSuccess(int requestCode, String Json, Object object);

    void onFailure(int requestCode, String errorCode, String message);

    void onNetworkFailure(int requestCode, String message);
}
