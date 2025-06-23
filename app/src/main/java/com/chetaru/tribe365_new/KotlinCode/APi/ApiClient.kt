package com.chetaru.tribe365_new.KotlinCode.APi

import android.annotation.SuppressLint
import android.content.Context
import com.chetaru.tribe365_new.KotlinCode.ApiCall.API
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@SuppressLint("StaticFieldLeak")
object ApiClient {
    private var context: Context?= null

     fun init(context: Context){ApiClient.context = context}

    private fun retrofitService():Retrofit{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient(context!!))
            .baseUrl(BASE_URL)
            .build()
    }
    private fun okHttpClient(context: Context): OkHttpClient{
        val cashSize: Long= (5 * 1024 * 1024).toLong()
        val cache = Cache(context.cacheDir,cashSize)
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor{ chain: Interceptor.Chain ->
                var request= chain.request()
                request = if (CheckNetwork.isConnected(context)){
                    request.newBuilder()
                        .header("content-type","application-json")
                        .header("Cache-control","public, max-age=" + 5)
                        .build()
                }else{
                    request.newBuilder()
                        .header("content-type","application-json")
                        .header("Cache-control","public, only-if-cached, max-stale= " + 60*60*24*7)
                        .build()
                }
                chain.proceed(request)
            }.readTimeout(30,TimeUnit.SECONDS)
            .connectTimeout(10,TimeUnit.SECONDS)
            .build()
    }
    val instance: API by lazy {
        retrofitService().create(API::class.java)
    }
}