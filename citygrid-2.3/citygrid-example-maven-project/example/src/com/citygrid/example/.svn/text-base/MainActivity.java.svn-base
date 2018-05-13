package com.citygrid.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
	/**
	 * Event handler for clicking the Go! button.
	 * @param view
	 */
	public void goGetSushi(View view) {
		Toast.makeText(MainActivity.this, "Let's go get some sushi!", Toast.LENGTH_LONG).show();
		EditText zipCodeView = (EditText) findViewById(R.id.zipCode);
		String zipCode = zipCodeView.getText().toString();
		Intent i = new Intent(this, List.class);
		i.putExtra("zipCode", zipCode);
		startActivity(i);
	}
}