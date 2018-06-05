package com.mad.madproject.model;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;
import com.mad.madproject.utils.RandomCollection;

import java.util.List;

/**
 * Created by limyandivicotrico on 5/30/18.
 */

/**
 * The Place class is a POJO Model retrieved to define the place retrieved from web service.
 */
public class Place {
    @SerializedName("geometry")
    private Geometry mGeometry;
    @SerializedName("icon")
    private String mIcon;
    @SerializedName("name")
    private String mName;
    @SerializedName("vicinity")
    private String mVicinity;

    private int timeToGo;

    private String placeType;
//    @BindingAdapter({"bind:imageUrl"})
//    public static void loadImage(ImageView view, String url) {
//        Glide.with(view.getContext()).load(url)
//                .crossFade()
//                .fitCenter()
//                .into(view);
//    }

    public Geometry getGeometry() {
        return mGeometry;
    }

    public void setGeometry(Geometry geometry) {
        this.mGeometry = geometry;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        this.mIcon = icon;
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getVicinity() {
        return mVicinity;
    }

    public void setVicinity(String vicinity) {
        this.mVicinity = vicinity;
    }

    public int getTimeToGo() {
        return timeToGo;
    }

    public void setTimeToGo(int timeToGo) {
        this.timeToGo = timeToGo;
    }

    /**
     * get the place type by using the algorithm collection.
     * @return the place type
     */
    public String getPlaceType() {
        RandomCollection rc;
        //TODO: Suggest to food places at 11 a.m.
        switch (timeToGo) {
            case 9:
                rc = new RandomCollection().add(40, "park").add(40, "amusement_park").add(20, "cafe");
                return rc.next();
            case 11:
                rc = new RandomCollection().add(30, "department_store").add(30, "aquarium")
                        .add(10, "convenience_store").add(30, "art_gallery");
                return rc.next();
            case 13:
                rc = new RandomCollection().add(40, "aquarium").add(30, "city_hall")
                        .add(30, "shopping_mall");
                return rc.next();
            case 15:
                rc = new RandomCollection().add(40, "movie_theater").add(10, "home_goods_store")
                        .add(50, "zoo");
                return rc.next();
            case 17:
                rc = new RandomCollection().add(100, "restaurant");
                return rc.next();
            case 19:
                rc = new RandomCollection().add(50, "casino").add(30, "night_club").add(20, "spa");
                return rc.next();
            default:
                //return park if there is an error in time.
                return "park";
        }
    }
}
