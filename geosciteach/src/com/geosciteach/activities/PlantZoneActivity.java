package com.geosciteach.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
 * The class PlantZoneActivity which allows the user to start a QR reader application.
 */
public class PlantZoneActivity extends GeoSciTeachBaseActivity {

	public final static String HTTP = "http";
	
	private static final int ACTIVITY_SCAN_QR = 0;
	private String contents;

	private Button mScanQRButton;
	
	/**
	 * This method overrides onCreate(...) in Activity. Set layout views related
	 * to the plant zone activity.
	 * 
	 * @param savedInstanceState
	 *            - bundle passed to this Activity.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.plantzone);

		mScanQRButton = (Button) findViewById(R.id.scan_qr_code);

		mScanQRButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ambientDataOnClick(v);
			}
		});
	}
	
	/**
	 * Method which starts the QR reader application.
	 * 
	 * @param button - the View associated to the button pressed.
	 */
	public void ambientDataOnClick(View button) {
		try {

			Intent intent = new Intent("com.google.zxing.client.android.SCAN");

			intent.setPackage("com.google.zxing.client.android");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");

			startActivityForResult(intent, ACTIVITY_SCAN_QR);
		} catch (ActivityNotFoundException activity) {
			qrBarcodeScannerRequired(this);
		}
	}

	/**
	 * Display a message stating that QR Barcode Scanner is required.
	 * 
	 * @param activity - the activity used with the AlertDialog.
	 */
	public static void qrBarcodeScannerRequired(final Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(activity.getString(R.string.qrdroid_missing))
				.setCancelable(true)
				.setNegativeButton(activity.getString(R.string.cancel),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						})
				.setPositiveButton(activity.getString(R.string.from_market),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								activity.startActivity(new Intent(
										Intent.ACTION_VIEW,
										Uri.parse(activity
												.getString(R.string.url_market))));
							}
						})
				.setNeutralButton(activity.getString(R.string.direct),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								activity.startActivity(new Intent(
										Intent.ACTION_VIEW,
										Uri.parse(activity
												.getString(R.string.url_direct))));
							}
						});
		builder.create().show();
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

		if (requestCode == ACTIVITY_SCAN_QR && resultCode == RESULT_OK) {

			contents = intent.getStringExtra("SCAN_RESULT");

			if (contents.toLowerCase().startsWith(HTTP)) {

				Uri uri = Uri.parse(contents);
				startActivity(new Intent(Intent.ACTION_VIEW, uri));
			} else {

				AlertDialog.Builder qrMessageBuilder = new AlertDialog.Builder(
						this);
				qrMessageBuilder
						.setMessage(contents)
						.setCancelable(false)
						.setTitle(this.getString(R.string.qr_text))
						.setNeutralButton(this.getString(R.string.ok),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

									}
								});

				AlertDialog qrMessageAlert = qrMessageBuilder.create();
				qrMessageAlert.show();
			}
		}
	}
}