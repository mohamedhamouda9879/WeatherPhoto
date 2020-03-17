package com.example.photoweather.Api;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class ApiManager {

    private static String BASEURL="https://api.openweathermap.org/";

   private static Retrofit retrofitInstance;
     private static Retrofit getInstance(){

         HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
             @Override
             public void log(String message) {
                 Log.e("api",message);
             }
         });

         OkHttpClient okHttpClient=new OkHttpClient.Builder()
                 .addInterceptor(httpLoggingInterceptor)
                 .build();

         if(retrofitInstance==null){
             retrofitInstance=new Retrofit.Builder().baseUrl(BASEURL)
                     .addConverterFactory(GsonConverterFactory.create())
                     .client(okHttpClient)
                     .build();
         }
     return retrofitInstance;
     }

     public static Services getApis(){

         return  getInstance().create(Services.class);
     }
}
