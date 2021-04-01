-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 01. Apr 2021 um 11:21
-- Server-Version: 10.4.18-MariaDB
-- PHP-Version: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `webshop`
--
CREATE DATABASE IF NOT EXISTS `webshop` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `webshop`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `adresse`
--

CREATE TABLE `adresse` (
  `Adress_id` int(11) NOT NULL,
  `Strasse` varchar(40) COLLATE utf8_german2_ci NOT NULL,
  `Hausnummer` varchar(5) COLLATE utf8_german2_ci NOT NULL,
  `PLZ` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

--
-- Daten für Tabelle `adresse`
--

INSERT INTO `adresse` (`Adress_id`, `Strasse`, `Hausnummer`, `PLZ`) VALUES
(1, 'Hochstraße', '1b', 45888),
(2, 'Pferdebahn', '123', 45355),
(3, 'Brunnenstr.', '123', 45966),
(12, 'Friedrichstrasse', '2b', 45468),
(13, 'Osmanstrasse', '4b', 45468),
(666, 'Am Schild', '11', 45147),
(667, 'Im Funkloch', '69', 88899),
(668, 'Hochheide', '16', 45789),
(669, 'Bismarckstrasse', '30', 47441),
(99998, 'Hackepeterstraße', '42', 12345),
(99999, 'Unterm Stein', '1307', 23456),
(100000, 'Hochstraße', '10A', 12346);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `angebot`
--

CREATE TABLE `angebot` (
  `Angebot_Id` int(11) NOT NULL,
  `Bezeichnung` varchar(40) COLLATE utf8_german2_ci NOT NULL,
  `Beschreibung` text COLLATE utf8_german2_ci DEFAULT NULL,
  `Preis` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

--
-- Daten für Tabelle `angebot`
--

INSERT INTO `angebot` (`Angebot_Id`, `Bezeichnung`, `Beschreibung`, `Preis`) VALUES
(1, 'Blu-Ray Harry Potter', 'Länge: 280 min', '13.40'),
(2, 'Sechserpack Dosenbier', 'Hau weg!', '3.79'),
(3, 'Pizza Taifun', 'Ganz viel Tintenfisch', '7.99'),
(12, 'Wein', 'Pilsener Wein', '2.70'),
(13, 'Bier', 'Pilsener Bier', '1.70'),
(666, 'DVD - Hobbit - Trilogie', 'Länge 460', '2.99'),
(667, 'Betamax - Lord of the Ding', 'Länge: sehr sehr lang', '8.99'),
(99998, 'Harry Potter und der Plastikpokal', 'Länge: nicht lang genug', '18.99'),
(99999, 'Bear Grylls - Ausgesetzt in der Wildnis', 'Länge 420', '29.99'),
(100000, 'Bleistift', 'Stift HB', '0.80'),
(100022, 'Mohnbrötchen', 'Hefeteig 200g', '1.20');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `bestellposition`
--

CREATE TABLE `bestellposition` (
  `Angebot_Id` int(11) NOT NULL,
  `Bestellung_Id` int(11) NOT NULL,
  `Anzahl` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

--
-- Daten für Tabelle `bestellposition`
--

INSERT INTO `bestellposition` (`Angebot_Id`, `Bestellung_Id`, `Anzahl`) VALUES
(1, 1, 3),
(2, 3, 4),
(3, 2, 2),
(12, 12, 5),
(12, 100005, 3),
(12, 100006, 3),
(13, 13, 4),
(666, 666, 2),
(667, 667, 6),
(99998, 99998, 16),
(99999, 99999, 2),
(100000, 100005, 5),
(100000, 100006, 5);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `bestellung`
--

CREATE TABLE `bestellung` (
  `Kundennummer` int(11) NOT NULL,
  `Bestellung_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

--
-- Daten für Tabelle `bestellung`
--

INSERT INTO `bestellung` (`Kundennummer`, `Bestellung_id`) VALUES
(1, 1),
(2, 2),
(3, 3),
(12, 12),
(12, 100005),
(12, 100006),
(13, 13),
(666, 666),
(667, 667),
(99998, 99998),
(99999, 99999);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `kunde`
--

CREATE TABLE `kunde` (
  `Kunde_Id` int(11) NOT NULL,
  `vorname` varchar(40) COLLATE utf8_german2_ci NOT NULL,
  `nachname` varchar(40) COLLATE utf8_german2_ci NOT NULL,
  `Adress_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

--
-- Daten für Tabelle `kunde`
--

INSERT INTO `kunde` (`Kunde_Id`, `vorname`, `nachname`, `Adress_id`) VALUES
(1, 'Jens', 'Hoffmann', 1),
(2, 'Herbert', 'Gröleier', 2),
(3, 'Dörte', 'Witzlos', 3),
(12, 'Peter', 'Zentner', 1),
(13, 'Franz', 'Dieter', 12),
(666, 'Axel', 'Schweiz', 666),
(667, 'Willma', 'Bier', 667),
(668, 'Sebi', 'Perle', 12),
(669, 'Sem', 'Tiryana', 13),
(670, 'Sebastian', 'Putz', 12),
(671, 'Mio', 'Oglu', 13),
(99998, 'Mario', 'Nette', 99998),
(99999, 'Uvuweye', 'Ossas', 99999);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `plz`
--

CREATE TABLE `plz` (
  `PLZ` int(5) NOT NULL,
  `Stadt` varchar(40) COLLATE utf8_german2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

--
-- Daten für Tabelle `plz`
--

INSERT INTO `plz` (`PLZ`, `Stadt`) VALUES
(12345, 'Entenhausen'),
(12346, 'Gansberg'),
(23456, 'Rockbottom'),
(45147, 'Essen'),
(45355, 'Essen'),
(45468, 'Muelheim an der Ruhr'),
(45789, 'Duisburg'),
(45888, 'Gelsenkirchen'),
(45966, 'Gladbeck'),
(47441, 'Moers'),
(55234, 'Blödsheim'),
(57489, 'Halbhusten'),
(88899, 'Buxtehude');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `adresse`
--
ALTER TABLE `adresse`
  ADD PRIMARY KEY (`Adress_id`),
  ADD KEY `constraint_PLZ` (`PLZ`);

--
-- Indizes für die Tabelle `angebot`
--
ALTER TABLE `angebot`
  ADD PRIMARY KEY (`Angebot_Id`);

--
-- Indizes für die Tabelle `bestellposition`
--
ALTER TABLE `bestellposition`
  ADD PRIMARY KEY (`Angebot_Id`,`Bestellung_Id`),
  ADD KEY `constraint_Bestellung` (`Bestellung_Id`);

--
-- Indizes für die Tabelle `bestellung`
--
ALTER TABLE `bestellung`
  ADD PRIMARY KEY (`Bestellung_id`),
  ADD KEY `constraint_Kunde` (`Kundennummer`);

--
-- Indizes für die Tabelle `kunde`
--
ALTER TABLE `kunde`
  ADD PRIMARY KEY (`Kunde_Id`),
  ADD KEY `consgtraint_adresse` (`Adress_id`);

--
-- Indizes für die Tabelle `plz`
--
ALTER TABLE `plz`
  ADD PRIMARY KEY (`PLZ`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `adresse`
--
ALTER TABLE `adresse`
  MODIFY `Adress_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=100007;

--
-- AUTO_INCREMENT für Tabelle `angebot`
--
ALTER TABLE `angebot`
  MODIFY `Angebot_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=100023;

--
-- AUTO_INCREMENT für Tabelle `bestellung`
--
ALTER TABLE `bestellung`
  MODIFY `Bestellung_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=100007;

--
-- AUTO_INCREMENT für Tabelle `kunde`
--
ALTER TABLE `kunde`
  MODIFY `Kunde_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=100000;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `adresse`
--
ALTER TABLE `adresse`
  ADD CONSTRAINT `constraint_PLZ` FOREIGN KEY (`PLZ`) REFERENCES `plz` (`PLZ`);

--
-- Constraints der Tabelle `bestellposition`
--
ALTER TABLE `bestellposition`
  ADD CONSTRAINT `bestellposition_ibfk_1` FOREIGN KEY (`Bestellung_Id`) REFERENCES `bestellung` (`Bestellung_id`),
  ADD CONSTRAINT `bestellposition_ibfk_2` FOREIGN KEY (`Angebot_Id`) REFERENCES `angebot` (`Angebot_Id`);

--
-- Constraints der Tabelle `bestellung`
--
ALTER TABLE `bestellung`
  ADD CONSTRAINT `bestellung_ibfk_1` FOREIGN KEY (`Kundennummer`) REFERENCES `kunde` (`Kunde_Id`);

--
-- Constraints der Tabelle `kunde`
--
ALTER TABLE `kunde`
  ADD CONSTRAINT `kunde_ibfk_1` FOREIGN KEY (`Adress_id`) REFERENCES `adresse` (`Adress_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
