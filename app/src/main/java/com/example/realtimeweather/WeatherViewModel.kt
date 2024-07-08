package com.example.realtimeweather

import android.net.Network
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.viewModelScope
import com.example.realtimeweather.api.*
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel(){
    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    //get data from retrofit
    fun getData(city:String){
        _weatherResult.value= NetworkResponse.Loading
        viewModelScope.launch{
           try{
               val response = weatherApi.getWeather(Constant.apikey, city)
               if(response.isSuccessful){
                   //Log.i("response", response.body().toString())
                   response.body()?.let{
                       _weatherResult.value = NetworkResponse.Success(it)
                   }
               }
               else{
                   //Log.i("error", response.message())
                   _weatherResult.value = NetworkResponse.Error("Failed to load data")
               }
           }
           catch(e: Exception){
               _weatherResult.value = NetworkResponse.Error("Failed to load data")
           }
        }

    }
}