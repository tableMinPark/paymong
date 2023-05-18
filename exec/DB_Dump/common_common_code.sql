CREATE DATABASE  IF NOT EXISTS `common` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `common`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: k8e103.p.ssafy.io    Database: common
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
-- Table structure for table `common_code`
--

DROP TABLE IF EXISTS `common_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `common_code` (
  `code` varchar(10) NOT NULL,
  `name` varchar(30) NOT NULL,
  `group_code` varchar(10) NOT NULL,
  `group_name` varchar(10) NOT NULL,
  PRIMARY KEY (`code`),
  KEY `common_code_group_code_group_code_idx` (`group_code`),
  CONSTRAINT `common_code_group_code_group_code` FOREIGN KEY (`group_code`) REFERENCES `group_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `common_code`
--

LOCK TABLES `common_code` WRITE;
/*!40000 ALTER TABLE `common_code` DISABLE KEYS */;
INSERT INTO `common_code` VALUES ('AT000','식사','AT','활동'),('AT001','간식','AT','활동'),('AT002','배변','AT','활동'),('AT003','수면','AT','활동'),('AT004','산책','AT','활동'),('AT005','훈련','AT','활동'),('AT006','쓰다듬기','AT','활동'),('AT007','기상','AT','활동'),('AT008','청소','AT','활동'),('AT009','진화','AT','활동'),('AT010','졸업','AT','활동'),('AT011','패널티','AT','활동'),('AT012','배틀','AT','활동'),('AT013','충전','AT','활동'),('AT014','충전 중지','AT','활동'),('CD000','정상','CD','컨디션'),('CD001','아픔','CD','컨디션'),('CD002','수면','CD','컨디션'),('CD003','졸림','CD','컨디션'),('CD004','배고픔','CD','컨디션'),('CD005','죽음','CD','컨디션'),('CD006','졸업','CD','컨디션'),('CD007','진화대기','CD','컨디션'),('CD008','먹는중','CD','컨디션'),('CH000','화산알','CH','캐릭터'),('CH001','석탄알','CH','캐릭터'),('CH002','황금알','CH','캐릭터'),('CH003','목성알','CH','캐릭터'),('CH004','지구알','CH','캐릭터'),('CH005','바람알','CH','캐릭터'),('CH100','별몽','CH','캐릭터'),('CH101','동글몽','CH','캐릭터'),('CH102','네몽','CH','캐릭터'),('CH200','안씻은 별별몽','CH','캐릭터'),('CH201','안씻은 둥글몽','CH','캐릭터'),('CH202','안씻은 나네몽','CH','캐릭터'),('CH203','까몽','CH','캐릭터'),('CH210','무난한 별별몽','CH','캐릭터'),('CH211','무난한 둥글몽','CH','캐릭터'),('CH212','무난한 나네몽','CH','캐릭터'),('CH220','유쾌한 별별몽','CH','캐릭터'),('CH221','유쾌한 둥글몽','CH','캐릭터'),('CH222','유쾌한 나네몽','CH','캐릭터'),('CH230','완벽한 별별몽','CH','캐릭터'),('CH231','완벽한 둥글몽','CH','캐릭터'),('CH232','완벽한 나네몽','CH','캐릭터'),('CH300','병든 별뿔몽','CH','캐릭터'),('CH301','병든 땡글몽','CH','캐릭터'),('CH302','병든 마미무메몽','CH','캐릭터'),('CH303','새까몽','CH','캐릭터'),('CH310','평범한 별뿔몽','CH','캐릭터'),('CH311','평범한 땡글몽','CH','캐릭터'),('CH312','평범한 마미무메몽','CH','캐릭터'),('CH320','매력적인 별뿔몽','CH','캐릭터'),('CH321','매력적인 땡글몽','CH','캐릭터'),('CH322','매력적인 마미무메몽','CH','캐릭터'),('CH330','엘리트 별뿔몽','CH','캐릭터'),('CH331','엘리트 땡글몽','CH','캐릭터'),('CH332','엘리트 마미무메몽','CH','캐릭터'),('FD000','별사탕','FD','음식'),('FD010','사과','FD','음식'),('FD011','삼각김밥','FD','음식'),('FD012','샌드위치','FD','음식'),('FD020','피자','FD','음식'),('FD021','닭다리','FD','음식'),('FD022','스테이크','FD','음식'),('FD030','우주음식','FD','음식'),('MP000','기본','MP','맵'),('MP001','스타벅스','MP','맵'),('MP002','이디야','MP','맵'),('MP003','할리스','MP','맵'),('MP004','투썸플레이스','MP','맵'),('MP005','메가커피','MP','맵'),('MP006','파스쿠찌','MP','맵'),('MP007','블루보틀','MP','맵'),('MP008','하이오커피','MP','맵'),('MP009','블루샥','MP','맵'),('MP010','컴포즈커피','MP','맵'),('MP011','맥도날드','MP','맵'),('MP012','버거킹','MP','맵'),('MP013','롯데리아','MP','맵'),('MP014','맘스터치','MP','맵'),('MP015','CGV','MP','맵'),('MP016','메가박스','MP','맵'),('MP017','이마트24','MP','맵'),('MP018','GS25','MP','맵'),('MP019','CU','MP','맵'),('MP020','올리브영','MP','맵'),('MP021','나이키','MP','맵'),('MP022','아디다스','MP','맵'),('MP023','다이소','MP','맵'),('MP024','PC방','MP','맵'),('MP025','노래방','MP','맵'),('MP026','던킨도너츠','MP','맵'),('MP027','파리바게뜨','MP','맵'),('MP028','뚜레쥬르','MP','맵'),('MP029','배스킨라빈스31','MP','맵'),('MP030','설빙','MP','맵'),('MP031','KFC','MP','맵'),('MP032','롯데시네마','MP','맵'),('MP033','쿠팡','MP','맵'),('MP034','요기요','MP','맵'),('MP035','배달의민족','MP','맵'),('MP036','카페','MP','맵'),('MP037','세븐일레븐','MP','맵'),('MP038','싸피자판기','MP','맵'),('PY000','삼성페이','PY','결제'),('SN000','초콜릿','SN','간식'),('SN001','사탕','SN','간식'),('SN002','음료수','SN','간식'),('SN010','쿠키','SN','간식'),('SN011','케이크','SN','간식'),('SN012','감튀','SN','간식'),('SN013','아이스크림','SN','간식'),('ST000','청소기','ST','스마트싱스'),('ST001','문열림센서','ST','스마트싱스'),('ST002','허브무선충전','ST','스마트싱스'),('ST003','허브무선충전중지','ST','스마트싱스');
/*!40000 ALTER TABLE `common_code` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-18 22:52:56
