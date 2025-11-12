-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8mb3 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`card_pack`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`card_pack` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `pack_name` VARCHAR(64) NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  `pack_rarity` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`cards`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`cards` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `card_name` VARCHAR(50) NOT NULL,
  `rarity` VARCHAR(50) NOT NULL,
  `picture_name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `cardName_UNIQUE` (`card_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`card_pack_inventory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`card_pack_inventory` (
  `pack_id` INT NOT NULL,
  `card_id` INT NULL,
  PRIMARY KEY (`pack_id`, `card_id`),
  INDEX `fk_cpi_cards` (`card_id` ASC) VISIBLE,
  CONSTRAINT `fk_cpi_card_pack`
    FOREIGN KEY (`pack_id`)
    REFERENCES `mydb`.`card_pack` (`id`),
  CONSTRAINT `fk_cpi_cards`
    FOREIGN KEY (`card_id`)
    REFERENCES `mydb`.`cards` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`poke_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`poke_types` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`types_bridge`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`types_bridge` (
  `card_id` INT NULL,
  `type_id` INT NOT NULL,
  PRIMARY KEY (`card_id`, `type_id`),
  INDEX `fk_types_bridge_poke_types` (`type_id` ASC) VISIBLE,
  CONSTRAINT `fk_types_bridge_cards`
    FOREIGN KEY (`card_id`)
    REFERENCES `mydb`.`cards` (`id`),
  CONSTRAINT `fk_types_bridge_poke_types`
    FOREIGN KEY (`type_id`)
    REFERENCES `mydb`.`poke_types` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(50) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `name` VARCHAR(64) NOT NULL,
  `picture_name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `userName_UNIQUE` (`user_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`user_inventory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user_inventory` (
  `user_id` INT NOT NULL,
  `card_id` INT NULL,
  `quantity` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`, `card_id`),
  INDEX `fk_user_inventory_cards` (`card_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_inventory_cards`
    FOREIGN KEY (`card_id`)
    REFERENCES `mydb`.`cards` (`id`),
  CONSTRAINT `fk_user_inventory_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
