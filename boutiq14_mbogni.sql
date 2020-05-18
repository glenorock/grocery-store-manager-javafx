-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema boutiq14_mbogni
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema boutiq14_mbogni
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `boutiq14_mbogni` DEFAULT CHARACTER SET utf8 ;
USE `boutiq14_mbogni` ;

-- -----------------------------------------------------
-- Table `boutiq14_mbogni`.`categorie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `boutiq14_mbogni`.`categorie` (
  `idCat` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nomCat` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`idCat`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `boutiq14_mbogni`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `boutiq14_mbogni`.`client` (
  `idClient` INT(4) NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(45) NOT NULL DEFAULT 'ND',
  `tel` VARCHAR(90) NULL DEFAULT NULL,
  `adresse` VARCHAR(90) NULL DEFAULT NULL,
  PRIMARY KEY (`idClient`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `boutiq14_mbogni`.`gestionnaire`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `boutiq14_mbogni`.`gestionnaire` (
  `idGest` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nomGest` VARCHAR(45) NOT NULL,
  `typeGest` ENUM('Gestionnaire', 'Caissiere', 'Magazinier') NOT NULL,
  `login` VARCHAR(20) NOT NULL,
  `pwd` VARCHAR(20) NOT NULL,
  `actif` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idGest`),
  UNIQUE INDEX `login` (`login` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `boutiq14_mbogni`.`facture`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `boutiq14_mbogni`.`facture` (
  `idFac` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `dateFac` DATETIME NOT NULL,
  `remise` DECIMAL(4,2) NOT NULL,
  `montant` DECIMAL(10,2) NOT NULL,
  `tel` VARCHAR(15) NULL DEFAULT NULL,
  `typeFac` TINYINT(1) NOT NULL DEFAULT '0',
  `idCaissiere` INT(10) UNSIGNED NOT NULL,
  `Client_idClient` INT(4) NOT NULL,
  PRIMARY KEY (`idFac`),
  INDEX `fk_idCaiss_idx` (`idCaissiere` ASC) VISIBLE,
  INDEX `fk_facture_Client1_idx` (`Client_idClient` ASC) VISIBLE,
  CONSTRAINT `fk_facture_Client1`
    FOREIGN KEY (`Client_idClient`)
    REFERENCES `boutiq14_mbogni`.`client` (`idClient`),
  CONSTRAINT `fk_idCaiss`
    FOREIGN KEY (`idCaissiere`)
    REFERENCES `boutiq14_mbogni`.`gestionnaire` (`idGest`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `boutiq14_mbogni`.`fournisseur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `boutiq14_mbogni`.`fournisseur` (
  `codeFour` INT(11) NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(45) NULL DEFAULT NULL,
  `Adresse` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`codeFour`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `boutiq14_mbogni`.`produit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `boutiq14_mbogni`.`produit` (
  `codePro` INT(6) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nomPro` VARCHAR(100) NOT NULL DEFAULT 'ND',
  `prixVente` DECIMAL(8,0) NULL DEFAULT NULL,
  `prixAchat` DECIMAL(8,0) NOT NULL,
  `qte` INT(8) UNSIGNED NOT NULL,
  `description` VARCHAR(100) NOT NULL DEFAULT 'RAS',
  `codeFour` VARCHAR(12) NOT NULL,
  `dateInsertion` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `actif` TINYINT(1) NOT NULL DEFAULT '1',
  `idCategorie` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`codePro`),
  INDEX `fk_idCategorie` (`idCategorie` ASC) VISIBLE,
  CONSTRAINT `fk_idCategorie`
    FOREIGN KEY (`idCategorie`)
    REFERENCES `boutiq14_mbogni`.`categorie` (`idCat`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `boutiq14_mbogni`.`gestionstock`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `boutiq14_mbogni`.`gestionstock` (
  `idStock` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `qte` INT(10) UNSIGNED NOT NULL,
  `dateStock` DATETIME NOT NULL,
  `operation` TINYINT(1) NOT NULL DEFAULT '0',
  `idGest` INT(10) UNSIGNED NOT NULL,
  `codePro` INT(8) UNSIGNED NOT NULL,
  PRIMARY KEY (`idStock`),
  INDEX `fk_idGest_stock_idx` (`idGest` ASC) VISIBLE,
  INDEX `fk_codePro_stock_idx` (`codePro` ASC) VISIBLE,
  CONSTRAINT `fk_codePro_stock`
    FOREIGN KEY (`codePro`)
    REFERENCES `boutiq14_mbogni`.`produit` (`codePro`),
  CONSTRAINT `fk_idGest_stock`
    FOREIGN KEY (`idGest`)
    REFERENCES `boutiq14_mbogni`.`gestionnaire` (`idGest`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `boutiq14_mbogni`.`lignefacture`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `boutiq14_mbogni`.`lignefacture` (
  `idLFac` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `codePro` INT(6) UNSIGNED NOT NULL,
  `idFac` INT(10) UNSIGNED NOT NULL,
  `prix` DECIMAL(10,2) NOT NULL,
  `qte` SMALLINT(4) UNSIGNED NOT NULL,
  PRIMARY KEY (`idLFac`),
  UNIQUE INDEX `idx_fac_pro` (`codePro` ASC, `idFac` ASC) VISIBLE,
  INDEX `fk_idFac` (`idFac` ASC) VISIBLE,
  CONSTRAINT `fk_codePro`
    FOREIGN KEY (`codePro`)
    REFERENCES `boutiq14_mbogni`.`produit` (`codePro`),
  CONSTRAINT `fk_idFac`
    FOREIGN KEY (`idFac`)
    REFERENCES `boutiq14_mbogni`.`facture` (`idFac`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
