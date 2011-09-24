package com.geosciteach.activities;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.geosciteach.utils.FileUtils;

/*
 *  GeoSciTeach Android App
 *  Copyright (C) 2011 Institute of Education, University of London.
 *
 *  The contents of this file may be used under the terms of the
 *  GNU GENERAL PUBLIC LICENSE (GPL) Version 3,
 *  provided that the above copyright notice and this permission notice
 *  is included in all copies or substantial portions of the software.
 *
 *  Author: George Sin
 */

/**
 * The class LeafCameraActivity which provides the Leaf Camera section of the
 * application. The activity provides leaf images which can be iterated through
 * while the devices camera is active.
 * 
 */
public class LeafCameraActivity extends GeoSciTeachBaseActivity {
	private SurfaceView preview = null;
	private SurfaceHolder previewHolder = null;
	private Camera camera = null;
	private boolean inPreview = false;
	private File mPhoto;

	private ArrayList<Integer> mLeafList = new ArrayList<Integer>();

	private ImageView mLeafImageView;
	private int mCurrentPos;

	/**
	 * This method overrides onCreate(...) in Activity. Set layout views related
	 * to the Leaf Camera activity.
	 * 
	 * @param savedInstanceState
	 *            - bundle passed to this Activity.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cameramain);

		preview = (SurfaceView) findViewById(R.id.preview);
		previewHolder = preview.getHolder();
		previewHolder.addCallback(surfaceCallback);
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		mLeafImageView = (ImageView) findViewById(R.id.leafImageView);

		Button next = (Button) findViewById(R.id.nextButton);

		next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nextOnClick(v);
			}
		});

		Button pre = (Button) findViewById(R.id.preButton);

		pre.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				preOnClick(v);
			}
		});

		Button capture = (Button) findViewById(R.id.captureButton);

		capture.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				buttonClickPressed(v);
			}
		});

		// load the list of images
		loadResources();

		mCurrentPos = 0;

		mLeafImageView.setImageResource(mLeafList.get(mCurrentPos));
		mLeafImageView.refreshDrawableState();

	}

	/**
	 * Method to load each of the leaf images to an Array List.
	 */
	private void loadResources() {
		mLeafList.add(R.drawable.acicular);
		mLeafList.add(R.drawable.alternate);
		mLeafList.add(R.drawable.cordate);
		mLeafList.add(R.drawable.evenpinnate);
		mLeafList.add(R.drawable.linear);
		mLeafList.add(R.drawable.oddpinnate);
		mLeafList.add(R.drawable.orbicular);
		mLeafList.add(R.drawable.ovate);
		mLeafList.add(R.drawable.palmate);
		mLeafList.add(R.drawable.rhomboid);
		mLeafList.add(R.drawable.rosette);
	}

	/**
	 * Method to iterate next through the leafs in Leaf Array List.
	 * 
	 * @param v
	 *            - The view that was clicked.
	 */
	public void nextOnClick(View v) {

		if (mCurrentPos + 1 <= mLeafList.size() - 1) {
			mCurrentPos++;

			mLeafImageView.setImageResource(mLeafList.get(mCurrentPos));
			mLeafImageView.refreshDrawableState();
		}
	}

	/**
	 * Method to iterate previous through the leafs in Leaf Array List.
	 * 
	 * @param v
	 *            - The view that was clicked.
	 */
	public void preOnClick(View v) {

		if (mCurrentPos - 1 >= 0) {
			mCurrentPos--;
			mLeafImageView.setImageResource(mLeafList.get(mCurrentPos));
			mLeafImageView.refreshDrawableState();
		}
	}

	/**
	 * This method overrides onResume(...) in Activity. Opens the camera object.
	 */
	@Override
	public void onResume() {
		super.onResume();

		camera = open();
	}

	/**
	 * This method calls opens on the camera activity, which creates a new
	 * Camera object to access the first back-facing camera on the device
	 * 
	 * @return Camera object opened. 
	 */
	public Camera open() {
		return (Camera.open());
	}

	/**
	 * This method overrides onPause(...) in Activity. Stops capturing from
	 * camera and releases the camera object.
	 */
	@Override
	public void onPause() {
		if (inPreview) {
			camera.stopPreview();
		}

		camera.release();
		camera = null;
		inPreview = false;

		super.onPause();
	}
	
	/**
	 * Method which takes a picture using the camera object.
	 * 
	 * @param button - The view that was clicked.
	 */
	public void buttonClickPressed(View button) {
		camera.takePicture(null, null, photoCallback);
		inPreview = false;
	}
	
	/**
	 * This method overrides onKeyDown(...) in Activity. 
	 * 
	 * @param keyCode
	 *            - The value in event.getKeyCode().
	 * @param event
	 *            - Description of the key event.
	 * 
	 * @return true to prevent this event from being propagated further, or
	 *         false to indicate that you have not handled this event and it
	 *         should continue to be propagated.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return (super.onKeyDown(keyCode, event));
	}

	/**
	 * Method to get the preview size of the image.
	 * 
	 * @param width - The new width of the surface. 
	 * @param height - The new height of the surface. 
	 * @param parameters - Current settings of camera service.
	 * @return Camera Size of Preview Image.
	 */
	private Camera.Size getBestPreviewSize(int width, int height,
			Camera.Parameters parameters) {

		return (parameters.getPreviewSize());
	}
	
	/**
	 * Method to start the preview activity.
	 */
	public void goToPreviewActivity() {
		Intent previewIntent = new Intent(LeafCameraActivity.this,
				PreviewActivity.class);
		previewIntent.putExtra(CollectDataActivity.PHOTO_FILE_NAME,
				mPhoto.getAbsolutePath());
		startActivity(previewIntent);
	}

	
	SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				camera.setPreviewDisplay(previewHolder);
			} catch (Throwable t) {
				Toast.makeText(LeafCameraActivity.this, t.getMessage(),
						Toast.LENGTH_LONG).show();
			}
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			Camera.Parameters parameters = camera.getParameters();
			Camera.Size size = getBestPreviewSize(width, height, parameters);

			if (size != null) {
				parameters.setPreviewSize(size.width, size.height);
				parameters.setPictureFormat(PixelFormat.JPEG);

				camera.setParameters(parameters);
				camera.startPreview();
				inPreview = true;
			}
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			// no-op
		}
	};

	Camera.PictureCallback photoCallback = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			new SavePhotoTask().execute(data);
			inPreview = true;
		}
	};
	
	/**
	 * 
	 * The class SavePhotoTask which saves the photo to file. This is done asynchronously.
	 *
	 */
	class SavePhotoTask extends AsyncTask<byte[], String, String> {
		@Override
		protected String doInBackground(byte[]... jpeg) {

			String filename = FileUtils
					.getUniqueFileNameAtApplicationDirectory(
							LeafCameraActivity.this
									.getString(R.string.leaf_photo_file),
							LeafCameraActivity.this.getApplicationContext());

			mPhoto = new File(
					FileUtils.getApplicationDirectory(LeafCameraActivity.this
							.getApplicationContext())
							+ FileUtils.getUniqueUser(LeafCameraActivity.this)
							+ filename);

			if (mPhoto.exists()) {
				mPhoto.delete();
			}

			try {
				FileOutputStream fos = new FileOutputStream(mPhoto.getPath());

				fos.write(jpeg[0]);
				fos.close();

				goToPreviewActivity();
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}

			return (null);
		}
	}
}