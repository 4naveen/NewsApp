package com.naveen.mynewsapp.service.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class GeneralService {

   companion object {
       fun isOnline(context: Context): Boolean {
           val cm = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
           val netInfo = cm.activeNetworkInfo
           return netInfo != null && netInfo.isConnected
       }

       fun showToast(mContext: Context?, message: String?) {
           try {
               Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
           } catch (e: Exception) {
               e.printStackTrace()
           }
       }


   }
}