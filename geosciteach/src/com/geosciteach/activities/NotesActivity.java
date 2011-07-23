package com.geosciteach.activities;

import java.io.File;

import com.geosciteach.utils.FileUtils;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

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
public class NotesActivity extends GeoSciTeachBaseActivity {

	public static final String NOTES = "NOTES";
	private EditText notesEditText;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.notes);

		notesEditText = (EditText) findViewById(R.id.noteedittext);
	}

	protected void onResume() {
		super.onResume();

		// Open Save data...
		SharedPreferences settings = getSharedPreferences(NOTES, 0);
		notesEditText.setText(settings.getString(NOTES, ""));
	}

	public void onPause() {
		super.onPause();

		// Save data...
		SharedPreferences settings = getSharedPreferences(NOTES, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(NOTES, notesEditText.getText().toString());

		// Commit the edits!
		editor.commit();

		// Write to file...
		File preparedFile = FileUtils.prepareFileToWriteDetailsTo(this.getApplicationContext(), this.getString(R.string.notes_file));
		FileUtils.writeDetailsToFile(preparedFile, notesEditText.getText().toString());
	}
}