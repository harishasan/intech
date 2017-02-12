package intech.assignment.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import intech.assignment.models.StationMetadata;

@Transactional
@Component
public interface StationMetadataDao extends CrudRepository<StationMetadata, Integer> {

	 @Query("select max(s.id) from StationMetadata s")
	 public Integer getMaxId();
}
