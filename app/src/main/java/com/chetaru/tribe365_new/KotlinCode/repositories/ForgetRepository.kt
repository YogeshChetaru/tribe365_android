package com.chetaru.tribe365_new.KotlinCode.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.chetaru.tribe365_new.KotlinCode.APi.ApiClient
import com.chetaru.tribe365_new.KotlinCode.ApiCall.API
import com.chetaru.tribe365_new.KotlinCode.model.home.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgetRepository private constructor(app: Application){
    private var instanceApi: API

    init {
        ApiClient.init(app)
        instanceApi= ApiClient.instance
    }

    companion object{
        private var forgetRepository: ForgetRepository?= null
        fun getInstance(app: Application): ForgetRepository?{
            if (forgetRepository == null){
                forgetRepository= ForgetRepository(app)
            }
            return forgetRepository
        }
    }

    fun forgetPassword(passwordSet: MutableLiveData<Any>, userName:String, password:String){
        instanceApi.forgetPassword(email = userName,password = password).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
               passwordSet.value= t
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    passwordSet.value = response.body()
                }else{
                    passwordSet.value= "error"
                }
            }
        })
    }

}