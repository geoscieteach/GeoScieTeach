package com.geosciteach.activities;

import java.io.File;

import com.geosciteach.utils.FileUtils;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/*
 *  GeoSciTeach Android App
 *  Copyright (C) 2011 Institute of Education, University of London.
 *
 *  The contents of this file may be used under the terms of the
 *  Creative Commons Attribution-ShareAlike 3.0 Unported License,
 *  provided that the above copyright notice and this permission notice
 *  is included in all copies or substantial portions of the software.
 *
 *  Author: George Sin
 */
public class AmbientDataActivity extends GeoSciTeachBaseActivity{
	
	private final String DELIMITER = ";";
	private final String NEW_SPACE = "\r\n";
	
	private Spinner mTemperatureSpinner;
	private Spinner mHumiditySpinner;
	private Spinner mSoilSpinner;
	private Spinner mPlacesSpinner;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.ambientdata);
		
		// Setup temperature
		mTemperatureSpinner = (Spinner) findViewById(R.id.temperatureSpinner);

		ArrayAdapter<CharSequence> temperatureAdapter = ArrayAdapter
				.createFromResource(this, R.array.temperature_array,
						android.R.layout.simple_spinner_item);
		temperatureAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mTemperatureSpinner.setAdapter(temperatureAdapter);
		
		// Setup humidity
		mHumiditySpinner = (Spinner) findViewById(R.id.humiditySpinner);

		ArrayAdapter<CharSequence> humidityAdapter = ArrayAdapter
				.createFromResource(this, R.array.humidity_array,
						android.R.layout.simple_spinner_item);
		humidityAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mHumiditySpinner.setAdapter(humidityAdapter);
		
		// Setup soil
		mSoilSpinner = (Spinner) findViewById(R.id.soilSpinner);

		ArrayAdapter<CharSequence> soilAdapter = ArrayAdapter
				.createFromResource(this, R.array.soil_array,
						android.R.layout.simple_spinner_item);
		soilAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mSoilSpinner.setAdapter(soilAdapter);
		
		// Setup places
		mPlacesSpinner = (Spinner) findViewById(R.id.placesSpinner);
		
		ArrayAdapter<CharSequence> placesAdapter = ArrayAdapter
		.createFromResource(this, R.array.country_array,
				android.R.layout.simple_spinner_item);
		
		placesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mPlacesSpinner.setAdapter(placesAdapter);
		
	}
	
	public void onPause() {
		super.onPause();
		
		// save the ambient data...
		
		String stringToWrite;
		
		stringToWrite = this.getString(R.string.temperature) + DELIMITER + mTemperatureSpinner.getItemAtPosition(mTemperatureSpinner.getSelectedItemPosition()).toString() + NEW_SPACE;
		stringToWrite += this.getString(R.string.humidity) + DELIMITER + mHumiditySpinner.getItemAtPosition(mHumiditySpinner.getSelectedItemPosition()).toString() + NEW_SPACE;
		stringToWrite += this.getString(R.string.soil) + DELIMITER + mSoilSpinner.getItemAtPosition(mSoilSpinner.getSelectedItemPosition()).toString() + NEW_SPACE;
		stringToWrite += this.getString(R.string.places) + DELIMITER + mPlacesSpinner.getItemAtPosition(mPlacesSpinner.getSelectedItemPosition()).toString() + NEW_SPACE;
		
		File preparedFile = FileUtils.prepareFileToWriteDetailsTo(this.getApplicationContext(), this.getString(R.string.ambient_file));

		FileUtils.writeDetailsToFile(preparedFile, stringToWrite);
	}
}
