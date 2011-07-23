
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
public class GeoSciTeachMapActivity extends MapActivity {
	
	public final static String INTENT_EXTRA_QUESTION_INFO = "question_info";
	
	private static PlaceItemizedOverlay mItemizedStoresOverlay = null;
	
	private Drawable mNectarDrawable;
	private MapView mMapView;
	private MapController mMapController;
	private List<Overlay> mMapOverlays;
	
	private MapBubblePopup mPopupButton;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    protected void onResume() {
		super.onResume();
		
		// set over lay and location...
        
        mMapView = (MapView) findViewById(R.id.mapview);		
		mMapView.setBuiltInZoomControls(true);
		
		mMapView.setSatellite(true);
		mMapView.setStreetView(true); 
		
		mMapController = mMapView.getController();
		mMapOverlays = mMapView.getOverlays();
		
		mNectarDrawable = this.getResources().getDrawable(
				R.drawable.map_pin);

		mItemizedStoresOverlay = new PlaceItemizedOverlay(mNectarDrawable);

		mMapOverlays.add(mItemizedStoresOverlay);
				
		addKewPinsToMap();
		
		mMapController.setZoom(17);
		
		Toast.makeText(getBaseContext(), this.getString(R.string.click_on_pins), Toast.LENGTH_SHORT).show();
    }
    
    private void addKewPinsToMap(){
    	int latitudeE6 = GpsUtils.latitudeToLatitudeE6(51.481738);
		int longitudeE6 = GpsUtils.longitudeToLongitudeE6(-0.290591);
		
		GeoPoint geoPoint = new GeoPoint(latitudeE6, longitudeE6);
		
		PlaceOverlayItem storeOverlayItem1 = new PlaceOverlayItem(geoPoint, this.getString(R.string.princess_royal_glasshouse), "placeName");
		
		mItemizedStoresOverlay.addOverlay(storeOverlayItem1);
		
		latitudeE6 = GpsUtils.latitudeToLatitudeE6(51.479075);
		longitudeE6 = GpsUtils.longitudeToLongitudeE6(-0.292794);
		
		geoPoint = new GeoPoint(latitudeE6, longitudeE6);
		
		PlaceOverlayItem storeOverlayItem2 = new PlaceOverlayItem(geoPoint, this.getString(R.string.palm_house), "placeName");
		
		mItemizedStoresOverlay.addOverlay(storeOverlayItem2);
		
		latitudeE6 = GpsUtils.latitudeToLatitudeE6(51.481899);
		longitudeE6 = GpsUtils.longitudeToLongitudeE6(-0.289498);
		
		geoPoint = new GeoPoint(latitudeE6, longitudeE6);
		
		PlaceOverlayItem storeOverlayItem3 = new PlaceOverlayItem(geoPoint, this.getString(R.string.davies_alpine_house), "placeName");
		
		mItemizedStoresOverlay.addOverlay(storeOverlayItem3);
		
		// Zoom to a spot on the map...
		mMapController.animateTo(geoPoint);
		
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private class PlaceItemizedOverlay extends ItemizedOverlay {
		private static final String TAG = "StoreItemizedOverlay";
		
		private ArrayList<PlaceOverlayItem> mOverlayItems = new ArrayList<PlaceOverlayItem>();
		private Object lock = new Object();
		private Drawable mDefaultMarker;

		public PlaceItemizedOverlay(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));
			mDefaultMarker = defaultMarker;
			populate();
		}

		@Override
		protected OverlayItem createItem(int i) {
			return mOverlayItems.get(i);
		}

		@Override
		public int size() {
			synchronized (lock) {
				return mOverlayItems.size();
			}
		}

		public void addOverlay(PlaceOverlayItem overlay) {
			synchronized (lock) {
				mOverlayItems.add(overlay);
				setLastFocusedIndex(-1);
				populate();
			}
		}

		@Override
		protected boolean onTap(int index) {
			final PlaceOverlayItem item = mOverlayItems.get(index);
			GeoPoint itemGeoPoint = item.getPoint();
			Point itemScreenPoint = mMapView.getProjection().toPixels(
					itemGeoPoint, null);
			
			mPopupButton = new MapBubblePopup(mMapView, item.getTitle(), item.getSnippet());
			
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
	
	private void startQuestionActivity(){
		Intent intent = new Intent(GeoSciTeachMapActivity.this,
				QuestionActivity.class);	      
		intent.putExtra(INTENT_EXTRA_QUESTION_INFO, "PLACE HOLDER");
		startActivity(intent);
	}
	
	private class PlaceOverlayItem extends OverlayItem {
	
		public PlaceOverlayItem(GeoPoint point, String title, String snippet) {
			super(point, title, snippet);
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	        setResult(RESULT_OK);
	        finish();
	        return true;
	    }
	    else{
	    	return super.onKeyDown(keyCode, event);
	    }
	}
}
