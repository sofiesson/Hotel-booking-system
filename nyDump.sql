-- MySQL dump 10.13  Distrib 8.0.36, for macos14 (arm64)
--
-- Host: localhost    Database: hotel_booking
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `booking_id` int NOT NULL AUTO_INCREMENT,
  `check_in` datetime NOT NULL,
  `check_out` datetime NOT NULL,
  `people_amount` int NOT NULL,
  `room_amount` int NOT NULL,
  `room_type` varchar(45) NOT NULL,
  `customer_email` varchar(45) NOT NULL,
  `room_id` int DEFAULT NULL COMMENT 'When assigned a room insert the room id',
  `paid` varchar(45) NOT NULL,
  PRIMARY KEY (`booking_id`),
  UNIQUE KEY `booking_id_UNIQUE` (`booking_id`),
  KEY `customer_email_idx` (`customer_email`),
  CONSTRAINT `customer_email` FOREIGN KEY (`customer_email`) REFERENCES `customer` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (61,'2025-03-21 00:00:00','2025-03-26 00:00:00',10,1,'Familj','john.ask@gmail.com',105,'false'),(62,'2025-03-21 00:00:00','2025-03-26 00:00:00',10,1,'Standard','john.ask@gmail.com',107,'false'),(63,'2025-03-21 00:00:00','2025-03-26 00:00:00',10,1,'Standard','john.ask@gmail.com',106,'false'),(64,'2025-03-21 00:00:00','2025-03-26 00:00:00',10,1,'Standard','john.ask@gmail.com',104,'false'),(65,'2025-03-21 00:00:00','2025-03-26 00:00:00',10,1,'Standard','john.ask@gmail.com',103,'false'),(66,'2025-03-21 00:00:00','2025-03-26 00:00:00',10,1,'Standard','john.doe@gmail.com',101,'false'),(67,'2025-04-09 00:00:00','2025-04-11 00:00:00',10,1,'Standard','john.ask@gmail.com',107,'false'),(68,'2025-04-09 00:00:00','2025-04-11 00:00:00',10,1,'Standard','john.ask@gmail.com',106,'false');
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `payment_method` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `post_code` varchar(45) NOT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('Jane','Doe','Card','test street 2','35240','01212312363','jane.doe@gmail.com'),('john','ask','Card','Storgatan 1','12345','01212312363','john.ask@gmail.com'),('john','Doe','Card','test street 1','35240','0121234563','john.doe@gmail.com');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `room_id` int NOT NULL,
  `beds` int NOT NULL,
  `size` int NOT NULL,
  `location_id` int NOT NULL,
  `price` double NOT NULL,
  `room_number` int NOT NULL,
  `room_type` varchar(70) NOT NULL,
  PRIMARY KEY (`room_id`),
  UNIQUE KEY `room_id_UNIQUE` (`room_id`),
  UNIQUE KEY `room_number_UNIQUE` (`room_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1101,2,16,1,1200,101,'Standard'),(1102,2,20,1,2000,102,'Deluxe'),(1103,2,16,1,1200,103,'Standard'),(1104,2,16,1,1200,104,'Standard'),(1105,4,30,1,2200,105,'Familj'),(1106,2,16,1,1200,106,'Standard'),(1107,2,16,1,1200,107,'Standard');
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_name` varchar(70) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `role` varchar(50) NOT NULL,
  `password` varchar(45) NOT NULL,
  `locationID` int NOT NULL,
  PRIMARY KEY (`user_name`),
  UNIQUE KEY `user_id_UNIQUE` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('adm1','Jane','Doe','Admin','123',1),('rec1','john','Doe','Receptionist','123',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-09 14:10:24
