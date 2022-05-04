package com.example.weatherapp

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class ForecastDetailFragment : Fragment(){

    private val args: ForecastDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
               detailScreen()
            }
        }
    }
    
    @Composable
    fun detailScreen(){
        Column {
            iconAndTemp()
            description()
        }
    }
    
    @Composable
    fun iconAndTemp(){
        val dayTemp = args.dayForecast.temp.day.toInt()
        val iconName = args.dayForecast.weather.firstOrNull()!!.icon
        val iconURL = "https://openweathermap.org/img/wn/${iconName}@2x.png"

        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier
            .fillMaxWidth()) {

            AsyncImage(model = iconURL, contentDescription = null, modifier = Modifier.size(100.dp))
            Text("$dayTemp °", fontSize = 75.sp)
        }
    }
    @Preview
    @Composable
    fun description(){
        val minTemp = args.dayForecast.temp.min
        val maxTemp = args.dayForecast.temp.max
        val humidity = args.dayForecast.humidity
        val pressure = args.dayForecast.pressure
        val windSpeed = args.dayForecast.speed
        val description = args.dayForecast.weather.firstOrNull()!!.description
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp)) {
            Text(text = "Low: $minTemp", fontSize = 20.sp)
            Text(text = "High: $maxTemp", fontSize = 20.sp)
            Text(text = "Humidity: $humidity %", fontSize = 20.sp)
            Text(text = "Pressure: $pressure hPa", fontSize = 20.sp)
            Text(text = "Wind Speed: $windSpeed", fontSize = 20.sp)
            Text(text = "$description", fontSize = 20.sp)
        }
    }

}