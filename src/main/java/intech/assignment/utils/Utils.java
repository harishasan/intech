package intech.assignment.utils;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

public class Utils {
	/**
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 * 
	 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	 * el2 End altitude in meters
	 * @returns Distance in Meters
	 */
	public static double distance(double latitude1, double latitude2, double longitude1, double longitude2, 
			double altitude1, double altitude2) {
	    
		final int R = 6371; // Radius of the earth

	    Double latDistance = Math.toRadians(latitude2 - latitude1);
	    Double lonDistance = Math.toRadians(longitude2 - longitude1);
	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = altitude1 - altitude2;

	    distance = Math.pow(distance, 2) + Math.pow(height, 2);

	    return Math.sqrt(distance);
	}
	
	public static LatLng findLatitudeLongitude(String place){
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyAGTIBbynAsKWVmWtZ3MN9LRJxmzDelau8");
		GeocodingResult[] results;
		try {
			results = GeocodingApi.geocode(context, place).await();
			if(results.length == 0)
				return null;
			
			return results[0].geometry.location;
		} catch (Exception e) {	
			System.out.println("Error in finding latlng with Google Maps");
			e.printStackTrace();
			return null;
		}
		
	}
}
