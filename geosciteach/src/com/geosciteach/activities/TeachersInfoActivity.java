package com.geosciteach.activities;

import java.io.File;

import com.geosciteach.utils.FileUtils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
public class TeachersInfoActivity extends GeoSciTeachBaseActivity {
	
	//private final String mLink = "http://www.youtube.com/user/GeoSciTeachproject?feature=mhee";
	private final String mLink = "http://m.youtube.com/?client=mv-google&rdm=4n18m1jgr#/my_favorites";
	
	public static final String TEACHERINFO = "TEACHERINFO";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.teacher);
		
		Button mYouTubeButton = (Button) findViewById(R.id.youtubecachbutton);
		
		mYouTubeButton.setOnClickListener(new View.OnClickListener() {

		    @Override
		    public void onClick(View v) {
		    	youTubeClickPressed(v);
		    }
		});
	}
	
	public void youTubeClickPressed(View button) {
		
		Uri uri = Uri.parse(mLink);
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}
	
	protected void onResume() {
		super.onResume();

		// Open Save data...
		
		EditText ideaandprompt = (EditText) findViewById(R.id.ideaandpromptsedittext);

		SharedPreferences settings = getSharedPreferences(TEACHERINFO, 0);
		ideaandprompt.setText(settings.getString(TEACHERINFO, ""));
	}
	
	public void onPause() {
		super.onPause();
		
		// save the teacher data...
		
		EditText ideaandprompt = (EditText) findViewById(R.id.ideaandpromptsedittext);
		
		SharedPreferences settings = getSharedPreferences(TEACHERINFO, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(TEACHERINFO, ideaandprompt.getText().toString());
		editor.commit();
		
		String stringToWrite = ideaandprompt.getText().toString();
		
		File preparedFile = FileUtils.prepareFileToWriteDetailsTo(this.getApplicationContext(), this.getString(R.string.teachers_notes_file));

		FileUtils.writeDetailsToFile(preparedFile, stringToWrite);
	}
}