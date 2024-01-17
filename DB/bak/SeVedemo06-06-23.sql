-- phpMyAdmin SQL Dump
-- version 5.0.4deb2+deb11u1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Creato il: Giu 06, 2023 alle 14:54
-- Versione del server: 10.5.19-MariaDB-0+deb11u2
-- Versione PHP: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `SeVedemo`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `Categoria`
--

CREATE TABLE `Categoria` (
  `IdCategoria` int(11) NOT NULL,
  `Categoria` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `Categoria`
--

INSERT INTO `Categoria` (`IdCategoria`, `Categoria`) VALUES
(1, 'Film'),
(2, 'Cocktail'),
(3, 'GiochiInScatola'),
(4, 'Videogiochi'),
(5, 'Ristorante'),
(6, 'Sport-AttivitaFisica'),
(7, 'Altro');

-- --------------------------------------------------------

--
-- Struttura della tabella `Evento`
--

CREATE TABLE `Evento` (
  `IdEvento` int(11) NOT NULL,
  `IdUtente` int(11) NOT NULL,
  `DataInizio` datetime NOT NULL,
  `DataFine` datetime NOT NULL,
  `Titolo` varchar(255) NOT NULL,
  `Descrizione` text NOT NULL,
  `NMaxPartecipanti` int(5) NOT NULL,
  `Posizione` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`Posizione`)),
  `IdCategoria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `Evento`
--

INSERT INTO `Evento` (`IdEvento`, `IdUtente`, `DataInizio`, `DataFine`, `Titolo`, `Descrizione`, `NMaxPartecipanti`, `Posizione`, `IdCategoria`) VALUES
(2, 2, '2023-06-22 07:09:09', '2023-06-23 21:09:09', 'Monopoli Mania', 'Ciao a tutti sto creando questo torneo di monopoli, il primo giorno giocan tutti il secondo i vincitori', 72, '{\"lat\": 45.0890085, \"lng\": 7.6397536}', 3),
(3, 3, '2023-06-08 15:09:09', '2023-06-08 16:09:09', 'Festa ITS', 'Salve sto organizzando una festa all\'ITS siete tutti invitati, alla festa ci sarà musica, open bar e tombola! ', 100, '{\"lat\":45.0817092, \"lng\":7.660215}', 7),
(5, 4, '2023-06-07 19:40:00', '2023-06-07 23:00:00', 'Nuova Trattoria', 'A Burga Town hanno aperto una nuova trattoria, vorrei provarla', 6, '{\"lat\": 41.8, \"lng\": 12.4}', 5),
(6, 5, '2023-06-04 17:40:22', '2023-06-04 19:40:22', 'Filmetto', 'Guardiani della galassia', 4, '{\"lat\": 41.8, \"lng\": 12.4}', 1),
(7, 6, '2023-06-17 15:33:48', '2023-06-17 19:16:59', 'Uscita Gioco tripla A', 'Domenica escirà il videogioco di ammio cuggino, sara bellissimo e fantastisticissimo, divertimento azzigurato', 5, '{\"lat\": 40.858335, \"lng\": 14.275658}', 4),
(8, 4, '2023-06-01 20:11:17', '2023-06-02 10:45:16', '18esimo', 'Festa per i miei 18 anni, al Golf Club la Margherita, per chi vuole c\'è la possibilità di dormire sul luogo...', 44, '{\"lat\":44.857062, \"lng\":7.819764}', 7),
(10, 3, '2023-06-05 13:05:30', '2023-06-05 13:55:30', 'Pausa pranzo', 'Cerco compagnia per la pausa pranzo, pensavo dii andare da Pierugo il paninaro', 3, '{\"lat\": 45.02673221489034, \"lng\": 7.656826403311339}', 5),
(11, 4, '2023-06-26 09:00:00', '2023-06-30 18:00:00', 'Campi parrocchiali 1a media', 'Campi parrocchiali aperti a tutti i ragazzi di prima media, trascorreranno una fantastica settimana tra giochi, momenti formativi e molto altro, una settimana spensierata lontani dalla tecnologia e dalla frenesia della città ', 50, '{\"lat\": 45.370761, \"lng\": 7.281139}', 7),
(12, 2, '2023-06-12 09:40:13', '2023-06-12 12:47:13', 'Lezione', 'PROGRAMMAZIONE - .NET FRAMEWORK / C# con prof che va ultra mega turbo spedivo', 29, '{\"lat\": 45.081702542418654, \"lng\": 7.660214997308384}', 7);

-- --------------------------------------------------------

--
-- Struttura della tabella `Gender`
--

CREATE TABLE `Gender` (
  `IdGender` int(11) NOT NULL,
  `Sesso` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `Gender`
--

INSERT INTO `Gender` (`IdGender`, `Sesso`) VALUES
(1, 'Maschio'),
(2, 'Femmina'),
(3, 'Altro'),
(4, 'NonSpecificato');

-- --------------------------------------------------------

--
-- Struttura della tabella `Partecipazione`
--

CREATE TABLE `Partecipazione` (
  `IdPartecipazione` int(11) NOT NULL,
  `IdUtente` int(11) NOT NULL,
  `IdEvento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `Partecipazione`
--

INSERT INTO `Partecipazione` (`IdPartecipazione`, `IdUtente`, `IdEvento`) VALUES
(11, 1, 6),
(5, 2, 2),
(8, 2, 5),
(9, 3, 7),
(7, 4, 2),
(6, 5, 3);

