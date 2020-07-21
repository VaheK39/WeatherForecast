package com.example.lesson48.model;

public class TwentyFourHourForecastModel {
    private String temp, icon, time, windSpeed;

    public TwentyFourHourForecastModel(String temp, String icon, String time, String windSpeed) {
        this.temp = temp;
        this.icon = icon;
        this.time = time;
        this.windSpeed = windSpeed;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }
}
