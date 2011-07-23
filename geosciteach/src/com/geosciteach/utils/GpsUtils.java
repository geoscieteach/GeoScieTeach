package com.geosciteach.utils;

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
public class GpsUtils {
	private static final int MULTIPLICATION_FACTOR = 1000000;
	static final double LATITUDE_MAX = 85.0511;
	static final double LATITUDE_MIN = -85.0511;
	static final double LONGITUDE_MAX = 180;
	static final double LONGITUDE_MIN = -180;

	public static int latitudeToLatitudeE6(double latitude) {
		return clipLatitude((int) (latitude * MULTIPLICATION_FACTOR));
	}
	
	public static int longitudeToLongitudeE6(double longitude) {
		return clipLongitude((int) (longitude * MULTIPLICATION_FACTOR));
	}

	private static int clipLatitude(int latitude) {
		if (latitude < LATITUDE_MIN * MULTIPLICATION_FACTOR) {
			return (int) (LATITUDE_MIN * MULTIPLICATION_FACTOR);
		} else if (latitude > LATITUDE_MAX * MULTIPLICATION_FACTOR) {
			return (int) (LATITUDE_MAX * MULTIPLICATION_FACTOR);
		} else {
			return latitude;
		}
	}

	private static int clipLongitude(int longitude) {
		if (longitude < LONGITUDE_MIN * MULTIPLICATION_FACTOR) {
			return (int) (LONGITUDE_MIN * MULTIPLICATION_FACTOR);
		} else if (longitude > LONGITUDE_MAX * MULTIPLICATION_FACTOR) {
			return (int) (LONGITUDE_MAX * MULTIPLICATION_FACTOR);
		} else {
			return longitude;
		}
	}
	
	public static double latitudeE6toLatitude(int latitudeE6) {
		return (double)latitudeE6/1000000d;
	}
	public static double longitudeE6toLongitude(int longitudeE6) {
		return (double)longitudeE6/1000000d;
	}
	
	public static float metersToMiles(float meters) {
		return meters * 0.000621371192f;
	}
}
