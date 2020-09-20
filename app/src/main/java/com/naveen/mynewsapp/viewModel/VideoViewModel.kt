package com.naveen.mynewsapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.naveen.mynewsapp.service.repository.MainRepository
import com.naveen.mynewsapp.service.utils.Response
import kotlinx.coroutines.Dispatchers

class VideoViewModel(application :Application) : AndroidViewModel(application) {
    var isConnected: Boolean = false
    private val repository: MainRepository
    init {
        repository = MainRepository(application)
    }



}