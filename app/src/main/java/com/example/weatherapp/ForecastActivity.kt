package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ForecastActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    val forecastTempData = listOf<DayForecast>(
        DayForecast(
            1644378432,
            1644392832,
            1644436032,
            ForecastTemp(72.0F, 60.0F, 80.0F),
            102.0F,
            40
        ),
        DayForecast(
            1644378432,
            1644378432,
            1644436392,
            ForecastTemp(70.0F, 60F, 78.0F),
            102.0F,
            45
        ),

        DayForecast(
            1644479232,
            1644479592,
            1644522792,
            ForecastTemp(77.0F, 63.0F, 82.0F),
            105.0F,
            50
        ),

        DayForecast(
            1644565992,
            1644565992,
            1644608712,
            ForecastTemp(76.0F, 66.0F, 80.0F),
            102.0F,
            50
        ),
        DayForecast(
            1644637632,
            1644637632,
            1644637632,
            ForecastTemp(73.0F, 66.0F, 78.0F),
            102.0F,
            52
        ),
        DayForecast(
            1644738012,
            1644738012,
            1644781032,
            ForecastTemp(70.0F, 65.0F, 73.0F),
            100.0F,
            52
        ),
        DayForecast(
            1644824112,
            1644824112,
            1644867492,
            ForecastTemp(75.0F, 63.0F, 79.0F),
            100.0F,
            52
        ),
        DayForecast(
            1644953472,
            1644910272,
            1644953472,
            ForecastTemp(72.0F, 65.0F, 76.0F),
            100.0F,
            52
            ),
        DayForecast(
            1644996492,
            1644996492,
            1645039992,
            ForecastTemp(75.0F, 67.0F, 79.0F),
            100.0F,
            52
        ),
        DayForecast(
            1645083612,
            1645083612,
            1645126992,
            ForecastTemp(78.0F, 68.0F, 78.0F),
            100.0F,
            52
        ),
        DayForecast(
            1645170432,
            1645170432,
            1645213752,
            ForecastTemp(78.0F, 62.0F, 79.0F),
            100.0F,
            52
        ),
        DayForecast(
            1645256832,
            1645256832,
            1645300212,
            ForecastTemp(72.0F, 62.0F, 76.0F),
            100.0F,
            52
        ),
        DayForecast(
            1645328832,
            164534359,
            1645328832,
            ForecastTemp(73.0F, 62.0F, 76.0F),
            100.0F,
            52
        ),
        DayForecast(
            1645415232,
            1645415232,
            1645473372,
            ForecastTemp(75.0F, 62.0F, 78.0F),
            100.0F,
            52
        ),
        DayForecast(
            1645516572,
            1645516572,
            1645559952,
            ForecastTemp(78.0F, 68.0F, 80.0F),
            100.0F,
            52
        ),
        DayForecast(
            1645602792,
            1645602792,
            1645646112,
            ForecastTemp(72.0F, 68.0F, 76.0F),
            100.0F,
            52
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = ForecastRecyclerViewAdapter(forecastTempData)

    }
}