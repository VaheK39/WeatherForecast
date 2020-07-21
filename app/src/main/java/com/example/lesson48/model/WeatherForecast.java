package com.example.lesson48.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherForecast implements Parcelable {
        @SerializedName("dt")
        @Expose
        private Integer dt;
        @SerializedName("main")
        @Expose
        private Main main;
        @SerializedName("weather")
        @Expose
        private List<Weather> weather = null;
        @SerializedName("clouds")
        @Expose
        private Clouds clouds;
        @SerializedName("wind")
        @Expose
        private Wind wind;
        @SerializedName("sys")
        @Expose
        private Sys sys;
        @SerializedName("dt_txt")
        @Expose
        private String dtTxt;
        @SerializedName("rain")
        @Expose
        private Rain rain;
        public final static Parcelable.Creator<WeatherForecast> CREATOR = new Parcelable.Creator<WeatherForecast>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public WeatherForecast createFromParcel(Parcel in) {
                return new WeatherForecast(in);
            }

            public WeatherForecast[] newArray(int size) {
                return (new WeatherForecast[size]);
            }

        }
                ;

        protected WeatherForecast(Parcel in) {
            this.dt = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.main = ((Main) in.readValue((Main.class.getClassLoader())));
            in.readList(this.weather, (com.example.lesson48.model.WeatherForecast.class.getClassLoader()));
            this.clouds = ((Clouds) in.readValue((Clouds.class.getClassLoader())));
            this.wind = ((Wind) in.readValue((Wind.class.getClassLoader())));
            this.sys = ((Sys) in.readValue((Sys.class.getClassLoader())));
            this.dtTxt = ((String) in.readValue((String.class.getClassLoader())));
            this.rain = ((Rain) in.readValue((Rain.class.getClassLoader())));
        }

        /**
         * No args constructor for use in serialization
         *
         */
        public WeatherForecast() {
        }

        /**
         *
         * @param dt
         * @param rain
         * @param dtTxt
         * @param weather
         * @param main
         * @param clouds
         * @param sys
         * @param wind
         */
        public WeatherForecast(Integer dt, Main main, java.util.List<Weather> weather, Clouds clouds, Wind wind, Sys sys, String dtTxt, Rain rain) {
            super();
            this.dt = dt;
            this.main = main;
            this.weather = weather;
            this.clouds = clouds;
            this.wind = wind;
            this.sys = sys;
            this.dtTxt = dtTxt;
            this.rain = rain;
        }

        public Integer getDt() {
            return dt;
        }

        public void setDt(Integer dt) {
            this.dt = dt;
        }

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public java.util.List<Weather> getWeather() {
            return weather;
        }

        public void setWeather(java.util.List<Weather> weather) {
            this.weather = weather;
        }

        public Clouds getClouds() {
            return clouds;
        }

        public void setClouds(Clouds clouds) {
            this.clouds = clouds;
        }

        public Wind getWind() {
            return wind;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public Sys getSys() {
            return sys;
        }

        public void setSys(Sys sys) {
            this.sys = sys;
        }

        public String getDtTxt() {
            return dtTxt;
        }

        public void setDtTxt(String dtTxt) {
            this.dtTxt = dtTxt;
        }

        public Rain getRain() {
            return rain;
        }

        public void setRain(Rain rain) {
            this.rain = rain;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(dt);
            dest.writeValue(main);
            dest.writeList(weather);
            dest.writeValue(clouds);
            dest.writeValue(wind);
            dest.writeValue(sys);
            dest.writeValue(dtTxt);
            dest.writeValue(rain);
        }

        public int describeContents() {
            return 0;
        }







    public static class Wind implements Parcelable {
        @SerializedName("speed")
        @Expose
        private Double speed;
        @SerializedName("deg")
        @Expose
        private Integer deg;
        public final static Parcelable.Creator<Wind> CREATOR = new Parcelable.Creator<Wind>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public Wind createFromParcel(Parcel in) {
                return new Wind(in);
            }

            public Wind[] newArray(int size) {
                return (new Wind[size]);
            }

        }
                ;

        protected Wind(Parcel in) {
            this.speed = ((Double) in.readValue((Double.class.getClassLoader())));
            this.deg = ((Integer) in.readValue((Integer.class.getClassLoader())));
        }

        /**
         * No args constructor for use in serialization
         *
         */
        public Wind() {
        }

        /**
         *
         * @param deg
         * @param speed
         */
        public Wind(Double speed, Integer deg) {
            super();
            this.speed = speed;
            this.deg = deg;
        }

        public Double getSpeed() {
            return speed;
        }

        public void setSpeed(Double speed) {
            this.speed = speed;
        }

        public Integer getDeg() {
            return deg;
        }

        public void setDeg(Integer deg) {
            this.deg = deg;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(speed);
            dest.writeValue(deg);
        }

        public int describeContents() {
            return 0;
        }
    }

    public static class Clouds implements Parcelable {

        @SerializedName("all")
        @Expose
        private Integer all;
        public final static Parcelable.Creator<Clouds> CREATOR = new Parcelable.Creator<Clouds>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public Clouds createFromParcel(Parcel in) {
                return new Clouds(in);
            }

            public Clouds[] newArray(int size) {
                return (new Clouds[size]);
            }

        }
                ;

        protected Clouds(Parcel in) {
            this.all = ((Integer) in.readValue((Integer.class.getClassLoader())));
        }

        /**
         * No args constructor for use in serialization
         *
         */
        public Clouds() {
        }

        /**
         *
         * @param all
         */
        public Clouds(Integer all) {
            super();
            this.all = all;
        }

        public Integer getAll() {
            return all;
        }

        public void setAll(Integer all) {
            this.all = all;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(all);
        }

        public int describeContents() {
            return 0;
        }

    }

    public static class Coordinates implements Parcelable {

        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lon")
        @Expose
        private Double lon;
        public final static Parcelable.Creator<Coordinates> CREATOR = new Parcelable.Creator<Coordinates>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public Coordinates createFromParcel(Parcel in) {
                return new Coordinates(in);
            }

            public Coordinates[] newArray(int size) {
                return (new Coordinates[size]);
            }

        }
                ;

        protected Coordinates(Parcel in) {
            this.lat = ((Double) in.readValue((Double.class.getClassLoader())));
            this.lon = ((Double) in.readValue((Double.class.getClassLoader())));
        }

        /**
         * No args constructor for use in serialization
         *
         */
        public Coordinates() {
        }

        /**
         *
         * @param lon
         * @param lat
         */
        public Coordinates(Double lat, Double lon) {
            super();
            this.lat = lat;
            this.lon = lon;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(lat);
            dest.writeValue(lon);
        }

        public int describeContents() {
            return 0;
        }
    }

    public static class Rain implements Parcelable {

        @SerializedName("3h")
        @Expose
        private Double _3h;
        public final static Parcelable.Creator<Rain> CREATOR = new Parcelable.Creator<Rain>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public Rain createFromParcel(Parcel in) {
                return new Rain(in);
            }

            public Rain[] newArray(int size) {
                return (new Rain[size]);
            }

        }
                ;

        protected Rain(Parcel in) {
            this._3h = ((Double) in.readValue((Double.class.getClassLoader())));
        }

        /**
         * No args constructor for use in serialization
         *
         */
        public Rain() {
        }

        /**
         *
         * @param _3h
         */
        public Rain(Double _3h) {
            super();
            this._3h = _3h;
        }

        public Double get3h() {
            return _3h;
        }

        public void set3h(Double _3h) {
            this._3h = _3h;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(_3h);
        }

        public int describeContents() {
            return 0;
        }

    }

    public static class Sys implements Parcelable {

        @SerializedName("pod")
        @Expose
        private String pod;
        public final static Parcelable.Creator<Sys> CREATOR = new Parcelable.Creator<Sys>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public Sys createFromParcel(Parcel in) {
                return new Sys(in);
            }

            public Sys[] newArray(int size) {
                return (new Sys[size]);
            }

        }
                ;

        protected Sys(Parcel in) {
            this.pod = ((String) in.readValue((String.class.getClassLoader())));
        }

        /**
         * No args constructor for use in serialization
         *
         */
        public Sys() {
        }

        /**
         *
         * @param pod
         */
        public Sys(String pod) {
            super();
            this.pod = pod;
        }

        public String getPod() {
            return pod;
        }

        public void setPod(String pod) {
            this.pod = pod;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(pod);
        }

        public int describeContents() {
            return 0;
        }
    }

    public static class Weather implements Parcelable {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("main")
        @Expose
        private String main;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("icon")
        @Expose
        private String icon;
        public final static Parcelable.Creator<com.example.lesson48.model.WeatherForecast> CREATOR = new Parcelable.Creator<com.example.lesson48.model.WeatherForecast>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public com.example.lesson48.model.WeatherForecast createFromParcel(Parcel in) {
                return new com.example.lesson48.model.WeatherForecast(in);
            }

            public com.example.lesson48.model.WeatherForecast[] newArray(int size) {
                return (new com.example.lesson48.model.WeatherForecast[size]);
            }

        };

        protected Weather(Parcel in) {
            this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.main = ((String) in.readValue((String.class.getClassLoader())));
            this.description = ((String) in.readValue((String.class.getClassLoader())));
            this.icon = ((String) in.readValue((String.class.getClassLoader())));
        }

        /**
         * No args constructor for use in serialization
         */
        public Weather() {
        }

        /**
         * @param icon
         * @param description
         * @param main
         * @param id
         */
        public Weather(Integer id, String main, String description, String icon) {
            super();
            this.id = id;
            this.main = main;
            this.description = description;
            this.icon = icon;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(id);
            dest.writeValue(main);
            dest.writeValue(description);
            dest.writeValue(icon);
        }

        public int describeContents() {
            return 0;
        }
    }

    public static class City implements Parcelable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("coord")
        @Expose
        private Coordinates coordinates;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("population")
        @Expose
        private Integer population;
        @SerializedName("timezone")
        @Expose
        private Integer timezone;
        @SerializedName("sunrise")
        @Expose
        private Integer sunrise;
        @SerializedName("sunset")
        @Expose
        private Integer sunset;
        public final static Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public City createFromParcel(Parcel in) {
                return new City(in);
            }

            public City[] newArray(int size) {
                return (new City[size]);
            }

        }
                ;

        protected City(Parcel in) {
            this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.name = ((String) in.readValue((String.class.getClassLoader())));
            this.coordinates = ((Coordinates) in.readValue((Coordinates.class.getClassLoader())));
            this.country = ((String) in.readValue((String.class.getClassLoader())));
            this.population = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.timezone = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.sunrise = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.sunset = ((Integer) in.readValue((Integer.class.getClassLoader())));
        }

        /**
         * No args constructor for use in serialization
         *
         */
        public City() {
        }

        /**
         *
         * @param country
         * @param coordinates
         * @param sunrise
         * @param timezone
         * @param sunset
         * @param name
         * @param id
         * @param population
         */
        public City(Integer id, String name, Coordinates coordinates, String country, Integer population, Integer timezone, Integer sunrise, Integer sunset) {
            super();
            this.id = id;
            this.name = name;
            this.coordinates = coordinates;
            this.country = country;
            this.population = population;
            this.timezone = timezone;
            this.sunrise = sunrise;
            this.sunset = sunset;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Coordinates getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Integer getPopulation() {
            return population;
        }

        public void setPopulation(Integer population) {
            this.population = population;
        }

        public Integer getTimezone() {
            return timezone;
        }

        public void setTimezone(Integer timezone) {
            this.timezone = timezone;
        }

        public Integer getSunrise() {
            return sunrise;
        }

        public void setSunrise(Integer sunrise) {
            this.sunrise = sunrise;
        }

        public Integer getSunset() {
            return sunset;
        }

        public void setSunset(Integer sunset) {
            this.sunset = sunset;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(id);
            dest.writeValue(name);
            dest.writeValue(coordinates);
            dest.writeValue(country);
            dest.writeValue(population);
            dest.writeValue(timezone);
            dest.writeValue(sunrise);
            dest.writeValue(sunset);
        }

        public int describeContents() {
            return 0;
        }

    }

    public static class Main implements Parcelable{
        @SerializedName("temp")
        @Expose
        private Double temp;
        @SerializedName("feels_like")
        @Expose
        private Double feelsLike;
        @SerializedName("temp_min")
        @Expose
        private Double tempMin;
        @SerializedName("temp_max")
        @Expose
        private Double tempMax;
        @SerializedName("pressure")
        @Expose
        private Integer pressure;
        @SerializedName("sea_level")
        @Expose
        private Integer seaLevel;
        @SerializedName("grnd_level")
        @Expose
        private Integer grndLevel;
        @SerializedName("humidity")
        @Expose
        private Integer humidity;
        @SerializedName("temp_kf")
        @Expose
        private Double tempKf;
        public final static Parcelable.Creator<Main> CREATOR = new Parcelable.Creator<Main>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public Main createFromParcel(Parcel in) {
                return new Main(in);
            }

            public Main[] newArray(int size) {
                return (new Main[size]);
            }

        }
                ;

        protected Main(Parcel in) {
            this.temp = ((Double) in.readValue((Double.class.getClassLoader())));
            this.feelsLike = ((Double) in.readValue((Double.class.getClassLoader())));
            this.tempMin = ((Double) in.readValue((Double.class.getClassLoader())));
            this.tempMax = ((Double) in.readValue((Double.class.getClassLoader())));
            this.pressure = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.seaLevel = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.grndLevel = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.humidity = ((Integer) in.readValue((Integer.class.getClassLoader())));
            this.tempKf = ((Double) in.readValue((Integer.class.getClassLoader())));
        }

        /**
         * No args constructor for use in serialization
         *
         */
        public Main() {
        }

        /**
         *
         * @param feelsLike
         * @param tempMax
         * @param temp
         * @param seaLevel
         * @param humidity
         * @param pressure
         * @param tempKf
         * @param grndLevel
         * @param tempMin
         */
        public Main(Double temp, Double feelsLike, Double tempMin, Double tempMax, Integer pressure, Integer seaLevel, Integer grndLevel, Integer humidity, Double tempKf) {
            super();
            this.temp = temp;
            this.feelsLike = feelsLike;
            this.tempMin = tempMin;
            this.tempMax = tempMax;
            this.pressure = pressure;
            this.seaLevel = seaLevel;
            this.grndLevel = grndLevel;
            this.humidity = humidity;
            this.tempKf = tempKf;
        }

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        public Double getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(Double feelsLike) {
            this.feelsLike = feelsLike;
        }

        public Double getTempMin() {
            return tempMin;
        }

        public void setTempMin(Double tempMin) {
            this.tempMin = tempMin;
        }

        public Double getTempMax() {
            return tempMax;
        }

        public void setTempMax(Double tempMax) {
            this.tempMax = tempMax;
        }

        public Integer getPressure() {
            return pressure;
        }

        public void setPressure(Integer pressure) {
            this.pressure = pressure;
        }

        public Integer getSeaLevel() {
            return seaLevel;
        }

        public void setSeaLevel(Integer seaLevel) {
            this.seaLevel = seaLevel;
        }

        public Integer getGrndLevel() {
            return grndLevel;
        }

        public void setGrndLevel(Integer grndLevel) {
            this.grndLevel = grndLevel;
        }

        public Integer getHumidity() {
            return humidity;
        }

        public void setHumidity(Integer humidity) {
            this.humidity = humidity;
        }

        public Double getTempKf() {
            return tempKf;
        }

        public void setTempKf(Double tempKf) {
            this.tempKf = tempKf;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(temp);
            dest.writeValue(feelsLike);
            dest.writeValue(tempMin);
            dest.writeValue(tempMax);
            dest.writeValue(pressure);
            dest.writeValue(seaLevel);
            dest.writeValue(grndLevel);
            dest.writeValue(humidity);
            dest.writeValue(tempKf);
        }

        public int describeContents() {
            return 0;
        }
    }



}
