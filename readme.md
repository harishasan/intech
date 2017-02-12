## NYC bicycle statistics using MySQL, Spring Boot, Spring Data, REST APIs

### Configurations

Open the `application.properties` file and set your own configurations.

### Prerequisites

- Java 8
- Maven > 3.0
- MySQL

### Setup DB
Download source code and run
	`mysql -u username -p < root_dir/src/main/resources/db_script.sql`

### Run

Go on the project's root folder, then type:
    `$ mvn spring-boot:run`

### REST APIs

- **Overall stats:** returns the global number of bikes and docks available right now
    - `/stats/overall-stats`
- **Station stats:** returns the number of bikes and docks available for “station_id” right now
    - `/stats/stations/{stationId}`   
- **Closest station by Lat/Lng:** returns the closest station to the given “latitude” and “longitude”
    - `/stations/closest-by-lat-long?latitude=XXX&longitude=XXX`   
- **Closest station by street:** returns the closest station to the given “street_name”
    - `/stations/closest-by-street?street=XXX`   
- **Stations with capacity:** returns a list of all stations which have at least “num_bikes” available
    - `/stations/with-capacity?capacity=XXX`   
- **Monthly stats:** returns the number of bike rides and disabled bikes for “month”
    - `/stats/monthly/bike-rides-and-disabled/{month}`   
- **Popular stations:** returns the id of the most popular station in “month”. Popularity is a function of the usage of the station 
    - `/stats/monthly/popular/{month}`   

### Todo
- Add tests