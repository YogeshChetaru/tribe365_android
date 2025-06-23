package com.chetaru.tribe365_new.KotlinCode.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.chetaru.tribe365_new.KotlinCode.APi.ApiClient
import com.chetaru.tribe365_new.KotlinCode.ApiCall.API
import com.chetaru.tribe365_new.KotlinCode.model.home.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository private constructor(app: Application){
    private var instanceApi: API

    init {
        ApiClient.init(app)
        instanceApi= ApiClient.instance
    }
    companion object{
        private var loginRepository: LoginRepository?=null

        fun getInstance(app: Application): LoginRepository?{
            if (loginRepository == null){
                loginRepository= LoginRepository(app)
            }
            return loginRepository
        }
    }
    /**
     * "email", et_username.getText().toString().trim(),
    "password", et_password.getText().toString(),
    "role", 3 + "",
    "deviceType", 1 + "",
    "deviceId", device_id,
    "fcmToken", sessionParam.fcm_token**/
    fun getLogin(loginRepository: MutableLiveData<Any>, userName: String, password: String,deviceId: String,fcmToken: String){
        instanceApi.getLogin(email = userName,password = password,deviceId= deviceId, fcmToken = fcmToken).enqueue(object : Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginRepository.value = t
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    loginRepository.value= response.body()
                }else{
                    loginRepository.value= "error"
                }
            }
        })

    }
    fun setPassword(passwordSet : MutableLiveData<Any>, userName: String,password: String){
        instanceApi.setPassword(email = userName, password = password).enqueue(object : Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                passwordSet.value = t
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    passwordSet.value= response.body()
                }else{
                    passwordSet.value = "error"
                }
            }
        })
    }
}