package com.geosciteach.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
 * The class DataFilesActivity which allows a user to reset the account in use
 * with the application. A "are you sure" message is shown when resetting the
 * account.
 */
public class DataFilesActivity extends GeoSciTeachBaseActivity {

	/**
	 * This method overrides onCreate(...) in Activity. Set layout views and the
	 * onClickListener of the reset button.
	 * 
	 * @param savedInstanceState
	 *            - bundle passed to this Activity.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.datafiles);

		Button mResetButton = (Button) findViewById(R.id.reset_button);

		mResetButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
				AlertDialog.Builder sureBuilder = new AlertDialog.Builder(
						DataFilesActivity.this);
				sureBuilder
						.setMessage(
								DataFilesActivity.this
										.getString(R.string.are_you_sure))
						.setCancelable(false)
						.setTitle(
								DataFilesActivity.this
										.getString(R.string.reset_account))
						.setPositiveButton(
								DataFilesActivity.this.getString(R.string.yes),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										resetClickPressed(v);
									}
								})
						.setNegativeButton(
								DataFilesActivity.this.getString(R.string.no),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// do nothing
									}
								});

				AlertDialog sureAlert = sureBuilder.create();
				sureAlert.show();
			}
		});
	}

	/**
	 * This method resets the application so that a new user folder is created.
	 * 
	 * @param button
	 *            - the View associated to the button pressed.
	 */
	public void resetClickPressed(View button) {

		SharedPreferences settings = getSharedPreferences(
				PasswordEntryActivity.LOGIN, MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();

		settings = getSharedPreferences(EarthMapActivity.MAPPINS, MODE_PRIVATE);
		editor = settings.edit();
		editor.clear();
		editor.commit();

		settings = getSharedPreferences(NotesActivity.NOTES, MODE_PRIVATE);
		editor = settings.edit();
		editor.clear();
		editor.commit();

		settings = getSharedPreferences(TeachersInfoActivity.TEACHERINFO,
				MODE_PRIVATE);
		editor = settings.edit();
		editor.clear();
		editor.commit();

		Intent intent = new Intent(this, PasswordEntryActivity.class);

		startActivity(intent);
	}
}