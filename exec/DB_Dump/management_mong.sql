CREATE DATABASE  IF NOT EXISTS `management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `management`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: k8e103.p.ssafy.io    Database: management
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
-- Table structure for table `mong`
--

DROP TABLE IF EXISTS `mong`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mong` (
  `mong_id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint NOT NULL,
  `code` varchar(10) NOT NULL,
  `state_code` varchar(10) NOT NULL DEFAULT 'CD000',
  `name` varchar(255) NOT NULL,
  `reg_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `weight` int NOT NULL DEFAULT '5',
  `age` int NOT NULL DEFAULT '0',
  `strength` int NOT NULL DEFAULT '10',
  `satiety` int NOT NULL DEFAULT '10',
  `health` int NOT NULL DEFAULT '10',
  `sleep` int NOT NULL DEFAULT '10',
  `penalty` int NOT NULL DEFAULT '0',
  `training_count` int NOT NULL DEFAULT '0',
  `stroke_count` int NOT NULL DEFAULT '0',
  `poop_count` int NOT NULL DEFAULT '0',
  `sleep_start` time NOT NULL DEFAULT '08:00:00',
  `sleep_end` time NOT NULL DEFAULT '22:00:00',
  `active` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`mong_id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mong`
--

LOCK TABLES `mong` WRITE;
/*!40000 ALTER TABLE `mong` DISABLE KEYS */;
INSERT INTO `mong` VALUES (1,2,'CH002','CD005','옭옭이','2023-04-28 02:19:24',5,0,0,0,0,0,123,0,2,4,'08:00:00','08:00:00',0),(2,19,'CH444','CD006','나유몽','2023-04-29 10:28:34',25,0,10,0,0,9,3,0,1,4,'08:00:00','08:00:00',0),(3,11,'CH004','CD005','유민몽','2023-05-02 03:20:35',5,0,30,0,0,12,74,0,0,4,'08:00:00','21:00:00',0),(4,10,'CH002','CD005','황몬','2023-05-04 14:37:51',5,0,1,0,0,12,69,0,0,4,'08:00:00','21:00:00',0),(5,13,'CH000','CD005','황몬','2023-05-04 14:38:21',5,0,1,0,0,12,73,0,0,4,'08:00:00','21:00:00',0),(6,14,'CH000','CD005','몽','2023-05-04 14:41:44',5,0,1,0,0,0,70,0,0,4,'22:00:00','08:00:00',0),(7,15,'CH004','CD005','몽키','2023-05-04 14:48:25',5,0,1,0,0,9,7,11,1,4,'22:00:00','08:00:00',0),(8,16,'CH002','CD005','나몽','2023-05-04 14:54:24',5,0,1,0,0,0,66,0,0,4,'21:00:00','08:00:00',1),(9,18,'CH004','CD005','몽','2023-05-04 15:12:13',5,0,1,0,0,0,126,0,0,4,'22:00:00','00:00:00',1),(62,22,'CH310','CD005','test mong\n','2023-05-04 14:41:44',35,0,40,0,0,1,0,29,2,1,'22:00:00','08:00:00',0),(63,23,'CH300','CD005','황몽','2023-05-06 16:02:37',97,0,9,4,4,11,6,4,13,1,'23:00:00','05:00:00',0),(64,24,'CH003','CD004','모키몽','2023-05-06 23:03:58',9,0,1,1,1,6,0,0,5,0,'22:00:00','08:00:00',0),(66,24,'CH000','CD005','못키몽','2023-05-09 21:37:49',23,0,10,0,0,0,63,1,0,4,'22:00:00','09:00:00',1),(67,34,'CH321','CD005','유민아 그거 중독이야','2023-05-10 17:10:52',99,0,10,0,0,7,0,18,18,0,'00:00:00','08:00:00',0),(68,23,'CH312','CD005','알린','2023-05-12 01:45:15',99,0,17,0,0,5,52,3,19,4,'23:00:00','07:00:00',0),(69,15,'CH004','CD005','하몽','2023-05-12 04:37:21',5,0,10,10,10,10,0,0,0,0,'21:00:00','08:00:00',0),(70,15,'CH005','CD005','몽몽몽키','2023-05-12 04:43:12',5,0,10,10,10,10,0,0,0,0,'21:00:00','08:00:00',0),(71,15,'CH100','CD005','못키22','2023-05-12 04:46:51',15,0,10,10,10,10,0,0,0,0,'03:00:00','08:00:00',0),(72,19,'CH005','CD005','나나나','2023-05-12 04:50:34',5,0,10,10,10,10,0,0,0,0,'00:00:00','06:00:00',0),(73,15,'CH221','CD005','테스몽','2023-05-12 05:01:44',32,0,11,5,0,3,0,1,1,2,'21:00:00','09:00:00',0),(74,34,'CH202','CD000','이번엔 졸업','2023-05-12 14:43:03',95,0,16,15,5,6,0,2,0,0,'00:00:00','08:00:00',0),(75,22,'CH100','CD005','as','2023-05-12 15:24:50',20,0,15,0,0,2,0,2,3,2,'03:00:00','07:00:00',0),(77,19,'CH102','CD005','나나','2023-05-12 16:33:25',15,0,13,0,0,12,76,1,0,4,'00:00:00','06:00:00',1),(78,15,'CH003','CD005','몽몽','2023-05-12 16:47:47',5,0,10,10,10,10,0,0,7,0,'17:00:00','08:00:00',0),(79,34,'CH444','CD006','졸업가즈아','2023-05-12 16:58:59',99,0,10,29,20,16,0,8,19,0,'00:00:00','08:00:00',0),(80,15,'CH220','CD005','경훈몽','2023-05-12 17:08:16',29,0,18,0,0,5,25,3,4,4,'22:00:00','07:00:00',0),(81,22,'CH444','CD006','mongg','2023-05-12 21:55:14',25,0,10,0,0,0,12,0,2,4,'23:00:00','07:00:00',0),(82,22,'CH101','CD005','sangmin_mong','2023-05-13 19:08:00',17,0,13,0,0,0,21,1,4,4,'22:00:00','06:00:00',0),(83,36,'CH004','CD005','대머리','2023-05-14 01:27:32',5,0,10,0,0,0,65,0,1,4,'00:00:00','00:00:00',1),(84,15,'CH230','CD005','몽','2023-05-14 14:38:50',78,0,24,0,9,16,1,22,36,3,'00:00:00','07:00:00',0),(85,22,'CH102','CD005','mongkyyyyy','2023-05-14 16:11:12',15,0,10,0,0,13,6,0,9,4,'21:00:00','06:00:00',0),(86,22,'CH102','CD005','evol_test\n','2023-05-15 10:16:37',20,0,13,13,0,1,0,1,7,0,'21:00:00','08:00:00',0),(87,37,'CH101','CD005','youmeanmong','2023-05-15 13:52:54',15,0,10,8,8,9,0,0,2,1,'01:00:00','08:00:00',0),(88,37,'CH102','CD005','메타몽','2023-05-15 14:37:03',15,0,10,9,9,10,0,0,0,0,'00:00:00','09:00:00',0),(89,37,'CH000','CD005','메타몽','2023-05-15 15:08:39',5,0,10,10,10,10,0,0,0,0,'18:00:00','06:00:00',0),(90,37,'CH002','CD005','메타몽','2023-05-15 15:14:42',5,0,10,9,9,10,0,0,1,0,'01:00:00','08:00:00',0),(91,37,'CH000','CD005','메타몽\n','2023-05-15 15:49:40',5,0,10,10,10,10,0,0,0,0,'00:00:00','09:00:00',0),(92,37,'CH312','CD005','메타몽\n','2023-05-15 15:53:09',27,0,20,0,0,32,20,0,12,4,'01:00:00','06:00:00',1),(93,23,'CH101','CD005','모모몽','2023-05-15 16:18:42',15,0,10,17,17,18,0,0,4,1,'23:00:00','07:00:00',0),(94,22,'CH310','CD005','mongmongkyy','2023-05-15 16:25:41',36,0,10,0,0,16,2,0,2,1,'02:00:00','07:00:00',0),(95,34,'CH202','CD004','하이용','2023-05-15 21:09:05',97,0,25,2,18,22,9,9,11,3,'00:00:00','09:00:00',1),(96,22,'CH102','CD005','snagminmong','2023-05-16 09:13:02',15,0,10,10,10,10,0,0,3,0,'23:50:00','04:00:00',0),(97,15,'CH101','CD005','모몽모몽','2023-05-16 09:21:29',32,0,4,10,0,0,0,0,22,0,'23:00:00','06:00:00',0),(98,22,'CH101','CD005','ssssssssangmin','2023-05-16 09:27:40',15,0,10,10,10,10,0,0,0,0,'23:00:00','07:00:00',0),(99,22,'CH100','CD005','eggmoney','2023-05-16 09:39:46',15,0,10,10,10,10,0,0,1,0,'23:00:00','07:00:00',0),(100,22,'CH100','CD005','egg1','2023-05-16 09:46:31',15,0,10,10,10,10,0,0,1,0,'21:00:00','05:00:00',0),(101,22,'CH312','CD005','egg2\n','2023-05-16 09:57:56',35,0,10,9,9,10,0,0,2,0,'19:00:00','09:00:00',0),(102,22,'CH312','CD005','egg3','2023-05-16 10:15:36',35,0,10,10,10,10,0,0,1,0,'22:00:00','07:00:00',0),(103,22,'CH002','CD005','egg4','2023-05-16 10:27:58',5,0,10,10,10,10,0,0,1,0,'22:00:00','07:00:00',0),(104,22,'CH101','CD005','egg5','2023-05-16 10:33:45',15,0,10,10,10,10,0,0,6,0,'23:00:00','07:00:00',0),(105,22,'CH101','CD005','egg6','2023-05-16 10:45:43',15,0,10,10,10,10,0,0,1,0,'23:50:00','04:00:00',0),(106,22,'CH102','CD005','egg7','2023-05-16 10:56:47',16,0,9,0,0,0,13,0,1,4,'23:00:00','06:00:00',1),(107,23,'CH221','CD005','모모모모모무뭉','2023-05-16 11:08:34',80,0,10,0,0,0,26,1,3,4,'23:00:00','00:00:00',1),(108,39,'CH102','CD005','test_mong','2023-05-16 20:54:54',15,0,10,0,0,0,0,0,3,3,'22:00:00','06:00:00',0),(109,39,'CH201','CD005','test_mong','2023-05-17 00:07:57',99,0,0,20,20,19,9,0,8,1,'17:00:00','17:20:00',0),(110,41,'CH102','CD005','얼음저금통','2023-05-17 09:02:38',17,0,18,0,0,0,0,2,10,0,'22:00:00','08:10:00',0),(111,42,'CH101','CD005','ㅋ','2023-05-17 13:17:24',22,0,9,0,0,0,15,0,14,4,'00:00:00','00:00:00',0),(112,43,'CH101','CD005','코치몽','2023-05-17 13:54:39',15,0,10,0,0,0,17,0,21,4,'00:00:00','00:00:00',1),(113,15,'CH201','CD005','죽지마','2023-05-17 16:21:31',26,0,20,0,0,0,9,2,7,3,'23:00:00','00:00:00',0),(114,41,'CH221','CD002','얼음저금통2','2023-05-17 16:30:25',34,0,13,0,10,20,0,1,46,0,'20:00:00','06:00:00',1),(115,39,'CH220','CD005','test_mong2','2023-05-17 20:03:20',29,0,20,0,0,0,0,1,5,4,'23:50:00','00:00:00',0),(116,45,'CH001','CD005','지니몽','2023-05-18 09:33:36',5,0,10,0,0,2,0,0,2,4,'00:00:00','00:00:00',1),(117,42,'CH001','CD005','하하','2023-05-18 11:15:51',5,0,10,0,0,3,1,0,2,4,'00:00:00','00:10:00',1),(118,15,'CH005','CD005','죽지마2','2023-05-18 11:33:07',5,0,10,10,10,10,0,0,1,4,'23:00:00','07:00:00',0),(119,15,'CH102','CD000','죽지마3','2023-05-18 11:34:53',19,0,8,11,6,5,0,0,6,0,'23:00:00','05:00:00',0),(120,39,'CH102','CD005','test3','2023-05-18 12:47:43',15,0,10,0,0,1,0,0,2,0,'08:00:00','09:00:00',0),(121,15,'CH000','CD000','싸피몽','2023-05-18 16:46:43',28,0,10,10,10,10,0,0,1,0,'23:00:00','07:00:00',0),(122,15,'CH220','CD000','싸피몽','2023-05-18 17:24:11',28,0,10,20,10,3,0,0,1,0,'23:00:00','06:00:00',0),(123,15,'CH221','CD000','싸피몽','2023-05-18 17:53:07',28,0,10,20,10,3,0,0,1,0,'23:00:00','06:00:00',0),(124,15,'CH221','CD000','싸피몽','2023-05-18 17:59:13',42,0,35,32,33,33,0,6,9,0,'23:00:00','07:00:00',1),(125,39,'CH101','CD001','test_mong','2023-05-18 18:54:28',23,0,12,17,0,2,0,1,7,0,'08:00:00','08:20:00',1),(126,44,'CH005','CD007','ssafypaymong@gmail.com','2023-05-18 19:20:19',5,0,10,0,0,3,0,0,6,4,'00:00:00','00:00:00',1),(127,47,'CH003','CD007','앙기몽','2023-05-18 21:43:54',5,0,10,6,8,8,0,0,5,1,'23:00:00','08:00:00',1);
/*!40000 ALTER TABLE `mong` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-18 22:52:58