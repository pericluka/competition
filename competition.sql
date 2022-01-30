CREATE TABLE `players` (
  `playerID` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `dateOfBirth` datetime NOT NULL,
  `placeOfBirth` varchar(90) COLLATE utf8_unicode_ci NOT NULL,
  `heightInCM` int(3) NOT NULL,
  `playerPosition` int(10) NOT NULL,
  `team` int(10) NOT NULL,
  `playerNumber` int(2) NOT NULL,
  PRIMARY KEY (`playerID`),
  KEY `playerPosition` (`playerPosition`),
  KEY `team` (`team`),
  CONSTRAINT `playerPosition` FOREIGN KEY (`playerPosition`) REFERENCES `position` (`positionID`) ON UPDATE CASCADE,
  CONSTRAINT `team` FOREIGN KEY (`team`) REFERENCES `team` (`teamID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `positions` (
  `positionID` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`positionID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `teams` (
  `teamID` int(10) NOT NULL,
  `fullName` varchar(90) NOT NULL,
  `nickname` varchar(45) NOT NULL,
  `founded` int(4) NOT NULL,
  `ground` varchar(45) NOT NULL,
  `capacity` int(6) NOT NULL,
  PRIMARY KEY (`teamID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
