package com.example.lesson48.retrofit;

import com.example.lesson48.model.FiveDayWeatherForecast;
import com.example.lesson48.utils.APIData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RestApi {

    @GET(APIData.HOURLY_FORECAST_SUFFIX_URL)
    Call<FiveDayWeatherForecast> getForecast(@QueryMap Map<String, String> queries);

}
