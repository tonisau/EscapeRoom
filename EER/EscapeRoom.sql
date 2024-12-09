-- MySQL Script generated by MySQL Workbench
-- Mon Dec  9 12:37:42 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema db-escaperoom
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db-escaperoom
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db-escaperoom` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `db-escaperoom` ;

-- -----------------------------------------------------
-- Table `db-escaperoom`.`escaperoom`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db-escaperoom`.`escaperoom` (
  `idescaperoom` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `cif` VARCHAR(9) NOT NULL,
  PRIMARY KEY (`idescaperoom`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db-escaperoom`.`room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db-escaperoom`.`room` (
  `idroom` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  `level` ENUM('LOW', 'MEDIUM', 'HIGH') NOT NULL DEFAULT 'MEDIUM' COMMENT '\'LOW, MEDIUM, HIGH\'',
  `escaperoom_idescaperoom` INT NOT NULL,
  `isActive` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`idroom`),
  INDEX `fk_room_escaperoom1_idx` (`escaperoom_idescaperoom` ASC) VISIBLE,
  CONSTRAINT `fk_room_escaperoom1`
    FOREIGN KEY (`escaperoom_idescaperoom`)
    REFERENCES `db-escaperoom`.`escaperoom` (`idescaperoom`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db-escaperoom`.`enigma`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db-escaperoom`.`enigma` (
  `idenigma` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  `room_idroom` INT NOT NULL,
  PRIMARY KEY (`idenigma`),
  INDEX `fk_enigma_room1_idx` (`room_idroom` ASC) VISIBLE,
  CONSTRAINT `fk_enigma_room1`
    FOREIGN KEY (`room_idroom`)
    REFERENCES `db-escaperoom`.`room` (`idroom`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = '		';


-- -----------------------------------------------------
-- Table `db-escaperoom`.`decoration`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db-escaperoom`.`decoration` (
  `iddecoration` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `material` ENUM('WOOD', 'PLASTIC', 'PAPER', 'GLASS', 'METAL') NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  `quantity` INT NOT NULL DEFAULT 1,
  `room_idroom` INT NOT NULL,
  PRIMARY KEY (`iddecoration`),
  INDEX `fk_decoration_room_idx` (`room_idroom` ASC) VISIBLE,
  CONSTRAINT `fk_decoration_room`
    FOREIGN KEY (`room_idroom`)
    REFERENCES `db-escaperoom`.`room` (`idroom`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = '	';


-- -----------------------------------------------------
-- Table `db-escaperoom`.`clue`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db-escaperoom`.`clue` (
  `idclue` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  `theme` ENUM('DETECTIVE', 'FUTURIST', 'COWBOYS') NOT NULL,
  `enigma_idenigma` INT NOT NULL,
  PRIMARY KEY (`idclue`),
  INDEX `fk_clue_enigma1_idx` (`enigma_idenigma` ASC) VISIBLE,
  CONSTRAINT `fk_clue_enigma1`
    FOREIGN KEY (`enigma_idenigma`)
    REFERENCES `db-escaperoom`.`enigma` (`idenigma`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db-escaperoom`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db-escaperoom`.`user` (
  `iduser` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `isSubscriber` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`iduser`))
ENGINE = InnoDB
COMMENT = '	';


-- -----------------------------------------------------
-- Table `db-escaperoom`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db-escaperoom`.`ticket` (
  `idticket` INT NOT NULL AUTO_INCREMENT,
  `price` DECIMAL(6,2) NOT NULL,
  `date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `room_idroom` INT NOT NULL,
  PRIMARY KEY (`idticket`),
  INDEX `fk_ticket_room1_idx` (`room_idroom` ASC) VISIBLE,
  CONSTRAINT `fk_ticket_room1`
    FOREIGN KEY (`room_idroom`)
    REFERENCES `db-escaperoom`.`room` (`idroom`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db-escaperoom`.`user_has_enigma`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db-escaperoom`.`user_has_enigma` (
  `enigma_idenigma` INT NOT NULL,
  `user_iduser` INT NOT NULL,
  INDEX `fk_player_has_enigma_enigma1_idx` (`enigma_idenigma` ASC) VISIBLE,
  INDEX `fk_user_has_enigma_user1_idx` (`user_iduser` ASC) VISIBLE,
  CONSTRAINT `fk_player_has_enigma_enigma1`
    FOREIGN KEY (`enigma_idenigma`)
    REFERENCES `db-escaperoom`.`enigma` (`idenigma`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_enigma_user1`
    FOREIGN KEY (`user_iduser`)
    REFERENCES `db-escaperoom`.`user` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db-escaperoom`.`user_has_ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db-escaperoom`.`user_has_ticket` (
  `user_iduser` INT NOT NULL,
  `ticket_idticket` INT NOT NULL,
  INDEX `fk_user_has_ticket_ticket1_idx` (`ticket_idticket` ASC) VISIBLE,
  INDEX `fk_user_has_ticket_user1_idx` (`user_iduser` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_ticket_user1`
    FOREIGN KEY (`user_iduser`)
    REFERENCES `db-escaperoom`.`user` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_ticket_ticket1`
    FOREIGN KEY (`ticket_idticket`)
    REFERENCES `db-escaperoom`.`ticket` (`idticket`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db-escaperoom`.`gift`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db-escaperoom`.`gift` (
  `idgift` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idgift`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db-escaperoom`.`user_has_gift`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db-escaperoom`.`user_has_gift` (
  `gift_idgift` INT NOT NULL,
  `user_iduser` INT NOT NULL,
  INDEX `fk_gift_has_user_user1_idx` (`user_iduser` ASC) VISIBLE,
  INDEX `fk_gift_has_user_gift1_idx` (`gift_idgift` ASC) VISIBLE,
  CONSTRAINT `fk_gift_has_user_gift1`
    FOREIGN KEY (`gift_idgift`)
    REFERENCES `db-escaperoom`.`gift` (`idgift`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_gift_has_user_user1`
    FOREIGN KEY (`user_iduser`)
    REFERENCES `db-escaperoom`.`user` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
