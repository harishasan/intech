package intech.assignment.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonString;
import com.google.api.client.util.Key;

@Entity
@Table(name = "stations")
public class Station extends GenericJson{

	@Id
	@JsonString
    @Key("station_id")
	private Integer id;
  
	@NotNull
	@Key("name")
	private String name;
  
	@NotNull
	@Key("short_name")
	@Column(name="short_name")
	private String shortName;
	
	@NotNull
	@Key("lat")
	@Column(columnDefinition="Decimal(20,16)")
	private double latitude;
	
	@NotNull
	@Key("lon")
	@Column(columnDefinition="Decimal(20,16)")
	private double longitude;
	
	@NotNull
	@Key("region_id")
	@Column(name="region_id")
	private double regionId;
	
	@Key("capacity")
	@NotNull
	private Integer capacity;
	
	@NotNull
	@Key("eightd_has_key_dispenser")
	@Column(name="has_key_dispenser")
	private Boolean hasKeyDispenser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getRegionId() {
		return regionId;
	}

	public void setRegionId(double regionId) {
		this.regionId = regionId;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Boolean getHasKeyDispenser() {
		return hasKeyDispenser;
	}

	public void setHasKeyDispenser(Boolean hasKeyDispenser) {
		this.hasKeyDispenser = hasKeyDispenser;
	}	
	
	@Override
	public boolean equals(Object o) {
		
		Station station = (Station) o;
		if(!this.getName().equals(station.getName()))
			return false;
		
		if(!this.getShortName().equals(station.getShortName()))
			return false;

		if(this.getLatitude() != station.getLatitude())
			return false;

		if(this.getLongitude() != station.getLongitude())
			return false;

		if(this.getRegionId() != station.getRegionId())
			return false;

		if(!this.getCapacity().equals(station.getCapacity()))
			return false;

		if(!this.getHasKeyDispenser().equals(station.getHasKeyDispenser()))
			return false;

		return true;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.name, this.shortName, this.latitude, this.longitude, this.regionId, this.capacity, this.hasKeyDispenser);
	}
}
