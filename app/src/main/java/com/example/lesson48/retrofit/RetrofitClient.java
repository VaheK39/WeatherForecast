package com.example.lesson48.retrofit;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.lesson48.model.FiveDayWeatherForecast;
import com.example.lesson48.utils.APIData;
import com.example.lesson48.utils.Constants;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String TAG = "RetrofitClient";
    private static Retrofit weatherForecast;
    private RestApi api;
    private Context context;
    private ForecastGetter forecastGetter;

    public RetrofitClient (Context context) {
        this.context = context;

    }


    public static Retrofit create() {
        if (weatherForecast == null){
            weatherForecast = new Retrofit.Builder()
                    .baseUrl(APIData.WEATHER_FORECAST_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return weatherForecast;
    }

    public void getForecast(Map<String, String> queryMap, String calledFrom) {
        Log.d(TAG, "getForecast: starting");
        if (context instanceof ForecastGetter) {
            forecastGetter = (ForecastGetter) context;
        }

        queryMap.put(Constants.MapQueries.KEY_UNITS, Constants.MapQueries.VALUE_UNITS_METRIC);
        queryMap.put(Constants.MapQueries.KEY_APP_ID, Constants.MapQueries.VALUE_APP_ID);

        api = weatherForecast.create(RestApi.class);
        Call<FiveDayWeatherForecast> call = api.getForecast(queryMap);
        getResponse(call, calledFrom);

    }

    private void getResponse(Call<FiveDayWeatherForecast> call, String calledFrom) {
        call.enqueue(new Callback<FiveDayWeatherForecast>() {
            @Override
            public void onResponse(Call<FiveDayWeatherForecast> call, Response<FiveDayWeatherForecast> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse:success CODE: " + response.code());
                    FiveDayWeatherForecast forecast = response.body();
                    assert forecast != null;
                    forecastGetter.setForecast(forecast, calledFrom);

                }
            }

            @Override
            public void onFailure(Call<FiveDayWeatherForecast> call, Throwable t) {
                Log.d(TAG, "\n\n---onFailure: FAIL---\n\n");
                t.printStackTrace();
            }
        });

    }


}
