CREATE DATABASE  IF NOT EXISTS `member` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `member`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: k8e103.p.ssafy.io    Database: member
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `things`
--

DROP TABLE IF EXISTS `things`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `things` (
  `things_id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint NOT NULL,
  `things_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `routine` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `things_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `reg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`things_id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `things`
--

LOCK TABLES `things` WRITE;
/*!40000 ALTER TABLE `things` DISABLE KEYS */;
INSERT INTO `things` VALUES (4,13,'ST000','청소기인듯버튼','스마트버튼','2023-05-04 16:24:10'),(5,13,'ST000','청소중','청소기','2023-05-04 16:24:30'),(19,13,'ST002','충전중~','허브무선충전','2023-05-09 14:39:08'),(20,13,'ST001','문열렸디~','문열림센서','2023-05-09 15:02:36'),(28,23,'ST001','문열림 알림','문열림센서','2023-05-10 14:57:05'),(29,34,'ST001','문열림 알림','문열림센서','2023-05-10 17:12:51'),(30,34,'ST000','청소기 돌림','청소기','2023-05-11 09:41:58'),(35,23,'ST000','청소하기','청소기','2023-05-16 15:45:51'),(43,42,'ST000','남의집청소기','청소기','2023-05-17 13:18:24'),(48,39,'ST000','chung','청소기','2023-05-18 01:22:15'),(49,39,'ST003','charge','허브무선충전중지','2023-05-18 01:22:52'),(50,39,'ST001','dooor','문열림센서','2023-05-18 01:27:30'),(51,39,'ST002','te','허브무선충전','2023-05-18 01:27:44'),(53,15,'ST002','충전중ㅋㅋ','허브무선충전','2023-05-18 13:33:07'),(55,15,'ST001','문열림 알림','문열림센서','2023-05-18 13:40:03'),(56,15,'ST003','충전 대기','허브무선충전중지','2023-05-18 13:56:27'),(61,15,'ST000','청소기 돌림','청소기','2023-05-18 17:55:38');
/*!40000 ALTER TABLE `things` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-18 22:53:00
