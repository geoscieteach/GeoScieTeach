package com.geosciteach.activities;

import java.util.ArrayList;
import java.util.List;

import com.geosciteach.utils.GpsUtils;
import com.geosciteach.utils.MapBubblePopup;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.view.KeyEvent;

import android.view.View;
import android.view.View.OnClickListener;
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
 * The class GeoSciTeachMapActivity which shows a map with the area of Kew as
 * map centre.
 */
public class GeoSciTeachMapActivity extends MapActivity {

	public final static String INTENT_EXTRA_QUESTION_INFO = "question_info";

	private static PlaceItemizedOverlay mItemizedStoresOverlay = null;

	private Drawable mNectarDrawable;
	private MapView mMapView;
	private MapController mMapController;
	private List<Overlay> mMapOverlays;

	private MapBubblePopup mPopupButton;

	/**
	 * This method overrides onCreate(...) in Activity.
	 * 
	 * @param savedInstanceState
	 *            - bundle passed to this Activity.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	/**
	 * This method overrides onResume(...) in Activity. Sets up the map view and
	 * adds pins which highlight areas of Kew.
	 */
	protected void onResume() {
		super.onResume();

		// set over lay and location...

		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setBuiltInZoomControls(true);

		mMapView.setSatellite(true);
		mMapView.setStreetView(true);

		mMapController = mMapView.getController();
		mMapOverlays = mMapView.getOverlays();

		mNectarDrawable = this.getResources().getDrawable(R.drawable.map_pin);

		mItemizedStoresOverlay = new PlaceItemizedOverlay(mNectarDrawable);

		mMapOverlays.add(mItemizedStoresOverlay);

		addKewPinsToMap();

		mMapController.setZoom(17);

		Toast.makeText(getBaseContext(),
				this.getString(R.string.click_on_pins), Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * Method to add pins which highlight some of sites of interest on map.
	 */
	private void addKewPinsToMap() {
		int latitudeE6 = GpsUtils.latitudeToLatitudeE6(51.481738);
		int longitudeE6 = GpsUtils.longitudeToLongitudeE6(-0.290591);

		GeoPoint geoPoint = new GeoPoint(latitudeE6, longitudeE6);

		PlaceOverlayItem storeOverlayItem1 = new PlaceOverlayItem(geoPoint,
				this.getString(R.string.princess_royal_glasshouse), "placeName");

		mItemizedStoresOverlay.addOverlay(storeOverlayItem1);

		latitudeE6 = GpsUtils.latitudeToLatitudeE6(51.479075);
		longitudeE6 = GpsUtils.longitudeToLongitudeE6(-0.292794);

		geoPoint = new GeoPoint(latitudeE6, longitudeE6);

		PlaceOverlayItem storeOverlayItem2 = new PlaceOverlayItem(geoPoint,
				this.getString(R.string.palm_house), "placeName");

		mItemizedStoresOverlay.addOverlay(storeOverlayItem2);

		latitudeE6 = GpsUtils.latitudeToLatitudeE6(51.481899);
		longitudeE6 = GpsUtils.longitudeToLongitudeE6(-0.289498);

		geoPoint = new GeoPoint(latitudeE6, longitudeE6);

		PlaceOverlayItem storeOverlayItem3 = new PlaceOverlayItem(geoPoint,
				this.getString(R.string.davies_alpine_house), "placeName");

		mItemizedStoresOverlay.addOverlay(storeOverlayItem3);

		// Zoom to a spot on the map...
		mMapController.animateTo(geoPoint);

	}

	/**
	 * The server needs to know whether or not you are currently displaying any
	 * kind of route information, such as a set of driving directions.
	 * 
	 * @return True if route information is displayed; false otherwise.
	 */
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Overlay class for map which allows pins to be added over a map.
	 */
	private class PlaceItemizedOverlay extends ItemizedOverlay {
		private static final String TAG = "StoreItemizedOverlay";

		private ArrayList<PlaceOverlayItem> mOverlayItems = new ArrayList<PlaceOverlayItem>();
		private Object lock = new Object();
		private Drawable mDefaultMarker;

		/**
		 * Constructor of PlaceItemizedOverlay.
		 * 
		 * @param defaultMarker
		 *            - Drawable of the pin which is to be used.
		 */
		public PlaceItemizedOverlay(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));
			mDefaultMarker = defaultMarker;
			populate();
		}

		/**
		 * Method by which subclasses create the actual Items.
		 * 
		 * @param i
		 *            - the position of the item.
		 * 
		 * @return the OverlayItem.
		 */
		@Override
		protected OverlayItem createItem(int i) {
			return mOverlayItems.get(i);
		}

		/**
		 * Method to get the number of items in this overlay.
		 * 
		 * @return the size of this overlay.
		 */
		@Override
		public int size() {
			synchronized (lock) {
				return mOverlayItems.size();
			}
		}

		/**
		 * Method to add an overlay to the array list of overlays.
		 * 
		 * @param overlay
		 *            - PlaceOverlayItem overlay to add.
		 */
		public void addOverlay(PlaceOverlayItem overlay) {
			synchronized (lock) {
				mOverlayItems.add(overlay);
				setLastFocusedIndex(-1);
				populate();
			}
		}

		/**
		 * Method to handle a "tap" on an item.
		 * 
		 * @param index
		 *            - the index of the pin.
		 * 
		 * @return true if you handled the tap, false if you want the event that
		 *         generated it to pass to other overlays
		 */
		@Override
		protected boolean onTap(int index) {
			final PlaceOverlayItem item = mOverlayItems.get(index);
			GeoPoint itemGeoPoint = item.getPoint();
			Point itemScreenPoint = mMapView.getProjection().toPixels(
					itemGeoPoint, null);

			mPopupButton = new MapBubblePopup(mMapView, item.getTitle(),
					item.getSnippet());

			Point popupClipAmount = new Point();
			int popupPlacementX = itemScreenPoint.x;
			int popupPlacementY = itemScreenPoint.y
					- mDefaultMarker.getIntrinsicHeight();
			mPopupButton.getScreenOffset(popupClipAmount, popupPlacementX,
					popupPlacementY);
			GeoPoint animPoint = mMapView.getProjection().fromPixels(
					(mMapView.getWidth() / 2) + popupClipAmount.x,
					(mMapView.getHeight() / 2) - popupClipAmount.y);
			mMapView.getController().animateTo(animPoint, new Runnable() {
				@Override
				public void run() {
					GeoPoint itemNewGeoPoint = item.getPoint();
					Point itemNewScreenPoint = mMapView.getProjection()
							.toPixels(itemNewGeoPoint, null);
					int popupNewPlacementX = itemNewScreenPoint.x;
					int popupNewPlacementY = itemNewScreenPoint.y
							- mDefaultMarker.getIntrinsicHeight();
					mPopupButton.show(popupNewPlacementX, popupNewPlacementY);
				}
			});
			mPopupButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startQuestionActivity();
					mPopupButton.dismiss();
				}
			});
			return true;
		}
	}

	/**
	 * Method to start the question activity.
	 */
	private void startQuestionActivity() {
		Intent intent = new Intent(GeoSciTeachMapActivity.this,
				QuestionActivity.class);
		intent.putExtra(INTENT_EXTRA_QUESTION_INFO, "PLACE HOLDER");
		startActivity(intent);
	}

	/**
	 * OverlayItem class for map place which represents a pin item which can be
	 * placed onto Overlay.
	 */
	private class PlaceOverlayItem extends OverlayItem {

		/**
		 * Constructor of PlaceOverlayItem.
		 * 
		 * @param point
		 *            - GeoPoint of the place to be added.
		 * 
		 * @param imagePath
		 *            - Path of image associated to pin.
		 * 
		 * @param title
		 *            - Title text.
		 * 
		 * @param snippent
		 *            - Snippet text.
		 */
		public PlaceOverlayItem(GeoPoint point, String title, String snippet) {
			super(point, title, snippet);
		}
	}
	
	/**
	 * This method overrides onKeyDown(...) in Activity. Ensures that the back
	 * button provides the correct response as required.
	 * 
	 * @param keyCode
	 *            - The value in event.getKeyCode().
	 * @param event
	 *            - Description of the key event.
	 * 
	 * @return true to prevent this event from being propagated further, or
	 *         false to indicate that you have not handled this event and it
	 *         should continue to be propagated.
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			setResult(RESULT_OK);
			finish();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
}
