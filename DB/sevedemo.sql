-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 18, 2024 at 03:30 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sevedemo`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetEventiAttiviFiltrati` (IN `sp_filtroCategoria` VARCHAR(255), IN `sp_filtroCitta` VARCHAR(255), IN `sp_filtroData` DATETIME, IN `sp_filtroHost` VARCHAR(255))   BEGIN
    DECLARE nome_host TEXT;

    -- Eventi attivi
    SELECT CAST(e.id_evento AS INT) AS id_evento,
    	CAST(e.id_utente AS INT) AS id_utente,
        e.titolo,
        c.categoria,
        e.data_inizio,
        e.data_fine,
        e.descrizione,
        CAST(e.n_max_partecipanti AS INT) AS n_max_partecipanti,
        sp.nome AS nome_host,
        sp.cognome AS cognome_host,
        CAST(COUNT(p.id_partecipazione) AS INT) AS numero_partecipanti,
        e.posizione
    FROM evento e
        LEFT JOIN partecipazione p ON e.id_evento = p.id_evento
        JOIN categoria c ON e.id_categoria = c.id_categoria
        JOIN (
            SELECT DISTINCT e.id_utente, u.nome, u.cognome
            FROM evento e
            JOIN utente u ON e.id_utente = u.id_utente
        ) AS sp ON e.id_utente = sp.id_utente
    WHERE e.data_inizio >= CURDATE()
        AND (sp_filtroCategoria IS NULL OR c.categoria = sp_filtroCategoria)
        AND (sp_filtroCitta IS NULL OR e.posizione LIKE CONCAT('%', sp_filtroCitta, '%'))
        AND (sp_filtroData IS NULL OR DATE(e.data_inizio) = DATE(sp_filtroData))
        AND (sp_filtroHost IS NULL OR sp.cognome = sp_filtroHost)
    GROUP BY e.id_evento, e.titolo;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetTuttiEventiFiltriDinamici` (IN `sp_filtroCategoria` VARCHAR(255), IN `sp_filtroCitta` VARCHAR(255), IN `sp_filtroData` DATE, IN `sp_filtroHost` VARCHAR(255))   BEGIN
    DECLARE nome_host TEXT;

    SELECT CAST(e.id_evento AS INT) AS id_evento,
    	CAST(e.id_utente AS INT) AS id_utente,
        e.titolo,
        c.categoria,
        e.data_inizio,
        e.data_fine,
        e.descrizione,
        CAST(e.n_max_partecipanti AS INT) AS n_max_partecipanti,
        sp.nome AS nome_host,
        sp.cognome AS cognome_host,
        CAST(COUNT(p.id_partecipazione) AS INT) AS numero_partecipanti,
        e.posizione
    FROM evento e
        LEFT JOIN partecipazione p ON e.id_evento = p.id_evento
        JOIN categoria c ON e.id_categoria = c.id_categoria
        JOIN (
            SELECT DISTINCT e.id_utente, u.nome, u.cognome
            FROM evento e
            JOIN utente u ON e.id_utente = u.id_utente
        ) AS sp ON e.id_utente = sp.id_utente
    WHERE e.data_inizio >= CURDATE()
        AND (sp_filtroCategoria IS NULL OR sp_filtroCategoria = '' OR c.categoria = sp_filtroCategoria)
        AND (sp_filtroCitta IS NULL OR sp_filtroCitta = '' OR e.posizione LIKE CONCAT('%', sp_filtroCitta, '%'))
        AND (sp_filtroData IS NULL OR DATE(e.data_inizio) = DATE(sp_filtroData) OR sp_filtroData = '0000-00-00')
        AND (sp_filtroHost IS NULL OR sp_filtroHost = '' OR sp.cognome = sp_filtroHost)
    GROUP BY e.id_evento, e.titolo;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_DeleteEventoByIdUtente` (IN `p_Id` INT, OUT `p_IsDeleted` BOOLEAN)   BEGIN
  DECLARE v_Count INT;

  SET p_IsDeleted = FALSE;

    SET v_Count = (
    SELECT COUNT(*)
    FROM evento
    WHERE id_evento = p_Id
  );

  IF v_Count > 0 THEN
        DELETE FROM evento
    WHERE id_evento = p_Id;

    SET p_IsDeleted = TRUE;
  END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_EliminaFotoByIdUtente` (IN `p_IdUtente` INT)   BEGIN
    -- Elimina le foto associate all'utente
    DELETE FROM file_data
    WHERE id_utente = p_IdUtente;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_GetEventiAttivi` ()   BEGIN
    DECLARE nome_host TEXT;

    SELECT CAST(e.id_evento AS INT) AS id_evento,
    	CAST(e.id_utente AS INT) AS id_utente,
        e.titolo,
        c.categoria,
        e.data_inizio,
        e.data_fine,
        e.descrizione,
        CAST(e.n_max_partecipanti AS INT) AS n_max_partecipanti,
        sp.nome AS nome_host,
        sp.cognome AS cognome_host,
        CAST(COUNT(p.id_partecipazione) AS INT) AS numero_partecipanti,
        e.posizione
    FROM evento e
        LEFT JOIN partecipazione p ON e.id_evento = p.id_evento
        JOIN categoria c ON e.id_categoria = c.id_categoria
        JOIN (
            SELECT DISTINCT e.id_utente, u.nome, u.cognome
            FROM evento e
            JOIN utente u ON e.id_utente = u.id_utente
        ) AS sp ON e.id_utente = sp.id_utente
    WHERE e.data_inizio >= CURDATE()     GROUP BY e.id_evento, e.titolo;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_GetEventibyIdEvento` (IN `sp_idCategoria` INT)   BEGIN

SELECT CAST(e.id_evento AS INT) AS id_evento,
			CAST(e.id_utente AS INT) AS id_utente,
            e.titolo,
            c.categoria,
            e.data_inizio,
            e.data_fine,
            e.descrizione,
            CAST(e.n_max_partecipanti AS INT) AS n_max_partecipanti,
            sp.nome AS nome_host,
            sp.cognome AS cognome_host,
            CAST(COUNT(p.id_partecipazione) AS INT) AS numero_partecipanti,
            e.posizione
            FROM evento e
            LEFT JOIN partecipazione p ON e.id_evento = p.id_evento
            JOIN categoria c ON e.id_categoria = c.id_categoria
            JOIN (
               SELECT DISTINCT e.id_utente, u.cognome, u.nome
               FROM evento e
             JOIN utente u ON e.id_utente = u.id_utente
            ) AS sp ON e.id_utente = sp.id_utente
            WHERE e.id_evento = sp_idCategoria
            GROUP BY e.id_evento, e.titolo;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_GetEventiOrganizzatiHostFuturi` (IN `sp_idHost` INT)   BEGIN
    SELECT CAST(e.id_evento AS UNSIGNED) AS id_evento,
        CAST(p.id_partecipazione AS UNSIGNED) AS id_partecipazione,
        u.email,
        u.nome,
        u.cognome,
        e.titolo AS 'Evento',
        e.descrizione AS 'Descrizione evento',
        CAST(e.n_max_partecipanti AS UNSIGNED) AS 'Numero massimo partecipanti',
        e.posizione,
        e.data_inizio,
        e.data_fine,
        c.categoria AS 'Categoria evento'
    FROM evento e
    LEFT JOIN partecipazione p ON e.id_evento = p.id_evento
    JOIN utente u ON p.id_utente = u.id_utente
    JOIN categoria c ON e.id_categoria = c.id_categoria
    WHERE e.id_utente = sp_idHost AND e.data_inizio > CURRENT_DATE
    ORDER BY e.id_evento ASC;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_GetEventiOrganizzatiHostPassati` (IN `sp_idHost` INT)   BEGIN

    SELECT CAST(e.id_evento AS UNSIGNED) AS id_evento,
    	CAST(e.id_utente AS UNSIGNED) AS id_utente,
        e.titolo,
        c.categoria,
        e.data_inizio,
        e.data_fine,
        e.descrizione,
        CAST(e.n_max_partecipanti AS UNSIGNED) AS n_max_partecipanti,
        sp.nome AS nome_host,
        sp.cognome AS cognome_host,
        CAST(COUNT(p.id_partecipazione) AS UNSIGNED) AS numero_partecipanti,
        AVG(IFNULL(r.voto, NULL)) AS media_voti, -- Aggiunta della media dei voti delle recensioni
        e.posizione
    FROM evento e
        LEFT JOIN partecipazione p ON e.id_evento = p.id_evento
        LEFT JOIN recensione r ON e.id_evento = r.id_evento -- Unione con la tabella delle recensioni
        JOIN categoria c ON e.id_categoria = c.id_categoria
        JOIN (
            SELECT DISTINCT e.id_utente, u.cognome, u.nome
            FROM evento e
            JOIN utente u ON e.id_utente = u.id_utente
        ) AS sp ON e.id_utente = sp.id_utente
    WHERE sp.id_utente = sp_idHost AND e.data_inizio < CURRENT_DATE
    GROUP BY e.id_evento, e.titolo;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_GetEventiPartecipati` (IN `p_Id` INT)   BEGIN
