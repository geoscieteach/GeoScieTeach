package com.geosciteach.activities;

import java.io.File;

import com.geosciteach.utils.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
public class PasswordEntryActivity extends Activity {
	
	public static final String LOGIN="login";
	public static final String LOGINPASSWORD="login_password";
	public static final String UNIQUEUSER="uniqueuser";
	public static final int ACTIVITY_MAP=Menu.FIRST;
	
	private EditText mPasswordEditText;
	private Button mEnterButton;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.passwordentry);
		
		mPasswordEditText = (EditText)findViewById(R.id.password_edittext);
		
		mEnterButton = (Button) findViewById(R.id.enter_button);
		
		mEnterButton.setOnClickListener(new View.OnClickListener() {

		    @Override
		    public void onClick(View v) {
		    	enterButtonOnClick(v);
		    }
		});
	}
	
	protected void onResume(){
		super.onResume();
		
		mPasswordEditText.setText("");
		
		SharedPreferences loginDetails = getSharedPreferences(LOGIN, MODE_PRIVATE);
		boolean password = loginDetails.getBoolean(LOGINPASSWORD, false);
		
		if(password == true){
			startMapView();
		}
	}
	
	public void startMapView(){
		Intent mainMapIntent = new Intent(PasswordEntryActivity.this, GeoSciTeachMapActivity.class);
		startActivityForResult(mainMapIntent, ACTIVITY_MAP);
	}
	
	public void enterButtonOnClick(View button){
		
		if(mPasswordEditText.getText().toString().equals(this.getString(R.string.password))){
			
			String filename = FileUtils.getUniqueFileNameAtApplicationDirectory("userId", this.getApplicationContext());
			
			// write time stamp? or any other user details which may be useful?
			SharedPreferences settings = getSharedPreferences(LOGIN, MODE_PRIVATE);
		    SharedPreferences.Editor editor = settings.edit();
		    editor.putBoolean(LOGINPASSWORD, true);
		    editor.putString(UNIQUEUSER, filename);

		    // Commit the edits!
		    editor.commit();
		    
		    File preparedFile = new File(FileUtils.getApplicationDirectory(this) + filename);
		    
			if(!preparedFile.exists()){
				if(preparedFile.mkdirs()){
					Log.i("FileUtils", "Created directory:" + preparedFile.getAbsoluteFile());
				}
				else{
					Log.i("FileUtils", "UNABLED TO create directory:" + preparedFile.getAbsoluteFile());
				}
			}
		    
			startMapView();
		}
		else{
			Toast.makeText(this, "Password Incorrect...", Toast.LENGTH_SHORT).show();
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		super.onActivityResult(requestCode, resultCode, intent);

		if (resultCode == RESULT_OK) {
			finish();
		}
	}
}