package intech.assignment.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import intech.assignment.models.StationDetail;

@Transactional
@Component
public interface StationDetailDao extends CrudRepository<StationDetail, Integer> {

	public StationDetail findOneByStationId(Integer stationId);
}
