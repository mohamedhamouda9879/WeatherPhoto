package com.example.photoweather.DataBase.Dao;


import com.example.photoweather.DataBase.Model.WeatherModel;
import com.example.photoweather.Model.WeatherItem;
import com.example.photoweather.Model.WeatherResponse;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface WeatherDao {

    @Insert
    void insertItem(WeatherModel item);


    @Query("select * from WeatherModel")
    List<WeatherModel> getWeatherList();


}
