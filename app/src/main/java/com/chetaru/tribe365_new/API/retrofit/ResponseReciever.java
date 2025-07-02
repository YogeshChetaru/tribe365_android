package com.chetaru.tribe365_new.API.retrofit;



import com.google.gson.JsonElement;

public interface ResponseReciever {

     void onSuccess(JsonElement responseJson);
    void onError(String error);



}
