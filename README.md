Deusto Eventos
============================

This example relies on the DataNucleus Maven plugin. Check the database configuration in the *datanucleus.properties* file and the JDBC driver dependency specified in the *pom.xml* file. In addition, the project contains the server and client example codes.

Run the following command to make tests:

      mvn test

Run the following command to build everything:

      mvn clean compile

Make sure that the database was correctly configured. Use the contents of the file *eventosDB.sql* to create the database and grant privileges. For example,

      mysql –uroot -p < sql/eventosDB.sql

therfore, execute the following command to enhance the database classes

      mvn datanucleus:enhance

Run the following command to create database schema for this sample.

      mvn datanucleus:schema-create

If it's required to run the integration tests, run the following command:

      mvn verify -Pintegration-tests

If it's required to run the performance tests, run the following command:

      mvn verify -Pperformance-tests

To launch the server run the command

    mvn jetty:run

To launch the web client make next steps:
      1. Open a internet navigator
      2. Search next url --> localhost:8080/inicio.html

