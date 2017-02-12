package intech.assignment.feed.models;

import java.util.List;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import intech.assignment.models.Station;

public class StationsWrapper  extends GenericJson{

	@Key("stations")
	private List<Station> stations;
	
	public List<Station> getStations() {
		return stations;
	}

	public void setStations(List<Station> stations) {
		this.stations = stations;
	}

}
