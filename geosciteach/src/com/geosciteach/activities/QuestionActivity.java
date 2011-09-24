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
 *  GNU GENERAL PUBLIC LICENSE (GPL) Version 3,
 *  provided that the above copyright notice and this permission notice
 *  is included in all copies or substantial portions of the software.
 *
 *  Author: George Sin
 */

/**
 * The class QuestionActivity which displays a Question to the user.
 */
public class QuestionActivity extends GeoSciTeachBaseActivity{
	
	/**
	 * This method overrides onCreate(...) in Activity. Set layout views related
	 * to the Question activity.
	 * 
	 * @param savedInstanceState
	 *            - bundle passed to this Activity.
	 */
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
    
	/**
	 * Method which starts the TeachersInfo Activity.
	 * 
	 * @param button
	 *            - the View associated to the button pressed.
	 */
    public void questionClickPressed(View button) {
    	
    	Intent intent = new Intent(QuestionActivity.this,
				TeachersInfoActivity.class);	      
		
		startActivity(intent);
    }
    
    /**
     * Method with returns the user to the GeoSciTeachMap Activity.
     * 
     * @param button - the View associated to the button pressed.
     */
	public void backToKewMapOnClickPressed(View button) {
		
		Intent intent = new Intent(QuestionActivity.this,
				GeoSciTeachMapActivity.class);	      
		
		startActivity(intent);
	}
}