package com.chetaru.tribe365_new.KotlinCode.APi

import android.util.Log

class MessageLog {
    companion object{
        fun setLogs(tag: String,msg: String?){
            Log.i("$tag: ", msg!!)
        }
    }
}