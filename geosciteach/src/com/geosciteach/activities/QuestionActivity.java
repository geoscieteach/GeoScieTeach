package com.geosciteach.activities;

import android.content.Intent;
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
public class QuestionActivity extends GeoSciTeachBaseActivity{
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        
        Button questionButton = (Button) findViewById(R.id.question_button);
        
        questionButton.setOnClickListener(new View.OnClickListener() {

		    @Override
		    public void onClick(View v) {
		    	questionClickPressed(v);
		    }
		});
        
        Button backToKewButton = (Button) findViewById(R.id.back_to_kew_button);
        
        backToKewButton.setOnClickListener(new View.OnClickListener() {

		    @Override
		    public void onClick(View v) {
		    	backToKewMapOnClickPressed(v);
		    }
		});
    }
    
    public void questionClickPressed(View button) {
    	
    	Intent intent = new Intent(QuestionActivity.this,
				TeachersInfoActivity.class);	      
		
		startActivity(intent);
    }
    
	public void backToKewMapOnClickPressed(View button) {
		
		Intent intent = new Intent(QuestionActivity.this,
				GeoSciTeachMapActivity.class);	      
		
		startActivity(intent);
	}
}