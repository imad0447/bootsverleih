CREATE TABLE T_Kunden (
   PKundenNr   INT(4) NOT NULL AUTO_INCREMENT,
   Anrede 	varchar(4) NOT NULL,
   Vorname	varchar(32) NOT NULL,
   Nachname	varchar(32) NOT NULL,
   Straße varchar(32) NOT NULL, 
   PLZ   char(5) NOT NULL , 
   Wohnort varchar(32) NOT NULL, 
   Geburtsdatum date NOT NULL,
   PRIMARY KEY (PKundenNr)
) AUTO_INCREMENT= 1000;

CREATE TABLE T_Boote (
   PBootNr  INT(3) NOT NULL AUTO_INCREMENT,
   Typ varchar(32) NOT NULL, 
   Farbe varchar(32) NOT NULL, 
   Kategorie varchar(32) NOT NULL, 
   MaxPersonen smallint NOT NULL, 
   Baujahr year NOT NULL, 
   PRIMARY KEY (PBootNr)
) AUTO_INCREMENT= 100;

CREATE TABLE T_Buchungen (
    PBuchungNr INT(5) NOT NULL AUTO_INCREMENT,
    FKundenNr   INT(4) NOT NULL,
    FBootNr  INT(3) NOT NULL,
    Ausleihdatum date NOT NULL,
    PRIMARY KEY (PBuchungNr),
    FOREIGN KEY (FKundenNr) REFERENCES T_Kunden (PKundenNr),
    FOREIGN KEY (FBootNr) REFERENCES T_Boote (PBootNr)
    
) AUTO_INCREMENT= 10000 ;