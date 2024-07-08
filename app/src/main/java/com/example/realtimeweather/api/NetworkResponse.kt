package com.example.realtimeweather.api

sealed class NetworkResponse<out T> {
    //T refers to weather model to wrap with anything
    data class Success<out T>(val data: T):NetworkResponse<T>()
    data class Error(val message:String): NetworkResponse<Nothing>()
    object Loading : NetworkResponse<Nothing>()

}