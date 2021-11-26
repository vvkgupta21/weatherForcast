package com.example.weatherforcast

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.loader.content.Loader
import com.example.weatherforcast.api.ForcastDataService
import com.example.weatherforcast.databinding.ActivityMainBinding
import com.example.weatherforcast.globalVariable.CustomLoader
import com.example.weatherforcast.repository.Repository
import com.example.weatherforcast.viewModel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var loader: CustomLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val repo = Repository(ForcastDataService)
        viewModel = ViewModelProvider(this, ViewModelFactory(repo))[MainViewModel::class.java]
        loader = CustomLoader(this)
        binding.retryButton.setOnClickListener {
            fetchData()
        }
        fetchData()
    }

    @SuppressLint("SetTextI18n")
    private fun fetchData(){
        viewModel.getForcastData("Bengaluru", "9b8cb8c7f11c077f8c4e217974d9ee40").observe(this, Observer {
            it?.let { stateData ->
                when (stateData.status) {
                    StateData.DataStatus.SUCCESS -> {
                        binding.childLayout.visibility = View.VISIBLE
                        binding.forcastCardView.visibility = View.VISIBLE
                        val cityName = stateData.data?.city?.name
                        val temp = stateData.data?.list?.get(0)?.wind?.deg
                        val celsius = ((temp?.minus(32))?.times(5))?.div(9)
                        val tempForDay1 = ((stateData.data?.list?.get(1)?.wind?.deg?.minus(32))?.times(5))?.div(9)
                        val tempForDay2 = ((stateData.data?.list?.get(2)?.wind?.deg?.minus(32))?.times(5))?.div(9)
                        val tempForDay3 = ((stateData.data?.list?.get(3)?.wind?.deg?.minus(32))?.times(5))?.div(9)
                        val tempForDay4 = ((stateData.data?.list?.get(4)?.wind?.deg?.minus(32))?.times(5))?.div(9)
                        binding.cityText.text = cityName
                        binding.tempText.text = "$celsius°C"
                        binding.dayTemp1.text = "$tempForDay1°C"
                        binding.dayTemp2.text = "$tempForDay2°C"
                        binding.dayTemp3.text = "$tempForDay3°C"
                        binding.dayTemp4.text = "$tempForDay4°C"
                        loader.hideProgress()
                    }
                    StateData.DataStatus.ERROR -> {
                        loader.hideProgress()
                        binding.loader.visibility = View.GONE
                        binding.emptyLayout.visibility = View.VISIBLE
                        binding.childLayout.visibility = View.GONE
                        binding.forcastCardView.visibility = View.GONE
                    }
                    StateData.DataStatus.LOADING -> {
                        loader.showProgress()
                        binding.childLayout.visibility = View.GONE
                        binding.forcastCardView.visibility = View.GONE
                    }
                }
            }
        })
    }
}