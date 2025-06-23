package com.chetaru.tribe365_new.KotlinCode.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.widget.Toast
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class Utils {

    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null || target.toString().isEmpty()) {
            false
        } else {
            EMAIL_ADDRESS_PATTERN.matcher(target.toString()).matches()
        }
    }
    fun showToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getAndroidID(context: Context): String? {
        return Settings.System.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }


    @SuppressLint("SimpleDateFormat")
    fun changeDateYMDtoDMY(date: String?): String? {
        val myDate: Date
        var outputDateStr = ""
        try {
            val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            myDate = inputFormat.parse(date)
            outputDateStr = outputFormat.format(myDate)
            println(outputDateStr)
        } catch (e: Exception) {
            //java.text.ParseException: Unparseable date: Geting error
            println("Excep$e")
        }
        return outputDateStr
    }

    @SuppressLint("SimpleDateFormat")
    fun changeDateDMYtoYMD(date: String?): String? {
        val myDate: Date
        var outputDateStr = ""
        try {
            val inputFormat: DateFormat = SimpleDateFormat("dd-MMM-yyyy")
            val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            myDate = inputFormat.parse(date)
            outputDateStr = outputFormat.format(myDate)
            //System.out.println(outputDateStr);
        } catch (e: Exception) {
            //java.text.ParseException: Unparseable date: Geting error
            // System.out.println("Excep" + e);
        }
        return outputDateStr
    }
    /****** Polymorphism in Java ***************/
    /** If you overload Static method in java it is the example of compile time polymorphism.**/
    /** Runtime Polymorphism or Dynamic Method Dispatch is a process in which a call to an overridden method is resolved at runtime than compile-time. **/
    /** Java Collection means a Single unit of object. Java collection framework provides many
     * interfaces (Set,List,Queue,DeQueue) and
     * Class (Arraylist,vector,LinkedList,PriorityQueue,HashSet,LinkedHashSet,TreeSet).
     */

}