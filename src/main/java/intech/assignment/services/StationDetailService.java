package intech.assignment.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import intech.assignment.dao.StationDetailDao;
import intech.assignment.dao.StationMetadataDao;
import intech.assignment.feed.models.StationDetailsFeed;
import intech.assignment.models.StationDetail;
import intech.assignment.models.StationMetadata;

@Transactional
@Service
public class StationDetailService {

	@Autowired
	private CachingService cachingService;
	
	@Autowired
	private StationMetadataDao stationMetadataDao;
	
	@Autowired
	private StationDetailDao stationDetailDao;
	
	@Autowired
	private StationStatsService stationStatsService;
	
	public Integer getCurrentAvailableBikesCount(){
		int total = 0;
		for(Integer stationId: cachingService.getStationDetailCache().asMap().keySet())
			total += cachingService.getStationDetailCache().getIfPresent(stationId).getBikesAvailable();
		
		return total;
	}
	
	public Integer getCurrentAvailableDocksCount(){
		int total = 0;
		for(Integer stationId: cachingService.getStationDetailCache().asMap().keySet())
			total += cachingService.getStationDetailCache().getIfPresent(stationId).getDocksAvailable();
		
		return total;	
	}
	
	public Integer getCurrentAvailableBikesCount(Integer stationId){
		StationDetail stationDetail = cachingService.getStationDetailCache().getIfPresent(stationId);
		return stationDetail == null ? -1 : stationDetail.getBikesAvailable();
	}
	
	public Integer getCurrentAvailableDocksCount(Integer stationId){
		StationDetail stationDetail = cachingService.getStationDetailCache().getIfPresent(stationId);
		return stationDetail == null ? -1 : stationDetail.getDocksAvailable();	
	}
		
	public void updateStationDetails(StationDetailsFeed stationDetailsFeed) {
		
		StationMetadata lastMetadata = cachingService.getLatestStationMetadata();
		if(lastMetadata == null || lastMetadata.getStationDetailsLastUpdatedTime() == null){
			System.out.println("No existing station details metadata found");
			//we are getting data for the first time
			updateCacheAndDB(stationDetailsFeed, lastMetadata);
			System.out.println("Saved station details and metadata into db and put it into cache");
		}
		else{
			System.out.println("Station data last updated timestamp on server: " + stationDetailsFeed.getLastUpdated());
			System.out.println("Station data local copy: " + lastMetadata.getStationDetailsLastUpdatedTime());
			
			if(lastMetadata.getStationDetailsLastUpdatedTime() < stationDetailsFeed.getLastUpdated()){
				System.out.println("Station details on server has a new timestamp");
				updateCacheAndDB(stationDetailsFeed, lastMetadata);
				
			}
			else{
				System.out.println("Station data timestamp on server has not changed");
			}
		}
	}
	
	private void updateCacheAndDB(StationDetailsFeed stationDetailsFeed, StationMetadata lastMetadata){
		
		StationMetadata stationMetadata = new StationMetadata();
		if(lastMetadata != null && lastMetadata.getStationsLastUpdatedTime() != null)
			stationMetadata.setStationsLastUpdatedTime(lastMetadata.getStationsLastUpdatedTime());
		
		stationMetadata.setStationDetailsLastUpdatedTime(stationDetailsFeed.getLastUpdated());
		stationMetadata = stationMetadataDao.save(stationMetadata);
		cachingService.getStationMetadataCache().invalidateAll();
		cachingService.getStationMetadataCache().put(stationMetadata.getId(), stationMetadata);
		
		//TODO: find a better way to find changes in existing station	
		for(StationDetail stationDetail: stationDetailsFeed.getStationsData().getStations()){
			
			StationDetail existing = cachingService.getStationDetailCache().getIfPresent(stationDetail.getStationId());
			
			if(existing == null){
				System.out.println("Found a new station detail object");
				stationDetail = stationDetailDao.save(stationDetail);
				cachingService.getStationDetailCache().put(stationDetail.getStationId(), stationDetail);
			}
			else{
				if(stationDetail.hashCode() !=  existing.hashCode()){
					//assuming whenever a new bike is available one ride has been completed. 
					if(stationDetail.getBikesAvailable() > existing.getBikesAvailable()
							|| stationDetail.getBikesDisabled() > existing.getBikesDisabled()){
						stationStatsService.update(stationDetail, existing);
					}
					
					System.out.println("Station detail has been updated on server: " + existing.getStationId());
					// for debugging
					//stationDetail.equals(existing);
					stationDetail.setId(existing.getId());
					stationDetail = stationDetailDao.save(stationDetail);
					cachingService.getStationDetailCache().invalidate(stationDetail.getStationId());
					cachingService.getStationDetailCache().put(stationDetail.getStationId(), stationDetail);
				}
			}
		}
		
		System.out.println("Completed station details update");
	}
}
