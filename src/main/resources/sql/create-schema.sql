-- drop all tables
DROP TABLE IF EXISTS item_value;

-- create new ones

CREATE  TABLE `item_value` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `creation` TIMESTAMP NOT NULL ,
  `last_update` TIMESTAMP NOT NULL ,
  `update_count` INT NOT NULL ,
  `dashboard_id` VARCHAR(255) NOT NULL ,
  `item_name` VARCHAR(255) NOT NULL ,
  `data` TEXT NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

CREATE INDEX idx_dasboard_id_item_name ON item_value (dashboard_id,item_name,last_update);