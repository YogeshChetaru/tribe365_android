package com.chetaru.tribe365_new.KotlinCode.APi

import android.content.Context
import com.chetaru.tribe365_new.KotlinCode.ApiCall.API
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ContextModule(val context: Context){

    @Provides
    fun provideContext(): Context = context
}

@Module(includes = [ContextModule::class])
class RetrofitModule{

    @Provides
    fun provideRetrofit(httpClient: OkHttpClient){
        Retrofit.Builder()
                .client(httpClient)
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
    @Provides
    fun provideHttpClient(interceptor: Interceptor, cache: Cache): OkHttpClient =
            OkHttpClient.Builder()
                    .addNetworkInterceptor(interceptor)
                    .cache(cache)
                    .build()
}
@Module(includes = [RetrofitModule::class])
class ExploreServiceModule{
    @Provides
    fun provideLoginService(retrofit: Retrofit) : API= retrofit.create(API::class.java)
}