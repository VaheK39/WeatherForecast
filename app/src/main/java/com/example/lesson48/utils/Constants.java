package com.example.lesson48.utils;

public class Constants {
    public static class MapQueries {
        public static final String KEY_UNITS = "units";
        public static final String KEY_APP_ID = "appid";
        public static final String KEY_CITY = "q";
        public static final String KEY_LAT = "lat";
        public static final String KEY_LNG = "lon";

        public static final String VALUE_UNITS_METRIC = "metric";
        public static final String VALUE_APP_ID = "e2c9e7a5d298e004aa0816e6dc955370";
    }

    public static class BundleKeys {

        public static final String FORECAST_YOUR_LOCATION_FRAGMENT = "bundle_key_forecast_your_location_fragment";
        public static final String FORECAST_FIND_LOCATION_FRAGMENT = "bundle_key_forecast_find_location_fragment";
        public static final String FORECAST_VP_FRAGMENT = "bundle_key_forecast_vp_fragment";
    }

    public static class WindDirections {

        public static final int N = 0;
        public static final int NE = 1;
        public static final int E = 2;
        public static final int SE = 3;
        public static final int S = 4;
        public static final int SW = 5;
        public static final int W = 6;
        public static final int NW = 7;
        public static final int N_360 = 8;

    }

    public static class MonthCodes {
        public static final int JANUARY = 0;
        public static final int FEBRUARY = 3;
        public static final int MARCH = 3;
        public static final int APRIL = 6;
        public static final int MAY = 1;
        public static final int JUNE = 4;
        public static final int JULY = 6;
        public static final int AUGUST = 2;
        public static final int SEPTEMBER = 5;
        public static final int OCTOBER = 0;
        public static final int NOVEMBER = 3;
        public static final int DECEMBER = 5;
    }

    public static class CenturyCodes {
        public static final int CE_1700 = 4;
        public static final int CE_1800 = 2;
        public static final int CE_1900 = 0;
        public static final int CE_2000 = 6;
        public static final int CE_2100 = 4;
        public static final int CE_2200 = 2;
        public static final int CE_2300 = 0;
    }

    public static class CallCases {
        public static final String CALL_FROM_MAIN = "main_call";
        public static final String CALL_FROM_MAP = "from_map_call";
    }

}
