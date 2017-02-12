package intech.assignment.schedulers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

import intech.assignment.feed.models.StationDetailsFeed;
import intech.assignment.feed.models.StationsFeed;
import intech.assignment.services.StationDetailService;
import intech.assignment.services.StationService;

@Component
public class FetchJsonData {

	@Autowired
	private StationService stationService;
	
	@Autowired
	private StationDetailService stationDetailService;
	
	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	static final JsonFactory JSON_FACTORY = new JacksonFactory();
	static boolean HAS_PREVIOUS_REQUEST_COMPLETED = true;
	
	static HttpRequestInitializer httpRequestInitializer =  new HttpRequestInitializer() {    
	@Override
     public void initialize(HttpRequest request) {
        request.setParser(new JsonObjectParser(JSON_FACTORY));
      }
    };	
        
	private void downloadStationData(){
		
		try {
			System.out.println("Requesting station data");
			HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(httpRequestInitializer);
			GenericUrl stationDataUrl = new GenericUrl("https://gbfs.citibikenyc.com/gbfs/en/station_information.json");
		    HttpRequest request = requestFactory.buildGetRequest(stationDataUrl);
		    StationsFeed stationDataFeed = request.execute().parseAs(StationsFeed.class);
		    System.out.println("Updating station data");
		    stationService.updateStationsData(stationDataFeed);
		    downloadStationDetails();
		    HAS_PREVIOUS_REQUEST_COMPLETED = true;
		    System.out.println("Fetch completed");
		    System.out.println("============================================");
		}
	    catch (IOException e) {
	    	System.out.println("Failied fetching data from server");
	    	HAS_PREVIOUS_REQUEST_COMPLETED = true;
			e.printStackTrace();
		}
	}
	
	private void downloadStationDetails() throws IOException{
		System.out.println("Requesting station details");
		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(httpRequestInitializer);
	    GenericUrl stationDetailsUrl = new GenericUrl("https://gbfs.citibikenyc.com/gbfs/en/station_status.json");
	    HttpRequest request = requestFactory.buildGetRequest(stationDetailsUrl);
	    StationDetailsFeed stationDetailFeed = request.execute().parseAs(StationDetailsFeed.class);
	    stationDetailService.updateStationDetails(stationDetailFeed);
	    
	    System.out.println("Updating station details");
	}

    @Scheduled(initialDelay = 5000, fixedDelay = 5000)
    public void fetchStationData() {
    	if(!HAS_PREVIOUS_REQUEST_COMPLETED )
    		return;
    	
    	System.out.println("Initiating fetch...");
    	downloadStationData();	
    }
}
