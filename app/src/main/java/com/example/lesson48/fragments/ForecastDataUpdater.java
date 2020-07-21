package com.example.lesson48.fragments;

import com.example.lesson48.model.FiveDayWeatherForecast;

public interface ForecastDataUpdater {
    void onForecastUpdate(FiveDayWeatherForecast forecast);


}
