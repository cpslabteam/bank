# Simple Bank Application 

This is a simple bank application using our technology stack, and will be used in comparisons with the NSX.

[TODO's](TODO.md)  
[REST API](server-resources.md)

### Contents
[**Technologies**](#technologies)  
[**Documentation**](#documentation)  
[**Running Instructions**](#running-instructions)

## Technologies

* [Java JDK 8]
* [AngularJS]
* [Bootstrap]
* [Restlet]
* [Hibernate]
* [Postgresql]

## Documentation

### Models

#### Data Model

![Bank Data Model][DataModel]

## Running Instructions

**Requires Eclipse Mars and PostgreSQL 9.4.4 or greater**

1. Import the *bank* project into Eclipse by importing as *File->Import->Maven->Existing Maven Projects*. 

2. Open *hibernate.cfg.xml* file in the *bank/src/main/resources/hibernate* directory, and change the database connection settings to fit your own, or adjust your properties to match the existing values. 

3. Connect to the postgres database to use, and run the *reset_bank.sql* script, located in *bank/scripts/database*. 

4. Run the *ServerActivator* class in *bank/src/main/java/cpslabteam/bank/server* to start the server. 

5. Access the client by addressing http://localhost:8000 or access the REST API by addressing http://localhost:9192/*{resource}*. To test the REST API use the [Postman] app.

####Optional
To display colored messages in eclipse console install the [ANSI Console] plugin (instructions in the link). If not then disable the color properties in *bankServer/src/main/resources/log4j.properties* by commenting out the color related properties and uncommenting the uncolored ones.

[//]: # (link variables)

   [Java JDK 8]: <http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html>
   [AngularJS]: <http://angularjs.org>
   [Bootstrap]: <http://getbootstrap.com/>
   [Restlet]: <https://restlet.com/>
   [Postgresql]: <http://www.postgresql.org/>
   [Hibernate]: <http://hibernate.org/>
   [DataModel]: <https://github.com/cpslabteam/bank/blob/master/docs/models/BankDataModel.png>
   [Postman]: <https://www.getpostman.com>
   [ANSI Console]: <http://mihai-nita.net/2013/06/03/eclipse-plugin-ansi-in-console/>
