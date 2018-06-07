package com.mad.madproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mad.madproject.R;
import com.mad.madproject.addtripdetails.AddTripDetailsActivity;
import com.mad.madproject.model.City;
import com.mad.madproject.utils.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by limyandivicotrico on 5/2/18.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<City> mCities;
    private LayoutInflater mInflater;


    public CityAdapter(Context context, ArrayList<City> cities) {
        this.mContext = context;
        this.mCities = cities;
        mInflater = LayoutInflater.from(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.city_layout)
        public RelativeLayout viewLayout;
        @BindView(R.id.city_image)
        public ImageView cityImage;
        @BindView(R.id.city_text)
        public TextView city;
        @BindView(R.id.country_text)
        public TextView country;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.city_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        Log.d(Constant.LOG_TAG, "onCreateViewHolder");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.ViewHolder holder, int position) {
        City city = mCities.get(position);

        //TODO: Fix this later (Not all have the same images)
        holder.cityImage.setImageResource(R.drawable.background);
        holder.city.setText(city.getCity());
        holder.country.setText(city.getCountry());

        holder.viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddTripDetailsActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }
}
