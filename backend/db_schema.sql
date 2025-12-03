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
  `first_name` VARCHAR(64) NOT NULL,
  `last_name` VARCHAR(64) NOT NULL,
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
  ('Normal'), 
  ('Fire'),
  ('Water'),
  ('Grass'),
  ('Electric'),
  ('Ice'),
  ('Fighting'),
  ('Poison'),
  ('Ground'),
  ('Flying'),
  ('Psychic'),
  ('Bug'),
  ('Ghost'),
  ('Rock'),
  ('Dragon'),
  ('Steel')
  ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO `mydb`.`cards` (`card_name`, `rarity`,`image_path`, `thumb_path`) VALUES
  ('Bulbasaur', 'Common', 'Bulbasaur.png', 'BulbasaurThumbnail.png'),
  ('Ivysaur', 'Uncommon', 'Ivysaur.png', 'IvysaurThumbnail.png'),
  ('Venusaur', 'Rare', 'Venusaur.png', 'VenusaurThumbnail.png'),
  ('Charmander', 'Common', 'Charmander.png', 'CharmanderThumbnail.png'),
  ('Charmeleon', 'Uncommon', 'Charmeleon.png', 'CharmeleonThumbnail.png'),
  ('Charizard', 'Rare', 'Charizard.png', 'CharizardThumbnail.png'),
  ('Squirtle', 'Common', 'Squirtle.png', 'SquirtleThumbnail.png'),
  ('Wartortle', 'Uncommon', 'Wartortle.png', 'WartortleThumbnail.png'),
  ('Blastoise', 'Rare', 'Blastoise.png', 'BlastoiseThumbnail.png'),
  ('Caterpie', 'Common', 'Caterpie.png', 'CaterpieThumbnail.png'),
  ('Metapod', 'Common', 'Metapod.png', 'MetapodThumbnail.png'),
  ('Butterfree', 'Uncommon', 'Butterfree.png', 'ButterfreeThumbnail.png'),
  ('Weedle', 'Common', 'Weedle.png', 'WeedleThumbnail.png'),
  ('Kakuna', 'Common', 'Kakuna.png', 'KakunaThumbnail.png'),
  ('Beedrill', 'Uncommon', 'Beedrill.png', 'BeedrillThumbnail.png'),
  ('Pidgey', 'Common', 'Pidgey.png', 'PidgeyThumbnail.png'),
  ('Pidgeotto', 'Uncommon', 'Pidgeotto.png', 'PidgeottoThumbnail.png'),
  ('Pidgeot', 'Rare', 'Pidgeot.png', 'PidgeotThumbnail.png'),
  ('Rattata', 'Common', 'Rattata.png', 'RattataThumbnail.png'),
  ('Raticate', 'Uncommon', 'Raticate.png', 'RaticateThumbnail.png'),
  ('Spearow', 'Common', 'Spearow.png', 'SpearowThumbnail.png'),
  ('Fearow', 'Uncommon', 'Fearow.png', 'FearowThumbnail.png'),
  ('Ekans', 'Uncommon', 'Ekans.png', 'EkansThumbnail.png'),
  ('Arbok', 'Rare', 'Arbok.png', 'ArbokThumbnail.png'),
  ('Pikachu', 'Rare', 'Pikachu.png', 'PikachuThumbnail.png'),
  ('Raichu', 'Rare', 'Raichu.png', 'RaichuThumbnail.png'),
  ('Sandshrew', 'Uncommon', 'Sandshrew.png', 'SandshrewThumbnail.png'),
  ('Sandslash', 'Rare', 'Sandslash.png', 'SandslashThumbnail.png'),
  ('Nidoran', 'Common', 'NidoranF.png', 'NidoranFThumbnail.png'),
  ('Nidorina', 'Uncommon', 'Nidorina.png', 'NidorinaThumbnail.png'),
  ('Nidoqueen', 'Rare', 'Nidoqueen.png', 'NidoqueenThumbnail.png'),
  ('Nidoran', 'Common', 'NidoranM.png', 'NidoranMThumbnail.png'),
  ('Nidorino', 'Uncommon', 'Nidorino.png', 'NidorinoThumbnail.png'),
  ('Nidoking', 'Rare', 'Nidoking.png', 'NidokingThumbnail.png'),
  ('Clefairy', 'Common', 'Clefairy.png', 'ClefairyThumbnail.png'),
  ('Clefable', 'Uncommon', 'Clefable.png', 'ClefableThumbnail.png'),
  ('Vulpix', 'Uncommon', 'Vulpix.png', 'VulpixThumbnail.png'),
  ('Ninetales', 'Rare', 'Ninetales.png', 'NinetalesThumbnail.png'),
  ('Jigglypuff', 'Uncommon', 'Jigglypuff.png', 'JigglypuffThumbnail.png'),
  ('Wigglytuff', 'Rare', 'Wigglytuff.png', 'WigglytuffThumbnail.png'),
  ('Zubat', 'Common', 'Zubat.png', 'ZubatThumbnail.png'),
  ('Golbat', 'Uncommon', 'Golbat.png', 'GolbatThumbnail.png'),
  ('Oddish', 'Common', 'Oddish.png', 'OddishThumbnail.png'),
  ('Gloom', 'Uncommon', 'Gloom.png', 'GloomThumbnail.png'),
  ('Vileplume', 'Rare', 'Vileplume.png', 'VileplumeThumbnail.png'),
  ('Paras', 'Common', 'Paras.png', 'ParasThumbnail.png'),
  ('Parasect', 'Uncommon', 'Parasect.png', 'ParasectThumbnail.png'),
  ('Venonat', 'Common', 'Venonat.png', 'VenonatThumbnail.png'),
  ('Venomoth', 'Uncommon', 'Venomoth.png', 'VenomothThumbnail.png'),
  ('Diglett', 'Common', 'Diglett.png', 'DiglettThumbnail.png'),
  ('Dugtrio', 'Rare', 'Dugtrio.png', 'DugtrioThumbnail.png'),
  ('Meowth', 'Uncommon', 'Meowth.png', 'MeowthThumbnail.png'),
  ('Persian', 'Rare', 'Persian.png', 'PersianThumbnail.png'),
  ('Psyduck', 'Common', 'Psyduck.png', 'PsyduckThumbnail.png'),
  ('Golduck', 'Rare', 'Golduck.png', 'GolduckThumbnail.png'),
  ('Mankey', 'Common', 'Mankey.png', 'MankeyThumbnail.png'),
  ('Primeape', 'Uncommon', 'Primeape.png', 'PrimeapeThumbnail.png'),
  ('Growlithe', 'Uncommon', 'Growlithe.png', 'GrowlitheThumbnail.png'),
  ('Arcanine', 'Rare', 'Arcanine.png', 'ArcanineThumbnail.png'),
  ('Poliwag', 'Common', 'Poliwag.png', 'PoliwagThumbnail.png'),
  ('Poliwhirl', 'Uncommon', 'Poliwhirl.png', 'PoliwhirlThumbnail.png'),
  ('Poliwrath', 'Rare', 'Poliwrath.png', 'PoliwrathThumbnail.png'),
  ('Abra', 'Common', 'Abra.png', 'AbraThumbnail.png'),
  ('Kadabra', 'Uncommon', 'Kadabra.png', 'KadabraThumbnail.png'),
  ('Alakazam', 'Rare', 'Alakazam.png', 'AlakazamThumbnail.png'),
  ('Machop', 'Common', 'Machop.png', 'MachopThumbnail.png'),
  ('Machoke', 'Uncommon', 'Machoke.png', 'MachokeThumbnail.png'),
  ('Machamp', 'Rare', 'Machamp.png', 'MachampThumbnail.png'),
  ('Bellsprout', 'Common', 'Bellsprout.png', 'BellsproutThumbnail.png'),
  ('Weepinbell', 'Uncommon', 'Weepinbell.png', 'WeepinbellThumbnail.png'),
  ('Victreebel', 'Rare', 'Victreebel.png', 'VictreebelThumbnail.png'),
  ('Tentacool', 'Uncommon', 'Tentacool.png', 'TentacoolThumbnail.png'),
  ('Tentacruel', 'Rare', 'Tentacruel.png', 'TentacruelThumbnail.png'),
  ('Geodude', 'Common', 'Geodude.png', 'GeodudeThumbnail.png'),
  ('Graveler', 'Uncommon', 'Graveler.png', 'GravelerThumbnail.png'),
  ('Golem', 'Rare', 'Golem.png', 'GolemThumbnail.png'),
  ('Ponyta', 'Uncommon', 'Ponyta.png', 'PonytaThumbnail.png'),
  ('Rapidash', 'Rare', 'Rapidash.png', 'RapidashThumbnail.png'),
  ('Slowpoke', 'Common', 'Slowpoke.png', 'SlowpokeThumbnail.png'),
  ('Slowbro', 'Rare', 'Slowbro.png', 'SlowbroThumbnail.png'),
  ('Magnemite', 'Uncommon', 'Magnemite.png', 'MagnemiteThumbnail.png'),
  ('Magneton', 'Rare', 'Magneton.png', 'MagnetonThumbnail.png'),
  ('Farfetchd', 'Uncommon', 'Farfetchd.png', 'FarfetchdThumbnail.png'),
  ('Doduo', 'Common', 'Doduo.png', 'DoduoThumbnail.png'),
  ('Dodrio', 'Uncommon', 'Dodrio.png', 'DodrioThumbnail.png'),
  ('Seel', 'Common', 'Seel.png', 'SeelThumbnail.png'),
  ('Dewgong', 'Uncommon', 'Dewgong.png', 'DewgongThumbnail.png'),
  ('Grimer', 'Common', 'Grimer.png', 'GrimerThumbnail.png'),
  ('Muk', 'Uncommon', 'Muk.png', 'MukThumbnail.png'),
  ('Shellder', 'Common', 'Shellder.png', 'ShellderThumbnail.png'),
  ('Cloyster', 'Rare', 'Cloyster.png', 'CloysterThumbnail.png'),
  ('Gastly', 'Common', 'Gastly.png', 'GastlyThumbnail.png'),
  ('Haunter', 'Uncommon', 'Haunter.png', 'HaunterThumbnail.png'),
  ('Gengar', 'Rare', 'Gengar.png', 'GengarThumbnail.png'),
  ('Onix', 'Uncommon', 'Onix.png', 'OnixThumbnail.png'),
  ('Drowzee', 'Common', 'Drowzee.png', 'DrowzeeThumbnail.png'),
  ('Hypno', 'Uncommon', 'Hypno.png', 'HypnoThumbnail.png'),
  ('Krabby', 'Common', 'Krabby.png', 'KrabbyThumbnail.png'),
  ('Kingler', 'Uncommon', 'Kingler.png', 'KinglerThumbnail.png'),
  ('Voltorb', 'Common', 'Voltorb.png', 'VoltorbThumbnail.png'),
  ('Electrode', 'Uncommon', 'Electrode.png', 'ElectrodeThumbnail.png'),
  ('Exeggcute', 'Uncommon', 'Exeggcute.png', 'ExeggcuteThumbnail.png'),
  ('Exeggutor', 'Rare', 'Exeggutor.png', 'ExeggutorThumbnail.png'),
  ('Cubone', 'Common', 'Cubone.png', 'CuboneThumbnail.png'),
  ('Marowak', 'Uncommon', 'Marowak.png', 'MarowakThumbnail.png'),
  ('Hitmonlee', 'Uncommon', 'Hitmonlee.png', 'HitmonleeThumbnail.png'),
  ('Hitmonchan', 'Rare', 'Hitmonchan.png', 'HitmonchanThumbnail.png'),
  ('Lickitung', 'Uncommon', 'Lickitung.png', 'LickitungThumbnail.png'),
  ('Koffing', 'Common', 'Koffing.png', 'KoffingThumbnail.png'),
  ('Weezing', 'Uncommon', 'Weezing.png', 'WeezingThumbnail.png'),
  ('Rhyhorn', 'Common', 'Rhyhorn.png', 'RhyhornThumbnail.png'),
  ('Rhydon', 'Uncommon', 'Rhydon.png', 'RhydonThumbnail.png'),
  ('Chansey', 'Uncommon', 'Chansey.png', 'ChanseyThumbnail.png'),
  ('Tangela', 'Uncommon', 'Tangela.png', 'TangelaThumbnail.png'),
  ('Kangaskhan', 'Rare', 'Kangaskhan.png', 'KangaskhanThumbnail.png'),
  ('Horsea', 'Common', 'Horsea.png', 'HorseaThumbnail.png'),
  ('Seadra', 'Uncommon', 'Seadra.png', 'SeadraThumbnail.png'),
  ('Goldeen', 'Common', 'Goldeen.png', 'GoldeenThumbnail.png'),
  ('Seaking', 'Uncommon', 'Seaking.png', 'SeakingThumbnail.png'),
  ('Staryu', 'Common', 'Staryu.png', 'StaryuThumbnail.png'),
  ('Starmie', 'Uncommon', 'Starmie.png', 'StarmieThumbnail.png'),
  ('MrMime', 'Uncommon', 'MrMime.png', 'MrMimeThumbnail.png'),
  ('Scyther', 'Uncommon', 'Scyther.png', 'ScytherThumbnail.png'),
  ('Jynx', 'Uncommon', 'Jynx.png', 'JynxThumbnail.png'),
  ('Electabuzz', 'Uncommon', 'Electabuzz.png', 'ElectabuzzThumbnail.png'),
  ('Magmar', 'Uncommon', 'Magmar.png', 'MagmarThumbnail.png'),
  ('Pinsir', 'Uncommon', 'Pinsir.png', 'PinsirThumbnail.png'),
  ('Tauros', 'Uncommon', 'Tauros.png', 'TaurosThumbnail.png'),
  ('Magikarp', 'Common', 'Magikarp.png', 'MagikarpThumbnail.png'),
  ('Gyarados', 'Rare', 'Gyarados.png', 'GyaradosThumbnail.png'),
  ('Lapras', 'Rare', 'Lapras.png', 'LaprasThumbnail.png'),
  ('Ditto', 'Rare', 'Ditto.png', 'DittoThumbnail.png'),
  ('Eevee', 'Common', 'Eevee.png', 'EeveeThumbnail.png'),
  ('Vaporeon', 'Uncommon', 'Vaporeon.png', 'VaporeonThumbnail.png'),
  ('Jolteon', 'Uncommon', 'Jolteon.png', 'JolteonThumbnail.png'),
  ('Flareon', 'Uncommon', 'Flareon.png', 'FlareonThumbnail.png'),
  ('Porygon', 'Uncommon', 'Porygon.png', 'PorygonThumbnail.png'),
  ('Omanyte', 'Uncommon', 'Omanyte.png', 'OmanyteThumbnail.png'),
  ('Omastar', 'Rare', 'Omastar.png', 'OmastarThumbnail.png'),
  ('Kabuto', 'Common', 'Kabuto.png', 'KabutoThumbnail.png'),
  ('Kabutops', 'Uncommon', 'Kabutops.png', 'KabutopsThumbnail.png'),
  ('Aerodactyl', 'Rare', 'Aerodactyl.png', 'AerodactylThumbnail.png'),
  ('Snorlax', 'Rare', 'Snorlax.png', 'SnorlaxThumbnail.png'),
  ('Articuno', 'Rare', 'Articuno.png', 'ArticunoThumbnail.png'),
  ('Zapdos', 'Rare', 'Zapdos.png', 'ZapdosThumbnail.png'),
  ('Moltres', 'Rare', 'Moltres.png', 'MoltresThumbnail.png'),
  ('Dratini', 'Uncommon', 'Dratini.png', 'DratiniThumbnail.png'),
  ('Dragonair', 'Uncommon', 'Dragonair.png', 'DragonairThumbnail.png'),
  ('Dragonite', 'Rare', 'Dragonite.png', 'DragoniteThumbnail.png'),
  ('Mewtwo', 'Rare', 'Mewtwo.png', 'MewtwoThumbnail.png'),
  ('Mew', 'Rare', 'Mew.png', 'MewThumbnail.png'),
  ('Galvantula', 'Rare', 'Galvantula.png', 'GalvantulaThumbnail.png'),
  ('Mega Gardevoir', 'Rare', 'Mega_Gardevoir.png', 'Mega_GardevoirThumbnail.png'),
  ('Shiftry', 'Rare', 'Shiftry.png', 'ShiftryThumbnail.png'),
  ('Volcanion', 'Rare', 'Volcanion.png', 'VolcanionThumbnail.png');

