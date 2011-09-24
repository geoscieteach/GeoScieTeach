package com.geosciteach.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.geosciteach.activities.R;

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
 * Class which provide map bubble pop up for earth map activity.
 */
public class EarthMapBubblePopup {
	private static final String TAG = "MapBubblePopup";

	protected final View mParentView;
	private final PopupWindow mWindow;
	private View mRoot;
	private Drawable mBackground = null;
	private String mTitleText;
	private String mLocationText;
	private String mImageResourcePath;
	private TextView mTitleTextView;
	private final WindowManager mWindowManager;
	private int mTop;

	/**
	 * Constructor of EarthMapBubblePopup.
	 * 
	 * @param parent
	 *            - parent view.
	 * @param titleText
	 *            - title text of bubble pop up.
	 * @param locationText
	 *            - snippet value
	 */
	public EarthMapBubblePopup(View parent, String titleText,
			String locationText) {
		this(parent, null, titleText, locationText);
	}

	/**
	 * Constructor of EarthMapBubblePopup.
	 * 
	 * @param parent
	 *            - parent view.
	 * @param pathToImage
	 *            - path to image for pop up.
	 * @param titleText
	 *            - title text of bubble pop up.
	 * @param locationText
	 *            - snippet value
	 */
	public EarthMapBubblePopup(View parent, String pathToImage,
			String titleText, String locationText) {
		mParentView = parent;
		mImageResourcePath = pathToImage;
		mTitleText = titleText;
		mLocationText = locationText;
		mWindow = new PopupWindow(parent.getContext());

		mWindow.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					EarthMapBubblePopup.this.mWindow.dismiss();
					return true;
				}
				return false;
			}
		});

		mWindowManager = (WindowManager) parent.getContext().getSystemService(
				Context.WINDOW_SERVICE);

		onCreate();
	}

	/**
	 * Method to set layout views related to the pop up.
	 */
	protected void onCreate() {
		LayoutInflater inflater = (LayoutInflater) mParentView.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = (View) inflater.inflate(R.layout.earthmap_popup_button,
				null);

		mTitleTextView = (TextView) view.findViewById(R.id.map_bubble_title);
		mTitleTextView.setText(mTitleText);

		if (mImageResourcePath != null) {
			ImageView mLeftImageView = (ImageView) view
					.findViewById(R.id.map_bubble_pic);

			String path = Environment.getExternalStorageDirectory()
					.getAbsolutePath();

			// scale down here...
			BitmapFactory.Options bfo = new BitmapFactory.Options();
			bfo.inSampleSize = 6;

			Bitmap mBitmap = BitmapFactory.decodeFile(mImageResourcePath, bfo);

			Bitmap scaledBitmap = Bitmap.createScaledBitmap(mBitmap, 128,
					(int) (mBitmap.getHeight() * (128f / mBitmap.getWidth())),
					true);

			mBitmap.recycle();

			mLeftImageView.setImageBitmap(scaledBitmap);
		}

		// set the inflated view as what we want to display
		this.setContentView(view);
	}

	/**
	 * Method which sets click listener of the map bubble.
	 * 
	 * @param listener - onClickListener value.
	 */
	public void setOnClickListener(View.OnClickListener listener) {
		mRoot.setOnClickListener(listener);
	}
	
	/**
	 * Method which sets long click listener of the map bubble.
	 * 
	 * @param listener - onClickListener value.
	 */
	public void setOnLongClickListener(View.OnLongClickListener listener) {
		mRoot.setOnLongClickListener(listener);
	}

	/**
	 * Method which defines layout values before showing the map bubble.
	 */
	private void preShow() {
		if (mRoot == null) {
			throw new IllegalStateException(
					"setContentView was not called with a view to display.");
		}

		if (mBackground == null) {
			mWindow.setBackgroundDrawable(new BitmapDrawable());
		} else {
			mWindow.setBackgroundDrawable(mBackground);
		}

		// if using PopupWindow#setBackgroundDrawable this is the only values of
		// the width and height that make it work
		// otherwise you need to set the background of the root viewgroup
		// and set the popupwindow background to an empty BitmapDrawable
		mWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		mWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		mWindow.setTouchable(true);
		mWindow.setFocusable(true);
		mWindow.setOutsideTouchable(true);

		mWindow.setContentView(this.mRoot);
	}
	
	/**
	 * Method to set the background drawable.
	 * 
	 * @param background - the background drawable to set the background.
	 */
	public void setBackgroundDrawable(Drawable background) {
		mBackground = background;
	}
	
	/**
	 * Method to set the content view.
	 * 
	 * @param root - the content view to set.
	 */
	public void setContentView(View root) {
		mRoot = root;
		mWindow.setContentView(root);
		mTop = mWindowManager.getDefaultDisplay().getHeight()
				- mParentView.getHeight();
	}
	
	/**
	 * Method to set the content view from a layout resource id.
	 * 
	 * @param layoutResID - the content view to set.
	 */
	public void setContentView(int layoutResID) {
		LayoutInflater inflator = (LayoutInflater) this.mParentView
				.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.setContentView(inflator.inflate(layoutResID, null));
	}
	
	/**
	 * Method to set on dismiss listener.
	 * 
	 * @param listener - the OnDismissListener listener.
	 */
	public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
		mWindow.setOnDismissListener(listener);
	}

	/**
	 * Method relating to screen off set required for the pop up.
	 * 
	 * @param point - relating to the pop up
	 * @param xPlacement - relating to the x placement of pop up.
	 * @param yPlacement - relating to the y placement of pop up.
	 */
	public void getScreenOffset(Point point, int xPlacement, int yPlacement) {
		if (point == null) {
			return;
		}
		point.x = 0;
		point.y = 0;

		int parentWidth = mParentView.getWidth();
		int parentHeight = mParentView.getHeight();

		mRoot.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		int bubbleWidth = this.mRoot.getMeasuredWidth();
		int bubbleHeight = this.mRoot.getMeasuredHeight();

		if ((xPlacement + bubbleWidth / 2) > parentWidth) {
			point.x = (xPlacement + bubbleWidth / 2) - parentWidth;
		} else if (xPlacement - bubbleWidth / 2 < 0) {
			point.x = xPlacement - bubbleWidth / 2;
		}

		if ((yPlacement + bubbleHeight / 2) > parentHeight + mTop) {
			point.y = (yPlacement + bubbleHeight / 2) - (parentHeight + mTop);
		} else if (yPlacement - bubbleHeight < 0) {
			point.y = -(yPlacement - bubbleHeight);
		}
		Log.d("Top", "mTop" + mTop);
		Log.d("bub top", "bubtop=" + (yPlacement - bubbleHeight / 2));
		Log.d("Y", "y=" + point.y);
		Log.d("Height", "parentHeight" + parentHeight);
	}
	
	/**
	 * Method to show the pop up window.
	 * 
	 * @param xPos - x position.
	 * @param yPos - y position.
	 */
	public void show(int xPos, int yPos) {
		this.preShow();

		mRoot.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		int rootWidth = this.mRoot.getMeasuredWidth();
		int rootHeight = this.mRoot.getMeasuredHeight();
		mWindow.setAnimationStyle(R.style.Animations_GrowFromBottom);

		mWindow.showAtLocation(this.mParentView, Gravity.NO_GRAVITY, xPos
				- rootWidth / 2, yPos + mTop - rootHeight);
	}
	
	/**
	 * Method to dismiss the pop up window
	 */
	public void dismiss() {
		mWindow.dismiss();
	}
}
