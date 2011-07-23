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
public class ShareActivity extends GeoSciTeachBaseActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.share);

		Button twitterButton = (Button) findViewById(R.id.twitter_button);

		twitterButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				twitterOnClick(v);
			}
		});
		
		Button wallWisherButton = (Button) findViewById(R.id.wallwisher_button);

		wallWisherButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				wallWisherOnClick(v);
			}
		});
		
		Button tumblerButton = (Button) findViewById(R.id.tumbler_button);

		tumblerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				tumblerButtonOnClick(v);
			}
		});
		
	}
	
	public void tumblerButtonOnClick(View button){
		Uri uri = Uri.parse( "http://geosciteach.tumblr.com/" );
		startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
		
	}
	
	public void wallWisherOnClick(View button) {
		Uri uri = Uri.parse( "http://www.wallwisher.com/wall/GeoSciTeach" );
		startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
	}

	public void twitterOnClick(View button) {
		Uri uri = Uri.parse("http://twitter.com/#!/geosciteach");
		startActivity(new Intent(Intent.ACTION_VIEW, uri));
	}
}