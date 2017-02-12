package intech.assignment.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.google.api.client.json.JsonString;
import com.google.api.client.util.Key;

/*
 * Although details in this class can be moved into Station class but the 
 * reason behind maintaining a separate class is the fact that parent class is 
 * not going to change very often but this class is going to very frequently change. 
 * That is why a separate class for frequently changing data. 
 */
@Entity
@Table(name = "station_details")
public class StationDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
  
	/*@NotNull
	@OneToOne
	@JoinColumn(name = "station_id", insertable=false, updatable=false)
	private Station station;
	*/
	
	@NotNull
	@JsonString
	@Key("station_id")
	@Column(name = "station_id")
	private Integer stationId;
  
	@NotNull
	@Key("num_bikes_available")
	@Column(name = "available_bikes")
	private Integer bikesAvailable;
	
	@NotNull
	@Key("num_bikes_disabled")
	@Column(name = "bikes_disabled")
	private Integer bikesDisabled;
	
	@NotNull
	@Key("num_docks_disabled")
	@Column(name = "docks_disabled")
	private Integer docksDisabled;
	
	@NotNull
	@Key("num_docks_available")
	@Column(name = "docks_available")
	private Integer docksAvailable;
	
	@NotNull
	@Key("is_installed")
	@Column(name="is_installed")
	private Integer isInstalled;
	
	@NotNull
	@Key("is_renting")
	@Column(name="is_renting")
	private Integer isRenting;
	
	@NotNull
	@Key("is_returning")
	@Column(name="is_returning")
	private Integer isReturning;
	
	@NotNull
	@Key("eightd_has_available_keys")
	@Column(name="has_available_keys")
	private Boolean hasAvailableKeys;
	
	@NotNull
	@Key("last_reported")
	@Column(name="last_updated")
	private Integer lastUpdated;

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}*/

	public Integer getBikesAvailable() {
		return bikesAvailable;
	}

	public void setBikesAvailable(Integer bikesAvailable) {
		this.bikesAvailable = bikesAvailable;
	}

	public Integer getBikesDisabled() {
		return bikesDisabled;
	}

	public void setBikesDisabled(Integer bikes_disabled) {
		this.bikesDisabled = bikes_disabled;
	}

	public Integer getDocksDisabled() {
		return docksDisabled;
	}

	public void setDocksDisabled(Integer docksDisabled) {
		this.docksDisabled = docksDisabled;
	}

	public Integer getDocksAvailable() {
		return docksAvailable;
	}

	public void setDocksAvailable(Integer docksAvailable) {
		this.docksAvailable = docksAvailable;
	}

	public Integer getIsInstalled() {
		return isInstalled;
	}

	public void setIsInstalled(Integer isInstalled) {
		this.isInstalled = isInstalled;
	}

	public Integer getIsRenting() {
		return isRenting;
	}

	public void setIsRenting(Integer isRenting) {
		this.isRenting = isRenting;
	}

	public Integer getIsReturning() {
		return isReturning;
	}

	public void setIsReturning(Integer isReturning) {
		this.isReturning = isReturning;
	}

	public Boolean getHasAvailableKeys() {
		return hasAvailableKeys;
	}

	public void setHasAvailableKeys(Boolean hasAvailableKeys) {
		this.hasAvailableKeys = hasAvailableKeys;
	}

	public Integer getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Integer lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	@Override
	public boolean equals(Object o) {
		StationDetail stationDetail = (StationDetail) o;
		
		if(!this.stationId.equals(stationDetail.stationId)){
			System.out.println("stationId: " + this.stationId + "-" + stationDetail.stationId);
			return false;
		}
		
		if(!this.bikesAvailable.equals(stationDetail.getBikesAvailable())){
			System.out.println("bikesAvailable: " + this.bikesAvailable + "-" + stationDetail.getBikesAvailable());
			return false;
		}
		
		if(!this.bikesDisabled.equals(stationDetail.getBikesDisabled())){
			System.out.println("bikesDisabled: " + this.bikesDisabled + "-" + stationDetail.getBikesDisabled());
			return false;
		}
		
		if(!this.docksDisabled.equals(stationDetail.getDocksDisabled())){
			System.out.println("docksDisabled: " + this.docksDisabled + "-" + stationDetail.getDocksDisabled());
			return false;
		}
		
		if(!this.docksAvailable.equals(stationDetail.getDocksAvailable())){
			System.out.println("docksAvailable: " + this.docksAvailable + "-" + stationDetail.getDocksAvailable());
			return false;
		}
		
		if(!this.isInstalled.equals(stationDetail.getIsInstalled())){
			System.out.println("isInstalled: " + this.isInstalled + "-" + stationDetail.getIsInstalled());
			return false;
		}
		
		if(!this.isRenting.equals(stationDetail.getIsRenting())){
			System.out.println("isRenting: " + this.isRenting + "-" + stationDetail.getIsRenting());
			return false;
		}
		
		if(!this.isReturning.equals(stationDetail.getIsReturning())){
			System.out.println("isReturning: " + this.isReturning + "-" + stationDetail.getIsReturning());
			return false;
		}
		
		if(!this.hasAvailableKeys.equals(stationDetail.getHasAvailableKeys())){
			System.out.println("hasAvailableKeys: " + this.hasAvailableKeys + "-" + stationDetail.getHasAvailableKeys());
			return false;
		}
		
		if(!this.lastUpdated.equals(stationDetail.getLastUpdated())){
			System.out.println("lastUpdated: " + this.lastUpdated + "-" + stationDetail.getLastUpdated());
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.bikesAvailable, this.bikesDisabled, this.docksDisabled, 
				this.docksAvailable, this.isInstalled, this.isRenting, this.isReturning, 
				this.hasAvailableKeys, this.stationId);
	}
	
}
