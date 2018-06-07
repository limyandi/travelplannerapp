package com.mad.madproject.tripdetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTripDetailsViewModel extends ViewModel {

    private MutableLiveData<String> startDateLive;
    private MutableLiveData<String> endDateLive;

    public final ObservableField<String> startDate = new ObservableField<>();
    public final ObservableField<String> endDate = new ObservableField<>();
    public final ObservableField<String> tripName = new ObservableField<>();

    public AddTripDetailsViewModel() {

        startDateLive = new MutableLiveData<>();
        endDateLive = new MutableLiveData<>();

        startDateLive.setValue(startDate.get());
        endDateLive.setValue(endDate.get());
    }

    public ItineraryPreview onNextClick(String cityName) {
        ItineraryPreview itineraryData = new ItineraryPreview(tripName.get(), cityName, Util.getUserUid(), "", startDate.get(), endDate.get());
        return itineraryData;
    }

    public void setInitialTripName(String cityName) {
        tripName.set("Trip to " + cityName);
    }

    public MutableLiveData<String> getStartDateLive() {
        return startDateLive;
    }

    public MutableLiveData<String> getEndDateLive() {
        return endDateLive;
    }

    public void setStartDate() {
        startDateLive.postValue(startDate.get());
    }


    public Date setAndGetInitialStartDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");//formating according to my need

        Date today = new Date();
        startDate.set(formatter.format(today));

        return today;
    }

    public Date setAndGetInitialEndDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");//formating according to my need

        Date today = new Date();
        Date oneDayAfter = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        endDate.set(formatter.format(oneDayAfter));

        return oneDayAfter;
    }
}
