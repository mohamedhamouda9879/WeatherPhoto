package com.example.photoweather.DataBase;

import android.content.Context;

import com.example.photoweather.DataBase.Dao.WeatherDao;
import com.example.photoweather.DataBase.Model.WeatherModel;
import com.example.photoweather.Model.WeatherItem;
import com.example.photoweather.Model.WeatherResponse;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {WeatherModel.class},version = 1,exportSchema = false)
public abstract class WeatherDataBase extends RoomDatabase {


    final static String DBName="WDB";
    public abstract WeatherDao weatherDao();

    private static WeatherDataBase myDataBase;

    public static WeatherDataBase getInstance(Context context) {

        if (myDataBase == null) {
            myDataBase = Room.databaseBuilder(context.getApplicationContext(),
                    WeatherDataBase.class, DBName)
                    .allowMainThreadQueries()
                    .build();
        }
        return myDataBase;
    }
}
