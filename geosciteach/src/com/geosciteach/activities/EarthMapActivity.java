package com.geosciteach.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.geosciteach.utils.EarthMapBubblePopup;
import com.geosciteach.utils.FileUtils;
import com.geosciteach.utils.GpsUtils;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

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
public class EarthMapActivity extends MapActivity {

	public final static String MAPPINS = "map_pins";
	private final String DELIMITER = ";";
	private final String MAP_DELIMITER = "@";

	private static StoreItemizedOverlay mItemizedStoresOverlay = null;

	private Drawable mPinDrawable;
	private MapView mMapView;

	private MapController mMapController;
	private List<Overlay> mMapOverlays;
	private File mFile;

	private boolean mImageToBePlaced = false;

	private RelativeLayout mRelativeLayout;

	private EarthMapBubblePopup mPopupButton;

	private Button mPlaceOnMapButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.earthmap);

		mMapView = (MapView) findViewById(R.id.earthmapview);
		mMapView.setBuiltInZoomControls(false);
		mMapView.setSatellite(false);

		mMapController = mMapView.getController();
		mMapOverlays = mMapView.getOverlays();

		mMapController.setZoom(3);

		mPinDrawable = this.getResources().getDrawable(R.drawable.map_pin);

		mItemizedStoresOverlay = new StoreItemizedOverlay(mPinDrawable);

		mMapOverlays.add(mItemizedStoresOverlay);

		mRelativeLayout = (RelativeLayout) findViewById(R.id.place_on_map_layout);

		mPlaceOnMapButton = (Button) findViewById(R.id.place_on_map_button);

		mPlaceOnMapButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				placeOnMapButtonOnClick(v);
			}
		});

		// Load all pins to map...
		placePinsOnDiskMap();

		gotoCountryLocation();
	}

	private void gotoCountryLocation() {
		String fileName = getIntent().getStringExtra(
				PreviewActivity.PHOTO_FILE_NAME);

		String country = getIntent().getStringExtra(
				PreviewActivity.PHOTO_COUNTRY);

		// Adding of a pin...
		if (fileName != null) {

			mFile = new File(fileName);

			mImageToBePlaced = true;

			if (mFile.exists()) {

				mRelativeLayout.setVisibility(View.VISIBLE);

				// animate to a Default point ...
				int latitudeE6 = GpsUtils.latitudeToLatitudeE6(12.606032);
				int longitudeE6 = GpsUtils.longitudeToLongitudeE6(22.119137);

				if (country.toLowerCase().equals("mexico")) {
					// 24.070541,-102.319338

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(24.070541);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(-102.319338);
				} else if (country.toLowerCase().equals("south africa")) {
					// -29.43577,24.506834

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(-29.43577);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(24.506834);
				} else if (country.toLowerCase().equals("chile")) {
					// -29.665144,-70.327162

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(-29.665144);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(-70.327162);
				} else if (country.toLowerCase().equals(
						"united states of america")) {
					// 40.064618,-92.827145

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(40.064618);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(-92.827145);
				} else if (country.toLowerCase().equals("peru")) {
					// -9.2496,-75.95215

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(-9.2496);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(-75.95215);
				} else if (country.toLowerCase().equals("brazil")) {
					// -11.324945,-48.881841

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(-11.324945);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(-48.881841);
				} else if (country.toLowerCase().equals("indonesia")) {
					// -1.906875,113.891584

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(-1.906875);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(113.891584);
				} else if (country.toLowerCase().equals("india")) {
					// 20.410541,78.999024

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(20.410541);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(78.999024);
				} else if (country.toLowerCase().equals("cambodia")) {
					// 11.635023, 105.366247

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(11.635023);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(105.366247);
				} else if (country.toLowerCase().equals("bolivia")) {
					// -18.787757,-64.350596

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(-10.288977);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(-51.342775);
				} else if (country.toLowerCase().equals("haiti")) {
					// 18.32 72.20

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(18.32);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(-72.20);
				} else if (country.toLowerCase().equals("malaysia")) {
					// 3.08N 101.42E

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(3.08);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(101.4);
				} else if (country.toLowerCase().equals("paraguay")) {
					// 25.16S 57.40W

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(-25.16);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(-57.40);
				} else if (country.toLowerCase().equals("ecuador")) {
					// 00.9S 78.21W

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(-00.9);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(-78.21);
				} else if (country.toLowerCase().equals("uruguay")) {
					// 34.53S 56.10W

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(-34.53);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(-56.10);
				} else if (country.toLowerCase().equals("colombia")) {
					// 4.39N 74.3W

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(4.39);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(-74.3);
				} else if (country.toLowerCase().equals("venezuela")) {
					// 10.30N 66.58W

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(10.30);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(-66.58);
				} else if (country.toLowerCase().equals("argentina")) {
					// 34.36S 58.23W

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(-34.36);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(-58.23);
				} else if (country.toLowerCase().equals("sudan")) {
					// 15.38N 032.32E

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(15.38);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(032.32);
				} else if (country.toLowerCase().equals("ethiopia")) {
					// 9.18N 38.444E

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(9.18);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(38.444);
				} else if (country.toLowerCase().equals("egypt")) {
					// 30.2N 31.13E

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(30.2);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(31.13);
				} else if (country.toLowerCase().equals("somalia")) {
					// 2.02N 45.21E

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(2.02);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(45.21);
				} else if (country.toLowerCase().equals("tanzania")) {
					// 6.1023S 35.4431E

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(-6.1023);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(35.4431);
				} else if (country.toLowerCase().equals("mozambique")) {
					// 25.57S 32.35E

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(-25.57);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(32.35);
				} else if (country.toLowerCase().equals("madagascar")) {
					// 18.55S 47.31E

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(-18.55);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(47.31);
				} else if (country.toLowerCase().equals("kenya")) {
					// 1.16S 36.48E

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(-1.16);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(36.48);
				} else {
					// center of map...

					latitudeE6 = GpsUtils.latitudeToLatitudeE6(-18.787757);
					longitudeE6 = GpsUtils.longitudeToLongitudeE6(-64.350596);
				}

				GeoPoint geoPoint = new GeoPoint(latitudeE6, longitudeE6);
				mMapController.animateTo(geoPoint);
			}
		}
	}

	public void onNewIntent(Intent intent) {

		super.onNewIntent(intent);

		mPlaceOnMapButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				placeOnMapButtonOnClick(v);
			}
		});

		gotoCountryLocation();
	}

	public void placePinsOnDiskMap() {

		SharedPreferences mapPinsDetails = getSharedPreferences(MAPPINS,
				MODE_PRIVATE);
		String mapPins = mapPinsDetails.getString(MAPPINS, null);

		if (mapPins != null) {

			String[] pinsToPlace = mapPins.split(DELIMITER);

			if (pinsToPlace != null && pinsToPlace.length > 0) {
				for (int i = 0; i < pinsToPlace.length; i++) {

					String[] pinInfo = pinsToPlace[i].split(MAP_DELIMITER);

					if (pinInfo != null && pinInfo.length >= 3) {

						File file = new File(pinInfo[0]);

						if (file.exists()) {

							GeoPoint geoPoint = new GeoPoint(
									Integer.parseInt(pinInfo[1]),
									Integer.parseInt(pinInfo[2]));

							PlaceOverlayItem storeOverlayItem = new PlaceOverlayItem(
									geoPoint, file.getPath(), file.getName(),
									"placeName");
							mItemizedStoresOverlay.addOverlay(storeOverlayItem);
						}
					}
				}
			}
		}
	}

	public void placeOnMapButtonOnClick(View button) {
		GeoPoint mapcenter = mMapView.getMapCenter();

		PlaceOverlayItem storeOverlayItem = new PlaceOverlayItem(mapcenter,
				mFile.getPath(), mFile.getName(), "placeName");
		mItemizedStoresOverlay.addOverlay(storeOverlayItem);

		mMapController.animateTo(mapcenter);

		mMapView.refreshDrawableState();

		mRelativeLayout.setVisibility(View.GONE);

		mImageToBePlaced = false;

		writeToFile(mFile.getPath(), mapcenter);
	}

	private void writeToFile(String path, GeoPoint mapcenter) {

		SharedPreferences mapPinsDetails = getSharedPreferences(MAPPINS,
				MODE_PRIVATE);
		String mapPins = mapPinsDetails.getString(MAPPINS, null);

		if (mapPins == null) {
			mapPins = "";
		}

		mapPins = mapPins + path + MAP_DELIMITER + mapcenter.getLatitudeE6() + MAP_DELIMITER
				+ mapcenter.getLongitudeE6() + DELIMITER;

		SharedPreferences.Editor editor = mapPinsDetails.edit();
		editor.putString(MAPPINS, mapPins);

		// Commit the edits!
		editor.commit();

		// Write to KML file...
		writeToKmlFile(mapPins);
	}

	private void writeToKmlFile(String mapPins) {

		if (mapPins != null) {

			String[] pinsToPlace = mapPins.split(DELIMITER);

			String placeMarkString = "";

			if (pinsToPlace != null && pinsToPlace.length > 0) {
				for (int i = 0; i < pinsToPlace.length; i++) {

					String[] pinInfo = pinsToPlace[i].split(MAP_DELIMITER);

					if (pinInfo != null && pinInfo.length >= 3) {

						File file = new File(pinInfo[0]);

						if (file.exists()) {

							placeMarkString = placeMarkString
									+ ""
									+ "  <Placemark id=\""
									+ file.getName()
									+ "\">\n"
									+ "    <name>"
									+ file.getName()
									+ "</name>\n"
									+ "	   <description><![CDATA[Lat: "
									+ GpsUtils.latitudeE6toLatitude(Integer
											.parseInt(pinInfo[1]))
									+ "<br>Long: "
									+ GpsUtils.longitudeE6toLongitude(Integer
											.parseInt(pinInfo[2]))
									+ " <br>"
									+ "	   		<img src=\""
									+ file.getName()
									+ "\" width=100><br>"
									+ "	   		]]>"
									+ "	   </description>\n"
									+ "	   <Style>\n"
									+ "	      <BalloonStyle>\n"
									+ "	           	<displayMode>default</displayMode>\n"
									+ "				<bgColor>"
									+ "64B40014"
									+ "</bgColor>\n"
									+ "				<textColor>ff000000</textColor>\n"
									+ "	   	  </BalloonStyle>\n"
									+ "	   </Style>\n"
									+ "    <Point>\n"
									+ "      <coordinates>"
									+ GpsUtils.longitudeE6toLongitude(Integer
											.parseInt(pinInfo[2]))
									+ ","
									+ GpsUtils.latitudeE6toLatitude(Integer
											.parseInt(pinInfo[1]))
									+ ",0</coordinates>\n" + "    </Point>\n"
									+ "  </Placemark>\n\n";
						}
					}
				}

				File kmlFile = new File(FileUtils.getApplicationDirectory(this
						.getApplicationContext())
						+ FileUtils.getUniqueUser(this.getApplicationContext())
						+ "placemark.kml");

				// write to kml

				String startStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
						+ "<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\">\n"

						+ "  <Document>\n"
						+ "    <name>GeoSciTeach KML</name>\n"
						+ "    <open>1</open>\n"
						+ "      <name>GeoSciTeach File</name>\n";

				String endStr = "</Document>\n" + "</kml>\n";

				placeMarkString = startStr + placeMarkString + endStr;

				FileOutputStream fout;
				try {
					fout = new FileOutputStream(kmlFile, false);
					fout.write(placeMarkString.getBytes());
					fout.flush();
					fout.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private class StoreItemizedOverlay extends ItemizedOverlay {
		private static final String TAG = "StoreItemizedOverlay";

		private ArrayList<PlaceOverlayItem> mOverlayItems = new ArrayList<PlaceOverlayItem>();
		private Object lock = new Object();
		private Drawable mDefaultMarker;

		public StoreItemizedOverlay(Drawable defaultMarker) {
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

			mPopupButton = new EarthMapBubblePopup(mMapView,
					item.getImagePath(), item.getTitle(), item.getSnippet());

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
					mPopupButton.dismiss();
				}
			});

			mPopupButton.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {

					mPopupButton.dismiss();
					mMapController.animateTo(mMapView.getMapCenter());

					AlertDialog.Builder pinRemoveBuilder = new AlertDialog.Builder(
							EarthMapActivity.this);
					pinRemoveBuilder
							.setMessage(EarthMapActivity.this.getString(R.string.are_you_sure))
							.setCancelable(false)
							.setTitle(EarthMapActivity.this.getString(R.string.delete_pin))
							.setPositiveButton(EarthMapActivity.this.getString(R.string.yes),
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											removePinSelected(item);
										}
									})
							.setNegativeButton(EarthMapActivity.this.getString(R.string.no),
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											// Do nothing
										}
									});

					AlertDialog pinRemoveAlert = pinRemoveBuilder.create();
					pinRemoveAlert.show();

					return true;
				}
			});

			return true;
		}
	}

	private class PlaceOverlayItem extends OverlayItem {
		private String mImagePath;
		private String mTitle;
		private GeoPoint mPoint;

		public PlaceOverlayItem(GeoPoint point, String imagePath, String title,
				String snippet) {
			super(point, title, snippet);
			mPoint = point;
			mTitle = title;
			mImagePath = imagePath;
		}

		public GeoPoint getPoint() {
			return mPoint;
		}

		public String getTitle() {
			return mTitle;
		}

		public String getImagePath() {
			return mImagePath;
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.global_menu, menu);

		if (this instanceof EarthMapActivity) {
			menu.findItem(R.id.menu_4).setEnabled(false);
		}

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_1:
			Intent questionIntent = new Intent(EarthMapActivity.this,
					QuestionActivity.class);
			startActivity(questionIntent);
			return true;
		case R.id.menu_2:
			Intent collectDataIntent = new Intent(EarthMapActivity.this,
					CollectDataActivity.class);
			startActivity(collectDataIntent);
			return true;
		case R.id.menu_3:
			Intent informationIntent = new Intent(EarthMapActivity.this,
					InformationActivity.class);
			startActivity(informationIntent);
			return true;
		case R.id.menu_4:
			Intent mapIntent = new Intent(EarthMapActivity.this,
					EarthMapActivity.class);
			startActivity(mapIntent);
			return true;

		case R.id.menu_5:
			Intent dataIntent = new Intent(EarthMapActivity.this,
					DataFilesActivity.class);
			startActivity(dataIntent);
			return true;

		case R.id.menu_6:
			Intent intent = new Intent(EarthMapActivity.this,
					ShareActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mImageToBePlaced) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(
						EarthMapActivity.this.getString(R.string.image_to_be_placed))
						.setCancelable(false)
						.setPositiveButton(EarthMapActivity.this.getString(R.string.yes),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										setResult(RESULT_CANCELED);
										finish();
									}
								})
						.setNegativeButton(EarthMapActivity.this.getString(R.string.no),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				AlertDialog cancelSurveyAlertMessage = builder.create();

				cancelSurveyAlertMessage.show();
			} else {
				setResult(RESULT_OK);
				finish();
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	public void removePinSelected(PlaceOverlayItem item) {

		mMapOverlays.clear();

		mMapView.refreshDrawableState();

		mMapController.animateTo(mMapView.getMapCenter());

		String newMapPins = "";
		mItemizedStoresOverlay = new StoreItemizedOverlay(mPinDrawable);

		SharedPreferences mapPinsDetails = getSharedPreferences(MAPPINS,
				MODE_PRIVATE);
		String mapPins = mapPinsDetails.getString(MAPPINS, null);

		if (mapPins != null) {

			String[] pinsToPlace = mapPins.split(DELIMITER);

			if (pinsToPlace != null && pinsToPlace.length > 0) {
				for (int i = 0; i < pinsToPlace.length; i++) {

					String[] pinInfo = pinsToPlace[i].split(MAP_DELIMITER);

					if (pinInfo != null && pinInfo.length >= 3) {

						File file = new File(pinInfo[0]);

						if (file.exists()) {

							if (item.getTitle().equals(file.getName())) {
							} else {
								newMapPins += pinsToPlace[i] + DELIMITER;

								GeoPoint geoPoint = new GeoPoint(
										Integer.parseInt(pinInfo[1]),
										Integer.parseInt(pinInfo[2]));

								PlaceOverlayItem storeOverlayItem = new PlaceOverlayItem(
										geoPoint, file.getPath(),
										file.getName(), "placeName");
								mItemizedStoresOverlay
										.addOverlay(storeOverlayItem);
							}
						}
					}
				}
			}
		}

		mMapOverlays.add(mItemizedStoresOverlay);

		SharedPreferences.Editor editor = mapPinsDetails.edit();
		editor.putString(MAPPINS, newMapPins);

		// Commit the edits!
		editor.commit();

		// Write to KML file...
		writeToKmlFile(newMapPins);
	}
}