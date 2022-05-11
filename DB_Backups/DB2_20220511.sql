-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: db2
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alert` (
  `alertId` int NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `amount` float NOT NULL,
  `lastRejectionDateTime` timestamp NOT NULL,
  `email` varchar(64) NOT NULL,
  PRIMARY KEY (`alertId`),
  KEY `username_idx` (`username`),
  CONSTRAINT `username` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert`
--

LOCK TABLES `alert` WRITE;
/*!40000 ALTER TABLE `alert` DISABLE KEYS */;
/*!40000 ALTER TABLE `alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `average_sales_optionalproduct_per_servicepackage`
--

DROP TABLE IF EXISTS `average_sales_optionalproduct_per_servicepackage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `average_sales_optionalproduct_per_servicepackage` (
  `packageId` int NOT NULL AUTO_INCREMENT,
  `averageOptionalProducts` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`packageId`),
  CONSTRAINT `average_sales_optionalproduct_per_servicepackage_servicepackage` FOREIGN KEY (`packageId`) REFERENCES `servicepackage` (`packageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `average_sales_optionalproduct_per_servicepackage`
--

LOCK TABLES `average_sales_optionalproduct_per_servicepackage` WRITE;
/*!40000 ALTER TABLE `average_sales_optionalproduct_per_servicepackage` DISABLE KEYS */;
/*!40000 ALTER TABLE `average_sales_optionalproduct_per_servicepackage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bestseller_optionalproduct`
--

DROP TABLE IF EXISTS `bestseller_optionalproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bestseller_optionalproduct` (
  `productID` int NOT NULL AUTO_INCREMENT,
  `sales` int NOT NULL,
  PRIMARY KEY (`productID`),
  CONSTRAINT `bestseller_optionalproduct_optionalproduct` FOREIGN KEY (`productID`) REFERENCES `optionalproduct` (`productID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bestseller_optionalproduct`
--

LOCK TABLES `bestseller_optionalproduct` WRITE;
/*!40000 ALTER TABLE `bestseller_optionalproduct` DISABLE KEYS */;
/*!40000 ALTER TABLE `bestseller_optionalproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'1234'),(2,'5678');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `insolvent_users`
--

DROP TABLE IF EXISTS `insolvent_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insolvent_users` (
  `username` varchar(64) NOT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `insolvent_users_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insolvent_users`
--

LOCK TABLES `insolvent_users` WRITE;
/*!40000 ALTER TABLE `insolvent_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `insolvent_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `optionalproduct`
--

DROP TABLE IF EXISTS `optionalproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `optionalproduct` (
  `productID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `monthlyFee` float NOT NULL,
  PRIMARY KEY (`productID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `optionalproduct`
--

LOCK TABLES `optionalproduct` WRITE;
/*!40000 ALTER TABLE `optionalproduct` DISABLE KEYS */;
INSERT INTO `optionalproduct` VALUES (1,'optional1',20),(2,'optional2',22);
/*!40000 ALTER TABLE `optionalproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_optionalproduct`
--

DROP TABLE IF EXISTS `order_optionalproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_optionalproduct` (
  `order_id` int NOT NULL,
  `optionalproduct_id` int NOT NULL,
  UNIQUE KEY `order_optionalproduct_unique` (`order_id`,`optionalproduct_id`),
  KEY `order_optionalproduct_service` (`optionalproduct_id`),
  CONSTRAINT `order_optionalproduct_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`orderId`),
  CONSTRAINT `order_optionalproduct_service` FOREIGN KEY (`optionalproduct_id`) REFERENCES `optionalproduct` (`productID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_optionalproduct`
--

LOCK TABLES `order_optionalproduct` WRITE;
/*!40000 ALTER TABLE `order_optionalproduct` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_optionalproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `orderId` int NOT NULL AUTO_INCREMENT,
  `creationDateTime` timestamp NOT NULL,
  `totalFee` float NOT NULL,
  `startTime` timestamp NOT NULL,
  `endTime` timestamp NOT NULL,
  `orderState` char(20) NOT NULL,
  `username` varchar(64) NOT NULL,
  `packageId` int NOT NULL,
  `validityPeriod` int NOT NULL,
  `monthlyFee` float NOT NULL,
  PRIMARY KEY (`orderId`),
  KEY `packageId_idx` (`packageId`,`validityPeriod`,`monthlyFee`),
  CONSTRAINT `servicePackage` FOREIGN KEY (`packageId`, `validityPeriod`, `monthlyFee`) REFERENCES `servicepackage` (`packageId`, `validityPeriod`, `monthlyFee`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (22,'2022-05-10 15:46:45',240,'2022-05-19 22:00:00','2023-05-19 22:00:00','PAID','andrea',1,12,20),(23,'2022-05-10 15:46:58',240,'2022-05-24 22:00:00','2023-05-24 22:00:00','PAID','andrea',1,12,20),(24,'2022-05-10 15:47:17',240,'2022-05-13 22:00:00','2023-05-13 22:00:00','PAID','andrea',1,12,20),(25,'2022-05-10 16:17:55',240,'2022-05-11 22:00:00','2023-05-11 22:00:00','PAID','andrea',1,12,20),(26,'2022-05-10 16:18:06',240,'2022-05-18 22:00:00','2023-05-18 22:00:00','PAID','andrea',1,12,20),(27,'2022-05-10 16:27:36',240,'2022-05-10 22:00:00','2023-05-10 22:00:00','PAID','andrea',1,12,20),(28,'2022-05-10 16:27:46',240,'2022-05-24 22:00:00','2023-05-24 22:00:00','PAID','andrea',1,12,20),(29,'2022-05-10 16:27:58',240,'2022-05-12 22:00:00','2023-05-12 22:00:00','PAID','andrea',1,12,20),(30,'2022-05-10 16:28:09',240,'2022-05-28 22:00:00','2023-05-28 22:00:00','PAID','andrea',1,12,20),(31,'2022-05-10 16:28:19',240,'2022-05-20 22:00:00','2023-05-20 22:00:00','PAID','andrea',1,12,20),(32,'2022-05-10 16:51:46',240,'2022-05-09 22:00:00','2023-05-09 22:00:00','PAID','andrea',1,12,20);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `serviceId` int NOT NULL AUTO_INCREMENT,
  `serviceType` char(20) NOT NULL,
  `includedMinutes` int DEFAULT NULL,
  `includedSms` int DEFAULT NULL,
  `feeMinutes` float DEFAULT NULL,
  `feeSms` float DEFAULT NULL,
  `includedGb` int DEFAULT NULL,
  `feeGb` float DEFAULT NULL,
  PRIMARY KEY (`serviceId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1,'MOBILE_PHONE',3,5,7,10,0,0),(2,'MOBILE_INTERNET',0,0,0,0,20,1);
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicepackage`
--

DROP TABLE IF EXISTS `servicepackage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicepackage` (
  `packageId` int NOT NULL AUTO_INCREMENT,
  `validityPeriod` int NOT NULL,
  `monthlyFee` float NOT NULL,
  `name` varchar(64) NOT NULL,
  PRIMARY KEY (`packageId`,`validityPeriod`,`monthlyFee`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicepackage`
--

LOCK TABLES `servicepackage` WRITE;
/*!40000 ALTER TABLE `servicepackage` DISABLE KEYS */;
INSERT INTO `servicepackage` VALUES (1,12,20,'prova'),(1,24,15,'prova'),(2,12,30,'prova1');
/*!40000 ALTER TABLE `servicepackage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicepackage_optionalproduct`
--

DROP TABLE IF EXISTS `servicepackage_optionalproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicepackage_optionalproduct` (
  `servicepackage_id` int NOT NULL,
  `servicepackage_validityPeriod` int NOT NULL,
  `servicepackage_monthlyFee` float NOT NULL,
  `optionalproduct_productID` int NOT NULL,
  UNIQUE KEY `servicepackage_optionalproduct_unique` (`servicepackage_id`,`servicepackage_validityPeriod`,`servicepackage_monthlyFee`,`optionalproduct_productID`),
  KEY `servicepackage_optionalproduct_optionalproduct` (`optionalproduct_productID`),
  CONSTRAINT `servicepackage_optionalproduct_optionalproduct` FOREIGN KEY (`optionalproduct_productID`) REFERENCES `optionalproduct` (`productID`),
  CONSTRAINT `servicepackage_optionalproduct_servicepackage` FOREIGN KEY (`servicepackage_id`, `servicepackage_validityPeriod`, `servicepackage_monthlyFee`) REFERENCES `servicepackage` (`packageId`, `validityPeriod`, `monthlyFee`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicepackage_optionalproduct`
--

LOCK TABLES `servicepackage_optionalproduct` WRITE;
/*!40000 ALTER TABLE `servicepackage_optionalproduct` DISABLE KEYS */;
INSERT INTO `servicepackage_optionalproduct` VALUES (1,12,20,1),(1,24,15,1),(1,12,20,2),(1,24,15,2);
/*!40000 ALTER TABLE `servicepackage_optionalproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicepackage_service`
--

DROP TABLE IF EXISTS `servicepackage_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicepackage_service` (
  `servicepackage_id` int NOT NULL,
  `servicepackage_validityPeriod` int NOT NULL,
  `servicepackage_monthlyFee` float NOT NULL,
  `service_id` int NOT NULL,
  UNIQUE KEY `servicepackage_service_unique` (`servicepackage_id`,`servicepackage_validityPeriod`,`servicepackage_monthlyFee`,`service_id`),
  KEY `servicepackage_service_service` (`service_id`),
  CONSTRAINT `servicepackage_service_service` FOREIGN KEY (`service_id`) REFERENCES `service` (`serviceId`),
  CONSTRAINT `servicepackage_service_servicepackage` FOREIGN KEY (`servicepackage_id`, `servicepackage_validityPeriod`, `servicepackage_monthlyFee`) REFERENCES `servicepackage` (`packageId`, `validityPeriod`, `monthlyFee`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicepackage_service`
--

LOCK TABLES `servicepackage_service` WRITE;
/*!40000 ALTER TABLE `servicepackage_service` DISABLE KEYS */;
INSERT INTO `servicepackage_service` VALUES (1,12,20,1),(1,24,15,1),(1,12,20,2),(1,24,15,2);
/*!40000 ALTER TABLE `servicepackage_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suspended_orders`
--

DROP TABLE IF EXISTS `suspended_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suspended_orders` (
  `orderId` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`orderId`),
  CONSTRAINT `suspended_orders_orders` FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suspended_orders`
--

LOCK TABLES `suspended_orders` WRITE;
/*!40000 ALTER TABLE `suspended_orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `suspended_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `total_purchases_per_package`
--

DROP TABLE IF EXISTS `total_purchases_per_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `total_purchases_per_package` (
  `packageId` int NOT NULL AUTO_INCREMENT,
  `total_purchases` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`packageId`),
  CONSTRAINT `total_purchases_per_package_servicepackage` FOREIGN KEY (`packageId`) REFERENCES `servicepackage` (`packageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `total_purchases_per_package`
--

LOCK TABLES `total_purchases_per_package` WRITE;
/*!40000 ALTER TABLE `total_purchases_per_package` DISABLE KEYS */;
/*!40000 ALTER TABLE `total_purchases_per_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `total_purchases_per_package_validityperiod`
--

DROP TABLE IF EXISTS `total_purchases_per_package_validityperiod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `total_purchases_per_package_validityperiod` (
  `packageId` int NOT NULL AUTO_INCREMENT,
  `validityPeriod` int NOT NULL,
  `total_purchases` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`packageId`,`validityPeriod`),
  CONSTRAINT `total_purchases_per_package_validityperiod_servicepackage` FOREIGN KEY (`packageId`, `validityPeriod`) REFERENCES `servicepackage` (`packageId`, `validityPeriod`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `total_purchases_per_package_validityperiod`
--

LOCK TABLES `total_purchases_per_package_validityperiod` WRITE;
/*!40000 ALTER TABLE `total_purchases_per_package_validityperiod` DISABLE KEYS */;
/*!40000 ALTER TABLE `total_purchases_per_package_validityperiod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `total_sales_per_package`
--

DROP TABLE IF EXISTS `total_sales_per_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `total_sales_per_package` (
  `packageId` int NOT NULL AUTO_INCREMENT,
  `totalSales` int NOT NULL DEFAULT '0',
  `totalSalesWithOptionalProduct` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`packageId`),
  CONSTRAINT `total_sales_per_package_servicepackage` FOREIGN KEY (`packageId`) REFERENCES `servicepackage` (`packageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `total_sales_per_package`
--

LOCK TABLES `total_sales_per_package` WRITE;
/*!40000 ALTER TABLE `total_sales_per_package` DISABLE KEYS */;
/*!40000 ALTER TABLE `total_sales_per_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `email` varchar(64) NOT NULL,
  `isInsolvent` tinyint NOT NULL DEFAULT '0',
  `FailedAttempts` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('andrea','1234','andrea@andrea.it',1,4),('gianluca','1234','gianluca@gianluca.it',0,0);
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

-- Dump completed on 2022-05-11 16:33:10
