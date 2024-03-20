/* DELETE 'eventosDB' database*/
DROP SCHEMA IF EXISTS eventosDB;
/* DELETE USER 'spq' AT LOCAL SERVER*/
DROP USER IF EXISTS 'spq'@'localhost';

/* CREATE 'eventosDB' DATABASE */
CREATE SCHEMA  eventosDB;
/* CREATE THE USER 'spq' AT LOCAL SERVER WITH PASSWORD 'spq' */
CREATE USER IF NOT EXISTS 'spq'@'localhost' IDENTIFIED BY 'spq';

GRANT ALL ON messagesDB.* TO 'spq'@'localhost';