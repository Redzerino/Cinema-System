-- NovaPlex database: schema and sample data
-- Creates the novaplexdb database if it does not exist, then builds all 10 tables.

CREATE DATABASE IF NOT EXISTS `novaplexdb`;
USE `novaplexdb`;

-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: novaplexdb
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `bookingID` int NOT NULL AUTO_INCREMENT,
  `bookingReference` varchar(20) DEFAULT NULL,
  `user_ID` int DEFAULT NULL,
  `guestEmail` varchar(255) DEFAULT NULL,
  `guestName` varchar(100) DEFAULT NULL,
  `showtimeID` int DEFAULT NULL,
  `totalAmount` decimal(8,2) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`bookingID`),
  UNIQUE KEY `bookingReference` (`bookingReference`),
  KEY `user_ID` (`user_ID`),
  KEY `showtimeID` (`showtimeID`),
  CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`user_ID`) REFERENCES `users` (`user_id`),
  CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`showtimeID`) REFERENCES `showtimes` (`showtimeID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (1,'NOVA-001',NULL,'Charles@gmail.com','Charlie',1,25.00,'Cancelled','2026-04-25 01:46:03'),(12,'NP-002',NULL,'sarah.connor@email.com','Sarah Connor',3,40.00,'Confirmed','2026-04-25 01:46:03'),(13,'NP-003',NULL,'tom.ryan@email.com','Tom Ryan',4,50.00,'Cancelled','2026-04-25 01:46:03'),(14,'NP-004',NULL,'emma.walsh@email.com','Emma Walsh',4,58.00,'Confirmed','2026-04-25 01:46:03'),(15,'NP-005',NULL,'liam.kelly@email.com','Liam Kelly',5,50.00,'Cancelled','2026-04-25 01:46:03'),(16,'NP-006',NULL,'aoife.brennan@email.com','Aoife Brennan',5,12.00,'Confirmed','2026-04-25 01:46:03'),(17,'NP-USER-001',1,NULL,NULL,3,24.00,'Confirmed','2026-04-25 01:46:03'),(18,'NP-USER-002',4,NULL,NULL,3,40.00,'Confirmed','2026-04-25 01:44:09');
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movies` (
  `MovieID` int NOT NULL AUTO_INCREMENT,
  `Title` varchar(50) DEFAULT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `Genre` varchar(50) DEFAULT NULL,
  `Duration` int DEFAULT NULL,
  `AgeRating` varchar(50) DEFAULT NULL,
  `Language` varchar(50) DEFAULT NULL,
  `ReleaseDate` date DEFAULT NULL,
  `Status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`MovieID`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES (2,'Brother Bear','A beautiful story about two brothers','Family',85,'PG','English','2003-12-05','Showing'),(3,'Lion King','A young lion who has to embrace his destiny to become king after losing his father','Family',88,'PG','English','1994-06-24','Inactive'),(4,'The Dark Knight','When the menace known as the Joker wreaks havoc on Gotham City, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.','Action',152,'12A','English','2008-07-18','Now Showing'),(5,'Inception','A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.','Sci-Fi',148,'12A','English','2010-07-16','Now Showing'),(6,'Interstellar','A team of explorers travel through a wormhole in space in an attempt to ensure humanitys survival.','Sci-Fi',169,'12A','English','2014-11-07','Inactive'),(7,'The Shawshank Redemption','Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.','Drama',142,'15','English','1994-09-23','Now Showing'),(8,'Oppenheimer','The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb during World War II.','Drama',180,'15','English','2023-07-21','Inactive'),(9,'The Super Mario Bros Movie','A plumber named Mario travels through an underground labyrinth with his brother Luigi, trying to save a captured princess.','Animation',92,'PG','English','2023-04-05','Now Showing'),(10,'Avengers Endgame','After the devastating events of Infinity War, the Avengers assemble once more in order to reverse Thanoss actions and restore balance to the universe.','Action',181,'12A','English','2019-04-26','Now Showing'),(11,'Dune Part Two','Paul Atreides unites with Chani and the Fremen while seeking revenge against the conspirators who destroyed his family.','Sci-Fi',166,'12A','English','2024-03-01','Inactive'),(12,'Inside Out 2','Joy, Sadness, Anger, Fear and Disgust navigate life inside the head of a teenager when a new emotion Anxiety shows up unexpectedly.','Animation',100,'PG','English','2024-06-14','Inactive'),(13,'Deadpool and Wolverine','Deadpool is offered a chance to join the Time Variance Authority but instead recruits a variant of Wolverine to help him.','Action',127,'15','English','2024-07-26','Coming Soon'),(14,'Wicked','The untold story of the witches of Oz. Elphaba and Glinda form an unlikely friendship before their lives take different paths.','Romance',160,'PG','English','2024-11-22','Coming Soon'),(15,'Moana 2','Moana receives an unexpected call from her wayfinding ancestors and sets sail on a long journey to reach the far seas of Oceania.','Animation',100,'PG','English','2024-11-27','Coming Soon'),(16,'Lion King','A young lion who has to embrace his destiny to become king after losing his father','Family',88,'PG','English','1994-06-24','Now Showing'),(17,'King Charles','A movie about a king','Thriller',60,'12A','English','2000-10-05','Coming Soon'),(18,'Hamilton','A shakespear play into a movie','Drama',62,'12A','English','2005-09-15','Coming Soon'),(19,'Moana','A lovely story about a young girl','Animation',65,'PG','English','2005-08-09','Coming Soon'),(20,'Lion King','A young lion who has to embrace his destiny to become king after losing his father','Family',88,'PG','English','1994-06-24','Inactive'),(21,'Aladin','A rif raf who wants to be a prince','Animation',78,'PG','English','2005-09-08','Coming Soon'),(22,'Tinker Bell','A fairy','Animation',54,'PG','English','2005-09-08','Coming Soon'),(23,'Turning Red','A girl turns into a red panda','Animation',78,'PG','English','2026-04-04','Now Showing'),(24,'Frozen','A girl with ice powers','Animation',78,'PG','English','2026-05-04','Coming Soon');
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `paymentID` int NOT NULL AUTO_INCREMENT,
  `bookingID` int DEFAULT NULL,
  `amount` decimal(8,2) DEFAULT NULL,
  `paymentMethod` varchar(50) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `paidAt` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`paymentID`),
  KEY `bookingID` (`bookingID`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`bookingID`) REFERENCES `bookings` (`bookingID`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,1,25.00,'Debit Card','PAY-001','Success','2026-04-25 01:46:03'),(14,16,24.00,'Credit Card','PAY-002','Success','2026-04-25 01:46:03'),(15,12,12.00,'PayPal','PAY-003','Success','2026-04-25 01:46:03'),(16,13,36.00,'Debit Card','PAY-004','Pending','2026-04-25 01:46:03'),(17,14,18.00,'Credit Card','PAY-005','Success','2026-04-25 01:46:03'),(18,15,24.00,'PayPal','PAY-006','Failed','2026-04-25 01:46:03');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `screens`
--

DROP TABLE IF EXISTS `screens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `screens` (
  `screenID` int NOT NULL AUTO_INCREMENT,
  `screenType` varchar(50) DEFAULT NULL,
  `capacity` int DEFAULT NULL,
  PRIMARY KEY (`screenID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `screens`
--

LOCK TABLES `screens` WRITE;
/*!40000 ALTER TABLE `screens` DISABLE KEYS */;
INSERT INTO `screens` VALUES (1,'Standard',100),(2,'IMAX',150);
/*!40000 ALTER TABLE `screens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seatbooking`
--

DROP TABLE IF EXISTS `seatbooking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seatbooking` (
  `seatBookingID` int NOT NULL AUTO_INCREMENT,
  `bookingID` int DEFAULT NULL,
  `showtimeID` int DEFAULT NULL,
  `seatID` int DEFAULT NULL,
  `pricePaid` decimal(6,2) DEFAULT NULL,
  PRIMARY KEY (`seatBookingID`),
  KEY `bookingID` (`bookingID`),
  KEY `showtimeID` (`showtimeID`),
  KEY `seatID` (`seatID`),
  CONSTRAINT `seatbooking_ibfk_1` FOREIGN KEY (`bookingID`) REFERENCES `bookings` (`bookingID`),
  CONSTRAINT `seatbooking_ibfk_2` FOREIGN KEY (`showtimeID`) REFERENCES `showtimes` (`showtimeID`),
  CONSTRAINT `seatbooking_ibfk_3` FOREIGN KEY (`seatID`) REFERENCES `seats` (`seatID`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seatbooking`
--

LOCK TABLES `seatbooking` WRITE;
/*!40000 ALTER TABLE `seatbooking` DISABLE KEYS */;
INSERT INTO `seatbooking` VALUES (1,1,1,3,25.00),(13,12,3,1,12.00),(14,16,3,2,12.00),(15,12,3,3,12.00),(16,13,4,6,12.00),(17,13,4,7,12.00),(18,13,4,8,12.00),(19,14,4,9,12.00),(20,14,4,10,12.00),(21,15,5,11,12.00),(22,15,5,12,12.00),(23,16,5,13,12.00);
/*!40000 ALTER TABLE `seatbooking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seatreserve`
--

DROP TABLE IF EXISTS `seatreserve`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seatreserve` (
  `reserveID` int NOT NULL AUTO_INCREMENT,
  `showtimeID` int DEFAULT NULL,
  `seatID` int DEFAULT NULL,
  `user_ID` int DEFAULT NULL,
  `guestSessionID` varchar(100) DEFAULT NULL,
  `reserveAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `expireAt` datetime DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`reserveID`),
  KEY `user_ID` (`user_ID`),
  KEY `showtimeID` (`showtimeID`),
  KEY `seatID` (`seatID`),
  CONSTRAINT `seatreserve_ibfk_1` FOREIGN KEY (`user_ID`) REFERENCES `users` (`user_id`),
  CONSTRAINT `seatreserve_ibfk_2` FOREIGN KEY (`showtimeID`) REFERENCES `showtimes` (`showtimeID`),
  CONSTRAINT `seatreserve_ibfk_3` FOREIGN KEY (`seatID`) REFERENCES `seats` (`seatID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seatreserve`
--

LOCK TABLES `seatreserve` WRITE;
/*!40000 ALTER TABLE `seatreserve` DISABLE KEYS */;
INSERT INTO `seatreserve` VALUES (1,1,4,1,'','2026-04-25 02:11:26','2026-04-25 02:26:26','Expired'),(2,3,5,NULL,'guest-reserve-001','2026-04-25 01:58:56','2026-04-25 02:13:56','Expired');
/*!40000 ALTER TABLE `seatreserve` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seats`
--

DROP TABLE IF EXISTS `seats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seats` (
  `seatID` int NOT NULL AUTO_INCREMENT,
  `screenID` int DEFAULT NULL,
  `seatRow` char(1) DEFAULT NULL,
  `seatNumber` int DEFAULT NULL,
  `seatType` varchar(20) DEFAULT NULL,
  `isWorking` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`seatID`),
  KEY `screenID` (`screenID`),
  CONSTRAINT `seats_ibfk_1` FOREIGN KEY (`screenID`) REFERENCES `screens` (`screenID`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seats`
--

LOCK TABLES `seats` WRITE;
/*!40000 ALTER TABLE `seats` DISABLE KEYS */;
INSERT INTO `seats` VALUES (1,1,'A',1,'Standard',1),(2,1,'A',2,'Standard',1),(3,1,'A',3,'Standard',1),(4,1,'A',4,'Standard',1),(5,1,'A',5,'Standard',1),(6,1,'B',1,'Standard',1),(7,1,'B',2,'Standard',1),(8,1,'B',3,'Standard',0),(9,1,'B',4,'Standard',1),(10,1,'B',5,'Standard',1),(11,1,'C',1,'Standard',1),(12,1,'C',2,'Standard',1),(13,1,'C',3,'Standard',1),(14,1,'C',4,'Standard',1),(15,1,'C',5,'Standard',1),(16,2,'A',1,'Standard',1),(17,2,'A',2,'Standard',1),(18,2,'A',3,'Standard',1),(19,2,'A',4,'Standard',1),(20,2,'A',5,'Standard',1),(21,2,'B',1,'Standard',1),(22,2,'B',2,'Standard',0),(23,2,'B',3,'Standard',1),(24,2,'B',4,'Standard',1),(25,2,'B',5,'Standard',1),(26,2,'C',1,'Standard',1),(27,2,'C',2,'Standard',1),(28,2,'C',3,'Standard',1),(29,2,'C',4,'Standard',1),(30,2,'C',5,'Standard',1),(32,1,'A',1,'Standard',1),(33,1,'A',2,'Standard',1),(34,1,'A',3,'Standard',1),(35,1,'A',4,'Standard',1),(36,1,'A',5,'Standard',1),(37,1,'B',1,'Standard',1),(38,1,'B',2,'Standard',1),(39,1,'B',3,'Standard',0),(40,1,'B',4,'Standard',1),(41,1,'B',5,'Standard',1),(42,1,'C',1,'Standard',1),(43,1,'C',2,'Standard',1),(44,1,'C',3,'Standard',1),(45,1,'C',4,'Standard',1),(46,1,'C',5,'Standard',1),(47,2,'A',1,'Standard',1),(48,2,'A',2,'Standard',1),(49,2,'A',3,'Standard',1),(50,2,'A',4,'Standard',1),(51,2,'A',5,'Standard',1),(52,2,'B',1,'Standard',1),(53,2,'B',2,'Standard',0),(54,2,'B',3,'Standard',1),(55,2,'B',4,'Standard',1),(56,2,'B',5,'Standard',1),(57,2,'C',1,'VIP',1),(58,2,'C',2,'VIP',1),(59,2,'C',3,'VIP',1),(60,2,'C',4,'VIP',1),(61,2,'C',5,'VIP',0);
/*!40000 ALTER TABLE `seats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `showtimes`
--

DROP TABLE IF EXISTS `showtimes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `showtimes` (
  `showtimeID` int NOT NULL AUTO_INCREMENT,
  `movieID` int DEFAULT NULL,
  `screenID` int DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`showtimeID`),
  KEY `movieID` (`movieID`),
  KEY `screenID` (`screenID`),
  CONSTRAINT `showtimes_ibfk_1` FOREIGN KEY (`movieID`) REFERENCES `movies` (`MovieID`),
  CONSTRAINT `showtimes_ibfk_2` FOREIGN KEY (`screenID`) REFERENCES `screens` (`screenID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `showtimes`
--

LOCK TABLES `showtimes` WRITE;
/*!40000 ALTER TABLE `showtimes` DISABLE KEYS */;
INSERT INTO `showtimes` VALUES (1,3,1,'2026-09-23 19:10:00','2026-09-23 20:38:00','Scheduled'),(2,2,2,'2026-09-23 19:10:00','2026-09-23 20:35:00','Scheduled'),(3,2,1,'2026-04-25 14:00:00','2026-04-25 15:25:00','Cancelled'),(4,3,2,'2026-04-25 17:00:00','2026-04-25 18:28:00','Scheduled'),(5,4,1,'2026-04-25 19:00:00','2026-04-25 21:32:00','Scheduled'),(6,5,2,'2026-04-26 14:00:00','2026-04-26 16:28:00','Scheduled'),(7,6,1,'2026-04-26 18:00:00','2026-04-26 20:49:00','Scheduled'),(8,14,2,'2026-10-13 15:15:00','2026-10-13 17:55:00','Scheduled'),(9,7,2,'2026-10-04 15:10:00','2026-10-04 17:32:00','Cancelled'),(10,13,2,'2026-08-12 15:10:00','2026-08-12 17:17:00','Scheduled'),(11,12,2,'2026-08-09 14:00:00','2026-08-09 15:40:00','Cancelled'),(12,19,2,'2026-05-11 15:00:00','2026-05-11 16:05:00','Cancelled'),(13,13,1,'2026-05-11 15:00:00','2026-05-11 17:07:00','Scheduled'),(14,10,2,'2026-09-08 15:00:00','2026-09-08 18:01:00','Scheduled');
/*!40000 ALTER TABLE `showtimes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets` (
  `ticketID` int NOT NULL AUTO_INCREMENT,
  `seatBookingID` int DEFAULT NULL,
  `qrCode` varchar(255) DEFAULT NULL,
  `issuedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ticketID`),
  UNIQUE KEY `qrCode` (`qrCode`),
  KEY `seatBookingID` (`seatBookingID`),
  CONSTRAINT `tickets_ibfk_1` FOREIGN KEY (`seatBookingID`) REFERENCES `seatbooking` (`seatBookingID`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (13,1,'QR-NP-001-A1','2026-04-25 01:46:03'),(14,13,'QR-NP-001-A2','2026-04-25 01:46:03'),(15,14,'QR-NP-002-A3','2026-04-25 01:46:03'),(16,15,'QR-NP-003-B1','2026-04-25 01:46:03'),(17,16,'QR-NP-003-B2','2026-04-25 01:46:03'),(18,17,'QR-NP-003-B3','2026-04-25 01:46:03'),(19,22,'QR-NP-004-B4','2026-04-25 01:46:03'),(20,18,'QR-NP-004-B5','2026-04-25 01:46:03'),(21,19,'QR-NP-005-C1','2026-04-25 01:46:03'),(22,20,'QR-NP-005-C2','2026-04-25 01:46:03'),(23,21,'QR-NP-006-C3','2026-04-25 01:46:03');
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password_hash` varchar(255) DEFAULT NULL,
  `firstName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  `createdOn` datetime DEFAULT CURRENT_TIMESTAMP,
  `isActive` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'sarahcrotty@novaplex.com','sarah001','Sarah','Crotty','Admin','2026-04-25 01:46:03',1),(3,'john.murphy@email.com','pass123','John','Murphy','Customer','2026-04-25 01:46:03',1),(4,'sarah.connor@email.com','pass123','Sarah','Connor','Customer','2026-04-25 01:46:03',1),(5,'tom.ryan@email.com','pass123','Tom','Ryan','Customer','2026-04-25 01:46:03',0),(6,'staff@novaplex.com','staff123','Cinema','Staff','Staff','2026-04-25 01:46:03',1),(7,'manager@novaplex.com','manager123','Cinema','Manager','Admin','2026-04-25 01:46:03',0),(8,'gordanramsey@gmail.com','gordan','gordan','ramsey','Customer','2026-04-25 01:36:22',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-25  7:59:10
