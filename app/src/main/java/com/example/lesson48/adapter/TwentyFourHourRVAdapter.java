package com.example.lesson48.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson48.R;
import com.example.lesson48.model.TwentyFourHourForecastModel;
import com.example.lesson48.utils.APIData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TwentyFourHourRVAdapter extends RecyclerView.Adapter<TwentyFourHourRVAdapter.TwentyFourHourVH> {

    private List<TwentyFourHourForecastModel> forecastModels;

    public TwentyFourHourRVAdapter(List<TwentyFourHourForecastModel> forecastModels) {
        this.forecastModels = forecastModels;
    }

    @NonNull
    @Override
    public TwentyFourHourVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycler_res_24h_forecast, parent, false
        );
        return new TwentyFourHourVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TwentyFourHourVH holder, int position) {
        TwentyFourHourForecastModel model = forecastModels.get(position);

        holder.temp.setText(model.getTemp());
        holder.time.setText(model.getTime());
        holder.windSpeed.setText(model.getWindSpeed());
        Picasso.get().load(model.getIcon()).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return forecastModels.size();
    }


    static class TwentyFourHourVH extends RecyclerView.ViewHolder {

        private AppCompatTextView temp, time, windSpeed;
        private AppCompatImageView icon;
        public TwentyFourHourVH(@NonNull View itemView) {
            super(itemView);
            findIds(itemView);
        }

        private void findIds(View v) {
            temp = v.findViewById(R.id.txt_temp_24h);
            time = v.findViewById(R.id.txt_time_24h);
            windSpeed = v.findViewById(R.id.txt_wind_speed_24h);
            icon = v.findViewById(R.id.img_weather_icon_24h);
        }
    }
}
