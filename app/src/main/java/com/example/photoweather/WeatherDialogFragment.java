package com.example.photoweather;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import static com.example.photoweather.Utils.ConvertPhotos.convertBase64ToBitmap;

public class WeatherDialogFragment extends DialogFragment {



    private ImageView imageView;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    private TextView tempp;
    private TextView descc;
    private TextView maxTempp;
    private TextView minTempp;
    private TextView datee;
    private Button shareButton;
    private View view;
    private String image;

    WeatherDialogFragment() {
    }

    Target target=new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            SharePhoto sharePhoto=new SharePhoto.Builder()
                    .setBitmap(convertBase64ToBitmap(image))
                    .build();
            if(shareDialog.canShow(SharePhotoContent.class)){
                SharePhotoContent content=new SharePhotoContent.Builder()
                        .addPhoto(sharePhoto)
                        .build();
                shareDialog.show(content);
            }
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.weather_dialog, container, false);
        FacebookSdk.sdkInitialize(this.getActivity());

        shareButton=view.findViewById(R.id.fb_share_button);
        callbackManager=CallbackManager.Factory.create();
        shareDialog=new ShareDialog(this);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(getActivity(), "Share is Successful!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getActivity(), "Share is Faild!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                Picasso.get()
                        .load(image)
                        .into(target);

            }
        });

        initView(view);

        Bundle bundle = getArguments();
        String Temmp = bundle.getString("temp");
        String MaxTemp = bundle.getString("Maxtemp");
        String MinTemp = bundle.getString("Mintemp");
        String dateTime = bundle.getString("date");
        String description = bundle.getString("desc");
         image = bundle.getString("image");

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
