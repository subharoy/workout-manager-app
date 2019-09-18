ALTER TABLE `category` DROP FOREIGN KEY `category_fk0`;
ALTER TABLE `workout` DROP FOREIGN KEY `workout_fk1`;
ALTER TABLE `workoutactivity` DROP FOREIGN KEY `workoutactivity_fk1`;

DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `workout`;
DROP TABLE IF EXISTS `workoutactivity`;