package intech.assignment.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "station_stats")
@NamedStoredProcedureQueries({
  @NamedStoredProcedureQuery(name = "aggregate_station_stats", procedureName = "aggregate_station_stats",
  parameters = {@StoredProcedureParameter(mode = ParameterMode.IN, name = "inputMonth", type = Integer.class)})
})
public class StationStats {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "station_id")
	private Integer stationId;
	
	@Column(name = "bike_rides")
	private Integer bikeRides;
	
	@Column(name = "disabled_bikes")
	private Integer disabledBikes;

	@Column(name = "month")
	private Integer month;

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public Integer getId() {
		return id;
	}

	public Integer getBikeRides() {
		return bikeRides;
	}

	public void setBikeRides(Integer bikeRides) {
		this.bikeRides = bikeRides;
	}

	public Integer getDisabledBikes() {
		return disabledBikes;
	}

	public void setDisabledBikes(Integer disabledBikes) {
		this.disabledBikes = disabledBikes;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
