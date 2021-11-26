package com.example.weatherforcast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforcast.repository.Repository
import com.example.weatherforcast.viewModel.MainViewModel

class ViewModelFactory(private val repo:Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repo) as T
    }
}