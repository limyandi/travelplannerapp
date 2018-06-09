package com.mad.madproject.addtripdetails;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.mad.madproject.model.ItineraryPreview;
import com.mad.madproject.utils.Constant;
import com.mad.madproject.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * View Model to present the add trip activity.
 */
public class AddTripDetailsViewModel extends ViewModel {

    public final ObservableField<String> startDate = new ObservableField<>();
    public final ObservableField<String> endDate = new ObservableField<>();
    public final ObservableField<String> tripName = new ObservableField<>();

    /**
     * View Model constructor
     */
    public AddTripDetailsViewModel() {

        //set the initial start date and end date when trip details view model is created.
        setInitialStartDate();
        setInitialEndDate();
    }

    public ItineraryPreview onNextClick(String cityName) {
        ItineraryPreview itineraryData = new ItineraryPreview(tripName.get(), cityName, Util.getUserUid(), "", startDate.get(), endDate.get());
        return itineraryData;
    }

    public void setInitialTripName(String cityName) {
        tripName.set("Trip to " + cityName);
    }


    /**
     * Set the initial start date.
     */
    private void setInitialStartDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT, Locale.US);//formating according to my need

        Date today = new Date();
        startDate.set(formatter.format(today));
    }

    //TODO: Might be wrong, we are accessing the UI Directly here.
    /**
     * Set the initial end date.
     */
    private void setInitialEndDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(Constant.DATE_FORMAT, Locale.US);//formating according to my need

        Date today = new Date();
        Date oneDayAfter = new Date(today.getTime() + (Constant.ONE_DAY));
        endDate.set(formatter.format(oneDayAfter));
    }
}
