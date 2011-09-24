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
 *  GNU GENERAL PUBLIC LICENSE (GPL) Version 3,
 *  provided that the above copyright notice and this permission notice
 *  is included in all copies or substantial portions of the software.
 *
 *  Author: George Sin
 */

/**
 * 
 * The class GeoSciTeachBaseActivity which provides a base Activity for other activities.
 * Within this base class the global menu button is defined. 
 * 
 */
public class GeoSciTeachBaseActivity extends Activity{
	
	/**
	 * Called the once by activity to create the menu bar for activity.
	 * 
	 * @param menu
	 *            - the menu to be used.
	 * 
	 * @return return true for the menu to be displayed
	 */
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
	
    /**
	 * This hook is called whenever an item in your options menu is selected.
	 * 
	 * @param item
	 *            - the menu item that was selected.
	 * 
	 * @return boolean Return false to allow normal menu processing to proceed,
	 *         true to consume it here.
	 */
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
}