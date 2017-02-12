package intech.assignment.services;

import java.util.Calendar;
import java.util.TimeZone;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import intech.assignment.dao.StationStatsDao;
import intech.assignment.models.StationDetail;
import intech.assignment.models.StationStats;

@Transactional
@Service
public class StationStatsService {

	@Autowired
	private StationStatsDao bikeUsageDao;
	
	private void create(int bikeRides, int bikesDisabled, int stationId) {
		StationStats stationStats = new StationStats();
		stationStats.setBikeRides(bikeRides);
		stationStats.setDisabledBikes(bikesDisabled);
		stationStats.setStationId(stationId);
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		stationStats.setMonth(calendar.get(Calendar.MONTH) + 1);
		bikeUsageDao.save(stationStats);
	}

	public void update(StationDetail newStation, StationDetail existing) {
		int bikeRides = 0, disabledBikes = 0;
		
		if(newStation.getBikesAvailable() > existing.getBikesAvailable())
			bikeRides = newStation.getBikesAvailable() - existing.getBikesAvailable();
		
		if(newStation.getBikesDisabled() > existing.getBikesDisabled())
			disabledBikes = newStation.getBikesDisabled() - existing.getBikesDisabled();
		
		this.create(bikeRides, disabledBikes, newStation.getStationId());
		System.out.println("Bike ride and disabled bikes data has been updated");
		
	}

}
