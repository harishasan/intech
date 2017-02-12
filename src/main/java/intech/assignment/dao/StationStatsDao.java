package intech.assignment.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import intech.assignment.models.StationStats;

@Transactional
@Component
public interface StationStatsDao extends CrudRepository<StationStats, Integer> {
	
	@Procedure(name = "aggregate_station_stats")
    void aggregateStationStats(@Param("inputMonth") Integer inputMonth);

	@Query(value = "select sum(bike_rides) from station_stats where month = ?", nativeQuery = true)
	public Integer countBikeRidesByMonth(Integer month);
	
	@Query(value = "select sum(disabled_bikes) from station_stats where month = ?", nativeQuery = true)
	Integer countDisabledBikesByMonth(Integer month);
		
}
