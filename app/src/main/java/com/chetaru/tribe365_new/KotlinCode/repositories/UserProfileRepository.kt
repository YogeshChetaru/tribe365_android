package com.chetaru.tribe365_new.KotlinCode.repositories

import android.app.Application
import com.chetaru.tribe365_new.KotlinCode.APi.ApiClient
import com.chetaru.tribe365_new.KotlinCode.ApiCall.API

class UserProfileRepository private constructor(app: Application){
    private var instanceApi: API

    init {
        ApiClient.init(app)
        instanceApi= ApiClient.instance
    }

    companion object{
        private var profileRepository: UserProfileRepository? = null
        fun getInstance(app: Application): UserProfileRepository?{
            if (profileRepository == null){
                profileRepository= UserProfileRepository(app)
            }
            return profileRepository
        }
    }

    private fun userProfile(){
        //instanceApi.userProfile()
    }
}