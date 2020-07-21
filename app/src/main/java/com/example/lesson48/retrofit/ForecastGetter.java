package com.example.lesson48.retrofit;

import com.example.lesson48.model.FiveDayWeatherForecast;

public interface ForecastGetter {
    void setForecast(FiveDayWeatherForecast forecast, String calledFrom);

}
