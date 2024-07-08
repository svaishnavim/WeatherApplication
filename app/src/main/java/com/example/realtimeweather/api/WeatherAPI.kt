package com.example.realtimeweather.api

import retrofit2.Response
import retrofit2.http.*

interface WeatherAPI {
    //create api
    @GET("/v1/current.json")
    suspend fun getWeather(@Query("key") apikey:String, @Query("q") city:String) : Response<WeatherModel>
}