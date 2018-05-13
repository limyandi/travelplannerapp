/*
 * Created by Christophe VONG
 * Copyright 2011 CityGrid Media, LLC All rights reserved.
 */

package com.citygrid.example;

import com.citygrid.CityGrid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Parameters extends Activity {
	
	String category;
	Boolean simulation;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getIntent().getExtras().getString("category");
        simulation = getIntent().getExtras().getBoolean("simulation");

        setContentView(R.layout.parameters);
        if(category.equalsIgnoreCase("Reviews")){
        	ToggleButton sortButton = (ToggleButton) findViewById(R.id.sortButton);
        	sortButton.setClickable(true);
        	sortButton.setVisibility(0);
        }
    }
    
	/**
	 * Event handler for clicking the Go! button.
	 * @param view
	 */
	public void goSearch(View view) {
		Toast.makeText(Parameters.this, "Looking for results...", Toast.LENGTH_LONG).show();
		EditText zipCodeView = (EditText) findViewById(R.id.zipCode);
		EditText whatView = (EditText) findViewById(R.id.what);
		ToggleButton sortButton = (ToggleButton) findViewById(R.id.sortButton);
		String zipCode = zipCodeView.getText().toString();
		String what = whatView.getText().toString();
		Intent i = new Intent(this, List.class);
		i.putExtra("category", category);
		i.putExtra("zipCode", zipCode);
		i.putExtra("what", what);
		i.putExtra("sort", sortButton.isChecked());
		i.putExtra("simulation",simulation);
		startActivity(i);
	}
}