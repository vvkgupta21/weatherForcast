package com.example.weatherforcast.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherforcast.ForcastModel
import com.example.weatherforcast.api.ForcastDataService

class Repository(private val forcastService:ForcastDataService) {

    suspend fun getForcastData(q: String, appId: String): LiveData<ForcastModel> {
        val data = MutableLiveData<ForcastModel>()
        val response = forcastService.forcastInstance.getForcastData(q, appId).await()
        data.value = response
        return data
    }
}