package intech.assignment.feed.models;

import java.util.List;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import intech.assignment.models.StationDetail;

public class StationDetailsWrapper extends GenericJson{

	@Key("stations")
	private List<StationDetail> stations;
	
	public List<StationDetail> getStations() {
		return stations;
	}

	public void setStations(List<StationDetail> stations) {
		this.stations = stations;
	}

}