SELECT CAST(e.id_evento AS INT) AS id_evento,
    	CAST(e.id_utente AS INT) AS id_utente,
        e.titolo,
        c.categoria,
        e.data_inizio,
        e.data_fine,
        e.descrizione,
        CAST(e.n_max_partecipanti AS INT) AS n_max_partecipanti,
        sp.nome AS nome_host,
        sp.cognome AS cognome_host,
        CAST(COUNT(p.id_partecipazione) AS INT) AS numero_partecipanti,
        AVG(IFNULL(r.voto, NULL)) AS media_voti,
        e.posizione
    FROM evento e
        LEFT JOIN partecipazione p ON e.id_evento = p.id_evento
        LEFT JOIN recensione r ON e.id_evento = r.id_evento
        JOIN categoria c ON e.id_categoria = c.id_categoria
        JOIN (
            SELECT DISTINCT e.id_utente, u.nome, u.cognome
            FROM evento e
            JOIN utente u ON e.id_utente = u.id_utente
        ) AS sp ON e.id_utente = sp.id_utente
    WHERE e.data_fine < CURDATE()
        AND p.id_utente = p_id -- Filtraggio per ID utente
    GROUP BY e.id_evento, e.titolo;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_GetEventiPassati` ()   BEGIN
    DECLARE nome_host TEXT;

    SELECT CAST(e.id_evento AS INT) AS id_evento,
    	CAST(e.id_utente AS INT) AS id_utente,
        e.titolo,
        c.categoria,
        e.data_inizio,
        e.data_fine,
        e.descrizione,
        CAST(e.n_max_partecipanti AS INT) AS n_max_partecipanti,
        sp.nome AS nome_host,
        sp.cognome AS cognome_host,
        CAST(COUNT(p.id_partecipazione) AS INT) AS numero_partecipanti,
        AVG(IFNULL(r.voto, NULL)) AS media_voti, -- Aggiunta della media dei voti delle recensioni
        e.posizione
    FROM evento e
        LEFT JOIN partecipazione p ON e.id_evento = p.id_evento
        LEFT JOIN recensione r ON e.id_evento = r.id_evento -- Unione con la tabella delle recensioni
        JOIN categoria c ON e.id_categoria = c.id_categoria
        JOIN (
            SELECT DISTINCT e.id_utente, u.nome, u.cognome
            FROM evento e
            JOIN utente u ON e.id_utente = u.id_utente
        ) AS sp ON e.id_utente = sp.id_utente
    WHERE e.data_fine < CURDATE() -- Filtraggio degli eventi passati
    GROUP BY e.id_evento, e.titolo;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_GetEventiPerCategoria` (IN `sp_categoria` TEXT)   BEGIN
    DECLARE nome_host TEXT;

    SELECT CAST(e.id_evento AS INT) AS id_evento,
    	CAST(e.id_utente AS INT) AS id_utente,
        e.titolo,
        c.categoria,
        e.data_inizio,
        e.data_fine,
        e.descrizione,
        CAST(e.n_max_partecipanti AS INT) AS n_max_partecipanti,
        sp.nome AS nome_host,
        sp.cognome as cognome_host,
        CAST(COUNT(p.id_partecipazione) AS INT) AS numero_partecipanti,
        e.posizione
    FROM evento e
        LEFT JOIN partecipazione p ON e.id_evento = p.id_evento
        JOIN categoria c ON e.id_categoria = c.id_categoria
        JOIN (
            SELECT DISTINCT e.id_utente, u.nome, u.cognome
            FROM evento e
            JOIN utente u ON e.id_utente = u.id_utente
        ) AS sp ON e.id_utente = sp.id_utente
    WHERE c.categoria = sp_categoria AND e.data_fine > CURRENT_DATE
    GROUP BY e.id_evento, e.titolo;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_GetEventiPerCitta` (IN `sp_citta` TEXT)   BEGIN
    SELECT CAST(e.id_evento AS UNSIGNED) AS id_evento,
    	CAST(e.id_utente AS UNSIGNED) AS id_utente,
        e.titolo,
        c.categoria,
        e.data_inizio,
        e.data_fine,
        e.descrizione,
        CAST(e.n_max_partecipanti AS UNSIGNED) AS n_max_partecipanti,
        sp.nome AS nome_host,
        sp.cognome as cognome_host,
        CAST(COUNT(p.id_partecipazione) AS UNSIGNED) AS numero_partecipanti,
        e.posizione
    FROM evento e
        LEFT JOIN partecipazione p ON e.id_evento = p.id_evento
        JOIN categoria c ON e.id_categoria = c.id_categoria
        JOIN (
            SELECT DISTINCT e.id_utente, u.nome, u.cognome
            FROM evento e
            JOIN utente u ON e.id_utente = u.id_utente
        ) AS sp ON e.id_utente = sp.id_utente
    WHERE e.posizione LIKE CONCAT('%', sp_citta, '%') AND e.data_fine > CURRENT_DATE
    GROUP BY e.id_evento, e.titolo;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_GetEventiPerData` (IN `sp_data` DATETIME)   BEGIN
    SELECT CAST(e.id_evento AS UNSIGNED) AS id_evento,
    CAST(e.id_utente AS UNSIGNED) AS id_utente,
        e.titolo,
        c.categoria,
        e.data_inizio,
        e.data_fine,
        e.descrizione,
        CAST(e.n_max_partecipanti AS UNSIGNED) AS n_max_partecipanti,
        sp.nome AS nome_host,
        sp.cognome AS cognome_host,
        CAST(COUNT(p.id_partecipazione) AS UNSIGNED) AS numero_partecipanti,
        e.posizione
    FROM evento e
        LEFT JOIN partecipazione p ON e.id_evento = p.id_evento
        JOIN categoria c ON e.id_categoria = c.id_categoria
        JOIN (
            SELECT DISTINCT e.id_utente, u.nome, u.cognome
            FROM evento e
            JOIN utente u ON e.id_utente = u.id_utente
        ) AS sp ON e.id_utente = sp.id_utente
    WHERE DATE(e.data_inizio) = DATE(sp_data) AND e.data_fine > CURRENT_DATE
    GROUP BY e.id_evento, e.titolo;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_GetEventiPerHost` (IN `sp_host` VARCHAR(255))   BEGIN
    DECLARE nome_host TEXT;
    SET nome_host := sp_host;

    SELECT CAST(e.id_evento AS UNSIGNED) AS id_evento,
    	CAST(e.id_utente AS UNSIGNED) AS id_utente,
        e.titolo,
        c.categoria,
        e.data_inizio,
        e.data_fine,
        e.descrizione,
        CAST(e.n_max_partecipanti AS UNSIGNED) AS n_max_partecipanti,
        sp.nome AS nome_host,
        sp.cognome AS cognome_host,
        CAST(COUNT(p.id_partecipazione) AS UNSIGNED) AS numero_partecipanti,
        e.posizione
    FROM evento e
        LEFT JOIN partecipazione p ON e.id_evento = p.id_evento
        JOIN categoria c ON e.id_categoria = c.id_categoria
        JOIN (
            SELECT DISTINCT e.id_utente, u.cognome, u.nome
            FROM evento e
            JOIN utente u ON e.id_utente = u.id_utente
        ) AS sp ON e.id_utente = sp.id_utente
    WHERE sp.cognome = sp_host AND e.data_inizio > CURRENT_DATE
    GROUP BY e.id_evento, e.titolo;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_GetListaEventiUtente` (IN `p_id` INT)   BEGIN
	SELECT DISTINCT e.id_evento,p.id_partecipazione , u.email, u.nome, u.cognome, e.titolo as       'Evento', e.descrizione as 'Descrizione evento', e.n_max_partecipanti     as 'Numero massimo partecipanti', e.posizione, e.data_inizio,         e.data_fine, c.categoria as 'Categoria evento'
    FROM utente u
    JOIN partecipazione p ON p.id_utente = u.id_utente
    JOIN evento e ON p.id_evento = e.id_evento
    JOIN categoria c ON c.id_categoria = e.id_categoria
    WHERE e.id_utente = p_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_GetRecensioniById` (IN `p_id` INT)   BEGIN
    SELECT r.id_recensione as 'id_recensione', r.descrizione AS 'Recensione', r.voto AS 'Voto', u.email AS 'Autore recensione', e.titolo AS 'Evento',
    c.categoria as 'Categoria', e.data_inizio as 'Data inizio'
    FROM utente u
    JOIN recensione r ON r.id_utente = u.id_utente
    JOIN evento e ON e.id_evento = r.id_evento
    JOIN categoria c ON c.id_categoria = e.id_categoria
    WHERE u.id_utente = p_id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_GetUtentiByIdEvento` (IN `p_id` INT)   BEGIN
    SELECT DISTINCT CAST(u.id_utente AS INT) AS id_utente,
        u.nome,
        u.cognome,
        u.foto
    FROM evento e
    LEFT JOIN partecipazione p ON p.id_evento = e.id_evento
    LEFT JOIN utente u ON u.id_utente = p.id_utente
    WHERE e.id_evento = p_id;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `categoria`
