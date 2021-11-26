package com.example.weatherforcast.api

import com.example.weatherforcast.ForcastModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.openweathermap.org"

//https://api.openweathermap.org/data/2.5/forecast?q=Bengaluru&APPID=9b8cb8c7f11c077f8c4e217974d9ee40

interface ApiInterface {

    @GET("data/2.5/forecast")
    fun getForcastData(@Query("q") q: String,
                      @Query("APPID") appId: String
    ): Deferred<ForcastModel>

}

//Retrofit Object,
//

object ForcastDataService{
    var forcastInstance :ApiInterface
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        forcastInstance = retrofit.create(ApiInterface::class.java)
    }
}