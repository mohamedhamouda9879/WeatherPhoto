package com.example.photoweather.Adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photoweather.DataBase.Model.WeatherModel;
import com.example.photoweather.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.photoweather.Utils.ConvertPhotos.convertBase64ToBitmap;


public class WeatherListAdapters extends RecyclerView.Adapter<WeatherListAdapters.viewHolder> {

    private List<WeatherModel> data;
    private onIteamClickLisnear onItemClick;

    public void setOnItemClick(onIteamClickLisnear onItemClick) {
        this.onItemClick = onItemClick;
    }

    public WeatherListAdapters(List<WeatherModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {

        final WeatherModel weatherModel=data.get(position);
        holder.imageView.setImageBitmap(convertBase64ToBitmap( weatherModel.getImage()));
        holder.temp.setText(weatherModel.getTemp());
        holder.Maxtemp.setText(weatherModel.getMaxtemp());
        holder.Mintemp.setText(weatherModel.getMintemp());
        holder.date.setText(weatherModel.getDate());
        holder.weatherCondition.setText(weatherModel.getWeatherCondition());

        if (onItemClick!=null){

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onItemClick(position,weatherModel);
                }
            });
        }
    }
    public void changeData(List<WeatherModel>items){
        this.data=items;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() { return (data==null)? 0 : data.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        private  TextView temp;
        private TextView Maxtemp;
        private TextView Mintemp;
        private TextView date;
        private TextView weatherCondition;
        private ImageView imageView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            temp=itemView.findViewById(R.id.temp);
            Maxtemp=itemView.findViewById(R.id.max_temp);
            Mintemp=itemView.findViewById(R.id.min_temp);
            date=itemView.findViewById(R.id.date);
            weatherCondition=itemView.findViewById(R.id.desc);
            imageView=itemView.findViewById(R.id.image);

        }
    }


    public  interface onIteamClickLisnear{


        void onItemClick(int pos, WeatherModel weatherModel);

    }
}
