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
public class PreviewActivity extends GeoSciTeachBaseActivity {

	public static final String PHOTO_FILE_NAME = "photo_file_name";
	public static final String PHOTO_COUNTRY = "photo_country";
	public static final int MAP_ACTIVITY = 1;

	private ImageView mImageViewer;
	private Spinner mCountrySpinner;
	private Bitmap mBitmap;

	private String mFileName;

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

	public void onNewIntent(Intent intent) {

		super.onNewIntent(intent);

		loadPreviewBitmap(intent);
		
		mCountrySpinner.setSelection(0);
	}

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

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		super.onActivityResult(requestCode, resultCode, intent);
		
		if (resultCode == RESULT_OK) {			
			finish();
		}
	}

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

	protected void onDestroy() {
		super.onDestroy();

		mBitmap.recycle();
	}
}