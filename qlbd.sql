-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 02, 2022 at 05:44 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 7.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


CREATE DATABASE `qlbd` CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `qlbd`;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `qlbd`
--

-- --------------------------------------------------------

--
-- Table structure for table `leaderboard`
--

CREATE TABLE `leaderboard` (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `time` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `leaderboard`
--

INSERT INTO `leaderboard` (`id`, `time`) VALUES
(0000000000, '2022-01-01 00:00:00'),
(0000000001, '2022-04-24 00:00:00'),
(0000000002, '2022-04-24 00:00:00'),
(0000000003, '2022-04-24 00:00:00'),
(0000000004, '2022-04-24 00:00:00'),
(0000000005, '2022-04-24 00:00:00'),
(0000000006, '2022-04-24 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `match_schedule`
--

CREATE TABLE `match_schedule` (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `round` int(10) UNSIGNED ZEROFILL NOT NULL,
  `id_first_team` int(10) UNSIGNED ZEROFILL NOT NULL,
  `id_second_team` int(10) UNSIGNED ZEROFILL NOT NULL,
  `time` datetime NOT NULL,
  `stadium` varchar(255) NOT NULL,
  `id_result` int(10) UNSIGNED ZEROFILL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `match_schedule`
--

INSERT INTO `match_schedule` (`id`, `round`, `id_first_team`, `id_second_team`, `time`, `stadium`, `id_result`) VALUES
(0000000001, 0000000001, 0000000003, 0000000002, '2022-04-01 01:00:00', 'Sân đội 3', 0000000011),
(0000000002, 0000000001, 0000000004, 0000000001, '2022-04-07 01:30:00', 'Sân đội 4', 0000000015),
(0000000003, 0000000001, 0000000005, 0000000001, '2022-04-09 02:00:00', 'Sân đội 5', NULL),
(0000000004, 0000000001, 0000000004, 0000000005, '2022-04-01 02:00:00', 'Sân đội 4', NULL),
(0000000005, 0000000001, 0000000005, 0000000001, '2022-04-09 02:00:00', 'Sân đội 5', NULL),
(0000000006, 0000000001, 0000000002, 0000000001, '2022-04-02 00:00:00', 'Sân đội 5', 0000000001),
(0000000007, 0000000001, 0000000004, 0000000002, '2022-04-16 03:30:00', 'Sân đội 4', NULL),
(0000000010, 0000000002, 0000000004, 0000000002, '2022-04-08 01:30:00', 'Sân đội 4', NULL),
(0000000011, 0000000002, 0000000003, 0000000001, '2022-04-14 00:30:00', 'Sân đội 3', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

CREATE TABLE `player` (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `name` varchar(255) NOT NULL,
  `dob` date NOT NULL,
  `note` varchar(255) NOT NULL,
  `id_type` int(10) UNSIGNED ZEROFILL NOT NULL,
  `id_team` int(10) UNSIGNED ZEROFILL NOT NULL,
  `total_goal` int(10) UNSIGNED ZEROFILL NOT NULL DEFAULT 0000000000
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`id`, `name`, `dob`, `note`, `id_type`, `id_team`, `total_goal`) VALUES
(0000000001, 'Cầu thủ AEE', '2001-04-01', 'ghi chú A', 0000000002, 0000000001, 0000000000),
(0000000002, 'Cầu thủ BE', '2001-03-01', '', 0000000001, 0000000001, 0000000000),
(0000000004, 'Cầu thủ D', '2001-02-01', '', 0000000001, 0000000002, 0000000000),
(0000000005, 'Cầu thủ E', '2001-01-01', 'Ghi chú cầu thủ E', 0000000002, 0000000002, 0000000000),
(0000000006, 'Cầu thủ F', '2001-04-30', 'Cầu thủ F', 0000000001, 0000000001, 0000000000),
(0000000007, 'Cầu thủ G', '2001-04-01', '', 0000000001, 0000000002, 0000000000),
(0000000008, 'Cầu thủ H', '2001-04-21', '', 0000000001, 0000000002, 0000000000),
(0000000010, 'Cầu thủ BC', '2001-04-05', '', 0000000001, 0000000001, 0000000000),
(0000000011, 'Cầu thủ DC', '2001-04-07', '', 0000000001, 0000000001, 0000000000);

-- --------------------------------------------------------

--
-- Table structure for table `player_leaderboard`
--

CREATE TABLE `player_leaderboard` (
  `id_leaderboard` int(10) UNSIGNED ZEROFILL NOT NULL,
  `id_player` int(10) UNSIGNED ZEROFILL NOT NULL,
  `total_goal` int(10) UNSIGNED ZEROFILL NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `priority`
--

CREATE TABLE `priority` (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `name` varchar(255) NOT NULL,
  `orderPriority` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `priority`
--

INSERT INTO `priority` (`id`, `name`, `orderPriority`) VALUES
(0000000001, 'Điểm', 0),
(0000000002, 'Hiệu số', 1),
(0000000003, 'Tổng số bàn thắng', 2),
(0000000004, 'Tổng số bàn đối kháng', 3);

-- --------------------------------------------------------

--
-- Table structure for table `regulation`
--

CREATE TABLE `regulation` (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(10) NOT NULL,
  `value` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `regulation`
--

INSERT INTO `regulation` (`id`, `name`, `type`, `value`, `status`) VALUES
(0000000001, 'Tuổi tối thiểu của cầu thủ', 'int', '16', 1),
(0000000002, 'Tuổi tối đa của cầu thủ', 'int', '40', 1),
(0000000003, 'Số lượng cầu thủ tối thiểu', 'int', '15', 1),
(0000000004, 'Số lượng cầu thủ tối đa', 'int', '22', 1),
(0000000005, 'Số lượng cầu thủ nước ngoài tối đa', 'int', '3', 1),
(0000000006, 'Thời điểm ghi bàn tối đa', 'int', '96', 1),
(0000000007, 'Điểm thắng', 'int', '3', 1),
(0000000008, 'Điểm hoà', 'int', '1', 1),
(0000000009, 'Điểm thua', 'int', '0', 1),
(0000000010, 'Số lượng các loại bàn thắng', 'int', '3', 1);

-- --------------------------------------------------------

--
-- Table structure for table `result`
--

CREATE TABLE `result` (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `first_team_score` int(10) UNSIGNED ZEROFILL NOT NULL,
  `second_team_score` int(10) UNSIGNED ZEROFILL NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `result`
--

INSERT INTO `result` (`id`, `first_team_score`, `second_team_score`) VALUES
(0000000001, 0000000002, 0000000000),
(0000000011, 0000000000, 0000000004),
(0000000015, 0000000000, 0000000000);

-- --------------------------------------------------------

--
-- Table structure for table `result_detail`
--

CREATE TABLE `result_detail` (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `id_result` int(10) UNSIGNED ZEROFILL NOT NULL,
  `id_player` int(10) UNSIGNED ZEROFILL NOT NULL,
  `id_type_of_goal` int(10) UNSIGNED ZEROFILL NOT NULL,
  `time` int(10) UNSIGNED ZEROFILL NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `result_detail`
--

INSERT INTO `result_detail` (`id`, `id_result`, `id_player`, `id_type_of_goal`, `time`) VALUES
(0000000001, 0000000001, 0000000004, 0000000001, 0000000003),
(0000000009, 0000000011, 0000000004, 0000000001, 0000000003),
(0000000010, 0000000011, 0000000005, 0000000001, 0000000007),
(0000000011, 0000000011, 0000000007, 0000000001, 0000000009),
(0000000018, 0000000015, 0000000006, 0000000002, 0000000009),
(0000000021, 0000000011, 0000000008, 0000000001, 0000000014),
(0000000022, 0000000011, 0000000005, 0000000001, 0000000016);

--
-- Triggers `result_detail`
--
DELIMITER $$
CREATE TRIGGER `changeTotalGoalAfterDelete` AFTER DELETE ON `result_detail` FOR EACH ROW BEGIN
UPDATE player SET total_goal = total_goal - 1 where id = old.id_player;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `changeTotalGoalAfterInsert` AFTER INSERT ON `result_detail` FOR EACH ROW BEGIN
UPDATE player SET total_goal = total_goal + 1 where id = new.id_player;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `changeTotalGoalAfterUpdate` AFTER UPDATE ON `result_detail` FOR EACH ROW BEGIN
UPDATE player SET total_goal = total_goal - 1 where id = old.id_player;
        UPDATE player SET total_goal = total_goal + 1 where id = new.id_player;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `round`
--

CREATE TABLE `round` (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `round`
--

INSERT INTO `round` (`id`, `name`) VALUES
(0000000001, 'Loại trực tiếp'),
(0000000002, 'Vòng bảng');

-- --------------------------------------------------------

--
-- Table structure for table `team`
--

CREATE TABLE `team` (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `name` varchar(255) NOT NULL,
  `home_stadium` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `team`
--

INSERT INTO `team` (`id`, `name`, `home_stadium`) VALUES
(0000000001, 'Đội bóng 1', 'Sân đội 1'),
(0000000002, 'Đội bóng 2', 'Sân đội 2'),
(0000000003, 'Đội bóng 3', 'Sân đội 3'),
(0000000004, 'Đội bóng 4', 'Sân đội 4'),
(0000000005, 'Đội bóng 5', 'Sân đội 5');

-- --------------------------------------------------------

--
-- Table structure for table `team_leaderboard`
--

CREATE TABLE `team_leaderboard` (
  `id_leaderboard` int(10) UNSIGNED ZEROFILL NOT NULL,
  `id_team` int(10) UNSIGNED ZEROFILL NOT NULL,
  `total_win` int(10) UNSIGNED ZEROFILL NOT NULL,
  `total_defeat` int(10) UNSIGNED ZEROFILL NOT NULL,
  `total_tire` int(10) UNSIGNED ZEROFILL NOT NULL,
  `difference` int(11) DEFAULT NULL,
  `rank` int(10) UNSIGNED NOT NULL,
  `rank_score` int(10) UNSIGNED ZEROFILL NOT NULL,
  `total_goal` int(10) UNSIGNED ZEROFILL NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `type_of_goal`
--

CREATE TABLE `type_of_goal` (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `name` varchar(255) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `type_of_goal`
--

INSERT INTO `type_of_goal` (`id`, `name`, `status`) VALUES
(0000000001, 'A', 1),
(0000000002, 'B', 1),
(0000000003, 'C', 1);

-- --------------------------------------------------------

--
-- Table structure for table `type_of_player`
--

CREATE TABLE `type_of_player` (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `type_of_player`
--

INSERT INTO `type_of_player` (`id`, `name`) VALUES
(0000000001, 'Trong nước'),
(0000000002, 'Nước ngoài');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `leaderboard`
--
ALTER TABLE `leaderboard`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `match_schedule`
--
ALTER TABLE `match_schedule`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_matchSchedule_round` (`round`),
  ADD KEY `FK_matchSchedule_firstTeam` (`id_first_team`),
  ADD KEY `FK_matchSchedule_secondTeam` (`id_second_team`),
  ADD KEY `FK_matchSchedule_result` (`id_result`);

--
-- Indexes for table `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_player_typeOfPlayer` (`id_type`),
  ADD KEY `FK_player_idTeam` (`id_team`);

--
-- Indexes for table `player_leaderboard`
--
ALTER TABLE `player_leaderboard`
  ADD PRIMARY KEY (`id_leaderboard`,`id_player`),
  ADD KEY `FK_playerLeaderboard_player` (`id_player`);

--
-- Indexes for table `priority`
--
ALTER TABLE `priority`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `regulation`
--
ALTER TABLE `regulation`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `result`
--
ALTER TABLE `result`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `result_detail`
--
ALTER TABLE `result_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_resultDetail_result` (`id_result`),
  ADD KEY `FK_resultDetail_player` (`id_player`),
  ADD KEY `FK_resultDetail_typeOfGoal` (`id_type_of_goal`);

--
-- Indexes for table `round`
--
ALTER TABLE `round`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `team`
--
ALTER TABLE `team`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `team_leaderboard`
--
ALTER TABLE `team_leaderboard`
  ADD PRIMARY KEY (`id_leaderboard`,`id_team`),
  ADD KEY `FK_teamLeaderboard_team` (`id_team`);

--
-- Indexes for table `type_of_goal`
--
ALTER TABLE `type_of_goal`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `type_of_player`
--
ALTER TABLE `type_of_player`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `leaderboard`
--
ALTER TABLE `leaderboard`
  MODIFY `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `match_schedule`
--
ALTER TABLE `match_schedule`
  MODIFY `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `player`
--
ALTER TABLE `player`
  MODIFY `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `priority`
--
ALTER TABLE `priority`
  MODIFY `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `regulation`
--
ALTER TABLE `regulation`
  MODIFY `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `result`
--
ALTER TABLE `result`
  MODIFY `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `result_detail`
--
ALTER TABLE `result_detail`
  MODIFY `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `round`
--
ALTER TABLE `round`
  MODIFY `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `team`
--
ALTER TABLE `team`
  MODIFY `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `type_of_goal`
--
ALTER TABLE `type_of_goal`
  MODIFY `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `type_of_player`
--
ALTER TABLE `type_of_player`
  MODIFY `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `match_schedule`
--
ALTER TABLE `match_schedule`
  ADD CONSTRAINT `FK_matchSchedule_firstTeam` FOREIGN KEY (`id_first_team`) REFERENCES `team` (`id`),
  ADD CONSTRAINT `FK_matchSchedule_result` FOREIGN KEY (`id_result`) REFERENCES `result` (`id`),
  ADD CONSTRAINT `FK_matchSchedule_round` FOREIGN KEY (`round`) REFERENCES `round` (`id`),
  ADD CONSTRAINT `FK_matchSchedule_secondTeam` FOREIGN KEY (`id_second_team`) REFERENCES `team` (`id`);

--
-- Constraints for table `player`
--
ALTER TABLE `player`
  ADD CONSTRAINT `FK_player_idTeam` FOREIGN KEY (`id_team`) REFERENCES `team` (`id`),
  ADD CONSTRAINT `FK_player_typeOfPlayer` FOREIGN KEY (`id_type`) REFERENCES `type_of_player` (`id`);

--
-- Constraints for table `player_leaderboard`
--
ALTER TABLE `player_leaderboard`
  ADD CONSTRAINT `FK_playerLeaderboard_leaderboard` FOREIGN KEY (`id_leaderboard`) REFERENCES `leaderboard` (`id`),
  ADD CONSTRAINT `FK_playerLeaderboard_player` FOREIGN KEY (`id_player`) REFERENCES `player` (`id`);

--
-- Constraints for table `result_detail`
--
ALTER TABLE `result_detail`
  ADD CONSTRAINT `FK_resultDetail_player` FOREIGN KEY (`id_player`) REFERENCES `player` (`id`),
  ADD CONSTRAINT `FK_resultDetail_result` FOREIGN KEY (`id_result`) REFERENCES `result` (`id`),
  ADD CONSTRAINT `FK_resultDetail_typeOfGoal` FOREIGN KEY (`id_type_of_goal`) REFERENCES `type_of_goal` (`id`);

--
-- Constraints for table `team_leaderboard`
--
ALTER TABLE `team_leaderboard`
  ADD CONSTRAINT `FK_teamLeaderboard_leaderboard` FOREIGN KEY (`id_leaderboard`) REFERENCES `leaderboard` (`id`),
  ADD CONSTRAINT `FK_teamLeaderboard_team` FOREIGN KEY (`id_team`) REFERENCES `team` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
