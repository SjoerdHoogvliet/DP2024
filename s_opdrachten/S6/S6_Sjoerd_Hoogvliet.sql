-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S6: Views
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
-- ------------------------------------------------------------------------


-- S6.1.
--
-- 1. Maak een view met de naam "deelnemers" waarmee je de volgende gegevens uit de tabellen inschrijvingen en uitvoering combineert:
--    inschrijvingen.cursist, inschrijvingen.cursus, inschrijvingen.begindatum, uitvoeringen.docent, uitvoeringen.locatie

CREATE OR REPLACE VIEW deelnemers AS
SELECT inschrijvingen.cursist, inschrijvingen.cursus, inschrijvingen.begindatum, uitvoeringen.docent, uitvoeringen.locatie
FROM inschrijvingen
         INNER JOIN uitvoeringen ON inschrijvingen.cursus = uitvoeringen.cursus

-- 2. Gebruik de view in een query waarbij je de "deelnemers" view combineert met de "personeels" view (behandeld in de les):
--     CREATE OR REPLACE VIEW personeel AS
-- 	     SELECT mnr, voorl, naam as medewerker, afd, functie
--       FROM medewerkers;

SELECT cursist.medewerker AS cursist, docent.medewerker AS docent
FROM deelnemers d
         INNER JOIN personeel cursist ON cursist.mnr = d.cursist
         INNER JOIN personeel docent ON docent.mnr = d.docent

-- 3. Is de view "deelnemers" updatable ? Waarom ?
-- Nee, deelnemers selecteert uit twee tabellen, namelijk inschrijvingen en uitvoeringen, daarom kan het niet updatable zijn


-- S6.2.
--
-- 1. Maak een view met de naam "dagcursussen". Deze view dient de gegevens op te halen:
--      code, omschrijving en type uit de tabel curssussen met als voorwaarde dat de lengte = 1. Toon aan dat de view werkt.

CREATE OR REPLACE VIEW dagcursussen AS
SELECT code, omschrijving, type
FROM cursussen
WHERE lengte = 1;

-- Bekijk de bijgevoegde foto's/foto's in de package, "cursussen.png" toont het SELECT * statement op cursussen, we zien hier drie cursussen met lengte 1, "VIEW_dagcursussen.png" toont die drie cursussen

-- 2. Maak een tweede view met de naam "daguitvoeringen".
--    Deze view dient de uitvoeringsgegevens op te halen voor de "dagcurssussen" (gebruik ook de view "dagcursussen"). Toon aan dat de view werkt

CREATE OR REPLACE VIEW daguitvoeringen AS
SELECT u.cursus, u.begindatum, u.docent, u.locatie
FROM uitvoeringen u
         JOIN dagcursussen d ON d.code = u.cursus

-- 3. Verwijder de views en laat zien wat de verschillen zijn bij DROP view <viewnaam> CASCADE en bij DROP view <viewnaam> RESTRICT

-- Bekijk de bijgevoegde foto's/foto's in deze package, DROP VIEW <> CASCADE zorgt ervoor dat ook alle views waarin deze view gerefrenced wordt gedropt worden, RESTRICT dropt slechts de huidige tabel