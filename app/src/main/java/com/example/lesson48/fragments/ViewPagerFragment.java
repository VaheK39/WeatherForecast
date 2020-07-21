package com.example.lesson48.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lesson48.R;
import com.example.lesson48.adapter.ViewPagerAdapter;
import com.example.lesson48.model.FiveDayWeatherForecast;
import com.example.lesson48.utils.Constants;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ViewPagerFragment extends Fragment implements ForecastDataUpdater {
    private static final String TAG = "ViewPagerFragment";

    private FiveDayWeatherForecast forecast;
    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ForecastDataUpdater forecastDataUpdater;

    public ViewPagerFragment() {
        // Required empty public constructor
    }


    public static ViewPagerFragment newInstance(FiveDayWeatherForecast forecast) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.BundleKeys.FORECAST_VP_FRAGMENT, forecast);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.forecast = getArguments().getParcelable(Constants.BundleKeys.FORECAST_VP_FRAGMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_pager, container, false);
        findIds(v);
        adapter = new ViewPagerAdapter(Objects.requireNonNull(getActivity()), loadFragments());
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
        new TabLayoutMediator(tabLayout, viewPager, (
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText(R.string.tab_1_title);
                            tab.setIcon(R.drawable.ic_your_location);
                            break;
                        case 1:
                            tab.setText(R.string.tab_2_title);
                            tab.setIcon(R.drawable.ic_find_your_city);
                            break;
                    }
                })).attach();
        return v;
    }

    private List<Fragment> loadFragments() {
        List<Fragment> list = new ArrayList<>();
        list.add(YourLocationFragment.newInstance(forecast));
        list.add(FindCityFragment.newInstance(forecast));
        return list;
    }


    private void findIds(View v) {
        viewPager = v.findViewById(R.id.view_pager);
        tabLayout = v.findViewById(R.id.tab_layout_locations);
    }

    


    @Override
    public void onForecastUpdate(FiveDayWeatherForecast forecast) {
//        forecastDataUpdater = FindCityFragment.newInstance(forecast);
//        forecastDataUpdater.onForecastUpdate(forecast);
        Log.d(TAG, "onForecastUpdate: activity = " + getActivity());
        List<Fragment> list = new ArrayList<>();
        list.add(YourLocationFragment.newInstance(this.forecast));
        list.add(FindCityFragment.newInstance(forecast));
        adapter = new ViewPagerAdapter(Objects.requireNonNull(getActivity()), list);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1,false);

    }
}