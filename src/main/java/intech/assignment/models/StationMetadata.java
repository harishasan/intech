package intech.assignment.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "station_metadata")
public class StationMetadata {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="station_info_last_checked_time")
	private Integer stationInfoLastCheckedTime;

	@Column(name="station_info_last_updated_time")
	private Integer stationsLastUpdatedTime;

	@Column(name="station_details_last_checked_time")
	private Integer stationDetailsLastCheckedTime;

	@Column(name="station_details_last_updated_time")
	private Integer stationDetailsLastUpdatedTime;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*
	public Integer getStationInfoLastCheckedTime() {
		return stationInfoLastCheckedTime;
	}

	public void setStationInfoLastCheckedTime(Integer stationInfoLastCheckedTime) {
		this.stationInfoLastCheckedTime = stationInfoLastCheckedTime;
	}*/

	public Integer getStationsLastUpdatedTime() {
		return stationsLastUpdatedTime;
	}

	public void setStationsLastUpdatedTime(Integer stationInfoLastUpdatedTime) {
		this.stationsLastUpdatedTime = stationInfoLastUpdatedTime;
	}

	/*
	public Integer getStationDetailsLastCheckedTime() {
		return stationDetailsLastCheckedTime;
	}

	public void setStationDetailsLastCheckedTime(Integer stationDetailsLastCheckedTime) {
		this.stationDetailsLastCheckedTime = stationDetailsLastCheckedTime;
	}*/

	public Integer getStationDetailsLastUpdatedTime() {
		return stationDetailsLastUpdatedTime;
	}

	public void setStationDetailsLastUpdatedTime(Integer stationDetailsLastUpdatedTime) {
		this.stationDetailsLastUpdatedTime = stationDetailsLastUpdatedTime;
	}

}
