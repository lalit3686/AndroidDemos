package abhi.color;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MyLocationListener implements LocationListener{

	private static String Latitude = "0.0";
	private static String Longitude = "0.0";
	
	@Override
	public void onLocationChanged(Location location) {
		if(location!=null){
			setLatitude(String.valueOf(location.getLatitude()));
			setLongitude(String.valueOf(location.getLongitude()));
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public static String getLatitude() {
		return Latitude;
	}

	public static void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public static String getLongitude() {
		return Longitude;
	}

	public static void setLongitude(String longitude) {
		Longitude = longitude;
	}
}
