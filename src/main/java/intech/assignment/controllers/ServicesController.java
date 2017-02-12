package intech.assignment.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.maps.model.LatLng;

import intech.assignment.dao.StationDao;
import intech.assignment.dao.StationStatsDao;
import intech.assignment.models.Station;
import intech.assignment.services.StationDetailService;
import intech.assignment.services.StationService;

/*
 * Please go through readme to find more about these services. 
 */
@Controller
public class ServicesController {

	@Autowired
	private StationDetailService stationDetailService;
	
	@Autowired
	private StationService stationService;
	
	@Autowired
	private StationDao stationDao;
	
	@Autowired
	private StationStatsDao stationStatsDao;
	
	@RequestMapping("/stats/overall-stats")
	@ResponseBody public Map<String, Integer> getOverallStats(){
		Map<String, Integer> map = new HashMap<>();
		map.put("bikes", stationDetailService.getCurrentAvailableBikesCount());
		map.put("docks", stationDetailService.getCurrentAvailableDocksCount());
		return map;
	}
	
	@RequestMapping("/stats/stations/{stationId}")
	@ResponseBody public Map<String, Integer> getOverallStats(@PathVariable Integer stationId){
		Map<String, Integer> map = new HashMap<>();
		map.put("bikes", stationDetailService.getCurrentAvailableBikesCount(stationId));
		map.put("docks", stationDetailService.getCurrentAvailableDocksCount(stationId));
		return map;
	}
	
	@RequestMapping("/stats/monthly/bike-rides-and-disabled/{month}")
	@ResponseBody public Map<String, Integer> getMonthlyStats(@PathVariable Integer month){
		Map<String, Integer> map = new HashMap<>();
		Integer bikeRides = stationStatsDao.countBikeRidesByMonth(month);
		Integer disabledBikes = stationStatsDao.countDisabledBikesByMonth(month);
		map.put("bikeRides", bikeRides == null ? 0 : bikeRides);
		map.put("disabledBikes", disabledBikes == null ? 0 : disabledBikes);
		return map;
	}
	
	@RequestMapping("/stats/monthly/popular/{month}")
	@ResponseBody List<Station> getPopularStationByMonth(@PathVariable Integer month){
		return stationDao.getPopularStationByMonth(month);
	}
	
	@RequestMapping("/stations/closest-by-lat-long")
	@ResponseBody public Station getClosestStationByLatitudeAndLongitude(@RequestParam Double latitude, Double longitude){
		return stationService.getClosestStation(latitude, longitude);
	}
	
	@RequestMapping("/stations/with-capacity")
	@ResponseBody public List<Station> getAllWithAtLeastNAvailableBikes(@RequestParam Integer capacity){
		return stationService.getAllWithAtLeastNCapacity(capacity);
	}
	
	@RequestMapping("/stations/closest-by-street")
	@ResponseBody Station getClosestStationStreetName(HttpServletResponse httpResponse, @RequestParam String street){
		street = street.toLowerCase();
		if(!street.contains("newyork") && !street.contains("new york"))
			street = street + "new york";
		
		LatLng latLng = intech.assignment.utils.Utils.findLatitudeLongitude(street);
		
		if(null == latLng){
			httpResponse.setStatus(404);
			return null;
		}
		
		return stationService.getClosestStation(latLng.lat, latLng.lng);
	}
}
