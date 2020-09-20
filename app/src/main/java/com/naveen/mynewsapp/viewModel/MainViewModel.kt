package com.naveen.mynewsapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.naveen.mynewsapp.service.repository.MainRepository
import com.naveen.mynewsapp.service.utils.Response
import kotlinx.coroutines.Dispatchers

class MainViewModel(application :Application) : AndroidViewModel(application) {
    var isConnected: Boolean = false
    private val repository: MainRepository
    init {
        repository = MainRepository(application)
    }

    fun getlist() = liveData(Dispatchers.IO) {
        emit(Response.loading(data = null))
        try {
            emit(Response.success(data = repository.getdataList()))
        } catch (exception: Exception) {
            emit(Response.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}