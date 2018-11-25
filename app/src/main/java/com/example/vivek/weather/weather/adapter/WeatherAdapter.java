package com.example.vivek.weather.weather.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vivek.weather.R;
import com.example.vivek.weather.utils.Utils;
import com.example.vivek.weather.weather.models.DayForeCastModel;

import java.util.List;
import java.util.Locale;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherItemViewHolder>{

    private List<DayForeCastModel> dayForeCastModelList;
    private Context mContext;

    public WeatherAdapter(List<DayForeCastModel> dayForeCastModels, Context context) {
        this.dayForeCastModelList = dayForeCastModels;
        this.mContext = context;
    }

    @NonNull
    @Override
    public WeatherItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_item, viewGroup, false);
        return new WeatherItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherItemViewHolder weatherItemViewHolder, int position) {
        DayForeCastModel dayForeCastModel = dayForeCastModelList.get(position);
        weatherItemViewHolder.onBind(dayForeCastModel);
    }

    @Override
    public int getItemCount() {
        if (dayForeCastModelList != null)
            return dayForeCastModelList.size();
        return 0;
    }

    class WeatherItemViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        AppCompatTextView dayTextView, temperatureTextView;

        WeatherItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.dayTextView = itemView.findViewById(R.id.dayTextView);
            this.temperatureTextView = itemView.findViewById(R.id.tempTextView);
        }

        void onBind(DayForeCastModel dayForeCastModel) {
            itemView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_bottom_border_grey));
            dayTextView.setText(Utils.getDayForDate(dayForeCastModel.getDate(), Locale.getDefault()));
            temperatureTextView.setText(mContext.getString(R.string.temp_text, dayForeCastModel.getForeCastDetails().getAvgTemperature()));
        }
    }
}
