-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: boutiq14_mbogni
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categorie`
--

DROP TABLE IF EXISTS `categorie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorie` (
  `idCat` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nomCat` varchar(60) NOT NULL,
  PRIMARY KEY (`idCat`)
) ENGINE=InnoDB AUTO_INCREMENT=1560 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorie`
--

LOCK TABLES `categorie` WRITE;
/*!40000 ALTER TABLE `categorie` DISABLE KEYS */;
INSERT INTO `categorie` VALUES (1551,'Boissons'),(1552,'Legumes'),(1553,'Viande'),(1554,'Boisson alcoolisée'),(1555,'Bonbons'),(1556,'Produit de Menage'),(1557,'les produits laitiers'),(1558,'Chocolat');
/*!40000 ALTER TABLE `categorie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `idClient` int(4) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) NOT NULL DEFAULT 'ND',
  `tel` varchar(90) DEFAULT NULL,
  `adresse` varchar(90) DEFAULT NULL,
  PRIMARY KEY (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (3,'default','default','default'),(4,'Otang Glen Orock','+237 683 07 05 82','Terre-Rouge Damas');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facture`
--

DROP TABLE IF EXISTS `facture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facture` (
  `idFac` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dateFac` datetime NOT NULL,
  `remise` decimal(4,2) NOT NULL,
  `montant` decimal(10,2) NOT NULL,
  `tel` varchar(15) DEFAULT NULL,
  `typeFac` tinyint(1) NOT NULL DEFAULT '0',
  `idCaissiere` int(10) unsigned NOT NULL,
  `Client_idClient` int(4) NOT NULL,
  PRIMARY KEY (`idFac`),
  KEY `fk_idCaiss_idx` (`idCaissiere`),
  KEY `fk_facture_Client1_idx` (`Client_idClient`),
  CONSTRAINT `fk_facture_Client1` FOREIGN KEY (`Client_idClient`) REFERENCES `client` (`idClient`),
  CONSTRAINT `fk_idCaiss` FOREIGN KEY (`idCaissiere`) REFERENCES `gestionnaire` (`idGest`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11166 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facture`
--

LOCK TABLES `facture` WRITE;
/*!40000 ALTER TABLE `facture` DISABLE KEYS */;
INSERT INTO `facture` VALUES (11111,'2020-05-28 06:40:23',0.00,15000.00,NULL,1,10,3),(11112,'2020-05-29 10:40:23',0.00,20000.00,NULL,1,7,4),(11113,'2020-05-10 12:50:30',0.00,5000.00,NULL,1,8,4),(11114,'2020-06-10 08:36:32',0.00,48000.00,NULL,1,7,3),(11115,'2020-06-10 08:39:10',0.00,47250.00,NULL,1,7,3),(11116,'2020-06-10 09:16:28',0.00,5500.00,NULL,1,7,3),(11117,'2020-06-10 09:20:19',0.00,6500.00,NULL,1,7,3),(11118,'2020-06-10 09:29:50',0.00,5000.00,NULL,1,7,3),(11119,'2020-06-09 00:00:00',0.00,50000.00,NULL,1,8,3),(11120,'2020-06-08 00:00:00',0.00,45000.00,NULL,1,10,3),(11121,'2020-06-05 00:00:00',0.00,50000.00,NULL,1,8,3),(11122,'2020-06-04 00:00:00',0.00,45000.00,NULL,1,10,4),(11123,'2020-06-03 00:00:00',0.00,20000.00,NULL,1,8,3),(11124,'2020-06-02 00:00:00',0.00,50000.00,NULL,1,10,4),(11125,'2020-05-25 00:00:00',0.00,30000.00,NULL,1,8,3),(11126,'2020-05-15 00:00:00',0.00,50000.00,NULL,1,10,4),(11127,'2020-05-09 00:00:00',0.00,25000.00,NULL,1,8,3),(11128,'2020-05-08 00:00:00',0.00,75000.00,NULL,1,10,3),(11129,'2020-04-09 00:00:00',0.00,16000.00,NULL,1,8,3),(11130,'2020-03-08 00:00:00',0.00,45000.00,NULL,1,10,4),(11131,'2020-05-01 00:00:00',0.00,10000.00,NULL,1,8,3),(11132,'2020-02-08 00:00:00',0.00,4000.00,NULL,1,10,3),(11133,'2020-01-05 00:00:00',0.00,50000.00,NULL,1,8,3),(11134,'2020-02-04 00:00:00',0.00,45000.00,NULL,1,10,4),(11135,'2020-03-03 00:00:00',0.00,20000.00,NULL,1,8,3),(11136,'2019-06-02 00:00:00',0.00,50000.00,NULL,1,10,4),(11137,'2019-05-25 00:00:00',0.00,30000.00,NULL,1,8,3),(11138,'2019-05-15 00:00:00',0.00,50000.00,NULL,1,10,4),(11139,'2019-05-09 00:00:00',0.00,25000.00,NULL,1,8,3),(11140,'2019-05-08 00:00:00',0.00,75000.00,NULL,1,10,3),(11141,'2019-04-09 00:00:00',0.00,16000.00,NULL,1,8,3),(11142,'2019-03-08 00:00:00',0.00,45000.00,NULL,1,10,4),(11143,'2019-05-01 00:00:00',0.00,10000.00,NULL,1,8,3),(11144,'2019-02-08 00:00:00',0.00,4000.00,NULL,1,10,3),(11145,'2020-06-10 10:45:25',0.00,10000.00,NULL,1,7,3),(11146,'2020-06-10 13:50:16',0.00,24875.00,NULL,1,7,4),(11147,'2020-06-10 14:26:21',0.00,55025.00,NULL,1,7,4),(11148,'2020-06-10 14:57:20',0.00,101775.00,NULL,1,7,3),(11149,'2020-06-12 16:58:15',0.00,500.00,NULL,1,7,4),(11150,'2020-06-12 17:03:49',0.00,1000.00,NULL,1,7,3),(11151,'2020-06-12 17:10:15',0.00,1500.00,NULL,1,7,3),(11152,'2020-06-12 17:13:40',0.00,31050.00,NULL,1,7,3),(11153,'2020-06-12 17:16:37',0.00,500.00,NULL,1,7,3),(11154,'2020-06-13 08:12:10',0.00,8000.00,NULL,1,7,4),(11155,'2020-06-13 08:20:47',0.00,400.00,NULL,1,7,4),(11156,'2020-06-13 08:49:11',0.00,1500.00,NULL,1,7,4),(11157,'2020-06-13 08:54:56',0.00,1000.00,NULL,1,7,3),(11158,'2020-06-13 08:58:35',0.00,500.00,NULL,1,7,4),(11159,'2020-06-13 09:45:38',0.00,500.00,NULL,1,7,3),(11160,'2020-06-13 09:46:41',0.00,300.00,NULL,1,7,4),(11161,'2020-06-13 10:04:25',0.00,500.00,NULL,1,7,4),(11162,'2020-06-13 10:30:24',0.00,3565.00,NULL,1,7,4),(11163,'2020-06-13 10:41:00',0.00,3575.00,NULL,1,7,3),(11164,'2020-06-13 11:12:30',0.00,1000.00,NULL,1,7,3),(11165,'2020-06-13 11:19:14',0.00,1000.00,NULL,1,7,4);
/*!40000 ALTER TABLE `facture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fournisseur`
--

DROP TABLE IF EXISTS `fournisseur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fournisseur` (
  `codeFour` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) DEFAULT NULL,
  `Adresse` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`codeFour`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fournisseur`
--

LOCK TABLES `fournisseur` WRITE;
/*!40000 ALTER TABLE `fournisseur` DISABLE KEYS */;
INSERT INTO `fournisseur` VALUES (1,'glen','terre-rouge'),(2,'Musa','Melen'),(3,'Damaris','Round Point Express'),(4,'Mario','Cité U'),(5,'Otang Glen Orock','Nsimeyoung Terre Rouge'),(7,'Memetal Engineering','Damas'),(8,'default','default'),(9,'Matip ','Ngousso');
/*!40000 ALTER TABLE `fournisseur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gestionnaire`
--

DROP TABLE IF EXISTS `gestionnaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gestionnaire` (
  `idGest` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nomGest` varchar(45) NOT NULL,
  `typeGest` enum('Gestionnaire','Caissiere','Magazinier') NOT NULL,
  `login` varchar(20) NOT NULL,
  `pwd` varchar(20) NOT NULL,
  `actif` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idGest`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gestionnaire`
--

LOCK TABLES `gestionnaire` WRITE;
/*!40000 ALTER TABLE `gestionnaire` DISABLE KEYS */;
INSERT INTO `gestionnaire` VALUES (1,'Boss','Gestionnaire','gest','gest',1),(3,'Orock Glen ','Gestionnaire','glen','glen',1),(7,'Madjio Cycy','Caissiere','cycy','cycy',1),(8,'Matip Gwet','Caissiere','matip','matip',1),(9,'Manengono Guy Mario','Magazinier','guy','guy',1),(10,'Otang Glen Orock','Caissiere','cass','cass',1),(11,'Orock Glen Otang','Magazinier','sales','sales',1),(12,'Admin','Gestionnaire','Admin','Admin',1),(13,'57u5u','Caissiere','kykyyk','123',1),(14,'Damaris','Gestionnaire','Damaris','damaris',1);
/*!40000 ALTER TABLE `gestionnaire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gestionstock`
--

DROP TABLE IF EXISTS `gestionstock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gestionstock` (
  `idStock` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `qte` int(10) unsigned NOT NULL,
  `dateStock` datetime NOT NULL,
  `operation` tinyint(1) NOT NULL DEFAULT '0',
  `idGest` int(10) unsigned NOT NULL,
  `codePro` int(8) unsigned NOT NULL,
  PRIMARY KEY (`idStock`),
  KEY `fk_idGest_stock_idx` (`idGest`),
  KEY `fk_codePro_stock_idx` (`codePro`),
  CONSTRAINT `fk_codePro_stock` FOREIGN KEY (`codePro`) REFERENCES `produit` (`codePro`),
  CONSTRAINT `fk_idGest_stock` FOREIGN KEY (`idGest`) REFERENCES `gestionnaire` (`idGest`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gestionstock`
--

LOCK TABLES `gestionstock` WRITE;
/*!40000 ALTER TABLE `gestionstock` DISABLE KEYS */;
INSERT INTO `gestionstock` VALUES (21,15,'2020-06-09 16:57:20',1,9,1),(22,10,'2020-06-10 08:54:34',0,11,10),(23,15,'2020-06-10 00:00:00',1,9,2),(24,20,'2020-06-01 00:00:00',1,11,13),(25,5,'2020-06-08 00:00:00',1,9,8),(26,5,'2020-06-08 00:00:00',1,11,7),(27,4,'2020-06-07 00:00:00',1,9,8),(37,4,'2020-06-07 00:00:00',1,9,1),(38,9,'2020-06-07 00:00:00',1,11,2),(39,11,'2020-06-06 00:00:00',1,9,3),(40,13,'2020-06-06 00:00:00',1,11,4),(41,4,'2020-06-05 00:00:00',1,9,5),(42,8,'2020-03-05 00:00:00',1,11,9),(43,6,'2020-06-04 00:00:00',1,9,10),(44,12,'2020-04-03 00:00:00',1,11,11),(45,17,'2020-06-02 00:00:00',1,9,123001),(46,15,'2020-05-16 00:00:00',1,11,123001),(47,5,'2020-06-10 14:10:36',0,3,11),(48,8,'2020-06-10 14:10:46',1,3,11),(49,20,'2020-06-10 14:20:11',1,3,123012),(50,15,'2020-06-10 14:44:23',1,3,123013),(51,10,'2020-06-10 14:50:01',1,3,123015),(52,50,'2020-06-10 14:51:43',1,3,123016),(53,50,'2020-06-10 14:53:29',1,3,123017);
/*!40000 ALTER TABLE `gestionstock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lignefacture`
--

DROP TABLE IF EXISTS `lignefacture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lignefacture` (
  `idLFac` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `codePro` int(6) unsigned NOT NULL,
  `idFac` int(10) unsigned NOT NULL,
  `prix` decimal(10,2) NOT NULL,
  `qte` smallint(4) unsigned NOT NULL,
  PRIMARY KEY (`idLFac`),
  UNIQUE KEY `idx_fac_pro` (`codePro`,`idFac`),
  KEY `fk_idFac` (`idFac`),
  CONSTRAINT `fk_codePro` FOREIGN KEY (`codePro`) REFERENCES `produit` (`codePro`),
  CONSTRAINT `fk_idFac` FOREIGN KEY (`idFac`) REFERENCES `facture` (`idFac`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lignefacture`
--

LOCK TABLES `lignefacture` WRITE;
/*!40000 ALTER TABLE `lignefacture` DISABLE KEYS */;
INSERT INTO `lignefacture` VALUES (1,1,11113,1000.00,2),(2,2,11113,2000.00,4),(3,123001,11113,2000.00,2),(4,8,11112,14300.00,4),(5,1,11112,1000.00,2),(6,2,11112,1500.00,2),(7,3,11112,1000.00,2),(8,4,11112,2000.00,4),(9,13,11112,200.00,4),(10,11,11111,9000.00,3),(11,10,11111,5000.00,2),(12,123001,11111,1000.00,1),(13,11,11115,45000.00,15),(14,123000,11115,2250.00,15),(15,4,11116,1000.00,2),(16,1,11116,2000.00,4),(17,2,11116,2500.00,5),(18,1,11117,2500.00,5),(19,5,11117,4000.00,8),(20,123001,11118,5000.00,5),(21,1,11145,5000.00,10),(22,2,11145,5000.00,10),(23,11,11146,6000.00,2),(24,1,11146,1000.00,2),(25,8,11146,17875.00,5),(26,123012,11147,43275.00,5),(27,4,11147,7500.00,15),(28,123000,11147,2250.00,15),(29,123001,11147,2000.00,2),(30,123017,11148,2500.00,5),(31,123016,11148,10000.00,10),(32,123015,11148,36000.00,3),(33,123013,11148,10000.00,5),(34,123012,11148,43275.00,5),(35,5,11149,500.00,1),(36,1,11150,1000.00,2),(37,2,11151,1500.00,3),(38,1,11152,2500.00,5),(39,123001,11152,2000.00,2),(40,123000,11152,750.00,5),(41,123016,11152,4000.00,4),(42,123017,11152,2000.00,4),(43,8,11152,14300.00,4),(44,5,11152,5500.00,11),(45,2,11153,500.00,1),(46,11,11154,3000.00,1),(47,2,11154,2500.00,5),(48,1,11154,2500.00,5),(49,12,11155,400.00,1),(50,5,11156,500.00,1),(51,2,11156,500.00,1),(52,1,11156,500.00,1),(53,4,11157,500.00,1),(54,5,11157,500.00,1),(55,1,11158,500.00,1),(56,5,11159,500.00,1),(57,123000,11160,300.00,2),(58,2,11161,500.00,1),(59,7,11162,3565.00,1),(60,8,11163,3575.00,1),(61,1,11164,500.00,1),(62,5,11164,500.00,1),(63,1,11165,500.00,1),(64,5,11165,500.00,1);
/*!40000 ALTER TABLE `lignefacture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produit`
--

DROP TABLE IF EXISTS `produit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produit` (
  `codePro` int(6) unsigned NOT NULL AUTO_INCREMENT,
  `nomPro` varchar(100) NOT NULL DEFAULT 'ND',
  `prixVente` decimal(8,0) DEFAULT NULL,
  `prixAchat` decimal(8,0) NOT NULL,
  `qte` int(8) unsigned NOT NULL,
  `description` varchar(100) NOT NULL DEFAULT 'RAS',
  `codeFour` int(11) DEFAULT NULL,
  `dateInsertion` datetime DEFAULT CURRENT_TIMESTAMP,
  `actif` tinyint(1) NOT NULL DEFAULT '1',
  `idCategorie` int(10) unsigned NOT NULL,
  PRIMARY KEY (`codePro`),
  KEY `fk_idCategorie` (`idCategorie`),
  KEY `codeFour` (`codeFour`),
  CONSTRAINT `fk_idCategorie` FOREIGN KEY (`idCategorie`) REFERENCES `categorie` (`idCat`) ON UPDATE CASCADE,
  CONSTRAINT `produit_ibfk_1` FOREIGN KEY (`codeFour`) REFERENCES `fournisseur` (`codeFour`)
) ENGINE=InnoDB AUTO_INCREMENT=123018 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produit`
--

LOCK TABLES `produit` WRITE;
/*!40000 ALTER TABLE `produit` DISABLE KEYS */;
INSERT INTO `produit` VALUES (1,'Grenadine',500,450,102,'RAS',3,'2020-05-11 16:25:34',1,1551),(2,'Coke',500,450,100,'RAS',3,'2020-05-11 16:25:53',1,1551),(3,'Fanta',500,450,147,'RAS',3,'2020-05-11 16:26:17',1,1551),(4,'Jus Naturel',500,450,48,'RAS',3,'2020-05-11 16:26:32',1,1551),(5,'Planet Geant',500,450,66,'RAS',3,'2020-05-11 18:22:21',1,1551),(7,'J.P.Chenet Black Chardonnay',3565,3000,49,'Vin Rouge',3,'2020-05-12 04:10:15',0,1554),(8,'J.P.Chenet Sauvignon Blanc 75Cl',3575,3200,55,'Vin Blanc',3,'2020-05-12 04:17:53',0,1554),(9,'Powers Gold Label',25500,23900,11,'Irish Whiskey',5,'2020-05-12 12:11:18',0,1554),(10,'Poulet Congèlé',2500,2250,18,'Poulet',4,'2020-05-12 12:30:35',0,1553),(11,'viande de porc congelée',3000,2600,25,'PorK Meat 1kg',2,'2020-05-12 12:50:51',0,1553),(12,'Eau Mineral',400,350,68,'Pure Water',8,'2020-05-13 15:59:27',1,1551),(13,'Biscuit O\'Choco',50,30,250,'Biscuit vendu en detaille',3,'2020-05-22 09:46:32',1,1555),(123000,'TicTac',150,130,143,'RAS',3,'2020-05-22 09:53:33',1,1555),(123001,'Bavaria',1000,750,41,'',4,'2020-05-22 10:00:00',1,1554),(123012,'Nido 900g',8655,6924,10,'',3,'2020-06-10 14:19:46',1,1557),(123013,'Tartina',2000,1702,10,'TARTINA Chocolate Spread-450g',2,'2020-06-10 14:44:13',1,1558),(123015,'Tartina 5L',12000,11160,7,'TARTINA Chocolate Spread-5L',2,'2020-06-10 14:48:09',1,1558),(123016,'Hebert-White-Chocolate',1000,750,36,'Solid White Chocolate',3,'2020-06-10 14:51:21',1,1558),(123017,'KitKat',500,450,41,'Bar Chocolaté',3,'2020-06-10 14:53:22',1,1555);
/*!40000 ALTER TABLE `produit` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-14  9:06:06
