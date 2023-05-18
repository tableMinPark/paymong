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
-- Table structure for table `point_history`
--

DROP TABLE IF EXISTS `point_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `point_history` (
  `point_history_id` bigint NOT NULL AUTO_INCREMENT,
  `point` int NOT NULL DEFAULT '0',
  `action` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `reg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `member_id` bigint NOT NULL,
  `code` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`point_history_id`)
) ENGINE=InnoDB AUTO_INCREMENT=714 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `point_history`
--

LOCK TABLES `point_history` WRITE;
/*!40000 ALTER TABLE `point_history` DISABLE KEYS */;
INSERT INTO `point_history` VALUES (1,10000,'페이 스타벅스 결제','2023-04-28 02:19:24',3,'PY000'),(2,5000,'페이 다이소 결제','2023-04-28 02:19:24',3,'PY000'),(3,4500,'페이 스타벅스 용원 결제','2023-05-02 07:24:25',3,'PY000'),(4,22,'페이 test입니다. 결제','2023-05-02 07:55:31',1,'PY000'),(5,22,'페이 test입니다. 결제','2023-05-02 08:03:01',1,'PY000'),(6,22,'페이 test입니다. 결제','2023-05-02 08:04:43',1,'PY000'),(7,-50,'페이 훈련 결제','2023-05-03 00:49:03',3,'PY000'),(8,-50,'페이 훈련 결제','2023-05-03 00:51:08',3,'PY000'),(9,-50,'페이 훈련 결제','2023-05-03 01:17:15',3,'PY000'),(10,-50,'페이 훈련 결제','2023-05-03 01:23:21',3,'PY000'),(11,450,'페이 스타벅스 용원 결제','2023-05-03 02:33:08',13,'PY000'),(12,450,'페이 하하 컴포즈 용원 결제','2023-05-03 02:34:00',13,'PY000'),(13,450,'페이 하하 결제','2023-05-03 02:34:12',13,'PY000'),(14,450,'페이 하하 결제','2023-05-03 02:37:22',13,'PY000'),(15,450,'페이 하하 결제','2023-05-03 02:38:07',13,'PY000'),(16,450,'페이 하하 결제','2023-05-03 02:40:18',13,'PY000'),(17,450,'페이 하하 결제','2023-05-03 02:42:47',13,'PY000'),(18,450,'페이 하하 결제','2023-05-03 02:43:19',13,'PY000'),(19,450,'페이 하하 결제','2023-05-03 02:47:49',13,'PY000'),(20,450,'페이 하하 결제','2023-05-03 02:48:11',13,'PY000'),(21,450,'페이 하하 결제','2023-05-03 02:55:36',13,'PY000'),(22,450,'페이 용인 스타벅스 용인 결제','2023-05-03 03:15:02',13,'PY000'),(23,450,'페이 용인 스타벅스 용인 결제','2023-05-03 03:17:06',13,'PY000'),(24,450,'페이 용인 스타벅스 용인 결제','2023-05-03 04:55:43',13,'PY000'),(25,450,'페이 용인 스타벅스 용인 결제','2023-05-03 04:57:06',13,'PY000'),(26,450,'페이 용인 스타벅스 용인 결제','2023-05-03 04:59:08',13,'PY000'),(27,450,'페이 용인 스타벅스 용인 결제','2023-05-03 05:01:32',13,'PY000'),(28,450,'페이 용인 스타벅스 용인 결제','2023-05-03 05:06:43',13,'PY000'),(29,450,'페이 용인 스타벅스 용인 결제','2023-05-03 05:23:52',13,'PY000'),(30,450,'페이 용인 스타벅스 용인 결제','2023-05-03 05:28:33',13,'PY000'),(31,450,'페이 용인 스타벅스 용인 결제','2023-05-03 05:30:25',13,'PY000'),(32,450,'페이 용인 스타벅스 용인 결제','2023-05-03 05:44:21',13,'PY000'),(33,450,'페이 용인 결제','2023-05-03 05:44:32',13,'PY000'),(34,450,'페이 배스킨 라빈스 서리완 결제','2023-05-03 05:44:45',13,'PY000'),(35,-50,'사과 구매','2023-05-03 06:49:36',13,'FD010'),(36,450,'페이 배스킨 라빈스 서리완 결제','2023-05-03 06:51:58',13,'PY000'),(37,450,'페이 배스킨 라빈스 서리완 결제','2023-05-03 06:52:01',13,'PY000'),(38,450,'페이 배스킨 라빈스 서리완 결제','2023-05-03 06:52:12',13,'PY000'),(39,-50,'사과 구매','2023-05-03 06:52:16',13,'FD010'),(40,450,'페이 배스킨 라빈스 서리완 결제','2023-05-03 07:00:16',13,'PY000'),(41,450,'페이 배스킨 라빈스 서리완 결제','2023-05-03 16:18:02',13,'PY000'),(42,-50,'사과 구매','2023-05-03 16:18:50',13,'FD010'),(44,450,'페이 배스킨 라빈스 서리완 결제','2023-05-04 14:32:45',13,'PY000'),(45,-50,'사과 구매','2023-05-04 14:33:15',13,'FD010'),(46,-50,'사과 구매','2023-05-04 14:34:00',13,'FD010'),(47,-50,'사과 구매','2023-05-04 14:34:42',13,'FD010'),(48,-50,'사과 구매','2023-05-04 14:38:11',13,'FD010'),(49,-50,'훈련','2023-05-07 00:59:30',24,'AT005'),(50,-50,'훈련','2023-05-07 01:00:19',24,'AT005'),(51,500,'스마트싱스 청소 보너스','2023-05-08 15:36:24',13,'ST000'),(52,-50,'훈련','2023-05-09 01:34:30',23,'AT005'),(53,-50,'훈련','2023-05-09 01:38:47',23,'AT005'),(54,-50,'훈련','2023-05-09 01:40:48',23,'AT005'),(55,-50,'훈련','2023-05-09 02:19:35',23,'AT005'),(56,-50,'훈련','2023-05-09 02:33:00',23,'AT005'),(57,-50,'훈련','2023-05-09 02:36:03',23,'AT005'),(58,-50,'훈련','2023-05-09 03:43:34',23,'AT005'),(59,-50,'훈련','2023-05-09 03:45:57',23,'AT005'),(60,-3,'별사탕 구매','2023-05-09 12:02:49',23,NULL),(61,-3,'별사탕 구매','2023-05-09 12:03:02',23,NULL),(62,-3,'별사탕 구매','2023-05-09 12:06:23',23,NULL),(63,-300,'초콜릿 구매','2023-05-09 12:09:48',23,NULL),(64,-3,'별사탕 구매','2023-05-09 12:15:24',23,NULL),(65,-3,'별사탕 구매','2023-05-09 12:18:20',23,NULL),(66,-500,'삼각김밥 구매','2023-05-09 14:21:42',23,NULL),(67,500,'스마트싱스 청소 보너스','2023-05-09 14:27:12',13,'ST000'),(68,-50,'훈련','2023-05-09 14:37:00',23,NULL),(69,-50,'훈련','2023-05-09 14:42:28',23,NULL),(70,75,'스마트싱스 문열림센서 보너스','2023-05-09 15:03:10',13,'ST001'),(71,-50,'훈련','2023-05-09 15:17:18',23,NULL),(72,-50,'훈련','2023-05-09 15:26:50',23,NULL),(73,-50,'훈련','2023-05-09 15:28:35',23,NULL),(74,-50,'훈련','2023-05-09 15:31:33',23,NULL),(75,-50,'훈련','2023-05-09 15:41:51',23,NULL),(76,-50,'훈련','2023-05-09 15:44:55',23,NULL),(77,-50,'훈련','2023-05-09 15:50:58',23,NULL),(78,-50,'훈련','2023-05-09 15:52:49',23,NULL),(79,-50,'훈련','2023-05-09 16:00:24',23,NULL),(80,-50,'훈련','2023-05-09 16:00:32',23,NULL),(81,-500,'샌드위치 구매','2023-05-09 16:10:52',23,NULL),(82,-50,'훈련','2023-05-09 16:13:07',23,NULL),(83,-50,'훈련','2023-05-09 16:18:09',23,NULL),(84,-50,'훈련','2023-05-09 16:29:24',23,NULL),(85,-50,'훈련','2023-05-09 16:29:49',23,NULL),(86,-50,'훈련','2023-05-09 16:46:09',23,NULL),(87,-50,'훈련','2023-05-09 16:51:43',23,NULL),(88,-50,'훈련','2023-05-09 16:57:20',23,NULL),(89,-50,'훈련','2023-05-09 16:58:09',23,NULL),(90,-50,'훈련','2023-05-09 16:58:21',23,NULL),(91,-50,'훈련','2023-05-09 17:00:28',23,NULL),(92,-50,'훈련','2023-05-09 17:01:01',23,NULL),(93,-50,'훈련','2023-05-09 17:15:32',23,NULL),(94,123,'페이 null 결제','2023-05-09 17:34:54',23,'PY000'),(95,123,'페이 3반 박상민 결제','2023-05-09 17:34:54',23,'PY000'),(96,123,'페이 충전 중 (2시간 47분 후 충전완료) 결제','2023-05-09 17:35:39',23,'PY000'),(97,123,'페이 3반 박상민 결제','2023-05-09 17:36:25',23,'PY000'),(98,123,'페이 null 결제','2023-05-09 17:36:25',23,'PY000'),(99,123,'페이 null 결제','2023-05-09 17:37:23',23,'PY000'),(100,123,'페이 3반 박상민 결제','2023-05-09 17:37:23',23,'PY000'),(101,123,'페이 3반 박상민 결제','2023-05-09 17:37:42',23,'PY000'),(102,123,'페이 null 결제','2023-05-09 17:37:42',23,'PY000'),(103,123,'페이 3반 박상민 결제','2023-05-09 17:37:42',23,'PY000'),(104,123,'페이 null 결제','2023-05-09 17:37:42',23,'PY000'),(105,123,'페이 null 결제','2023-05-09 17:37:43',23,'PY000'),(106,123,'페이 3반 박상민 결제','2023-05-09 17:37:43',23,'PY000'),(107,123,'페이 3반 박상민 결제','2023-05-09 17:37:55',23,'PY000'),(108,123,'페이 null 결제','2023-05-09 17:37:55',23,'PY000'),(109,123,'페이 3반 박상민 결제','2023-05-09 17:39:34',23,'PY000'),(110,123,'페이 null 결제','2023-05-09 17:39:34',23,'PY000'),(111,123,'페이 null 결제','2023-05-09 17:39:54',23,'PY000'),(112,123,'페이 3반 박상민 결제','2023-05-09 17:39:54',23,'PY000'),(113,123,'페이 null 결제','2023-05-09 17:39:55',23,'PY000'),(114,123,'페이 3반 박상민 결제','2023-05-09 17:39:55',23,'PY000'),(115,123,'페이 null 결제','2023-05-09 17:40:10',23,'PY000'),(116,123,'페이 3반 박상민 결제','2023-05-09 17:40:10',23,'PY000'),(117,123,'페이 null 결제','2023-05-09 17:40:10',23,'PY000'),(118,123,'페이 3반 박상민 결제','2023-05-09 17:40:10',23,'PY000'),(119,123,'페이 null 결제','2023-05-09 17:43:12',23,'PY000'),(120,123,'페이 3반 박상민 결제','2023-05-09 17:43:12',23,'PY000'),(121,123,'페이 null 결제','2023-05-09 17:43:12',23,'PY000'),(122,123,'페이 3반 박상민 결제','2023-05-09 17:43:12',23,'PY000'),(123,123,'페이 null 결제','2023-05-09 17:43:16',23,'PY000'),(124,123,'페이 3반 박상민 결제','2023-05-09 17:43:16',23,'PY000'),(125,123,'페이 null 결제','2023-05-09 17:43:31',23,'PY000'),(126,123,'페이 3반 박상민 결제','2023-05-09 17:43:31',23,'PY000'),(127,-50,'훈련','2023-05-09 17:46:01',23,NULL),(128,123,'페이 null 결제','2023-05-09 17:46:37',23,'PY000'),(129,123,'페이 3반 박상민 결제','2023-05-09 17:46:38',23,'PY000'),(130,123,'페이 null 결제','2023-05-09 17:46:39',23,'PY000'),(131,123,'페이 3반 박상민 결제','2023-05-09 17:46:39',23,'PY000'),(132,123,'페이 3반 박상민 결제','2023-05-09 17:46:40',23,'PY000'),(133,123,'페이 null 결제','2023-05-09 17:46:40',23,'PY000'),(134,-50,'훈련','2023-05-09 18:49:23',23,NULL),(135,-50,'훈련','2023-05-09 19:02:47',23,NULL),(136,-50,'훈련','2023-05-09 19:11:08',23,NULL),(137,-50,'훈련','2023-05-09 19:23:46',23,NULL),(138,-50,'훈련','2023-05-09 19:23:51',23,NULL),(139,-50,'훈련','2023-05-09 19:25:55',23,NULL),(140,-50,'훈련','2023-05-09 19:27:53',23,NULL),(141,-50,'훈련','2023-05-09 19:29:49',23,NULL),(142,-50,'훈련','2023-05-09 19:33:51',23,NULL),(143,-50,'훈련','2023-05-09 19:45:26',23,NULL),(144,-3,'별사탕 구매','2023-05-09 19:47:23',24,NULL),(145,-50,'훈련','2023-05-09 19:54:23',23,'AT005'),(146,-3,'별사탕 구매','2023-05-09 19:54:50',23,'FD000'),(147,100,'페이 null 결제','2023-05-09 19:56:14',23,'PY000'),(148,100,'페이 3반 박상민 결제','2023-05-09 19:56:14',23,'PY000'),(149,100,'페이 3반 박상민 결제','2023-05-09 19:56:21',23,'PY000'),(150,100,'페이 null 결제','2023-05-09 19:56:21',23,'PY000'),(151,100,'페이 null 결제','2023-05-09 19:56:22',23,'PY000'),(152,100,'페이 3반 박상민 결제','2023-05-09 19:56:22',23,'PY000'),(153,100,'페이 null 결제','2023-05-09 19:56:34',23,'PY000'),(154,100,'페이 null 결제','2023-05-09 19:56:34',23,'PY000'),(155,-300,'초콜릿 구매','2023-05-09 19:57:13',23,'SN000'),(156,100,'페이 null 결제','2023-05-09 19:57:33',23,'PY000'),(157,100,'페이 null 결제','2023-05-09 19:58:54',23,'PY000'),(158,100,'페이 3반 박상민 결제','2023-05-09 19:58:54',23,'PY000'),(159,100,'페이 null 결제','2023-05-09 19:58:55',23,'PY000'),(160,100,'페이 3반 박상민 결제','2023-05-09 19:58:55',23,'PY000'),(161,-50,'훈련','2023-05-09 19:59:40',23,'AT005'),(162,100,'페이 null 결제','2023-05-09 20:01:08',23,'PY000'),(163,100,'페이 찬영이 결제','2023-05-09 20:01:08',23,'PY000'),(164,100,'페이 null 결제','2023-05-09 20:01:18',23,'PY000'),(165,100,'페이 찬영이 결제','2023-05-09 20:01:18',23,'PY000'),(166,100,'페이 찬영이 결제','2023-05-09 20:01:23',23,'PY000'),(167,100,'페이 null 결제','2023-05-09 20:01:23',23,'PY000'),(168,100,'페이 null 결제','2023-05-09 20:04:08',23,'PY000'),(169,100,'페이 3반 권혁근 결제','2023-05-09 20:04:08',23,'PY000'),(170,100,'페이 null 결제','2023-05-09 20:04:10',23,'PY000'),(171,100,'페이 3반 권혁근 결제','2023-05-09 20:04:10',23,'PY000'),(172,100,'페이 null 결제','2023-05-09 20:05:13',23,'PY000'),(173,100,'페이 3반 권혁근 결제','2023-05-09 20:05:13',23,'PY000'),(174,100,'페이 3반 권혁근 결제','2023-05-09 20:05:41',23,'PY000'),(175,100,'페이 null 결제','2023-05-09 20:05:41',23,'PY000'),(176,100,'페이 null 결제','2023-05-09 20:05:56',23,'PY000'),(177,100,'페이 Starbucks Korea 결제','2023-05-09 20:05:56',23,'PY000'),(178,100,'페이 null 결제','2023-05-09 20:06:13',23,'PY000'),(179,100,'페이 null 결제','2023-05-09 20:06:13',23,'PY000'),(180,100,'페이 3반 권혁근 결제','2023-05-09 20:06:39',23,'PY000'),(181,100,'페이 null 결제','2023-05-09 20:06:39',23,'PY000'),(182,100,'페이 null 결제','2023-05-09 20:10:34',23,'PY000'),(183,-300,'초콜릿 구매','2023-05-09 20:10:43',23,'SN000'),(184,100,'페이 null 결제','2023-05-09 20:11:33',23,'PY000'),(185,100,'페이 null 결제','2023-05-09 20:11:56',23,'PY000'),(186,100,'페이 null 결제','2023-05-09 20:11:56',23,'PY000'),(187,100,'페이 null 결제','2023-05-09 20:11:59',23,'PY000'),(188,100,'페이 null 결제','2023-05-09 20:11:59',23,'PY000'),(189,100,'페이 null 결제','2023-05-09 20:12:12',23,'PY000'),(190,100,'페이 null 결제','2023-05-09 20:17:38',23,'PY000'),(191,100,'페이 스타벅스 권혁근 결제','2023-05-09 20:17:38',23,'PY000'),(192,280,'페이 나우커피 녹산공단점 결제','2023-05-09 20:28:16',23,'PY000'),(193,190,'페이 나우커피 녹산공단점 결제','2023-05-09 20:34:25',23,'PY000'),(194,-50,'훈련','2023-05-09 20:42:47',23,'AT005'),(195,-3,'별사탕 구매','2023-05-09 20:44:24',24,NULL),(196,-300,'초콜릿 구매','2023-05-09 20:44:51',24,NULL),(197,-300,'초콜릿 구매','2023-05-09 20:51:54',24,NULL),(198,-50,'훈련','2023-05-09 20:54:12',23,'AT005'),(199,-50,'훈련','2023-05-09 20:54:25',23,'AT005'),(200,1120,'페이 버거킹 부산명지DT점 결제','2023-05-09 22:09:31',23,'PY000'),(201,580,'페이 버거킹 부산명지DT점 결제','2023-05-09 22:11:25',23,'PY000'),(202,650,'페이 버거킹 부산명지DT점 결제','2023-05-09 22:12:27',23,'PY000'),(203,-600,'감튀 구매','2023-05-10 09:09:06',23,'SN012'),(204,-50,'훈련','2023-05-10 09:25:59',23,'AT005'),(205,-50,'훈련','2023-05-10 10:16:01',23,'AT005'),(206,-50,'훈련','2023-05-10 10:16:49',23,'AT005'),(207,-50,'훈련','2023-05-10 11:26:41',23,'AT005'),(208,-50,'훈련','2023-05-10 11:28:15',23,'AT005'),(209,-50,'훈련','2023-05-10 11:34:12',23,'AT005'),(210,-50,'훈련','2023-05-10 11:37:20',23,'AT005'),(211,-50,'훈련','2023-05-10 11:45:28',23,'AT005'),(212,-50,'훈련','2023-05-10 11:47:46',23,'AT005'),(213,-50,'훈련','2023-05-10 11:57:39',23,'AT005'),(214,-50,'훈련','2023-05-10 11:59:48',23,'AT005'),(215,-50,'훈련','2023-05-10 12:00:29',23,'AT005'),(216,-50,'훈련','2023-05-10 12:09:55',23,'AT005'),(217,-50,'훈련','2023-05-10 12:11:57',23,'AT005'),(218,-50,'훈련','2023-05-10 12:16:14',23,'AT005'),(219,-50,'훈련','2023-05-10 12:16:47',23,'AT005'),(220,-50,'훈련','2023-05-10 12:17:18',23,'AT005'),(221,-50,'훈련','2023-05-10 12:18:08',23,'AT005'),(222,-50,'훈련','2023-05-10 12:19:45',23,'AT005'),(223,-50,'훈련','2023-05-10 12:21:39',23,'AT005'),(224,-50,'훈련','2023-05-10 14:18:20',23,'AT005'),(225,-50,'훈련','2023-05-10 14:24:35',23,'AT005'),(226,-50,'훈련','2023-05-10 14:26:12',23,'AT005'),(227,-50,'훈련','2023-05-10 14:32:14',23,'AT005'),(228,75,'스마트싱스 문열림센서 보너스','2023-05-10 15:01:14',23,'ST001'),(229,-50,'훈련','2023-05-10 15:52:37',23,'AT005'),(230,-50,'훈련','2023-05-10 15:53:28',23,'AT005'),(231,-50,'훈련','2023-05-10 15:55:48',23,'AT005'),(232,-50,'훈련','2023-05-10 16:07:29',23,'AT005'),(233,-50,'훈련','2023-05-10 16:39:42',23,'AT005'),(234,-50,'훈련','2023-05-10 16:40:16',23,'AT005'),(235,-50,'훈련','2023-05-10 16:40:47',23,'AT005'),(236,-50,'훈련','2023-05-10 16:41:50',23,'AT005'),(237,-50,'훈련','2023-05-10 16:42:58',23,'AT005'),(238,-50,'훈련','2023-05-10 17:01:24',22,'AT005'),(239,75,'스마트싱스 문열림센서 보너스','2023-05-10 17:12:59',34,'ST001'),(240,-50,'훈련','2023-05-10 17:59:18',15,'AT005'),(241,-300,'초콜릿 구매','2023-05-11 01:25:58',23,'SN000'),(242,-600,'감튀 구매','2023-05-11 01:26:55',23,'SN012'),(243,-3,'별사탕 구매','2023-05-11 07:12:22',34,'FD000'),(244,500,'스마트싱스 청소 보너스','2023-05-11 09:42:07',34,'ST000'),(245,5,'산책','2023-05-11 11:42:56',24,'AT004'),(246,-300,'초콜릿 구매','2023-05-11 11:56:18',23,'SN000'),(247,-300,'초콜릿 구매','2023-05-11 11:57:47',23,'SN000'),(248,-300,'초콜릿 구매','2023-05-11 11:59:55',23,'SN000'),(249,-300,'초콜릿 구매','2023-05-11 12:03:49',24,'SN000'),(250,-50,'훈련','2023-05-11 12:06:06',15,'AT005'),(251,-50,'훈련','2023-05-11 12:13:05',15,'AT005'),(252,-50,'훈련','2023-05-11 12:14:02',15,'AT005'),(253,-300,'사탕 구매','2023-05-11 12:20:42',23,'SN001'),(254,-50,'훈련','2023-05-11 13:48:28',15,'AT005'),(255,-3,'별사탕 구매','2023-05-11 13:59:25',23,'FD000'),(256,-50,'훈련','2023-05-11 14:00:23',15,'AT005'),(257,-50,'훈련','2023-05-11 14:01:08',15,'AT005'),(258,-50,'훈련','2023-05-11 14:01:28',15,'AT005'),(259,-50,'훈련','2023-05-11 14:02:44',15,'AT005'),(260,-50,'훈련','2023-05-11 06:47:16',15,'AT005'),(261,-50,'훈련','2023-05-11 06:47:32',15,'AT005'),(262,-50,'훈련','2023-05-11 06:57:32',15,'AT005'),(263,-50,'훈련','2023-05-11 07:06:25',15,'AT005'),(264,75,'스마트싱스 문열림센서 보너스','2023-05-11 08:01:18',34,'ST001'),(265,-3,'별사탕 구매','2023-05-11 08:14:26',34,'FD000'),(266,-500,'사과 구매','2023-05-11 08:14:57',34,'FD010'),(267,-1000,'닭다리 구매','2023-05-11 08:30:09',34,'FD021'),(268,5,'산책','2023-05-11 11:43:57',22,'AT004'),(269,5,'산책','2023-05-11 11:53:56',22,'AT004'),(270,5,'산책','2023-05-11 11:58:48',22,'AT004'),(271,5,'산책','2023-05-11 11:58:49',22,'AT004'),(272,5,'산책','2023-05-11 11:58:50',22,'AT004'),(273,5,'산책','2023-05-11 11:58:51',22,'AT004'),(274,5,'산책','2023-05-11 11:58:52',22,'AT004'),(275,5,'산책','2023-05-11 11:58:52',22,'AT004'),(276,5,'산책','2023-05-11 11:58:52',22,'AT004'),(277,5,'산책','2023-05-11 11:59:03',22,'AT004'),(278,5,'산책','2023-05-11 11:59:06',22,'AT004'),(279,5,'산책','2023-05-11 11:59:06',22,'AT004'),(280,5,'산책','2023-05-11 11:59:06',22,'AT004'),(281,5,'산책','2023-05-11 11:59:07',22,'AT004'),(282,5,'산책','2023-05-11 11:59:07',22,'AT004'),(283,5,'산책','2023-05-11 11:59:07',22,'AT004'),(284,5,'산책','2023-05-11 11:59:07',22,'AT004'),(285,5,'산책','2023-05-11 12:00:10',22,'AT004'),(286,5,'산책','2023-05-11 12:03:46',22,'AT004'),(287,5,'산책','2023-05-11 12:03:51',22,'AT004'),(288,-50,'훈련','2023-05-11 12:46:44',22,'AT005'),(289,-50,'훈련','2023-05-11 12:48:03',22,'AT005'),(290,-50,'훈련','2023-05-11 12:51:44',22,'AT005'),(291,-50,'훈련','2023-05-11 12:52:32',22,'AT005'),(292,-50,'훈련','2023-05-11 13:09:02',22,'AT005'),(293,-50,'훈련','2023-05-11 13:17:44',22,'AT005'),(294,500,'스마트싱스 청소 보너스','2023-05-12 00:42:11',34,'ST000'),(295,-50,'훈련','2023-05-12 00:48:27',22,'AT005'),(296,-500,'샌드위치 구매','2023-05-12 00:49:05',22,'FD012'),(297,-50,'훈련','2023-05-12 00:50:12',22,'AT005'),(298,-50,'훈련','2023-05-12 00:54:03',22,'AT005'),(299,-50,'훈련','2023-05-12 01:04:23',22,'AT005'),(300,-500,'샌드위치 구매','2023-05-12 05:10:40',15,'FD012'),(301,-1000,'피자 구매','2023-05-12 05:12:53',15,'FD020'),(302,-3,'별사탕 구매','2023-05-12 05:30:18',15,'FD000'),(303,-50,'훈련','2023-05-12 05:42:10',15,'AT005'),(304,-50,'훈련','2023-05-12 05:42:57',15,'AT005'),(305,-600,'아이스크림 구매','2023-05-12 05:46:31',15,'SN013'),(306,-50,'훈련','2023-05-12 05:47:04',15,'AT005'),(307,-3,'별사탕 구매','2023-05-12 05:52:01',15,'FD000'),(308,-500,'샌드위치 구매','2023-05-12 06:03:24',34,'FD012'),(309,-5000,'우주음식 구매','2023-05-12 06:03:56',34,'FD030'),(310,-50,'훈련','2023-05-12 06:15:01',34,'AT005'),(311,-50,'훈련','2023-05-12 06:15:19',34,'AT005'),(312,-500,'삼각김밥 구매','2023-05-12 06:35:48',22,'FD011'),(313,-300,'사탕 구매','2023-05-12 06:36:10',22,'SN001'),(314,-5000,'우주음식 구매','2023-05-12 06:50:38',23,'FD030'),(315,75,'스마트싱스 문열림센서 보너스','2023-05-12 07:05:39',34,'ST001'),(316,-3,'별사탕 구매','2023-05-12 07:06:53',22,'FD000'),(317,-1000,'피자 구매','2023-05-12 07:08:08',22,'FD020'),(318,-50,'훈련','2023-05-12 07:08:43',22,'AT005'),(319,-50,'훈련','2023-05-12 07:09:14',22,'AT005'),(320,-3,'별사탕 구매','2023-05-12 07:51:30',23,'FD000'),(321,-500,'사과 구매','2023-05-12 07:51:44',23,'FD010'),(322,-300,'초콜릿 구매','2023-05-12 08:00:01',23,'SN000'),(323,-300,'사탕 구매','2023-05-12 08:02:37',23,'SN001'),(324,-3,'별사탕 구매','2023-05-12 08:02:46',23,'FD000'),(325,-600,'케이크 구매','2023-05-12 08:12:42',34,'SN011'),(326,-5000,'우주음식 구매','2023-05-12 08:14:01',34,'FD030'),(327,-50,'훈련','2023-05-12 08:14:44',34,'AT005'),(328,-3,'별사탕 구매','2023-05-12 08:18:16',23,'FD000'),(329,-50,'훈련','2023-05-12 08:18:42',23,'AT005'),(330,-50,'훈련','2023-05-12 08:18:56',15,'AT005'),(331,-50,'훈련','2023-05-12 08:18:56',15,'AT005'),(332,-50,'훈련','2023-05-12 08:18:56',15,'AT005'),(333,-50,'훈련','2023-05-12 08:18:56',15,'AT005'),(334,-50,'훈련','2023-05-12 08:18:56',15,'AT005'),(335,-50,'훈련','2023-05-12 08:18:56',15,'AT005'),(336,-50,'훈련','2023-05-12 08:18:56',15,'AT005'),(337,-50,'훈련','2023-05-12 08:18:56',15,'AT005'),(338,-50,'훈련','2023-05-12 08:18:56',15,'AT005'),(339,-50,'훈련','2023-05-12 08:18:56',15,'AT005'),(340,-50,'훈련','2023-05-12 08:18:56',15,'AT005'),(341,-50,'훈련','2023-05-12 08:19:01',34,'AT005'),(342,-50,'훈련','2023-05-12 08:19:07',23,'AT005'),(343,-50,'훈련','2023-05-12 08:19:10',15,'AT005'),(344,-50,'훈련','2023-05-12 08:21:34',23,'AT005'),(345,-50,'훈련','2023-05-12 08:34:04',19,'AT005'),(346,-50,'훈련','2023-05-12 08:39:33',15,'AT005'),(347,-500,'사과 구매','2023-05-12 18:01:24',15,'FD010'),(348,-500,'사과 구매','2023-05-12 18:01:40',34,'FD010'),(349,290,'페이 이스턴웰스 결제','2023-05-12 18:08:00',15,'PY000'),(350,80,'페이 주식회사 이스턴웰스 결제','2023-05-12 18:15:23',15,'PY000'),(351,-500,'사과 구매','2023-05-12 18:17:58',15,'FD010'),(352,14,'산책','2023-05-12 18:37:23',15,'AT004'),(353,-1000,'피자 구매','2023-05-12 18:37:48',15,'FD020'),(354,475,'페이 이마트24 녹산더시티점 결제','2023-05-12 18:41:38',15,'PY000'),(355,1925,'페이 무신사 결제','2023-05-12 18:51:26',15,'PY000'),(356,-500,'삼각김밥 구매','2023-05-12 18:56:48',22,'FD011'),(357,-50,'훈련','2023-05-12 18:57:27',22,'AT005'),(358,-50,'훈련','2023-05-12 18:57:36',22,'AT005'),(359,-50,'훈련','2023-05-12 18:57:44',22,'AT005'),(360,-50,'훈련','2023-05-12 18:58:06',22,'AT005'),(361,-50,'훈련','2023-05-12 19:33:02',22,'AT005'),(362,-50,'훈련','2023-05-13 19:30:23',22,'AT005'),(363,-500,'삼각김밥 구매','2023-05-13 19:30:39',22,'FD011'),(364,-500,'샌드위치 구매','2023-05-13 19:30:47',22,'FD012'),(365,-50,'훈련','2023-05-13 19:32:29',22,'AT005'),(366,-50,'훈련','2023-05-14 16:02:45',15,'AT005'),(367,-500,'샌드위치 구매','2023-05-14 16:08:50',15,'FD012'),(368,-1000,'피자 구매','2023-05-14 16:09:18',15,'FD020'),(369,-50,'훈련','2023-05-14 16:25:48',15,'AT005'),(370,-3,'별사탕 구매','2023-05-14 16:52:28',15,'FD000'),(371,-300,'초콜릿 구매','2023-05-14 16:53:57',15,'SN000'),(372,-1000,'피자 구매','2023-05-14 16:54:51',15,'FD020'),(373,-500,'샌드위치 구매','2023-05-14 16:56:45',15,'FD012'),(374,600,'페이 쇼타임PC방 부산정관 결제','2023-05-14 21:23:54',36,'PY000'),(375,1130,'페이 버거킹 부산명지DT점 결제','2023-05-14 22:33:19',15,'PY000'),(376,710,'페이 버거킹 부산명지DT점 결제','2023-05-14 22:35:49',15,'PY000'),(377,990,'페이 버거킹 부산명지DT점 결제','2023-05-14 22:39:45',15,'PY000'),(378,1060,'페이 씨유 정관파크점 결제','2023-05-15 03:22:52',36,'PY000'),(379,600,'페이 커피팜(COFFEE FARM) 결제','2023-05-15 08:36:22',15,'PY000'),(380,-1000,'피자 구매','2023-05-15 08:40:13',15,'FD020'),(381,-5000,'우주음식 구매','2023-05-15 09:32:28',34,'FD030'),(382,-600,'아이스크림 구매','2023-05-15 09:42:32',34,'SN013'),(383,-300,'초콜릿 구매','2023-05-15 09:42:45',34,'SN000'),(384,-300,'음료수 구매','2023-05-15 09:42:53',34,'SN002'),(385,-600,'감튀 구매','2023-05-15 09:43:02',34,'SN012'),(386,-500,'사과 구매','2023-05-15 09:48:18',23,'FD010'),(387,80,'페이 (주)이스턴웰스 본사 결제','2023-05-15 10:22:02',34,'PY000'),(388,-50,'훈련','2023-05-15 11:35:30',34,'AT005'),(389,-50,'훈련','2023-05-15 14:11:36',22,'AT005'),(390,-600,'케이크 구매','2023-05-15 14:26:57',34,'SN011'),(391,-600,'아이스크림 구매','2023-05-15 14:27:11',34,'SN013'),(392,-50,'훈련','2023-05-15 14:28:02',34,'AT005'),(393,-50,'훈련','2023-05-15 14:28:29',34,'AT005'),(394,-5000,'우주음식 구매','2023-05-15 14:30:35',34,'FD030'),(395,-600,'감튀 구매','2023-05-15 14:30:53',34,'SN012'),(396,-300,'초콜릿 구매','2023-05-15 14:31:04',34,'SN000'),(397,-600,'쿠키 구매','2023-05-15 14:31:22',34,'SN010'),(398,-300,'사탕 구매','2023-05-15 14:31:38',34,'SN001'),(399,-300,'음료수 구매','2023-05-15 14:32:01',34,'SN002'),(400,-3,'별사탕 구매','2023-05-15 15:04:23',34,'FD000'),(401,-50,'훈련','2023-05-15 15:05:56',34,'AT005'),(402,-50,'훈련','2023-05-15 15:06:25',34,'AT005'),(403,-50,'훈련','2023-05-15 15:06:25',34,'AT005'),(404,-50,'훈련','2023-05-15 15:06:26',34,'AT005'),(405,-50,'훈련','2023-05-15 15:06:26',34,'AT005'),(406,-50,'훈련','2023-05-15 15:06:26',34,'AT005'),(407,-50,'훈련','2023-05-15 15:06:26',34,'AT005'),(408,-50,'훈련','2023-05-15 15:06:26',34,'AT005'),(409,-50,'훈련','2023-05-15 15:06:26',34,'AT005'),(410,-50,'훈련','2023-05-15 15:06:26',34,'AT005'),(411,-50,'훈련','2023-05-15 15:06:26',34,'AT005'),(412,-50,'훈련','2023-05-15 15:06:26',34,'AT005'),(413,-50,'훈련','2023-05-15 15:06:26',34,'AT005'),(414,-50,'훈련','2023-05-15 15:06:26',34,'AT005'),(415,-50,'훈련','2023-05-15 15:06:47',34,'AT005'),(416,-50,'훈련','2023-05-15 15:06:50',34,'AT005'),(417,500,'스마트싱스 청소 보너스','2023-05-15 15:32:29',34,'ST000'),(418,75,'스마트싱스 문열림센서 보너스','2023-05-15 15:33:03',34,'ST001'),(419,100,'스마트싱스 허브충전 보너스','2023-05-15 15:35:57',15,'ST002'),(420,75,'스마트싱스 문열림센서 보너스','2023-05-15 15:36:47',15,'ST001'),(421,500,'스마트싱스 청소 보너스','2023-05-15 15:38:56',15,'ST000'),(422,-500,'샌드위치 구매','2023-05-15 15:57:12',22,'FD012'),(423,-500,'샌드위치 구매','2023-05-15 15:57:59',22,'FD012'),(424,-3,'별사탕 구매','2023-05-15 16:06:26',22,'FD000'),(425,-3,'별사탕 구매','2023-05-15 16:48:32',22,'FD000'),(426,-3,'별사탕 구매','2023-05-15 16:50:13',15,'FD000'),(427,-500,'사과 구매','2023-05-15 16:55:57',15,'FD010'),(428,-300,'사탕 구매','2023-05-15 16:56:23',15,'SN001'),(429,-500,'삼각김밥 구매','2023-05-15 16:57:55',15,'FD011'),(430,80,'페이 주식회사 이스턴웰스 결제','2023-05-15 17:23:19',15,'PY000'),(431,-50,'훈련','2023-05-15 17:41:07',15,'AT005'),(432,500,'배틀','2023-05-15 17:46:26',34,'AT012'),(433,-500,'배틀','2023-05-15 17:46:26',15,'AT012'),(434,-500,'배틀','2023-05-15 17:46:28',34,'AT012'),(435,-500,'배틀','2023-05-15 17:46:28',15,'AT012'),(436,500,'배틀','2023-05-15 17:46:28',15,'AT012'),(437,500,'배틀','2023-05-15 17:46:28',34,'AT012'),(438,-500,'사과 구매','2023-05-15 17:58:41',34,'FD010'),(439,-500,'배틀','2023-05-15 18:04:42',15,'AT012'),(440,500,'배틀','2023-05-15 18:04:42',34,'AT012'),(441,-500,'배틀','2023-05-15 18:04:43',34,'AT012'),(442,-500,'배틀','2023-05-15 18:04:43',15,'AT012'),(443,500,'배틀','2023-05-15 18:04:43',15,'AT012'),(444,500,'배틀','2023-05-15 18:04:43',34,'AT012'),(445,-500,'배틀','2023-05-15 18:14:01',34,'AT012'),(446,500,'배틀','2023-05-15 18:14:01',15,'AT012'),(447,500,'배틀','2023-05-15 18:17:03',15,'AT012'),(448,-500,'배틀','2023-05-15 18:17:03',34,'AT012'),(449,-500,'배틀','2023-05-15 18:17:04',15,'AT012'),(450,500,'배틀','2023-05-15 18:17:04',34,'AT012'),(451,-600,'케이크 구매','2023-05-15 18:17:29',34,'SN011'),(452,-600,'감튀 구매','2023-05-15 18:18:21',34,'SN012'),(453,-5000,'우주음식 구매','2023-05-15 18:18:39',34,'FD030'),(454,-1000,'스테이크 구매','2023-05-15 18:18:55',34,'FD022'),(455,-3,'별사탕 구매','2023-05-15 18:19:10',15,'FD000'),(456,-1000,'닭다리 구매','2023-05-15 18:19:13',34,'FD021'),(457,-1000,'피자 구매','2023-05-15 18:19:26',34,'FD020'),(458,-500,'배틀','2023-05-15 18:20:48',34,'AT012'),(459,500,'배틀','2023-05-15 18:20:48',15,'AT012'),(460,-500,'배틀','2023-05-15 18:21:31',34,'AT012'),(461,500,'배틀','2023-05-15 18:21:31',15,'AT012'),(462,-500,'배틀','2023-05-15 18:21:51',34,'AT012'),(463,500,'배틀','2023-05-15 18:21:51',15,'AT012'),(464,-500,'배틀','2023-05-15 18:22:07',34,'AT012'),(465,500,'배틀','2023-05-15 18:22:07',15,'AT012'),(466,-500,'배틀','2023-05-15 18:22:41',34,'AT012'),(467,500,'배틀','2023-05-15 18:22:41',15,'AT012'),(468,-500,'배틀','2023-05-15 18:22:48',34,'AT012'),(469,500,'배틀','2023-05-15 18:22:48',15,'AT012'),(470,-500,'배틀','2023-05-15 18:22:59',34,'AT012'),(471,500,'배틀','2023-05-15 18:22:59',15,'AT012'),(472,-500,'샌드위치 구매','2023-05-15 18:23:09',34,'FD012'),(473,-500,'배틀','2023-05-15 18:23:45',34,'AT012'),(474,500,'배틀','2023-05-15 18:23:45',15,'AT012'),(475,170,'페이 이마트24 녹산더시티점 결제','2023-05-15 18:34:27',15,'PY000'),(476,450,'페이 이마트24 녹산더시티 결제','2023-05-15 18:34:52',34,'PY000'),(477,9,'산책','2023-05-15 18:35:02',15,'AT004'),(478,-300,'음료수 구매','2023-05-15 20:42:40',34,'SN002'),(479,-50,'훈련','2023-05-15 20:54:10',34,'AT005'),(480,-50,'훈련','2023-05-15 20:54:11',34,'AT005'),(481,-50,'훈련','2023-05-15 20:54:11',34,'AT005'),(482,-50,'훈련','2023-05-15 20:54:11',34,'AT005'),(483,-50,'훈련','2023-05-15 20:54:11',34,'AT005'),(484,-500,'배틀','2023-05-15 21:14:26',34,'AT012'),(485,500,'배틀','2023-05-15 21:14:26',15,'AT012'),(486,-500,'배틀','2023-05-15 21:14:56',15,'AT012'),(487,500,'배틀','2023-05-15 21:14:56',34,'AT012'),(488,-1000,'닭다리 구매','2023-05-15 21:20:40',15,'FD021'),(489,190,'페이 나우커피 녹산공단점 결제','2023-05-15 21:50:05',34,'PY000'),(490,-50,'훈련','2023-05-15 22:40:23',15,'AT005'),(491,160,'페이 이마트24 녹산더시티점 결제','2023-05-15 22:55:02',15,'PY000'),(492,16,'산책','2023-05-15 22:56:13',34,'AT004'),(493,15,'산책','2023-05-15 22:56:26',15,'AT004'),(494,-500,'배틀','2023-05-16 09:37:10',15,'AT012'),(495,500,'배틀','2023-05-16 09:37:10',34,'AT012'),(496,-500,'배틀','2023-05-16 09:39:40',15,'AT012'),(497,500,'배틀','2023-05-16 09:39:40',34,'AT012'),(498,-500,'배틀','2023-05-16 09:41:41',34,'AT012'),(499,500,'배틀','2023-05-16 09:41:41',15,'AT012'),(500,500,'배틀','2023-05-16 09:45:04',15,'AT012'),(501,-500,'배틀','2023-05-16 09:45:04',34,'AT012'),(502,-500,'배틀','2023-05-16 09:45:05',34,'AT012'),(503,500,'배틀','2023-05-16 09:45:05',15,'AT012'),(504,500,'배틀','2023-05-16 09:47:34',34,'AT012'),(505,-500,'배틀','2023-05-16 09:47:34',15,'AT012'),(506,-500,'배틀','2023-05-16 09:47:35',34,'AT012'),(507,-500,'배틀','2023-05-16 09:47:35',15,'AT012'),(508,500,'배틀','2023-05-16 09:47:35',34,'AT012'),(509,500,'배틀','2023-05-16 09:47:35',15,'AT012'),(510,200,'스마트싱스 허브충전 보너스','2023-05-16 11:19:08',15,'ST002'),(511,500,'스마트싱스 청소 보너스','2023-05-16 11:20:01',15,'ST000'),(512,100,'스마트싱스 문열림센서 보너스','2023-05-16 11:20:22',15,'ST001'),(513,-500,'사과 구매','2023-05-16 11:30:59',15,'FD010'),(514,-500,'사과 구매','2023-05-16 11:45:52',37,'FD010'),(515,-300,'초콜릿 구매','2023-05-16 11:46:56',15,'SN000'),(516,-300,'사탕 구매','2023-05-16 11:51:18',15,'SN001'),(517,-300,'음료수 구매','2023-05-16 11:51:41',15,'SN002'),(518,-500,'사과 구매','2023-05-16 11:53:01',15,'FD010'),(519,-500,'삼각김밥 구매','2023-05-16 11:54:56',15,'FD011'),(520,-3,'별사탕 구매','2023-05-16 11:56:28',15,'FD000'),(521,-500,'배틀','2023-05-16 12:10:19',15,'AT012'),(522,500,'배틀','2023-05-16 12:10:19',34,'AT012'),(523,-500,'배틀','2023-05-16 12:10:20',15,'AT012'),(524,-500,'배틀','2023-05-16 12:10:20',34,'AT012'),(525,500,'배틀','2023-05-16 12:10:20',34,'AT012'),(526,500,'배틀','2023-05-16 12:10:20',15,'AT012'),(527,-500,'배틀','2023-05-16 12:12:43',34,'AT012'),(528,500,'배틀','2023-05-16 12:12:43',15,'AT012'),(529,-500,'배틀','2023-05-16 12:13:20',15,'AT012'),(530,500,'배틀','2023-05-16 12:13:20',34,'AT012'),(531,500,'배틀','2023-05-16 12:21:53',34,'AT012'),(532,-500,'배틀','2023-05-16 12:21:53',15,'AT012'),(533,-300,'음료수 구매','2023-05-16 13:50:21',15,'SN002'),(534,-600,'쿠키 구매','2023-05-16 13:50:47',15,'SN010'),(535,-3,'별사탕 구매','2023-05-16 14:40:15',23,'FD000'),(536,-1000,'스테이크 구매','2023-05-16 14:40:39',23,'FD022'),(537,60,'페이 주식회사 이스턴웰스 결제','2023-05-16 14:41:38',15,'PY000'),(538,-5000,'우주음식 구매','2023-05-16 14:57:02',34,'FD030'),(539,-500,'배틀','2023-05-16 14:57:37',15,'AT012'),(540,500,'배틀','2023-05-16 14:57:37',34,'AT012'),(541,-300,'초콜릿 구매','2023-05-16 15:41:53',23,'SN000'),(542,-600,'아이스크림 구매','2023-05-16 15:42:12',23,'SN013'),(543,-50,'훈련','2023-05-16 15:43:22',23,'AT005'),(544,200,'스마트싱스 허브충전 보너스','2023-05-16 15:56:34',15,'ST002'),(545,-300,'사탕 구매','2023-05-16 15:57:06',22,'SN001'),(546,100,'스마트싱스 문열림센서 보너스','2023-05-16 16:13:57',15,'ST001'),(547,460,'페이 로카모빌리티_법인택시 결제','2023-05-16 20:11:20',15,'PY000'),(548,10000,'테스트 계정 충전','2023-05-16 20:46:16',39,NULL),(549,-50,'훈련','2023-05-17 01:50:24',39,'AT005'),(550,-5000,'우주음식 구매','2023-05-17 09:11:30',34,'FD030'),(551,500,'배틀','2023-05-17 09:14:14',34,'AT012'),(552,-500,'배틀','2023-05-17 09:14:14',15,'AT012'),(553,500,'배틀','2023-05-17 09:19:07',15,'AT012'),(554,-500,'배틀','2023-05-17 09:19:07',34,'AT012'),(555,-500,'배틀','2023-05-17 09:21:35',34,'AT012'),(556,500,'배틀','2023-05-17 09:21:35',15,'AT012'),(557,-500,'배틀','2023-05-17 09:23:02',15,'AT012'),(558,500,'배틀','2023-05-17 09:23:02',34,'AT012'),(559,-500,'배틀','2023-05-17 09:23:15',34,'AT012'),(560,500,'배틀','2023-05-17 09:23:15',15,'AT012'),(561,-500,'배틀','2023-05-17 09:23:28',15,'AT012'),(562,500,'배틀','2023-05-17 09:23:28',34,'AT012'),(563,-500,'배틀','2023-05-17 09:24:06',15,'AT012'),(564,500,'배틀','2023-05-17 09:24:06',34,'AT012'),(565,-500,'배틀','2023-05-17 09:24:31',34,'AT012'),(566,500,'배틀','2023-05-17 09:24:31',15,'AT012'),(567,-500,'배틀','2023-05-17 09:24:54',15,'AT012'),(568,500,'배틀','2023-05-17 09:24:54',34,'AT012'),(569,-500,'배틀','2023-05-17 09:25:01',15,'AT012'),(570,500,'배틀','2023-05-17 09:25:01',34,'AT012'),(571,-500,'배틀','2023-05-17 09:25:14',15,'AT012'),(572,500,'배틀','2023-05-17 09:25:14',34,'AT012'),(573,-500,'배틀','2023-05-17 09:26:42',34,'AT012'),(574,500,'배틀','2023-05-17 09:26:42',15,'AT012'),(575,500,'스마트싱스 청소 보너스','2023-05-17 09:30:23',15,'ST000'),(576,-500,'배틀','2023-05-17 09:31:53',34,'AT012'),(577,500,'배틀','2023-05-17 09:31:53',15,'AT012'),(578,-500,'배틀','2023-05-17 09:32:09',15,'AT012'),(579,500,'배틀','2023-05-17 09:32:09',34,'AT012'),(580,100,'스마트싱스 문열림센서 보너스','2023-05-17 09:36:19',15,'ST001'),(581,-500,'배틀','2023-05-17 09:37:35',34,'AT012'),(582,500,'배틀','2023-05-17 09:37:35',15,'AT012'),(583,-500,'배틀','2023-05-17 09:37:46',15,'AT012'),(584,500,'배틀','2023-05-17 09:37:46',34,'AT012'),(585,-50,'훈련','2023-05-17 09:45:18',34,'AT005'),(586,-50,'훈련','2023-05-17 09:45:38',34,'AT005'),(587,-600,'아이스크림 구매','2023-05-17 09:46:21',34,'SN013'),(588,-50,'훈련','2023-05-17 09:46:52',34,'AT005'),(589,200,'스마트싱스 허브충전 보너스','2023-05-17 09:54:44',15,'ST002'),(590,200,'스마트싱스 허브충전 완료 보너스','2023-05-17 11:30:24',15,'ST003'),(591,100,'스마트싱스 문열림센서 보너스','2023-05-17 12:17:23',15,'ST001'),(592,22,'산책','2023-05-17 13:04:22',41,'AT004'),(593,22,'산책','2023-05-17 13:04:22',41,'AT004'),(594,380,'페이 씨유녹산공단점 결제','2023-05-17 13:05:56',41,'PY000'),(595,-3,'별사탕 구매','2023-05-17 13:07:42',41,'FD000'),(596,-3,'별사탕 구매','2023-05-17 13:27:11',41,'FD000'),(597,80,'페이 주식회사 이스턴웰스 결제','2023-05-17 13:56:56',15,'PY000'),(598,80,'페이 주식회사 이스턴웰스 결제','2023-05-17 13:57:41',15,'PY000'),(599,-3,'별사탕 구매','2023-05-17 13:59:25',15,'FD000'),(600,-500,'배틀','2023-05-17 14:06:46',34,'AT012'),(601,500,'배틀','2023-05-17 14:06:46',15,'AT012'),(602,-1000,'스테이크 구매','2023-05-17 14:08:36',15,'FD022'),(603,-5000,'우주음식 구매','2023-05-17 14:08:42',34,'FD030'),(604,200,'스마트싱스 허브충전 보너스','2023-05-17 14:14:08',15,'ST002'),(605,200,'스마트싱스 허브충전 완료 보너스','2023-05-17 14:14:26',15,'ST003'),(606,-5000,'우주음식 구매','2023-05-17 14:35:20',39,'FD030'),(607,-3,'별사탕 구매','2023-05-17 15:30:48',39,'FD000'),(608,-3,'별사탕 구매','2023-05-17 15:42:27',42,'FD000'),(609,-300,'초콜릿 구매','2023-05-17 15:43:48',42,'SN000'),(610,-500,'사과 구매','2023-05-17 15:44:13',42,'FD010'),(611,-500,'사과 구매','2023-05-17 15:52:34',39,'FD010'),(612,-200,'사과 구매','2023-05-17 16:25:25',42,'FD010'),(613,-200,'삼각김밥 구매','2023-05-17 16:39:34',39,'FD011'),(614,-3,'별사탕 구매','2023-05-17 16:41:12',41,'FD000'),(615,-200,'샌드위치 구매','2023-05-17 16:41:38',39,'FD012'),(616,-3,'별사탕 구매','2023-05-17 17:49:04',41,'FD000'),(617,-200,'사과 구매','2023-05-17 18:46:39',39,'FD010'),(618,54,'산책','2023-05-17 21:19:40',15,'AT004'),(619,54,'산책','2023-05-17 21:21:08',15,'AT004'),(620,54,'산책','2023-05-17 23:14:07',39,'AT004'),(621,-200,'삼각김밥 구매','2023-05-17 23:35:55',39,'FD011'),(622,-3,'별사탕 구매','2023-05-18 00:51:05',39,'FD000'),(623,-3,'별사탕 구매','2023-05-18 10:31:02',41,'FD000'),(624,-200,'사과 구매','2023-05-18 10:31:37',41,'FD010'),(625,-3,'별사탕 구매','2023-05-18 10:39:28',15,'FD000'),(626,-3,'별사탕 구매','2023-05-18 10:58:41',41,'FD000'),(627,500,'스마트싱스 청소 보너스','2023-05-18 11:08:19',15,'ST000'),(628,-3,'별사탕 구매','2023-05-18 11:30:57',41,'FD000'),(629,700,'페이 고모리커피공장 녹산점 결제','2023-05-18 13:03:58',45,'PY000'),(630,-3,'별사탕 구매','2023-05-18 13:13:57',34,'FD000'),(631,-200,'사과 구매','2023-05-18 13:14:08',34,'FD010'),(632,200,'스마트싱스 허브충전 보너스','2023-05-18 13:33:16',15,'ST002'),(633,-3,'별사탕 구매','2023-05-18 13:33:40',41,'FD000'),(634,-50,'훈련','2023-05-18 13:34:33',41,'AT005'),(635,100,'스마트싱스 문열림센서 보너스','2023-05-18 13:40:21',15,'ST001'),(636,500,'배틀','2023-05-18 13:47:26',34,'AT012'),(637,-500,'배틀','2023-05-18 13:47:26',15,'AT012'),(638,-500,'배틀','2023-05-18 13:48:21',34,'AT012'),(639,500,'배틀','2023-05-18 13:48:21',15,'AT012'),(640,-500,'배틀','2023-05-18 13:51:20',34,'AT012'),(641,500,'배틀','2023-05-18 13:51:20',15,'AT012'),(642,-5000,'우주음식 구매','2023-05-18 13:56:46',34,'FD030'),(643,-3,'별사탕 구매','2023-05-18 14:07:51',41,'FD000'),(644,-350,'아이스크림 구매','2023-05-18 15:02:28',15,'SN013'),(645,200,'스마트싱스 허브충전 완료 보너스','2023-05-18 15:03:20',15,'ST003'),(646,-5000,'우주음식 구매','2023-05-18 15:33:06',34,'FD030'),(647,-50,'훈련','2023-05-18 15:34:22',34,'AT005'),(648,-50,'훈련','2023-05-18 15:35:35',34,'AT005'),(649,-50,'훈련','2023-05-18 15:35:35',34,'AT005'),(650,-50,'훈련','2023-05-18 15:35:35',34,'AT005'),(651,-50,'훈련','2023-05-18 15:35:35',34,'AT005'),(652,-50,'훈련','2023-05-18 15:35:35',34,'AT005'),(653,-50,'훈련','2023-05-18 15:36:07',34,'AT005'),(654,-50,'훈련','2023-05-18 15:36:44',34,'AT005'),(655,500,'스마트싱스 청소 보너스','2023-05-18 16:07:43',34,'ST000'),(656,-3,'별사탕 구매','2023-05-18 16:47:18',41,'FD000'),(657,-200,'삼각김밥 구매','2023-05-18 16:47:30',15,'FD011'),(658,-500,'배틀','2023-05-18 16:48:26',15,'AT012'),(659,500,'배틀','2023-05-18 16:48:27',34,'AT012'),(660,60,'페이 (주)이스턴웰스 본사 결제','2023-05-18 16:49:46',34,'PY000'),(661,60,'페이 (주)이스턴웰스 본사 결제','2023-05-18 16:50:15',34,'PY000'),(662,-200,'삼각김밥 구매','2023-05-18 17:24:40',15,'FD011'),(663,-500,'배틀','2023-05-18 17:25:28',15,'AT012'),(664,500,'배틀','2023-05-18 17:25:28',34,'AT012'),(665,60,'페이 (주)이스턴웰스 본사 결제','2023-05-18 17:26:41',34,'PY000'),(666,60,'페이 (주)이스턴웰스 본사 결제','2023-05-18 17:27:19',34,'PY000'),(667,-200,'삼각김밥 구매','2023-05-18 17:53:35',15,'FD011'),(668,-500,'배틀','2023-05-18 17:54:44',15,'AT012'),(669,500,'배틀','2023-05-18 17:54:44',34,'AT012'),(670,-200,'삼각김밥 구매','2023-05-18 17:59:31',15,'FD011'),(671,-500,'배틀','2023-05-18 18:00:38',15,'AT012'),(672,500,'배틀','2023-05-18 18:00:38',34,'AT012'),(673,122,'산책','2023-05-18 18:41:15',15,'AT004'),(674,6950,'페이 (주)우아한형제들 결제','2023-05-18 18:44:53',15,'PY000'),(675,-200,'사과 구매','2023-05-18 19:06:00',39,'FD010'),(676,-150,'사탕 구매','2023-05-18 19:06:08',39,'SN001'),(677,-50,'훈련','2023-05-18 19:06:38',39,'AT005'),(678,5170,'페이 디스카운트 결제','2023-05-18 19:21:20',41,'PY000'),(679,-200,'사과 구매','2023-05-18 20:28:12',15,'FD010'),(680,-5000,'우주음식 구매','2023-05-18 20:37:37',34,'FD030'),(681,500,'배틀','2023-05-18 20:41:36',15,'AT012'),(682,-500,'배틀','2023-05-18 20:41:36',34,'AT012'),(683,-500,'배틀','2023-05-18 20:43:19',15,'AT012'),(684,500,'배틀','2023-05-18 20:43:19',34,'AT012'),(685,-500,'배틀','2023-05-18 20:44:05',34,'AT012'),(686,500,'배틀','2023-05-18 20:44:05',15,'AT012'),(687,-500,'배틀','2023-05-18 20:45:09',34,'AT012'),(688,500,'배틀','2023-05-18 20:45:09',15,'AT012'),(689,-500,'배틀','2023-05-18 20:45:31',34,'AT012'),(690,500,'배틀','2023-05-18 20:45:31',15,'AT012'),(691,-500,'배틀','2023-05-18 20:46:17',15,'AT012'),(692,500,'배틀','2023-05-18 20:46:17',34,'AT012'),(693,-500,'배틀','2023-05-18 20:48:24',34,'AT012'),(694,500,'배틀','2023-05-18 20:48:24',15,'AT012'),(695,500,'배틀','2023-05-18 21:08:04',15,'AT012'),(696,-500,'배틀','2023-05-18 21:08:04',39,'AT012'),(697,-500,'배틀','2023-05-18 21:12:11',39,'AT012'),(698,500,'배틀','2023-05-18 21:12:11',15,'AT012'),(699,-500,'배틀','2023-05-18 21:12:30',15,'AT012'),(700,500,'배틀','2023-05-18 21:12:30',39,'AT012'),(701,-500,'배틀','2023-05-18 21:14:15',39,'AT012'),(702,500,'배틀','2023-05-18 21:14:15',15,'AT012'),(703,-5000,'우주음식 구매','2023-05-18 21:20:47',15,'FD030'),(704,-50,'훈련','2023-05-18 22:11:23',15,'AT005'),(705,-50,'훈련','2023-05-18 22:12:35',15,'AT005'),(706,-150,'초콜릿 구매','2023-05-18 22:13:45',15,'SN000'),(707,-50,'훈련','2023-05-18 22:14:35',15,'AT005'),(708,-500,'스테이크 구매','2023-05-18 22:21:17',39,'FD022'),(709,-50,'훈련','2023-05-18 22:22:27',39,'AT005'),(710,-150,'초콜릿 구매','2023-05-18 22:26:46',15,'SN000'),(711,-50,'훈련','2023-05-18 22:27:55',15,'AT005'),(712,-5000,'우주음식 구매','2023-05-18 22:28:19',15,'FD030'),(713,-50,'훈련','2023-05-18 22:29:14',15,'AT005');
/*!40000 ALTER TABLE `point_history` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-18 22:53:01