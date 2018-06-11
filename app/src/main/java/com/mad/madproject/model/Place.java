package com.mad.madproject.model;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;
import com.mad.madproject.utils.RandomCollection;
import com.mad.madproject.utils.Util;

import java.io.Serializable;
import java.util.List;

/**
 * Created by limyandivicotrico on 5/30/18.
 */

/**
 * The Place class is a POJO Model retrieved to define the place retrieved from web service.
 */
public class Place implements Serializable {
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
        return Util.getPlaceType(timeToGo);
    }
}
