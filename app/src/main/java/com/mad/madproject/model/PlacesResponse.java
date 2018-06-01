package com.mad.madproject.model;

import java.util.List;

/**
 * Created by limyandivicotrico on 5/30/18.
 */

/**
 * The places response handle the retrieval of the root data from the web service Google Places API.
 */
public class PlacesResponse {
    //store the results retrieved from the web service.
    List<Place> results;

    public List<Place> getResults() {
        return results;
    }

    public void setResults(List<Place> results) {
        this.results = results;
    }
}
