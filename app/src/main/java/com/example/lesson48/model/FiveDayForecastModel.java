package com.example.lesson48.model;

public class FiveDayForecastModel {
    private String description, dayTemp, nightTemp, icon;

    public FiveDayForecastModel(String description, String dayTemp, String nightTemp, String icon) {
        this.description = description;
        this.dayTemp = dayTemp;
        this.icon = icon;
        this.nightTemp = nightTemp;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }


    public String getDayTemp() {
        return dayTemp;
    }


    public String getNightTemp() {
        return nightTemp;
    }


}
