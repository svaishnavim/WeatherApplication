    package com.example.realtimeweather

import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.realtimeweather.ui.theme.RealtimeWeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            RealtimeWeatherTheme {
                Surface(modifier = Modifier.fillMaxSize(), color=MaterialTheme.colorScheme.background) {
                    WeatherPage(weatherViewModel)
                }
            }
        }
    }
}