--

CREATE TABLE `categoria` (
  `id_categoria` int(11) NOT NULL,
  `categoria` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categoria`
--

INSERT INTO `categoria` (`id_categoria`, `categoria`) VALUES
(1, 'Film'),
(2, 'Cocktail'),
(3, 'GiochiInScatola'),
(4, 'Videogiochi'),
(5, 'Ristorante'),
(6, 'Sport-AttivitaFisica'),
(7, 'Altro');

-- --------------------------------------------------------

--
-- Table structure for table `evento`
--

CREATE TABLE `evento` (
  `id_evento` int(11) NOT NULL,
  `id_utente` int(11) NOT NULL,
  `data_inizio` datetime NOT NULL,
  `data_fine` datetime NOT NULL,
  `titolo` varchar(255) NOT NULL,
  `descrizione` text NOT NULL,
  `n_max_partecipanti` int(11) NOT NULL,
  `posizione` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `id_categoria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `evento`
--

INSERT INTO `evento` (`id_evento`, `id_utente`, `data_inizio`, `data_fine`, `titolo`, `descrizione`, `n_max_partecipanti`, `posizione`, `id_categoria`) VALUES
(1, 1, '2023-06-06 18:00:00', '2023-06-06 22:00:00', 'Serata cinema', 'Serata di proiezione di film italiani', 20, '{\"lat\":\"45.071233\",\"lon\":\"7.688134\",\"indirizzo\":\"Via Nizza, 230, Torino, Piemonte, Italia\"}', 1),
(2, 2, '2023-06-09 20:00:00', '2023-06-09 23:00:00', 'Aperitivo in terrazza', 'Aperitivo in terrazza con vista sulla città', 15, '{\"lat\":\"45.067746\",\"lon\":\"7.693805\",\"indirizzo\":\"Corso Vittorio Emanuele II, 1, Torino, Piemonte, Italia\"}', 2),
(3, 3, '2023-06-14 14:00:00', '2023-06-14 18:00:00', 'Giochi di società', 'Pomeriggio di giochi di società', 10, '{\"lat\":\"45.068858\",\"lon\":\"7.678026\",\"indirizzo\":\"Via Sant\'Ottavio, 32, Torino, Piemonte, Italia\"}', 3),
(4, 4, '2023-06-09 16:00:00', '2023-06-10 20:00:00', 'Torneo di videogiochi', 'Torneo di videogiochi con premi per i vincitori', 8, '{\"lat\":\"45.063588\",\"lon\":\"7.660413\",\"indirizzo\":\"Via San Francesco da Paola, 27, Torino, Piemonte, Italia\"}', 4),
(5, 5, '2023-06-13 00:00:00', '2023-06-13 22:00:00', 'Degustazione di vini', 'Degustazione di vini pregiati', 12, '{\"lat\":\"45.057896\",\"lon\":\"7.664511\",\"indirizzo\":\"Via Principe Amedeo, 21, Torino, Piemonte, Italia\"}', 5),
(6, 6, '2023-07-08 19:00:00', '2023-07-08 22:30:00', 'Cena gourmet', 'Cena gourmet con piatti prelibati', 10, '{\"lat\":\"45.060769\",\"lon\":\"7.679730\",\"indirizzo\":\"Via Madama Cristina, 71, Torino, Piemonte, Italia\"}', 1),
(8, 8, '2023-07-12 14:00:00', '2023-07-12 18:00:00', 'Gara di cucina', 'Gara di cucina tra amici', 8, '{\"lat\":\"45.051754\",\"lon\":\"7.666942\",\"indirizzo\":\"Corso Regina Margherita, 177, Torino, Piemonte, Italia\"}', 3),
(9, 9, '2023-07-14 16:00:00', '2023-07-14 20:00:00', 'Torneo di calcetto', 'Torneo di calcetto con premi per i vincitori', 12, '{\"lat\":\"45.064879\",\"lon\":\"7.655480\",\"indirizzo\":\"Via Pietro Micca, 13, Torino, Piemonte, Italia\"}', 4),
(10, 10, '2023-07-16 19:00:00', '2023-07-16 22:00:00', 'Degustazione di birre artigianali', 'Degustazione di birre artigianali di produzione locale', 20, '{\"lat\":\"45.064110\",\"lon\":\"7.677283\",\"indirizzo\":\"Via Cesare Battisti, 7, Torino, Piemonte, Italia\"}', 5),
(11, 11, '2023-07-01 11:05:00', '2023-07-02 11:05:00', 'prova 1 ', 'ciao questo è un evento di prova', 23, '{\"lat\":\"45.0656923\",\"lon\":\"7.6840939\",\"indirizzo\":\"22, Lidl, Circoscrizione 1, Torino, 10123\"}', 7),
(12, 1, '2023-07-20 11:19:25', '2023-07-21 11:19:25', 'Pizzata delle medie', 'Ciao a tutti ci ritroviamo con i compagni di medie per fare un pizzata!', 20, '{\"lat\":\"45.067746\",\"lon\":\"7.693805\",\"indirizzo\":\"Corso Vittorio Emanuele II, 1, Torino, Piemonte, Italia\"}', 5),
(13, 12, '2023-07-06 11:32:00', '2023-07-06 11:32:00', 'Pizzata', 'ciao a tutti facciamo una pizzata', 10, '{\"lat\":\"45.0516137\",\"lon\":\"7.6488671\",\"indirizzo\":\"Via Tripoli, 10 int. 12, Torino, Piemonte, Italia\"}', 5);

-- --------------------------------------------------------

--
-- Table structure for table `file_data`
--

CREATE TABLE `file_data` (
  `id` int(11) NOT NULL,
  `id_utente` int(11) NOT NULL,
  `name` text NOT NULL,
  `type` text NOT NULL,
  `file_path` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `file_data`
--

INSERT INTO `file_data` (`id`, `id_utente`, `name`, `type`, `file_path`) VALUES
(10, 19, 'ILTQq.png', 'image/png', 'C:/Foto-Profili/ILTQq.png');

-- --------------------------------------------------------

--
-- Table structure for table `gender`
--

CREATE TABLE `gender` (
  `id_gender` int(11) NOT NULL,
  `sesso` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `gender`
--

INSERT INTO `gender` (`id_gender`, `sesso`) VALUES
(1, 'Maschio'),
(2, 'Femmina'),
(3, 'Altro'),
(4, 'NonSpecificato');

-- --------------------------------------------------------

--
-- Table structure for table `partecipazione`
--

CREATE TABLE `partecipazione` (
  `id_partecipazione` int(11) NOT NULL,
  `id_utente` int(11) NOT NULL,
  `id_evento` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `partecipazione`
--

INSERT INTO `partecipazione` (`id_partecipazione`, `id_utente`, `id_evento`) VALUES
(1, 1, 1),
(5, 1, 2),
(9, 1, 3),
(13, 1, 6),
(23, 1, 8),
(27, 1, 9),
(32, 1, 10),
(2, 2, 1),
(6, 2, 2),
(10, 2, 3),
(14, 2, 6),
(24, 2, 8),
(28, 2, 9),
(33, 2, 10),
(3, 3, 1),
(7, 3, 2),
(11, 3, 4),
(15, 3, 6),
(25, 3, 8),
(29, 3, 9),
(34, 3, 10),
(4, 4, 1),
(8, 4, 2),
(64, 4, 3),
(12, 4, 5),
(16, 4, 6),
(26, 4, 8),
(30, 4, 9),
(35, 4, 10),
(17, 5, 6),
(31, 5, 9),
(36, 5, 10),
(37, 6, 10),
(38, 7, 10),
(39, 8, 10),
(40, 9, 10),
(41, 10, 10),
(42, 11, 6);

-- --------------------------------------------------------

--
-- Table structure for table `preferiti`
--

CREATE TABLE `preferiti` (
  `id` int(11) NOT NULL,
  `id_utente` int(11) NOT NULL,
  `id_utente_preferito` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `preferiti`
--

INSERT INTO `preferiti` (`id`, `id_utente`, `id_utente_preferito`) VALUES
(2, 7, 3),
(5, 7, 5);

-- --------------------------------------------------------

--
-- Table structure for table `recensione`
--

CREATE TABLE `recensione` (
  `id_recensione` int(11) NOT NULL,
  `id_utente` int(11) NOT NULL,
  `id_evento` int(11) NOT NULL,
  `voto` int(11) NOT NULL,
  `descrizione` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `recensione`
--

INSERT INTO `recensione` (`id_recensione`, `id_utente`, `id_evento`, `voto`, `descrizione`) VALUES
(2, 2, 1, 5, 'Ottima serata, mi sono divertita molto e ho fatto nuove conoscenze.'),
(4, 4, 1, 4, 'Serata ben organizzata e divertente.'),
(5, 1, 2, 5, 'Vista mozzafiato, ottimi cocktail e compagnia piacevole.'),
(6, 2, 2, 4, 'Bel locale, buoni drink, ma il prezzo un po elevato.'),
(7, 3, 2, 3, 'Location suggestiva, ma non ho trovato nulla di eccezionale nel cibo e nei drink.'),
(8, 4, 2, 5, 'Ottima serata, il panorama è stato il top.'),
(9, 1, 3, 4, 'Divertente pomeriggio, i giochi erano interessanti.'),
(10, 2, 3, 5, 'Mi sono divertita molto, bel gruppo di persone.'),
(11, 3, 4, 3, 'Torneo divertente, ma non ho apprezzato la scelta dei videogiochi.'),
(12, 4, 5, 4, 'Degustazione interessante, i vini erano molto buoni.'),
(43, 1, 6, 4, 'recensione di prova'),
(48, 3, 1, 5, 'se funziona sono un drago'),
(51, 4, 3, 5, 'se funziona sono un drago');

-- --------------------------------------------------------

--
-- Table structure for table `utente`
--

CREATE TABLE `utente` (
  `id_utente` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nome` text NOT NULL,
  `cognome` text NOT NULL,
  `telefono` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `id_gender` int(11) NOT NULL,
  `presentazione` varchar(500) NOT NULL,
  `ruolo` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `utente`
--

INSERT INTO `utente` (`id_utente`, `email`, `nome`, `cognome`, `telefono`, `password`, `id_gender`, `presentazione`, `ruolo`) VALUES
(1, 'mario.rossi@gmail.com', 'Paolo', 'Rossi', '3331234567', '$2a$10$DGX7C6qvFRqgdKA2thzZ4eEXc8qjxMqL.9g0V1AiI33CCkaqT0C8W', 1, 'Ciao a tutti!', 'utente'),
(2, 'laura.bianchi@yahoo.it', 'Laura', 'Bianchi', '3337654321', '$2a$10$DGX7C6qvFRqgdKA2thzZ4eEXc8qjxMqL.9g0V1AiI33CCkaqT0C8W', 2, 'Sono appassionata di Viaggi', 'utente'),
(3, 'giuseppe.verdi@hotmail.com', 'Giuseppe', 'Verdi', '3339876543', '$2a$10$DGX7C6qvFRqgdKA2thzZ4eEXc8qjxMqL.9g0V1AiI33CCkaqT0C8W', 1, 'Ciao a tutti!', 'utente'),
(4, 'paolo.neri@gmail.com', 'Paolo', 'Neri', '3333456789', '$2a$10$DGX7C6qvFRqgdKA2thzZ4eEXc8qjxMqL.9g0V1AiI33CCkaqT0C8W', 4, 'Non ho molto da dire', 'utente'),
(5, 'anna.rossi@gmail.com', 'Anna', 'Rossi', '3331122334', '$2a$10$DGX7C6qvFRqgdKA2thzZ4eEXc8qjxMqL.9g0V1AiI33CCkaqT0C8W', 2, 'Ciao a tutti!', 'utente'),
(6, 'giovanni.rossi@gmail.com', 'Giovanni', 'Rossi', '3335566778', '$2a$10$DGX7C6qvFRqgdKA2thzZ4eEXc8qjxMqL.9g0V1AiI33CCkaqT0C8W', 1, 'Ciao a tutti!', 'utente'),
(7, 'francesca.verdi@yahoo.it', 'Francesca', 'Verdi', '3331122334', '$2a$10$DGX7C6qvFRqgdKA2thzZ4eEXc8qjxMqL.9g0V1AiI33CCkaqT0C8W', 2, 'Sono appassionata di cinema', 'utente'),
(8, 'marta.neri@hotmail.com', 'Marta', 'Neri', '3334455667', '$2a$10$DGX7C6qvFRqgdKA2thzZ4eEXc8qjxMqL.9g0V1AiI33CCkaqT0C8W', 2, 'Ciao a tutti!', 'utente'),
(9, 'luca.bianchi@gmail.com', 'Luca', 'Bianchi', '3339988776', '$2a$10$DGX7C6qvFRqgdKA2thzZ4eEXc8qjxMqL.9g0V1AiI33CCkaqT0C8W', 1, 'Sono un grande appassionato di videogiochi', 'utente'),
(10, 'giulia.rossi@gmail.com', 'Giulia', 'Rossi', '3331234567', '$2a$10$DGX7C6qvFRqgdKA2thzZ4eEXc8qjxMqL.9g0V1AiI33CCkaqT0C8W', 2, 'Ciao a tutti!', 'utente'),
(11, 'paolo@gmail.com', 'Paolo', 'PIeravino', '12345678', '$2a$10$eE5csZGeXE5XSZAbjOND6uSGzV7SzOcrByRQP6aULMTmhxQLWcfzq', 1, 'Ciao sono Paolo', 'utente'),
(12, 'luca@gmail.com', 'Luca', 'Vigevano', '123456', '$2a$10$AFbDrimIPJS57tqGR5kD/u4/ZfAItvTzzFiqAxnpOXkRN/hcoJmCa', 1, 'ciao sono Luca', 'utente'),
(13, 'federicoruppa1@gmail.com', 'Federico', 'Ruppa', '123', 'asd', 4, 'asd', 'utente'),
(14, 'federico.ruppa@edu.itspiemonte.it', 'Federicaio', 'Ruppaio', '234', 'qwe', 4, 'qwer', 'utente'),
(15, 'cinzia.perona01@gmail.com', 'Cinzia', 'Perona', '123', '123456', 2, 'ciao sono Cinzia', 'utente'),
(16, 'example@example36.com', 'Paolo', 'PIeravino', '12345678', '$2a$10$7np16RO/lpigEXSdXlYp6u0NnswEWenwbQ8DZox/huklIyJMR9/ua', 3, 'umjk', 'utente'),
(17, 'example@example88.com', 'Paolo', 'asd', '123456', '$2a$10$uu6po.dhizY/Gn1vjZH1.enMyvkdp/ygiEmoSka.H6WvzZniRG6c6', 1, 'ncdjjvfjk', 'utente'),
(19, 'example@example79.com', 'ciaio', 'PIeravino', '123456', '$2a$10$YWgJ1uHDuLa.tutqRTKV1e76ecLU63ZZNRk6LGVFkutm60eRWKLZq', 2, '235twGRYEAHYRE', 'utente'),
(21, 'prova.prova@prova.com', 'paolo', 'marco', '485469465', '$2a$10$71gZ9RerFZAmkk06/CE/5.NBi/M8vL1OlhjjjGt7ixrzTsDNKWXxq', 2, 'askfjghwa', 'utente'),
(22, 'example@example34.com', 'Paolo', 'PIeravino', '1234', '$2a$10$Hv3zr66bJbgsQ2sO7L.dput9FuKKuc4VLoT0iT.5PGFfvwKDeYgf.', 1, 'ciao', 'utente');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id_categoria`);

--
-- Indexes for table `evento`
--
ALTER TABLE `evento`
  ADD PRIMARY KEY (`id_evento`),
  ADD KEY `IdCategoria` (`id_categoria`) USING BTREE,
  ADD KEY `IdUtente` (`id_utente`);

--
-- Indexes for table `file_data`
--
ALTER TABLE `file_data`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UQ_IdUtente` (`id_utente`),
  ADD KEY `id_utente` (`id_utente`);

--
-- Indexes for table `gender`
--
ALTER TABLE `gender`
  ADD PRIMARY KEY (`id_gender`);

--
-- Indexes for table `partecipazione`
--
ALTER TABLE `partecipazione`
  ADD PRIMARY KEY (`id_partecipazione`),
  ADD UNIQUE KEY `UQ_IdPartecipazione` (`id_partecipazione`),
  ADD UNIQUE KEY `UQ_IdUtente_IdEvento` (`id_utente`,`id_evento`),
  ADD KEY `FK_Partecipazione_Evento` (`id_evento`);

--
-- Indexes for table `preferiti`
--
ALTER TABLE `preferiti`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UQ_idUtente_idPreferito` (`id_utente`,`id_utente_preferito`);

--
-- Indexes for table `recensione`
--
ALTER TABLE `recensione`
  ADD PRIMARY KEY (`id_recensione`),
  ADD UNIQUE KEY `UQ_IdUtente_IdEvento` (`id_utente`,`id_evento`),
  ADD KEY `FK_Recensione_Evento` (`id_evento`);

--
-- Indexes for table `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`id_utente`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `id_gender` (`id_gender`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id_categoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `evento`
--
ALTER TABLE `evento`
  MODIFY `id_evento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `file_data`
--
ALTER TABLE `file_data`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `gender`
--
ALTER TABLE `gender`
  MODIFY `id_gender` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `partecipazione`
--
ALTER TABLE `partecipazione`
  MODIFY `id_partecipazione` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;

--
-- AUTO_INCREMENT for table `preferiti`
--
ALTER TABLE `preferiti`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `recensione`
--
ALTER TABLE `recensione`
  MODIFY `id_recensione` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT for table `utente`
--
ALTER TABLE `utente`
  MODIFY `id_utente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `evento`
--
ALTER TABLE `evento`
  ADD CONSTRAINT `FK_Categoria_Evento` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `file_data`
--
ALTER TABLE `file_data`
  ADD CONSTRAINT `Fk_Foto_id_utente` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id_utente`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `partecipazione`
--
ALTER TABLE `partecipazione`
  ADD CONSTRAINT `FK_Partecipazione_Evento` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id_evento`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_Partecipazione_Utente` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id_utente`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `preferiti`
--
ALTER TABLE `preferiti`
  ADD CONSTRAINT `preferiti_ibfk_1` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id_utente`);

--
-- Constraints for table `recensione`
--
ALTER TABLE `recensione`
  ADD CONSTRAINT `FK_Recensione_Evento` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id_evento`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_Recensione_Utente` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id_utente`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `utente`
--
ALTER TABLE `utente`
  ADD CONSTRAINT `FK_Gender_Utente` FOREIGN KEY (`id_gender`) REFERENCES `gender` (`id_gender`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
