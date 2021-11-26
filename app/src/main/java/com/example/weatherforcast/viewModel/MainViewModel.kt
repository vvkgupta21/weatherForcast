package com.example.weatherforcast.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforcast.ForcastModel
import com.example.weatherforcast.StateLiveData
import com.example.weatherforcast.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private var repo: Repository): ViewModel() {

    private lateinit var viewModelJob: Job

    fun getForcastData(q: String, appId: String): StateLiveData<ForcastModel> {
        val data = StateLiveData<ForcastModel>()
        data.postLoading()
        viewModelJob = CoroutineScope(Job() + Dispatchers.Main).launch {
            try {
                val response = repo.getForcastData(q, appId)
                response.value?.let { data.postSuccess(it)}
            } catch (e: Exception) {
                data.postError(e)
            }
        }
        return data
    }
}