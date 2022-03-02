package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForecastActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    @Inject lateinit var viewModel: ForecastViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
        viewModel.dailyForecast.observe(this){ dailyForecast ->
            recyclerView.adapter = ForecastRecyclerViewAdapter(dailyForecast.forecastList)
        }
    }


}