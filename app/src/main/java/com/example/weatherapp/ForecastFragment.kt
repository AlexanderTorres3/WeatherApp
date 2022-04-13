package com.example.weatherapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForecastFragment: Fragment(R.layout.fragment_forecast) {
    lateinit var recyclerView: RecyclerView
    @Inject lateinit var viewModel: ForecastFragmentViewModel
    private val args: ForecastFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData(args.coordinates)
        viewModel.dailyForecast.observe(this){ dailyForecast ->
            recyclerView.adapter = ForecastRecyclerViewAdapter(dailyForecast.forecastList)
        }
    }
}