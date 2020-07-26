package com.example.lesson48.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.example.lesson48.R;
import com.example.lesson48.adapter.FiveDayForecastRVFindLocAdapter;
import com.example.lesson48.day_counter.DayOfTheWeekCounter;
import com.example.lesson48.model.FiveDayForecastModel;
import com.example.lesson48.model.FiveDayWeatherForecast;
import com.example.lesson48.utils.APIData;
import com.example.lesson48.utils.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FindCityFragment extends Fragment {
    private static final String TAG = "FindCityFragment";


    private FiveDayWeatherForecast forecast;
    private AppCompatTextView txtLocation, txtTemp, txtDescription;
    private AppCompatButton btnFindOnMap;
    private ConstraintLayout constLayMainData;
    private RelativeLayout relLay5dForecastContainer;
    private RecyclerView recyclerView;

    private FiveDayForecastRVFindLocAdapter adapter;
    private OnFindFromMapListener findFromMapListener;

    public FindCityFragment() {
        // Required empty public constructor
    }


    public static FindCityFragment newInstance(FiveDayWeatherForecast forecast) {
        FindCityFragment fragment = new FindCityFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.BundleKeys.FORECAST_FIND_LOCATION_FRAGMENT, forecast);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.forecast = getArguments().getParcelable(Constants.BundleKeys.FORECAST_FIND_LOCATION_FRAGMENT);
            if (getContext() instanceof OnFindFromMapListener) {
                findFromMapListener = (OnFindFromMapListener)getContext();
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find_city, container, false);
        findIds(v);
        startAnimations();
        initData(forecast, loadRecyclerViewData(forecast));
        initButtons();
        return v;
    }

    private void startAnimations() {
        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_slow);
        Animation downFadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.down_fade_in_short);
        constLayMainData.startAnimation(fadeIn);
        relLay5dForecastContainer.startAnimation(downFadeIn);
        constLayMainData.setVisibility(View.VISIBLE);
        relLay5dForecastContainer.setVisibility(View.VISIBLE);
    }


    private void initButtons() {
        btnFindOnMap.setOnClickListener( view -> {
            findFromMapListener.onFindFromMapClick();
            constLayMainData.setVisibility(View.GONE);
            relLay5dForecastContainer.setVisibility(View.GONE);
        });
    }

    private void initData(FiveDayWeatherForecast forecast, List<FiveDayForecastModel> fiveDayForecastModels) {
        String location = forecast.getCity().getName();
        String temp = String.valueOf(forecast.getWeatherForecast().get(0).getMain().getTemp());
        String description = forecast.getWeatherForecast().get(0).getWeather().get(0).getDescription();

        Date date = new Date(forecast.getWeatherForecast().get(0).getDt());
        String str = DateFormat.format("yyyy/MM/dd hh:mm:ss", date).toString();

        Log.d(TAG, "initData: " + forecast.getCity().getTimezone() + ", " + str);
        txtLocation.setText(location);
        txtTemp.setText(temp);
        txtDescription.setText(description);
        initRecyclerView(fiveDayForecastModels);
    }

    private void initRecyclerView(List<FiveDayForecastModel> fiveDayForecastModels) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        adapter = new FiveDayForecastRVFindLocAdapter(getContext(), fiveDayForecastModels);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private List<FiveDayForecastModel> loadRecyclerViewData(FiveDayWeatherForecast forecast) {
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

    private void findIds(View v) {
        txtLocation = v.findViewById(R.id.txt_location_name_find_loc);
        txtTemp = v.findViewById(R.id.txt_temp_find_loc);
        txtDescription = v.findViewById(R.id.txt_description_find_loc);
        btnFindOnMap = v.findViewById(R.id.btn_find_location_on_map);
        recyclerView = v.findViewById(R.id.recycler_view_5d_forecast_find_loc);
        relLay5dForecastContainer = v.findViewById(R.id.rel_lay_five_day_forecast_recycler_container_find_loc);
        constLayMainData = v.findViewById(R.id.const_lay_main_info_container_find_loc);
    }



    public interface OnFindFromMapListener {
        void onFindFromMapClick();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (constLayMainData.getVisibility() == View.GONE
                || relLay5dForecastContainer.getVisibility() == View.GONE) {
            startAnimations();
        }
    }
}