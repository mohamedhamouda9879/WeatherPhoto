package com.example.photoweather;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import static com.example.photoweather.MainActivity.convertBase64ToBitmap;

public class WeatherDialogFragment extends DialogFragment {


    protected View rootView;
    protected ImageView imageView;

    protected TextView tempp;
    protected TextView descc;
    protected TextView maxTempp;
    protected TextView minTempp;
    protected TextView datee;

    WeatherDialogFragment() {

    }

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.weather_dialog, container, false);


        initView(view);

        Bundle bundle = getArguments();
        String Temmp = bundle.getString("temp");
        String MaxTemp = bundle.getString("Maxtemp");
        String MinTemp = bundle.getString("Mintemp");
        String dateTime = bundle.getString("date");
        String description = bundle.getString("desc");
        String image = bundle.getString("image");

        tempp.setText(Temmp);
        descc.setText(description);
        maxTempp.setText(MaxTemp);
        minTempp.setText(MinTemp);
        datee.setText(dateTime);
        imageView.setImageBitmap(convertBase64ToBitmap(image));

        return view;

    }


    private void initView(View rootView) {
        imageView = (ImageView) rootView.findViewById(R.id.image_view);
        tempp=(TextView)rootView.findViewById(R.id.tempp);
        descc = (TextView) rootView.findViewById(R.id.descc);
        maxTempp = (TextView) rootView.findViewById(R.id.max_tempp);
        minTempp = (TextView) rootView.findViewById(R.id.min_tempp);
        datee = (TextView) rootView.findViewById(R.id.datee);
    }
}