INSERT INTO types_bridge (card_id, type_id) VALUES
  ((SELECT id FROM cards WHERE card_name='Abra'), (SELECT id FROM poke_types WHERE name='Psychic')),
  ((SELECT id FROM cards WHERE card_name='Aerodactyl'), (SELECT id FROM poke_types WHERE name='Dragon')),
  ((SELECT id FROM cards WHERE card_name='Alakazam'), (SELECT id FROM poke_types WHERE name='Psychic')),
  ((SELECT id FROM cards WHERE card_name='Arbok'), (SELECT id FROM poke_types WHERE name='Poison')),
  ((SELECT id FROM cards WHERE card_name='Arcanine'), (SELECT id FROM poke_types WHERE name='Fire')),
  ((SELECT id FROM cards WHERE card_name='Articuno'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Beedrill'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Bellsprout'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Blastoise'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Bulbasaur'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Butterfree'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Caterpie'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Chansey'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Charizard'), (SELECT id FROM poke_types WHERE name='Fire')),
  ((SELECT id FROM cards WHERE card_name='Charmander'), (SELECT id FROM poke_types WHERE name='Fire')),
  ((SELECT id FROM cards WHERE card_name='Clefable'), (SELECT id FROM poke_types WHERE name='Psychic')),
  ((SELECT id FROM cards WHERE card_name='Cloyster'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Cubone'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Dewgong'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Diglett'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Ditto'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Dodrio'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Doduo'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Dragonair'), (SELECT id FROM poke_types WHERE name='Dragon')),
  ((SELECT id FROM cards WHERE card_name='Dragonite'), (SELECT id FROM poke_types WHERE name='Dragon')),
  ((SELECT id FROM cards WHERE card_name='Dratini'), (SELECT id FROM poke_types WHERE name='Dragon')),
  ((SELECT id FROM cards WHERE card_name='Drowzee'), (SELECT id FROM poke_types WHERE name='Psychic')),
  ((SELECT id FROM cards WHERE card_name='Dugtrio'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Eevee'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Ekans'), (SELECT id FROM poke_types WHERE name='Poison')),
  ((SELECT id FROM cards WHERE card_name='Electabuzz'), (SELECT id FROM poke_types WHERE name='Electric')),
  ((SELECT id FROM cards WHERE card_name='Electrode'), (SELECT id FROM poke_types WHERE name='Electric')),
  ((SELECT id FROM cards WHERE card_name='Exeggcute'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Exeggutor'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Farfetchd'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Fearow'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Flareon'), (SELECT id FROM poke_types WHERE name='Fire')),
  ((SELECT id FROM cards WHERE card_name='Gastly'), (SELECT id FROM poke_types WHERE name='Psychic')),
  ((SELECT id FROM cards WHERE card_name='Gengar'), (SELECT id FROM poke_types WHERE name='Psychic')),
  ((SELECT id FROM cards WHERE card_name='Geodude'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Gloom'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Golbat'), (SELECT id FROM poke_types WHERE name='Poison')),
  ((SELECT id FROM cards WHERE card_name='Goldeen'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Golduck'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Golem'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Graveler'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Grimer'), (SELECT id FROM poke_types WHERE name='Poison')),
  ((SELECT id FROM cards WHERE card_name='Growlithe'), (SELECT id FROM poke_types WHERE name='Fire')),
  ((SELECT id FROM cards WHERE card_name='Gyarados'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Haunter'), (SELECT id FROM poke_types WHERE name='Psychic')),
  ((SELECT id FROM cards WHERE card_name='Hitmonchan'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Hitmonlee'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Horsea'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Hypno'), (SELECT id FROM poke_types WHERE name='Psychic')),
  ((SELECT id FROM cards WHERE card_name='Ivysaur'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Jigglypuff'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Jolteon'), (SELECT id FROM poke_types WHERE name='Electric')),
  ((SELECT id FROM cards WHERE card_name='Jynx'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Kabuto'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Kabutops'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Kadabra'), (SELECT id FROM poke_types WHERE name='Psychic')),
  ((SELECT id FROM cards WHERE card_name='Kakuna'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Kangaskhan'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Kingler'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Koffing'), (SELECT id FROM poke_types WHERE name='Poison')),
  ((SELECT id FROM cards WHERE card_name='Krabby'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Lapras'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Lickitung'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Machamp'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Machoke'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Machop'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Magikarp'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Magmar'), (SELECT id FROM poke_types WHERE name='Fire')),
  ((SELECT id FROM cards WHERE card_name='Magnemite'), (SELECT id FROM poke_types WHERE name='Electric')),
  ((SELECT id FROM cards WHERE card_name='Magneton'), (SELECT id FROM poke_types WHERE name='Electric')),
  ((SELECT id FROM cards WHERE card_name='Mankey'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Marowak'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Meowth'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Metapod'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Mew'), (SELECT id FROM poke_types WHERE name='Psychic')),
  ((SELECT id FROM cards WHERE card_name='Mewtwo'), (SELECT id FROM poke_types WHERE name='Psychic')),
  ((SELECT id FROM cards WHERE card_name='Moltres'), (SELECT id FROM poke_types WHERE name='Fire')),
  ((SELECT id FROM cards WHERE card_name='MrMime'), (SELECT id FROM poke_types WHERE name='Psychic')),
  ((SELECT id FROM cards WHERE card_name='Muk'), (SELECT id FROM poke_types WHERE name='Poison')),
  ((SELECT id FROM cards WHERE card_name='Dratini'), (SELECT id FROM poke_types WHERE name='Dragon')),
  ((SELECT id FROM cards WHERE card_name='Nidoking'), (SELECT id FROM poke_types WHERE name='Poison')),
  ((SELECT id FROM cards WHERE card_name='Nidoqueen'), (SELECT id FROM poke_types WHERE name='Poison')),
  ((SELECT id FROM cards WHERE card_name='NidoranF'), (SELECT id FROM poke_types WHERE name='Poison')),
  ((SELECT id FROM cards WHERE card_name='NidoranM'), (SELECT id FROM poke_types WHERE name='Poison')),
  ((SELECT id FROM cards WHERE card_name='Nidorina'), (SELECT id FROM poke_types WHERE name='Poison')),
  ((SELECT id FROM cards WHERE card_name='Nidorino'), (SELECT id FROM poke_types WHERE name='Poison')),
  ((SELECT id FROM cards WHERE card_name='Ninetales'), (SELECT id FROM poke_types WHERE name='Fire')),
  ((SELECT id FROM cards WHERE card_name='Oddish'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Omanyte'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Omastar'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Onix'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Paras'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Parasect'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Persian'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Pidgeot'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Pidgeotto'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Pidgey'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Pikachu'), (SELECT id FROM poke_types WHERE name='Electric')),
  ((SELECT id FROM cards WHERE card_name='Pinsir'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Poliwag'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Poliwhirl'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Poliwrath'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Ponyta'), (SELECT id FROM poke_types WHERE name='Fire')),
  ((SELECT id FROM cards WHERE card_name='Porygon'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Primeape'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Psyduck'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Raichu'), (SELECT id FROM poke_types WHERE name='Electric')),
  ((SELECT id FROM cards WHERE card_name='Rapidash'), (SELECT id FROM poke_types WHERE name='Fire')),
  ((SELECT id FROM cards WHERE card_name='Raticate'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Rattata'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Rhydon'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Rhyhorn'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Sandshrew'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Sandslash'), (SELECT id FROM poke_types WHERE name='Fighting')),
  ((SELECT id FROM cards WHERE card_name='Scyther'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Seadra'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Seaking'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Seel'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Shellder'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Slowbro'), (SELECT id FROM poke_types WHERE name='Psychic')),
  ((SELECT id FROM cards WHERE card_name='Slowpoke'), (SELECT id FROM poke_types WHERE name='Psychic')),
  ((SELECT id FROM cards WHERE card_name='Snorlax'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Spearow'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Squirtle'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Starmie'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Staryu'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Tangela'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Tauros'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Tentacool'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Tentacruel'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Vaporeon'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Venomoth'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Venonat'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Venusaur'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Victreebel'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Vileplume'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Voltorb'), (SELECT id FROM poke_types WHERE name='Electric')),
  ((SELECT id FROM cards WHERE card_name='Vulpix'), (SELECT id FROM poke_types WHERE name='Fire')),
  ((SELECT id FROM cards WHERE card_name='Wartortle'), (SELECT id FROM poke_types WHERE name='Water')),
  ((SELECT id FROM cards WHERE card_name='Weedle'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Weepinbell'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Weezing'), (SELECT id FROM poke_types WHERE name='Poison')),
  ((SELECT id FROM cards WHERE card_name='Wigglytuff'), (SELECT id FROM poke_types WHERE name='Normal')),
  ((SELECT id FROM cards WHERE card_name='Zapdos'), (SELECT id FROM poke_types WHERE name='Electric')),
  ((SELECT id FROM cards WHERE card_name='Zubat'), (SELECT id FROM poke_types WHERE name='Poison'))
  -- Special Multi-Type Cards
  ((SELECT id FROM cards WHERE card_name='Galvantula'), (SELECT id FROM poke_types WHERE name='Electric')),
  ((SELECT id FROM cards WHERE card_name='Galvantula'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Mega Gardevoir'), (SELECT id FROM poke_types WHERE name='Psychic')),
  ((SELECT id FROM cards WHERE card_name='Mega Gardevoir'), (SELECT id FROM poke_types WHERE name='Fairy')),
  ((SELECT id FROM cards WHERE card_name='Shiftry'), (SELECT id FROM poke_types WHERE name='Grass')),
  ((SELECT id FROM cards WHERE card_name='Shiftry'), (SELECT id FROM poke_types WHERE name='Poison')),
  ((SELECT id FROM cards WHERE card_name='Volcanion'), (SELECT id FROM poke_types WHERE name='Fire')),
  ((SELECT id FROM cards WHERE card_name='Volcanion'), (SELECT id FROM poke_types WHERE name='Water'));


INSERT INTO `mydb`.`card_pack`(`pack_name`, `price`, `pack_rarity`) VALUES
  ('Starter Pack', 11.99, 'Common'),
  ('Advanced Pack', 14.99, 'Uncommon'),
  ('Rare Pack', 19.99, 'Rare');


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
