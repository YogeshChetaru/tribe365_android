package com.chetaru.tribe365_new.KotlinCode.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.chetaru.tribe365_new.API.Models.Home.HomeDetailResponse
import com.chetaru.tribe365_new.KotlinCode.APi.ApiClient
import com.chetaru.tribe365_new.KotlinCode.ApiCall.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeRepository private constructor(app: Application){
    private var instanceApi: API

    init {
        ApiClient.init(app)
        instanceApi= ApiClient.instance
    }
    companion object{
        private var homeRepository: HomeRepository?= null

        fun getInstance(app: Application): HomeRepository?{
            if (homeRepository == null){
                homeRepository= HomeRepository(app)
            }
            return homeRepository
        }
    }


    /************ calling api section ********************
     * 1) HomePageDetail
     * 2) getKudos
     * 3) addKudosAward
     * 4) addDotValueKudosAward
     * 5) getDepartmentUserList
     * 6) getBubbleUnReadNotifications
     * 7) addDOTBubbleRatingsToMultiDepartment
     * 8) getCurrentVersionOfApp
     * 9) addHappyIndex
     * 10)postFeedback
     * 11)userApplyLeave
     * 12)userChangeLeaveStatus
     **/
     private fun homePageDetail(homePageResponse: MutableLiveData<Any>,orgId: String){
         instanceApi.getHomePageDetail(orgId = orgId).enqueue(object : Callback<HomeDetailResponse>{
             override fun onFailure(call: Call<HomeDetailResponse>, t: Throwable) {
                 homePageResponse.value=t
             }

             override fun onResponse(call: Call<HomeDetailResponse>,response: Response<HomeDetailResponse>) {
                 if (response.isSuccessful){
                     homePageResponse.value= response.body()
                 }else{
                     homePageResponse.value="error"
                 }
             }
         })

     }


}