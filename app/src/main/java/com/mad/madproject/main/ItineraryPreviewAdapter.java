package com.mad.madproject.main;

/**
 * Created by limyandivicotrico on 5/17/18.
 */

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;
import com.mad.madproject.R;
import com.mad.madproject.viewitinerary.ViewItineraryActivity;
import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.utils.Constant;
import com.mad.madproject.utils.Util;

import java.util.ArrayList;

/**
 * The adapter for the itinerary, inflate the view holder to be used for the recycler view.
 */
public class ItineraryPreviewAdapter extends RecyclerView.Adapter<ItineraryPreviewAdapter.MyViewHolder> {

    //contain all the itinerary preview lists.
    private ArrayList<ItineraryPreview> mItineraryPreviews;
    private Context mContext;
    private LayoutInflater mInflater;
    //initialised so it will be used created once and reused.
    private StorageReference mCityStorageReference;

    /**
     * View holder class link the java object to all xml view.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView rootView;
        TextView tripNameTv;
        ImageView imageViewIcon;
        TextView placeTv;
        TextView dateTv;
        TextView daysIntervalTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.rootView = (CardView) itemView.findViewById(R.id.card_view);
            this.tripNameTv = (TextView) itemView.findViewById(R.id.activity_main_view_itinerary_trip_name);
            this.placeTv = (TextView) itemView.findViewById(R.id.activity_main_place);
            this.dateTv = (TextView) itemView.findViewById(R.id.activity_date_tv);
            this.daysIntervalTv = (TextView) itemView.findViewById(R.id.activity_main_days);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.city_image_view);
        }
    }

    /**
     * Constructor to be called by another class, requires the mContext to be called in and the content of the data.
     * @param context represents the environment to be instantiated for the adapter.
     * @param data represents the data contained by the adapter
     */
    public ItineraryPreviewAdapter(Context context, ArrayList<ItineraryPreview> data) {
        this.mContext = context;
        this.mItineraryPreviews = data;
        mInflater = LayoutInflater.from(context);
        //to be used by the view holder GLIDE API to load the picture.
        mCityStorageReference = Util.getStorageReference("city");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                           int viewType) {
        View view = mInflater
                .inflate(R.layout.itinerary_card_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        final ItineraryPreview itineraryPreview = mItineraryPreviews.get(listPosition);

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewItineraryActivity.class);
                intent.putExtra("Day", itineraryPreview.getDayInterval());
                intent.putExtra("PreviewKey", itineraryPreview.getItineraryPreviewId());
                view.getContext().startActivity(intent);
            }
        });

        holder.tripNameTv.setText(itineraryPreview.getTripName());
        holder.placeTv.setText(itineraryPreview.getCity());
        //TODO: Fix warning.
        holder.dateTv.setText(itineraryPreview.getStartDate() + " - " + itineraryPreview.getEndDate());
        holder.daysIntervalTv.setText(itineraryPreview.getDayInterval() + " days trip");

        StorageReference cityReference = mCityStorageReference.child(itineraryPreview.getCity()+".jpg");
        Log.d(Constant.LOG_TAG, itineraryPreview.getCity()+".jpg");

        //handle error with .error and put a default picture.
        Glide.with(holder.imageViewIcon.getContext()).using(new FirebaseImageLoader()).load(cityReference).error(R.drawable.question_mark).into(holder.imageViewIcon);

    }

    @Override
    public int getItemCount() {
        return mItineraryPreviews.size();
    }


}