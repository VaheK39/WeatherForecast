package com.example.lesson48.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FiveDayWeatherForecast implements Parcelable
{

    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private Integer message;
    @SerializedName("cnt")
    @Expose
    private Integer cnt;
    @SerializedName("list")
    @Expose
    private java.util.List<WeatherForecast> weatherForecast = null;
    @SerializedName("city")
    @Expose
    private WeatherForecast.City city;
    public final static Parcelable.Creator<FiveDayWeatherForecast> CREATOR = new Creator<FiveDayWeatherForecast>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FiveDayWeatherForecast createFromParcel(Parcel in) {
            return new FiveDayWeatherForecast(in);
        }

        public FiveDayWeatherForecast[] newArray(int size) {
            return (new FiveDayWeatherForecast[size]);
        }

    }
            ;

    protected FiveDayWeatherForecast(Parcel in) {
        this.cod = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.cnt = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.weatherForecast, (WeatherForecast.class.getClassLoader()));
        this.city = ((WeatherForecast.City) in.readValue((WeatherForecast.City.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public FiveDayWeatherForecast() {
    }

    /**
     *
     * @param city
     * @param cnt
     * @param cod
     * @param message
     * @param weatherForecast
     */
    public FiveDayWeatherForecast(String cod, Integer message, Integer cnt, java.util.List<WeatherForecast> weatherForecast, WeatherForecast.City city) {
        super();
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.weatherForecast = weatherForecast;
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<WeatherForecast> getWeatherForecast() {
        return weatherForecast;
    }

    public void setWeatherForecast(java.util.List<WeatherForecast> weatherForecast) {
        this.weatherForecast = weatherForecast;
    }

    public WeatherForecast.City getCity() {
        return city;
    }

    public void setCity(WeatherForecast.City city) {
        this.city = city;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cod);
        dest.writeValue(message);
        dest.writeValue(cnt);
        dest.writeList(weatherForecast);
        dest.writeValue(city);
    }

    public int describeContents() {
        return 0;
    }

}