package com.example.photoweather.Api;


import com.example.photoweather.Model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Services {

  /*  @GET("sources")
    Call<SourcesResponse> getNewsSources(@Query("apiKey") String apiKey, @Query("language") String Language);
*/
    @GET("data/2.5/weather")
    Call<WeatherResponse>getWeather(
             @Query("lat") double lat
            , @Query("lon") double lon
            ,@Query("apiKey") String apiKey);
}
