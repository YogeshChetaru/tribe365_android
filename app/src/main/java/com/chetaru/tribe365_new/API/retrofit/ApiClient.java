package com.chetaru.tribe365_new.API.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    public static final String BASE_URL = "https://console.tribe365.co/api/";  // live URL

    //public static final String BASE_URL = "https://tribe365demo.chetaru.co.uk/api/"; //(demo)  staging

    //public static final String BASE_URL = "https://staging-redis-console.tribe365.co/api/"; //new staging Redis

    //only use for help
    public static final String SUPPORT_URL = "https://console.tribe365.co/resources/views/support/";  //support url Call for help

    public static OkHttpClient okHttpClient = null;
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getRequestHeader())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public static Retrofit getCustomClient(String baseURL) {
        Retrofit retrofit_ = new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(getRequestHeader())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit_;
    }

    public static OkHttpClient getRequestHeader() {
        if (null == okHttpClient) {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }
}
