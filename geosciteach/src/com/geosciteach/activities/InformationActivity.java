package com.geosciteach.activities;

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
 *  Creative Commons Attribution-ShareAlike 3.0 Unported License,
 *  provided that the above copyright notice and this permission notice
 *  is included in all copies or substantial portions of the software.
 *
 *  Author: George Sin
 */

/**
 * The class InformationActivity which provides access to Kewpaedia, Google,
 * Wikipedia and Teacher Suggestions sections of the application.
 */
public class InformationActivity extends GeoSciTeachBaseActivity {

	/**
	 * This method overrides onCreate(...) in Activity. Set layout views and
	 * button related to starting Kewpaedia, Google, Wikipedia and Teacher
	 * Suggestions sections of the application.
	 * 
	 * @param savedInstanceState
	 *            - bundle passed to this Activity.
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information);

		Button kewButton = (Button) findViewById(R.id.kewpaedia_button);

		kewButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				kewpaediaOnClick(v);
			}
		});

		Button googleButton = (Button) findViewById(R.id.google_button);

		googleButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				googleOnClick(v);
			}
		});

		Button wikiButton = (Button) findViewById(R.id.wikipedia_button);

		wikiButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				wikipediaOnClick(v);
			}
		});

		Button youTubeButton = (Button) findViewById(R.id.youtube_button);

		youTubeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				youTubeOnClick(v);
			}
		});

		Button teacherSuggestionsButton = (Button) findViewById(R.id.teacher_suggestions_button);

		teacherSuggestionsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				teacherSuggestionOnClick(v);
			}
		});
	}
	
	/**
	 * Method to start web browser with youtube page.
	 * 
	 * @param button - the View associated to the button pressed.
	 */
	public void youTubeOnClick(View button) {
		Uri uri = Uri.parse("http://www.youtube.com/");
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}
	
	/**
	 * Method to start web browser with google page.
	 * 
	 * @param button - the View associated to the button pressed.
	 */
	public void googleOnClick(View button) {
		Uri uri = Uri.parse("http://www.google.co.uk/");
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}
	
	/**
	 * Method to start web browser with wikipedia page.
	 * 
	 * @param button - the View associated to the button pressed.
	 */
	public void wikipediaOnClick(View button) {
		Uri uri = Uri.parse("http://en.wikipedia.org/wiki/Main_Page");
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}
	
	/**
	 * Method to start web browser with kewpaedia page.
	 * 
	 * @param button - the View associated to the button pressed.
	 */
	public void kewpaediaOnClick(View button) {
		Uri uri = Uri.parse("http://www.kew.org/plants-fungi/index.htm");
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}
	
	/**
	 * Method to start TeachersInfoActivity Activity.
	 * 
	 * @param button - the View associated to the button pressed.
	 */
	public void teacherSuggestionOnClick(View button) {
		Intent intent = new Intent(InformationActivity.this,
				TeachersInfoActivity.class);

		startActivity(intent);
	}
}