package com.geosciteach.utils;

import com.geosciteach.activities.R;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
public class MapBubblePopup {
	private static final String TAG = "MapBubblePopup";

	protected final View mParentView;
	private final PopupWindow mWindow;
	private View mRoot;
	private Drawable mBackground = null;
	private String mTitleText;
	private String mLocationText;
	private int mLeftImageResourceId;
	private TextView mTitleTextView;
	private TextView mSubTextView;
	private ImageView mLeftImageView;
	private final WindowManager mWindowManager;
	private int mTop;

	public MapBubblePopup(View parent, String titleText, String locationText) {
		this(parent, 0, titleText, locationText);
	}

	public MapBubblePopup(View parent, int leftImageResourceId,
			String titleText, String locationText) {
		mParentView = parent;
		mLeftImageResourceId = leftImageResourceId;
		mTitleText = titleText;
		mLocationText = locationText;
		mWindow = new PopupWindow(parent.getContext());

		mWindow.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					MapBubblePopup.this.mWindow.dismiss();
					return true;
				}
				return false;
			}
		});

		mWindowManager = (WindowManager) parent.getContext().getSystemService(
				Context.WINDOW_SERVICE);

		onCreate();
	}

	protected void onCreate() {
		LayoutInflater inflater = (LayoutInflater) mParentView.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = (View) inflater.inflate(R.layout.map_popup_button, null);

		mTitleTextView = (TextView) view.findViewById(R.id.map_bubble_title);
		mTitleTextView.setText(mTitleText);
		// mSubTextView = (TextView)view.findViewById(R.id.map_bubble_subtext);
		// mSubTextView.setText(mLocationText);
		if (mLeftImageResourceId != 0) {
			// mLeftImageView =
			// (ImageView)view.findViewById(R.id.map_bubble_pic);
			// mLeftImageView.setImageResource(mLeftImageResourceId);
		}

		// set the inflated view as what we want to display
		this.setContentView(view);
	}

	public void setOnClickListener(View.OnClickListener listener) {
		mRoot.setOnClickListener(listener);
	}

	protected void onShow() {
	}

	private void preShow() {
		if (mRoot == null) {
			throw new IllegalStateException(
					"setContentView was not called with a view to display.");
		}
		onShow();

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

	public void setBackgroundDrawable(Drawable background) {
		mBackground = background;
	}

	public void setContentView(View root) {
		mRoot = root;
		mWindow.setContentView(root);
		mTop = mWindowManager.getDefaultDisplay().getHeight()
				- mParentView.getHeight();
	}

	public void setContentView(int layoutResID) {
		LayoutInflater inflator = (LayoutInflater) this.mParentView
				.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.setContentView(inflator.inflate(layoutResID, null));
	}

	public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
		mWindow.setOnDismissListener(listener);
	}

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

	public void show(int xPos, int yPos) {
		this.preShow();

		mRoot.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		int rootWidth = this.mRoot.getMeasuredWidth();
		int rootHeight = this.mRoot.getMeasuredHeight();
		mWindow.setAnimationStyle(R.style.Animations_GrowFromBottom);

		mWindow.showAtLocation(this.mParentView, Gravity.NO_GRAVITY, xPos
				- rootWidth / 2, yPos + mTop - rootHeight);
	}

	public void dismiss() {
		mWindow.dismiss();
	}
}
