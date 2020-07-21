package com.example.lesson48.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson48.R;
import com.example.lesson48.model.FiveDayForecastModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FiveDayForecastRVAdapter extends RecyclerView.Adapter<FiveDayForecastRVAdapter.FiveDayForecastVH> {

    private List<FiveDayForecastModel> fiveDayForecastModels;

    public FiveDayForecastRVAdapter(List<FiveDayForecastModel> fiveDayForecastModels) {
        this.fiveDayForecastModels = fiveDayForecastModels;
    }

    @NonNull
    @Override
    public FiveDayForecastVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recycler_res_5d_forecast, parent, false
        );
        return new FiveDayForecastVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FiveDayForecastVH holder, int position) {
        FiveDayForecastModel model = fiveDayForecastModels.get(position);

        holder.description.setText(model.getDescription());
        holder.dayTemp.setText(model.getDayTemp());
        holder.nightTemp.setText(model.getNightTemp());
        Picasso.get().load(model.getIcon()).into(holder.icon);

    }

    @Override
    public int getItemCount() {
        return fiveDayForecastModels.size();
    }

    static class FiveDayForecastVH extends RecyclerView.ViewHolder {

        private AppCompatTextView description, dayTemp, nightTemp;
        private AppCompatImageView icon;

        public FiveDayForecastVH(@NonNull View itemView) {
            super(itemView);
            findIds(itemView);
        }

        private void findIds(View v) {
            icon = v.findViewById(R.id.img_weather_icon_5d);
            description = v.findViewById(R.id.txt_description_5d);
            dayTemp = v.findViewById(R.id.txt_day_temp_5d);
            nightTemp = v.findViewById(R.id.txt_night_temp_5d);
        }
    }
}
