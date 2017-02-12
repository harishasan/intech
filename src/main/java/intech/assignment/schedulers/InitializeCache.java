package intech.assignment.schedulers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import intech.assignment.services.CachingService;

@Service
public class InitializeCache {
	
	@Autowired
	private CachingService cachingService;
	
	@PostConstruct
    public void populateCache() {
		cachingService.buildCacheObjects();
		cachingService.populateCache();
    }
}
