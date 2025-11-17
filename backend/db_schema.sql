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
  `image_path` VARCHAR(255) NULL,
  `thumb_path` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `cardName_UNIQUE` (`card_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `mydb`.`card_pack_inventory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`card_pack_inventory` (
  `pack_id` INT NOT NULL,
  `card_id` INT NOT NULL,
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
  `card_id` INT NOT NULL,
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
  `card_id` INT NOT NULL,
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

-- -----------------------------------------------------
-- TEST DATA FOR PROJECT
-- -----------------------------------------------------
INSERT INTO `mydb`.`poke_types` (`name`) VALUES
  ('Fire'), 
  ('Water'),
  ('Electric')
  ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO `mydb`.`cards` (`card_name`, `rarity`,`image_path`, `thumb_path`) VALUES
  ('Charizard', 'Rare', 'assets/images/cards/charizard.png', 'assets/images/thumbs/charizard.png'),
  ('Pikachu', 'Uncommon', 'assets/images/cards/pikachu.png', 'assets/images/thumbs/pikachu.png'),
  ('Squirtle', 'Common', 'assets/images/cards/squirtle.png', 'assets/images/thumbs/squirtle.png');

INSERT INTO `mydb`.`card_pack`(`pack_name`, `price`, `pack_rarity`) VALUES
  ('Starter Pack', 4.99, 'Common');

INSERT INTO `mydb`.`users` (`user_name`, `password`, `name`,`picture_name` ) VALUES
  ('ash', 'password123', 'Ash Ketchup', NULL),
  ('brock', 'password456', 'Brock Powers', NULL);

INSERT INTO `mydb`.`types_bridge` (`card_id`, `type_id`)
  SELECT c.id, t.id FROM cards c JOIN poke_types t
  WHERE c.card_name= 'Charizard' AND t.name= 'Fire'
  UNION ALL
  SELECT c.id, t.id FROM cards c JOIN poke_types t 
  WHERE c.card_name= 'Squirtle' AND t.name='Water'
  UNION ALL
  SELECT c.id, t.id FROM cards c JOIN poke_types t 
  WHERE c.card_name='Pikachu' AND t.name='Electric';

INSERT INTO `mydb`.`user_inventory` (`user_id`, `card_id`, `quantity`)
  SELECT u.id, c.id, 2 FROM users u JOIN cards c
  WHERE u.user_name='ash' AND c.card_name='Pikachu'
  UNION ALL
  SELECT u.id, c.id, 2 FROM users u JOIN cards c
  WHERE u.user_name='ash' AND c.card_name='Charizard';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
