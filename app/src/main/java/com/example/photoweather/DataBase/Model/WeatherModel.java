package com.example.photoweather.DataBase.Model;

import android.graphics.Bitmap;

import com.example.photoweather.Model.Main;
import com.example.photoweather.Model.WeatherItem;
import com.example.photoweather.Model.Wind;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class WeatherModel {

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo
    String  image;
    @ColumnInfo
    String temp;
    @ColumnInfo
    String Maxtemp;
    @ColumnInfo
    String Mintemp;
    @ColumnInfo
    String city;
    @ColumnInfo
    String weatherCondition;
    @ColumnInfo
    String date;


    public WeatherModel() {
    }

    @Ignore
    public WeatherModel(String image, String temp, String maxtemp,
                        String mintemp, String city,
                        String weatherCondition, String date) {
        this.image = image;
        this.temp = temp;
        Maxtemp = maxtemp;
        Mintemp = mintemp;
        this.city = city;
        this.weatherCondition = weatherCondition;
        this.date = date;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getMaxtemp() {
        return Maxtemp;
    }

    public void setMaxtemp(String maxtemp) {
        Maxtemp = maxtemp;
    }

    public String getMintemp() {
        return Mintemp;
    }

    public void setMintemp(String mintemp) {
        Mintemp = mintemp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
