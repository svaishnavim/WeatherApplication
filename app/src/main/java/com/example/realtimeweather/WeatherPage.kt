package com.example.realtimeweather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.example.realtimeweather.api.*

@Composable
fun WeatherPage(viewModel: WeatherViewModel){

    var city by remember {
        mutableStateOf("")
    }
    //observe as state for live data
    val weatherResult = viewModel.weatherResult.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            OutlinedTextField(
                modifier=Modifier.weight(1f),
                value = city, onValueChange = {
                city=it
            },
                label={
                    Text(text = "Search for any location") }
            )
            IconButton(onClick = {
                viewModel.getData(city)
                keyboardController?.hide()
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search for any location")
            }
        }
        when(val result = weatherResult.value){
            is NetworkResponse.Error -> {
                Text(text=result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                WeatherDetails(data = result.data)
            }
            null -> {}
        }
    }
}


@Composable
fun WeatherDetails(data:WeatherModel){
    Column(modifier= Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally){
        Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.Bottom){
            Icon(imageVector =Icons.Default.LocationOn , contentDescription = "Location icon", modifier = Modifier.size(40.dp))
            Text(text=data.location.name, fontSize = 30.sp, color=Color.Red, fontWeight = FontWeight.Bold)
            Spacer(modifier=Modifier.width(8.dp))
            Text(text=data.location.country, fontSize = 20.sp, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text="${data.current.temp_c} °C", fontSize = 58.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

        //add image from API
        AsyncImage(modifier = Modifier.size(160.dp),model = "https:${data.current.condition.icon}".replace("64x64", "128x128"), contentDescription ="Condition icon" )
        Text(text=data.current.condition.text,
            fontSize = 22.sp, textAlign = TextAlign.Center, color=Color.Black)
        Text(text="Last Updated")
        Text(text=data.current.last_updated)

        Spacer(modifier = Modifier.height(18.dp))
        Card(modifier = Modifier.background(Color.Magenta)){
            Column(modifier = Modifier.fillMaxWidth()){
                Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                    WeatherKeyVal(key = "Humidity", value = data.current.humidity)
                    WeatherKeyVal(key = "Wind Speed", value = data.current.wind_kph + "km/h")
                }
                Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                    WeatherKeyVal(key = "UV", value = data.current.uv)
                    WeatherKeyVal(key = "Feels Like", value = data.current.feelslike_c + " °C")
                }
                Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                    WeatherKeyVal(key = "Local Date", value = data.location.localtime.split(" ")[0])
                    WeatherKeyVal(key = "Local Time", value = data.location.localtime.split(" ")[1])
                }
            }
        }


    }
}

@Composable
fun WeatherKeyVal(key:String, value:String){
    Column(modifier=Modifier.padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally){
        Text(text=value, fontSize=24.sp, fontWeight=FontWeight.Bold)
        Text(text=key, fontWeight=FontWeight.SemiBold, color=Color.Gray)
    }
}