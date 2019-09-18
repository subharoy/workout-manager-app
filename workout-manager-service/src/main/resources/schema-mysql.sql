CREATE SCHEMA IF NOT EXISTS `fse`;
COMMIT;

CREATE USER IF NOT EXISTS `fse` 
IDENTIFIED BY 'fse123' 
REQUIRE NONE PASSWORD EXPIRE NEVER;
COMMIT;

GRANT ALTER, CREATE, CREATE TEMPORARY TABLES, 
CREATE VIEW, DELETE, DROP, EXECUTE, GRANT OPTION, 
INDEX, INSERT, SELECT, TRIGGER, UPDATE, REFERENCES 
ON fse.* TO fse;
COMMIT;

CREATE TABLE `fse`.`category` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `category` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
COMMIT;

CREATE TABLE `fse`.`workout` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `categoryid` int(9) NOT NULL,
  `title` varchar(128) NOT NULL,
  `notes` varchar(256) DEFAULT NULL,
  `burnrate` double DEFAULT '2',
  PRIMARY KEY (`id`),
  KEY `workout_fk1` (`categoryid`),
  CONSTRAINT `workout_fk1` FOREIGN KEY (`categoryid`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
COMMIT;

CREATE TABLE `fse`.`workoutactivity` (
  `id` int(12) NOT NULL,
  `startdate` date DEFAULT NULL,
  `starttime` time DEFAULT NULL,
  `enddate` date DEFAULT NULL,
  `endtime` time DEFAULT NULL,
  `comment` varchar(64) DEFAULT NULL,
  `status` boolean NOT NULL DEFAULT 0,
  KEY `workoutactivity_fk1` (`id`),
  CONSTRAINT `workoutactivity_fk1` FOREIGN KEY (`id`) REFERENCES `workout` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
COMMIT;