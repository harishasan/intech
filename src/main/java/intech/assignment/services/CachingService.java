package intech.assignment.services;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import intech.assignment.dao.StationDao;
import intech.assignment.dao.StationDetailDao;
import intech.assignment.dao.StationMetadataDao;
import intech.assignment.models.Station;
import intech.assignment.models.StationDetail;
import intech.assignment.models.StationMetadata;

@Service
public class CachingService {
	
	private LoadingCache<Integer, Station> stationCache;
	private LoadingCache<Integer, StationDetail> stationDetailCache;
	private LoadingCache<Integer, StationMetadata> stationMetadataCache;
	
	@Autowired
	private StationDao stationDao;
	
	@Autowired
	private StationDetailDao stationDetailDao;
	
	@Autowired
	private StationMetadataDao stationMetadataDao;
	
	public void buildCacheObjects(){
		
		System.out.println("Building cache...");
		stationCache = CacheBuilder.newBuilder()
        .maximumSize(1000)
        .expireAfterAccess(365, TimeUnit.DAYS)
        .build(new CacheLoader<Integer, Station>(){
           
           @Override
           public Station load(Integer id) throws Exception {
              return stationDao.findOne(id);
           } 
        });
		
		stationDetailCache = CacheBuilder.newBuilder()
        .maximumSize(1000)
        .expireAfterAccess(365, TimeUnit.DAYS)
        .build(new CacheLoader<Integer, StationDetail>(){
           
           @Override
           public StationDetail load(Integer id) throws Exception {
              return stationDetailDao.findOneByStationId(id);
           } 
        });
		
		stationMetadataCache = CacheBuilder.newBuilder()
        .maximumSize(1000)
        .expireAfterAccess(365, TimeUnit.DAYS)
        .build(new CacheLoader<Integer, StationMetadata>(){
           
           @Override
           public StationMetadata load(Integer id) throws Exception {
              return stationMetadataDao.findOne(id);
           } 
        });
	}

	public void populateCache(){
		
		Integer maxStationMetadataId = stationMetadataDao.getMaxId();
		if(maxStationMetadataId != null){
			StationMetadata stationMetadata = stationMetadataDao.findOne(maxStationMetadataId);
			stationMetadataCache.put(stationMetadata.getId(), stationMetadata);
		}
		
		for(Station station : stationDao.findAll())
			stationCache.put(station.getId(), station);
		
		for(StationDetail stationDetail: stationDetailDao.findAll())
			stationDetailCache.put(stationDetail.getStationId(), stationDetail);
	}
	
	public LoadingCache<Integer, Station> getStationCache() {
		return stationCache;
	}

	public LoadingCache<Integer, StationDetail> getStationDetailCache() {
		return stationDetailCache;
	}

	public LoadingCache<Integer, StationMetadata> getStationMetadataCache() {
		return stationMetadataCache;
	}
	
	public StationMetadata getLatestStationMetadata(){
		if(stationMetadataCache.size() == 0)
			return null;
		
		for(Integer key : stationMetadataCache.asMap().keySet()){
			try {
				return stationMetadataCache.get(key);
			} catch (ExecutionException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}
}
