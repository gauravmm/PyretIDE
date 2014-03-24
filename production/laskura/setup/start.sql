-- phpMyAdmin SQL Dump
-- version 3.3.10.4
-- http://www.phpmyadmin.net
--
-- Host: mysql.dev.gauravmanek.com
-- Generation Time: Mar 24, 2014 at 12:23 PM
-- Server version: 5.1.56
-- PHP Version: 5.3.27

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `laskura`
--

-- --------------------------------------------------------

--
-- Table structure for table `data`
--

CREATE TABLE IF NOT EXISTS `data` (
  `user_id` int(11) NOT NULL,
  `filename` varchar(128) NOT NULL,
  `updated` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `data` text NOT NULL,
  PRIMARY KEY (`filename`,`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `data`
--

INSERT INTO `data` (`user_id`, `filename`, `updated`, `data`) VALUES
(1, 'test1', '2014-03-24 11:54:29', ''),
(1, 'Test2', '0000-00-00 00:00:00', 'This is yet another test file.'),
(1, '~launchstate', '2014-03-24 11:54:39', 'CURRENT_TAB	0'),
(1, 'test12', '0000-00-00 00:00:00', ''),
(1, 'test3', '2014-03-23 19:22:28', 'This is the new new first test file.'),
(1, 'Test 6', '2014-03-24 12:06:37', 'a'),
(1, '~config', '2014-03-24 11:57:19', '#Format: key=value\r\n#Mon Mar 24 14:54:36 EDT 2014\r\nui.toolbar.iconsize=Dimension\\: [30,20]'),
(1, 'Test 7', '2014-03-25 00:00:00', '');

-- --------------------------------------------------------

--
-- Table structure for table `session`
--

CREATE TABLE IF NOT EXISTS `session` (
  `session_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `started` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`session_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=24 ;

--
-- Dumping data for table `session`
--

INSERT INTO `session` (`session_id`, `user_id`, `started`) VALUES
(3, 0, '2014-03-23 16:24:55'),
(11, 2, '2014-03-24 11:44:17'),
(23, 1, '2014-03-24 12:15:22');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` blob NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `created`) VALUES
(1, 'captain', 0x2a42324237323134443945423644434437423445423736423833443437443441373635324146354237, '2014-03-23 15:14:15'),
(2, 'dummy', 0x2a39344244434542453139303833434532413146393539464430324639363443374146344346433239, '2014-03-23 15:14:15');
