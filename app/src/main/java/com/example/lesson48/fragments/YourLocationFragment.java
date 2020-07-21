package com.example.lesson48.fragments;

import android.location.Address;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.lesson48.R;
import com.example.lesson48.adapter.FiveDayForecastRVAdapter;
import com.example.lesson48.adapter.TwentyFourHourRVAdapter;
import com.example.lesson48.day_counter.DayOfTheWeekCounter;
import com.example.lesson48.geo.GeoLocate;
import com.example.lesson48.model.FiveDayForecastModel;
import com.example.lesson48.model.FiveDayWeatherForecast;
import com.example.lesson48.model.TwentyFourHourForecastModel;
import com.example.lesson48.utils.APIData;
import com.example.lesson48.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class YourLocationFragment extends Fragment {
    private static final String TAG = "YourLocationFragment";

    private FiveDayWeatherForecast forecast;
    private AppCompatTextView txtTemp, txtDescription, txtRealFeel,
            txtPressure, txtHumidity, txtWind, txtLocationName,
            txtNext24h, txtNext5d, txtDetailedInfo;
    private RelativeLayout relLayNext24h, relLayNext5d;
    private ConstraintLayout constLayDetailedInfo;
    private RecyclerView recyclerView, recyclerViewFiveDay;
    private TwentyFourHourRVAdapter twentyFourHourRVAdapter;
    private FiveDayForecastRVAdapter fiveDayForecastRVAdapter;

    public YourLocationFragment() {
        // Required empty public constructor
    }


    public static YourLocationFragment newInstance(FiveDayWeatherForecast forecast) {
        YourLocationFragment fragment = new YourLocationFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.BundleKeys.FORECAST_YOUR_LOCATION_FRAGMENT, forecast);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.forecast = getArguments().getParcelable(Constants.BundleKeys.FORECAST_YOUR_LOCATION_FRAGMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_your_location, container, false);
        findIds(v);
        startAnimations();
        initData();
        initRecyclerView();


        return v;
    }

    private void startAnimations() {
        Animation firstAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.down_fade_in);
        Animation secondAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.down_fade_in_offset_second);
        Animation thirdAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.down_fade_in_offset_third);
        txtNext24h.startAnimation(firstAnimation);
        relLayNext24h.startAnimation(firstAnimation);
        txtNext5d.startAnimation(secondAnimation);
        relLayNext5d.startAnimation(secondAnimation);
        txtDetailedInfo.startAnimation(thirdAnimation);
        constLayDetailedInfo.startAnimation(thirdAnimation);
    }

    private void initRecyclerView() {
        twentyFourHourRVAdapter = new TwentyFourHourRVAdapter(loadForecastData());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(twentyFourHourRVAdapter);

        fiveDayForecastRVAdapter = new FiveDayForecastRVAdapter(loadFiveDayForecastData());
        recyclerViewFiveDay.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFiveDay.setAdapter(fiveDayForecastRVAdapter);
        recyclerViewFiveDay.setNestedScrollingEnabled(false);

    }

    private List<FiveDayForecastModel> loadFiveDayForecastData() {
        List<FiveDayForecastModel> list = new ArrayList<>();
        for (int i = 0; i < forecast.getWeatherForecast().size(); i+=8) {

            long dayTempRounded = Math.round(forecast.getWeatherForecast().get(i).getMain().getTempMax());
            long nightTempRounded = Math.round(forecast.getWeatherForecast().get(i + 4).getMain().getTempMin());


            DayOfTheWeekCounter dayOfTheWeekCounter = new DayOfTheWeekCounter(getContext(),
                    forecast.getWeatherForecast().get(i).getDtTxt().substring(0, 10));
            String dayOfTheWeek;

            switch (i) {
                case 0:
                    dayOfTheWeek = getString(R.string.today);
                    break;
                case 8:
                    dayOfTheWeek = getString(R.string.tomorrow);
                    break;
                default: dayOfTheWeek = dayOfTheWeekCounter.getDayOfTheWeek();
            }

            String description = dayOfTheWeek + " "
                    + getString(R.string.interpunct) + " "
                    + forecast.getWeatherForecast().get(i).getWeather().get(0).getDescription();
            String dayTemp = String.valueOf(dayTempRounded);
            String nightTemp = String.valueOf(nightTempRounded);
            String icon = APIData.WEATHER_ICON_URL + forecast.getWeatherForecast().get(i).getWeather().get(0).getIcon() + getString(R.string.png_format);

            list.add(new FiveDayForecastModel(description, dayTemp, nightTemp, icon));
        }


        return list;
    }

    private List<TwentyFourHourForecastModel> loadForecastData() {

        List<TwentyFourHourForecastModel> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            String temp = String.valueOf(forecast.getWeatherForecast().get(i).getMain().getTemp());
            String icon = APIData.WEATHER_ICON_URL + forecast.getWeatherForecast().get(i).getWeather().get(0).getIcon() + getString(R.string.png_format);
            String time = forecast.getWeatherForecast().get(i).getDtTxt().substring(11, 16);
            String windSpeed = forecast.getWeatherForecast().get(i).getWind().getSpeed() + getString(R.string.meters_in_second);


            list.add(new TwentyFourHourForecastModel(temp,icon, time, windSpeed));
        }

        return list;
    }

    private void initData() {
        String temp = String.valueOf(forecast.getWeatherForecast().get(0).getMain().getTemp());
        String description = forecast.getWeatherForecast().get(0).getWeather().get(0).getDescription();
        String realFeel = String.valueOf(forecast.getWeatherForecast().get(0).getMain().getFeelsLike());
        String pressure = forecast.getWeatherForecast().get(0).getMain().getPressure() + getString(R.string.hecto_pascal);
        String humidity = forecast.getWeatherForecast().get(0).getMain().getHumidity() + getString(R.string.percent_symbol);
        String windSpeed = forecast.getWeatherForecast().get(0).getWind().getSpeed() + getString(R.string.meters_in_second);
        String windDirection = getWindDirection(forecast.getWeatherForecast().get(0).getWind().getDeg());
        String windData = windDirection + " " + windSpeed;


        txtTemp.setText(temp);
        txtDescription.setText(description);
        txtRealFeel.setText(realFeel);
        txtPressure.setText(pressure);
        txtHumidity.setText(humidity);
        txtWind.setText(windData);
        txtLocationName.setText(forecast.getCity().getName());
    }

    private String getWindDirection(double deg) {
        int dir = (int)Math.round(deg / 45);
        switch (dir) {
            case Constants.WindDirections.N:
            case Constants.WindDirections.N_360:
                return getString(R.string.north);
            case Constants.WindDirections.NE:
                return getString(R.string.north_east);
            case Constants.WindDirections.E:
                return getString(R.string.east);
            case Constants.WindDirections.SE:
                return getString(R.string.south_east);
            case Constants.WindDirections.S:
                return getString(R.string.south);
            case Constants.WindDirections.SW:
                return getString(R.string.south_west);
            case Constants.WindDirections.W:
                return getString(R.string.west);
            case Constants.WindDirections.NW:
                return getString(R.string.north_west);

            default: return getString(R.string.no_data);
        }
    }

    private void findIds(View v) {
        txtTemp = v.findViewById(R.id.txt_temp);
        txtDescription = v.findViewById(R.id.txt_description);
        txtRealFeel = v.findViewById(R.id.txt_real_feel);
        txtPressure = v.findViewById(R.id.txt_pressure);
        txtHumidity = v.findViewById(R.id.txt_humidity);
        txtWind = v.findViewById(R.id.txt_wind);
        recyclerView = v.findViewById(R.id.recycler_view_24h_forecast);
        recyclerViewFiveDay = v.findViewById(R.id.recycler_view_5d_forecast);
        txtLocationName = v.findViewById(R.id.txt_location_name);
        txtNext24h = v.findViewById(R.id.txt_next_24_h);
        txtNext5d = v.findViewById(R.id.txt_next_5d);
        txtDetailedInfo = v.findViewById(R.id.txt_detailed_info);
        relLayNext24h = v.findViewById(R.id.rel_lay_24h_res_view_container);
        relLayNext5d = v.findViewById(R.id.rel_lay_five_day_forecast_recycler_container);
        constLayDetailedInfo = v.findViewById(R.id.const_lay_detailed_info_container);
    }
}