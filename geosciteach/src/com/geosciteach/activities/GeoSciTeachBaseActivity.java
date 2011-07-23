package com.geosciteach.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
public class GeoSciTeachBaseActivity extends Activity{
	
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.global_menu, menu);
	   
	    if (this instanceof QuestionActivity){
	    	menu.findItem(R.id.menu_1).setEnabled(false);
	    }
	    else if(this instanceof CollectDataActivity){
	    	menu.findItem(R.id.menu_2).setEnabled(false);
	    }
	    else if(this instanceof InformationActivity){
	    	menu.findItem(R.id.menu_3).setEnabled(false);
	    }
	    else if(this instanceof DataFilesActivity){
	    	menu.findItem(R.id.menu_5).setEnabled(false);
	    }
	    else if(this instanceof ShareActivity){
	    	menu.findItem(R.id.menu_6).setEnabled(false);
	    }
	    
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	    case R.id.menu_1:
	        Intent questionIntent = new Intent(GeoSciTeachBaseActivity.this, QuestionActivity.class);
	        startActivity(questionIntent);
	        return true;
	    case R.id.menu_2:
	    	Intent collectDataIntent = new Intent(GeoSciTeachBaseActivity.this, CollectDataActivity.class);
	        startActivity(collectDataIntent);
	        return true;
	    case R.id.menu_3:
	    	Intent informationIntent = new Intent(GeoSciTeachBaseActivity.this, InformationActivity.class);
	        startActivity(informationIntent);
	        return true;
	    case R.id.menu_4:
	    	Intent mapIntent = new Intent(GeoSciTeachBaseActivity.this, EarthMapActivity.class);
	        startActivity(mapIntent);
	        return true;        
	    case R.id.menu_5:
	    	Intent dataIntent = new Intent(GeoSciTeachBaseActivity.this, DataFilesActivity.class);
	        startActivity(dataIntent);
	        return true;
	    	
	    case R.id.menu_6:
	    	Intent intent = new Intent(GeoSciTeachBaseActivity.this, ShareActivity.class);	      
			startActivity(intent);
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
}