package com.geosciteach.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.geosciteach.activities.PasswordEntryActivity;
import com.geosciteach.activities.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
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

/**
 * The class FileUtils which provides static methods all related to file
 * creation and access.
 */
public class FileUtils {

	/**
	 * Method to check the availability of the external storage of device.
	 * Whether the external storage has read or write attributes or not.
	 * 
	 * @param baseContext
	 *            - the Context which a toast message can be displayed to.
	 * @return a boolean stating whether read or write access to available
	 *         (true) or not (false)
	 */
	public static boolean checkExternalStorageReadAndWritable(
			Context baseContext) {

		boolean mExternalStorageWritable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageWritable = true;
		}

		if (!mExternalStorageWritable) {
			Toast.makeText(baseContext,
					baseContext.getString(R.string.no_external_writable),
					Toast.LENGTH_SHORT).show();
		}

		return mExternalStorageWritable;
	}

	/**
	 * Method to check the availability of the external storage of device.
	 * Whether the external storage has read attributes or not.
	 * 
	 * @param baseContext
	 *            - the Context which a toast message can be displayed to.
	 * @return a boolean stating whether read access to available (true) or not
	 *         (false)
	 */
	public static boolean checkExternalStorageReadable(Context baseContext) {

		boolean mExternalStorageAvailable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)
				|| Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read the media
			mExternalStorageAvailable = true;
		}

		if (!mExternalStorageAvailable) {
			Toast.makeText(baseContext,
					baseContext.getString(R.string.no_external_readable),
					Toast.LENGTH_SHORT).show();
		}

		return mExternalStorageAvailable;
	}

	/**
	 * Method to get the application directory of the user project.
	 * 
	 * @param baseContext
	 *            - the Context which related to application
	 * @return The application directory.
	 */
	public static String getApplicationDirectory(Context baseContext) {

		String dir = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ baseContext.getString(R.string.geosciteach_directory);

		File directoryToSearch = new File(dir);

		try {
			// create directory if required...
			directoryToSearch.mkdirs();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dir;
	}

	/**
	 * 
	 * Method to get the unique filename at the application directory. If there
	 * is already a filename which exists at the directory will increment the
	 * filename until a filename which does not exist is found.
	 * 
	 * @param filename
	 *            - the filename which requires a unique filename to be found.
	 * @param baseContext
	 *            - the Context which related to application.
	 * @return The unique filename found.
	 */
	public static String getUniqueFileNameAtApplicationDirectory(
			String filename, Context baseContext) {

		String fileNameWithOutExtension = filename;
		String fileExtension = "";
		int counter = 0;

		int extension = filename.lastIndexOf(".");

		if (extension != -1) {
			fileNameWithOutExtension = filename.substring(0, extension);
			fileExtension = filename.substring(extension);
		}

		String dir = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ baseContext.getString(R.string.geosciteach_directory);

		if (getUniqueUser(baseContext) != null) {
			dir += getUniqueUser(baseContext);
		}

		File file = new File(dir, filename);

		while (file.exists()) {
			counter++;
			file = new File(dir, fileNameWithOutExtension + counter
					+ fileExtension);
		}

		return file.getName();
	}

	/**
	 * Method to write content to a file.
	 * 
	 * @param fileToWriteTo
	 *            - The file to write details to.
	 * @param detailsToWrite
	 *            - Content details to write to file.
	 */
	public static void writeDetailsToFile(File fileToWriteTo,
			String detailsToWrite) {

		try {

			OutputStream outputStream = new FileOutputStream(fileToWriteTo);

			if (fileToWriteTo.exists()) {

				PrintWriter printWriter = new PrintWriter(outputStream);

				printWriter.print(detailsToWrite);

				printWriter.close();
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to get reference to File object from providing a filename.
	 * 
	 * @param context
	 *            - the Context which related to application.
	 * @param filename
	 *            - the file name which is to be create in the application
	 *            directory.
	 * @return the file to created from provided file name.
	 */
	public static File prepareFileToWriteDetailsTo(Context context,
			String filename) {
		File fileToWriteTo = null;

		String uniqueUser = getUniqueUser(context);

		if (uniqueUser == null) {

			fileToWriteTo = new File(FileUtils.getApplicationDirectory(context)
					+ filename);
		} else {
			fileToWriteTo = new File(FileUtils.getApplicationDirectory(context)
					+ uniqueUser + filename);
		}

		return fileToWriteTo;

	}

	/**
	 * Method to get unique user which is stored in the share preferences.
	 * 
	 * @param context
	 *            - the Context which related to application.
	 * @return The unique user value.
	 */
	public static String getUniqueUser(Context context) {
		SharedPreferences loginDetails = context.getSharedPreferences(
				PasswordEntryActivity.LOGIN, context.MODE_PRIVATE);
		String uniqueuser = loginDetails.getString(
				PasswordEntryActivity.UNIQUEUSER, null);

		if (uniqueuser != null) {

			uniqueuser += "/";
		}

		return uniqueuser;
	}
}