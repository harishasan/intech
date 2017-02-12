package intech.assignment.schedulers;

import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import intech.assignment.dao.StationStatsDao;

/*
 * Aggregates bike data(rides and disability), for each station, on monthly basis
 * using a stored procedure 
 */
@Component
public class AggregateBikeDataByStation {
	
	@Autowired
	private StationStatsDao stationStatsDao;
	
	@Scheduled(fixedDelay = 300000)
	public void aggregateBikeUsageForMonth(){
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		stationStatsDao.aggregateStationStats(currentMonth);
		System.out.println("====> Completed the stats aggregation for month: " + currentMonth);
	}
}
