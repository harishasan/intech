package intech.assignment.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import intech.assignment.models.Station;

@Transactional
@Component
public interface StationDao extends CrudRepository<Station, Integer> {
	
	//TODO: improve this query
	@Query(value = "select * from stations where id in (select station_id from (select sum(bike_rides) as bike_rides, "
			+ "station_id from station_stats where month = :month group by station_id) t3 where bike_rides = (select max(bike_rides) "
			+ "as bike_rides from ( select * from (select sum(bike_rides) as bike_rides, station_id from station_stats where month = :month "
			+ "group by station_id) t1 ) t2 ));", nativeQuery = true)
	public List<Station> getPopularStationByMonth(@Param("month") Integer month);
}

