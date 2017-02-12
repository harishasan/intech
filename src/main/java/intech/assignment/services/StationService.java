package intech.assignment.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import intech.assignment.dao.StationDao;
import intech.assignment.dao.StationMetadataDao;
import intech.assignment.feed.models.StationsFeed;
import intech.assignment.models.Station;
import intech.assignment.models.StationDetail;
import intech.assignment.models.StationMetadata;
import intech.assignment.utils.Utils;

@Transactional
@Service
public class StationService {

	@Autowired
	private CachingService cachingService;
	
	@Autowired
	private StationMetadataDao stationMetadataDao;
	
	@Autowired
	private StationDao stationDao;
	
	public Station getClosestStation(Double latitude, Double longitude) {
		double minimumDistance = Double.MAX_VALUE;
		Station closestStation = null;
		
		for(Integer stationId: cachingService.getStationCache().asMap().keySet()){
			Station currentStation = cachingService.getStationCache().getIfPresent(stationId);
			double currentDistance = Utils.distance(latitude, currentStation.getLatitude(), longitude, 
					currentStation.getLongitude(), 0, 0);
			if(minimumDistance > currentDistance){
				minimumDistance = currentDistance;
				closestStation = currentStation;
			}
		}
		return closestStation;
	}
	
	public List<Station> getAllWithAtLeastNCapacity(Integer capacity) {
		List<Station> stations = new ArrayList<>();
		for(Integer stationId : cachingService.getStationDetailCache().asMap().keySet()){
			StationDetail stationDetail = cachingService.getStationDetailCache().getIfPresent(stationId);
			if(stationDetail.getBikesAvailable() >= capacity)
				stations.add(cachingService.getStationCache().getIfPresent(stationDetail.getStationId()));
		}
		
		return stations;
	}
	
	public void updateStationsData(StationsFeed stationDataFeed) {
		
		StationMetadata lastMetadata = cachingService.getLatestStationMetadata();
		if(lastMetadata == null || lastMetadata.getStationsLastUpdatedTime() == null){
			System.out.println("No existing station metadata found");
			//we are getting data for the first time
			updateCacheAndDB(stationDataFeed, lastMetadata);
			System.out.println("Saved station data and metadata into db and put it into cache");
		}
		else{
			System.out.println("Station data last updated timestamp on server: " + stationDataFeed.getLastUpdated());
			System.out.println("Station data local copy: " + lastMetadata.getStationsLastUpdatedTime());
			
			if(lastMetadata.getStationsLastUpdatedTime() < stationDataFeed.getLastUpdated()){
				System.out.println("Station data on server has a new timestamp");
				updateCacheAndDB(stationDataFeed, lastMetadata);
				
			}
			else{
				System.out.println("Station data timestamp on server has not changed");
			}
		}
	}
	
	private void updateCacheAndDB(StationsFeed stationDataFeed, StationMetadata lastMetadata){
		StationMetadata stationMetadata = new StationMetadata();
		
		if(lastMetadata != null && lastMetadata.getStationDetailsLastUpdatedTime() != null)
			stationMetadata.setStationDetailsLastUpdatedTime(lastMetadata.getStationDetailsLastUpdatedTime());
		
		stationMetadata.setStationsLastUpdatedTime(stationDataFeed.getLastUpdated());
		stationMetadata = stationMetadataDao.save(stationMetadata);
		cachingService.getStationMetadataCache().invalidateAll();
		cachingService.getStationMetadataCache().put(stationMetadata.getId(), stationMetadata);
		
		//TODO: find a better way to find changes in existing station	
		for(Station station: stationDataFeed.getStationsData().getStations()){
			Station existing = cachingService.getStationCache().getIfPresent(station.getId());
			if(existing == null){
				System.out.println("Found a new station: " + station.getId());
				station = stationDao.save(station);
				cachingService.getStationCache().put(station.getId(), station);
			}
			else{
				if(station.hashCode() !=  existing.hashCode()){
					System.out.println("Station has been updated on server: " + station.getId());
					station = stationDao.save(station);
					cachingService.getStationCache().invalidate(station.getId());
					cachingService.getStationCache().put(station.getId(), station);
				}
			}
		}
		
		System.out.println("Completed station data update");
	}
}
