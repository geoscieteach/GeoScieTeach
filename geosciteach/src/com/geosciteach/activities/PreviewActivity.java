package com.geosciteach.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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

/**
 * The class PreviewActivity which shows a image file to preview.
 */
public class PreviewActivity extends GeoSciTeachBaseActivity {

	public static final String PHOTO_FILE_NAME = "photo_file_name";
	public static final String PHOTO_COUNTRY = "photo_country";
	public static final int MAP_ACTIVITY = 1;

	private ImageView mImageViewer;
	private Spinner mCountrySpinner;
	private Bitmap mBitmap;

	private String mFileName;
	
	/**
	 * This method overrides onCreate(...) in Activity. Set layout views related
	 * to the Preview activity.
	 * 
	 * @param savedInstanceState
	 *            - bundle passed to this Activity.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview);

		ImageButton uploadButton = (ImageButton) findViewById(R.id.upload_button);

		uploadButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				uploadButtonClickPressed(v);
			}
		});

		mImageViewer = (ImageView) findViewById(R.id.imageviewer);

		mCountrySpinner = (Spinner) findViewById(R.id.countryspinner);

		ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter
				.createFromResource(this, R.array.country_array,
						android.R.layout.simple_spinner_item);
		countryAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mCountrySpinner.setAdapter(countryAdapter);
		
		loadPreviewBitmap(getIntent());
	}

	/**
	 * Method which is called on re-entry to PreviewActivity.
	 * 
	 * @param intent
	 *            - The intent associated to the Activity.
	 */
	public void onNewIntent(Intent intent) {

		super.onNewIntent(intent);

		loadPreviewBitmap(intent);
		
		mCountrySpinner.setSelection(0);
	}
	
	/**
	 * Method to load the preview bitmap.
	 * 
	 * @param intent - the intent which has extra data relating to photo file name.
	 */
	public void loadPreviewBitmap(Intent intent) {
		mFileName = intent.getStringExtra(
				CollectDataActivity.PHOTO_FILE_NAME);

		if (mFileName != null) {

			// scale down here...
			BitmapFactory.Options bfo = new BitmapFactory.Options();
			bfo.inSampleSize = 2;

			mBitmap = BitmapFactory.decodeFile(mFileName, bfo);

			mImageViewer.setImageBitmap(mBitmap);
		}
	}
	
	/**
	 * This method overrides onActivityResult(...) in Activity. Deals with the
	 * result from Activities started from this class.
	 * 
	 * @param requestCode
	 *            - The integer request code originally supplied to
	 *            startActivityForResult(), allowing you to identify who this
	 *            result came from.
	 * @param resultCode
	 *            - The integer result code returned by the child activity
	 *            through its setResult().
	 * @param intent
	 *            - An Intent, which can return result data to the caller
	 *            (various data can be attached to Intent "extras").
	 */
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		super.onActivityResult(requestCode, resultCode, intent);
		
		if (resultCode == RESULT_OK) {			
			finish();
		}
	}
	
	/**
	 * Method to start the map activity with the image path passed as an extra value in the intent.
	 * 
	 * @param button - the View associated to the button pressed.
	 */
	public void uploadButtonClickPressed(View button) {

		if (mCountrySpinner.getSelectedItemPosition() == 0) {
			Toast.makeText(getApplicationContext(), this.getString(R.string.country_needs_to_be_selected),
					Toast.LENGTH_SHORT).show();
		} else {

			Intent intent = new Intent(PreviewActivity.this,
					EarthMapActivity.class);
			intent.putExtra(PHOTO_FILE_NAME, mFileName);
			intent.putExtra(PHOTO_COUNTRY,
					(String) mCountrySpinner.getSelectedItem());
			
			startActivityForResult(intent, MAP_ACTIVITY); 
		}
	}

	/**
	 * This method overrides onDestroy(...) in Activity. Ensures that the preview bitmap is recycled.
	 */
	protected void onDestroy() {
		super.onDestroy();

		mBitmap.recycle();
	}
}