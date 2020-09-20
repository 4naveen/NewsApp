package com.naveen.mynewsapp.service.repository


import android.app.Application
import com.naveen.mynewsapp.service.data.local.ApiService


class MainRepository(val application :Application ) {
    private var apiservice: ApiService? = null


    init {
        apiservice = ApiService()

    }

    fun getdataList() = apiservice!!.getList(application)


}