create database intech;
use intech;
-- MySQL dump 10.13  Distrib 5.6.15, for osx10.7 (x86_64)
--
-- Host: localhost    Database: intech
-- ------------------------------------------------------
-- Server version	5.6.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `station_details`
--

DROP TABLE IF EXISTS `station_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `station_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `station_id` int(11) NOT NULL,
  `available_bikes` int(11) NOT NULL,
  `bikes_disabled` int(11) DEFAULT NULL,
  `docks_available` int(11) DEFAULT NULL,
  `docks_disabled` int(11) DEFAULT NULL,
  `is_installed` bit(1) DEFAULT NULL,
  `is_renting` bit(1) DEFAULT NULL,
  `is_returning` bit(1) DEFAULT NULL,
  `has_available_keys` bit(1) DEFAULT NULL,
  `last_updated` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `station_details_station_id_stations_id_idx` (`station_id`),
  CONSTRAINT `station_details_station_id_stations_id` FOREIGN KEY (`station_id`) REFERENCES `stations` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28015 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `station_metadata`
--

DROP TABLE IF EXISTS `station_metadata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `station_metadata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `station_info_last_checked_time` int(10) unsigned DEFAULT NULL,
  `station_info_last_updated_time` int(10) unsigned DEFAULT NULL,
  `station_details_last_checked_time` int(10) unsigned DEFAULT NULL,
  `station_details_last_updated_time` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `INT_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1316 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `station_stats`
--

DROP TABLE IF EXISTS `station_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `station_stats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month` int(11) NOT NULL,
  `bike_rides` int(11) NOT NULL,
  `disabled_bikes` int(11) NOT NULL,
  `station_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stations`
--

DROP TABLE IF EXISTS `stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stations` (
  `id` int(10) NOT NULL,
  `name` varchar(256) NOT NULL,
  `short_name` varchar(48) NOT NULL,
  `latitude` decimal(20,16) NOT NULL,
  `longitude` decimal(20,16) NOT NULL,
  `region_id` int(11) NOT NULL,
  `capacity` int(11) NOT NULL,
  `has_key_dispenser` bit(1) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-12 13:28:33

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `aggregate_station_stats`(IN inputMonth Integer)
BEGIN
 
 DECLARE v_finished INTEGER DEFAULT 0;
 DECLARE current_station_id Integer DEFAULT 0;
 
 -- declare cursor for employee email
 DEClARE station_stats CURSOR FOR SELECT distinct station_id from station_stats;
 
 -- declare NOT FOUND handler
 DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;
 
 OPEN station_stats;
 
	 process_station: LOOP
	 
		FETCH station_stats INTO current_station_id;
	 
	 IF v_finished = 1 THEN 
		LEAVE process_station;
	 END IF;
	 
		-- build email list
		set @total_bikes_rides = (select sum(bike_rides) from station_stats 
        where station_id = current_station_id and month = inputMonth);
        
        set @total_bikes_disabled = (select sum(disabled_bikes) from station_stats 
        where station_id = current_station_id and month = inputMonth);
        
        delete from station_stats where month = inputMonth and station_id = current_station_id;
        
        insert into station_stats(month, bike_rides, disabled_bikes, station_id) 
        values(inputMonth, @total_bikes_rides, @total_bikes_disabled, current_station_id);
	 
	 END LOOP process_station;
 
 CLOSE station_stats;
 
END$$
DELIMITER ;