-- --------------------------------------------------------

--
-- Struttura della tabella `Recensione`
--

CREATE TABLE `Recensione` (
  `IdRecensione` int(11) NOT NULL,
  `IdUtente` int(11) NOT NULL,
  `IdEvento` int(11) NOT NULL,
  `Voto` decimal(10,0) NOT NULL,
  `Descrizione` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `Recensione`
--

INSERT INTO `Recensione` (`IdRecensione`, `IdUtente`, `IdEvento`, `Voto`, `Descrizione`) VALUES
(1, 1, 2, '7', 'Un torneo di Monopoli ben organizzato. Divertimento assicurato per tutti i partecipanti.'),
(2, 4, 2, '8', 'Mi sono divertito molto durante il torneo di Monopoli. Atmosfera piacevole e partite avvincenti.'),
(3, 5, 5, '9', 'La nuova trattoria di Burga Town è stata una piacevole scoperta. Cibo delizioso e servizio eccellente.');

-- --------------------------------------------------------

--
-- Struttura della tabella `Utente`
--

CREATE TABLE `Utente` (
  `IdUtente` int(11) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Nome` text NOT NULL,
  `Cognome` text NOT NULL,
  `Telefono` varchar(255) NOT NULL,
  `IdGender` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `Utente`
--

INSERT INTO `Utente` (`IdUtente`, `Email`, `Nome`, `Cognome`, `Telefono`, `IdGender`) VALUES
(1, 'Catania.Danari@gmail.com', 'Catania', 'Danari', '12354685148', 1),
(2, 'franchinocino@gmail.com', 'Franchino', 'Cino', '3370399134', 4),
(3, 'gianna.pigmalione@gmail.com', 'Gianna', 'Pigmalione', '3302511356', 2),
(4, 'gianni.rodari@scamail.it', 'Gianni', 'Rodari', '123456122', 4),
(5, 'mario.rossi@gmail.com', 'Mario', 'Rossi', '3491324576', 1),
(6, 'Maura.Pittavina@gmail.com', 'Maura', 'Pittavina', '2453228976', 2),
(7, 'raimondo.scrofaldi@hotmail.it', 'Raimondo', 'Scrofaldi', '12343335678', 3);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `Categoria`
--
ALTER TABLE `Categoria`
  ADD PRIMARY KEY (`IdCategoria`);

--
-- Indici per le tabelle `Evento`
--
ALTER TABLE `Evento`
  ADD PRIMARY KEY (`IdEvento`),
  ADD KEY `IdCategoria` (`IdCategoria`) USING BTREE,
  ADD KEY `IdUtente` (`IdUtente`);

--
-- Indici per le tabelle `Gender`
--
ALTER TABLE `Gender`
  ADD PRIMARY KEY (`IdGender`);

--
-- Indici per le tabelle `Partecipazione`
--
ALTER TABLE `Partecipazione`
  ADD PRIMARY KEY (`IdPartecipazione`),
  ADD UNIQUE KEY `UQ_IdPartecipazione` (`IdPartecipazione`),
  ADD UNIQUE KEY `UQ_IdUtente_IdEvento` (`IdUtente`,`IdEvento`);

--
-- Indici per le tabelle `Recensione`
--
ALTER TABLE `Recensione`
  ADD PRIMARY KEY (`IdRecensione`),
  ADD UNIQUE KEY `UQ_IdUtente_IdEvento` (`IdUtente`,`IdEvento`),
  ADD KEY `FK_Recensione_Evento` (`IdEvento`);

--
-- Indici per le tabelle `Utente`
--
ALTER TABLE `Utente`
  ADD PRIMARY KEY (`IdUtente`),
  ADD KEY `FK_Gender_Utente` (`IdGender`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `Evento`
--
ALTER TABLE `Evento`
  MODIFY `IdEvento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT per la tabella `Partecipazione`
--
ALTER TABLE `Partecipazione`
  MODIFY `IdPartecipazione` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT per la tabella `Recensione`
--
ALTER TABLE `Recensione`
  MODIFY `IdRecensione` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT per la tabella `Utente`
--
ALTER TABLE `Utente`
  MODIFY `IdUtente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `Evento`
--
ALTER TABLE `Evento`
  ADD CONSTRAINT `FK_Categoria_Evento` FOREIGN KEY (`IdCategoria`) REFERENCES `Categoria` (`IdCategoria`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Limiti per la tabella `Partecipazione`
--
ALTER TABLE `Partecipazione`
  ADD CONSTRAINT `FK_Partecipazione_Evento` FOREIGN KEY (`IdEvento`) REFERENCES `Evento` (`IdEvento`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_Partecipazione_Utente` FOREIGN KEY (`IdUtente`) REFERENCES `Utente` (`IdUtente`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Limiti per la tabella `Recensione`
--
ALTER TABLE `Recensione`
  ADD CONSTRAINT `FK_Recensione_Evento` FOREIGN KEY (`IdEvento`) REFERENCES `Evento` (`IdEvento`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_Recensione_Utente` FOREIGN KEY (`IdUtente`) REFERENCES `Utente` (`IdUtente`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Limiti per la tabella `Utente`
--
ALTER TABLE `Utente`
  ADD CONSTRAINT `FK_Gender_Utente` FOREIGN KEY (`IdGender`) REFERENCES `Gender` (`IdGender`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
