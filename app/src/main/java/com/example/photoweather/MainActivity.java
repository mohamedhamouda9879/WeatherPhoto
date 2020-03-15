package com.example.photoweather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.photoweather.Adapters.WeatherListAdapters;
import com.example.photoweather.Api.ApiManager;
import com.example.photoweather.Base.BaseActivity;
import com.example.photoweather.DataBase.Model.WeatherModel;
import com.example.photoweather.DataBase.WeatherDataBase;
import com.example.photoweather.Model.Main;
import com.example.photoweather.Model.WeatherItem;
import com.example.photoweather.Model.WeatherResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity implements LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 500;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;
    ImageView imageView;
    public static final String APIKEY="9e8d8267f5693a95b9c0a0f012c59230";
    MyLocationProvider myLocationProvider;
    Location location;
    Bitmap imageBitmap;
    String temp="";
    String MinTemp="";
    String MaxTemp="";
    String city="";
    String desc="";
   String selectedImage="";
   List<WeatherModel> data;
   RecyclerView recyclerView;
   WeatherListAdapters adapter;
    String currentDateandTime="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recyclerView);
        setData();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
                /*.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        if(checkForLocationPermession()){
            //call your function
            showUserLocation();
        }else {
            requestLocationPermession();
        }

        callApi();
        adapter=new WeatherListAdapters(data);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClick(new WeatherListAdapters.onIteamClickLisnear() {
            @Override
            public void onItemClick(int pos, WeatherModel weatherModel) {

                Log.e("errr",data.get(pos).getTemp()+"");
                Bundle intent = new Bundle();
                intent.putString("temp",data.get(pos).getTemp());
                intent.putString("Maxtemp",data.get(pos).getMaxtemp());
                intent.putString("Mintemp",data.get(pos).getMintemp());
                intent.putString("date",data.get(pos).getDate());
                intent.putString("desc",data.get(pos).getWeatherCondition());
                intent.putString("image",data.get(pos).getImage());
//                startActivity(intent);

                WeatherDialogFragment weatherDialogFragment=new WeatherDialogFragment();
                weatherDialogFragment.setArguments(intent);
                weatherDialogFragment.show(getSupportFragmentManager(),"Weather");
            }
        });
    }

    void setData(){

        data= new ArrayList<>();
        data=WeatherDataBase.getInstance(MainActivity.this).weatherDao().getWeatherList();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA && resultCode == RESULT_OK
                ) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            //selectedImage=(Uri) extras.get("data");
            selectedImage=convertBitmapToBase64(imageBitmap);

            Log.e("CAMERA",currentDateandTime);
            WeatherDataBase.getInstance(MainActivity.this).weatherDao().insertItem(
                    new WeatherModel(selectedImage,temp,MaxTemp,
                            MinTemp,city,desc,currentDateandTime));
           // Bitmap x=convertBase64ToBitmap(selectedImage);
           //imageView.setImageBitmap(x);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent();
        takePictureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }

    private void callApi(){
        ApiManager.getApis().getWeather(location.getLatitude()
                ,location.getLongitude(),APIKEY)
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call,
                                           Response<WeatherResponse> response) {
                        Log.e("API","can get response");
                        Log.e("API",response.body()+"");
                        Log.e("API",response.body().getMain().getTemp()+"");
                        Log.e("API",response.body().getWind().getSpeed()+"");
                        Log.e("API",response.body().getName()+"");
                        temp=((int)((response.body().getMain().getTemp())-273.15))+"";
                        MinTemp=((int)((response.body().getMain().getTempMin())-273.15))+"";
                        MaxTemp=((int)((response.body().getMain().getTempMax())-273.15))+"";
                        city=response.body().getName();
                        desc=response.body().getWeather().get(0).getDescription();
                        Log.e("TEMP",temp);
                        Log.e("desc",desc);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
                        currentDateandTime = sdf.format(new Date());



                    }
                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Log.e("API","can't get response");
                        Log.e("API",t.getLocalizedMessage());
                    }
                });
    }
    private void showUserLocation() {
        myLocationProvider= new MyLocationProvider(MainActivity.this,this);
        location=myLocationProvider.getDeviceLocation();
        if(location==null){
            Log.e("location","null");
        }else {

            Log.e("location","can get location");
            Log.e("location",location.getLatitude()+" "+location.getLongitude());
        }

    }
    private void requestLocationPermession() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            //show explanation to user
            showConfirmationMessage(R.string.warning, R.string.location_explanitaion, R.string.ok
                    , new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            ActivityCompat.requestPermissions(activity,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);

                        }
                    });


        } else {
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }

    public boolean checkForLocationPermession(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    //call your function
                    showUserLocation();

                } else {
                    Toast.makeText(this, "Permission is denied ,cannot access your location", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public static String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    public static Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
