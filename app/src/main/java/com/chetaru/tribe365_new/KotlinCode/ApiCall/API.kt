package com.chetaru.tribe365_new.KotlinCode.ApiCall

import com.chetaru.tribe365_new.API.Models.Home.HomeDetailResponse
import com.chetaru.tribe365_new.KotlinCode.APi.*
import com.chetaru.tribe365_new.KotlinCode.model.home.LoginResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface API {
   /* @GET(PRODUCT_LIST)
    fun getProductList(@Query("page") pageIndex: Int, @Query("productTagId") productId: String= PRODUCT_TAG_ID,
                       @Query("marketCode") marketCode: String= MARKET_CODE): Call<ResponseProducts>*/
    /**
     * "email", et_username.getText().toString().trim(),
    "password", et_password.getText().toString(),
    "role", 3 + "",
    "deviceType", 1 + "",
    "deviceId", device_id,
    "fcmToken", sessionParam.fcm_token**/
    @POST(api_userLogin)
    fun getLogin(@Query("email") email:String,@Query("password") password: String,@Query("role") role: String= ROLE,
                @Query("deviceType") deviceType: String= DEVICE_TYPE, @Query("deviceId") deviceId :String,
                 @Query("fcmToken") fcmToken: String): Call<LoginResponse>
    @POST(api_userSetPassword)
    fun setPassword(@Query("email") email: String,@Query("password") password: String):Call<LoginResponse>

    @POST(api_forgotPassword)
    fun forgetPassword(@Query("email") email: String,@Query("password") password: String):Call<LoginResponse>

    @POST(getHomePageDetails)
    fun getHomePageDetail(@Query("orgId") orgId: String,@Query("deviceType") deviceType:String = DEVICE_TYPE ): Call<HomeDetailResponse>

  //  @POST(userProfile)
}