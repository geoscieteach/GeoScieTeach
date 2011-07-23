package com.geosciteach.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.geosciteach.utils.FileUtils;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
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
public class CollectDataActivity extends GeoSciTeachBaseActivity {

	private static final int ACTIVITY_TAKE_PICTURE = 0;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 1;
	public static final String PHOTO_FILE_NAME = "photo_file_name";

	private File mFile = null;
	private Button mCameraButton;
	private Button mAmbientButton;
	private Button mNotesButton;
	private Button mVideoButton;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collect);

		Button mPlantButton = (Button) findViewById(R.id.plant_characteristics);

		mPlantButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				plantClickPressed(v);
			}
		});

		mCameraButton = (Button) findViewById(R.id.camera_button);

		mCameraButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cameraClickPressed(v);
			}
		});

		mAmbientButton = (Button) findViewById(R.id.ambient_data_button);

		mAmbientButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ambientDataClickPressed(v);
			}
		});

		mNotesButton = (Button) findViewById(R.id.notes_button);

		mNotesButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				notesClickPressed(v);
			}
		});

		mVideoButton = (Button) findViewById(R.id.video_button);

		mVideoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				videoClickPressed(v);
			}
		});

		Button plantZoneButton = (Button) findViewById(R.id.plant_button);

		plantZoneButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				plantZoneClickPressed(v);
			}
		});
	}

	public void plantZoneClickPressed(View button) {
		Intent intent = new Intent(this, PlantZoneActivity.class);
		startActivity(intent);
	}

	public void plantClickPressed(View button) {
		Intent intent = new Intent(this, LeafCameraActivity.class);
		startActivity(intent);
	}

	public void cameraClickPressed(View button) {

		String filename = FileUtils.getUniqueFileNameAtApplicationDirectory(
				this.getString(R.string.my_photo_file),
				this.getApplicationContext());

		mFile = new File(FileUtils.getApplicationDirectory(this
				.getApplicationContext())
				+ FileUtils.getUniqueUser(this)
				+ filename);

		Uri outputFileUri = Uri.fromFile(mFile);

		Intent intent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		startActivityForResult(intent, ACTIVITY_TAKE_PICTURE);
	}

	public void videoClickPressed(View button) {

		mFile = new File(FileUtils.getApplicationDirectory(this)
				+ FileUtils.getUniqueUser(this)
				+ this.getString(R.string.my_video_file));

		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
		intent.putExtra("android.intent.extra.durationLimit", 30000);

		startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		super.onActivityResult(requestCode, resultCode, intent);

		if (requestCode == ACTIVITY_TAKE_PICTURE && resultCode == RESULT_OK) {

			Intent previewIntent = new Intent(CollectDataActivity.this,
					PreviewActivity.class);
			previewIntent.putExtra(PHOTO_FILE_NAME, mFile.getAbsolutePath());
			startActivity(previewIntent);
		}
		if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE
				&& resultCode == RESULT_OK) {

			try {
				AssetFileDescriptor videoAsset = getContentResolver()
						.openAssetFileDescriptor(intent.getData(), "r");
				// ... do something with the AssetFileDescriptor
				// to read the file, create an inputStream

				FileInputStream fileInputStream = videoAsset
						.createInputStream();

				File tmpFile = new File(FileUtils.getApplicationDirectory(this
						.getApplicationContext())
						+ FileUtils.getUniqueUser(this),
						this.getString(R.string.my_video_file));
				FileOutputStream fos = new FileOutputStream(tmpFile);

				byte[] buf = new byte[1024];
				int len;
				while ((len = fileInputStream.read(buf)) > 0) {
					fos.write(buf, 0, len);
				}
				fileInputStream.close();
				fos.close();

			} catch (FileNotFoundException e) {

				Toast.makeText(getBaseContext(),
						this.getString(R.string.error_video_1),
						Toast.LENGTH_LONG).show();

				e.printStackTrace();

			} catch (Exception e) {

				Toast.makeText(getBaseContext(),
						this.getString(R.string.error_video_2),
						Toast.LENGTH_LONG).show();

				e.printStackTrace();
			}
		}
	}

	public void ambientDataClickPressed(View button) {
		Intent intent = new Intent(CollectDataActivity.this,
				AmbientDataActivity.class);
		startActivity(intent);
	}

	public void notesClickPressed(View button) {
		Intent intent = new Intent(CollectDataActivity.this,
				NotesActivity.class);
		startActivity(intent);
	}
}