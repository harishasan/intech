package intech.assignment.feed.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class StationDetailsFeed extends GenericJson{

	@Key("last_updated")
	private Integer lastUpdated;
	
	@Key("data")
	private StationDetailsWrapper stationsData;
	
	public StationDetailsWrapper getStationsData() {
		return stationsData;
	}

	public void setStationsData(StationDetailsWrapper stationsData) {
		this.stationsData = stationsData;
	}

	public Integer getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Integer lastUpdated) {
		this.lastUpdated = lastUpdated;
	}	
}
